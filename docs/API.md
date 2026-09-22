# API와 웹 경로

[README로 돌아가기](../README.md)

## JWT 발급

`POST /user/api/login` · `Content-Type: application/json`

웹 회원가입(`/user/signup`)으로 만든 계정을 사용합니다.

```json
{"userid": "demo-user", "password": "your-local-password"}
```

성공 시 토큰 문자열을 반환합니다.

```json
{"token": "<issued-jwt>"}
```

API 요청에는 `Authorization: Bearer <issued-jwt>` 헤더를 사용합니다. 현재 JWT 유효기간은 코드상 24시간입니다. 잘못된 계정·비밀번호는 예외를 발생시키며, 일관된 오류 응답 계약은 아직 별도로 정의되어 있지 않습니다.

## 주식 검색

`GET /api/stocks`

| 선택 파라미터 | 형식 | 검색 방식 |
| :--- | :--- | :--- |
| `ticker` | 문자열 | 종목 코드 일치 |
| `name` | 문자열 | 종목명 부분 일치 |
| `date` | `YYYY-MM-DD` | 날짜 일치 |
| `useYN` | `Y` 또는 `N` | 활성 상태 일치 |

```bash
curl "http://localhost:8080/api/stocks?ticker=AAPL&useYN=Y" \
  -H "Authorization: Bearer <issued-jwt>"
```

응답은 `PreStock`의 JSON 배열입니다. 아래 값은 형식을 보여주기 위한 예시이며, 실제 시세나 모델 예측 결과가 아닙니다.

```json
[
  {
    "id": 0,
    "date": "2024-01-01",
    "ticker": "AAPL",
    "name": "Apple Inc",
    "curPrice": 150.0,
    "predictPrice": 160.0,
    "predictGap": 10.0,
    "useYN": "Y"
  }
]
```

조회 결과가 없으면 빈 배열입니다. `useYN`을 생략하면 활성 레코드만으로 제한하지 않습니다. 날짜는 현재 컨트롤러에서 `LocalDate.parse`로 처리합니다.

## 웹 화면과 API 범위

| 경로 | 용도 |
| :--- | :--- |
| `GET /` | 홈 |
| `GET /user/login` | 로그인 화면 |
| `GET, POST /user/signup` | 회원가입 |
| `POST /user/api/login` | JWT 발급 |
| `GET /api/stocks` | JSON 검색 API |

주식 REST 컨트롤러에는 현재 GET 검색이 구현되어 있습니다. POST·PATCH·DELETE REST API는 구현 완료 기능으로 안내하지 않습니다. 웹 화면의 경로는 [PreStockController](../src/main/java/Nemsi/AiStock/controller/PreStockController.java)를 참고하세요.

## 구현 근거

- [UserApiController](../src/main/java/Nemsi/AiStock/controller/UserApiController.java)
- [StockApiController](../src/main/java/Nemsi/AiStock/controller/StockApiController.java)
- [PreStock](../src/main/java/Nemsi/AiStock/domain/PreStock.java)
- [SecurityConfig](../src/main/java/Nemsi/AiStock/config/SecurityConfig.java)
