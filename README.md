# PAP2023L-ZJK1


<h2>Zespół:</h2>

- Mikołaj Chomanski
- Adam Czubak
- Władysław Młynik
- Jakub Kierasiński

<h2>Pomysł</h2>
Aplikacja Desktopowa do wspólnego odtwarzania muzyki.

<h2>Technologie</h2>

- GUI: Java + swing
- Server: Java
- Bazadanych: mySQL na własnym serwerze, Pliki muzyki będziemy przechowywać w tradycyjny sposób, a w bazie danych będziemy przechowywać ścieżkę do plików


<h2>Zaimplementowane Funkcjonalności</h2>

- tworzenie konta
- logowanie się z dowolnego komputer
- zmiana hasła
- dodawanie awatara
- odzyskiwanie hasła przy pomocy maila
- zmiana nazwy użytkownika
- zmiana kolorystyki na ciemny lub jasny


- wysyłanie wiadomości tekstowych
- wysyłanie zdjęć
- tworzenie grup
- dodawanie osób do grup
- kody do grup zależne od id grupy


- streamowanie muzyki z serweru
- zsynchronizowane streamowanie muzyki


- wgrywanie muzyki
- usuwanie muzyki na stałe
- przechowywanie muzyki


<h2>Sposób uruchomienia</h2>
Repozytorium jest podzielone na dwie częśc, jedną z kodem do aplikacji klienta, a druga część zawiera kod do aplikacji po stronie serwera.
Aby uruchomić program można wejść do pliku Main.java i uruchomić metodę main tej klasy. Można również spakować pakiet przy użyciu mavena i uruchomić tak otrzymany pakiet jar.

Należy uważać czy serwer jest aktualnie aktywny, jeżeli nie jest można pobrać przykładowy plik sql i stworzyć własną bazę danych na swoim komputerze lub srwerze. Wtedy warto też zmienić nazwę i hasło do bazy danych w pliku DatabaseInformation.java. Aby uruchomic serwer należy wejść do pliku Main i uruchomić metodę main tej klasy. Druga opcja to spakowanie serwera do pakietu i uruchomienie pliku jar.
Po pierwsze trzeba uważać czy serwer jest aktualnie aktywny. Jeżeli nie jest aktywny, to w sekcji serwer repozytorium znajduje się cały kod serwera. Wtedy również należy zmienić ip po stronie klienta na ip serwera.
