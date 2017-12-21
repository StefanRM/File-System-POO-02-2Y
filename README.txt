  _______                     ___  
 |__   __|                   |__ \ 
    | | ___ _ __ ___   __ _     ) |
    | |/ _ \ '_ ` _ \ / _` |   / / 
    | |  __/ | | | | | (_| |  / /_ 
    |_|\___|_| |_| |_|\__,_| |____|
                                   
Nume:    Maftei Stefan - Radu
Grupa:   324CD
Materie: POO

	In implementarea temei am folosit doua design patterns: Factory Pattern si
Composite Pattern.
	Factory Pattern l-am utilizat la generarea comenzilor, fiind un mod potrivit
pentru a verifica si a instantia o comanda, folosind o interfata comuna pentru toate
comenzile. Toate comenzile mostenesc o clasa abstracta "Commands" in care se afla
functiile de executare si de obtinere a comenzii. In clasa "CommandsFactory" toate
comenzile sunt identificate prin tipul "Commands" si in momentul returnarii se face
downcast la comanda respectiva.
	Tot in clasa "Commands" am implementat functiile:
1) checkPathGetDirectory()
	- foloseste o cale absoluta pentru a cauta un director;
	- verifica daca utilizatorul curent are dreptul de a intra (executa) in
directoarele din path;
	- returneaza directorul daca a fost gasit sau null, in caz contrar.

2) createNewPath()
	- parcurge de la un director dat calea inspre radacina, folosindu-se de
parintii fiecarui director (parintele radacinii root este null);
	- returneaza calea de la radacina la directorul dat.

3) processPathArgument()
	- transforma o cale relativa intr-o cale absoluta; daca primeste o cale
absoluta ea ramane aceeasi;
	- ataseaza caii relative calea curenta;
	- returneaza calea absoluta obtinuta.

4) parseArgumentAndEntityToList()
	- primeste un argument care reprezinta calea;
	- elimina "/" de la sfarsitul caii;
	- desparte apoi calea in doua -> ultimul element din cale si calea parinte;
	- returneaza cele doua elemente obtinute.

	Composite Pattern l-am utilizat pentru a trata directoare si fisierele in
acelasi mod, deoarece au foarte multe similaritati. L-am folosit, de asemenea,
pentru crearea structurii arborescente a sistemului de fisiere.
	Fisierele sunt frunze in aceasta structura arborescenta, iar directoarele
sunt noduri din care pleaca alte directoare sau fisiere. Ambele contin functii
comune de obtinere a numelui, de obtinere si schimbare a owner-ului, de obtinere
si schimbare a permisiunilor, si de printare.
	Un director contine o lista de entitati  care pot fi fisiere sau directoare. 
La adaugare se adauga in lista de entitati, necontand ce fel de entitate este.
Entitatea respectiva se verifica apoi daca e director, pentru a seta parintele
acestuia. La printare se parcurge lista si se executa functia de printare a entitatii:
la directoare se parcurge lista si astfel se merge recursiv, iar la fisiere doar se
afiseaza.

	Am creat o clasa "Helpers" in care am stocat variabile statice, care le voi
folosi pe tot parcursul programului, astfel neavand nevoie sa instantiez clasa tot
timpul. Aceste variabile sunt:
1) indent -> pentru printarea indentata;
2) users -> lista de useri din program;
3) currentUser -> user-ul curent care executa comenzi;
4) currentPath -> calea curenta folosita.

	Comenzile le-am impartit in doua categorii: cele cu argument si cele cu
parametru si argument. Argumentul este, in functie de comanda, ori un user ori o cale.
In clasa "Commands" sunt facute aceste diferentieri, existand doua functii de
executie si doua functii de obtinere a comenzii complete. Pentru a obtine comanda
se trimite spre "CommandsFactory" numele comenzii dorite si aceasta returneaza
comanda respectiva.
	Functiile de executie a comenzilor implementate:
1) adduser:
	- verifica daca e root si daca user-ul se afla in sistem;
	- daca totul e bine, se adauga user si se creaza directorul sau.

2) deluser:
	- verifica daca e root si daca user-ul se afla in sistem;
	- daca nu sunt erori, se sterge user-ul din sistem si se schimba owner-ul
entitatilor create de user.

3) chuser:
	- se verifica daca user-ul e in sistem;
	- daca exista atunci se marcheaza ca el este user-ul curent si se schimba
calea curenta cu directorul user-ului curent.

4) cd:
	- se obtine calea absoluta din calea data de argument;
	- se cauta directorul, iar daca acesta exista se schimba calea curenta cu
calea sa.

5) mkdir:
	- se imparte calea in doua: in numele directorului de creat si calea parinte
a acestuia;
	- se obtine calea absoluta din calea parinte;
	- se cauta directorul, iar daca exista se verifica daca exista vreo entitate
cu numele directorului de creat;
	- daca nu apar erori se creeaza directorul.

6) ls:
	- se imparte calea in doua;
	- se obtine calea absoluta din calea parinte;
	- se cauta directorul, apoi se obtine directorul de afisat din directorul
parinte;
	- daca exista drepturi de executie si citire se afiseaza continutul.

7) chmod:
	- se converteste parametrul (numarul din 2 cifre) intr-un string de permisiuni;
	- se imparte calea in doua;
	- se obtine calea absoluta din calea parinte;
	- se cauta directorul, iar daca acesta exista se cauta entitatea la care se
schimba permisiunile, verificand daca acest lucru e posibil.

8) touch:
	- se imparte calea in doua;
	- se obtine calea absoluta din calea parinte;
	- se cauta directorul, iar daca exista se verifica daca exista vreo entitate
cu numele fisierului de creat;
	- daca nu apar erori se creaza fisierul.

9) rm:
	- se imparte calea in doua;
	- se obtine calea absoluta din calea parinte;
	- se cauta directorul;
	- daca exista director si comanda e "rm" se cauta fisierul si daca exista in
lista de entitati si permisiunile permit, se sterge;
	- daca exista director si comanda e "rm -r" se cauta directorul si se verifica
daca permisiunile permit si directorul nu face parte din calea curenta; daca trece de
verificare se elimina.

10) rmdir:
	- ca la "rm -r", doar ca se verifica daca directorul este in plus si gol.

11) writetofile:
	- se imparte calea in doua;
	- se obtine calea absoluta din calea parinte;
	- se cauta directorul, iar daca exista se verifica daca exista fisierul si daca
permisiunile permit sa fie scris;
	- in cazul in care trece verificarea se scrie in membrul content al fisierului.

12) cat:
- se imparte calea in doua;
	- se obtine calea absoluta din calea parinte;
	- se cauta directorul, iar daca exista se verifica daca exista fisierul si daca
permisiunile permit sa fie citit
	- in cazul in care trece verificarea se afiseaza membrul content al fisierului.