# RPG Task Planner
Projekt aplikacji przygotowany na zaliczenie przedmiotu Aplikacje Mobilne.

Jest to aplikacja pozwalająca na planowanie swoich codziennych zadań w niecodzienny sposób - w duchu zadań z gier RPG. Aplikacja pozwala na stworzenie 
swojego bohatera, którego rozwijamy poprzez wykonywanie zadań w świecie rzeczywistym. Tutaj każde zadanie wymaga określenia trudności, ze wzrostem
której rosną otrzymywane za zadania nagrody, ale rośnie też minimalna ilość czasu, jaką na te zadanie trzeba poświęcić. Progres postaci polega na kupowaniu nowych 
przedmiotów ze sklepu, które zwiększają statystyki bohatera, a ich siła rośnie wraz z poziomem bohatera.  


## Opis funkcjonalności:

### 1. Logowanie i rejestracja
Logowanie i rejestracja zostało zaimplementowane w oparciu o integracje z chmurą Firebase. Aplikacja umożliwia logowanie za pomocą maila i hasła oraz za pomocą Google.
Po skutecznym zalogowaniu i restarcie aplikacji użytkownik pozostaje zalogowany, aż do momentu ręcznego wylogowania.  

### 2. Pierwsze logowanie
Po pierwszym logowaniu użytkownik jest proszony o utworzenie swojego bohatera.
Do wyboru są 3 klasy: Wojownik, Łotr oraz Mag, które różnią się wyglądem, odnawialną statystyką oraz dostępnymi przedmiotami w sklepie.
Po stworzeniu bohatera wszystkie niezbędne informacje zostają zapisane w zewnętrznej bazie Firestore.

### 3. Ekran główny
Po zalogowaniu użytkownik trafia do ekranu głównego. Z tego poziomu może wybrać jeden z dostępnych modułów, które zostaną opisane niżej.
Dodatkowo, w celu nawigowania po aplikacji może się posłużyć szufladką nawigacyjną.

### 4. Moduł Zadania
W tym ekranie użytkownik widzi listę zadań do wykonania, ilość posiadanych zasobów oraz może dodać nowe zadanie.

- Dodanie nowego zadania polega na wprowadzeniu jego nazwy (nie może się powtarzać z już istniejącym zadaniem) oraz przewidywanych kosztów w zasobach. Podczas tego w sposób dynamiczny 
	  aktualizowane i wyświetane są trudność zadania, przewidywane nagrody (złoto, doświadczenie) oraz przewidywany czas zakończenia.
- Po kliknięciu na zadanie z listy zadań wyświetlane są jego szczegóły takie jak przewidywane nagrody, data rozpoczęcia / najwcześniejszego zakończenia, trudność i stan. Ekran ten umożliwia też zaliczenie zadania, ale tylko jeśli aktualny czas jest już później niż najwcześniejszy czas wykonania. Po zaliczeniu zadania bohaterowi przydzielane są nagrody.
- Zasoby w ekranie z listą odnawiają się co godzinę o wartość zależną od statystyk bohatera. Jeśli aplikacja nie była uruchamiana przez więcej niż godzinę to odnowienie nastąpi wielokrotnie, poprawną liczbę razy. Po kliknięciu w zasób wyświetla sie informacja o posiadanym zasobie i następnym odnowieniu
	  
### 5. Moduł Sklep
- Sklep umożliwia wydawanie zarobionego złota na przedmioty, które ulepszają statystyki bohatera. W aplikacji jest 8 typów przedmiotów (hełm, zbroja, broń, druga ręka, pas, pierścień, artefakt i buty), każdy z nich wzmacnia inne statystyki.
- Po wejściu do sklepu pojawia się lista dostępnych przedmiotów, która odnawia się codziennie o godz. 00.00 - przedmioty są wtedy losowane z zewnętrznej bazy danych Firestore,
a następnie zapisywane w lokalnej bazie jako dostępne w sklepie. Dzięki temu różni użytkownicy mogą mieć różne przedmioty w sklepach. W sklepie zawsze zostaje umieszczone po jednym przedmiocie z każdego typu.
- W przyszłości planowanie jest wprowadzenie drobnej losowości do wartości statystyk przedmiotów.
- Po kliknięciu na przedmiot w sklepie trafia on do "koszyka" tzn. jego szczegóły są wyświetlane na dole ekranu i jest on wtedy możliwy do kupienia. Ekran szczegółów zapewnia też porównanie z ulepszaną statystyką obecnie założonego przedmiotu w celu porównania jakości przed zakupem.
- Po kliknięciu przycisku "kup" przedmiot trafia do ekwipunku bohatera i może poźniej zostać wyekwipowany.
- Po przytrzymaniu na przedmiocie wyświetla się ekran detali szczegółów, gdzie użytkownik może dodatkowo przeczytać jego opis.
- Jeśli przedmiot, który znajduje się w sklepie jest obecnie wyekwipowany przez bohatera, to jego nazwa zmienia kolor na złoty.
- Statystyki przedmiotów w sklepie skalują się z poziomem bohatera. Każdy przedmiot ma swój unikalny splash art.

### 6. Moduł Postać
Ekran postaci odpowiada za zarządzanie ekwipunkiem oraz umożliwia wgląd w statystyki.<br>
Ekran ekwipunku pozwala na wybranie interesującej użytkownika części ekwipunku z graficznej reprezentacji ekwipunku (aktualnie wyekwipowane części są wyświetlane, pozostałe są puste), a następnie na wybór z listy dostępnych przedmiotów tej kategorii.<br>Po wybraniu konkretnego przedmiotu uruchamia się ekran detali przedmiotu, gdzie można wyekwipować dany przedmiot. Wyekwipowanie przedmiotu wiąże się ze wzrostem odpowiedniej statystyki bohatera
jak zostało to wyjaśnione niżej:

- hełm - max zasób* - maksymalna wartość zasobu, który jest wydawany w celu osiągnięcia lepszych nagród za zadania
- zbroja - max zdrowie - maksymalna wartość zdrowia, które jest wydawane w celu osiągnięcia lepszych nagród za zadania
- broń - czas odnowienia - skraca czas potrzebny do wykonania zadań o poszczególnych stopniach trudności
- druga ręka, pas - mnożnik złota - zwiększa otrzymywaną ilość złota za ukończenie zadania
- pierścień - regeneracja zdrowia - zwiększa ilość regenerowanego zdrowia z każdym cyklem regeneracji
- artefakt - regeneracja zasobu* - zwiększa ilość regenerowanego zasobu z każdym cyklem regeneracji 
- buty - mnożnik doświadczenia - zwiększa otrzymywaną ilość doświadczenia za ukończenie zadania

*w zależności od klasy zasób jest czymś innym:
- wojownik - energia
- łotr - zwinność
- mag - mana

W ekranie statystyk użytkownik widzi dokładny wgląd we wszystkie statystyki postaci. Dodatkowo z tego poziomu dostępny jest wgląd w obecny poziom postaci, obecnie doświadczenie oraz doświadczenie
potrzebne do kolejnego poziomu. Poza tym znajduje się tu również podsumowanie wykonanych zadań w postaci osiągnięć za wykonane zadania poszczególnych trudności (łatwe, średnie, trudne)

### 7. Moduł Udostępnij
Wybranie tego modułu otwiera przeglądarkę i pozwala udostępnić swój progres w postaci Tweeta w serwise X.

### 8. Wyloguj się 
Pozwala na wylogowanie z aplikacji i zalogowanie się na innym koncie.


## Sposób zarządzania danymi:
Aplikacja przechowuje dane na 3 sposoby:
- zewnętrzna baza danych - Firestore
- lokalna baza danych - SQLite z biblioteką Room
- pamięć podręczna - obiekty singleton

### Firestore
W zewn. bazie danych przechowywane są informacje o użytkownikach, bohaterach, ekwipunkach i zadaniach bohaterów oraz są w niej składowane "bazowe podstawy" przedmiotów, które po trafienu 
do sklepu użytkownika są odpowiednio ulepszane w zależności od jego poziomu.
Dostęp do tych struktur z poziomu kodu odbywa się za pomocą klas typu Repository, które zawierają gotowe zapytania do bazy danych.

### SQLite
Lokalna baza danych jest wykorzystywana w celu przechowania informacji o zawartości sklepu. Znajdują się w niej przedmioty już po uwzględnieniu poziomu bohatera. Codziennie o 00.00 (lub po ponownym uruchomieniu aplikacji po tym terminie) trafiają do niej nowe przedmioty losowane z zewnętrznej bazy danych. Od strony technicznej dostęp do bazy jest zrealizowany z pomocą biblioteki Room.

### Pamięć podręczna
W pamięci podręcznej są przechowywane najpotrzebniejsze informacje, do których aplikacja często wymaga dostępu, takie jak obecnie zalogowany użytkownik, bohater czy też jego ekwipunek.
Takie podejście redukuje liczbę wymaganych dostępów do bazy danych. Jednakże, gdy zostaje coś zmienione w pamięci podręcznej, to zostało zadbane o to, aby te zmiany pojawiły się również w kopii tych danych
w bazie danych.


## Plany na rozbudowę:
- losowe wartości statystyk przedmiotów
- opisy zadań
- możliwość podziału zadania na podzadania
- więcej dostępnych przedmiotów
- integracja z innymi mediami społecznościowymi
