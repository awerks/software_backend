## This project is a part of the Software Engineering course

## [Database documentation](https://dbdocs.io/awerks/se_project?view=relationships)

## [API documentation](https://app.swaggerhub.com/apis/softwareproject-afb/Software_project/1.0.0)

## [Backend Proxy](https://se-backend.up.railway.app)

## Local documentation in the `documentation` branch

[View Local Documentation](https://github.com/awerks/software_backend/tree/documentation)

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

replace `<host>`, `<user>`, `<port>` and `<database>` with the values from the railway database variables

or with `railway-cli`

```bash
railway connect
```

## Run the project locally

```bash
./mvnw spring-boot:run
```

access the server at `http://localhost:8080`

## Install dbdocs and login

```bash
npm install -g dbdocs && dbdocs login
```

## Generate DBML

```bash
dbdocs db2dbml mysql <connection-string> -o database.dbml
```

get the connection string from the railway dashboard (`MYSQL_PUBLIC_URL`)

## If you make changes to the database, publish the changes to dbdocs

```bash
dbdocs build database.dbml --project awerks/se_project
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
