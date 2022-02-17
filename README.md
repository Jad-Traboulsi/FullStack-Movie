## **Fullstack Movie Project**

**What is it about?** 

This project talks about a movie database that is in MongoDB  and the IDs created are passed to the PostgresSQL database

Users and their contact, address, and movies watched are stored in the PostgresSQL Database.

User's password are hashed in the database using BCrypt.

The PostgresSQL is handled by Java backend, it provides certain APIs to be given so that the frontend can handle.

The MongoDB is handled by a node module that is Javascript based.

The frontend is a React app created that handles both backends.

---

**How To Install**

 1. First you have to make sure you have PostgresSQL, MongoDB, node are installed
 2. In *Databases/JavaBackendDB* restore this file as a backup in PostgresSQL and name it JavaProject
 3. In MongoDBCompass on port 27117 create database called Movies and collection called movieCollection after that import the *movieCollection.json* in *Databases*
 4. Now that the databases are ready, go to *Java Backend\external\src\main\resources\application.properties* and change *spring.datasource.username=XXX* and *spring.datasource.password=YYY*
to your according username and password in PostgresSQL
5. launch the main java application in Java *Backend\external\src\main\java\fr\epita\project\external\ProjectApplication.java*
6. launch the api in *C:\Users\Jadtr\OneDrive\Java... Proj\JavaScript\api* with **npm start** in the cmd
7. launch the react app in *C:\Users\Jadtr\OneDrive\Java... Proj\JavaScript\client* with **npm start** in the cmd

---
Enjoy!