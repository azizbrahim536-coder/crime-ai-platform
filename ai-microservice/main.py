from fastapi import FastAPI
from pydantic import BaseModel
from typing import Optional

app = FastAPI(title="CrimeAI Microservice")


class ChatRequest(BaseModel):
    question: str


class ChatResponse(BaseModel):
    answer: str


class ClassificationRequest(BaseModel):
    description: str


class ClassificationResponse(BaseModel):
    typeCrime: str
    confidence: float


def classify_crime(description: str):
    text = description.lower()

    keywords = {
        "VOL": ["vol", "voleur", "cambriolage", "argent", "bijoux", "téléphone", "magasin"],
        "AGRESSION": ["agression", "frapper", "violence", "blessure", "attaque", "coups"],
        "FRAUDE": ["fraude", "arnaque", "escroquerie", "faux", "chèque", "carte bancaire"],
        "HOMICIDE": ["meurtre", "homicide", "mort", "assassinat", "cadavre"],
        "CYBERCRIME": ["piratage", "hack", "facebook", "compte", "mot de passe", "cyber", "phishing"]
    }

    best_type = "NON_DEFINI"
    best_score = 0

    for crime_type, words in keywords.items():
        score = sum(1 for word in words if word in text)

        if score > best_score:
            best_score = score
            best_type = crime_type

    confidence = min(best_score / 3, 1.0)

    return best_type, confidence


@app.get("/")
def home():
    return {
        "message": "CrimeAI Microservice fonctionne correctement"
    }


@app.post("/ai/chat", response_model=ChatResponse)
def chat(request: ChatRequest):
    question = request.question.lower()

    if "bonjour" in question or "salut" in question or "salam" in question:
        return ChatResponse(
            answer="Bonjour, je suis l'assistant IA de la plateforme CrimeAI. Comment puis-je vous aider ?"
        )

    if "vol" in question:
        return ChatResponse(
            answer="Pour une affaire de vol, il est recommandé de vérifier le lieu, la date, les témoins, les objets volés et les personnes suspectes."
        )

    if "agression" in question:
        return ChatResponse(
            answer="Pour une agression, il faut analyser la victime, les blessures, les témoins, le lieu et l'heure de l'incident."
        )

    if "statistique" in question or "dashboard" in question:
        return ChatResponse(
            answer="Les statistiques peuvent aider à identifier les types de crimes les plus fréquents, les villes concernées et l'évolution des affaires."
        )

    if "rapport" in question:
        return ChatResponse(
            answer="Vous pouvez générer un rapport PDF depuis la page détails d'une affaire."
        )

    return ChatResponse(
        answer="Je peux vous aider à analyser les crimes, les affaires, les suspects, les victimes et les statistiques. Essayez de poser une question plus précise."
    )


@app.post("/ai/classify", response_model=ClassificationResponse)
def classify(request: ClassificationRequest):
    crime_type, confidence = classify_crime(request.description)

    return ClassificationResponse(
        typeCrime=crime_type,
        confidence=confidence
    )