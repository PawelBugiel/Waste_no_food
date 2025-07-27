# Spis Treści


* [BE-BUG-01-01-2025: Update product (ponownie)](#be-bug-01-01-2025-update-product-ponownie)
* [BE-REFINE-01-01-2025: Logging](#be-refine-01-01-2025:-logging)
* [BE-BUG-01-01-2025: Error handling](#be-bug-01-01-2025-error-handling)
* [BE/FE-REFINE-01-01-2025: Domyślne sortowanie](#befe-refine-01-01-2025-domyślne-sortowanie)
* [BE-REFINE-01-01-2025: Documentation](#be-refine-01-01-2025-documentation)
* [BE-REFINE-01-01-2025: Trimming request values](#be-refine-01-01-2025-trimming-request-values)
* [BE-BUG-11-05-2025: Update product](#be-bug-11-05-2025-update-product)
* [BE-REFINE-SECURITY-11-05-2025: authStore.js](#be-refine-security-11-05-2025-authstorej)
* [BE-REFINE-SECURITY-28-05-2025: Database credentials](#be-refine-security-28-05-2025-database-credentials)
* [FE/BE-BUG-03-06-2025: Add new product](#febe-bug-03-06-2025-add-new-product)
* [FE/BE-FEATURE-11-06-2025: Product history](#febe-feature-11-06-2025-product-history)
* [FE-REFINE-26-07-2025: Exception message](#fe-refine-26-07-2025-exception-message)
* [FE-IMPROVE-27-07-2025: UPDATE EXISTING PRODUCT](#fe-improve-27-07-2025-update-existing-product)

---

## BE-BUG-01-01-2025: Create product

* **create product** - nazwa: "A onion" nie działa (powiązane z aktualizacją).

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## BE-REFINE-01-01-2025: Search by partial name

* **search by partial name** - BE wysyła zapytania do DB dla każdej wprowadzonej litery.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## BE-BUG-01-01-2025: Update product (ponownie)

* **update product** - nazwa: "A onion" działa (powiązane z tworzeniem).

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

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

## BE-BUG-11-05-2025: Update product

**HTTP Status 400 – Bad Request**
Test przez Postmana nie powiódł się: problem z konwersją wartości typu 'java.lang.String' na wymagany typ 'java.util.UUID'; Nieprawidłowy ciąg UUID: `<uuid>`.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## BE-REFINE-SECURITY-11-05-2025: authStore.js

* **Bezpieczeństwo localStorage:** Przechowywanie tokenu w localStorage jest powszechne, ale naraża token na ataki XSS (Cross-Site Scripting). Jeśli atakujący wstrzyknie złośliwy skrypt, może odczytać token z localStorage.
  * **Alternatywa:** Przechowywanie tokenu w HttpOnly cookie, co zabezpiecza przed XSS, ale wymaga zmian w backendzie (ustawienie cookie przez serwer).
* **Walidacja tokenu:** Kod nie sprawdza ważności tokenu (np. czy nie wygasł). Jeśli token wygaśnie, żądania do API będą odrzucane (np. status 401 Unauthorized). Warto dodać logikę sprawdzającą `exp` (expiration) z dekodowanego tokenu i automatyczne wylogowanie użytkownika, jeśli token wygasł.
* **Domyślna rola:** Ustawianie "ENDUSER" jako domyślnej roli może być problematyczne, jeśli API wymaga konkretnej roli. Warto rozważyć wylogowanie użytkownika, jeśli rola nie jest dostępna.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---

## BE-REFINE-SECURITY-28-05-2025: Database credentials

Dane dostępowe do bazy danych przenieść z pliku `application.properties` do **zmiennych środowiskowych**. Dzięki temu:
* Możliwa zmiana konfiguracji bez modyfikacji kodu i przebudowywania obrazu Dockera.
* Przechowywanie danych uwierzytelniających poza kodem źródłowym.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*
## FE/BE-BUG-03-06-2025: Add new product

Nie można dodać produktu z polskimi znakami diakrytycznymi.

*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

--- 

## FE/BE-FEATURE-11-06-2025: Product history

Dodać logowanie historii zmian w liście produktów: 
kto coś zmienił 
kiedy - data i czas
szczególy zmian - nazwa produktu, nowa nazwa produktu itp. 


*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---
## FE-REFINE-26-07-2025: Exception message

treść exceptionów pojawia się w złym miejscu 
bo przesuwa tabelę z rekordami wynikowymi w dół. 
Trzeba Yyy zrobić to w inny sposób aby niezależnie od długości zwrócone wartości błędu nie powodowało to rozjeżdżania się formularza pomysł mam taki żeby użyć modela który będzie tak naprawdę niezależny nowym oknem. 


*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*

---
## FE-27-07-2025: UPDATE EXISTING PRODUCT

Jeśli produkt istnieje ( nazwa i data przydatności ) to backend wyrzuca konflikt. Przerobić frontend aby w takiej sytuacji pytał czy zaktualizować ilość produktów . Po to aby uprościć procedurę aktualizacji ilości istniejącego produktu.


*<sub>[Wróć do Spisu Treści](#spis-treści)</sub>*