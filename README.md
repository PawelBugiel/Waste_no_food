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
* **Zarządzanie Produktami (CRUD):** Zestaw endpointów REST API pozwalających na pełne zarządzanie cyklem życia produktów.
* **Uwierzytelnianie i Autoryzacja (JWT):** Rejestracja i logowanie użytkowników z generowaniem tokenów JWT, zabezpieczenie endpointów w oparciu o role.
* **Zarządzanie Użytkownikami:** Dostępne dla administratorów operacje pozwalające na zarządzanie kontami użytkowników.
* **Dokumentacja API**: Automatycznie generowana, interaktywna dokumentacja Swagger UI.

### Frontend (Interfejs Użytkownika)
* **Login Page**: Formularz umożliwiający dostęp do aplikacji.
* **Products Dashboard**: Główny panel do zarządzania produktami, oferujący listowanie, paginację, sortowanie oraz pełne operacje CRUD. Dostępny dla wszystkich zalogowanych użytkowników.
* **Admin Dashboard**: Centralny punkt nawigacyjny dla administratora, umożliwiający przejście do zarządzania produktami lub użytkownikami. Dostępny tylko dla Administratora.
* **User Management**: Panel administracyjny do zarządzania kontami użytkowników (dodawanie, usuwanie, przypisywanie ról). Dostępny tylko dla Administratora.

<small>[Spis treści](#spis-treści)</small>

---

## Zrzuty Ekranu

| Widok Logowania | Panel Administratora |
| :---: | :---: |
| <a href="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/LoginPage.png" target="_blank"><img src="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/LoginPage.png" alt="Widok Logowania" title="Kliknij, aby powiększyć" width="400"></a> | <a href="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/AdminDashboard.png" target="_blank"><img src="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/AdminDashboard.png" alt="Panel Administratora" title="Kliknij, aby powiększyć" width="400"></a> |
| **Panel Produktów** | **Zarządzanie Użytkownikami** |
| <a href="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/ProductsDashboard.png" target="_blank"><img src="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/ProductsDashboard.png" alt="Panel Produktów" title="Kliknij, aby powiększyć" width="400"></a> | <a href="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/UserManagement.png" target="_blank"><img src="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/UserManagement.png" alt="Zarządzanie Użytkownikami" title="Kliknij, aby powiększyć" width="400"></a> |

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

* **Docker Desktop**: Aplikacja niezbędna do uruchomienia projektu w skonteneryzowanym środowisku
* **Git**: Narzędzie potrzebne do sklonowania repozytorium z kodem źródłowym.

### Instrukcja Uruchomienia
Aplikacja jest w pełni skonteneryzowana, a jej uruchomienie sprowadza się do kilku kroków.

**1. Sklonuj repozytorium i przejdź do folderu projektu**  

Użyj poniższych komend, aby pobrać projekt na swój dysk i wejść do jego głównego katalogu.

```bash
# Sklonuj repozytorium
git clone [https://github.com/pawelbugiel/Waste_no_food.git]

# Przejdź do nowo utworzonego katalogu
cd Waste_no_food
```

**2. Skonfiguruj środowisko**  
Przed pierwszym uruchomieniem, należy utworzyć plik konfiguracyjny ze zmiennymi środowiskowymi. Wystarczy skopiować dołączony szablon.
```bash

# Windows (w PowerShell lub CMD)
copy .env.example .env

# Linux/macOS
#cp .env.example .env
```
Plik .env zawiera hasło do bazy danych i jest ignorowany przez Git.  
<br>

**3. Uruchom aplikację**  
Użyj Docker Compose, aby pobrać obrazy i uruchomić wszystkie usługi w tle (`-d`).
```bash  

docker-compose up -d
```
Aplikacja jest teraz uruchomiona. Przejdź do sekcji "Sposób użycia", aby dowiedzieć się, jak zacząć z niej korzystać.

**4. Zatrzymywanie aplikacji**  
Aby zatrzymać i usunąć wszystkie kontenery powiązane z projektem, użyj komendy:
```bash
   
docker-compose down
```

<small>[Spis treści](#spis-treści)</small>

---

## Sposób użycia

1.  Po uruchomieniu aplikacji, otwórz przeglądarkę i przejdź na adres `http://localhost:8080`.
2.  Aplikacja posiada dwa domyślne konta demonstracyjne z różnymi uprawnieniami:
  * **Administrator** (`admin@admin` | `Admin22*`) - może zarządzać produktami oraz użytkownikami.
  * **Użytkownik** (`user@user` | `User123*`) - może zarządzać wyłącznie produktami.
3.  Interaktywna dokumentacja API (Swagger UI) jest dostępna pod adresem: `http://localhost:8081/swagger-ui.html`.

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

## Licencja

Projekt jest udostępniony na licencji MIT. Zobacz plik [LICENSE](LICENSE), aby uzyskać więcej informacji.

<small>[Spis treści](#spis-treści)</small>

---

## Kontakt
**Paweł Bugiel**
* LinkedIn: [https://www.linkedin.com/in/pawel-bugiel-b936a1229](https://www.linkedin.com/in/pawel-bugiel-b936a1229)
* Email: [pawel.bugiel@outlook.com](mailto:pawel.bugiel@outlook.com)

<small>[Spis treści](#spis-treści)</small>

---
<p align="center"><a href="#waste-no-food">^ Wróć na górę ^</a></p>