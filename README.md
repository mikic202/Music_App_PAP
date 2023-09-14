# PAP MUsic App
This is project of music app calle "Connected by Music" created during 2023L semestrer of PAP cours at Warsaw University of Technology. This project was a colaboration between four people. This project was developed on gitlab of our faculty and only after finishing it it was moved to GitHub.


## Colaborators:

|Contributors| Git nicknames|
|------------|:--------------:|
|Mikołaj Chomanski| mchomans, [mikic202](https://github.com/mikic202)|
|Władysław Młynik | wmlynik|
|Adam Czubak| aczubak |
|Jakub Kierasiński| jkierasi |

## Technologies
- GUI: Java + swing
- Server: Java
- Database: mySQL

<h2>Pomysł</h2>
Aplikacja Desktopowa do wspólnego odtwarzania muzyki.


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
