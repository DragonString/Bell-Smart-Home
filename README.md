Bell Smart Home
====================
- WeMos D1 Mini를 이용한 스마트홈 플랫폼 제어 서버  
- 연관 프로젝트: https://gitlab.bellsoft.net/connected-life/bell-smart-home-node  


## ※ Auto DevOps CI/CD Status
- master : 
[![pipeline status](https://gitlab.bellsoft.net/connected-life/bell-smart-home/badges/master/pipeline.svg)](https://gitlab.bellsoft.net/connected-life/bell-smart-home/commits/master)
  
## ※ 질문 전 체크사항  
#### Q. 프로젝트를 클론해서 보는데 한글이 다 깨져서 보입니다.  
* [ ]  인코딩 설정을 UTF-8로 변경하세요.  


## ※ 개발 전 환경설정  
- IDE 설정  
`C:\Program Files\JetBrains\IntelliJ IDEA {Version}\bin\idea64.exe.vmoptions`  
메모리 할당량 수정 후 파일 제일 하단에 아래 매개변수 추가  
`-Dfile.encoding=UTF-8`


- 파일 수정 후 저장되지 않았을 시 * 표시 켜기  
`File - Settings` 메뉴  
`Editor > General > Editor Tabs`  
`Mark modified (*)` 체크  


- 인코딩 수정
`File - Settings` 메뉴  
`Editor > File Encodings`  
Encoding 수정  


- DevTools 설정 1/3  
`File - Settings` 메뉴  
`Build, Execution, Deployment > Build Toolls > Gradle`  
`Build and run using:` 항목에 `IntelliJ IDEA` 선택  

- DevTools 설정 2/3  
`File - Settings` 메뉴  
`Build, Execution, Deployment > Compiler`  
`Build project automatically` 체크  

- DevTools 설정 3/3  
`Ctrl + Shift + A` 단축키로 `Registry` 검색  
`compiler.automake.allow.when.app.running` 의 값을 체크  
