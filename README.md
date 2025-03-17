# 바디가이드 앱 서버 ( MVP ) 

### 주 기능 : 운동 기록, 운동 분석, 유저 활동 통계 

<img width="900" alt="image" src="(https://github.com/user-attachments/assets/1ef6826f-f002-4ad5-9ec0-760989125edc)">

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

## 1. 운동 분석 데이터 캐싱을 통한 성능 최적화  
- 유저가 운동을 기록할 때, **운동 종류(총 287가지)에 대한 분석 정보**를 DB에서 조회해야 함
- 기록한 운동 개수만큼 **DB 커넥션이 발생**하여 성능 저하 가능성이 존재  
- 하지만 운동 분석 정보는 잘 바뀌지 않고 **정적 데이터**이므로  
  → **Redis에 캐싱하여 조회 속도를 최적화**  
    <br/>
    
## 2. 운동 기록과 분석의 비동기 처리  
- 운동 기록 시, **유저의 3대 운동, 근육 부위별 최고 점수 등의 통계를 업데이트**해야 함  
- 운동 기록 후 **즉시 분석을 수행하면 응답 시간이 길어질 가능성**이 있음  
- 하지만 애플리케이션 구조상  
  → **기록 완료 시점과 분석 완료 시점 간의 시간 차이가 허용 가능**  
- 따라서  
  → **운동 기록과 분석을 비동기 방식으로 병렬 처리하여 속도와 효율을 향상**
    <br/>

<br/><br/>

## Developer

|<img width="200" alt="image" src="" />|
|:--:|
|[오민석(DuDungSil)](https://github.com/DuDungSil)|
|mvp|

<br/><br/>

<!--## Article ( 블로그, 유튜브 등 )-->
