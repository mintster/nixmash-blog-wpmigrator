WordPress to NixMash Blog Engine Migrator
==========================

Migrates the posts, categories and tags of a WordPress blog to a destination MySQL database with the NixMash Blog Engine schema. The WordPress to NixMash Blog Migrator was built in Spring Boot. It also uses Kamran Zafar's excellent Spring WordPress WP-API Client to migrate rendered WP Post content.

- [NixMash Blog Engine](https://github.com/mintster/nixmash-blog)
- [NixMash Spring Repository](https://github.com/mintster/spring-data)
- [Spring WordPress WP-API Client](https://github.com/kamranzafar/spring-wpapi)

*Be sure to read [the Project Wiki](https://github.com/mintster/wp-nixmash-blog-migrator/wiki) for configuration and setup information.*

## Project Status

This application, the **WordPress to NixMash Blog Migrator** is complete. The **NixMash Blog Engine** is online and can be viewed at [NixMash](http://nixmash.com). The source code of the **NixMash Blog Engine** is found in [here.](https://github.com/mintster/nixmash-blog)

## Installation Overview

- MySQL setup scripts located in `/install/mysql/.` Run `setup.sql` in your empty destination MySQL database, and `wpsetup.sql` in WordPress database to create four utility SQL Views.
- Update `application.properties` in both `/test/resources` and `/main/resources` for 1) blogger username, 2) WordPress API blog Url, 3) WordPress Origination datasource and 4) NixMash Blog Destination datasource

## Running the Application

You can either run the migrator inside of your IDE or use the SpringBoot Gradle Plugin to create an Executable JAR.

```bash
PROJECT_ROOT> $ gradle clean bootRepackage
JAR_LOCATION> $ java -jar wpspring-exec.jar
```

You can run the migrator in your IDE Terminal View with the Spring Gradle Plugin `bootRun` task

```bash
PROJECT_ROOT> $ gradle bootRun
```

You can also perform a complete migration within your IntelliJ, Eclipse or NetBeans IDE.

## Output

The Output of the application is a MySQL database containing all of your WordPress Blog posts, categories and tags. A new NixMash Blog user account is created with the data you provided in `application.properties` with a default password of *"password."* 

This is what a successful execution of the WP Migrator application looks like.

![WordPress to NixMash Blog Migrator Execution](http://nixmash.com/x/pics/github/wpspring0125a.png)


