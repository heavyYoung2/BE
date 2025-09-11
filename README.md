# 회비영

## 📌 프로젝트 개요
- 홍익대학교 컴퓨터공학과 학생들을 위한 통합 관리 플랫폼
- 기존에는 학생회비 납부 확인과 각종 서비스를 모두 수기로 처리해 비효율적이고 불필요한 노동이 발생
- 본 프로젝트는 디지털 전환을 통해 운영 과정을 투명하고 효율적으로 개선하고, 학생들이 납부한 회비를 보다 편리하고 활발하게 활용할 수 있도록 하는 것을 목표로 함

## 👥 멤버
| 박형진 | 안제웅 | 윤현일 |
|:------:|:------:|:------:|
| <img src="https://github.com/gud0217.png" width="200" height="200" /> | <img src="https://github.com/ajwoong.png" width="200" height="200" /> | <img src="https://github.com/yhi9839.png" width="200" height="200" /> |
| PL | BE | BE |
| [GitHub](https://github.com/gud0217) | [GitHub](https://github.com/ajwoong) | [GitHub](https://github.com/yhi9839) |

## ⚙️ 기술 스택
- Language: Java 21
- Framework: Spring Boot 3.5.5
- Database: MySQL 8.0.43
- Infra: AWS, Docker, Nginx, GitHub Actions

## 🚀 배포 & 운영 (Deployment & Operation)
- CI/CD: GitHub Actions → Docker Hub → AWS EC2 (예정)
- 운영 환경: Ubuntu 22.04, Nginx Reverse Proxy, HTTPS (예정)
- 모니터링: CloudWatch, Prometheus, Grafana (예정)

## 🔑 주요 기능 (Features)
- 회원 인증: OAuth2 + JWT 기반 로그인
- 회비 관리: 납부 내역 확인 및 QR 검증
- 사물함 관리: 대여, 반납
- 관리자 기능: 대여물품 관리, 회비납부 인증, 공지사항 관리

## 📡 API 문서 (API Reference)
- Swagger: `/swagger-ui/index.html`
- 노션 API 명세서: https://concrete-vise-062.notion.site/API-1df9715a4be680f0858ac72b73ee02d3?pvs=74

## 🏗️ 아키텍처 (Architecture)
flowchart LR
  Client --> BFF --> Backend[(Spring Boot)]
  Backend --> DB[(MySQL)]
  Backend --> S3[(AWS S3)]
  
## 🧪 테스트 (Testing)
- ./gradlew test 실행 시 단위 테스트/통합 테스트 자동화 (예정)
- Jacoco 리포트 제공 (코드 커버리지) (예정)

## 🤝 기여 가이드 (Contributing)
- 브랜치 전략: GitHub Flow (main + feature 브랜치)
  - https://concrete-vise-062.notion.site/Git-Branch-2539715a4be68036af99d68ebaf90759?source=copy_link
- 코드 스타일:
  - https://concrete-vise-062.notion.site/2539715a4be680babbdde21692446613?source=copy_link
- PR 규칙:
   - https://concrete-vise-062.notion.site/Issue-PR-Commit-2539715a4be68074bd71e123523cd16c?source=copy_link
