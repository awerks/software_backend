## This project is a part of the Software Engineering course

## [Database documentation](https://dbdocs.io/awerks/se_project?view=relationships)

## [API documentation](https://app.swaggerhub.com/apis/softwareproject-afb/Software_project/1.0.0)

## [Backend Proxy](https://javaspringboot-production-c0c1.up.railway.app)

## Install `railway-cli`

```bash
curl -fsSL https://railway.com/install.sh | sh
```

## Connect to this project

```bash
railway link
```

## Install MySQL client

#### For MacOS

```bash
brew install mysql
```

#### For Windows

```bash
winget install mysql
```

#### For Linux

```bash
sudo apt install mysql-client
```

## Connect to MySQL

```bash
mysql -h <host> -u <user> -P <port> -p <database>
```

or with `railway-cli`

```bash
railway connect
```

## Run the project locally

```bash
./mvnw spring-boot:run
```

## Make and push changes to the project

```bash
git add .
git commit -m "your message"
git push
```

Railway will automatically deploy the changes to the production environment.

or using `railway-cli`

```bash
railway up
```
