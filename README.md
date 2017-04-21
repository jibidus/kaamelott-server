**This project is under development**

[![Build Status](https://travis-ci.org/jibidus/kaamelott-server.svg?branch=master)](https://travis-ci.org/jibidus/kaamelott-server) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/c0b6091c42f048a6864a087490baa150)](https://www.codacy.com/app/Jibidus/kaamelott-server?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jibidus/kaamelott-server&amp;utm_campaign=badger)

# Prerequisites

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c0b6091c42f048a6864a087490baa150)](https://www.codacy.com/app/Jibidus/kaamelott-server?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jibidus/kaamelott-server&amp;utm_campaign=badger)

- Java 8

# Start server in localhost
- Run application
```shell
$ ./gradlew bootRun
```
- Go to `http://localhost:8080`

# Deploy to Heroku
```shell
$ heroku apps:create my-app-name
$ heroku addons:create heroku-postgresql:hobby-dev
$ git push heroku master
```

# Start production in localhost with postgresql in Docker container
- Start PostgreSQL Docker container
```shell
$ docker run --name kaamelott-server-postgres -e POSTGRES_USER=kaamelott -e POSTGRES_PASSWORD=kaamelott -p 5432:5432 -d postgres:alpine
```

-  Run application
```shell
$ export JDBC_DATABASE_URL=jdbc:postgresql://[container IP]:5432/kaamelott?user=kaamelott&password=kaamelott
$ SPRING_PROFILES_ACTIVE=production ./gradlew bootRun
```

