Aplikacja "Serwis Aukcyjny" (backend REST API)

Projekt grupowy wykonany na zakończenie kursu Java w Software Development Academy.
Autorzy projektu (
kolejność alfabetyczna): Całka Mateusz, Piękoś Rafał, Sadzyński Marek, Żmijewski Adam

Stack technologiczny:
Java 11, Spring (Spring Boot, Spring Data JPA, Spring Security, Spring Mail),
KeyCloak, MySQL, Flyway.
(Frontend UI wykonany w Angularze, który konsumuje to API jest dostępny w oddzielnym
repozytorium)

Struktura:
Domain-driven Design.

Wzorzec:
Adaptacja CQRS (Command Query Responsibility Segregation). Wszelkie operacje
zmieniające stan odbywają się za pośrednictwem dedykowanych obiektów Poleceń (
Command), natomiast operacje pobierające dane (Query)
obsługiwane są bezpośrednio w serwisach.

Zabezpieczenia:
Konta użytkowników przechowywane są w oddzielnej bazie KeyCloak (hasła szyfrowane), a
komunikacja zalogowanego użytkownika na linii frontend-backend uwierzytelniana jest
za pomocą tokena JWT.

Kontenery Docker:
Baza danych SQL aplikacji, serwer Keycloak oraz baza SQL danych KeyCloak umieszczone
zostały w kontenerach Dockera (konfig: "docker-compose up -d").

Krótki opis zawartych funkcjonalności:
API udostępnia prezentację i możliwość filtrowania aukcji dla wszystkich
odwiedzających serwis. Ponadto użytkownicy, którzy założyli konto i potwierdzili swój
adres e-mail, po zalogowaniu otrzymują dodatkowe możliwości: mogą zakładać nowe
aukcje, brać udział w licytacjach, korzystać z opcji "
Kup Teraz", a także wystawiać komentarze do przeprowadzanych transakcji.
<br><br><br>
** INSTRUKCJA URUCHOMIENIA **

1) Niniejsze repozytorium należy sklonować do własnego IDE (np Intellij IDEA).
2) W katalogu głównym aplikacji należy wpisać polecenie:
   <br><br>
   docker-compose up -d
   <br><br>
   Skonfiguruje ono 3 kontenery Dockera. W dwóch znajdują się bazy danych MySQL
   (jedna dla naszej aplikacji, druga dla Keycloak). W trzecim kontenerze znajduje
   się sam Keycloak.

WAŻNE: Może zdarzyć się tak, że kontener Keycloak wystartuje zanim jego baza MySQL
będzie gotowa i wówczas będzie to objawiało się jego ciągłym resetowaniem. W takiej
sytuacji należy po prostu <strong>usunąć</strong> ten jeden kontener ("
jboss/keycloak"). Następnie przy działających obu kontenerach MySQL ponowne wpisanie
komendy "docker-compose up -d" poprawnie skonfiguruje  brakujący kontener Keycloak.

Aby upewnić się, że kontener Keycloak działa poprawnie należy w przeglądarce przejść
pod adres http://localhost:8000 i sprawdzić czy wyświetla się jego strona powitalna (
pierwsze wyświetlenie trwa dłuższą chwilę).

3) W środowisku IDE należy skompilować i uruchomić aplikację. REST API będzie
   dostępne pod adresem http://localhost:8080 i gotowe do konsumpcji dla frontendu.

4) Teraz można przejść do uruchomienia frontendowej części aplikacji z repozytorium
   "Serwis-Aukcyjny-frontend" (instrukcja jest dostępna w pliku readme tamtego
   projektu).

** KONIEC INSTRUKCJI **
