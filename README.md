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
1. Create local mySQL database and pass connection information (url, username, password) into `application.properties` (lines 2-4). 
2. Configure desired SMTP server configuration in `application.properties` (lines 15-20). 
2. Execute `CharityApplication` class main method

Note: Application will populate database with crucial entries and create one user with admin privileges using queries located in import.sql dump file.
  
Admin credentials:  
login: admin@admin.pl  
password: Admin!123

 
	
	
