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


```bash
Branch Name

{타입}-{주제}

feat-공지사항

Commit message

[타입] 본문

타입
[feat] 기능 추가/수정에 관련한 작업명 #00
[fix] 오류 수정과 관련된 작업명 #00
[refactor] 리팩토링 관련 작업명 #00
[docs] 문서 등록/수정 관련 작업명 #00

```

<br>  
