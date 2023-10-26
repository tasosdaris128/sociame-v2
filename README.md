# SOCIAME

A simple (and basic) social network example.

## Run

Create an `.env` file with the following format:

```bash
#!/bin/bash

export APP_PORT="Application port"
export PGHOST="Postgres IP or domain"
export PGPORT="Postgres port"
export PGCTL="Postgres database name"
export PGUSR="Postgres username"
export PGPWD="Postgres password"
export MAIL_FROM="Mail sender ID"
export MAIL_HOST="SMTP host"
export MAIL_PORT="SMTP port"
export MAIL_USERNAME="Mail application username"
export MAIL_PASSWORD="Mail application password"
export JWT_EXPIRATION_IN_MINUTES="Token expiration"
export JWT_SECRET="JWT secret"
```

Then, just run `./makew` in order to run it.

## Tech stack

- Java 17.
- Maven.
- PostgreSQL.
- Spring Boot.

## Feature list

- [X] Login.
- [X] Get user/account info.
- [X] Registration Step 1 (Email invitation).
- [X] Registration Step 2 (Password setup).
- [X] Upgrade plan.
- [X] Downgrade plan.
- [X] Follow account.
- [X] Unfollow account.
- [X] View followers and following accounts list.
- [X] Search accounts.
- [X] Make post.
- [ ] Comment on post.
- [X] Get own posts.
- [X] Get following accounts posts.
- [X] Get followers accounts posts.
- [ ] Get comments of post.

## Posting business logic

- Free users can post small text with a limit of 1000 characters.
- Premium users can post text with a limit of 3000 characters.
- Free users can comment up to 5 mes per post.
- Premium users can comment unlimited mes.