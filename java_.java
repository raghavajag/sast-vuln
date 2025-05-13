import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet {
    public String showUser(HttpServletRequest request, HttpServletResponse response) {
        // Get username from request parameters (clear source)
        String username = request.getParameter("username");
        
        // Fake sanitization (ineffective)
        String safeInput = fakeSanitizeInput(username);
        
        // Establish database connection
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db", "", "");
            Statement stmt = conn.createStatement();
            
            // Vulnerable query construction by concatenation
            String query = "SELECT * FROM users WHERE username = '" + safeInput + "'";
            
            // Execution (clear sink)
            ResultSet rs = stmt.executeQuery(query);
            
            // Process result
            String result = "";
            if (rs.next()) {
                result = rs.getString("username") + ", " + rs.getString("email");
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            return result;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    // Ineffective sanitization method
    private String fakeSanitizeInput(String input) {
        // This does not prevent SQL Injection
        return input.replace("'", "''");
    }
}
