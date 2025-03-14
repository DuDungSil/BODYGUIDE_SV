# 바디가이드 앱 서버 ( MVP ) 

### 주 기능 : 운동 기록, 운동 분석, 유저 활동 통계 

<br/><br/>

## ERD

<img width="900" alt="image" src="https://github.com/user-attachments/assets/0ad9f658-bf1a-434c-b941-dd5281fb85dc">

<br/><br/>

## Architecture

<img width="900" alt="image" src="https://github.com/user-attachments/assets/db496e61-8c3a-456a-9219-fed31fbadd28">

<br/><br/>

## Tech Stack
- Java 17, SpringBoot 3.4.2, Spring Data JPA, QueryDSL
- mySQL, Redis ( cache )
- AWS ( EC2 ), Docker, Nginx
- Swagger
- Spring Security, Kakao/Google Oauth, JWT

<br/><br/>

## API specification
 
[Swagger](https://api.bodyguide.co.kr/swagger-ui/index.html#/)

<br/><br/>

## 기술적 고민

1. 운동 기록을 하면서 각 운동의 분석정보들을 db에서 가져와야함 ( 운동 종류 287 가지 )
    => 유저가 운동 기록시마다 db와 기록한 운동 개수만큼 커넥션 발생
    => 운동 분석 정보들은 정적 데이터이므로 redis에 캐싱하기로 결정
2. 

<br/><br/>

## Developer

|<img width="200" alt="image" src="" />|
|:--:|
|[오민석(DuDungSil)](https://github.com/DuDungSil)|
|mvp|

<br/><br/>

<!--## Article ( 블로그, 유튜브 등 )-->
