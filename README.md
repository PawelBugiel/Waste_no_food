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

Projekt jest moją piaskownicą deweloperską, w której rozwijam i demonstruję praktyczne zastosowanie poniższych koncepcji technicznych:

* **Architektura Full-Stack:** Integracja backendu w technologii Spring Boot z frontendem opartym na Vue.js.
* **Bezpieczeństwo API:** Zabezpieczenie dostępu do zasobów REST API w oparciu o role przy użyciu Spring Security i tokenów JWT.
* **Wzorce i Architektura:** Zastosowanie architektury warstwowej oraz wzorca DTO w celu zwiększenia modułowości i separacji zagadnień (SoC).
* **Testowanie Aplikacji:** Weryfikacja logiki biznesowej za pomocą testów jednostkowych i integracyjnych (JUnit, Mockito).
* **Zautomatyzowane Środowisko (Docker):** Użycie Docker Compose do orkiestracji usług aplikacji (backend, frontend, baza danych), włączając w to konfigurację sieci, wolumenów oraz kontrolę kolejności uruchamiania za pomocą mechanizmu `healthcheck`.

<small>[Spis treści](#spis-treści)</small>

---

## Kluczowe Funkcjonalności

Aplikacja oferuje bogaty zestaw funkcji do zarządzania produktami, z podziałem na role użytkowników.

#### Dla wszystkich zalogowanych użytkowników:

* **Zarządzanie Produktami (CRUD):** Pełna obsługa cyklu życia produktów, włączając w to ich tworzenie, edycję i usuwanie z użyciem interaktywnego formularza.
* **Wyszukiwanie po częściowej nazwie:** Dynamiczne filtrowanie listy produktów w czasie rzeczywistym po wpisaniu fragmentu nazwy.
* **Sortowanie i Paginacja po stronie serwera:** Wydajne przeglądanie dużej liczby produktów dzięki dynamicznemu sortowaniu po kliknięciu w nagłówek kolumny oraz paginacji (następna/poprzednia strona).
* **Wizualne wskaźniki świeżości:** Automatyczne obliczanie dni do końca daty ważności i oznaczanie produktów kolorami (np. produkty bliskie przeterminowania, produkty przeterminowane) dla szybkiej identyfikacji.
* **Bezpieczne usuwanie:** Wyświetlanie modala z prośbą o potwierdzenie przed permanentnym usunięciem produktu.

#### Dodatkowo dla Administratora:

* **Zarządzanie użytkownikami:** Dostęp do dedykowanego panelu umożliwiającego przeglądanie, tworzenie i usuwanie kont użytkowników oraz przypisywanie im ról.
* **Dostęp do dokumentacji API:** Możliwość przeglądania interaktywnej dokumentacji Swagger UI.

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
git clone https://github.com/pawelbugiel/Waste_no_food.git

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
  * [ ] Dodanie logowania i monitorowania (np. Logback/SLF4J).

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