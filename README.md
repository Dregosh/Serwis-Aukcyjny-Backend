Aplikacja "Serwis Aukcyjny" (backend REST API)

Projekt grupowy wykonany na zakończenie kursu Java w Software Development Academy. Autorzy projektu (
kolejność alfabetyczna): Całka Mateusz, Piękoś Rafał, Sadzyński Marek, Żmijewski Adam

Stack technologiczny:
Java 11, Spring (Spring Boot, Spring Data JPA, Spring Security, Spring Mail), KeyCloak, MySQL, Flyway.
(Frontend UI wykonany w Angularze, który konsumuje to API jest dostępny w oddzielnym repozytorium)

Struktura: 
Domain-driven Design.

Wzorzec:
Adaptacja CQRS (Command Query Responsibility Segregation). Wszelkie operacje zmieniające stan odbywają
się za pośrednictwem dedykowanych obiektów Poleceń (Command), natomiast operacje pobierające dane (Query)
obsługiwane są bezpośrednio w serwisach.

Zabezpieczenia:
Konta użytkowników przechowywane są w oddzielnej bazie KeyCloak (hasła szyfrowane), a komunikacja
zalogowanego użytkownika na linii frontend-backend uwierzytelniana jest za pomocą tokena JWT.

Kontenery Docker:
Baza danych SQL aplikacji, serwer Keycloak oraz baza SQL danych KeyCloak umieszczone zostały w
kontenerach Dockera (konfig: "docker-compose up -d").

Krótki opis zawartych funkcjonalności:
API udostępnia prezentację i możliwość filtrowania aukcji dla wszystkich odwiedzających
serwis. Ponadto użytkownicy, którzy założyli konto i potwierdzili swój adres e-mail, po zalogowaniu
otrzymują dodatkowe możliwości: mogą zakładać nowe aukcje, brać udział w licytacjach, korzystać z opcji "
Kup Teraz", a także wystawiać komentarze do przeprowadzanych transakcji.
