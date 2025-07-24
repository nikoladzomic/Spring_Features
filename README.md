# Insurance Client Registry 🏢

Spring Boot aplikacija za upravljanje klijentima i njihovim osiguravajućim polisama.

## 📋 Opis Projekta

Insurance Client Registry je REST API aplikacija koja omogućava:
- Upravljanje klijentima (kreiranje, čitanje, ažuriranje, deaktivacija)
- Pretragu aktivnih klijenata
- Upravljanje osiguravajućim polisama
- Soft delete funkcionalnost

## 🛠️ Tehnologije

- **Java 21**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **MySQL 8.0**
- **MapStruct** - za mapiranje između entiteta i DTO-ova
- **Maven** - dependency management
- **JUnit 5** - testiranje
- **Mockito** - mock objekti za testove

## 🏗️ Arhitektura

```
src/
├── main/java/com/example/spring_boot_starter_parent/
│   ├── controller/          # REST kontroleri
│   ├── service/             # Business logika
│   ├── repository/          # Data pristup
│   ├── model/              # JPA entiteti
│   ├── dto/                # Data Transfer Objects
│   │   ├── request/        # DTO za zahteve
│   │   └── response/       # DTO za odgovore
│   ├── mapper/             # MapStruct maperi
│   ├── exception/          # Custom exception klase
│   └── config/             # Konfiguracija
└── test/                   # Unit i integration testovi
```

## 🚀 Pokretanje Aplikacije

### Preduslovi
- Java 21
- MySQL 8.0
- Maven 3.6+

### Koraci

1. **Kloniraj repozitorijum:**
```bash
git clone https://github.com/nikoladzomic/Spring_Features.git
```

2. **Podesi MySQL bazu:**
```sql
CREATE DATABASE insurance_registry;
```

3. **Konfiguriši `application.properties`:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/insurance_registry
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. **Pokreni aplikaciju:**
```bash
mvn clean install
mvn spring-boot:run
```

Aplikacija će biti dostupna na: `http://localhost:8080`

## 📚 API Endpoints

### Klijenti

| Metoda | Endpoint | Opis |
|--------|----------|------|
| POST | `/api/v1/clients` | Kreiranje novog klijenta |
| GET | `/api/v1/clients/{id}` | Preuzimanje klijenta po ID-u |
| PUT | `/api/v1/clients/{id}` | Ažuriranje klijenta |
| DELETE | `/api/v1/clients/{id}` | Deaktivacija klijenta (soft delete) |
| PATCH | `/api/v1/clients/{id}/activate` | Aktivacija klijenta |
| GET | `/api/v1/clients/search?q={term}` | Pretraga aktivnih klijenata |

### Primer API poziva

**Kreiranje klijenta:**
```json
POST /api/v1/clients
{
  "firstName": "Marko",
  "lastName": "Petrović",
  "email": "marko@email.com",
  "phoneNumber": "064-123-4567",
  "address": "Beograd, Srbija"
}
```

**Odgovor:**
```json
{
  "id": 1,
  "firstName": "Marko",
  "lastName": "Petrović",
  "email": "marko@email.com",
  "phoneNumber": "064-123-4567",
  "address": "Beograd, Srbija",
  "active": true,
  "policyCount": 0,
  "policies": [],
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

## 🧪 Testiranje

Pokreni sve testove:
```bash
mvn test
```

Pokreni testove sa coverage:
```bash
mvn test jacoco:report
```

## 📊 Database Schema

### Client tabela
```sql
CREATE TABLE clients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    address TEXT,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Policy tabela
```sql
CREATE TABLE policies (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    policy_number VARCHAR(50) UNIQUE NOT NULL,
    type ENUM('AUTO', 'HOME', 'LIFE', 'HEALTH') NOT NULL,
    status ENUM('ACTIVE', 'EXPIRED', 'CANCELLED') NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    premium_amount DECIMAL(10,2) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    client_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id)
);
```

### Maven Dependencies
Ključne dependency-je u `pom.xml`:
- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-validation`
- `mysql-connector-java`
- `mapstruct`
- `spring-boot-starter-test`

## 🎯 Funkcionalnosti

✅ **Implementirano:**
- CRUD operacije za klijente
- Soft delete funkcionalnost
- Pretraga klijenata
- Mapiranje sa MapStruct
- Custom exception handling
- Unit testovi za service layer
- Validacija podataka

🔄 **U toku:**
- Integration testovi
- Spring Security implementacija
- API dokumentacija sa Swagger

📋 **Planirano:**
- Pagination i sorting
- Caching
- Health checks sa Actuator

## 👨‍💻 Autor

**Nikola Dzomic**
Email: nikoladzomic123@gmail.com

## 🤝 Doprinos

1. Fork-ujte projekat
2. Kreirajte feature branch (`git checkout -b feature/amazing-feature`)
3. Commit-ujte promene (`git commit -m 'Add amazing feature'`)
4. Push-ujte na branch (`git push origin feature/amazing-feature`)
5. Otvorite Pull Request

## 📝 Napomene

- Aplikacija koristi soft delete - klijenti se označavaju kao neaktivni umesto brisanja
- Email adrese moraju biti jedinstvene u sistemu
- Svi timestamp-ovi su automatski generisani
- API koristi standardne HTTP status kodove