<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen" alt="Spring Boot 3.2" />
  <img src="https://img.shields.io/badge/Java-21-blue" alt="Java 21" />
  <img src="https://img.shields.io/badge/Kafka-3.6-orange" alt="Kafka 3.6" />
  <img src="https://img.shields.io/badge/Next.js-15-black" alt="Next.js 15" />
  <img src="https://img.shields.io/badge/React-19-blue" alt="React 19" />
  <img src="https://img.shields.io/badge/Zustand-4-yellow" alt="Zustand 4" />
  <img src="https://img.shields.io/badge/TailwindCSS-3-teal" alt="TailwindCSS 3" />
</p>

# 🛍️ Promo – Code-Promo Full-Stack Demo

## 🎯 Pitch

Proof of Concept d’application d’un code promotionnel à un panier :

1. L’utilisateur ouvre la page **`/cart`**
2. Il saisit un **code promo**
3. Le backend calcule la remise et renvoie le nouveau total
4. Un événement Kafka (**DiscountAppliedEvent** ou **DiscountRejectedEvent**) est publié

---

## 📋 Table des matières

1. [Fonctionnalités](#-fonctionnalités)
2. [Stack technique](#-stack-technique)
3. [Prérequis](#-prérequis)
4. [Installation & démarrage](#-installation--démarrage)
5. [Données de démonstration](#-données-de-démonstration)
6. [Endpoints](#-endpoints)
7. [Inspecter le topic Kafka](#-inspecter-le-topic-kafka)
8. [Tests Maven](#-tests-maven)
9. [Licence & Auteur](#-licence--auteur)

---

## 🚀 Fonctionnalités

| Fonctionnalité                                        | Statut |
| :---------------------------------------------------- | :----: |
| API REST versionnée (`/api/v1/discount/*`)            |   ✅   |
| Modèles : `Cart`, `Product`, `DiscountCode`           |   ✅   |
| Règles métier : expiration, quota, catégories ciblées |   ✅   |
| Stockage in-memory (`ConcurrentHashMap`)              |   ✅   |
| Publication Kafka (topic `discounts`)                 |   ✅   |
| Front Next.js 15 · Zustand · Tailwind                 |   ✅   |
| Toast d’erreur + mise à jour dynamique du total       |   ✅   |
| Tests unitaires (JUnit 5 · Mockito)                   |   ✅   |

---

## 🛠️ Stack technique

| Couche        | Technologies / Librairies                     |
| :------------ | :-------------------------------------------- |
| **Backend**   | Spring Boot 3.2, Java 21, Spring Kafka        |
| **Frontend**  | Next.js 15 (App Router), React 19, TypeScript |
| **État**      | Zustand 4                                     |
| **UI**        | TailwindCSS 3, Radix UI, Lucide Icons         |
| **Messaging** | Apache Kafka 3.6 (Bitnami container)          |
| **Tests**     | JUnit 5, Mockito                              |

---

## 📦 Prérequis

| Outil   | Version minimale          |
|:--------|:--------------------------|
| JDK     | 21                        |
| Node.js | 23                        |
| React   | 19                        |
| Maven   | 3.9                       |
| Docker  | — (pour `docker compose`) |

---

## ⚡ Installation & démarrage rapide

```bash
# Cloner le dépôt
git clone https://gitlab.com/fr_kata_sf/c4-SF-0413-SI01.git
cd c4-SF-0413-SI01

# Lancer avec Docker Compose (Kafka + backend + frontend)
docker compose up --build

# → Frontend : http://localhost:3000/cart
# → API      : http://localhost:8080/api/v1/discount/cart/cart123


Sans Docker :
• Backend : cd promo-backend && ./mvnw spring-boot:run (Kafka requis sur localhost:9092)
• Frontend : cd promo-front && npm install && npm run dev
```
---

## 📝 Données de démonstration

| Type       | Identifiant / Code | Détails                                                         |
|------------|--------------------|-----------------------------------------------------------------|
| **Panier** | `cart123`          | *Clean Code* — 40 €<br>*Smart TV* — 300 € (total : 340 €)       |
| **Codes**  | `BOOK20`           | −20 % sur la catégorie **books** (expire le 01/01/2030)         |
|            | `ELECTRO10`        | −10 % sur la catégorie **electronics** (pas de date d’expiration)|
|            | `OLD10`            | expiré → renvoie une erreur « Code expiré »                     |


---

## 📡 Endpoints

| Méthode | URL                                   | Corps de requête (JSON)       | Réponse attendue |
|---------|---------------------------------------|-------------------------------|------------------|
| **GET** | `/api/v1/discount/cart/{id}`          | —                             | Objet `Cart` |
| **POST**| `/api/v1/discount/apply`              | `{ "cart": { … }, "code": "" }` | Objet `DiscountResponse` |

```bash
Exemple d’appel POST /apply
curl -X POST http://localhost:8080/api/v1/discount/apply \
-H "Content-Type: application/json" \
-d '{
"cart": { "cartId": "cart123" },
"code": "BOOK20"
}'

Réponse :

{
"applied": true,
"discountAmount": 68.0
}
```
---

## 🛠️ Inspecter le topic Kafka `discounts`

```bash
# Ouvrir un shell dans le conteneur Kafka
docker compose exec kafka bash

# 1) Vérifier que le topic existe
kafka-topics.sh --bootstrap-server localhost:9092 --list
# → doit afficher   discounts   (en plus de __consumer_offsets)

# 2) Consommer les messages (depuis le début)
kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic discounts \
  --from-beginning \
  --property print.value=true

Chaque événement publié (code appliqué ou rejeté) s’affiche alors au format JSON :

{"cartId":"cart123","code":"BOOK20","discountAmount":68.0,"timestamp":"2025-06-25T16:10:42.123Z"}

```

## ✅ Tests Maven

Lance tous les tests unitaires du backend :

```bash
cd promo-backend
./mvnw clean test
```

## 📄 Licence & Auteur


**Auteur** : *Mohamed Najib HMAIDA*  
Contact : hmaida.najib@gmail.com • [LinkedIn](https://www.linkedin.com/in/mohamed-najib-hmaida-82b56a8/)

