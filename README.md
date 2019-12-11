# Oddaj rzeczy
Oddaj rzeczy is a web application allowing for creating donations of unused items to charities. The project follows the MVC model with 
a main purpose of learning Spring framework. 

## Technologies used in  the project
- Spring Boot 2
- Spring Security
- Spring Data
- Hibernate
- Javascript/jQuery
- JSP templates
- MySQL database

## Setup
1. Create local mySQL schema (UTF-8 charset).
2. Pack the project using terminal command `mvn clean package`
3. Execute the application by passing database settings in the following command line:
```
java -jar charity-0.0.1-SNAPSHOT.war 
--spring.datasource.url=<schema-URL>, 
--spring.datasource.username=<database-username>,  
--spring.datasource.password=<database-password>
```
Example:
```
java -jar charity-0.0.1-SNAPSHOT.war 
--spring.datasource.url=jdbc:mysql://localhost:3306/charity_donation?serverTimezone=UTC, 
--spring.datasource.username=root, 
--spring.datasource.password=confidentialpassword
```

You can pass additional settings regarding smtp server with these options (by default the app is using a mock smtp service hosted on https://mailtrap.io/):
```
--spring.mail.host=<smtp-adress>,
--spring.mail.port=<smtp-port>,
--spring.mail.username=<username>,
--spring.mail.password=<password>
```

Note: Application will populate database with crucial entries and create one user with admin privileges using queries located in import.sql dump file.
  
Admin credentials:  
login: admin@admin.pl  
password: Admin!123


 
	
	
