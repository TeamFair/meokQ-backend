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
java -jar -Dspring.profiles.active = local build/libs/api-0.0.1-SNAPSHOT.jar
```
For development environment:
```bash
java -jar -Dspring.profiles.active = dev build/libs/api-0.0.1-SNAPSHOT.jar
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
//feat-공지사항

Commit message

[타입] 본문

타입
[feat] 기능 추가/수정에 관련한 작업명 #00
[fix] 오류 수정과 관련된 작업명 #00
[refactor] 리팩토링 관련 작업명 #00
[docs] 문서 등록/수정 관련 작업명 #00

```

<br>  

### API NAMING ROLE
-  대내/대외 구분자 1자리 + 도메인 약어 2자리 + 시퀀스 3자리
-  EX) ICP001, IAC001
-  대내/대외 구분자 1자리: I(대내)/E(대외)
  - I(대내): 시스템 내부에서 처리하는 로직을 포함.
  - E(대외): 시스템 외부로 처리하는 로직을 포함. (EX. 결제사 통신 등)
- 도메인 약어 2자리
  - AC(계정), CP(쿠폰) 등
  - 관련 약어등은 정의할 때마다 문서화 해두고 관리하는 것이 필요.
- 시퀀스 3자리
  - 001, 002처럼 3자리로 사용
