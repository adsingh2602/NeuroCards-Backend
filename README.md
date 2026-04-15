# 🧠 NeuroCards - Backend

AI-powered flashcard generation and spaced repetition system built with **Spring Boot**.

---

## 🚀 Features

* 📄 Upload PDF → Extract text
* 🤖 AI generates flashcards (Q&A)
* 🧠 Spaced Repetition Algorithm
* 📊 Progress Tracking (Mastered / Learning / Due)
* 🔁 Review System (Again / Good / Easy)

---

## 🛠️ Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL
* WebClient (AI API)
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
```

---

### 3. application.yaml

```
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

* Authentication (JWT)
* User-specific decks
* AI difficulty classification
* Cloud deployment (AWS)

---

## 👨‍💻 Author

Amardeep Singh
