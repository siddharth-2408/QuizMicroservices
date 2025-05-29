# 🧠 Quiz Application - Microservices Edition

> **From Monolith to Microservices**: A hands-on journey transforming a simple quiz app into a distributed, scalable system using Spring Cloud ecosystem.

---

## 🎯 What This Project Does

Ever wondered how Netflix, Amazon, or Uber handle millions of requests? This project demonstrates **microservices architecture** in action! 

**The Challenge**: Transform a simple monolithic quiz application into a distributed system that can scale independently.

**The Solution**: Break it into specialized, communicating services that work together seamlessly.

### 💡 Real-World Impact
- **Scalability**: Each service can be scaled independently based on demand
- **Resilience**: If one service fails, others continue working
- **Development Speed**: Teams can work on different services simultaneously
- **Technology Flexibility**: Each service can use different databases or frameworks

---

## 🏗️ System Architecture

```
┌─────────────────┐    ┌──────────────────┐
│   API Gateway   │────│  Service Registry│
│    (Port 8765)  │    │   (Eureka Server)│
└─────────┬───────┘    └──────────────────┘
          │
    ┌─────┴─────┐
    │           │
┌───▼────┐ ┌───▼────┐
│Quiz    │ │Question│
│Service │◄┤Service │
│        │ │        │
└────────┘ └────────┘
```

### 🎭 Meet the Services

| Service | Role | Responsibility |
|---------|------|----------------|
| 🌐 **API Gateway** | Traffic Controller | Routes all requests, single entry point |
| 📋 **Service Registry** | Phone Book | Keeps track of all running services |
| ❓ **Question Service** | Question Master | Manages questions, calculates scores |
| 🎯 **Quiz Service** | Quiz Orchestrator | Creates quizzes, coordinates with Question Service |

---

## 🛠️ Tech Arsenal

**Core Framework**
- ☕ **Java 17+** - Modern Java features
- 🚀 **Spring Boot 3.x** - Rapid application development
- ☁️ **Spring Cloud** - Microservices toolkit

**Microservices Stack**
- 🔍 **Eureka Server** - Service discovery made easy
- 🌉 **Spring Cloud Gateway** - Intelligent routing
- 🤝 **OpenFeign** - Declarative REST client
- 🗄️ **Spring Data JPA** - Database operations simplified

**Data & Tools**
- 🐬 **MySQL** - Reliable data storage
- 🔧 **Lombok** - Less boilerplate code
- 📦 **Maven** - Dependency management

---

## 🚀 Quick Start Guide

### 🔧 Prerequisites Checklist
- [ ] Java 17 or higher installed
- [ ] Maven 3.6+ ready to go
- [ ] MySQL 8.0+ running
- [ ] Your favorite IDE opened

### 📥 Get the Code
```bash
git clone https://github.com/yourusername/quiz-microservices.git
cd quiz-microservices
```

### 🗄️ Database Setup
```sql
-- Create databases for our services
CREATE DATABASE questiondb;
CREATE DATABASE quizdb;
```

### ⚙️ Configuration
Update `application.properties` in both services:
```properties
# Database connection
spring.datasource.url=jdbc:mysql://localhost:3306/db_name_for_both_dbs
spring.datasource.username=quiz_user
spring.datasource.password=quiz_password

# JPA settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 🎬 Launch Sequence (Order Matters!)

```bash
# Step 1: Start the Service Registry (Eureka Server)
cd ServiceRegistry
mvn spring-boot:run
# ✅ Wait for "Eureka Server started" message

# Step 2: Start Question Service
cd ../QuestionService  
mvn spring-boot:run
# ✅ Check Eureka dashboard: http://localhost:8761

# Step 3: Start Quiz Service
cd ../QuizService
mvn spring-boot:run
# ✅ Verify both services are registered

# Step 4: Start API Gateway
cd ../ApiGateway
mvn spring-boot:run
# 🎉 Ready to rock!
```

### 🌐 Service Health Check
- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8765
- **Question Service**: http://localhost:8080 & 8081 (Two instances were created there)
- **Quiz Service**: http://localhost:8090

---

## 📡 API Reference Guide

### 🎯 Base URL
All requests go through the API Gateway:
```
http://localhost:8765
```

### ❓ Question Service Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/question-service/question/allquestions` | 📋 Fetch all questions |
| `GET` | `/question-service/question/category/{category}` | 🏷️ Get questions by category |
| `POST` | `/question-service/question/add` | ➕ Add new question |
| `GET` | `/question-service/question/generate` | 🎲 Generate random question IDs |
| `POST` | `/question-service/question/getQuestions` | 📝 Get specific questions by IDs |
| `POST` | `/question-service/question/getScore` | 🏆 Calculate quiz score |

### 🎯 Quiz Service Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/quiz-service/quiz/create` | 🆕 Create a new quiz |
| `GET` | `/quiz-service/quiz/get/{id}` | 📋 Get quiz questions |
| `POST` | `/quiz-service/quiz/submit/{id}` | ✅ Submit quiz responses |

---

## 🧪 Testing Playground

### 🔥 Hot API Requests (Copy & Paste Ready!)

#### 1️⃣ Add Your First Question
```bash
POST http://localhost:8080/question-service/question/add
Content-Type: application/json

{
  "questionTitle": "What is the main advantage of microservices?",
  "option1": "Scalability",
  "option2": "Complexity", 
  "option3": "Single deployment",
  "option4": "Shared database",
  "rightAnswer": "Scalability",
  "difficultyLevel": "Medium",
  "category": "Architecture"
}
```

#### 2️⃣ Generate Question IDs
```bash
GET http://localhost:8080/question-service/question/generate?category=Architecture&num=3
```

#### 3️⃣ Create an Awesome Quiz
```bash
POST http://localhost:8080/quiz-service/quiz/create
Content-Type: application/json

{
  "category": "Architecture",
  "num": 5,
  "title": "Microservices Mastery Quiz"
}
```

#### 4️⃣ Take the Quiz
```bash
# First, get the quiz questions
GET http://localhost:8080/quiz-service/quiz/get/1

# Then submit your answers
POST http://localhost:8080/quiz-service/quiz/submit/1
Content-Type: application/json

[
  {
    "id": 1,
    "response": "Scalability"
  },
  {
    "id": 2, 
    "response": "Spring Boot"
  }
]
```

#### 5️⃣ Direct Score Calculation
```bash
POST http://localhost:8080/question-service/question/getScore
Content-Type: application/json

[
  {
    "id": 1,
    "response": "Scalability"
  }
]
```

---

## 🗂️ Project Structure

```
Microservices/
├── 📁 ServiceRegistry/          # 🏢 Eureka Server - Service Discovery
│   ├── src/main/java/
│   └── pom.xml
├── 📁 QuestionService/          # ❓ Question Management & Scoring
│   ├── src/main/java/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── model/
│   │   └── dao/
│   └── pom.xml
├── 📁 QuizService/              # 🎯 Quiz Orchestration
│   ├── src/main/java/
│   │   ├── controller/
│   │   ├── dao/
│   │   ├── dto/
│   │   ├── service/
│   │   ├── model/
│   │   └── feign/
│   └── pom.xml
├── 📁 ApiGateway/               # 🌐 Traffic Router
│   ├── src/main/java/
│   └── pom.xml
└── 📄 README.md                 # 📖 You are here!
```

---

## 🎓 What I Learned

### 🧠 Core Concepts Mastered
- **Service Discovery**: How services find and communicate with each other
- **Load Balancing**: Distributing requests across multiple instances (It was managed automatically but still)
- **API Gateway Pattern**: Single entry point for distributed systems
- **Inter-Service Communication**: Using Feign for declarative REST calls
- **Distributed Data Management**: Each service managing its own data

### 🔥 Technical Skills Gained
- Spring Cloud ecosystem proficiency
- Microservices design patterns
- Distributed system debugging
- Service-to-service authentication concepts
- Database design for microservices

---

## 🙏 Acknowledgments

This project was built as part of my microservices learning journey. Special thanks to:
- Telusko

---
