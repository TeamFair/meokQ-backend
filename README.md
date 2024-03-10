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

## Convention
### Git convention