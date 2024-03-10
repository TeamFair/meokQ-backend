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
#### Tracking Issue
- Register issue
  - 문제상황/해결방법/추후 개선사항 등을 포함하여 작성.
- commit 메세지는 아래 형식을 따라 작성.
```
[feat] 기능 추가/수정에 관련한 작업명 #00
[fix] 오류 수정과 관련된 작업명 #00
[refactor] 리팩토링 관련 작업명 #00
[docs] 문서 등록/수정 관련 작업명 #00
```

#### branch 전략
- `dev branch`를 default branch로 둔다.
- 각 기능은 branch를 만들어서 작업 완료 후 merge 한다.
- `main branch`는 배포 완료된 버전을 포함한다.
  - 배포전 `Pull request`를 생성한 후, 변경 내역과 버전을 기재한다.
  - 예시 : [publish server V.1.0.5 #25](https://github.com/TeamFair/meokQ-backend/pull/25)
  ```shell
  publish server V.1.0.5 #25
  
  ICH003 조회 조건 반영 [bug] Specification Query Issue : Custom query #17
  ICH003 응답값에 userNickname추가 [feat] ICH003 응답값 추가(userNickname) #23
  IMK004 수정 API 추가(null이 아닌 값만 update) [feat] 마켓정보 수정 API(IMK004) 추가 #18
  ICH005 도전내역 검토결과 등록 API ID 변경(ICH003 -> ICH005)
  ICH004, ICP001 응답 예시 변경
  ICH004 응답 형태 변경(missionTitles, rewardTitles) [feat] IQU004 응답값 단순화 #24
  ```
### Deployment convention
1. 배포간격 : 7~14일 간격으로 배포
2. version up
  - version은 
2. process(현행)
  - Build project
  - Connect to Cloud
  - Copy jar to Cloud
  - Kill Service
  - Run Service
  - Check Service