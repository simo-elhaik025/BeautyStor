# Architecture Decision Log

## Décision #001
**Date :** 19/07/2026

**Décision**
Le backend sera développé sous forme d'une API REST avec Spring Boot.

**Justification**
Permettre un frontend indépendant et faciliter l'évolution du projet.

---

## Décision #002

**Décision**
Le projet sera organisé en trois parties principales :
- backend/
- frontend/
- docs/

**Justification**
Séparer clairement le code, l'interface utilisateur et la documentation.

---

## Décision #003

**Décision**
Les produits pourront posséder plusieurs images.

**Justification**
Répondre aux standards d'un site e-commerce professionnel.

---

## Décision #004

**Décision**
L'architecture sera générique afin de supporter d'autres catégories de produits à l'avenir.

**Justification**
Préparer l'évolution vers les vêtements et autres catégories sans modifier l'architecture.
