drop database if exists Game2Store;
create database Game2Store;
use Game2Store;

create table Utente(
	email varchar(30) primary key,
    password text not null,
    username varchar(25) unique not null,
    nome varchar(30) not null,
    cognome varchar(20) not null,
    ddn date not null,
    nazione varchar(15) not null,
    ammin boolean not null
);


create table Gioco(
	titolo varchar(20) primary key,
    genere varchar(15) not null,
    offerta boolean not null,
    prezzo double not null,
    dataUscita date not null,
    contatoreRilevanze integer not null,
	descrizione text not null
);


create table ImagesGioco(
	id integer auto_increment primary key,
	titoloGioco varchar(20),
    pathImages text,
    foreign key(titoloGioco) references Gioco(titolo) on update cascade on delete cascade
);
    

create table CartaCredito(
    numeroCarta char(19),
    via varchar(30) not null,
    cap int(5) not null,
    citta varchar(20) not null,
    emailUtente varchar(30) not null,
    circuito varchar(20) not null,
    dataScadenza char(8) not null,
    foreign key (emailUtente) references Utente(email) on update cascade on delete cascade,
    primary Key(numeroCarta, emailUtente)
);

create table Carrello(
    id integer primary key auto_increment,
    totaleCarrello double not null,
    emailUtente varchar(30) not null,
	foreign key (emailUtente) references Utente(email) on update cascade on delete cascade
);	

create table RegistrareGiocoCarrello(
	titoloGioco varchar(20),
    idCarrello integer,
    primary key(titoloGioco, idCarrello),
    foreign key (titoloGioco) references Gioco(titolo) on update cascade on delete cascade,
    foreign key (idCarrello) references Carrello(id) on update cascade on delete cascade
);

create table Ordine(
    id integer primary key auto_increment,
    dataAcquisto date not null,
    totaleOrdine double not null,
    emailUtente varchar(30) not null,
    numeroCarta char(19),
    foreign key (emailUtente) references Utente(email) on update cascade on delete cascade,
    foreign key (numeroCarta) references CartaCredito(numeroCarta) on update cascade on delete cascade
);

create table AcquistoGiocoOrdine(
    idOrdine integer,
    titoloGioco varchar(20),
    prezzoAcquisto double not null,
    primary key(idOrdine, titoloGioco),
    foreign key(idOrdine) references Ordine(id) on update cascade on delete cascade,
    foreign key(titoloGioco) references Gioco(titolo) on update cascade on delete cascade
);

Insert into Gioco VALUES 
("God of War", "azione", true, 49.99, "2018-04-20", 0, "God of War per PC è stato anticipato per mesi, da circa metà 2021, fino a quando gli sviluppatori hanno finalmente confermato l'uscita per PC nel mese di gennaio 2022! È la versione per PC di God of War del 2018, inizialmente disponibile solo su PlayStation. Il gioco consente ai giocatori PC di godersi l'intenso gioco di azione e avventura di Norse God. Il gioco è l'ottavo dell'intera serie multipiattaforma e si svolge in uno sfondo che, sebbene non sia un vero mondo aperto e più una serie di luoghi collegati, è comunque ampio ed esplorabile all'interno. Raramente ti sentirai costretto di fare determinate cose, nonostante la progressione lineare del gioco."),
("Tetris", "strategia", false, 9.99, "1984-06-06", 12, "Due leggende si uniscono per creare il miglior gioco di rompicapo Puyo Puyo, la serie di rompicapo famosissima in Giappone, e il celebre Tetris tornano insieme per far esplodere ancora più puyo ed eliminare sempre più tetrimini."),
("Pac-Man", "strategia", false, 10.99, "1980-05-22", 11, "Preparati a nuove sfide! Sei pronto a ingurgitare schiere di fantasmi e all'azione frenetica di PAC-MAN? L'acclamato gioco arcade debutta alla grande su Game2Store con una mole impressionante di nuovi contenuti e un'interfaccia migliorata per confrontare i punteggi con gli amici! Attira i fantasmi, mettili tutti in fila dietro di te e infine divorali a incredibile velocità per battere ogni record! E trova sempre una via d'uscita, o dovrai farti strada a suon di bombe! Una nuova esperienza di PAC-MAN che ti lascerà a bocca aperta! Pronto a dominare le strade? Mettiti al volante di auto leggendarie e sfreccia per Ventura Bay, un campo giochi urbano senza fine. Esplora l'intreccio di storie mentre ti fai una reputazione, crei l'auto dei tuoi sogni e diventi l'icona definitiva delle corse. Gioca e rigioca, perché questa volta hai 5 modi diversi di vincere."),
("Minecraft", "avventura", true, 18.99, "2011-11-18", 7, "Minecraft per PC è il videogioco più venduto di tutti i tempi. Questo da solo dovrebbe essere sufficiente per farlo comprare, ma ecco qualche informazione in più sul perché dovresti iniziare a giocare ora. È un gioco sandbox in cui i giocatori devono scavare, costruire e creare il loro mondo ideale"),
("Gta5", "azione", true, 39.99, "2013-09-17", 6, "Grand Theft Auto V per PC è un gioco di avventura e azione, il quinto della serie GTA. Come con gli altri giochi della serie, ottieni punti commettendo crimini. Il gioco alterna una narrazione in terza persona e un gioco in prima persona, e tu giochi come tre criminali che cercano di evitare una determinata agenzia governativa e commettere rapine. Non dovrai scegliere un solo personaggio con cui giocare, la narrazione del gioco passa da un personaggio all'altro."),
("Need for Speed", "corse", false, 29.99, "2020-01-01", 4, "Pronto a dominare le strade? Mettiti al volante di auto leggendarie e sfreccia per Ventura Bay, un campo giochi urbano senza fine. Esplora l'intreccio di storie mentre ti fai una reputazione, crei l'auto dei tuoi sogni e diventi l'icona definitiva delle corse. Gioca e rigioca, perché questa volta hai 5 modi diversi di vincere."),
("Horizon", "avventura", false, 9.99, "2017-02-28", 3, "Horizon Zero Dawn per PC è un gioco di ruolo d'azione in cui controllerai Aloy, una sopravvissuta umana nel panorama distopico del 31° secolo. Gli esseri umani possono essere rari e l'accesso alla tecnologia è limitato, ma c'è una grande popolazione di creature robotiche ostili (che furono originariamente create da Big Tech che poi ne perse il controllo)."),
("Fifa22", "sport", true, 29.99, "2021-09-27", 7, "FIFA 22 per PC è la trentesima uscita della serie della popolarissima serie di videogiochi di simulazione di calcio. Il gioco segue gli alti e bassi della vita reale dei giocatori e delle squadre di tutto il mondo, utilizzando e combinando il motion capture ad una grafica all'avanguardia, con motori fisici che ti lasciano provare le vere emozioni del campo."),
("F12022", "corse", false, 59.99, "2022-05-01", 2, "F1 22 per PC è il ventitreesimo, e si dice che sia l'ultimo, del franchise dell'amato gioco di corse con licenza. Unisciti ai piloti e alle auto di tutto il mondo per competere su alcuni dei circuiti più amati al mondo. Sembra che il gioco tornerà in qualche modo, ma senza la licenza ufficiale di F1, ma in questa fase i dettagli sono ancora imprecisi."),
("Elden Ring", "azione", false, 59.99, "2022-02-25", 16, "Elden Ring per PC è un gioco di ruolo d'azione (ARPG) scritto dalle superstar George RR Martin (l'autore della serie di libri Le Cronache del Ghiaccio e del Fuoco che ha dato origine alla serie televisiva Game of Thrones) e Hidetaka Miyazake (famoso per molti popolari videogiochi: dalla serie Souls, a Bloodborne, a Sekiro).");


Insert into ImagesGioco (titoloGioco, pathImages) values 
("God of War", "images\\GodOFWar\\copertina.jpg"),
("Tetris", "images\\Tetris\\copertina.jpg"),
("Pac-Man", "images\\Pac-Man\\copertina.jpg"),
("Minecraft", "images\\Minecraft\\copertina.jpg"),
("Gta5", "images\\GTA5\\copertina.jpg"),
("Horizon", "images\\Horizon\\copertina.jpg"),
("Fifa22", "images\\FIFA22\\copertina.jpg"),
("F12022", "images\\F12022\\copertina.jpg"),
("Elden Ring", "images\\EldenRing\\copertina.jpg"),
("Need for Speed", "images\\NeedForSpeed\\copertina.jpg"),
("Tetris", "images\\Tetris\\screen1.jpg"),
("Tetris", "images\\Tetris\\screen2.jpg"),
("Tetris", "images\\Tetris\\screen3.jpg"),
("Tetris", "images\\Tetris\\screen4.jpg"),
("Pac-Man", "images\\Pac-Man\\screen1.jpg"),
("Pac-Man", "images\\Pac-Man\\screen2.jpg"),
("Pac-Man", "images\\Pac-Man\\screen3.jpg"),
("Pac-Man", "images\\Pac-Man\\screen4.jpg"),
("Need for Speed", "images\\NeedForSpeed\\screen1.jpg"),
("Need for Speed", "images\\NeedForSpeed\\screen2.jpg"),
("Need for Speed", "images\\NeedForSpeed\\screen3.jpg"),
("Need for Speed", "images\\NeedForSpeed\\screen4.jpg"),
("Minecraft", "images\\Minecraft\\screen1.jpg"),
("Minecraft", "images\\Minecraft\\screen2.jpg"),
("Minecraft", "images\\Minecraft\\screen3.jpg"),
("Minecraft", "images\\Minecraft\\screen4.jpg"),
("Horizon", "images\\Horizon\\screen1.jpg"),
("Horizon", "images\\Horizon\\screen2.jpg"),
("Horizon", "images\\Horizon\\screen3.jpg"),
("Horizon", "images\\Horizon\\screen4.jpg"),
("GTA5", "images\\GTA5\\screen1.jpg"),
("GTA5", "images\\GTA5\\screen2.jpg"),
("GTA5", "images\\GTA5\\screen3.jpg"),
("GTA5", "images\\GTA5\\screen4.jpg"),
("God of War", "images\\GodOFWar\\screen1.jpg"),
("God of War", "images\\GodOFWar\\screen2.jpg"),
("God of War", "images\\GodOFWar\\screen3.jpg"),
("God of War", "images\\GodOFWar\\screen4.jpg"),
("Fifa22", "images\\FIFA22\\screen1.jpg"),
("Fifa22", "images\\FIFA22\\screen2.jpg"),
("Fifa22", "images\\FIFA22\\screen3.jpg"),
("Fifa22", "images\\FIFA22\\screen4.jpg"),
("F12022", "images\\F12022\\screen1.jpg"),
("F12022", "images\\F12022\\screen2.jpg"),
("F12022", "images\\F12022\\screen3.jpg"),
("F12022", "images\\F12022\\screen4.jpg"),
("Elden Ring", "images\\EldenRing\\screen1.jpg"),
("Elden Ring", "images\\EldenRing\\screen2.jpg"),
("Elden Ring", "images\\EldenRing\\screen3.jpg"),
("Elden Ring", "images\\EldenRing\\screen4.jpg");

