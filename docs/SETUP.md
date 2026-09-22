# 로컬 실행과 테스트

[README로 돌아가기](../README.md)

## 준비

- **JDK 17**: `JAVA_HOME`을 JDK 17 설치 폴더로 지정합니다.
- **Gradle 8.13**: 아래 검증에서 사용한 버전입니다. [공식 배포](https://services.gradle.org/distributions/gradle-8.13-bin.zip)를 풀고 `bin` 폴더를 PATH에 추가하거나 실행 파일의 전체 경로를 사용합니다.
- 저장소에는 `gradlew` 스크립트가 있지만 `gradle/wrapper/gradle-wrapper.jar`가 없습니다. 따라서 현재 checkout에서 Wrapper 명령만으로 실행할 수 없습니다. 이 안내는 별도로 준비한 Gradle을 사용합니다.

```bash
git clone https://github.com/ZANDHIFORCE/AI-stock-backend.git
cd AI-stock-backend
java -version
gradle --version
```

## 로컬 H2 환경 선택

기본 `application.properties`는 원격 PostgreSQL을 가리킵니다. 아래 값을 **현재 터미널에서 모두 설정한 뒤** 실행하면 원격 DB 대신 로컬 메모리 H2를 사용합니다. `local-demo`는 로컬 H2에 새로 지정하는 예시 비밀번호입니다.

Windows PowerShell:

```powershell
$env:SPRING_DATASOURCE_URL = "jdbc:h2:mem:readme_demo;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;NON_KEYWORDS=ROLE,PASSWORD,NAME"
$env:SPRING_DATASOURCE_DRIVER_CLASS_NAME = "org.h2.Driver"
$env:SPRING_DATASOURCE_USERNAME = "sa"
$env:SPRING_DATASOURCE_PASSWORD = "local-demo"
$env:SPRING_SQL_INIT_MODE = "always"
$env:SPRING_SQL_INIT_DATA_LOCATIONS = "optional:classpath:/local-demo-data.sql"
$env:SERVER_ADDRESS = "127.0.0.1"
$env:SERVER_PORT = "8080"
gradle bootRun
```

macOS / Linux의 동등한 설정:

```bash
export SPRING_DATASOURCE_URL='jdbc:h2:mem:readme_demo;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;NON_KEYWORDS=ROLE,PASSWORD,NAME'
export SPRING_DATASOURCE_DRIVER_CLASS_NAME='org.h2.Driver'
export SPRING_DATASOURCE_USERNAME='sa'
export SPRING_DATASOURCE_PASSWORD='local-demo'
export SPRING_SQL_INIT_MODE='always'
export SPRING_SQL_INIT_DATA_LOCATIONS='optional:classpath:/local-demo-data.sql'
export SERVER_ADDRESS='127.0.0.1'
export SERVER_PORT='8080'
gradle bootRun
```

`http://127.0.0.1:8080`에서 확인합니다. H2 데이터는 프로세스 종료 후 유지되지 않습니다. 위 설정은 터미널을 닫으면 사라집니다.

### 데이터 초기화 설정을 바꾸는 이유

기존 `data.sql`은 주석만 있어 로컬 초기화에서 빈 스크립트 오류가 발생했습니다. 위 명령은 선택적인 별도 데이터 스크립트 경로를 지정해 해당 파일의 실행을 건너뜁니다. 테이블은 기존 `schema.sql`로 생성하고 데이터는 빈 상태에서 시작합니다. 애플리케이션 소스나 DB 스키마를 수정하지 않습니다.

## 테스트

위 H2 환경변수를 설정한 터미널에서:

```bash
gradle test
```

검증 환경은 Windows, JDK 17, Gradle 8.13입니다. 이 조건에서 기존 테스트 **13개가 통과**했습니다. macOS/Linux 명령은 같은 환경변수를 설정하는 동등한 안내이며 해당 운영체제에서 별도 실행하지는 않았습니다.

테스트 결과 파일: `build/reports/tests/test/index.html`

## PostgreSQL을 사용할 때

본인이 관리하는 개발 DB를 준비한 뒤 URL·드라이버·계정·비밀번호를 모두 외부 설정으로 지정하고 [현재 스키마](../src/main/resources/schema.sql)와 호환성을 확인해야 합니다. 기존 원격 DB 접속 정보는 이 문서에서 사용하지 않습니다. PostgreSQL 연동은 이번 로컬 검증 범위에 포함되지 않습니다.

[검증 기록](VALIDATION.md) · [API 안내](API.md)
