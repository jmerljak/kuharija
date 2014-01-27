Kuharija
========
**Kuharija** (Culinary Masterpieces) is a prototype of culinary educational rich internet application. It was developed as a part of my master's thesis (*Development of personalized user interface for media-rich educational web application*) and evaluated with two iterations of usability testing. It is mobile-friendly, supports internationalization and (basic) personalization.


Installation
------------
Let's assume you have:
- installed and basically configured JBoss Application Server 7,
- installed PostgreSQL 9, created database `${db.name}` in default tablespace and granted privileges to user `${db.user}` with password `${db.password}`,
- replaced dummy database name, username and password in properties section of pom.xml with real ones.

Than you can compile and deploy application (including JDBC driver and datasource) to JBoss Application Server 7 in one step!

`mvn clean apt:process install jboss-as:deploy`

Application is then accessible at [http://localhost:8080/kuharija/](http://localhost:8080/kuharija/).

Note: Deploy also creates database tables, but does not insert any data. You can insert basic data from provided script at `/src/main/resources/db/basic_data.sql`.

Developers
------------
You can easily import this project into your favourite IDE. For example, in Eclipse use `Import` and then `Existing Maven projects`. Essentially you have to configure Java build path to include `/target/generated-sources/java` and run `mvn apt:process` to generate QueryDsl classes.

Used tools, technologies and libraries:
- [https://maven.apache.org/](Apache Maven)
- [https://www.java.com/](Java)
- JPA, Hibernate
- [http://www.querydsl.com/](QueryDsl)
- [http://www.joda.org/joda-time/](Joda Time)
- [https://code.google.com/p/guava-libraries/](Guava)
- [http://www.gwtproject.org/](GWT)
- [http://gwtbootstrap.github.io/](GWTBootstrap)
- ...


Copyright
---------
You are (except as otherwise noted) free to use, copy, fork, adapt and redistribute all of the project's source code, but please give the credit.


Jakob Merljak, 2013.
