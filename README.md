# Praktikum SS 2026 Termin 3 No 2 (LMS With GUI)

## About

Goal is it turn the appointment 2 architecture into an MVP architecture
(but with GUI in the 2nd exercise)

## Result

Seems to be ok at least imo.
It works with MySQL driver,
no SQL errors.
File service easy replacable by database service at all.

## Docker + MySQL Setup

| Purpose | Docker Command | Example Output |
|----------|----------|----------|
| Download the latest MySQL image | `docker pull mysql:latest` | ```text\nC:\Users\Office>docker pull mysql:latest\nlatest: Pulling from library/mysql\nDigest: sha256:c11782aa2a96624c1efc121768641d96954faa136d6aa82751b032d8c426ffbc\nStatus: Image is up to date for mysql:latest\ndocker.io/library/mysql:latest\n\nWhat's next:\n    View a summary of image vulnerabilities and recommendations → docker scout quickview mysql:latest\n``` |
| Start a MySQL container. Environment variables are specified via `-e`, and ports are mapped via `-p`. The left port (`33060`) belongs to the host machine, while the right port (`3306`) is the port exposed inside the container. | `docker run -d --name mysql-cont-1 -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 mysql:latest` | ```text\nC:\Users\Office>docker run -d --name mysql-cont-1 -e MYSQL_ROOT_PASSWORD=password -p 33060:3306 mysql:latest\na717980e24b86f263b0a66b3b62894ef46440ef1a5ea1a9b071cd03522d2a430\n``` |
| List all running containers | `docker container ls` | ```text\nC:\Users\Office>docker container ls\nCONTAINER ID   IMAGE          COMMAND                  CREATED          STATUS          PORTS                                           NAMES\na717980e24b8   mysql:latest   \"docker-entrypoint.s…\"   20 minutes ago   Up 20 minutes   0.0.0.0:33060->3306/tcp, [::]:33060->3306/tcp   mysql-cont-1\n[additional containers would be listed line by line]\n``` |
| List all containers (including stopped ones) | `docker container ls -a` | Displays both running and stopped containers. |
| Open a MySQL shell inside the container | `docker exec -it <identifier> mysql -u root -p` | ```text\nC:\Users\Office>docker exec -it a717980e24b8 mysql -u root -p\nEnter password:\n\nWelcome to the MySQL monitor. Commands end with ; or \\g.\nYour MySQL connection id is 9\nServer version: 9.7.0 MySQL Community Server - GPL\n\nCopyright (c) 2000, 2026, Oracle and/or its affiliates.\n\nType 'help;' or '\\h' for help. Type '\\c' to clear the current input statement.\n\nmysql>\n``` |
| Notes regarding login | — | - The identifier can be either the container ID (`a717980e24b8`) or the container name (`mysql-cont-1`).<br>- `root` is the database user.<br>- `-p` stands for **password prompt**, not port.<br>- The password is `password`, as configured via `MYSQL_ROOT_PASSWORD=password`. |
| Execute any SQL command | `<SQL command>;` | ```text\nmysql> create database lms;\nQuery OK, 1 row affected (0.101 sec)\n\nmysql>\n``` |
| Create the database before starting the application for the first time | `create database lms;` | The `lms` database must exist before launching the application. Missing tables can then be created automatically by the application. |
| Exit the MySQL shell | `quit` | ```text\nmysql> quit\nBye\n\nWhat's next:\n    Try Docker Debug for seamless, persistent debugging tools in any container or image → docker debug a717980e24b8\n    Learn more at https://docs.docker.com/go/debug-cli/\n\nC:\Users\Office>\n``` |

## Application Prerequisites

Before starting the application for the first time, create the database:

```sql
CREATE DATABASE lms;
```

Once the database exists, the application will connect to it and automatically create any missing tables if necessary.

## Getting started

### GitLab Token For IDE Use

glpat-IkCQeZPpDcGwEnkG7dYQCm86MQp1OjN0Nwk.01.0z01ekrbd