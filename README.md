# 📰 통합 피드 서비스 `다모아`

## 목차

- [개요](#개요)
- [개발 환경](#skils)
- [Running Tests](#running-tests)
- [API Reference](#api-reference)
- [프로젝트 진행 및 이슈 관리](#프로젝트-진행-및-이슈-관리)
- [구현과정(설계 및 의도)](#구현과정설계-및-의도)
- [TIL 및 회고](#til-및-회고)
- [Authors](#authors)

## 개요
사용자는 인스타그램, 스레드, 페이스북, 트위터 등 SNS 마다 게시물을 확인해야 하는 번거로움이 있습니다.

`다모아`는 하나의 화면으로 사용자 해시태그가 포함된 모든 게시물과 통계를 한 곳에서 확인할 수 있도록 기획된 **통합 피드 서비스**입니다.

## 개발 환경
<img src="https://img.shields.io/badge/Language-%23121011?style=for-the-badge"> <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white"><img src="https://img.shields.io/badge/17-515151?style=for-the-badge">

<img src="https://img.shields.io/badge/Framework-%23121011?style=for-the-badge"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/3.3.2-515151?style=for-the-badge"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">

<img src="https://img.shields.io/badge/Database-%23121011?style=for-the-badge"> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"> <img src="https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=&logoColor=white"> <img src="https://img.shields.io/badge/querydsl-6DB33F?style=for-the-badge&logo=&logoColor=white">

<img src="https://img.shields.io/badge/Build-%23121011?style=for-the-badge"> <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"><img src="https://img.shields.io/badge/8.8-515151?style=for-the-badge">

<img src="https://img.shields.io/badge/Deployment-%23121011?style=for-the-badge"> <img src="https://img.shields.io/badge/aws%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white"> <img src="https://img.shields.io/badge/flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white"> <img src="https://img.shields.io/badge/aws rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">

<img src="https://img.shields.io/badge/version control-%23121011?style=for-the-badge"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

## Running Tests
여기에 Running Tests 내용을 작성하세요...


## API Reference

<details>
<summary>사용자 회원가입 API</summary>

> 사용자는 계정, 이메일, 비밀번호로 회원가입이 가능합니다.

``` java
POST /api/v1/members/register
```

#### Request
```json
{
  "account": "tenten",
  "email": "tenten@gmail.com",
  "password": "password12!"
}
```

| Field | Type | Description |
| --- | --- | --- |
| `account` | `string` | 계정 |
| `email` | `string` | 이메일 |
| `password` | `string` | 비밀번호 |

#### Response
**1. 201 Created**
``` json
HTTP/1.1 201
Content-Type: application/json

{
  "memberId": "7c7d11e1-ae2e-4dfb-a6bb-b106bc007136"
}
```

| Field | Type | Description |
| --- | --- | --- |
| `memberId` | `string` | UUID |

**2. 400 Bad Request**
```json
HTTP/1.1 400
Content-Type: application/json

{
  "message": "잘못된 요청입니다. 입력값을 확인하고 다시 시도해주세요.",
  "detail": [
    "계정은 1~50자만 가능합니다.",
    "계정은 필수 입력입니다.",
    "이메일은 1~100자만 가능합니다.",
    "이메일은 필수 입력입니다.",
    "올바른 이메일 형식을 입력해 주세요.",
    "비밀번호는 필수 입력입니다.",
    "비밀번호는 1) 최소 10자 이상, 2) 숫자/문자/특수문자(!@#$%^&*) 중 2가지 이상 포함, 3) 3회 이상 연속되는 문자를 사용할 수 없습니다."
  ]
}
```

**3. 409 Conflict**
```json
HTTP/1.1 409
Content-Type: application/json

{
  "message": "이미 사용중인 계정입니다."
}
```
</details>

<details>
<summary>사용자 로그인 API</summary>

> 사용자는 계정, 비밀번호로 로그인이 가능하고, 로그인이 성공하면 JWT가 발급됩니다.

``` java
POST /api/v1/members/login
```

#### Request
```json
{
  "account": "tenten",
  "password": "password12!"
}
```

| Field | Type | Description |
| --- | --- | --- |
| `account` | `string` | 계정 |
| `password` | `string` | 비밀번호 |

#### Response
**1. 200 Ok**
```json
HTTP/1.1 200
Content-Type: application/json

{
  "account": "tenten",
  "accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZW50ZW4yIiwicm9sZSI6IlBSRV9NRU1CRVIiLCJhY2NvdW50IjoidGVudGVuMiIsImlhdCI6MTcyNDU5NTI3NiwiZXhwIjoxNzI4MTk1Mjc2fQ.kHK0gWjmKkJSjJCWCnoSmP3pGnT5O9OWOf74iQ-yupl7TzenIEXJvzu00UT0dxYq",
  "refreshToken": "eyJhbGciOiJIUzM4NCJ9.eyJleHAiOjI5MzQxOTUyNzZ9.mzsurji239LQi8mVYlW_f6Flld9zt36Sh5X9J2RamlymONrRjek13inUabyB4KO8"
}
```

| Field | Type | Description |
| --- | --- | --- |
| `account` | `string` | 계정 |
| `accessToken` | `string` | JWT 액세스 토큰 |
| `refreshToken` | `string` | JWT 리프레시 토큰 |

**2. 400 Bad Request**
```json
HTTP/1.1 400
Content-Type: application/json

{
  "message": "잘못된 요청입니다. 입력값을 확인하고 다시 시도해주세요.",
  "detail": [
    "계정은 필수 입력입니다.",
    "비밀번호는 필수 입력입니다."
  ]
}
```

**3. 401 Unauthorized**
```json
HTTP/1.1 401
Content-Type: application/json

{
  "message": "존재하지 않는 계정입니다."
}
```
```json
HTTP/1.1 401
Content-Type: application/json

{
  "message": "비밀번호를 잘못 입력했습니다."
}
```

**4. 403 Forbidden**
```json
HTTP/1.1 403
Content-Type: application/json

{
  "message": "서비스 회원이 아닙니다. 이메일 인증을 먼저 해주세요."
}
```
</details>

<details>
<summary>회원 인증 API</summary>

> 사용자는 발송된 인증코드 인증 절차를 통해 회원가입을 완료할 수 있다

```java
POST /api/v1/members/verify
```

#### Request
| Parameter   | Type     | Default Value           | Description                |
| :--------   | :------- | :-------------------    | :------------------------- |
| `account`   | `String` | 필수 값                  | 계정 중복 불가 |
| `email`   | `String` | 필수 값                  | email 형식 일치 |
| `password`   | `String` | 필수 값                  | 1. 최소 10자 이상, 2. 숫자/문자/특수문자(!@#$%^&*) 중 2가지 이상 포함, 3) 3회 이상 연속되는 문자를 사용 불가 |
| `code`   | `String` | 필수 값                  | 6자리 랜덤 코드 |

#### Response

200 OK
```json
HTTP/1.1 201
Content-Type: application/json

{
    "message": "가입되었습니다."
}
```
- 400 Bad Request
```json
HTTP/1.1 400
Content-Type: application/json

{
    "message": "인증 코드가 일치하지 않습니다."
}
```
- 404 Not Found
```json
HTTP/1.1 404
Content-Type: application/json

{
    "message": "사용자 정보를 찾을 수 없습니다."
}
```
```json
HTTP/1.1 404
Content-Type: application/json

{
    "message": "발급된 인증 코드가 존재하지 않습니다."
}
```
</details>

<details>
<summary>게시물 목록 API</summary>

> 유저는 해시태그, 타입, 검색어를 활용해 게시물 목록을 조회할 수 있습니다.
- 게시물 목록은 요청한 기준에 따라 정렬된 형태로 반환합니다.
- 페이지 당 개수 및 원하는 페이지를 지정할 수 있습니다.

#### Request

``` java
GET /api/v1/posts
```

| Parameter    | Type     | Default Value           | Description                                                                                   |
| :----------- | :------- | :---------------------- | :-------------------------------------------------------------------------------------------- |
| `hashtag`    | `string` | 사용자 account          | 1건의 해시태그로, 정확히 일치하는 값만 검색함.                                                |
| `type`       | `string` | X (미입력 시 모든 타입) | 게시물의 유형으로,SNS 플랫폼을 의미함. 'INSTAGRAM', 'TWITTER','Threads', 'FACEBOOK' 사용 가능 |
| `order-by`   | `string` | `created_at`            | 정렬 기준. `created_at`,`updated_at`,`like_count`,`share_count`,`view_count` 사용 가능        |
| `order`      | `string` | `desc`                  | 정렬 순서. `asc`, `desc` 사용 가능                                                            |
| `search-by`  | `string` | `title,content`         | 검색 기준. `title`, `content`, `title,content` 사용 가능                                      |
| `search`     | `string` | X                       | `search_by`에서 검색할 키워드로 유저가 입력한다. 해당 문자가 포함된 게시글 검색               |
| `page-count` | `int`    | 10                      | 페이지당 개수                                                                                 |
| `page`       | `int`    | 0                       | 조회하려는 페이지 지정                                                                        |

#### Response
``` json
HTTP/1.1 200
Content-Type: application/json

[{
    "id": 11,
    "contentId": "b8aa5ee0-3c40-40db-9a47-83ac00c1003b",
    "type": "FACEBOOK",
    "title": "가족과 함께한 피크닉",
    "content": "주말에 가족들과 공원에서 즐거운 시간",
    "viewCount": 40,
    "likeCount": 25,
    "shareCount": 8
}, 
...
{
    "id": 14,
    "contentId": "091fa89a-2b1d-4153-b861-90e60c7737fb",
    "type": "FACEBOOK",
    "title": "주말 드라이브",
    "content": "이번 주말엔 산으로 드라이브 다녀왔습",
    "viewCount": 63,
    "likeCount": 35,
    "shareCount": 12
}]
```

| Parameter    | Type     | Description    |
| :----------- | :------- | :------------- |
| `id`         | `string` | 게시물 id      |
| `contentId`  | `string` | SNS 고유식별값 |
| `title`      | `string` | 제목           |
| `content`    | `string` | 내용 (20자 제한) |
| `viewCount`  | `string` | 조회수         |
| `likeCount`  | `string` | 좋아요수       |
| `shareCount` | `string` | 공유수         |

</details>

<details>
<summary>게시물 상세 API</summary>

> 사용자는 게시물 id로 게시물의 상세 정보를 조회할 수 있습니다.

```java
GET /api/v1/posts/{postId}/detail
```

#### request
| Parameter   | Type     | Default Value           | Description                |
| :--------   | :------- | :-------------------    | :------------------------- |
| `postId` | `Number` | 필수 값 | 게시물 id |

#### response
- 200 OK
```json
HTTP/1.1 200
Content-Type: application/json
        
{
    "createdAt": "2024-08-07T02:15:00",
    "updatedAt": "2024-08-26T19:56:48.5413289",
    "id": 2,
    "contentId": "69d99bba-6231-11ef-b22e-027a06169877",
    "type": "TWITTER",
    "title": "새벽 감성 🌙",
    "content": "잠이 안 와서 트윗 남겨봐요. 여러분도 좋은 밤 되세요.",
    "viewCount": 7,
    "likeCount": 3,
    "shareCount": 4
}
```
</details>

<details>
<summary>게시글 좋아요 API</summary>

> 사용자는 게시글 목록에 특정 게시글에 좋아요를 누를 수 있습니다.

```java
POST /api/v1/posts/{postId}
```

#### Request
| Parameter   | Type     | Default Value           | Description                |
| :--------   | :------- | :-------------------    | :------------------------- |
| `postId`   | `Number` | 필수 값                  | 특정 글의 식별값 |

#### Response
- 200 OK
```json
HTTP/1.1 200
Content-Type: application/json

{
    "message": "좋아요 처리되었습니다."
}
```

- 404 Not Found
```json
HTTP/1.1 404
Content-Type: application/json

{
    "message": "게시글이 존재하지 않습니다."
}
```
</details>

<details>
<summary>게시물 공유 API</summary>

> 사용자는 게시물을 다른 SNS로 공유할 수 있습니다.

```java
PATCH /api/v1/posts/{postId}/share
```

#### Request
| Parameter   | Type     | Default Value           | Description                |
| :--------   | :------- | :-------------------    | :------------------------- |
| `postId` | `Number` | 필수 값 | 게시물 id |

#### Response
- 200 OK
```json
HTTP/1.1 200
Content-Type: application/json

{
    "message" : "게시물 공유에 성공했습니다."
}
```

- 400 Bad Request
```json
HTTP/1.1 400
Content-Type: application/json

{
    "message" : "잘못된 요청입니다. 입력값을 확인하고 다시 시도해주세요."
}
```
</details>

<details>
<summary>해시태그 통계 API</summary>

> 유저는 본인 계정명 또는 특정 해시태그 일자별, 시간별 게시물 갯수 통계를 확인할 수 있습니다.

``` java
GET /api/v1/hashtags/stat?
hashtag=tenten&
unit=DATE&
metric=COUNT&
start=2024-08-01T00:00:00&
end=2024-08-31T23:59:59
```

#### Request

| Parameter   | Type     | Default Value           | Description                |
| :--------   | :------- | :-------------------    | :------------------------- |
| `hashtag`   | `string` | 필수 값                  | 1건의 해시태그로, 정확히 일치하는 값만 검색 |
| `unit`      | `string` | 필수 값                  | `DATE`, `HOUR` 사용 가능 |
| `start`     | `date`   | 필수 값                  | `yyyy-MM-ddTHH:mm:ss`과 같은 날짜 형식이며, 조회 기준 시작일을 의미 |
| `end`       | `date`   | 필수 값                  | `yyyy-MM-ddTHH:mm:ss`과 같은 날짜 형식이며, 조회 기준 종료일을 의미 |
| `metric`    | `string` | 필수 값                 | `COUNT`, `VIEW_COUNT`, `LIKE_COUNT`, `SHARE_COUNT` 사용 가능 |

#### Response

``` json
HTTP/1.1 200
Content-Type: application/json

[{
    "date": "2024-08-02T00:00:00",
    "value": 48
},
{
    "date": "2024-08-04T00:00:00",
    "value": 60
},
...
{
    "date": "2024-08-30T00:00:00",
    "value": 2064
},
{
    "date": "2024-08-31T00:00:00",
    "value": 3552
}]
```

| Parameter   | Type     | Description                |
| :--------   | :------- | :------------------------- |
| `date`   | `string` |  통계에 해당하는 일자 |
| `value`      | `number` | 통계 값        |

</details>

## 프로젝트 진행 및 이슈 관리

| 날짜 | 제목 | 주요 내용 |
| --- | --- | --- |
| **2024/08/20** | **정규수업 회의** | [팀 규칙 및 컨벤션 논의 & 구현 기능 분배](https://www.notion.so/sebel/7ab88d2b8b174672b6f50e290765be3c?pvs=4) |
| **2024/08/21** | **데일리 스크럼** | [프로젝트 초기 세팅 및 요구사항 명세](https://www.notion.so/sebel/fff33e3d6c1e80548e26d315a8b58b33?pvs=4) |
| **2024/08/23** | **데일리 스크럼** | [ERD 작성, 명세서 공유](https://www.notion.so/sebel/ERD-698074ffa75e4d7697d8fe98bc10e32e?pvs=4) |
| **2024/08/26** | **데일리 스크럼** | [과제1 최종 점검](https://www.notion.so/sebel/1-db40193fa362444b8b7e34a69a59beed?pvs=4) |

## 구현과정
### 
<details>
<summary>ERD 모델링</summary>

![damoa_erd](https://github.com/user-attachments/assets/3787647c-80b5-42a4-bf4d-a1a10ac6ce87)
- post(게시물)
    - 외부 SNS의 게시물 데이터
    - 게시물을 외부 SNS에서 가져와야 하지만, DB에 값이 존재한다고 가정하고 구현
- hashtag(해시태그)
    - 게시물에 달린 복수의 해시태그 데이터
    - 게시물(post)와 해시태그(hashtag)의 관계는 다대다이지만, 중간 테이블을 거치지 않고 일대다 관계로 단순화하여 성능 최적화
- interaction_history(상호작용 이력)
    - 상호작용 이력 통계에 필요한 일자에 따른 조회, 좋아요, 공유를 이력으로 관리
    - 조회수, 좋아요 수, 공유 수 등의 값을 효율적으로 조회하기 위해, 상호작용 이력 외에 게시물 테이블에도 해당 통계 값을 속성으로 추가 관리
- member (사용자)
    - 서비스에 등록한 사용자
    - 검증된 사용자와 검증되지 않은 사용자를 구별하기 위해 role 속성으로 판단 (`PER_MEMBER`, `MEMBER`)
- verification_code (인증 번호)
    - 사용자 이메일 검증에 사용되는 인증 번호

</details>
<details>
<summary>아키텍처 설계</summary>

- 도메인 주도 설계(DDD) 기반의 계층형 아키텍처
    - 프로젝트의 규모가 크지 않기 때문에, 복잡한 아키텍처보다 간단하면서도 효과적인 계층형 아키텍처를 선택
    - 팀원 간 역할 분담이 용이하고, 도메인 로직의 명확한 구현을 위해 DDD 기반의 도메인 패키지 분리

</details>
<details>
<summary>디렉터리 구조</summary>

``` plain
├── 📂 server
│   ├── 📂 common
│   │   ├── 📂 config
│   │   ├── 📂 exception
│   │   ├── 📂 model
│   │   └── 📂 util
│   ├── 📂 member
│   │   ├── 📂 controller
│   │   ├── 📂 domain
│   │   ├── 📂 repository
│   │   └── 📂 service
│   ├── 📂 hashtag
│   ├── 📂 interaction
│   ├── 📂 post
│   ├── 📂 stat
│   └── 📂 verifiaction
```

</details>

## TIL 및 회고
<details>
<summary>JPA 일대다 관계 단방향 매핑 시 양방향 참조가 불가능한 문제</summary>

&nbsp;

테이블 사이에는 양방향, 단방향 개념이 없지만, JPA에서는 양쪽 테이블에서 annotation을 설정해 주어야 테이블 간의 관계를 맺어줄 수 있다.

단방향일 경우 한쪽에서만 관계를 참조해 조회할 수 있으므로, 양방향으로 조회를 해야 한다면 양 테이블에 관계 매핑을 해주어야 한다.

기존의 Post와 Hashtag는 1:N 관계로, Hashtag Entity에 `@ManyToOne` 단방향 관계는 설정되어 있었으나 Post에는 설정이 되어있지 않았다.

Post에서 Hashtag를 조회하기 위해 다음 코드를 추가했다.

```java
@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
private List<Hashtag> hashtags;
```

</details>

## Author
|<img src="https://avatars.githubusercontent.com/u/58517873?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/65033360?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/114724461?s=400&v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/83827023?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/148259495?v=4" width="150" height="150"/>|
|:-:|:-:|:-:|:-:|:-:|
|김유경<br/>[@YuGyeong98](https://github.com/YuGyeong98)|배기연<br/>[@GiYeons](https://github.com/GiYeons)|백현경<br/>[@hyunkkkk](https://github.com/hyunkkkk)|이찬미<br/>[@05AM](https://github.com/05AM)|최유림<br/>[@Yuurim98](https://github.com/Yuurim98)
|회원가입<br/> 로그인 <br/> 게시물 조회수 급상승 시 알림|게시물 목록|게시물 상세 <br/>게시물 공유|통계 <br/>인기 해시태그|회원가입 승인 <br/>게시물 좋아요|
