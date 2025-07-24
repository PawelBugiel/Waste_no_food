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

**Waste no food** to aplikacja webowa typu full-stack, której głównym celem biznesowym jest **pomoc w monitorowaniu dat przydatności do spożycia produktów spożywczych** i minimalizowanie ich marnotrawstwa w gospodarstwach domowych.

Ten projekt to praktyczna demonstracja moich umiejętności Java Backend Developera w tworzeniu aplikacji po stronie serwera z użyciem **Java i Spring Boot**. Moja praca nad projektem obejmowała następujące obszary:

* **Implementacja REST API:** Budowa i ochrona endpointów z użyciem **Spring Security** i **JWT**.
* **Architektura backendu:** Zastosowanie architektury warstwowej i wzorca **DTO**
* **Osługa błędów:** Stworzenie mechanizmu obsługi wyjątków, który dostarcza frontendowi, pomocne komunikaty o błędach.
* **Modelowanie danych i integracja z bazą danych**: Zaprojektowanie encji JPA i zarządzanie trwałością danych w relacyjnej bazie MySQL.
* **TBD !! Zapewnienie jakości przez automatyczne testy** jednostkowe i integracyjne (JUnit, Mockito).
* **Środowisko deweloperskie:** Stworzenie środowiska uruchomieniowego za pomocą **Docker** i Docker Compose.
* **Pełna funkcjonalność:** Integracja z frontendem **(Vue.js)** w celu prezentacji działającego produktu.

<small>[Spis treści](#spis-treści)</small>

---

## Kluczowe Funkcjonalności

Aplikacja oferuje zestaw funkcji do zarządzania produktami, z podziałem na role użytkowników.

#### Dla wszystkich zalogowanych użytkowników:

* **Zarządzanie Produktami (CRUD):** Obsługa cyklu życia produktów, włączając w to ich tworzenie, edycję i usuwanie za pomocą intuicyjnego interfejsu.
* **Wyszukiwanie i Sortowanie**: Interaktywna lista produktów z możliwością wyszukiwania po fragmencie nazwy oraz sortowania po dowolnej kolumnie — wszystkie operacje wykonywane są po stronie serwera.
* **Paginacja po stronie serwera:** Przeglądanie listy produktów  oraz paginacji (następna/poprzednia strona).
* **Wizualne wskaźniki świeżości:** Automatyczne obliczanie dni do końca daty ważności i oznaczanie produktów kolorami (np. produkty bliskie przeterminowania, produkty przeterminowane) dla szybkiej identyfikacji.
* **Bezpieczne usuwanie:** Wyświetlanie modala z prośbą o potwierdzenie przed permanentnym usunięciem produktu.
* **Powiadomienia wizualne:** Automatyczne obliczanie dni do końca daty ważności i oznaczanie produktów kolorami (produkty przeterminowane lub bliskie terminu), co ułatwia priorytetyzację.


#### Dodatkowo dla Administratora:

* **Zarządzanie użytkownikami:** Dostęp do dedykowanego panelu umożliwiającego przeglądanie, tworzenie i usuwanie kont użytkowników oraz przypisywanie im ról.
* **Dostęp do dokumentacji API:** Możliwość przeglądania dokumentacji Swagger UI.

<small>[Spis treści](#spis-treści)</small>

---

## Zrzuty Ekranu

|                                                                                                                                                 Widok Logowania                                                                                                                                                 | Panel Administratora |
|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:| :---: |
|        <a href="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/LoginPage.png" target="_blank"><img src="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/LoginPage.png" alt="Widok Logowania" title="Kliknij, aby powiększyć" width="400"></a>        | <a href="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/AdminDashboard.png" target="_blank"><img src="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/AdminDashboard.png" alt="Panel Administratora" title="Kliknij, aby powiększyć" width="400"></a> |
|                                                                                                                                               **Panel Produktów**                                                                                                                                               | **Zarządzanie Użytkownikami** |
| <a href="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/ProductDashboard.png" target="_blank"><img src="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/ProductDashboard.png" alt="Panel Produktów" title="Kliknij, aby powiększyć" width="400"></a> | <a href="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/UserManagement.png" target="_blank"><img src="https://raw.githubusercontent.com/PawelBugiel/Waste_no_food/main/docs/images/UserManagement.png" alt="Zarządzanie Użytkownikami" title="Kliknij, aby powiększyć" width="400"></a> |

<small>[Spis treści](#spis-treści)</small>

---

## Stos Technologiczny

| Kategoria | Technologie                                                                               |
| :--- |:------------------------------------------------------------------------------------------|
| **Backend** | Java 17, Spring Boot, Spring Security, Spring Data JPA, Hibernate, MapStruct, Lombok, JWT |
| **Frontend** | Vue.js 3, Vue Router, Pinia, Bootstrap, Node.js                                           |
| **Baza Danych** | MySQL                                                                                     |
| **Testowanie** | JUnit 5, Mockito                                                                          |
| **DevOps & Narzędzia** | Docker, Docker Compose, Git, Maven, IntelliJ IDEA, Postman, OpenAPI (Swagger)             |

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

Lista planowanych i zrealizowanych zadań dla projektu:

* **Backend & Dane**
  * [x] Skonfigurowanie bazy danych (MySQL) i warstwy utrwalania danych (Spring Data JPA).
  * [x] Implementacja REST API dla operacji CRUD na produktach i użytkownikach. 
  * [x] Zabezpieczenie API za pomocą Spring Security i autoryzacji opartej na JWT.
  * [x] Zaimplementowanie centralnej obsługi wyjątków w oparciu o AOP (@RestControllerAdvice).
  * [ ] Pokrycie kodu backendu testami jednostkowymi i integracyjnymi.
  * [ ] Dodanie logowania i monitorowania (np. Logback/SLF4J).
  * [ ] Integracja z zewnętrznym API (**Open Food Facts API**) w celu automatycznego pobierania danych o produktach.

* **Frontend (UI)**
  * [x] Zbudowanie interfejsu użytkownika w technologii Vue.js (z Vue Router i Pinia).
  * [x] Widoki: logowania, panelu administratora, panelu produktów i panelu zarządzania użytkownikami.
  * [x] Dopracowanie User Experience (UX):
    * Ujednolicony wygląd i stylistyka kolorystyczna.
    * Blokowanie nieaktywnych pól i przycisków podczas edycji, aby zapobiec błędom.
    * Wizualne sygnały dla użytkownika (zmiana koloru i kursora dla nieaktywnych elementów).
    * Zaprojektowane ikony dla kluczowych akcji.
  * [ ] Obsługa błędów. 

* **DevOps & Deployment**
  * [x] Konteneryzacja aplikacji (Docker & Docker Compose):
    * Przygotowanie plików `Dockerfile` do budowania obrazów dla backendu i frontendu.
    * Stworzenie konfiguracji `docker-compose.yml` do orkiestracji usług.
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