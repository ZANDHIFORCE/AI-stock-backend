# 📈 AiStock Prediction System

AI 기반 주가 예측 데이터 관리 및 분석을 위한 Spring Boot 백엔드 애플리케이션입니다. 예측 가격과 현재 가격의 차이(Gap)를 분석하여 유망 종목 선별을 지원합니다.

## ✨ 주요 기능
- **주가 예측 데이터 관리**: 종목별 예측가, 현재가, 변동폭(Gap) 저장 및 관리
- **Gap 자동 계산**: 데이터 입력 시 예측가와 현재가의 차이를 비즈니스 로직에서 자동 산출
- **상태 관리**: `useYN` 플래그를 통한 최신 활성 데이터 필터링 조회
- **REST API 제공**: 외부 연동을 위한 주식 데이터 조회 API 엔드포인트 지원

## 🛠 기술 스택
- **Backend**: Java 17, Spring Boot 3.x
- **Database**: H2 Database (Development) / JDBC
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
- `GET /api/stocks`: 전체 예측 데이터 목록 조회
- `GET /api/stocks?useYN=Y`: 현재 활성화된 예측 데이터만 조회

---
© 2026 ZANDHIFORCE. All rights reserved.
