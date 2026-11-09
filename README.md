# spring-boot-keycloak-sample5

[![Java CI with Maven](https://github.com/hendisantika/spring-boot-keycloak-sample5/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-boot-keycloak-sample5/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.x-6DB33F?logo=springboot)
![Keycloak](https://img.shields.io/badge/Keycloak-26-4D4D4D?logo=keycloak)

Sample Spring Boot application that acts as an **OAuth2 Resource Server** secured by **Keycloak** and uses the
**Keycloak Admin Client** to manage users, roles and groups (create user, send verification email, forgot password,
assign/unassign roles & groups, RBAC check).

## Tech Stack

| Component             | Version                         |
|-----------------------|---------------------------------|
| Java (JDK)            | **25** (Temurin recommended)    |
| Spring Boot           | 4.1.x                           |
| Keycloak Admin Client | 26.x                            |
| Keycloak Server       | 26.x                            |
| Build tool            | Maven Wrapper (`./mvnw`)        |
| Lombok                | managed by Spring Boot          |

## Prerequisites

- JDK 25 (`java -version` should print `25`)
- Docker & Docker Compose (to run Keycloak + database)
- Postman (optional, to try the APIs)

> **Note:** Since JDK 23, `javac` no longer runs annotation processors found on the classpath implicitly.
> Lombok and the Spring Boot configuration processor are therefore registered explicitly in
> `maven-compiler-plugin` → `annotationProcessorPaths` inside `pom.xml`.

## Getting Started

1. Clone this repository:
   ```shell
   git clone https://github.com/hendisantika/spring-boot-keycloak-sample5.git
   cd spring-boot-keycloak-sample5
   ```
2. Run Keycloak, choose one of:
    - Plain container:
      ```shell
      docker run --name keycloak26 -p 7080:8080 \
        -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin \
        quay.io/keycloak/keycloak:26.0.0 start-dev
      ```
    - Keycloak + MySQL (Keycloak on port `7080`):
      ```shell
      docker compose -f mysql-docker-compose.yml up -d
      ```
    - Keycloak + PostgreSQL (Keycloak on port `9082`, adjust `app.keycloak.serverUrl` accordingly):
      ```shell
      docker compose -f postgres-docker-compose.yml up -d
      ```
3. In the Keycloak Admin Console (`http://localhost:7080`, `admin` / `admin`):
    - Create realm `PowerRanger`.
    - Configure the `admin-cli` client (client authentication + service account roles with `realm-management`
      permissions) and copy its client secret.
    - Create realm role `MERGE` to try the RBAC endpoint.
4. Adjust `src/main/resources/application.yml` if needed:
   ```yaml
   app:
     keycloak:
       admin:
         clientId: admin-cli
         clientSecret: <your-client-secret>
       realm: PowerRanger
       serverUrl: http://localhost:7080
   ```
5. Run the application:
   ```shell
   ./mvnw clean spring-boot:run
   ```
   The app runs on `http://localhost:8080/auth`.
6. Import `Spring Boot Keycloak Sample 5.postman_collection.json` into Postman and test the endpoints.

## API Endpoints

Base URL: `http://localhost:8080/auth`

| Method | Endpoint                                   | Auth   | Description                          |
|--------|--------------------------------------------|--------|--------------------------------------|
| POST   | `/users`                                   | Public | Create a new user                    |
| PUT    | `/users/{id}/send-verification-email`      | Public | Send verification email              |
| DELETE | `/users/{id}`                              | Public | Delete a user                        |
| PUT    | `/users/forgot-password?username=`         | Public | Send reset-password email            |
| GET    | `/users/{id}/roles`                        | JWT    | Get roles of a user                  |
| GET    | `/users/{id}/groups`                       | JWT    | Get groups of a user                 |
| PUT    | `/roles/assign/users/{userId}?roleName=`   | JWT    | Assign realm role to a user          |
| DELETE | `/roles/remove/users/{userId}?roleName=`   | JWT    | Remove realm role from a user        |
| PUT    | `/groups/{groupId}/assign/users/{userId}`  | JWT    | Add user to a group                  |
| DELETE | `/groups/{groupId}/remove/users/{userId}`  | JWT    | Remove user from a group             |
| GET    | `/rbac/merge-role`                         | JWT    | Accessible only with role `MERGE`    |

Example request body for `POST /users`:

```json
{
  "username": "yuji",
  "password": "53cret",
  "firstName": "Yuji",
  "lastName": "Itadori"
}
```

Protected endpoints require a bearer token issued by the `PowerRanger` realm:

```shell
curl -H "Authorization: Bearer <access_token>" http://localhost:8080/auth/users/<id>/roles
```

## Build & Test

```shell
./mvnw clean package
```

## CI/CD & Dependency Management

- **GitHub Actions** (`.github/workflows/maven.yml`): builds and tests the project with Temurin **JDK 25** on every
  push / pull request to `main` (Maven cache enabled, concurrent runs on the same ref are cancelled).
- **Dependabot** (`.github/dependabot.yml`):
    - Maven: checked **daily** at 05:00 Asia/Jakarta, updates grouped into `spring-boot`, `keycloak`,
      `maven-plugins` and `minor-and-patch` to reduce PR noise.
    - GitHub Actions: checked **weekly** (Monday 05:00 Asia/Jakarta), all actions grouped in one PR.
    - PRs are labeled (`dependencies`, `java` / `github_actions`) and use conventional commit prefixes
      (`chore(deps)`, `ci(deps)`).

## Author

**Hendi Santika** — [s.id/hendisantika](https://s.id/hendisantika) · Telegram: [@hendisantika34](https://t.me/hendisantika34)
