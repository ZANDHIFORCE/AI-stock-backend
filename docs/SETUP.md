# 로컬 실행과 테스트

[README로 돌아가기](../README.md)

## 준비

- **JDK 17**: `JAVA_HOME`을 JDK 17 설치 폴더로 지정합니다.
- **Gradle 8.13**: 저장소 Wrapper를 사용합니다. 최초 실행 때 배포 파일과 의존성을 내려받습니다.

```bash
git clone https://github.com/ZANDHIFORCE/AI-stock-backend.git
cd AI-stock-backend
java -version
./gradlew --version
```

Windows PowerShell에서는 `./gradlew` 대신 `.\gradlew.bat`를 사용합니다.

## 로컬 H2 실행

```powershell
.\gradlew.bat bootRun --args="--spring.profiles.active=local --server.address=127.0.0.1"
```

macOS / Linux:

```bash
./gradlew bootRun --args='--spring.profiles.active=local --server.address=127.0.0.1'
```

`http://127.0.0.1:8080`에서 확인합니다. `local` 프로필은 `schema.sql`로 빈 H2 테이블을 만들고, 데이터는 프로세스 종료 후 사라집니다. 주석만 있던 `data.sql`은 제거했습니다. `JWT_SECRET`이 없거나 비어 있으면 로컬에서만 `SecureRandom`으로 32바이트 임시 키를 생성합니다. 키는 인증 컴포넌트 초기화 때 한 번 생성하여 유지하며, 재시작하면 이전 토큰은 무효가 됩니다. `JWT_SECRET`을 지정하면 그 값을 사용합니다.

## 테스트

```powershell
.\gradlew.bat clean test bootJar
```

테스트 리소스에서 로컬 H2 설정을 가져오므로 원격 DB 환경변수 없이 실행할 수 있습니다. 테스트 결과는 `build/reports/tests/test/index.html`에서 확인합니다.

## PostgreSQL을 사용할 때

기본 설정은 접속 정보와 `JWT_SECRET`을 외부에서 받아야 하며, 임시 키를 자동 생성하지 않습니다. [.env.example](../.env.example)의 항목을 본인 환경에 맞게 **환경변수로 설정한 뒤** 실행합니다. Spring Boot가 `.env` 파일을 자동으로 읽지는 않습니다. JWT 키는 암호학적 난수 생성기로 만든 32바이트 이상의 값을 사용합니다.

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/aistock"
$env:DB_DRIVER = "org.postgresql.Driver"
$env:DB_USERNAME = "aistock"
$env:DB_PASSWORD = "본인 DB 비밀번호"
$env:JWT_SECRET = "32바이트 이상의 본인 비밀키"
.\gradlew.bat bootRun
```

DB는 [현재 스키마](../src/main/resources/schema.sql)와 호환되어야 합니다. 원격 PostgreSQL 연동은 이번 검증에 포함하지 않았습니다. JWT 서명과 만료 시간(24시간)은 기존 방식 그대로이며, 외부 키를 바꾸면 이전 토큰은 유효하지 않습니다.

Wrapper JAR는 [Gradle 공식 SHA-256](https://gradle.org/release-checksums/)의 8.13 값 `81a82aaea5abcc8ff68b3dfcb58b3c3c429378efd98e7433460610fecd7ae45f`와 대조했습니다. 배포 ZIP 체크섬도 Wrapper 설정에 고정했습니다.

[검증 기록](VALIDATION.md) · [API 안내](API.md)
