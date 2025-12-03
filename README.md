# Maybank-GTDCards
Demo code
Java Backend Interview Assignment:
1. Create a Java SpringBoot Application.
2. Project Structure is required for ease of maintainability and readability.
3. Explore api for Client (Example: to be test via Postman. Please provide the Postman Collection.)
4. Each api required to log REQUEST &amp; RESPONSE info into logs file.
5. Your project able connect to database, preferred with MSSQL database (Using Local Machine DB,
DB name: TESTDB).
- @transactional is required implement in the project. (For http Methos: INSERT, UPDATE, GET
method)
6. Explore 1 GET method api with Pagination (Each Page 10 records)
7. Explore an api which will nested calling another api from 3rd party.
Example: Postman/Client &gt; Your API Endpoint &gt; calling another api
You are able to upload the assignment to a public git folder and share the URL for us to review.


Project structure and prerequisites:
•	Java 21
•	Springboot 3.X.X
•	Mysql or MSSQL

Recommended structure
Project Name : Maybank-GTDCards/
├─ src/main/java/com/maybank
│  ├─ MaybankGtdCardsApplication.java
│  ├─ config/
│  │  ├─ DatabaseConfig.java            # Optional, mostly use application.properties
│  │  ├─ LoggingConfig.java             # Logback configuration (inline or file)
│  │  └─ WebClientConfig.java           # For 3rd-party API calls
│  ├─ interceptor/
│  │  └─ RequestResponseLoggingFilter.java  # Logs request/response
│  ├─ controller/
│  │  └─ CustomerController.java
│  ├─ service/
│  │  └─ CustomerService.java
│  ├─ repository/
│  │  └─ CustomerRepository.java
│  ├─ model/
│  │  └─ Customer.java
│  ├─ dto/
│  │  ├─ ApiResponse.java
│  │  └─ CreateCustomerRequest.java
│  ├─ client/
│  │  └─ ExchangeRateClient.java        # Example 3rd-party nested call
│  └─ exception/
│     ├─ GlobalExceptionHandler.java
│     └─ ResourceNotFoundException.java
├─ src/main/resources/
│  ├─ application.properties
│  └─ logback-spring.xml
├─ pom.xml
 
Prerequisites
•	MSSQL Server running locally with database TESTDB and SQL authentication.
•	Create login and user with appropriate privileges.
•	Dev tools: Java 21, Maven, Postman.
Maven configuration
xml
<!-- pom.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <!-- ✅ Only ONE parent, aligned with Spring Boot 3.3.2 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.2</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>com.maybank</groupId>
    <artifactId>MayBankGtdCard-Mssql-Mysql-Demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>Maybank-GTDCards</name>
    <description>Spring Boot Demo</description>

    <properties>
        <java.version>21</java.version>
    </properties>

    <dependencies>
        <!-- Core Spring Boot starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- Databases -->
     <!--   <dependency>
            <groupId>com.microsoft.sqlserver</groupId>
            <artifactId>mssql-jdbc</artifactId>
            <version>12.6.1.jre17</version>
        </dependency>-->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.0.33</version>
            <scope>runtime</scope>
        </dependency>

        <!-- Reactive WebClient -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- Devtools -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
Configuration files
application.properties
 
# ==============================
# Server configuration
# ==============================
server.port=8080

# ==============================
# SQL Server Datasource (default)
# ==============================
#spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=TESTDB;encrypt=false
#spring.datasource.username=sa
#spring.datasource.password=yourStrong(!)Password
#spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

# ==============================
# MySQL Datasource (alternative)
# ==============================
# comment these lines if you want to use MySQL instead of SQL Server
 spring.datasource.url=jdbc:mysql://localhost:3306/testdb
 spring.datasource.username=root
 spring.datasource.password=root
 spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
 

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# ==============================
# Jackson configuration
# ==============================
#spring.jackson.serialization.write-dates-as-timestamps=false

# ==============================
# Logging configuration
# ==============================
logging.level.root=INFO
logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter=INFO
logging.level.com.example.demo=DEBUG
logging.file.name=logs/app.log


Endpoints :-
Endpoint	Method	Purpose	Notes
/api/customers/{id}	GET	Fetch customer by ID	Transactional read-only
/api/customers	GET	Pagination list	Default size=10; use page param
/api/customers	POST	Create customer	Transactional; validates name/email
/api/customers/{id}	PUT	Update customer	Transactional; prevents duplicate emails
/api/customers/{id}/with-rate	GET	Nested 3rd-party call	Combines customer with USD-INR rate
 
Postman collection:-
Import this JSON in Postman. Adjust host/port if needed.
{
	"info": {
		"_postman_id": "a1d12fb4-2da7-4179-b00d-e6b1f0a52537",
		"name": "SpringBoot MSSQL Demo",
		"description": "Collection to test Customer APIs with logging, transactions, pagination, and nested API call.",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "50502566",
		"_collection_link": "https://mvr-rajendran-2914809.postman.co/workspace/Rajendran-M~91108527-1ddb-4839-99d1-1eb3a97866c6/collection/50502566-a1d12fb4-2da7-4179-b00d-e6b1f0a52537?action=share&source=collection_link&creator=50502566"
	},
	"item": [
		{
			"name": "Get Customer by ID",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/api/customers/1",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"customers",
						"1"
					]
				}
			},
			"response": []
		},
		{
			"name": "List Customers (Pagination)",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/api/customers?page=0&size=10",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"customers"
					],
					"query": [
						{
							"key": "page",
							"value": "0"
						},
						{
							"key": "size",
							"value": "10"
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "Create Customer",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"name\": \"Rajendran\",\n  \"email\": \"rajendran@example.com\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/api/customers",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"customers"
					]
				}
			},
			"response": []
		},
		{
			"name": "Update Customer",
			"request": {
				"method": "PUT",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"name\": \"Rajendran M\",\n  \"email\": \"rajendran.m@example.com\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/api/customers/1",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"customers",
						"1"
					]
				}
			},
			"response": []
		},
		{
			"name": "Get Customer with USD-INR rate",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/api/customers/1/with-rate",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"customers",
						"1",
						"with-rate"
					]
				}
			},
			"response": []
		}
	]
}
Database : 
mysql> create database testdb;
Query OK, 1 row affected (0.02 sec)

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| sys                |
| testdb             |
+--------------------+
5 rows in set (0.02 sec)

mysql> use testdb;
Database changed

mysql> CREATE TABLE customers (
    ->     id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ->     name VARCHAR(100) NOT NULL,
    ->     email VARCHAR(150) NOT NULL UNIQUE,
    ->     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    -> );
Query OK, 0 rows affected (0.14 sec)
mysql> SHOW COLUMNS FROM customers;
+------------+--------------+------+-----+-------------------+-------------------+
| Field      | Type         | Null | Key | Default           | Extra             |
+------------+--------------+------+-----+-------------------+-------------------+
| id         | bigint       | NO   | PRI | NULL              | auto_increment    |
| name       | varchar(100) | NO   |     | NULL              |                   |
| email      | varchar(150) | NO   | UNI | NULL              |                   |
| created_at | timestamp    | NO   |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
+------------+--------------+------+-----+-------------------+-------------------+
4 rows in set (0.00 sec)
mysql> select * from customers;
+----+-----------+---------------------+---------------------+
| id | name      | email               | created_at          |
+----+-----------+---------------------+---------------------+
|  1 | Rajendran | rajendran@gmail.com | 2025-12-03 23:07:55 |
+----+-----------+---------------------+---------------------+
1 row in set (0.00 sec)

POST : http://localhost:8080/api/customers
 
 









GET: http://localhost:8080/api/customers/1
 

Pagination:
GET: http://localhost:8080/api/customers?page=0&size=10 
 
 
 
PUT: http://localhost:8080/api/customers/1
 

GET: http://localhost:8080/api/customers/1/with-rate

 

Thank you for giving me this opportunity. Welcome!
