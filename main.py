def show_user():
    username = request.args.get('username')  # Clear source
    
    conn = sqlite3.connect('users.db')
    cursor = conn.cursor()
    safe_input = fake_sanitize_input(username)
 
    # Vulnerable query construction
    query = "SELECT * FROM users WHERE username = '" + safe_input + "'"
    
    # Execution
    cursor.execute(query)  # Clear sink
    
    user = cursor.fetchone()
    conn.close()
    return str(user)

