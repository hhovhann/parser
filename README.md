# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)


# Before running aplication
1. You need to have installed Mysql Server and running inport 3310 in your case that can be 3306 which is default one
2. You need to create database schema please check databaseSchema.md inside of the project
3. In case if table will not be created you can do that manualy before runing the application, please check database schema
4. You need to have installed in your maschine Java 1.8
5. 

#How to run .....

#Stack of technologies
Java version 1.8.0_201
SpringBoot version 2.1.5.RELEASE
Maven version 5.0

#Nice to have but have a gap here :)


SQL test queries

Hibernate: create table access_log (log_id bigint not null, ip_address varchar(255) not null, request varchar(255), start_date datetime, status integer, user_agent varchar(255), primary key (log_id)) engine=MyISAM
Hibernate: create table hibernate_sequence (next_val bigint) engine=MyISAM