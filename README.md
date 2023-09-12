

# Capstone_Design_Project
- AI 기반의 제주도 교통량 예측 및 관광지 추천 애플리케이션 개발  
  (Jeju Island traffic forecasting and tourist destination recommendation application based on AI)

<br/>

<h1>Team & Dev Code</h1>

- 정민수 : AI Dev / Jupyter / [Github](https://github.com/Min-su-Jeong/Capstone_Design_Project)  
- 안정환 : Frontend / Android Studio / [Github](https://github.com/JungHwanAhn/Jeju)  
- 조시완 : Backend / Spring Boot, H2 / [Github](https://github.com/Singe00/CrtDgn)  

<br/>

<h1>User's Manual</h1>

[사용자 메뉴얼](https://github.com/Min-su-Jeong/Capstone_Design_Project/files/11782152/default.pdf)

<br/>

<h1>Development</h1>

> 버전
  - python : 3.7.13
  - numpy : 1.21.6
  - pandas : 1.3.5
  - scikit-learn : 1.0.2
  - xgboost : 1.6.2
  - lightgbm : 3.3.3
  - joblib : 1.2.0
  - scipy : 1.7.3
  - folium : 0.14.0

<br/>

> 시스템 구성도
  <p align="left">
    <img src="https://github.com/Min-su-Jeong/Capstone_Design_Project/assets/74342121/0e06bb8c-49f0-4a6d-9efd-1090654b1ba7" width="460" height="400">
  </p>

> api 문서 링크
  https://documenter.getpostman.com/view/20733307/2s93sXbZbJ
<br/>

> 주요 기능
  <p align="left">
    <img src="https://github.com/Min-su-Jeong/Capstone_Design_Project/assets/74342121/270dac61-f24d-46e3-baf0-1ca337518df0" width="650" height="350">
  </p>
  <br/>
  <p align="left">
    <img src="https://github.com/Min-su-Jeong/Capstone_Design_Project/assets/74342121/3b971bea-d3c5-4b7d-81ec-41f9b4fd32c6" width="650" height="350">
  </p>

<br/>

> 예측 결과

  | Prediction Model | MAE | Accuracy |
  |:---------:|:---:|:--------:|
  | RandomForest | 3.25 | 91.4% |
  | XGBoost | 3.88 | 88.9% |
  | LightGBM | 4.82 | 84.1% |

<br/>

<h1>Paper</h1>

- M. Jeong, J. Ahn, S. Jo, B. Oh and H. Ahn, "AI-based Jeju Island Traffic Volume Forecasting and Tourist Attraction Recommendation System", Proc. Of Korean Institute of Information Technology Conference, pp.793-796, Jun. 2023.
- [Paper link](https://www.dbpia.co.kr/journal/articleDetail?nodeId=NODE11485609)

<br/>

<h1>History</h1>

#### 1. 프로젝트 주제 아이디어 회의✔
- 회의 날짜/시간 : 2023.03.05(일) / 21:00 ~ 23:00
- 회의 내용 : 프로젝트 주제 아이디어 도출 및 설명
- 회의 결론 : 각자 생각한 프로젝트 주제 아이디어 발표 준비
- 자세한 내용은 repository에 첨부된 『[Meeting Notes/회의록(프로젝트 주제 아이디어).hwp](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Meeting%20Notes)』 파일 참조

> #### 1-1. 프로젝트 주제 아이디어 발표✔
> - 발표 날짜/시간 : 2023.03.07(화) / 18:00 ~ 19:00
> - 발표 내용 : 프로젝트 주제 아이디어 세부 설명 및 피드백
> - 자세한 내용은 repository에 첨부된 『[Presentation/발표자료(프로젝트 주제 아이디어).pptx](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Presentation)』 파일 참조

#### 2. 프로젝트 주제 아이디어 선정 회의✔
- 회의 날짜/시간 : 2023.03.12(일) / 21:00 ~ 23:00
- 회의 내용 : 프로젝트 주제 아이디어 선정 및 역할 분담, 주제에 관한 프로젝트 계획
- 회의 결론 : 프로젝트 주제 아이디어 선정 및 세부 개발 계획 작성
- 자세한 내용은 repository에 첨부된 『[Meeting Notes/회의록(프로젝트 주제 아이디어 선정).hwp](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Meeting%20Notes)』 파일 참조

> #### 2-1. 프로젝트 주제 아이디어 선정 발표✔
> - 발표 날짜/시간 : 2023.03.14(화) / 18:00 ~ 19:00
> - 발표 내용 : 프로젝트 주제 아이디어 선정 발표(선정 배경, 개발 목적, 개발 목표, 주요 개발 내용, 최종 결과, 활용 계획, 개발 환경, 개발 일정, 역할 분담)
> - 자세한 내용은 repository에 첨부된 『[Presentation/발표자료(프로젝트 주제 아이디어 선정).pptx](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Presentation)』 파일 참조

#### 3. 과제 제안서 작성 회의✔
- 회의 날짜/시간 : 2023.03.26(일) / 20:00 ~ 23:00
- 회의 내용 : 과제 제안서 작성 역할 분담, 보고서 피드백 및 수정, 추후 설계 내용 간략 토의 등
- 자세한 내용은 repository에 첨부된 『[Meeting Notes/회의록(과제 제안서 작성).hwp](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Meeting%20Notes)』 파일 참조

> #### 3-1. 프로젝트 과제 제안서 제출 및 피드백✔
> - 미팅 날짜/시간 : 2023.03.28(화) / 19:30 ~ 20:00
> - 미팅 내용 : 과제 제안서 첨삭, 연구개발의 활용 방안 검토, 추후 상세 설계에 대한 논의 등
> - 자세한 내용은 repository에 첨부된 『[Report/보고서(프로젝트 과제 제안서).hwp](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Report)』 파일 참조

#### 4. 시스템 구조 및 소프트웨어 설계 회의✔
- 회의 날짜/시간 : 2023.04.02(일) / 21:00 ~ 23:00
- 회의 내용 : 시스템 구조, 소프트웨어 설계, 설계 방안 아이디어 모색 및 추후 계획 토의 등
- 자세한 내용은 repository에 첨부된 『[Meeting Notes/회의록(시스템 구조 및 소프트웨어 설계).hwp](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Meeting%20Notes)』 파일 참조

> #### 4-1. 상세 설계 회의✔
> - 회의 날짜/시간 : 2023.04.09(일) / 20:00 ~ 23:00
> - 회의 내용 : 상세 설계 내용(프레임워크 구성, 시스템 구조도, 소프트웨어 설계) 구체화, PPT 작성 등
> - 자세한 내용은 repository에 첨부된 『[Meeting Notes/회의록(상세 설계).hwp](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Meeting%20Notes)』 파일 참조

> #### 4-2. 상세 설계 발표✔
> - 발표 날짜/시간 : 2023.04.11(화) / 19:00 ~ 20:00
> - 발표 내용 : 주제 안내, 주요 개발 내용, 전체 시스템 구조, 디자인, Use Case, 변경사항, 개발일정 등
> - 자세한 내용은 repository에 첨부된 『[Presentation/발표자료(상세 설계).pptx](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Presentation)』 파일 참조

#### 5. 중간 개발 보고1 회의✔
- 회의 날짜/시간 : 2023.05.03(수) / 20:00 ~ 22:00
- 회의 내용 : 개발 진행 상황, 개발 상황 자료 및 내용 공유 등 
- 자세한 내용은 repository에 첨부된 『[Meeting Notes/회의록(중간 개발 보고1).hwp](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Meeting%20Notes)』 파일 참조

> #### 5-1. 중간 개발 보고2 회의✔
> - 회의 날짜/시간 : 2023.05.08(월) / 21:00 ~ 23:00
> - 회의 내용 : 현재 개발 진행 상황 보고를 위한 준비, 데모 준비 등
> - 자세한 내용은 repository에 첨부된 『[Meeting Notes/회의록(중간 개발 보고2).hwp](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Meeting%20Notes)』 파일 참조

> #### 5-2. 중간 개발 보고 미팅 및 피드백✔
> - 미팅 날짜/시간 : 2023.05.11(목) / 16:00 ~ 17:00
> - 미팅 내용 : 중간 개발 결과 발표 및 피드백, 향후 개발 계획 등
> - 자세한 내용은 repository에 첨부된 『[Presentation/발표자료(중간 개발 보고).pptx](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Presentation)』 파일 참조

> #### 5-3. 중간 결과 발표
> - 발표 날짜/시간 : 2023.05.16(화) / 18:00 ~ 19:00
> - 발표 내용 : 프로젝트 진도 상황 발표
> - 자세한 내용은 repository에 첨부된 『[Presentation/발표자료(중간 발표).pptx](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Presentation)』 파일 참조

#### 6. 최종 개발 보고1 회의✔
- 회의 날짜/시간 : 2023.05.25(목) / 22:00 ~ 23:00
- 회의 내용 : 개발 진행 상황 검토, 기술 스택별 개발 계획 수립 등 
- 자세한 내용은 repository에 첨부된 『[Meeting Notes/회의록(최종 개발 보고1).hwp](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Meeting%20Notes)』 파일 참조

> #### 6-1. 최종 개발 보고2 회의✔
> - 회의 날짜/시간 : 2023.06.10(토) / 21:00 ~ 23:00
> - 회의 내용 : 개발 진행 상황 검토, 미팅 준비, 최종자료 정리 등
> - 자세한 내용은 repository에 첨부된 『[Meeting Notes/회의록(최종 개발 보고2).hwp](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Meeting%20Notes)』 파일 참조

> #### 6-2. 최종 개발 보고 미팅 및 피드백✔
> - 미팅 날짜/시간 : 2023.06.13(화) / 15:30 ~ 16:00
> - 미팅 내용 : 개발 진행 상황 보고 및 기능 관련 피드백, 버그 수정 등

#### 7. 최종 보고서 제출 및 최종 발표✔
- 발표 날짜/시간 : 2023.06.20(화) / 18:00 ~ 18:30
- 발표 내용 : 개발목표 및 내용, 개발일정 및 역할분담, 수정사항, 최종 시연, 활용방안, 후기 등
- 자세한 내용은 repository에 첨부된 『[Presentation/발표자료(최종 발표).pptx](https://github.com/Min-su-Jeong/Capstone_Design_Project/tree/main/Presentation)』 파일 참조

