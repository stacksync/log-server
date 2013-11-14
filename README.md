Welcome to StackSync!
=====================

> **NOTE:** This is BETA quality code!

**Table of Contents**

- [Introduction](#introduction)
- [Architecture](#architecture)
- [Log server](#log-server)
  - [REST API](#rest-api)
    - [Requirements](#requirements)
    - [Installation](#installation)
  - [Batch process](#batch-process)
    - [Requirements](#requirements)
    - [Database initialization](#database-initialization)
    - [Installation](#installation)
- [Issue Tracking](#issue-tracking)
- [Licensing](#licensing)
- [Contact](#contact)

# Introduction

StackSync (<http://stacksync.com>) is a scalable open source Personal Cloud
that implements the basic components to create a synchronization tool.


# Architecture

In general terms, StackSync can be divided into three main blocks: clients
(desktop and mobile), synchronization service (SyncService) and storage
service (Swift, Amazon S3, FTP...). An overview of the architecture
with the main components and their interaction is shown in the following image.

<p align="center">
  <img width="500" src="https://raw.github.com/stacksync/desktop/master/res/stacksync-architecture.png">
</p>

The StackSync client and the SyncService interact through the communication
middleware called ObjectMQ. The sync service interacts with the metadata
database. The StackSync client directly interacts with the storage back-end
to upload and download files.

As storage back-end we are using OpenStack Swift, an open source cloud storage
software where you can store and retrieve lots of data in virtual containers.
It's based on the Cloud Files offering from Rackspace. But it is also possible
to use other storage back-ends, such as a FTP server or S3.

# Log server

The aim of this log server is to receive error logs from clients to process and
store them in a database. This allows us to study the errors and find possible
bugs in the clients.

This service has two different parts:

## REST API

The client uses a REST API to send the logs to the server. API specification:

- URL structure: http://domain.ext/api/version
- Parameters: The URL can be specified with the following optional query:
  
  | Field         | Description        |
  | ------------- | ------------------ |
  | type      | If the value is "compress" means that the content body will be gzipped. |


- Method: PUT
- Request headers:
  
  | Field         | Description        |
  | ------------- | ------------------ |
  | computer      | The computer name. |

- Request body: The content of the log file.

When the API receives a log file. It will be stored in the /var/log/stacksync

### Requirements

- [Tonic](https://github.com/peej/tonic)
- PHP5

### Installation

- Install tonic under /usr/local/tonic
- Create a folder in /var/www to host your REST API.
- Copy the code from the [server](server) folder.
 
## Batch process
This process reads the files in the /var/log/stacksync folder and insert the
logs in the database for a future revision. When the log is processed it will
move the files into a processed folder, to avoid read more than one time the
same log.

This task can be periodically executed with cron.

### Requirements

- Java 1.7
- Maven
- Database server (we use PostgreSQL)

### Database initialization

First, you need to install the required libs using the command below:

    $ sudo apt-get install libpq-dev

Open a terminal and run the following commands to add the repository containing
PostgreSQL 9.2 and install it:

    $ sudo add-apt-repository ppa:pitti/postgresql
    $ sudo apt-get update
    $ sudo apt-get install postgresql-9.2
    $ sudo apt-get upgrade

In order to initialize the database we need to create the database and the user
and execute the script “setup_db.sql” located in "src/main/resources".

First, enter in a Postgres command line mode:

    $ sudo -u postgres psql

Execute the commands below to create a user and the database. The database must
be called stacksync:

    postgres=# create database logs;
    postgres=# create user db_user with password 'db_pass';
    postgres=# grant all privileges on database logs to db_user;
    postgres=# \q

Enter to the database with the user role created. Note that the first parameter
is the host, the second is the database name and the last one is the username:

    $ psql -h localhost stacksync stacksync_user

Now run execute the script.

    postgres=# \i ./log_db.sql
    postgres=# \q

At this point, the database is ready for store logs!

### Build and execution

First of all intall the database

To create an executable jar:

    $ mvn assembly:assembly
    
Execution parameters:

- **Help (-h/-help)**: Print help message.
- **Config file (-c/-config)**: Path to the configuration file
- **Dump config (-dump-config)**: Dumps an example of configuration file. You
can redirect the output to the new file to edit the configuration.
- **Version (-V/-version)**: Print version.

When log-service is executed, it reads the config.properties that is at the
same level, or the one specified using the -c parameter. Here is an example
of config.properties:

    # StackSync - SyncService configuration
    #
    # Type of database used as metadata backend.
    # For the moment, only 'postgresql' is available.
    datasource=postgresql
    #
    # Logs path
    logs.path=/var/log/stacksync
    #
    # Logs path processed folder
    logs.processed.folder=processed
    #
    # PostgreSQL configuration
    # ========================
    # 
    # Host
    postgresql.host=localhost
    #
    # Port 
    postgresql.port=5432
    #
    # Database
    postgresql.database=logs
    #
    # User
    postgresql.user=db_user
    #
    # Password
    postgresql.password=user_pass
    
You have to change the values to access to the database.

Finally, run the jar file:

    $ java -jar log-server-1.0.2-jar-with-dependencies.jar

# Issue Tracking
For the moment, we are going to use the github issue tracking.

# Licensing
StackSync is licensed under the GPLv3. Check [license.txt](license.txt) for the latest
licensing information.

# Contact
Visit www.stacksync.com to contact information.
