# LinkedIn Clone - Microservices Architecture

A scalable, event-driven LinkedIn clone built with microservices architecture using Spring Boot, Spring Cloud, Apache Kafka, and deployed on Kubernetes. This system demonstrates modern cloud-native application design patterns including service discovery, API gateway, event-driven communication, and polyglot persistence.

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3+-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-Event--Driven-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-Orchestrated-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white)


## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [System Architecture](#-system-architecture)
- [Tech Stack](#-tech-stack)
- [Microservices](#-microservices)
- [Database Schema](#-database-schema)
- [Project Structure](#-project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#-installation--setup)
- [Environment Variables](#-environment-variables)
- [Running Locally](#-running-locally)
- [API Endpoints](#-api-endpoints)
- [Authentication Flow](#-authentication-flow)
- [Event-Driven Architecture](#-event-driven-architecture)
- [Kubernetes Deployment](#-kubernetes-deployment)
- [Docker Deployment](#-docker-deployment)
- [Monitoring & Observability](#-monitoring--observability)
- [Security Considerations](#-security-considerations)
- [Scaling & Performance](#-scaling--performance)
- [Contributing](#-contributing)
- [License](#-license)

## 🎯 Overview

This project is a professional implementation of a LinkedIn-like social networking platform built using microservices architecture. It demonstrates industry best practices for building scalable, distributed systems with proper separation of concerns, event-driven communication, and cloud-native deployment strategies.

The system supports core LinkedIn features including user authentication, post creation, social connections, post likes, and real-time notifications, all orchestrated through a robust microservices infrastructure.

## ✨ Features

- **User Management**
    - User registration with email validation
    - JWT-based authentication and authorization
    - Secure password hashing using BCrypt
    - User profile management

- **Post Management**
    - Create, read, and retrieve posts
    - User-specific post feeds
    - Post content validation

- **Social Connections**
    - Send connection requests
    - Accept/reject connection requests
    - View first-degree connections
    - Graph-based relationship storage using Neo4j

- **Engagement**
    - Like/unlike posts
    - Real-time like tracking

- **Notifications**
    - Event-driven notification system
    - Notifications for new posts from connections
    - Notifications for post likes
    - Asynchronous message processing

- **File Upload (In Development)**
    - Cloudinary integration for media storage
    - Support for image uploads

- **Infrastructure**
    - Service discovery with Netflix Eureka
    - API Gateway for request routing
    - Event streaming with Apache Kafka
    - Containerized deployment with Docker
    - Kubernetes orchestration
    - Kafka UI for stream monitoring

## 🏗️ System Architecture
```
                                    ┌─────────────────┐
                                    │   Kubernetes    │
                                    │    Ingress      │
                                    └────────┬────────┘
                                             │
                                    ┌────────▼────────┐
                                    │   API Gateway   │
                                    │   (Port 8083)   │
                                    └────────┬────────┘
                                             │
                        ┌────────────────────┼────────────────────┐
                        │                    │                    │
                ┌───────▼────────┐  ┌───────▼────────┐  ┌───────▼────────┐
                │  User Service  │  │ Posts Service  │  │ Connections    │
                │  (PostgreSQL)  │  │  (PostgreSQL)  │  │ Service        │
                └───────┬────────┘  └───────┬────────┘  │ (Neo4j)        │
                        │                    │           └───────┬────────┘
                        │                    │                   │
                        └────────────────────┼───────────────────┘
                                             │
                                    ┌────────▼────────┐
                                    │  Apache Kafka   │
                                    │  Event Streams  │
                                    └────────┬────────┘
                                             │
                                    ┌────────▼────────────┐
                                    │ Notification Service│
                                    │   (PostgreSQL)      │
                                    └─────────────────────┘

                        ┌────────────────────────────────┐
                        │   Eureka Discovery Server      │
                        │        (Port 8761)             │
                        └────────────────────────────────┘
```

### Architecture Highlights

- **API Gateway Pattern**: Single entry point for all client requests with JWT validation
- **Service Discovery**: Dynamic service registration and discovery using Netflix Eureka
- **Event-Driven Communication**: Asynchronous messaging via Kafka for loosely coupled services
- **Polyglot Persistence**: PostgreSQL for transactional data, Neo4j for graph relationships
- **Circuit Breaker**: Resilient inter-service communication with Spring Cloud OpenFeign
- **Containerization**: All services packaged as Docker containers
- **Orchestration**: Kubernetes manifests for production deployment

## 🛠️ Tech Stack

### Backend
- **Language**: Java 21
- **Framework**: Spring Boot 4.0.1, Spring Boot 3.3.4 (API Gateway)
- **Build Tool**: Maven
- **Microservices Framework**: Spring Cloud 2025.1.0

### Service Discovery & Gateway
- Netflix Eureka Server
- Spring Cloud Gateway

### Databases
- **PostgreSQL 16**: User, Posts, Notification services
- **Neo4j**: Connections service (graph database)

### Message Broker
- **Apache Kafka**: Event streaming and asynchronous communication

### Security
- **JWT (JJWT 0.12.6)**: Token-based authentication
- **BCrypt**: Password hashing

### DevOps & Deployment
- **Docker**: Container runtime
- **Docker Compose**: Local multi-container orchestration
- **Kubernetes**: Production orchestration
- **Google Jib**: Containerless Docker builds
- **Google Cloud Storage**: File storage (configured)
- **Cloudinary**: Image hosting

### Monitoring
- **Kafbat UI**: Kafka stream monitoring
- **Spring Actuator**: Service health checks

### Additional Libraries
- **Lombok**: Boilerplate reduction
- **ModelMapper**: DTO mapping
- **Spring Data JPA**: ORM for PostgreSQL
- **Spring Data Neo4j**: Graph database access
- **OpenFeign**: Declarative REST clients

## 🔧 Microservices

### 1. Discovery Server
- **Port**: 8761
- **Technology**: Netflix Eureka Server
- **Purpose**: Service registry for dynamic service discovery
- **Docker Image**: `xtremeneon/discovery-server:latest`

### 2. API Gateway
- **Port**: 8083
- **Technology**: Spring Cloud Gateway
- **Purpose**:
    - Single entry point for all client requests
    - JWT token validation
    - Request routing to downstream services
    - Load balancing
- **Docker Image**: `xtremeneon/api-gateway:latest`

### 3. User Service
- **Database**: PostgreSQL (`userDB`)
- **Technology**: Spring Boot, JPA
- **Purpose**:
    - User registration and authentication
    - JWT token generation
    - User profile management
- **Docker Image**: `xtremeneon/user-service:latest`

### 4. Posts Service
- **Database**: PostgreSQL (`postsDB`)
- **Technology**: Spring Boot, JPA, Kafka Producer
- **Purpose**:
    - Create and retrieve posts
    - Manage post likes
    - Publish events for post creation and likes
    - User context propagation via Feign interceptors
- **Docker Image**: `xtremeneon/posts-service:latest`

### 5. Connections Service
- **Database**: Neo4j (graph database)
- **Technology**: Spring Data Neo4j, Kafka Producer
- **Purpose**:
    - Manage user connections (graph relationships)
    - Send/accept/reject connection requests
    - Query connection networks
    - Publish connection events
- **Docker Image**: `xtremeneon/connections-service:latest`

### 6. Notification Service
- **Database**: PostgreSQL (`notificationDB`)
- **Technology**: Spring Boot, Kafka Consumer, OpenFeign
- **Purpose**:
    - Consume events from Kafka
    - Generate notifications for users
    - Store notification history
    - Notify users about connection activities and post interactions
- **Docker Image**: `xtremeneon/notification-service:latest`

### 7. Uploader Service (Under Development)
- **Technology**: Spring Boot, Cloudinary SDK, Google Cloud Storage
- **Purpose**:
    - File upload handling
    - Integration with Cloudinary for image hosting
    - Media asset management

## 📊 Database Schema

### User Service (PostgreSQL)

**Table: `users`**
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
```

### Posts Service (PostgreSQL)

**Table: `posts`**
```sql
CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Table: `post_likes`**
```sql
CREATE TABLE post_likes (
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(post_id, user_id)
);
```

### Connections Service (Neo4j Graph)

**Node: `Person`**
```cypher
(:Person {
    id: Long,
    userId: Long,
    name: String
})
```

**Relationship: `CONNECTED_TO`**
```cypher
(:Person)-[:CONNECTED_TO]->(:Person)
```

### Notification Service (PostgreSQL)

**Table: `notification`**
```sql
CREATE TABLE notification (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 📁 Project Structure
```
LinkedIn/
├── api-gateway/                    # API Gateway service
│   ├── src/main/java/
│   │   └── com/divyansh/linkedin/api_gateway/
│   │       ├── filters/
│   │       │   └── AuthenticationFilter.java
│   │       ├── JWTService.java
│   │       └── ApiGatewayApplication.java
│   ├── src/main/resources/
│   │   └── application-k8s.properties
│   └── pom.xml
│
├── discovery-server/               # Eureka service discovery
│   ├── src/main/java/
│   └── pom.xml
│
├── user-service/                   # User authentication & management
│   ├── src/main/java/
│   │   └── com/divyansh/linkedin/user_service/
│   │       ├── controller/
│   │       │   └── AuthController.java
│   │       ├── dto/
│   │       │   ├── LoginRequestDTO.java
│   │       │   ├── SignUpRequestDTO.java
│   │       │   └── UserDTO.java
│   │       ├── entity/
│   │       │   └── User.java
│   │       ├── repository/
│   │       │   └── UserRepository.java
│   │       ├── service/
│   │       │   ├── AuthService.java
│   │       │   └── JWTService.java
│   │       ├── utils/
│   │       │   └── PasswordUtil.java
│   │       └── exception/
│   └── pom.xml
│
├── posts-service/                  # Posts & likes management
│   ├── src/main/java/
│   │   └── com/divyansh/linkedin/posts_service/
│   │       ├── controller/
│   │       │   ├── PostsController.java
│   │       │   └── LikesController.java
│   │       ├── entity/
│   │       │   ├── Post.java
│   │       │   └── PostLike.java
│   │       ├── event/
│   │       │   ├── PostCreatedEvent.java
│   │       │   └── PostLikedEvent.java
│   │       ├── clients/
│   │       │   └── ConnectionsClient.java
│   │       ├── auth/
│   │       │   ├── UserContextHolder.java
│   │       │   ├── UserInterceptor.java
│   │       │   ├── WebConfig.java
│   │       │   └── FeignClientInterceptor.java
│   │       └── config/
│   │           └── KafkaTopicConfig.java
│   └── pom.xml
│
├── connections-service/            # Social connections (Neo4j)
│   ├── src/main/java/
│   │   └── com/divyansh/linkedin/connections_service/
│   │       ├── controller/
│   │       │   └── ConnectionsController.java
│   │       ├── entity/
│   │       │   └── Person.java
│   │       ├── repository/
│   │       │   └── PersonRepository.java
│   │       ├── service/
│   │       │   └── ConnectionsService.java
│   │       ├── event/
│   │       │   ├── SendConnectionRequestEvent.java
│   │       │   └── AcceptConnectionRequestEvent.java
│   │       └── config/
│   │           └── KafkaTopicConfig.java
│   └── pom.xml
│
├── notification-service/           # Event-driven notifications
│   ├── src/main/java/
│   │   └── com/divyansh/linkedin/notification_service/
│   │       ├── consumer/
│   │       │   ├── PostsServiceConsumer.java
│   │       │   └── ConnectionsServiceConsumer.java
│   │       ├── entity/
│   │       │   └── Notification.java
│   │       ├── service/
│   │       │   └── SendNotification.java
│   │       └── clients/
│   │           └── ConnectionsClient.java
│   └── pom.xml
│
├── uploader-service/               # File upload service
│   ├── src/main/java/
│   │   └── com/divyansh/linkedin/uploader_service/
│   │       ├── controller/
│   │       │   └── FileUploadController.java
│   │       ├── service/
│   │       │   ├── FileUploaderService.java
│   │       │   └── CloudinaryFileUploadService.java
│   │       └── config/
│   │           └── FileUploaderConfig.java
│   └── pom.xml
│
├── k8s/                            # Kubernetes manifests
│   ├── api-gateway.yml
│   ├── user-service.yml
│   ├── posts-service.yml
│   ├── connections-service.yml
│   ├── notification-service.yml
│   ├── user-db.yml
│   ├── posts-db.yml
│   ├── connections-db.yml
│   ├── notification-db.yml
│   ├── kafka.yml
│   ├── kafkaui.yml
│   └── ingress.yml
│
├── docker-compose.yml              # Docker Compose orchestration
└── .github/
    └── appmod/                     # TODO: CI/CD workflows
```

## 📋 Prerequisites

- **Java Development Kit (JDK)**: 21 or higher
- **Maven**: 3.8+
- **Docker**: 20.10+
- **Docker Compose**: 2.0+
- **Kubernetes** (optional): Minikube, K3s, or cloud provider (GKE, EKS, AKS)
- **kubectl** (optional): For Kubernetes deployment
- **Git**: For version control

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
# Cloning the Repository
git clone https://github.com/Divyansh9192/LinkedIn-Backend-System.git
cd LinkedIn-Backend-System
```

### 2. Build All Services

Each service can be built independently using Maven:
```bash
# Build all services
for service in discovery-server api-gateway user-service posts-service connections-service notification-service uploader-service; do
    cd $service
    mvn clean package -DskipTests
    cd ..
done
```

### 3. Build Docker Images (Using Jib)
```bash
# Build and push images using Jib
for service in discovery-server api-gateway user-service posts-service connections-service notification-service; do
    cd $service
    mvn compile jib:build
    cd ..
done
```

**Note**: Update the Docker registry in each `pom.xml` file:
```xml
<configuration>
    <to>
        <image>docker.io/YOUR_DOCKERHUB_USERNAME/${project.name}:${project.version}</image>
    </to>
</configuration>
```

## 🔐 Environment Variables

### User Service

| Variable | Description | Default                                 | Required |
|----------|-------------|-----------------------------------------|----------|
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | `jdbc:postgresql://user-db:5432/userDB` | Yes |
| `SPRING_DATASOURCE_USERNAME` | Database username | `xtremeneon`                            | Yes |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `your_db_password`                      | Yes |
| `JWT_SECRET_KEY` | Secret key for JWT signing | -                                       | Yes |
| `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` | Eureka server URL | `http://discovery-server:8761/eureka`   | Yes |

### Posts Service

| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | `jdbc:postgresql://posts-db:5432/postsDB` | Yes |
| `SPRING_DATASOURCE_USERNAME` | Database username | `xtremeneon` | Yes |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `your_db_password` | Yes |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | Kafka broker addresses | `kafka:9092` | Yes |
| `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` | Eureka server URL | `http://discovery-server:8761/eureka` | Yes |

### Connections Service

| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `SPRING_NEO4J_URI` | Neo4j connection URI | `bolt://connections-db:7687` | Yes |
| `SPRING_NEO4J_AUTHENTICATION_USERNAME` | Neo4j username | `neo4j` | Yes |
| `SPRING_NEO4J_AUTHENTICATION_PASSWORD` | Neo4j password | `your_neo4j_password` | Yes |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | Kafka broker addresses | `kafka:9092` | Yes |
| `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` | Eureka server URL | `http://discovery-server:8761/eureka` | Yes |

### Notification Service

| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | `jdbc:postgresql://notification-db:5432/notificationDB` | Yes |
| `SPRING_DATASOURCE_USERNAME` | Database username | `xtremeneon` | Yes |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `your_db_password` | Yes |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | Kafka broker addresses | `kafka:9092` | Yes |
| `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` | Eureka server URL | `http://discovery-server:8761/eureka` | Yes |

### API Gateway

| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `JWT_SECRET_KEY` | Secret key for JWT validation | - | Yes |
| `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` | Eureka server URL | `http://discovery-server:8761/eureka` | Yes |

### Uploader Service

| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `CLOUDINARY_CLOUD_NAME` | Cloudinary cloud name | - | No |
| `CLOUDINARY_API_KEY` | Cloudinary API key | - | No |
| `CLOUDINARY_API_SECRET` | Cloudinary API secret | - | No |
| `SPRING_CLOUD_GCP_STORAGE_PROJECT_ID` | GCP project ID | - | No |
| `SPRING_CLOUD_GCP_STORAGE_CREDENTIALS_LOCATION` | GCP credentials path | - | No |

## 🏃 Running Locally

### Using Docker Compose (Recommended)

1. **Start all services**:
```bash
docker-compose up -d
```

2. **Verify services are running**:
```bash
docker-compose ps
```

3. **View logs**:
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f user-service
```

4. **Access services**:
    - API Gateway: http://localhost:8083
    - Eureka Dashboard: http://localhost:8761
    - Kafka UI: http://localhost:8090
    - Neo4j Browser: http://localhost:7474

5. **Stop all services**:
```bash
docker-compose down

# Remove volumes (clean state)
docker-compose down -v
```

### Running Services Individually (Development)

1. **Start infrastructure services**:
```bash
docker-compose up -d kafka kafbat-ui notification-db posts-db user-db connections-db
```

2. **Start Discovery Server**:
```bash
cd discovery-server
mvn spring-boot:run
```

3. **Start microservices** (in separate terminals):
```bash
# User Service
cd user-service
mvn spring-boot:run

# Posts Service
cd posts-service
mvn spring-boot:run

# Connections Service
cd connections-service
mvn spring-boot:run

# Notification Service
cd notification-service
mvn spring-boot:run

# API Gateway
cd api-gateway
mvn spring-boot:run
```

## 📡 API Endpoints

### Authentication (User Service)

#### Register User
```http
POST /auth/signup
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "SecurePassword123"
}

Response: 201 Created
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "john.doe@example.com",
  "password": "SecurePassword123"
}

Response: 200 OK
"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### Posts (Posts Service)

All endpoints require JWT token in header: `Authorization: Bearer <token>`

#### Create Post
```http
POST /core
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "content": "This is my first post!"
}

Response: 201 Created
{
  "id": 1,
  "content": "This is my first post!",
  "userId": 1,
  "createdAt": "2026-01-31T10:30:00"
}
```

#### Get Post by ID
```http
GET /core/{postId}
Authorization: Bearer <JWT_TOKEN>

Response: 200 OK
{
  "id": 1,
  "content": "This is my first post!",
  "userId": 1,
  "createdAt": "2026-01-31T10:30:00"
}
```

#### Get All Posts for User
```http
GET /core/users/{userId}/allPosts
Authorization: Bearer <JWT_TOKEN>

Response: 200 OK
[
  {
    "id": 1,
    "content": "This is my first post!",
    "userId": 1,
    "createdAt": "2026-01-31T10:30:00"
  }
]
```

#### Like Post
```http
POST /likes/{postId}
Authorization: Bearer <JWT_TOKEN>

Response: 204 No Content
```

#### Unlike Post
```http
DELETE /likes/{postId}
Authorization: Bearer <JWT_TOKEN>

Response: 204 No Content
```

### Connections (Connections Service)

#### Get First-Degree Connections
```http
GET /core/first-degree
Authorization: Bearer <JWT_TOKEN>

Response: 200 OK
[
  {
    "id": 2,
    "userId": 2,
    "name": "Jane Smith"
  }
]
```

#### Send Connection Request
```http
POST /core/request/{userId}
Authorization: Bearer <JWT_TOKEN>

Response: 200 OK
true
```

#### Accept Connection Request
```http
POST /core/accept/{userId}
Authorization: Bearer <JWT_TOKEN>

Response: 200 OK
true
```

#### Reject Connection Request
```http
POST /core/reject/{userId}
Authorization: Bearer <JWT_TOKEN>

Response: 200 OK
true
```

## 🔒 Authentication Flow

1. **User Registration**:
    - Client sends credentials to `/auth/signup`
    - User Service validates and creates user
    - Password is hashed using BCrypt
    - User details returned (no token)

2. **User Login**:
    - Client sends credentials to `/auth/login`
    - User Service validates credentials
    - JWT token generated with user ID in claims
    - Token returned to client

3. **Authenticated Requests**:
    - Client includes JWT in `Authorization` header
    - API Gateway validates token
    - Gateway extracts user ID from token
    - Request forwarded to downstream services with `X-User-Id` header

4. **Service-to-Service Communication**:
    - Services use Feign clients for inter-service calls
    - FeignClientInterceptor adds `X-User-Id` header
    - User context propagated across service boundaries

## 📨 Event-Driven Architecture

### Kafka Topics

| Topic | Producer | Consumer | Event Type | Purpose |
|-------|----------|----------|------------|---------|
| `post-created-topic` | Posts Service | Notification Service | `PostCreatedEvent` | Notify connections when user creates post |
| `post-liked-topic` | Posts Service | Notification Service | `PostLikedEvent` | Notify post creator when post is liked |
| `send-connection-request-topic` | Connections Service | Notification Service | `SendConnectionRequestEvent` | Notify user of incoming connection request |
| `accept-connection-request-topic` | Connections Service | Notification Service | `AcceptConnectionRequestEvent` | Notify user when connection request is accepted |

### Event Flow Example

**Post Creation Flow**:
```
1. User creates post via API Gateway
2. Posts Service persists post to database
3. Posts Service publishes PostCreatedEvent to Kafka
4. Notification Service consumes event
5. Notification Service queries Connections Service for user's connections
6. Notification Service creates notifications for all connections
```

**Post Like Flow**:
```
1. User likes post via API Gateway
2. Posts Service creates PostLike record
3. Posts Service publishes PostLikedEvent to Kafka
4. Notification Service consumes event
5. Notification Service creates notification for post creator
```

## ☸️ Kubernetes Deployment

### Prerequisites

- Kubernetes cluster (Minikube, GKE, EKS, AKS)
- `kubectl` configured
- Docker images pushed to registry

### Deploy to Kubernetes

1. **Apply database manifests**:
```bash
kubectl apply -f k8s/user-db.yml
kubectl apply -f k8s/posts-db.yml
kubectl apply -f k8s/notification-db.yml
kubectl apply -f k8s/connections-db.yml
```

2. **Deploy Kafka**:
```bash
kubectl apply -f k8s/kafka.yml
kubectl apply -f k8s/kafkaui.yml
```

3. **Deploy microservices**:
```bash
kubectl apply -f k8s/user-service.yml
kubectl apply -f k8s/posts-service.yml
kubectl apply -f k8s/connections-service.yml
kubectl apply -f k8s/notification-service.yml
kubectl apply -f k8s/api-gateway.yml
```

4. **Configure Ingress**:
```bash
kubectl apply -f k8s/ingress.yml
```

5. **Verify deployment**:
```bash
kubectl get pods
kubectl get services
kubectl get ingress
```

### Kubernetes Configuration Notes

- Services use `application-k8s.properties` profile
- Eureka registration disabled in K8s (uses native service discovery)
- Service-to-service communication via K8s DNS
- Ingress routes traffic to API Gateway
- Kafka UI accessible at `/kafka-ui` path

## 🐳 Docker Deployment

### Build Custom Images (Optional)
```bash
# Build individual service
cd user-service
mvn compile jib:dockerBuild

# Build all services
./build-all.sh  # Create this script
```

### Docker Compose Configuration

The `docker-compose.yml` orchestrates:
- 1x Apache Kafka broker
- 1x Kafbat UI (Kafka monitoring)
- 4x PostgreSQL databases
- 1x Neo4j database
- 6x Spring Boot microservices
- Custom network: `linkedin-network`
- Persistent volumes for databases

## 📊 Monitoring & Observability

### Spring Actuator Endpoints

All services expose Actuator endpoints for health checks and metrics:
```bash
# Health check
curl http://localhost:8080/actuator/health

# Info endpoint
curl http://localhost:8080/actuator/info
```

### Kafka Monitoring

Access Kafbat UI for real-time Kafka monitoring:
- **URL**: http://localhost:8090
- **Features**:
    - Topic management
    - Message browsing
    - Consumer group monitoring
    - Broker metrics

### Eureka Dashboard

Monitor service registration and discovery:
- **URL**: http://localhost:8761
- **Features**:
    - Registered services
    - Service health status
    - Instance metadata

### Neo4j Browser

Visualize connection graphs:
- **URL**: http://localhost:7474
- **Username**: neo4j
- **Password**: your_db_password

## 🔐 Security Considerations

### Current Implementation

✅ **Implemented**:
- JWT-based authentication
- BCrypt password hashing
- API Gateway authentication filter
- User context propagation across services

⚠️ **Recommendations for Production**:

1. **Secrets Management**:
    - Use Kubernetes Secrets or HashiCorp Vault
    - Externalize database credentials
    - Rotate JWT secret keys regularly

2. **Network Security**:
    - Enable HTTPS/TLS for all endpoints
    - Implement service mesh (Istio, Linkerd)
    - Use network policies in Kubernetes

3. **Database Security**:
    - Use strong passwords (current defaults are for development)
    - Enable SSL for database connections
    - Implement connection pooling with HikariCP

4. **Rate Limiting**:
    - Implement rate limiting at API Gateway
    - Use Redis for distributed rate limiting

5. **Input Validation**:
    - Add comprehensive validation annotations
    - Sanitize user inputs to prevent injection attacks

6. **CORS Configuration**:
    - Configure appropriate CORS policies
    - Whitelist allowed origins

7. **Audit Logging**:
    - Implement centralized logging (ELK stack)
    - Log authentication attempts
    - Track sensitive operations

## ⚡ Scaling & Performance

### Horizontal Scaling

Services are stateless and can be scaled horizontally:
```bash
# Docker Compose
docker-compose up -d --scale user-service=3

# Kubernetes
kubectl scale deployment user-service --replicas=3
```

### Performance Optimizations

1. **Database Connection Pooling**:
    - HikariCP configured by default
    - Tune pool size based on load

2. **Caching Strategy**:
    - Add Redis for frequently accessed data
    - Implement cache-aside pattern
    - Cache user profiles and connections

3. **Kafka Optimization**:
    - Configure partition count based on throughput
    - Adjust consumer group concurrency
    - Enable compression

4. **API Gateway**:
    - Implement request caching
    - Enable response compression
    - Configure connection pooling

5. **Database Optimization**:
    - Add proper indexes
    - Optimize Neo4j queries with query profiling
    - Use read replicas for read-heavy operations

### Load Testing Recommendations
```bash
# Install Apache Bench
sudo apt-get install apache2-utils

# Test login endpoint
ab -n 1000 -c 10 -p login.json -T application/json \
   http://localhost:8083/auth/login

# Test create post
ab -n 1000 -c 10 -H "Authorization: Bearer <token>" \
   -p post.json -T application/json \
   http://localhost:8083/core
```

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

### Getting Started

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

### Coding Standards

- Follow Java naming conventions
- Use Lombok annotations appropriately
- Write meaningful commit messages
- Add Java Doc for public methods
- Maintain consistent code formatting
- Write tests for new features

### Pull Request Process

1. Update README.md with details of changes
2. Update API documentation if endpoints change
3. Ensure all tests pass
4. Request review from maintainers
5. Squash commits before merging

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 📞 Contact

**Divyansh** - [@xtremeneon](https://hub.docker.com/u/xtremeneon)

**Project Link**: [https://github.com/Divyansh9192/LinkedIn-Backend-System](https://github.com/Divyansh9192/LinkedIn-Backend-System)

---

## 🙏 Acknowledgments

- Spring Boot and Spring Cloud teams for excellent frameworks
- Netflix OSS for Eureka
- Apache Kafka for robust event streaming
- Neo4j for graph database capabilities
- The open-source community

---

**⭐ If you find this project helpful, please consider giving it a star!**