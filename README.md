# 🧠 NeuroCards - Backend

AI-powered flashcard generation and spaced repetition system built with **Spring Boot**.

---

## 🚀 Features

* 📄 Upload PDF → Extract text
* 🤖 AI generates high-quality flashcards (Q&A)
* 🧠 Spaced Repetition Algorithm
* 📊 Progress Tracking (Mastered / Learning / Due)
* 🔁 Review System (Again / Good / Easy)
* 🔐 JWT Authentication (User-specific data)

---

## 🛠️ Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL(Neon DB)
* WebClient (Groq AI API)
* Maven

---

## 📂 Project Structure

```
service/
 ├── AiService
 ├── FlashcardService
 ├── ReviewService
 ├── ProgressService

repository/
entity/
config/
```

---

## ⚙️ Setup Instructions

### 1. Clone project

```
https://github.com/adsingh2602/NeuroCards-Backend.git
cd __
```

---

### 2. Configure Environment Variables

Create `.env` file:

```
GROQ_API_KEY=your_api_key_here
GROQ_API_KEY=your_api_key_here
SPRING_DATASOURCE_URL=your_db_url
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password
```

---

### 3. application.yaml

```
spring:
  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 100MB

  datasource:
    url: ${SPRING_DATASOURCE_URL}
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}

groq:
  api-key: ${GROQ_API_KEY}
```

---

### 4. Run Application

```
./mvnw spring-boot:run
```

Server runs on:

```
http://localhost:8080
```

---

## 🔗 API Endpoints

### Auth

```
POST /api/auth/signup
```
```
POST /api/auth/login
```


### Upload PDF

```
POST /api/decks/upload-pdf
```

### Get All Decks

```
GET /api/decks
```

### Get Flashcards

```
GET /api/decks/{id}
```

### Review Card

```
POST /api/review/{cardId}?rating=good
```

### Get Progress

```
GET /api/progress/{deckId}
```

---

## 🧠 Spaced Repetition Logic

* **Again** → Reset progress
* **Good** → Increase interval
* **Easy** → Faster mastery

---

## 🔐 Security

* API keys stored in `.env`
* `.env` excluded via `.gitignore`

---

## 📌 Future Improvements

* Notifications for due cards
* AI difficulty classification
* Cloud deployment (AWS)

---

## 🔗 Live link

```
https://neurocards-iota.vercel.app/
```

---

## 👨‍💻 Author

Amardeep Singh
