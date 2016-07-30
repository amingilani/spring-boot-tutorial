# Zero to Spring Boot in Seven Days

This is the story of how I went from zero to Spring Boot in seven days, and how you can too. This entire article is based on my experience alone. Expect plenty
of Nodejs and Ruby references.

## Goal

For this project, we'll be creating a Spring app that lets us sign in, and get advice.

### Prerequisites

It is assumed that you:

  - Can use Git
  - Are comfortable with the command line
  - Have prior programming experience


## Spring

The Spring Framework is Platform that provides you the building blocks to
a great Java app. This lets you focus on business logic without
being bogged down by the other stuff.

While the Java Platform is robust, as a developer your time is better spent
implementing your own application, as opposed to creating an ORM for your
database layer, or a web framework. This is where Spring makes things easier.

Spring provides a number of Java objects which abstract away these low level
tasks. These objects encapsulate best practices, and allow you to build a whole
web application from "Plain Old Java Objects" (POJOs).

### Dependency Injection

The Spring Framework injects its modules automagically when required, during the
build process. Focus on building your own application and not the

### Modules

![Spring Modules](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/images/spring-overview.png)

Spring organizes its features in a set of about 20 modules, grouped into:

1. **Core**  
  fundamental spring modules.
2. **AOP and Instrumentation**  
  an AOP implementation to help you decouple code that doesn't belong in business logic itself.  
  e.g. running `Log( User + " made a " + transaction.type);` every time a user makes a transaction from their bank account.  
  This way we see `"John made a deposit"` when John makes a deposit.  
  AOP would allow us to write this rule in an aspect, keeping the transaction related code clean.
3. **Messaging**  
  Support for integrating with messaging systems like AQMP and STOMP.
4. **Data Access/Integration**  
  This contains the `JDBC`, `ORM`, `OXM`, `JMS`, and `Transaction` modules.
5. **Web**  
   Things related to web, including HTTP, MVC and Web Sockets.
6. **Test**  
  Unit and Integration testing frameworks

### Spring Boot

Getting a Spring application running can be tedious and daunting. That's why
Spring Boot "takes and opinionated view of building Spring applications and gets
you up and running as quickly as possible."

The idea is that Spring Boot lets you bootstrap your project, and then gets out
of your way as the project starts becoming more complex.

Spring boot also uses a large amount of convention over configuration, so a lot
of times *things just work.*

## Building the application

Let's break this down into subtasks:

0. **Bootstrapping with Spring Initializer**  
  Precursors to the project

1. **Making A Web App**  
  A Spring MVC app that serves static content

2. **Securing The Web App**  
  Using Spring Security to enable authentication for the with an in-memory user

3. **Attaching a Database**  
  Creating Models for our User and saving them to a database on registration.

4. **Switching Spring Security to Use The Database**  
  Making Spring Security use our User Model for Authentication.

We'll be using Maven for our build.

### Maven

Maven is a build automation tool for Java projects. It defines project details,
manages dependencies, and can execute builds. Loosely translated, it's
like `package.json` and `grunt` for Java, all packaged into one, with build
conventions baked in.

### Bootstrapping with Spring Initializers

To get started with a Spring Boot project, the quickest way is to use
[Spring Initializer](https://start.spring.io) and select all the modules that
you would require. For this project we'll need:

1. Web
2. Thymeleaf
3. JPA
4. H2
5. Security

We'll be using Maven for our build.

TLDR: run:
`curl start.spring.io/starter.zip -d dependencies=web,data-jpa,thymeleaf,h2,security -d javaVersion=1.8 -d applicationName=myapp -d artifactId=myapp -d packageName=myapp -d type=maven-project -d packaging=jar -o initial.zip`

Go ahead and explore the `pom.xml` file, this is the manifest for your project


#### Making A Web App

First, go ahead and comment the `spring-boot-starter-security` dependency. Until we configure the `security` package, it defaults to restricting
everything behind a `401 Unauthorized` error.

```xml
<!-- <dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency> -->
```

## Resources

  1. Learn Java
    - [Codecademy](https://www.codecademy.com/learn/learn-java)
    - [Learn X in Y Minutes](https://learnxinyminutes.com/docs/java/)
  2. Learn about Maven
    - [Maven in 5 Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
    - [Getting Started with Maven](http://spring.io/guides/gs/maven/)
  3. Spring:
    - [Spring Framework Quickstart](https://projects.spring.io/spring-framework/)
    - [Spring Framework Reference](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/)
    - [Spring Boot Quickstart](http://projects.spring.io/spring-boot/)
    - [Spring Boot Reference Documentation](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle)
