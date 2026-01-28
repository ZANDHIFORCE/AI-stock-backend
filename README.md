# 📈 AiStock Prediction System

AI 기반 주가 예측 데이터 관리 및 분석을 위한 Spring Boot 백엔드 애플리케이션입니다. 예측 가격과 현재 가격의 차이(Gap)를 분석하여 유망 종목 선별을 지원하며, 웹과 앱 모두를 위한 통합 인증 시스템을 제공합니다.

## ✨ 주요 기능
- **통합 인증 시스템**:
    - **Web**: Spring Security 기반 세션 로그인 및 회원가입 (BCrypt 암호화)
    - **App**: JWT(JSON Web Token) 기반 Stateless 인증 지원
- **공통 레이아웃**: Thymeleaf Fragment를 활용한 반응형 네비게이션 바 구현
- **주가 예측 데이터 관리**: 종목별 예측가, 현재가, 변동폭(Gap) 저장 및 관리
- **Gap 자동 계산**: 데이터 입력 시 예측가와 현재가의 차이를 비즈니스 로직에서 자동 산출
- **검색 및 필터링**: 티커, 종목명, 날짜, 활성 상태(`useYN`) 등 다양한 조건으로 데이터 검색
- **강력한 보안**: 주식 데이터 접근 및 API 호출 시 인증 필수 정책 적용

## 🛠 기술 스택
- **Backend**: Java 17, Spring Boot 3.4.1
- **Security**: Spring Security 6, JWT (JJWT 0.11.5)
- **Database**: PostgreSQL (Remote) / H2 Database (Test) / JDBC
- **View**: Thymeleaf, Bootstrap 5
- **Build Tool**: Gradle

## 🚀 시작하기

### 실행 방법
1. 저장소 클론:
   ```bash
   git clone https://github.com/ZANDHIFORCE/AI-stock-backend.git
   ```
2. 프로젝트 빌드 및 실행:
   ```bash
   ./gradlew bootRun
   ```
3. 접속: `http://localhost:8080`

## 📊 API 엔드포인트

### 인증 관련 (API)
- `POST /user/api/login`: JWT 토큰 발급
    - **Request Body**: `{"userid": "...", "password": "..."}`
    - **Response**: `{"token": "eyJhbG..."}`

### 주식 데이터 관련
- `GET /api/stocks`: 주식 데이터 검색 및 목록 조회 (Bearer 토큰 필요)
    - **Query Parameters**:
        - `ticker`: 종목 코드
        - `name`: 종목명
        - `date`: 날짜 (`YYYY-MM-DD`)
        - `useYN`: 활성화 여부
    - **Headers**: `Authorization: Bearer <JWT_TOKEN>`

---
© 2026 ZANDHIFORCE. All rights reserved.