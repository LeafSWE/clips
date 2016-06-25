# CLIPS: Communication & Localization with Indoor Positioning Systems
Clips è un'applicazione di tipo prototipale creata con l'intento di sperimentare scenari alternativi per la Navigazione indoor 
su smartphone facendo uso della tecnologia Beacon.

Il progetto è stato sviluppato dal team **Leaf** come progetto didattico proposto da Miriade S.p.A. per il corso di 
Ingegneria del software 2015/2016 della laurea triennale in Scienze Informatiche.




# Funzioni del prodotto
L'applicazione permette la navigazione all’interno di un edificio (supportato dall'app): l’utente sceglie
come destinazione un'area dell’edifico in cui si trova e riceve indicazioni utili
per poterla raggiungere.
Per identificare le varie aree che compongono un edificio, si utilizza la
dicitura Point of Interest (POI).
Dall’applicazione è inoltre possibile consultare informazioni di varia natura:
  - relative all’edificio nel suo complesso: nome, indirizzo, orari d’apertura, descrizione;
  - relative ai singoli POIg: nome, descrizione.
  
In entrambi i casi la descrizione viene messa a disposizione dal gestore dell’edificio
o del singolo POI, essa serve da presentazione e/o per fornire informazioni
aggiuntive a quelle già previste dall’applicazione (nome, indirizzo,
orari d’apertura).
Il prodotto mette a disposizione degli utenti in possesso di un codice
sviluppatore alcune funzionalità avanzate. Tali funzionalità permettono di
registrare i valori di: 
  - Universal Unique Identifier (UUID);
  - Major;
  - Minor;
  - livello di potenza del segnale;
  - livello della batteria;
  - distanza approssimativa del dispositivo dal beacon;
  - area coperta e formato dei beacon rilevati dal dispositivo.
I primi utilizzatori di queste funzionalità saranno i componenti
del gruppo Leaf che le adopereranno per creare l'infrastruttura beacon necessaria
al funzionamento dell’applicazione ed ottimizzarne l’interazione con
l’applicazione. Le funzionalità avanzate potranno poi essere messe a disposizione
di chiunque voglia contribuire al progetto CLIPS: sia per mappare
nuovi edifici, sia per manutenere l’infrastruttura dei beacon di edifici già
mappati.
