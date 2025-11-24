# 💰 회비영 (H-Fee Manager)
> **홍익대학교 컴퓨터공학과 학생회비 투명성 확보 및 통합 관리 플랫폼**

## 📌 프로젝트 개요
- **홍익대학교 컴퓨터공학과 학생들을 위한 통합 관리 플랫폼**
- 기존에는 학생회비 납부 확인과 각종 서비스를 모두 수기로 처리해 비효율적이고 불필요한 노동이 발생
- 본 프로젝트는 디지털 전환을 통해 운영 과정을 투명하고 효율적으로 개선하고, 학생들이 납부한 회비를 보다 편리하고 활발하게 활용할 수 있도록 하는 것을 목표로 함

## 👥 멤버
| **박형진** | **안제웅** | **윤현일** |
|:------:|:------:|:------:|
| <img src="https://github.com/gud0217.png" width="150" height="150" /> | <img src="https://github.com/ajwoong.png" width="150" height="150" /> | <img src="https://github.com/yhi9839.png" width="150" height="150" /> |
| **BE** | **BE** | **BE** |
| [GitHub](https://github.com/gud0217) | [GitHub](https://github.com/ajwoong) | [GitHub](https://github.com/yhi9839) |

## ⚙️ 기술 스택
| Category | Stack |
| :--- | :--- |
| **Language** | <img src="https://img.shields.io/badge/Java%2021-007396?style=flat-square&logo=java&logoColor=white"/> |
| **Framework** | <img src="https://img.shields.io/badge/Spring%20Boot%203.5.4-6DB33F?style=flat-square&logo=springboot&logoColor=white"/> |
| **Database** | <img src="https://img.shields.io/badge/MySQL%208.0.43-4479A1?style=flat-square&logo=mysql&logoColor=white"/> |
| **Infra** | <img src="https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=amazonwebservices&logoColor=white"/> <img src="https://img.shields.io/badge/Nginx-009639?style=flat-square&logo=nginx&logoColor=white"/> <img src="https://img.shields.io/badge/GitHub%20Actions-2088FF?style=flat-square&logo=githubactions&logoColor=white"/> |

## ERD
<img width="100%" src="https://github.com/user-attachments/assets/659618e5-d6d8-4cda-9871-4a206ac28e38" alt="회비영 ERD">

## 🏗️ 아키텍처 (Architecture)
<img width="80%" src="https://github.com/user-attachments/assets/edc17d6b-56e0-4072-8d78-f4ba44ca31e5" alt="아키텍처">

## 🚀 배포 & 운영 (Deployment & Operation)
* **CI/CD**
    * `GitHub Actions` → `Docker Hub` → `AWS EC2`
* **운영 환경**
    * Ubuntu 22.04, Nginx Reverse Proxy
* **모니터링**
    * CloudWatch, Prometheus, Grafana (예정)

## 🔑 주요 기능 (Features)
- QR
- 물품
- 공지
- 사물함
- 마이페이지

## 📡 API 문서 (API Reference)
| 문서 | 링크 |
| :--- | :--- |
| **API 명세서** | [👉 노션 바로가기](https://concrete-vise-062.notion.site/API-1df9715a4be680f0858ac72b73ee02d3?pvs=74) |

<br>

## 🤝 팀 컨벤션
| Category | Link |
| :--- | :--- |
| **브랜치 전략** | [GitHub Flow 전략 보기](https://concrete-vise-062.notion.site/Git-Branch-2539715a4be68036af99d68ebaf90759?source=copy_link) |
| **코드 스타일** | [Code Style 가이드](https://concrete-vise-062.notion.site/2539715a4be680babbdde21692446613?source=copy_link) |
| **PR 규칙** | [Issue / PR / Commit 규칙](https://concrete-vise-062.notion.site/Issue-PR-Commit-2539715a4be68074bd71e123523cd16c?source=copy_link) |
