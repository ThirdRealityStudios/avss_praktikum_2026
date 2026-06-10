# Praktikum SS 2026 Termin 4 No 1 (Client With GUI)

## About

Goal is it turn the appointment 3 architecture into a "hybrid" MVP / ModelConsumer architecture,
so a client communicates with the server via HTTP.
The ModelConsumer acts as a "simulated" model component because it does not know
business logic directly.
It does instead establish a HTTP connection to the server,
including the real business logic which takes the final decisions in the end
(e.g. whether to create a new borrower domain object persistently in DB or not).

## Debug Logs For Better Understanding / Study Project

This Client application contains debug logs (e.g. "Creating request to server with the following Json body...")
beause this project exists to understand HOW the client-server architecture works.
It is there for learning and such (although my implementation is probably not perfect - but so far bug free for normal use).
It demonstrates how you could implement a client-server application;
not how you would create it for production in econonic scales or for businesses.

## How To Execute

(Compile with IntelliJ e.g. and) open console to enter:

`java -jar "myExecutableClient.jar"`

With the yet given .jar file on this GitHub repo,
there is no need to compile and such if you only want to demonstrate the code fast.

Make sure,
the server deployable is running yet (look at the repo for instructions).

## Docker + MySQL Setup

| Purpose | Docker Command | Example Output |
|----------|----------|----------|
| Download the latest MySQL image | `docker pull mysql:latest` | `C:\Users\Office>docker pull mysql:latest`<br><br>`latest: Pulling from library/mysql`<br>`Digest: sha256:c11782aa2a96624c1efc121768641d96954faa136d6aa82751b032d8c426ffbc`<br>`Status: Image is up to date for mysql:latest`<br>`docker.io/library/mysql:latest` |
| Start a MySQL container. Environment variables are specified via `-e`, and ports are mapped via `-p`. The left port (`33060`) belongs to the host machine, while the right port (`3306`) is the port exposed inside the container. | `docker run -d --name mysql-cont-1 -e MYSQL_ROOT_PASSWORD=password -p 33060:3306 mysql:latest` | `C:\Users\Office>docker run -d --name mysql-cont-1 -e MYSQL_ROOT_PASSWORD=password -p 33060:3306 mysql:latest`<br><br>`a717980e24b86f263b0a66b3b62894ef46440ef1a5ea1a9b071cd03522d2a430` |
| List all running containers | `docker container ls` | `C:\Users\Office>docker container ls`<br><br>`CONTAINER ID   IMAGE          COMMAND                  CREATED          STATUS          PORTS                                           NAMES`<br>`a717980e24b8   mysql:latest   "docker-entrypoint.s…"   20 minutes ago   Up 20 minutes   0.0.0.0:3306->3306/tcp, [::]:33060->3306/tcp   mysql-cont-1`<br>`[additional containers would be listed line by line]` |
| List all containers (including stopped ones) | `docker container ls -a` | Displays both running and stopped containers. |
| Open a MySQL shell inside the container | `docker exec -it <identifier> mysql -u root -p` | `C:\Users\Office>docker exec -it a717980e24b8 mysql -u root -p`<br><br>`Enter password:`<br><br>`Welcome to the MySQL monitor. Commands end with ; or \g.`<br>`Your MySQL connection id is 9`<br>`Server version: 9.7.0 MySQL Community Server - GPL`<br><br>`Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.`<br><br>`mysql>` |
| Notes regarding login | — | - The identifier can be either the container ID (`a717980e24b8`) or the container name (`mysql-cont-1`).<br>- `root` is the database user.<br>- `-p` stands for **password prompt**, not port.<br>- The password is `password`, as configured via `MYSQL_ROOT_PASSWORD=password`. |
| Execute any SQL command | `<SQL command>;` | `mysql> create database lms;`<br><br>`Query OK, 1 row affected (0.101 sec)` |
| Create the database before starting the application for the first time | `create database lms;` | The `lms` database must exist before launching the application. Missing tables can then be created automatically by the application. |
| Exit the MySQL shell | `quit` | `mysql> quit`<br><br>`Bye`<br><br>`C:\Users\Office>` |

## Application Prerequisites

Before starting the application for the first time, create the database:

```sql
CREATE DATABASE lms;
```

Once the database exists, the application will connect to it and automatically create any missing tables if necessary.

## Getting started

### GitLab Token For IDE Use

glpat-IkCQeZPpDcGwEnkG7dYQCm86MQp1OjN0Nwk.01.0z01ekrbd
