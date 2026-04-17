# Blogger Box – Backend

API REST Spring Boot pour le projet Blogger Box (M1 MIAGE Dauphine).

## Stack

- Java 21
- Spring Boot 3.4.4
- Maven
- PostgreSQL (Supabase)
- Swagger UI disponible sur `http://localhost:8080/swagger-ui/index.html`

## Prérequis

- Java 21+
- Maven 3.8+
- Une base PostgreSQL accessible (ex. Supabase)

## Configuration

Copier le fichier d'exemple et renseigner les variables :

```bash
cp .env.example .env
```

| Variable      | Description                     | Exemple                          |
|---------------|---------------------------------|----------------------------------|
| `DB_HOST`     | Hôte PostgreSQL                 | `db.xxxx.supabase.co`            |
| `DB_PORT`     | Port PostgreSQL                 | `5432`                           |
| `DB_NAME`     | Nom de la base                  | `postgres`                       |
| `DB_USERNAME` | Utilisateur                     | `postgres.xxxx`                  |
| `DB_PASSWORD` | Mot de passe                    | `your_password`                  |

> `spring.jpa.hibernate.ddl-auto=validate` : le schéma doit exister avant le démarrage.  
> Schéma minimal :
> ```sql
> CREATE TABLE category (
>     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
>     name VARCHAR(255) NOT NULL
> );
>
> CREATE TABLE post (
>     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
>     title VARCHAR(255) NOT NULL,
>     content TEXT,
>     created_date TIMESTAMP,
>     category_id UUID REFERENCES category(id)
> );
> ```

## Lancement

```bash
export DB_HOST=... DB_PORT=5432 DB_NAME=... DB_USERNAME=... DB_PASSWORD=...
mvn spring-boot:run
```

Ou directement depuis IntelliJ IDEA avec les variables d'environnement dans la run configuration.

## Endpoints principaux

| Méthode | URL                          | Description                        |
|---------|------------------------------|------------------------------------|
| GET     | `/v1/categories`             | Liste des catégories (filtre ?name=)|
| POST    | `/v1/categories`             | Créer une catégorie                |
| PUT     | `/v1/categories/{id}`        | Modifier une catégorie             |
| PATCH   | `/v1/categories/{id}`        | Modifier le nom d'une catégorie    |
| DELETE  | `/v1/categories/{id}`        | Supprimer une catégorie            |
| GET     | `/v1/posts`                  | Liste des posts (filtre ?value=)   |
| POST    | `/v1/posts`                  | Créer un post                      |
| PUT     | `/v1/posts/{id}`             | Modifier un post                   |
| DELETE  | `/v1/posts/{id}`             | Supprimer un post                  |
| GET     | `/v1/categories/{id}/posts`  | Posts d'une catégorie              |

Documentation complète : `http://localhost:8080/swagger-ui/index.html`
