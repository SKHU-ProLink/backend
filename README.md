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
