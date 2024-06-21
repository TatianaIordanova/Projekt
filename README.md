# Database
To configure access to your database, add your username and password in application.properties(lines 11,13 and 27,29).
## "tss" database
### Cars 
The application will automatically create the required Cars and Persons table structure. To add initial data to the Cars table in tss database use the following command:
```
INSERT INTO cars (brand, model, year, color, created, image_file_name) VALUES
("Ford", "Mustang", 1969, "Green", CURRENT_TIMESTAMP,"p-ford-mustang-1969-coupe.png"),
("Chevrolet", "Camaro SS", 2016, "Red", CURRENT_TIMESTAMP, "2016-Chevrolet-Camaro-SS-1012.jpg"),
("Toyota", "Supra", 1998, "Black", CURRENT_TIMESTAMP, "1998_toyota_supra_15430170671c474674751f94108237.jpg"),
("Audi", "RS7-R ABT", 2022, "Silver", CURRENT_TIMESTAMP, "abt-audi-rs7-r-1-19ae704e7ddbff8.jpg")
```
### Persons
To add initial data to the Persons table in tss database use the following command:
```
INSERT INTO persons (id, name, surname) VALUES
(1, 'Adam', 'Mada'),
(10099, 'Roman', 'Nowak'),
(467, 'Jakub', 'Abel')
```
## "auth" database 
Now you should create your own "auth" database, which will store username, crypted password and user roles. 
### Table creating
First create the users table:
```
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    user_pass_crypt VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL
);
```
And then create user_roles table:
```
CREATE TABLE user_roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_name) REFERENCES users(user_name) ON DELETE CASCADE
);
```
### Filling tables
Now enter data for users:
```
INSERT INTO users (user_name, user_pass_crypt, enabled) VALUES ('admin', '$2a$12$dMjwdYNIHXg00h/BsbEwIOdvwmrHI2BVbuTQotT2kk3.ZJHk6sd1i', 1); -- pssword: admin
INSERT INTO users (user_name, user_pass_crypt, enabled) VALUES ('user', '$2a$12$f09cyjm1jy/ku501t/WR5e7Xwj6kJTq.RqzaGHTP.fNUaBSEe.Cpy', 1); -- pssword: user
```

And then enter data for user_roles:
```
INSERT INTO user_roles (user_name, role_name) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_roles (user_name, role_name) VALUES ('user', 'ROLE_USER');
```

# HTTPS certificate
In my apache-tomcat root directory in the "conf" folder there is a file with an HTTPS certificate called pass_tomcatPKCS12.keystore. 
I configured the certificate to work in the application.properties file. If you have your own certificate, then you can put it in this folder and set its configuration.
