## 팀원 소개

<table>
<tr>
<td align="center"><b>김보민</b></td>
<td align="center"><b>박대경</b></td>
<td align="center"><b>박연지</b></td>
</tr>

<tr>
<td align="center">
<img src="https://github.com/bomin0214.png" width="150">
</td>

<td align="center">
<img src="https://github.com/ggok0265.png" width="150">
</td>

<td align="center">
<img src="https://github.com/yeonja23.png" width="150">
</td>
</tr>

<tr>
<td align="center">BE</td>
<td align="center">BE, Lead</td>
<td align="center">BE</td>
</tr>

<tr>
<td align="center">
추후 작성
</td>

<td align="center">
추후 작성
</td>

<td align="center">
추후 작성
</td>
</tr>

</table>

## 🌳 Branch Strategy

| 브랜치 | 설명 | 생성 기준 |
|---|---|---|
| `main` | 배포 가능한 안정 버전 | develop에서 최종 merge |
| `develop` | 기능 개발 통합 브랜치 | 기본 개발 브랜치 |
| `feature/*` | 새로운 기능 개발 | develop에서 생성 |
| `fix/*` | 버그 수정 | develop에서 생성 |
| `hotfix/*` | 긴급 버그 수정 | main에서 생성 |
| `refactor/*` | 코드 리팩토링 | develop에서 생성 |
| `docs/*` | 문서 수정 | develop에서 생성 |

## ✨ Commit Type

| Type | 설명 |
|-----|-----|
| feat | 새로운 기능 추가 |
| fix | 버그 수정 |
| docs | 문서 수정 (README 등) |
| style | 코드 포맷팅, 세미콜론 등 (기능 변화 없음) |
| refactor | 코드 리팩토링 |
| test | 테스트 코드 추가 및 수정 |
| chore | 빌드, 패키지, 설정 파일 수정 |
| perf | 성능 개선 |
| ci | CI/CD 관련 설정 수정 |
| build | 빌드 시스템 수정 |

## 🏷 Label Guide
- 이슈 및 PR에는 작업의 유형과 도메인을 명확히 하기 위해 라벨을 사용한다.
- 각 이슈/PR에는 **최대 2~3개의 라벨을 조합하여 사용한다**
### 📌 작업 유형 라벨

- `feature` : 새로운 기능 개발과 관련된 작업  
- `bug` : 기능 오류, 예외 발생 등 버그 수정 작업  
- `hotfix` : 운영 환경에서 발생한 긴급한 버그 수정  
- `refactor` : 기능 변화 없이 코드 구조 개선 및 리팩토링  
- `docs` : README, API 문서 등 문서 작성 및 수정  


### 📌 도메인 라벨

- `api` : REST API 개발 및 수정 관련 작업  
- `db` : 데이터베이스 설계, 쿼리, 엔티티 관련 작업  
- `auth` : 로그인, 인증/인가, 보안 관련 작업  
- `config` : 환경설정, yml, 서버 설정 등 설정 작업  
- `deploy` : 배포, 서버 운영, CI/CD 관련 작업  

### 📌 사용 예시
```
feature + api
bug + auth
hotfix + deploy
refactor + db
docs + api
```

### 📌 규칙
- 이슈 생성 시 라벨을 반드시 지정한다
- 라벨 없이 생성된 이슈는 작업을 진행하지 않는다
