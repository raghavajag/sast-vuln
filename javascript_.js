const express = require('express');
const sqlite3 = require('sqlite3').verbose();
const app = express();

app.get('/showUser', (req, res) => {
  // Get username from query parameters (clear source)
  const username = req.query.username;

  // Fake sanitization (ineffective)
  const safeInput = fakeSanitizeInput(username);

  // Establish database connection
  let db = new sqlite3.Database('users.db', (err) => {
    if (err) {
      res.send('Error connecting to database: ' + err.message);
      return;
    }
  });

  // Vulnerable query construction by concatenation
  const query = `SELECT * FROM users WHERE username = '${safeInput}'`;

  // Execution (clear sink)
  db.get(query, (err, row) => {
    if (err) {
      res.send('Error executing query: ' + err.message);
    } else {
      res.send(row ? JSON.stringify(row) : 'No user found');
    }
  });

  // Close database connection
  db.close();
});

// Ineffective sanitization method
function fakeSanitizeInput(input) {
  // This does not prevent SQL Injection
  return input.replace(/'/g, "''");
}

app.listen(3000, () => {
  console.log('Server running on port 3000');
});

function notCalled(input) {
  db.get(query, (err, row) => {
    if (err) {
      res.send('Error executing query: ' + err.message);
    } else {
      res.send(row ? JSON.stringify(row) : 'No user found');
    }
  });

  // Close database connection
}

