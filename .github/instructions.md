# CodeRabbit 리뷰 가이드라인

## ✅ 프로젝트 배경
- **언어/프레임워크**: Java 17, Spring Boot
- **아키텍처**: 레이어드 아키텍처 + DDD 패키지 구조
- **목표**: 도메인 순수성 유지, SOLID 원칙 준수, 유지보수성과 확장성 높은 코드 작성

---

## 📂 패키지 구조 (DDD)

---

## 🔎 리뷰 체크포인트

### 1. SOLID 원칙
- **SRP**: 클래스가 단일 책임을 지니는가?
- **OCP**: 정책 확장이 if-else 대신 전략/상태 패턴으로 가능한가?
- **LSP**: 하위 타입이 상위 계약을 위배하지 않는가?
- **ISP**: 인터페이스가 필요한 기능만 제공하는가?
- **DIP**: 상위 모듈이 구현체 대신 인터페이스에 의존하는가?

### 2. 레이어드 아키텍처
- `presentation → application → domain` 방향만 허용, 역참조 금지
- `domain`은 **스프링/DB 의존성 없음** (순수 자바)
- `infrastructure`만 외부 연동(JPA, 외부 API) 책임

### 3. 엔티티 (JPA)
- `BaseEntity` 상속 (`createdAt`, `updatedAt` 자동 관리)
- `@NoArgsConstructor(access = PROTECTED)`
- Setter 금지, 의미 있는 메서드로 상태 변경
- 연관관계는 **지연로딩(LAZY)**, 필요 시 fetch join

### 4. 서비스 계층
- 트랜잭션은 서비스 계층에 선언 (`@Transactional`)
- 읽기 전용은 `@Transactional(readOnly = true)`
- 도메인 규칙은 domain에 위임, 서비스는 흐름 제어 담당

### 5. 컨트롤러
- 요청/응답 DTO만 노출 (엔티티 반환 금지)
- 검증은 Bean Validation(`@Valid`, `@PastOrPresent`)
- 응답은 `ApiResponse<T>` 통일

### 6. 예외 처리
- 전역 예외 처리(`@RestControllerAdvice`)
- 비즈니스 예외는 계층화 (`NotFoundException`, `DomainException`)
- 에러 메시지/코드는 Enum으로 관리

### 7. 성능/쿼리
- N+1 방지(fetch join, batch size)
- 필요 시 QueryDSL, Native Query 사용 검토
- Pagination 시 count 쿼리 최적화

### 8. 테스트
- 단위 테스트: 도메인/서비스
- 통합 테스트: Repository, Controller (`@SpringBootTest`, Testcontainers)
- Given–When–Then 패턴 준수

### 9. 코드 스타일
- 네이밍: 도메인 용어 기반, 축약 금지 (`isActive`, `hasPermission`)
- import: 와일드카드 금지, 불필요한 import 제거
- 포매팅: 속성명–값 사이 **한 칸만**, 정렬용 다중 공백 금지
- 주석: “무엇”보다 “왜” 설명

---

## 📝 CodeRabbit 요청
리뷰 시 반드시 아래를 확인하세요:
1. SOLID 위반 의심 지점 (거대 클래스, 분기 남발 등)
2. 아키텍처 계층 침범 여부 (domain에서 infra 의존 등)
3. 도메인 모델이 JPA/스프링에 오염되지 않았는지
4. 서비스 계층의 트랜잭션 경계 적절성
5. N+1, 불필요한 쿼리 발생 여부
6. 예외 처리/응답 포맷 일관성
7. 검증(Bean Validation, 도메인 불변식) 누락 여부
8. 테스트 커버리지 및 실패 케이스 포함 여부  