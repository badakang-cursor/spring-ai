# 개요

# 1강 프로젝트세팅

URL : https://www.youtube.com/watch?v=QyRKXYd4xC8&list=PL_GeFwqaAwWFBN0G6Ml3cAtH40fAnRDzU&index=22

📚 목차
1. 🌟 스프링 AI와 MCP 소개 [00:00:12]  
2. 🔑 MCP(Model Concept Protocol)란? [00:00:55]  
3. 🛠 개발 환경 세팅과 API 키 발급 방법 [00:02:44]  
4. 🤖 AI 엔지니어링 역할과 주요 용어 정리 [00:04:03]  
5. 📖 스프링 AI 주요 기능과 구조 [00:08:05]  
6. 🧱 스프링 AI 프로젝트 생성 및 기본 코드 작성 [00:15:31]  
7. 🚀 챗 모델 실습과 실행 방법 [00:20:42] 


### Spring AI 강의

**1. MCP(Model Concept Protocol)**
  - Model (모델)
    - Spring AI의 ChatClient가 모델을 관리합니다.
      - Spring AI에서 모델은 ChatClient 인터페이스를 통해 추상화됩니다. 여러분은 코드에서 직접 GPT-4나 Gemini API를 호출할 필요가 없습니다. 대신 ChatClient를 사용하면 됩니다.

     - 핵심:
       - 추상화: ChatClient는 다양한 LLM(GPT-4, Gemini 등)의 복잡한 API를 단일 인터페이스로 통일합니다.
       - 유연성: application.properties 파일에 설정만 변경하면 코드 수정 없이 다른 모델로 쉽게 전환할 수 있습니다.
       - 코드 예시:
          ```java
          // Spring AI의 ChatClient를 의존성 주입받아 사용
          private final ChatClient chatClient;
          ```
  - Concept (개념)
      - Spring AI의 PromptTemplate와 Prompt가 개념을 구현합니다.
        - 프롬프트 엔지니어링을 통해 모델에게 전달할 개념(Concept)을 설계하는 것은 매우 중요합니다. Spring AI는 이를 위해 PromptTemplate과 Prompt 객체를 제공합니다.

      - 핵심:
        - 템플릿화: PromptTemplate을 사용하면 역할(persona), 지시사항, 변수 등을 포함하는 프롬프트 형식을 미리 정의할 수 있습니다.
        - 동적 바인딩: 템플릿에 정의된 변수({name}, {topic} 등)에 데이터를 동적으로 바인딩하여 프롬프트를 완성합니다.
        - 코드 예시:
          ```java
          // {topic} 부분에 동적으로 값이 채워지는 PromptTemplate
          PromptTemplate promptTemplate = new PromptTemplate("""
                                                            너는 유능한 AI 어시스턴트야.
                                                            {topic}에 대해 100자 이내로 간략하게 설명해줘.
                                                            """);
          // "topic" 변수에 "Spring Al"를 바인딩하여 Prompt 생성
          Prompt prompt = promptTemplate.create(Map.of("topic", "Spring Al"));
          ```

  - Protocol (프로토콜)
    - Spring AI가 프로토콜 통신을 대신 처리합니다.
      - Spring AI는 내부적으로 HTTP 클라이언트(예: RestTemplate 또는 WebClient)를 사용하여 각 모델 공급자의 API 프로토콜에 맞춰 통신합니다. 개발자는 이 과정을 신경 쓸 필요가 없습니다.

    - 핵심:
      - 자동화: Spring AI는 프롬프트 객체를 해당 모델 API가 요구하는 JSON 형식으로 자동 변환하고, 응답 데이터를 다시 Java 객체로 변환합니다.
      - 안전성: API 키 관리, 인증, 통신 실패 처리 등 프로토콜과 관련된 복잡한 작업들을 프레임워크가 대신 처리해 줍니다.
      - 코드 예시:
          ```java
        // chatClient를 호출하면, Spring AI가 프로토콜에 맞춰 통신을 시작
        String response = chatClient.call(prompt).getResult().getOutput().getText();
          ```

**2. 실습 환경 준비**
  - JDK 17, Eclipse 2024-12-R 버전 설치
  - Eclipse 2024-12-R 버전 설치
    - Help > Eclipse Marketplace ... > Spring tools (aka Spring Tool Suite) 3.31.0.REALEASE
    - Lombok 설치
  - 생성형 AI API 키 발급
    - Google Al Studio API 키
      - https://aistudio.google.com/ > 로그인 > Get API key > API Keys
    - Open AI API 7|
      - https://platform.openai.com/ > 로그인 > Settings(冷) > API Keys
      - springai : 에스케이하이픈피알오제이하이픈KRVacui7uHzhKmnFiw6ZsNlnqWcopwAm32ppuhHtM7j6JNfMAW4dQ8kwSIPIyMM_q8L__ZfCYhT3BlbkFJuO9GH_3eS2gho1ix4nyWIzA26dv6I32KO7vkNOJQgBb6WMIxT2QqLQXf0yORGKuDnMrasQW2QA
    - Anthropic의 Claude API 키
      - https://www.anthropic.com/api > Start building > 01 > Get API Key

**3. AI 엔지니어링이란?**
  - AI 엔지니어링의 구분

    ![AI 엔지니어링](https://github.com/badakang-cursor/spring-ai/blob/main/references/1-ai_engineering.png?raw=true "AI 엔지니어링")

    - 프롬프트 엔지니어링
      - 사전에 정확한 요구 사항을 제시
    - 검색 증강 생성(RAG)
      - 검색해서 더 정확한 결과를 얻는 검색증강생성(RAG)과 벡터 데이터베이스 
      - 모델의 성능을 직접 조정하는 파인튜닝 등이 포함
    - 파운데이션 모델(FM)
      - 대규모 데이터를 학습해 다양한 작업에 적용할 수 있는 인공지능(AI) 모델이다.
      - Open AI의 GPT 시리즈와 Google의 제미나이(Gemini) 등,
      - 이 모델들은 텍스트 생성, 이미지 분석, 코드 작성 등 여러 분야에서 활용

**4. AI 기본 용어 및 설명**
  - AI 모델(Models)이란?
    - 정보를 처리 하고 생성하도록 설계된 알고리즘
    - 학습을 통한 예측이 가능한 유형의 집합

  - 프롬프트(Prompts)
    - 언어 기반의 입력 기초
    - 역할에 따라서 모델의 역할은 답변자인 "시스템 역할"을 입력(질문)인 "사용자 역할"로 구분
    - 프롬프트 템플릿(Prompt Templates)
      - 요청의 컨텍스트(시스템 역할)을 설정하고 입력에 맞는(사용자 역할)의 입력을 위한 구조
      - 예> Tell me a {역할} joke about {입력}.
        - Tell me a {프로그래머} joke about {커피}.
        - 왜 프로그래머는 커피 없이는 일 못 할까?
        - 왜긴 왜야 ... 컴파일보다 카페인이 더 빨라서지!

  - 임베딩(Embeddings)
    - 텍스트를 숫자 배열이나 벡터(크기와 방향을 함께 갖는 양)로 변환하여 AI 모델이 언어 데이터를 처리하고 해석 할 수 있게 한다.
    - 비슷한 주제에 대한 문장은 다차원 공간에서 더 가깝게 배치 된다.
    - 이를 통해서 텍스트 분류, 의미론적 검색, 추천 알고리즘과 같은 작업에 사용

  - LLM(Large Language Model)과 LMM(Large MultiModal Model)
    - LLM은 방대한 양의 데이터를 학습하여 다양한 종류의 텍스트를 생성
    - LLM은 텍스트 데이터 이외에도 비디오, 오디오, 이미지 등 여러가지 유형의 데이터를 통합적으로 처리
  
  - 토큰(Tokes)
    - AI 모델이 작동하는 기본 구성 요소
    - 텍스트를 작은 조각으로 나눈 기계가 처리 가능한 단위, 나둬진 토큰을 백터(숫자) 로 변경하여 숫자을 통해서 딥러닝 모델이 계산 하고 예측한다
    - 입력된 단어를 토큰으로 변환하며 출력시 토큰을 다시 단어로 변환
    - 토큰이 많아지면 비용이 크게 증가 한다, ChatGPT3에는 4K 토큰 제한이 있는 반면 GPT4는 8K, 16K, 32K와 같은 다양한 옵션을 제공
  
**5. Al 모델에 학습되지 않은 정보의 적용 방법**

모델에서 학습하지 않은 데이터를 적용하기 위해 AI 모델을 사용자가 커스터마이징할 수 있는 방법

  - 파인 튜닝(fine Tuning)
    - 기계 학습 기술로서 모델을 조정하고 내부 가중치를 변경하는 작업

  - 프롬프트 스터핑(Prompt Stuffing)
    - 모델에 제공된 프롬프트 내에 데이터를 삽입(Embedding) 하는 방법
    - Spring AI에서는 RAG(Retrieval Argumented Generation) 기술을 사용하여 구현
  - 툴(Tools) 호출
    - 언어 모델을 외부 시스템의 API에 연결하는 사용자 정의 함수를 사용할 수 있다

**6. Spring Al 란?**

[(https://docs.spring.io/spring-ai/reference/index.html)](https://docs.spring.io/spring-ai/reference/index.html)

  - Spring AI는 인공지능 기능을 간단하게 통합할 수 있도록 도와주는 Spring 기반 프레임워크입니다.
  - Python 생태계의 LangChain, Llamalndex 등에서 영감을 받았지만, 직접 포팅한 것은 아니며 Java 생태계에 맞게 설계되었습니다.
  - AI 모델 <-> 기업 데이터 & API 간의 통합 문제 해결


**7. Spring Al 주요 특징**

  - AI 모델 제공자에 관계없이 통합된 API 제공
     - OpenAl, Anthropic, Google, Microsoft, Amazon, Ollama 등 호환
  - 지원 모델 유형
     - Chat Completion (채팅 생성), Embedding (임베딩 벡터), Text to Image (이미지 생성), Audio Transcription (음성 → 텍스트), Text to Speech (텍스트 → 음성), Moderation (유해 콘텐츠 필터링), Structured Output (AI 결과를 POJO로 매핑)
  - 벡터 데이터베이스 연동
  - Function Calling (도구 호출 기능)
     - AI가 실시간으로 클라이언트의 기능을 요청하고 실행 가능
  - 관측 가능성 (Observability)
  - 문서 데이터 수집 및 전처리 (ETL) 프레임워크 지원
  - AI 평가 도구 제공
  - Spring Boot와의 통합
  - ChatClient API
  - Advisors API
  - 기억 기능 및 RAG 지원

**8. Spring Al 주요 기능**

  - 채팅, 텍스트-이미지, 임베딩 모델을 위한 AI 제공자들을 위한 포터블한 API 지원, 동기식 및 스트리밍 API 옵션이 모두 지원되며 모델별 고유 기능에 접근하는 것 또한 가능하다.
  - Anthropic, OpenAl,Microsoft, Amazon, Google 그리고 Ollama 등 모든 주요 AI 모델 제공업체를 지원하며 지원되는 모델 유형은 다음과 같다

| 카테고리 | 설명 | 사용 예시 |
|:-----|:----|:-----|
| Chat Models | 자연어 처리 및 대화 기능을 제공하는 LLM 기반 모델입니다. | AI 챗봇, Q&A 시스템 |
| Embedding Models | 텍스트를 벡터로 변환하여 유사도 검색 및 문서 검색에 사용됩니다. | 문서 유사도 비교, RAG 서비스 |
| Image Models | 주어진 프롬프트로 이미지 생성 및 수정 기능을 제공합니다. | 이미지 생성, 이이지 펀집 |
| Audio Models | 음성을 텍스트로 변환(STT)하거나 텍스트를 음성으로 변한(TTS)합니다. | 음성 인식, 음성 합성 |
| Moderation Models | 콘텐츠 검열 및 부적절한 택스트/이미지를 감지하는 모델입니다. | 택스트 필터링, 부적절 콘텐츠 차단 |


  - 구조화된 출력 - AI 모델의 출력을 POJO로 매핑
  - 주요 벡터 데이터베이스 지원
    - 벡터 데이터베이스에 관계없이 일관된 API 지원
    - SQL과 유사한 메타데이터 필터 API 포함
    - Apache Cassandra, Azure Cosmos DB, Azure Vector Search, Chroma, Elasticsearch,
    - GemFire, MariaDB, Milvus, MongoDB Atlas, Neo4j, OpenSearch, Oracle,
    - PostgreSQL/PGVector, PineCone,Qdrant,Redis, SAP Hana, Typesense 및 Weaviate 등

  - 도구/함수 호출
    - 모델이 클라이언트 측 도구 및 함수를 실행하도록 요청할 수 있어 필요에 따라 실시간 정보에 액세스하고 액션을 처리한다

**9. Spring Al의 추상화 방식**

  - 프롬프트 관리자 –Prompt와 ChatOptions
    - Prompt 클래스는 Spring AI에서 모델에 보낼 Message(메시지)와 ChatOptions(모델 파라미터 옵 션)을 감싸는 역할을 한다.
    ```java
    public class Prompt implements ModelRequest<List<Message>> {
    private final List<Message> messages;
    private ChatOptions modelOptions;
    @Override public ChatOptions getOptions() { ... }
    @Override public List<Message> getInstructions() { ... }
    ```


  - 프롬프트는 '어떤 메시지를 보낼지' 와 '어떤 옵션으로 보낼지' 를 함께 담고 있는 구조로서 ChatOptions 는 LLM 호출 시 사용할 다양한 파라미터를 정의한 인터페이스이다. 
  - 대부분의 LLM에서 공통으로 사용될 수 있는 옵션들만 포함하고 있다.
    ```java
    public interface ChatOptions extends ModelOptions {
    String getModel();
    Float getFrequencyPenalty(); // frequencyPenalty
    Integer getMaxTokens(); // maxTokens
    Float getPresencePenalty(); // presencePenalty
    List<String> getStopSequences(); // stopSequences
    Float getTemperature(); // temperature
    Integer getTopK(); // topK
    Float getTopP(); // topP ChatOptions copy();
    ```
  - ChatOptions가 제공하는 속성 (maxTokens, temperature, stopSequences)은 벤더 간 자동 변환된다.
  - 즉, Spring AI는 각 벤더마다 다른 옵션 명칭과 구격을 개발자가 알 필요 없이 일관되게 사용할 수 있도록 도와준다
  - 내부적으로 벤더별 API 규격에 맞게 자동 변환(mapping)하여 수행한다
  - 예를 들어, OpenAl stop를 통해서 텍스트 생성을 중단한다
  - 하지만 Anthropic stop_sequences 올 통해서 비슷한 기능을 한다 하지만 Spring AI가 알아서 처리한다.
    ```java
    import org.springframework.ai.chat.prompt.ChatOptions

    ChatOptions openAlChatOptions = ChatOptions.builder() .model("gpt-3.5-turbo")
        .temperature(0.7).stopSequences(listOf("₩n")) //OpenAI의 'stop' 옵션으로 자동 변환
        .build()

    ChatOptions anthropicChatOptions = ChatOptions.builder().model("claude-3-7-sonnet-20250219")
        .temperature(0.7).stopSequences(listOf("₩n")) //Anthropic의 'stop_sequences' 옵션 ..
        .build()
    ```
  - 정의되지 않은 추가 옵션(seed, logitBias 등)은 직접 매핑이 필요하다.

**10. Spring AI의 주요 추상화 계층 - ChatModel**

  - Spring AI는 ChatModel 이라는 핵심 컴포넌트를 기반으로 작동하며 LLM과의 상호 작용을 담당하는 인터페이스 이다
    - ChatModel 인터페이스를 구현한 클래스의 결과는 ChatResponse라는 공통된 응답객체로 리턴된다. 이 안에는 모델의 출력 메시지뿐만 아니라, 사용된 프롬프트, 모델 파라미터, 응답 시간 등의 메타 정보 도 포함되어 있어서, 후처리나 로깅, 디버깅시에도 유용하게 활용할 수 있다.
    ```java
    public interface ChatModel extends Modek<Prompt, ChatResponse>{ 
        default String call(String message) { ... }
        @Override ChatResponse call(Prompt prompt);
    }
    ```

# 5-2강 Spring AI 실무 프로젝트 LLM 챗봇 2탄 with JPA React Frontend
URL : https://www.youtube.com/watch?v=RRMztAkZfB8&list=PL_GeFwqaAwWGYihI94OReWQNzX8UoSAep&index=14

1. 🖥 백엔드 서버 실행 및 DB 데이터 확인 [00:12]  
2. 💻 React 프론트엔드 프로젝트 생성 및 의존성 설치 [02:43]  
3. 🎨 Bootstrap 연동 및 기본 CSS 설정 [06:27]  
4. 🛠 챗봇 UI 컴포넌트 구성 및 구조 설계 [08:06]  
5. 🔄 React 상태 관리(useState, useEffect 등) 및 이벤트 처리 [12:16]  
6. 🌐 백엔드 API와 데이터 연동(fetch, async/await) 및 오류 처리 [18:24]  
7. ⚙ CORS 문제 해결을 위한 Spring WebConfig 설정 [31:55]  
8. 📝 최종 작동 확인 및 향후 프로젝트 소개 [34:41]  

### vite UI 환경구성
- PS C:\dev\projects\java\fullstackcamp\spring-ai> npm create vite@latest springai-chatbot-ui -- template react
  - react선택
  - javascript선택
- cd springai-chatbot-ui
- vite 외부 라이브러리 설치 : npm i
- bootstrap 설치 : npm i bootstrap
- react-router-dom 설치 : npm i react-router-dom
- 실행 : npm run dev

# 6-1강 Spring AI LLM 실무 프로젝트 RAG를 통한 챗봇 구현 / 이론 및 기본 Chatbot 리뷰
URL : https://www.youtube.com/watch?v=RRMztAkZfB8&list=PL_GeFwqaAwWGYihI94OReWQNzX8UoSAep&index=14

✨ 목차  
📌[0:10] 프로젝트 소개 및 목표  
📚[1:18] RAG와 인베딩 개념 설명  
🔍[2:06] 정보 검색과 언어 생성 과정  
🧮[2:29] 인베딩과 코사인 유사도 이해하기  
📄[4:19] PDF 도큐먼트 리더와 텍스트 처리  
💻[6:06] 기본 챗봇 API 구현 실습  
🌐[14:02] CORS 설정 및 웹 서버 구성  
🖥[17:14] 프론트엔드(React) 프로젝트 세팅  
📝[27:22] 프론트엔드 간단 UI 및 기능 구현  
✅[35:10] 기본 챗봇 테스트 및 결과 확인  

### Spring Al ChatModels 실무 시나리오 (예제: 고객 지원 챗봇)

Spring AI의 ChatModels 인터페이스를 활용하여 실제 업무에서 사용 가능한 고객 지원 챗봇을 만드는 방법을 학습한다
이 챗봇은 고객의 질문의 특정 주제에 맞는 질문에 명확한 답을 제시하는 챗봇을 구현한다

확장: RAG(검색 증강 생성) 및 파일 첨부 기능 추가

기존 고객 지원 챗봇 시나리오에 RAG(Retrieval-Augmented Generation) 기술을 도입하여, 사용자가 첨부한 파일(예: 상품
설명서, 기술 설명서, 규정 등 PDF)의 내용을 기반으로 답변하는 고급 기능을 추가합니다.

요구사항: 이전의 챗봇 시스템에서는 불필요한 정보를 Model에서 생성을 하였지만, 보유하고 있는 첨부파일(PDF)를 통해
RAG 기술을 적용하여 명확한 답을 PDF에서 답변 할 수 있도록 한다

추가 기능:
1. 파일 첨부: 사용자가 채팅창에 상품 설명서 PDF 파일을 첨부할 수 있습니다.
2. RAG 기반 답변: 챗봇은 사용자가 첨부한 파일의 내용을 실시간으로 분석하고, 그 내용을 근거로 사용자의 질문에 답변
합니다. 이를 통해 미리 학습되지 않은 최신 정보나 상세 정보에 대해서도 정확한 응대가 가능해집니다.

