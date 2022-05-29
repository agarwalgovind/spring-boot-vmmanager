
#Spring Boot VMManager Steps -

# Install and run the PostgreSQL on the system
# Verify application.properties for any property changes

# Create Database
CREATE DATABASE vmmanagerdb;

# START THE APPLICATION

# Insert data into Roles Table
INSERT INTO ROLES(name) VALUES('NONMASTER');
INSERT INTO ROLES(name) VALUES('MASTER');

# API list provided in the file - src/main/resources/VMManagerDB.postman_collection.json
