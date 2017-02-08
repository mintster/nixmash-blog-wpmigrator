WordPress to NixMash Blog Engine Migrator
==========================

Migrates a WordPress blog content to a destination MySQL database with the NixMash Blog schema. Built with Gradle in Spring Boot using Spring JPA. It also uses Kamran Zafar's excellent Spring WordPress WP-API Client to migrated rendered WP Post content.

- [NixMash Spring Repository](https://github.com/mintster/spring-data)
- [Spring WordPress WP-API Client](https://github.com/kamranzafar/spring-wpapi)

##Project Status

This application, the **WordPress to NixMash Blog Migrator** is complete. The NixMash Blog Engine is a reduction of the NixMash Spring code base and is still in development. When complete it will be announced here and elsewhere.

##Installation Overview

- MySQL setup scripts located in `/install/mysql/.` Run `setup.sql` in empty destination MySQL database, `wpsetup.sql` in WordPress database to create four utility SQL Views.
- Update `application.properties` in both `/test/resources` and `/main/resources` for blogger username, WordPress API blog Url, WordPress Origination and Destination datasources

##Running the Application

You can either run the migrator inside of your IDE or use the SpringBoot Gradle Plugin to create an Executable JAR.

```js
PROJECT_ROOT> $ gradle clean bootRepackage
JAR_LOCATION> $ java -jar wpspring-exec.jar
```

##Output

The Output of the application is a MySQL database fully populated with your WordPress Blog posts, categories and tags. A new NixMash Blog user account is created with the data you provided in `application.properties` with the default password of *"password."* 

This is what a successful execution of the WP Migrator application looks like.

![WordPress to NixMash Blog Migrator Execution](http://nixmash.com/x/pics/github/wpspring0125a.png)


