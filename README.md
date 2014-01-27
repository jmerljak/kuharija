Kuharija
========

**Kuharija** is a prototype of culinary educational rich internet application. It was developed as a part of my master's thesis (Development of personalized user interface for media-rich educational web application) and evaluated with two iterations of usability testing. It is mobile-friendly, supports internationalization and (basic) personalization.


Usage
------------
Let's suppose you have:
- installed and basically configured JBoss Application Server 7
- installed PostgreSQL 9, created database ${db.name} in default tablespace and granted privileges to user ${db.user} with password ${db.password}
- replaced dummy database name, username and password in properties section of pom.xml with real ones

Than you can compile and deploy application (including JDBC driver and datasource) to JBoss Application Server 7 in one step!

`mvn clean apt:process install jboss-as:deploy`

Maven build also creates database tables, but does not insert any data.

Application is accessible at http://localhost:8080/kuharija/


Used tools, technologies and libraries
------------------------
- Maven
- Java
- JPA, Hibernate
- QueryDsl
- Joda Time
- Guava
- GWT
- GWTBootstrap
- ...


Copyright
---------
You are (except as otherwise noted) free to use, copy, fork, adapt and redistribute all of the project's source code, but please give the credit.


Jakob Merljak, 2013.
