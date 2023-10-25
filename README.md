# SOCIAME

A simple (and basic) social network example.

## Run

Create an `/env` file with the following format:

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
- [ ] Registration Step 1 (Email invitation).
- [ ] Registration Step 2 (Password setup).
- [ ] Upgrade plan.
- [ ] Downgrade plan.
- [ ] Follow account.
- [ ] Unfollow account.
- [ ] View followers and following accounts list.
- [ ] Search accounts.
- [ ] Make post.
- [ ] Comment on post.
- [ ] Get own posts.
- [ ] Get following accounts posts.
- [ ] Get followers accounts posts.