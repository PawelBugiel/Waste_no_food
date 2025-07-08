# Waste no food

Aplikacja webowa full-stack (Spring Boot + Vue.js) do zarządzania domową spiżarnią i minimalizowania marnowania żywności.

![Java](https://img.shields.io/badge/Java-17-blue.svg?logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/Spring_Boot-3.3.0-green.svg?logo=spring&logoColor=white) ![Vue.js](https://img.shields.io/badge/Vue.js-3-brightgreen.svg?logo=vuedotjs&logoColor=white) ![MySQL](https://img.shields.io/badge/MySQL-blue.svg?logo=mysql&logoColor=white) ![Docker](https://img.shields.io/badge/Docker-blue.svg?logo=docker&logoColor=white) ![JWT](https://img.shields.io/badge/JWT-black.svg?logo=jsonwebtokens&logoColor=white)

---

## Demo

![Waste no food - Demo GIF](https://TUTAJ_WSTAW_LINK_DO_TWOJEGO_GIF-A.gif)

---

## Spis treści
- [O projekcie](#o-projekcie)
- [Kluczowe Funkcjonalności](#kluczowe-funkcjonalności)
- [Zrzuty Ekranu](#zrzuty-ekranu)
- [Stos Technologiczny](#stos-technologiczny)
- [Uruchomienie Projektu](#uruchomienie-projektu)
- [Sposób użycia](#sposób-użycia)
- [Roadmap / Plan Rozwoju](#roadmap--plan-rozwoju)
- [Dane logowania](#dane-logowania)
- [Licencja](#licencja)
- [Kontakt](#kontakt)

---

## O projekcie

**Waste no food** to aplikacja webowa typu CRUD, której głównym celem biznesowym jest **pomoc w monitorowaniu dat przydatności do spożycia produktów spożywczych** i minimalizowanie ich marnotrawstwa w gospodarstwach domowych.

Projekt ten pełni rolę mojej piaskownicy deweloperskiej, w której rozwijam i demonstruję praktyczne umiejętności w zakresie tworzenia nowoczesnych aplikacji. Kluczowe aspekty, które projekt prezentuje, to:

* **Architektura Full-Stack:** Demonstracja umiejętności integracji backendu (Spring Boot) z frontendem (Vue.js) w spójną, działającą aplikację.
* **Projektowanie REST API:** Budowa bezpiecznego i elastycznego REST API, zabezpieczonego mechanizmem **JWT (JSON Web Tokens)**.
* **Czysty i skalowalny kod:** Zastosowanie architektury warstwowej (prezentacja, logika, dane) oraz wzorców projektowych (np. DTO) dla lepszej modułowości i łatwości utrzymania.
* **Kompleksowe testowanie:** Pokrycie kodu testami jednostkowymi (JUnit, Mockito) i integracyjnymi, z analizą pokrycia kodu za pomocą JaCoCo, co gwarantuje jakość i stabilność rozwiązania.
* **Konteneryzacja:** Wykorzystanie Docker i Docker Compose do tworzenia przenośnych i łatwych w zarządzaniu środowisk deweloperskich oraz produkcyjnych.
* **Rozwiązywanie problemów:** Budowanie doświadczenia w debugowaniu, rozwiązywaniu realnych problemów programistycznych i optymalizacji kodu.

<small>[Spis treści](#spis-treści)</small>

---

## Kluczowe Funkcjonalności

### Backend (REST API)
* **Zarządzanie produktami (CRUD)**: Pełen zestaw operacji (Create, Read, Update, Delete) na produktach spożywczych.
* **Uwierzytelnianie i autoryzacja (JWT)**: Rejestracja i logowanie użytkowników z generowaniem tokenów JWT, zabezpieczenie endpointów w oparciu o role (użytkownik, administrator).
* **Zarządzanie użytkownikami**: Dostępne dla administratorów operacje pozwalające na zarządzanie kontami użytkowników.
* **Dokumentacja API**: Automatycznie generowana, interaktywna dokumentacja Swagger UI.

### Frontend (Interfejs Użytkownika)
* **Logowanie i Rejestracja**: Formularze umożliwiające dostęp do aplikacji.
* **Panel Główny**: Dashboard do przeglądania, dodawania i zarządzania produktami.
* **Zarządzanie Produktami**: Intuicyjne formularze do edycji i tworzenia nowych produktów.
* **Panel Administracyjny**: Dedykowany widok dla administratorów z dodatkowymi funkcjami zarządzania.

<small>[Spis treści](#spis-treści)</small>

---

## Zrzuty Ekranu

| Widok Logowania | Główny Dashboard | Formularz Edycji |
| :---: | :---: | :---: |
| ![Widok Logowania](https://TUTAJ_WSTAW_LINK_DO_ZRZUTU_EKRANU_1.png) | ![Główny Dashboard](https://TUTAJ_WSTAW_LINK_DO_ZRZUTU_EKRANU_2.png) | ![Edycja Produktu](https://TUTAJ_WSTAW_LINK_DO_ZRZUTU_EKRANU_3.png) |

<small>[Spis treści](#spis-treści)</small>

---

## Stos Technologiczny

| Kategoria | Technologie |
| :--- | :--- |
| **Backend** | Java 17, Spring Boot, Spring Security, Spring Data JPA, Hibernate, MapStruct, Lombok, JWT |
| **Frontend** | Vue.js 3, Vue Router, Pinia, Bootstrap, Node.js |
| **Baza Danych** | MySQL |
| **Testowanie** | JUnit 5, Mockito, JaCoCo |
| **DevOps & Narzędzia** | Docker, Docker Compose, Git, Maven, IntelliJ IDEA, Postman, OpenAPI (Swagger) |

<small>[Spis treści](#spis-treści)</small>

---

## Uruchomienie Projektu

### Wymagania
* **Docker** i **Docker Compose** (zawarte w Docker Desktop)
* **Git** (do sklonowania repozytorium)

### Uruchomienie za pomocą Docker Compose (zalecane)
To najprostszy sposób na uruchomienie całej aplikacji (backend, frontend, baza danych) za pomocą jednego polecenia.

1.  Sklonuj repozytorium:
    ```bash
    git clone [https://github.com/TwojaNazwaUzytkownika/FoodToEat.git](https://github.com/TwojaNazwaUzytkownika/FoodToEat.git)
    cd FoodToEat
    ```
2.  Upewnij się, że lokalny wolumen `fte-db-vol-PROD` jest utworzony, ponieważ `docker-compose.yml` używa go jako zewnętrznego. Jeśli nie, utwórz go:
    ```bash
    docker volume create fte-db-vol-PROD
    ```
3.  Uruchom aplikację w tle:
    ```bash
    docker-compose up -d
    ```
  * Frontend będzie dostępny pod adresem: `http://localhost:8080`
  * Backend będzie dostępny pod adresem: `http://localhost:8081`

### Uruchomienie ze źródeł (dla deweloperów)
Alternatywnie, możesz uruchomić każdy komponent osobno. Wymaga to zainstalowanego **JDK 17**, **Maven** oraz **Node.js**.

<small>[Spis treści](#spis-treści)</small>

---

## Sposób użycia

1.  Po uruchomieniu aplikacji, otwórz przeglądarkę i przejdź na adres `http://localhost:8080`.
2.  Zarejestruj nowe konto lub użyj gotowych danych do logowania (podanych poniżej).
3.  Po zalogowaniu możesz zarządzać produktami spożywczymi.
4.  Interaktywna dokumentacja API (Swagger UI) jest dostępna pod adresem: `http://localhost:8081/swagger-ui.html`.

<small>[Spis treści](#spis-treści)</small>

---

## Roadmap / Plan Rozwoju

Poniżej znajduje się lista planowanych i zrealizowanych zadań dla projektu:

* **Backend & Dane**
  * [x] Skonfigurowanie bazy danych i warstwy JPA.
  * [x] Implementacja REST API dla operacji CRUD na produktach.
  * [x] Implementacja uwierzytelniania i autoryzacji z JWT.
  * [ ] Pokrycie kodu backendu kompleksowymi testami jednostkowymi i integracyjnymi.
  * [ ] Integracja z zewnętrznym API (**Open Food Facts API**) w celu automatycznego pobierania danych o produktach.
  * [ ] Dodanie zaawansowanego logowania i monitorowania (np. Logback/SLF4J).

* **Frontend (UI)**
  * [x] Rozwój interfejsu w Vue.js z Vue Router i Pinia.
  * [x] Strona logowania/rejestracji oraz widok listy produktów.
  * [x] Formularze do dodawania i edycji produktów.
  * [ ] Obsługa błędów i walidacja po stronie klienta.
  * [ ] Implementacja zaawansowanych funkcji UI (wyszukiwanie, filtrowanie, sortowanie).

* **DevOps & Deployment**
  * [x] Przygotowanie konfiguracji Docker Compose.
  * [ ] Konfiguracja CI/CD przy użyciu **GitHub Actions**.
  * [ ] Wdrożenie aplikacji na platformie chmurowej (np. AWS, Oracle Cloud).

<small>[Spis treści](#spis-treści)</small>

---

## Dane logowania
Dane przeznaczone do testowania aplikacji w środowisku deweloperskim:

* **Administrator**:
  * Login: `admin@admin`
  * Hasło: `Admin22*`
* **Użytkownik**:
  * Login: `user@user`
  * Hasło: `User123*`

<small>[Spis treści](#spis-treści)</small>

---

## Licencja

Projekt jest udostępniony na licencji MIT. Zobacz plik `LICENSE`, aby uzyskać więcej informacji.
*(Pamiętaj, aby dodać plik `LICENSE` z treścią licencji MIT do swojego repozytorium)*

<small>[Spis treści](#spis-treści)</small>

---

## Kontakt
**Paweł Bugiel**
* LinkedIn: [https://www.linkedin.com/in/pawel-bugiel-b936a1229](https://www.linkedin.com/in/pawel-bugiel-b936a1229)
* Email: pawel.bugiel@outlook.com

<small>[Spis treści](#spis-treści)</small>

---
<p align="center"><a href="#waste-no-food">^ Wróć na górę ^</a></p>
````