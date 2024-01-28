## MeokQ Spring Boot API Project
This repository hosts the Spring Boot API application for the MeokQ service.

## Getting Started
### Git clone
```bash
git clone https://github.com/TeamFair/meokQ-backend.git
```

### Build project
```bash
gradle clean bootJar
```

### Run project
For local environment:
```bash
java -jar -Dspring.active.profiles = local build/libs/api-0.0.1-SNAPSHOT.jar
```
For development environment:
```bash
java -jar -Dspring.active.profiles = dev build/libs/api-0.0.1-SNAPSHOT.jar
```

## Dependencies
### Environment
- Java Version: 17
- Build Tool: Gradle
- Dependency Management: io.spring.dependency-management 1.1.3

### Libraries
- Spring Boot 3.0.1
- Spring Boot Starter Data JPA
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Validation
- Spring Boot DevTools
- Spring Security
- OAuth2 Client 및 Resource Server
- JSON Web Token (JWT) - jjwt 0.11.5
- MariaDB JDBC Driver 2.4.0
- HikariCP Connection Pool 5.0.1
- Amazon AWS SDK 2.20.56
  - Amazon S3
- Springdoc OpenAPI 2.0.2
- Spring Boot Starter Test
  - Spring RestDocs MockMvc
  - Mockito Core 5.2.0
