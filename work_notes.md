# Spis Treści

* [BE-BUG-01-01-2025: Create product](#be-bug-01-01-2025-create-product)
* [BE-REFINE-01-01-2025: Search by partial name](#be-refine-01-01-2025-search-by-partial-name)
* [BE-REFINE-01-01-2025: Logging](#be-refine-01-01-2025-logging)
* [BE-BUG-01-01-2025: Error handling](#be-bug-01-01-2025-error-handling)
* [BE/FE-REFINE-01-01-2025: Domyślne sortowanie](#befe-refine-01-01-2025-domyślne-sortowanie)
* [BE-REFINE-01-01-2025: Documentation](#be-refine-01-01-2025-documentation)
* [BE-REFINE-01-01-2025: Trimming request values](#be-refine-01-01-2025-trimming-request-values)
* [BE-REFINE-SECURITY-11-05-2025: authStore.js](#be-refine-security-11-05-2025-authstorejs)
* [FE/BE-BUG-03-06-2025: Add new product](#febe-bug-03-06-2025-add-new-product)
* [FE/BE-FEATURE-11-06-2025: Product history](#febe-feature-11-06-2025-product-history)
* [FE-REFINE-26-07-2025: Exception message](#fe-refine-26-07-2025-exception-message)
* [FE-REFACTOR-03-08-2025: Extract reusable components](#fe-refactor-03-08-2025-extract-reusable-components)
* [FE-REFACTOR-03-08-2025: Centralize duplicated logic](#fe-refactor-03-08-2025-centralize-duplicated-logic)
* [BE-IMPROVE-03-08-2025: Handle potential race condition](#be-improve-03-08-2025-handle-potential-race-condition)
* [TYP-ZAKRES-DD-MM-RRRR: Krótki opis zadania](#typ-zakres-dd-mm-rrrr)

---

## BE-BUG-01-01-2025: Create product

* **create product** - nazwa: "A onion" nie działa (powiązane z aktualizacją).

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## BE-REFINE-01-01-2025: Search by partial name

* **Problem:** Frontend wysyła zapytanie do API po wciśnięciu każdego klawisza w polu wyszukiwania. Powoduje to nadmiarowe, niepotrzebne zapytania do bazy danych dla każdej wprowadzonej litery.
* **Rozwiązanie:** Należy zaimplementować **debouncing** po stronie frontendu. Technika ta opóźnia wykonanie funkcji (w tym przypadku wysłania zapytania) do momentu, aż upłynie określony czas bez jej ponownego wywołania (np. 300 ms po tym, jak użytkownik przestanie pisać). Znacząco zredukuje to obciążenie serwera i bazy danych.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

---

## BE-REFINE-01-01-2025: Logging

* **logging** - do pliku / konsoli.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## BE-BUG-01-01-2025: Error handling

* **error handling** - obsługa wyjątku braku odpowiedzi z BE.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## BE/FE-REFINE-01-01-2025: Domyślne sortowanie

* **default sorting** - w BE czy FE?

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## BE-REFINE-01-01-2025: Documentation

* **Documentation** - rozważ Javadoc, Springdoc OpenAPI Swagger.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## BE-REFINE-01-01-2025: Trimming request values

* **Trimming request values**.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

---

## BE-REFINE-SECURITY-11-05-2025: authStore.js

* **Bezpieczeństwo localStorage:** Przechowywanie tokenu w localStorage jest powszechne, ale naraża token na ataki XSS (Cross-Site Scripting). Jeśli atakujący wstrzyknie złośliwy skrypt, może odczytać token z localStorage.
  * **Alternatywa:** Przechowywanie tokenu w HttpOnly cookie, co zabezpiecza przed XSS, ale wymaga zmian w backendzie (ustawienie cookie przez serwer).
* **Walidacja tokenu:** Kod nie sprawdza ważności tokenu (np. czy nie wygasł). Jeśli token wygaśnie, żądania do API będą odrzucane (np. status 401 Unauthorized). Warto dodać logikę sprawdzającą `exp` (expiration) z dekodowanego tokenu i automatyczne wylogowanie użytkownika, jeśli token wygasł.
* **Domyślna rola:** Ustawianie "ENDUSER" jako domyślnej roli może być problematyczne, jeśli API wymaga konkretnej roli. Warto rozważyć wylogowanie użytkownika, jeśli rola nie jest dostępna.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

---

## FE/BE-BUG-03-06-2025: Add new product

* **Problem:** Nie można dodać produktu z polskimi znakami diakrytycznymi.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## FE/BE-FEATURE-11-06-2025: Product history

* **Tytuł:** Dodać logowanie historii zmian w liście produktów.
* Szczegóły: Kto, kiedy i co dokładnie zmienił (np. nazwa produktu, ilość, data ważności).

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## FE-REFINE-26-07-2025: Exception message

* **Problem:** Treść komunikatów o błędach pojawia się w złym miejscu, co powoduje "rozjeżdżanie się" interfejsu.
* **Pomysł:** Wyświetlać komunikaty o błędach w osobnym, niezależnym oknie (modal), aby nie wpływały na layout formularza i tabeli.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## FE-REFACTOR-03-08-2025: Extract reusable components

* **Tytuł:** Podział dużych komponentów na mniejsze, reużywalne części.
* **Problem:** Komponenty `ProductsDashboardView` i `UserManagementView` są zbyt duże i zawierają logikę dla formularzy, tabel i modali, co utrudnia ich utrzymanie.
* **Rozwiązanie:** Wydzielić powtarzalne elementy (np. `ProductForm.vue`, `UserTable.vue`, `AppModal.vue`) do osobnych komponentów, aby zwiększyć czytelność i reużywalność kodu.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## FE-REFACTOR-03-08-2025: Centralize duplicated logic

* **Tytuł:** Usunięcie duplikacji logiki paginacji, sortowania i wylogowywania.
* **Problem:** Metody takie jak `logout`, `nextPage`, `prevPage`, `sort` są powielone w kilku komponentach.
* **Rozwiązanie:** Stworzyć reużywalny "composable" (np. `useTableFeatures.js`) w Vue 3, który będzie zawierał logikę paginacji i sortowania. Przenieść logikę wylogowywania do jednego, centralnego miejsca (np. komponentu nagłówka).

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## BE-IMPROVE-03-08-2025: Handle potential race condition

* **Tytuł:** Zabezpieczenie operacji aktualizacji ilości produktu.
* **Problem:** Metoda `addQuantityToProduct` w serwisie jest podatna na błędy (race conditions), gdy dwóch użytkowników próbuje jednocześnie zaktualizować ten sam produkt.
* **Rozwiązanie:** Zmodyfikować metodę, aby wykonywała atomową operację `UPDATE` bezpośrednio na bazie danych, zamiast pobierać, modyfikować i zapisywać obiekt. To zadanie dla zaawansowanych, ale warto o nim pamiętać.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## TYP-ZAKRES-DD-MM-RRRR

* **Tytuł:** Tutaj wpisz pełny, opisowy tytuł zadania.
* Szczegółowy opis problemu lub nowej funkcjonalności.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*


TODO width BE
1. feature- logować kto wykonał daną akcję na produktach, później na użytkownikach
2. zapisywać usunięte przeterminowane produkty
3. zezwolić na edycję produktu przeterminowanego ?
4. Komentarze JavaDoc i inne.
5. zabezpieczenie przed usunięciem wszystkich użytkowników z rolą administrator ?

FE
1. feature - Dodać informację kto jest zalogowany ?
2. fix - dodanie produktu o takiej Samej nazwie i takiej samej dacie przydatności ale inne wielkości liter powoduje konflikt