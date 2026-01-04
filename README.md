# Blog

A modern Java Spring Boot REST API service for managing a blog system with users, posts, tags, and categories.

## Features

- **User Management**: Create and manage blog users with authentication
- **Post Management**: Create, update, publish, and manage blog posts
- **Tag System**: Organize posts with customizable tags
- **Category System**: Classify posts into categories
- **Draft & Published States**: Posts can be saved as drafts or published
- **Spring Security**: Secured endpoints with HTTP Basic authentication
- **Database Migrations**: Liquibase for version-controlled schema management
- **PostgreSQL**: Production-ready relational database
- **Docker Support**: Container-ready with docker-compose for local development
- **REST API**: Complete RESTful API with proper HTTP methods and status codes
- **Clean Code**: Modern Java practices with Lombok for reduced boilerplate

## Technology Stack

- **Java 21**: Latest LTS version
- **Spring Boot 3.4.1**: Application framework
- **Spring Data JPA**: Data persistence
- **Spring Security**: Authentication and authorization
- **Hibernate**: ORM framework
- **Liquibase**: Database migration tool
- **PostgreSQL**: Production database
- **Lombok**: Reduce boilerplate code
- **Docker**: Container platform
- **Maven**: Build and dependency management

## Project Structure

```
de.ityreh.home.blog
├── config/              # Configuration classes (Security, Exception Handling)
├── controller/          # REST Controllers
├── dto/                 # Data Transfer Objects
├── entity/              # JPA Entities
├── repository/          # Spring Data JPA Repositories
├── service/             # Business logic layer
└── security/            # Security components
```

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.9+
- Docker & Docker Compose (for containerized setup)

### Run with Docker Compose (Recommended)

The easiest way to run the application locally is using Docker Compose:

```bash
# Start the application and PostgreSQL database
docker-compose up -d

# View logs
docker-compose logs -f blog-app

# Stop the application
docker-compose down

# Stop and remove volumes (clean slate)
docker-compose down -v
```

The application will be available at `http://localhost:8080`

### Run Locally (Without Docker)

#### 1. Start PostgreSQL

You need a PostgreSQL database running. You can use Docker:

```bash
docker run --name blog-postgres -e POSTGRES_DB=blogdb -e POSTGRES_USER=bloguser -e POSTGRES_PASSWORD=blogpass -p 5432:5432 -d postgres:16-alpine
```

#### 2. Build the Project

```bash
mvn clean package
```

#### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Database Migrations

This project uses Liquibase for database schema management. Migration files are located in:

```
src/main/resources/db/changelog/
├── db.changelog-master.xml          # Master changelog file
└── changes/
    └── 001-initial-schema.xml       # Initial database schema
```

Liquibase automatically runs migrations on application startup. To add new migrations:

1. Create a new changeset XML file in `src/main/resources/db/changelog/changes/`
2. Include it in the master changelog file

## Environment Variables

The application can be configured using environment variables:

- `DB_HOST`: PostgreSQL host (default: `localhost`)
- `DB_PORT`: PostgreSQL port (default: `5432`)
- `DB_NAME`: Database name (default: `blogdb`)
- `DB_USER`: Database username (default: `bloguser`)
- `DB_PASSWORD`: Database password (default: `blogpass`)

- JDBC URL: `jdbc:h2:mem:blogdb`
- Username: `sa`
- Password: (leave empty)

## API Endpoints

### Users

- `POST /api/users` - Create a new user (public)
- `GET /api/users` - Get all users (authenticated)
- `GET /api/users/{id}` - Get user by ID (authenticated)
- `GET /api/users/username/{username}` - Get user by username (authenticated)
- `PUT /api/users/{id}` - Update user (authenticated)
- `DELETE /api/users/{id}` - Delete user (authenticated)

### Posts

- `POST /api/posts` - Create a new post (authenticated)
- `GET /api/posts` - Get all posts (public)
- `GET /api/posts/{id}` - Get post by ID (public)
- `GET /api/posts/author/{authorId}` - Get posts by author (public)
- `GET /api/posts/status/{status}` - Get posts by status (public)
- `PUT /api/posts/{id}` - Update post (authenticated)
- `POST /api/posts/{id}/publish` - Publish a post (authenticated)
- `DELETE /api/posts/{id}` - Delete post (authenticated)

### Tags

- `POST /api/tags` - Create a new tag (authenticated)
- `GET /api/tags` - Get all tags (public)
- `GET /api/tags/{id}` - Get tag by ID (public)
- `GET /api/tags/name/{name}` - Get tag by name (public)
- `PUT /api/tags/{id}` - Update tag (authenticated)
- `DELETE /api/tags/{id}` - Delete tag (authenticated)

### Categories

- `POST /api/categories` - Create a new category (authenticated)
- `GET /api/categories` - Get all categories (public)
- `GET /api/categories/{id}` - Get category by ID (public)
- `GET /api/categories/name/{name}` - Get category by name (public)
- `PUT /api/categories/{id}` - Update category (authenticated)
- `DELETE /api/categories/{id}` - Delete category (authenticated)

## Example Usage

### Create a User

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

### Create a Category

```bash
curl -X POST http://localhost:8080/api/categories \
  -u john_doe:password123 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Technology",
    "description": "Tech-related posts"
  }'
```

### Create a Post

```bash
curl -X POST http://localhost:8080/api/posts \
  -u john_doe:password123 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My First Blog Post",
    "content": "This is the content of my first post.",
    "authorId": 1,
    "categoryId": 1,
    "status": "DRAFT"
  }'
```

### Publish a Post

```bash
curl -X POST http://localhost:8080/api/posts/1/publish \
  -u john_doe:password123
```

## Security

- User passwords are encrypted using BCrypt
- HTTP Basic Authentication is used for protected endpoints
- Public access is allowed for reading posts, tags, and categories
- User registration is public
- All modifications require authentication
- Stateless session management (suitable for REST APIs)

## Database Schema

The application automatically creates the following tables:

- **users**: User accounts with authentication credentials
- **posts**: Blog posts with title, content, status, and timestamps
- **categories**: Post categories
- **tags**: Post tags
- **post_tags**: Many-to-many relationship between posts and tags

## Development Notes

- Clean Code practices are followed throughout the codebase
- All services use constructor injection via Lombok's `@RequiredArgsConstructor`
- DTOs separate API contracts from domain entities
- Global exception handling provides consistent error responses
- Validation is performed using Jakarta Bean Validation annotations
- Comprehensive logging is included for debugging

## License

This project is created for educational and demonstration purposes.
