# Proiect Spring Boot - Managementul Listei de Materiale (BOM)

Acest proiect este o aplicație web construită cu **Spring Boot**, destinată managementului produselor și a listelor de materiale (Bill of Materials - BOM). Reprezintă o modernizare a conceptului din proiectul JSP/Servlet, utilizând un framework modern pentru o dezvoltare și configurare simplificată.

## Scopul Aplicației

Aplicația, denumită `ProductionManager`, oferă o interfață web pentru a gestiona inventarul de componente și produse finite.

- **Managementul Componentelor**: Permite operații CRUD (Create, Read, Update, Delete) pentru componentele individuale, stocând detalii precum cod, cost, stoc, furnizor.
- **Managementul Produselor**: Permite operații CRUD pentru produsele finite, care sunt asamblate din componente.
- **Managementul Listei de Materiale (BOM)**: Funcționalitatea centrală care permite definirea "rețetei" pentru fiecare produs, specificând componentele necesare și cantitățile acestora.

## Tehnologii Utilizate

- **Framework**: Spring Boot 3.4.0
- **Limbaj de programare**: Java 21
- **Build Tool**: Apache Maven
- **Server**: Rulează pe un server web embedded (Tomcat, inclus în Spring Boot), deci nu necesită o instalare separată de Tomcat.
- **Acces la Date**:
    - Spring Data JPA
    - Hibernate (ca implementare JPA)
- **Bază de date**: MySQL 8.x
- **Template Engine**: Thymeleaf (pentru randarea paginilor HTML)
- **Mediu de dezvoltare**: Poate fi rulat din orice IDE modern (Eclipse, IntelliJ IDEA) sau direct din linia de comandă.

---

## Setup și Rulare

Urmați pașii de mai jos pentru a configura și rula proiectul.

### 1. Prerechizite

- **JDK 21** (Java Development Kit)
- **Apache Maven** (poate fi folosit și wrapper-ul `mvnw` inclus în proiect)
- **MySQL Server** (versiune 8.x recomandată)

### 2. Baza de Date

Acest proiect folosește **exact aceeași bază de date** ca și proiectul anterior (JSP/Tomcat).

1.  Dacă nu ați făcut-o deja, rulați scriptul din fișierul `bazadedate.sql` într-un client MySQL pentru a crea schema `product_components` și tabelele aferente.
2.  Dacă ați rulat deja scriptul pentru celălalt proiect, baza de date este pregătită.

### 3. Configurare Proiect

Fișierul principal de configurare este `demo/src/main/resources/application.properties`.

1.  **Deschideți fișierul `application.properties`**.
2.  **Modificați credențialele bazei de date**: Găsiți și actualizați următoarele linii pentru a corespunde cu configurarea serverului dumneavoastră MySQL:
    ```properties
    spring.datasource.username=bdpi_app
    spring.datasource.password=florian@2005
    ```
    Înlocuiți `bdpi_app` și `florian@2005` cu utilizatorul și parola corecte pentru MySQL.
3.  **Hibernate DDL-Auto**: Setarea `spring.jpa.hibernate.ddl-auto=update` înseamnă că Hibernate va încerca să actualizeze schema bazei de date la pornire pentru a se potrivi cu entitățile definite în cod. Pentru prima rulare, acest lucru este util.

### 4. Rularea Aplicației

Există mai multe metode pentru a porni aplicația:

#### Metoda 1: Folosind Maven Wrapper (Recomandat)

1.  Deschideți un terminal sau o linie de comandă în directorul rădăcină al proiectului (`demo`).
2.  Executați comanda:
    - Pe Windows: `mvnw spring-boot:run`
    - Pe macOS/Linux: `./mvnw spring-boot:run`
3.  Aplicația va porni, iar serverul va fi accesibil pe `http://localhost:8080`.

#### Metoda 2: Dintr-un IDE (Eclipse/IntelliJ)

1.  **Importați proiectul**:
    - În IDE, selectați `File -> Open...` sau `File -> Import...`.
    - Navigați la directorul `demo` și importați-l ca pe un proiect Maven existent. IDE-ul va detecta automat `pom.xml` și va descărca dependențele.
2.  **Rulați clasa principală**:
    - Găsiți clasa care conține adnotarea `@SpringBootApplication` (probabil `demo/src/main/java/com/example/demo/DemoApplication.java`).
    - Faceți clic dreapta pe această clasă și selectați `Run As -> Java Application` (în Eclipse) sau pur și simplu apăsați pe butonul "Run" (în IntelliJ).

#### Metoda 3: Crearea unui fișier JAR executabil

1.  Deschideți un terminal în directorul `demo`.
2.  Construiți proiectul folosind Maven:
    - Pe Windows: `mvnw clean package`
    - Pe macOS/Linux: `./mvnw clean package`
3.  Această comandă va crea un fișier `.jar` în directorul `target/`.
4.  Rulați fișierul JAR:
    ```sh
    java -jar target/demo-0.0.1-SNAPSHOT.jar
    ```

După pornire, indiferent de metodă, deschideți un browser și accesați `http://localhost:8080` pentru a utiliza aplicația.
