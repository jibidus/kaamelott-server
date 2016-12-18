**This project is under development**

[![Build Status](https://travis-ci.org/jibidus/kaamelott-server.svg?branch=master)](https://travis-ci.org/jibidus/kaamelott-server) [![Codacy Badge](https://api.codacy.com/project/badge/grade/7fce461304114f39a1413ddb2d3466a0)](https://www.codacy.com/app/jean-baptiste_2/kaamelott-server)

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