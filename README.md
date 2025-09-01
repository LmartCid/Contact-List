# Contact-List
- Contact management application built with Java, exposing a TCP  server to handle a list of contacts stored in a relational database through JDBC.

# Features
- ➕ Add new contact (name, phone, email)
- 📃 List all contacts
- 🗑️ Delete a contact by `id`.
- ✏️ Update a contact’s phone number
- 🔌 Client-server architecture** using TCP sockets
- 🗄️ Persistence via JDBC (MySQL).

  # 🧱 Architecture (overview)
- TCP Server (Java)
  - Listens for connections on a port (9999)
  - Executes JDBC operations against the database
  - Sends responses back to the client in plain text.

    # 🗃️ Database Schema (for MySQL)
```sql
CREATE TABLE contactos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  apellido VARCHAR(20) NOT NULL,
  telefono VARCHAR(10) NOT NULL,
  correo VARCHAR(60) NOT NULL
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

# ⚙️ Configuration File:
-There is a file configuration where you have some data regarding to important aspects for database and to stablish database connnection correctly
  such as username, password or url connection for mySql.

# 🚀 Run the Project
- Create a Run Configuration with the server’s main class. Run server before running client.
- Ensure the JDBC driver is in the classpath.
-You must have installed some tools previously: mySql, java with jdk 21 and Eclipse as IDE or others like net Beans. 






