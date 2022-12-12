# TACBAETICS

## 프로젝트 기획
### 프로젝트 설명
> 💡 택배를 전략적(Tactics)으로 관리할 수 있는 배송 관리 시스템 **TACBAETICS** 입니다.
> <br/><br/>

### 설계 고려사항
<details>
<summary>📌 데이터 기준</summary>
<div markdown="1">
<br/>
 1. 택배 데이터 수 : 500만개
 
 - 2021년 택배 물동량 36억건
    - [2021년 택배물동량 36억개, 가격 인상도 145원에 달해](https://www.klnews.co.kr/news/articleView.html?idxno=304003)
    - [택배시장, 대한통운 압도적 1위](https://brunch.co.kr/@logibridge/247)
    
 - 2021년 국내 하루 택배물동량 
 	- 하루평균 약 천만건 
	- 그 중 가장 큰 점유율을 가지고 있는 CJ대한통운 점유율 약 41.9%, 
	- 따라서 대략 5,000,000 건의 물량을 커버할 수 있도록 테스트 및 설계
</div>
</details>

<details>
<summary>📌 Latency, Throughput 목표</summary>
<div markdown="1">
<br/>

 1. Latency 목표값 설정  
 
  ```
 📢 택배 업무의 실시간성으로 인해 택배기사는 1초이내 최대한 빠른 응답을 바랄것이며, 관리자도 단순조회는 1초이내, 소량 업데이트는 10초이내, 대량업데이트는 1분이내를 원할 것으로 예측한다.
  ```
  
   * 일반적인 경우 : 0.05~0.1초
   * 복잡한 트랜잭션이 필요한 경우 : 10초이내
  
 2. Throughput 목표값 설정
 
  ```
 📢 News1 자료(2021년 기준)를 통한 예측으로, DAU(Daily Active User, 하루 순수 이용자) 추이는 평균 약 2800 명이다.
  ```
  
   * DAU : 2800 (단위 : 명)
   * 안전계수 : 3
   * 1일 평균 접속 수에 대한 최대 피크 때 배율 : 2배<br/><br/>
   * 1명당 평균 접속 수 : 1회<br/>
   &nbsp; ⇒ 2,800(명) * 1(회) / 86,400(초) * 3(안전계수) * 2(1일 평균 접속 수에 대한 최대 피크 때 배율) = 약 185 rps

  	
</div>
</details>

<details>
<summary>📌 동시성 제어 기준</summary>
<br/>

 ```
 📢 1개의 물류센터 당 약 25,000건 시나리오, 따라서 25,000건당 관리자1명, 택배기사 65명의 사용자 예측
 ```
 
 1. 1초당 최대 동시 접속자 수 : 2800명 (5,000,000건 기준)
	
 2. 시간 당 처리량 : 가용성이 보장되는 범위의 최대치
 
 	* 앞선 Latency의 내용을 참고하여 택배기사는 가능한 빠른 응답을 원하고 있음
 
 
 
<div markdown="1">
</div>
</details> 

## 아키텍처
![tacbaeticsArchitect](https://user-images.githubusercontent.com/113872320/206975527-8d22c161-c81f-449f-83d7-8c676e68f1b1.png)

## 핵심 기능

### 🔍 검색(필터 및 정렬)

> * **Query DSL**을 통한 관리자용 택배의 각종 정보 및 배송상태 조회기능
> * 택배기사용 택배(자신에게 할당된) 정보 조회기능
> * 지역별 택배 현황 카운팅 기능으로 택배 배송율 모니터링 기능
> * 쿼리 성능 개선을 통해 모든 페이지에서 ??초 이내로 응답(Latency 목표값 달성)
> * 부하테스트를 통해 DB 병목 지점을 확인 -> 커버링 인덱스 사용

### 📦 업데이트

> * 전체 업무 시작전 지역별 배송 담당자 배정 기능을 통한 벌크업데이트 기능
> * 운송장 검색을 통한 택배 상태 및 배송담당자 수정 기능
> * 택배기사의 실시간 배송완료처리 및 완료취소 기능
> * 성능 개선(bulk update 쿼리)

### 🧐 지역별 배송 담당자 배정 자동 추천 기능
> * 지역별 난이도 및 택배기사의 희망수량에 따라 배정표 자동 추천 기능

### 🐈‍⬛ Github Actions + Docker를 활용한 CI/CD
> * 배포 자동화를 통해 효율적인 협업 및 작업 환경 구축
> * 심플하고 접근이 쉬운 Github Actions로 결정

## 트러블슈팅

<details>
<summary>🏪 관리자용 검색 및 업데이트 성능 개선</summary>
<div markdown="1">

- **필요성**
    - 택배 데이터가 500만개 및 이에따라 동시사용자가 증가하면서 **응답시간이 증가**

  ⇒ 관리자의 입장에서 응답시간이 길다고 이탈하지는 않겠지만, 업무의 효율성이 떨어지게됨

  ⇒ 목표 : 페이지 로딩 시간 **10초 이내**

- **진행 단계**
    - 1. 조회 관련 해결시도중

    - 2. 업데이트 관련 해결시도중

👇🏻**더 자세한 내용이 알고싶다면?**👇🏻

[관리자용 성능 테스트 및 개선](https://www.notion.so/fce84cd73e1d48f192c152ce39c11a9c)

</div>
</details>

<details>
<summary>📈 업데이트 성능 개선</summary>
<div markdown="1">

- **필요성**
    - 상품 데이터가 100만개로 증가하면서 **메인페이지 로딩 시간이 최대 4.8초까지 증가**

  ⇒ KISSmetrics에 따르면 로딩시간 3초 이상 시 고객의 40% 이탈률 발생

  ![Untitled](https://user-images.githubusercontent.com/31721097/190343898-e73ba4c4-e934-47bc-b404-ec0f699f90c3.png)

  ⇒ 목표 : 페이지 로딩 시간 **2초 이내**

- **진행 단계**
    - 1. Index 생성
        - **적용 계기**
        - **결과 분석**

    - 2. Covering Index 생성
        - **적용 계기**

          : 성능을 개선하기 위해 Full Scan이 발생하는 product 테이블 개션의 필요성을 알게 됨

          => Covering Index를 통해 'where, order by, offset ~ limit'를 인덱스 검색으로 빠르게 처리하고 **걸러진 데이터를 통해서만 데이터 블록에 접근**

        - **QueryDSL에서의 Covering 인덱스 적용**

          : QueryDSL의 경우 from절의 서브쿼리를 지원하지 않음

          ⇒ `커버링 인덱스를 활용하여 조회 대상의 PK를 조회`하는 부분과 `해당 PK로 필요한 컬럼 항목들을 조회`하는 부분을 나누어 구현

        - **결과 분석**
            - 개선된 부분
                - 마지막 페이지 속도가 **최대 236%까지 개선**
            - 추가 개선이 필요한 부분
                - 첫페이지에 대해 목표했던 **2초 이내의 결과는 달성하지 못함**
                - **키워드 검색**에 대한 성능 개선이 다른 항목들에 비해 많이 되지 않음
    - ~~6. Full-Text Index 생성 및 match()-against() Query 사용~~
        - **적용 계기**

          : 마지막 페이지 부분이 개선되었으나 처음 목표했던 2초 이내는 달성하지 못함. 그래서 엘라스틱서치 적용도 고려했지만 엘라스틱서치의 역인덱싱 방식과 같은 원리로 동작하는 MySQL의 Full-Text 인덱스를 알게됨

- **결과**

  ![Untitled](https://user-images.githubusercontent.com/31721097/190360338-705e078d-1c1b-4acd-9fb8-0c82a4dd0e14.png)


👇🏻**더 자세한 내용이 알고싶다면?**👇🏻

[성능 테스트 및 개선](https://www.notion.so/d08327e203924032879a77930913000e)

</div>
</details>

<details>
<summary>📶 부하 테스트</summary>
<div markdown="1">

- **테스트 계기**
    - 대용량 데이터를 처리하는 패션 플랫폼과 같이 많은 이용자들이 사용해 부하가 많이 발생했을 때 시스템이 정상 작동하는지 여부와 응답 성능을 예측하기 위함
- **목표값 설정**
    - [Latency 목표값, Throughput 목표값 설정](https://www.notion.so/MUCOSA-5fe8f0d732234c56b643b24310ab7d33)
- **병목 현상 확인**
    - 조회 성능 검증 단계 진행 중 **rps는 목표값 안**에 들어오지만 **Latency가 급격하게 증가**하고 **Success Rate가 떨어지는 현상** 발견

      ⇒ **DB가 병목 구간**일 것으로 추론

      ⇒ 메인페이지 로딩, 검색 페이지 로딩 시 RDS의 **CPU가 94.47%**까지 상승
    ![MUCOSA (2)](https://user-images.githubusercontent.com/31721097/190361709-bebc8354-5555-471f-9222-f24bfde5167c.png)

- **대안**
    1. ~~일부 Query를 Application단에서 처리함으로써 DB 부하를 낮춤~~
        - 개발 일정상 무리가 있다고 판단
    2. ~~DB Scale Out을 통해 DB 성능 개선~~
        - 개발 일정상 무리가 있다고 판단
    3. **DB Scale Up을 통해 DB 성능 개선**
- **결과**

  ![MUCOSA (3)](https://user-images.githubusercontent.com/31721097/190361725-375137e1-492a-4534-ae69-cd47a43d5f2e.png)

- **결과분석**
    - t3.micro ⇒ **r5.8xlarge** 인스턴스로 교체 후 조회 페이지 테스트 진행 시 **목표했던 Latency와 Throughput을 충족**

👇🏻**더 자세한 내용이 알고싶다면?**👇🏻

[부하 테스트 및 개선](노션링크)

</div>
</details>

<details>
<summary>🤖 로깅</summary>
<div markdown="1">

- **로깅 기능의 필요성 및 목표**
    - 애플리케이션 최적화를 위해서 **로직이 작동하는 시간**을 기록 및 측정
    - 로직의 검증을 위해서 사용자의 **요청 및 서버의 응답**을 기록
    - 기존에 작성된 로직에 영향을 끼치거나 로직의 변경이 있으면 안된다.
- **문제점**
    - 로그가 필요한 곳에 일일이 로그 로직을 작성해야 한다.
    - 중복된 로그 로직 때문에 유지보수 및 업데이트 비용이 발생한다.
- **문제 해결**
    - 로그 기능을 횡단 관심사(부가 기능)라고 판단 **AOP**를 사용하여 일관성 있는 로직을 구현

</div>
</details>

## 프로젝트 관리
<details>
<summary>지속적인 배포(CD)</summary>
<div markdown="1">

   * 지속적인 배포의 필요성
     * 기능이 추가될 때마다 배포해야하는 불편함이 있어 배포 자동화의 필요성 인식
   * 대안
   
     |Jenkins|Github Actions|
     |------|------|
     |무료|일정 사용량 이상 시 유료|
     |작업 또는 작업이 동기화되어 제품을 시장에 배포하는데 더 많은 시간이 소요|클라우드가 있으므로, 별도 설치 필요 없음|
     |계정 및 트리거를 기반으로하며 Github 이벤트를 준수하지 않는 빌드를 중심으로 함|모든 Github 이벤트에 대한 작업을 제공하고 다양한 언어와 프레임워크를 지원|
     |전 세계 많은 사람들이 이용해 문서가 다양|젠킨스에 비해 문서가 없음|
     |캐싱 메커니즘을 지원하기 위해 플러그인 사용 가능|캐싱이 필요한 경우 자체 캐싱 메커니즘을 작성해야함|
     
   * 선택
     * GitHub Actions 편의성 및 접근성이 좋아서 의견 수렴 후 선택.
	
</div>
</details>

<details>
<summary>Git</summary>
<div markdown="1">
<br/>

   * Git Commit 메시지 컨벤션의 필요성
     * commit된 코드가 어떤 내용을 작성 했는 지 파악하려면 commit을 확인해야 한다.
     * 프로젝트 진행 중에는 수 많은 코드가 commit되기 때문에 일일이 내용을 확인하기 힘들기 때문에 
메시지 컨벤션을 통해서 제목이나 description을 통해서 commit의 정보를 전달한다.
   * Git Commit 메시지 컨벤션 전략
   
   ```
   Feat : 내가 작업한 기능 구현 완료
   Fix : 버그 수정 및 기능 수정완료
   Build : 빌드 수정 완료
   Chore : 자잘한 수정 완료
   Ci : Ci 설정 수정완료
   Docs : 문서 수정에 대한 커밋
   Style : 코드 스타일 혹은 포맷 등에 관한 커밋
   Refactor : 코드 리팩토링에 대한 커밋
   Test : 테스트 코드 수정에 대한 커밋
   ```
   
 👇🏻더 자세한 내용이 알고싶다면?👇🏻<br/>
    &nbsp; 🚥 &nbsp; [Git](노션링크)
</div>
</details>


## 설계
<details>
<summary>📝 API 설계</summary>
<div markdown="1">
<br/>
	
![tacbaeticsErd](https://user-images.githubusercontent.com/113872320/206990749-c1b7e39b-0320-403d-939d-0a97fd815d24.png)
	
</div>
</details>

<details>
<summary>📘 DB 설계</summary>
<div markdown="1">
<br/>
	
![tacbaeticsErd](https://user-images.githubusercontent.com/113872320/206990749-c1b7e39b-0320-403d-939d-0a97fd815d24.png)
	
</div>
</details>

## 팀원

|이름|포지션|분담|@ Email|Github|
|------|------|------|------|------|
|권순한|BackEnd|프로젝트 매니징<br/> 시나리오 설계<br/>데이터 생성<br/>업데이트 기능|soonable@gmail.com|https://github.com/soonhankwon|
|이재선|BackEnd|검색(쿼리 최적화) <br/>택배기사용 조회기능<br/>택배기사용 업데이트<br/>부하 테스트|jason1208@naver.com|https://github.com/|
|최규범|BackEnd|회원가입<br/>로그인<br/>관리자용 조회기능<br/>관리자용 업데이트<br/>부하 테스트|rbqjachl95@google.com|https://github.com/|