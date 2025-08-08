# 🛒 Delivery Scheduler — Carrefour Java Kata

## 🧰 Technologies utilisées
<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4-brightgreen" alt="Spring Boot 3.4" />
  <img src="https://img.shields.io/badge/Java-21-blue" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring%20AI-1.0.0--M6-orange" alt="Spring AI 1.0.0-M6" />
  <img src="https://img.shields.io/badge/Spring%20Data%20JPA-3.x-green" alt="Spring Data JPA 3.x" />
  <img src="https://img.shields.io/badge/H2-DB-lightgrey" alt="H2 Database" />
  <img src="https://img.shields.io/badge/JUnit-5-red" alt="JUnit 5" />
  <img src="https://img.shields.io/badge/Mockito-5-yellow" alt="Mockito 5" />
  <img src="https://img.shields.io/badge/Maven-3.9%2B-C71A36" alt="Maven 3.9+" />
</p>

## 🎯 Pitch

MVP de réservation de créneaux de livraison :

1. Le client choisit un mode de livraison : DRIVE, DELIVERY, DELIVERY_TODAY, DELIVERY_ASAP.
2. Il consulte les créneaux disponibles pour un jour donné.
3. Il réserve un créneau (prévention du double-booking via @Version).
4. Un endpoint Spring AI fournit un conseil (texte) contextualisé par mode + jour.

## 📋 Fonctionnalités

- [x] Choix du mode de livraison : DRIVE, DELIVERY, DELIVERY_TODAY, DELIVERY_ASAP
- [x] Consultation des créneaux disponibles par jour et par mode
- [x] Réservation d’un créneau (prévention double-booking via @Version / optimistic locking)
- [x] Endpoint Spring AI pour un conseil contextualisé (mode + jour)
- [x] Tests unitaires & d’API (service + contrôleur)
- [x] Seed de données désactivé automatiquement en profil `test`

---

## 🛠️ Stack technique

Backend : Spring Boot 3.4, Java 21, Spring Web, Spring Data JPA, H2 (dev), Spring AI  
Tests : JUnit 5, Mockito, Spring Boot Test

---

## 📦 Prérequis

- JDK 21
- Maven 3.9+
- (Optionnel) Clé API OpenAI pour l’endpoint AI

---

## ⚙️ Configuration (application.properties)

### JPA/H2 (dev)
spring.datasource.url=jdbc:h2:mem:devdb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true

### Spring AI (mettre une vraie clé si vous activez l’IA)
spring.ai.openai.api-key=${OPENAI_API_KEY:}
spring.ai.openai.chat.options.model=gpt-4o-mini

### Seed (désactivé en test, activable en dev)
app.seed.enabled=true

---


## 🧰 Installation & démarrage — Delivery Scheduler (Docker ou local)


 1) Cloner le dépôt
git clone https://github.com/<votre-org>/delivery-scheduler.git
cd delivery-scheduler

 2) Préparer les variables d'environnement
    → Copiez l'exemple puis renseignez votre clé OpenAI
cp .env.example .env
    Ouvrez .env et remplacez:
    OPENAI_API_KEY=sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

 
### Option A : Docker (recommandé)
 

 3A) Build & run (multi-stage Dockerfile -> image runtime JRE only)
docker compose up --build -d

 4A) Vérifier les logs de l'API
docker compose logs -f api

 5A) Smoke test
curl "http://localhost:8080/ai/advice?method=DELIVERY&day=2025-08-10"
 → {"advice":"Pour garantir la disponibilité de votre créneau ..."}

 (Arrêt)
 docker compose down           # stop
 docker compose down -v        # stop + volumes

### Option B : Exécution locale (JDK 21 + Maven 3.9+)

 3B) Exporter la clé dans votre shell
export OPENAI_API_KEY="sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"

 4B) Démarrer l'app
mvn -q spring-boot:run

 5B) Smoke test
curl "http://localhost:8080/ai/advice?method=DELIVERY&day=2025-08-10"

 
## Notes & dépannage rapides


 - 401 invalid_api_key ?
   Vérifiez la variable dans le container :
   docker compose exec api printenv | grep OPENAI

 - Build/packaging manuel :
   mvn -q -DskipTests package && java -jar target/*.jar

 - Profil test :
   Le seed de données est désactivé automatiquement pendant les tests.

---

## 🚀 Démarrage

### 1) Lancer l’appli (profil dev par défaut)
mvn spring-boot:run

### 2) (Facultatif) Avec profil explicite
SPRING_PROFILES_ACTIVE=dev mvn spring-boot:run

### 3) Console H2 (dev)
   - JDBC URL : jdbc:h2:mem:devdb
   - UI       : http://localhost:8080/h2-console

---

## 🔑 Clé OpenAI (si vous testez l’IA)

 Générer une clé sur https://platform.openai.com/
 Puis exporter la variable d’environnement avant de lancer l’appli :
### Windows PowerShell
$Env:OPENAI_API_KEY="sk-..."
### bash/zsh
export OPENAI_API_KEY="sk-..."

---

## 🧪 Tests

### Tous les tests
mvn clean test

### Notes :
 - Le seed de données est désactivé en profil test.
 - Si vous voyez des erreurs de conversion @PathVariable/@RequestParam,
   assurez-vous que le plugin maven-compiler utilise -parameters.

---

## 📡 API – Endpoints principaux

### Lister les créneaux par mode + jour (exemple de route)
GET /slots?method=DELIVERY&day=2025-08-08
→ 200 OK
[
{ "id": 1, "day": "2025-08-08", "start": "10:00", "end": "11:00", "reserved": false, "method": "DELIVERY" }
...
]

### Réserver un créneau
POST /reservations/{slotId}
Content-Type: application/json
{
"customerRef": "CUST-12345"
}
→ 200 OK
{
"reservationId": 42,
"slotId": 1,
"customerRef": "CUST-12345"
}
### Cas d’erreur possibles :
 - 404 si le slot n’existe pas
 - 409 si le slot est déjà réservé (IllegalStateException)

### Conseil (Spring AI)
GET /ai/advice?method=DELIVERY&day=2025-08-08
→ 200 OK
{ "advice": "..." }
### Cas d’erreur :
 - 400 si method invalide (enum)
 - 400 si day manquant
 - 401 côté AI si OPENAI_API_KEY manquante/invalide

---

## 🧪 Exemples CURL

### Slots
curl "http://localhost:8080/slots?method=DELIVERY&day=2025-08-08"

### Réservation
curl -X POST "http://localhost:8080/reservations/1" \
-H "Content-Type: application/json" \
-d '{ "customerRef": "CUST-12345" }'

### Conseil AI
curl "http://localhost:8080/ai/advice?method=DELIVERY&day=2025-08-08"

---

## 📚 Jeux de données (dev)

### Le seed (CommandLineRunner) crée quelques créneaux sur plusieurs jours
### pour chaque mode de livraison. En test, il est désactivé.

---

## ✅ Critères (MVP) couverts

- Sélection d’un mode de livraison (enum)
- Sélection d’un jour et d’un créneau (créneaux réservables et exclusifs)
- Conflits gérés (double-booking empêché)
- Endpoint AI qui commente la sélection (avec mock en test unitaire)
- Tests verts (service + contrôleur)

---

## 🧭 Décisions & limites

- Stockage H2 en mémoire pour la démo (remplaçable par Postgres/MySQL)
- Pas d’authentification/quotas client pour rester focalisé sur le kata
- Spring AI utilisable avec clé OpenAI, sinon tests AI mockés


---

## 👤 Auteur

Mohamed Najib HMAIDA  
hmaida.najib@gmail.com  |  LinkedIn: https://www.linkedin.com/in/mohamed-najib-hmaida-82b56a8/
