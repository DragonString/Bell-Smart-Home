Bell Smart Home
====================
- WeMos D1 Mini를 이용한 스마트홈 플랫폼 제어 서버  
- 연관 프로젝트: https://git.bellsoft.net/Bell/BSHN  


## ※ Auto DevOps CI/CD Status
- master : 
[![pipeline status](https://git.bellsoft.net/Bell/BSH/badges/master/pipeline.svg)](https://git.bellsoft.net/Bell/BSH/commits/master)
- develop : 
[![pipeline status](https://git.bellsoft.net/Bell/BSH/badges/develop/pipeline.svg)](https://git.bellsoft.net/Bell/BSH/commits/develop)
  
## ※ 질문 전 체크사항  
#### Q. 프로젝트 클론했는데 컴파일에러가 오지게 뜹니다.
* [ ]  클론 후 프로젝트 자동 Import를 체크했다면, 기존 프로젝트를 삭제하고 Existing Gradle Project 로 임포트하세요.  
* [ ]  lombok 라이브러리를 IDE에 설치하세요.  
* [ ]  프로젝트를 재 빌드하세요.  

#### Q. 프로젝트를 클론해서 보는데 한글이 다 깨져서 보입니다.  
* [ ]  인코딩 설정을 UTF-8로 변경하세요.  


## ※ 개발 전 환경설정  
- STS - Windows - Preferences  
1. General - Workspace 에서 Text file encoding을 UTF-8 로 변경   
2. lombok.jar 파일을 실행해서 STS 설치폴더에 lombok 의존성 설치     
3. 프로젝트 클론 후 '프로젝트 우클릭 - Gradle - Refresh Gradle Project' 으로 의존성 체크 진행 필수   
4. Lombok Library 를 사용하는데 이클립스 버그로 정상 커밋을 받았는데 컴파일에러가 발생할 때 'Project - Clean...' 으로 로컬에 다시 빌드하면 에러가 사라짐.  


## ※ 이용 예정 기술 및 도구  
- IDE  
Spring Tools Suite 4  

- CI/CD  
GitLab Auto DevOps  

- 언어  
JAVA 1.8.0_221  
Thymeleaf  
HTML  
CSS  
SQL  

- 외부 라이브러리  
Spring Boot 2.1.9  
Gradle 3.x  
Lombok  
JDBC MySQL Connector  
JPA  
DevTools  
등등..

- 물리 서버  
Dell PowerEdge R610 (6C 12T * 2, DDR3 ECC 64GB)  
Dell PowerEdge R710 (6C 12T * 2, DDR3 ECC 24GB)  
I7 2600 (4C 8T, DDR3 32GB)  

- 가상 서버(vSphere 6.7)  
Ubuntu Server 18.04 LTS  
GitLab Community Edition 12.2.5  
MySQL 5.7.27  
Kubernetes Cluster 1.15.4  
DNS(Bind9)  
NTP  



## ※ 이용 사이트  
- BSN GitLab : https://git.bellsoft.net/  
- Project Repository : https://git.bellsoft.net/Bell/BSH  


## ※ 참고 사이트  
- STS : https://download.springsource.com/release/STS4/4.4.0.RELEASE/dist/e4.13/spring-tool-suite-4-4.4.0.RELEASE-e4.13.0-win32.win32.x86_64.zip  
- Docker System Status : https://status.docker.com/  
