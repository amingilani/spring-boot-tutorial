# Zero to Spring Boot in Seven Days

This is the story of how I went from zero to Spring Boot in seven days, and how you can too. This entire article is based on my experience alone. Expect plenty
of Nodejs and Ruby references.

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

## Spring Boot

## Goal

For this project, we'll be creating a Spring app that lets us sign in, and get advice.

## Maven

Maven is a build automation tool for Java projects. It defines project details, manages dependencies, and can execute builds. Loosely translated, it's
like `package.json` and `grunt` for Java, all packaged into one, with Rails-like
conventions instead of configurations.

## Prerequisites

It is assumed that you:

  - Can Git it
  - Are comfortable with the command line
  - Have prior programming experience

## Precursors

  1. Learn Java
    - [Codecademy](https://www.codecademy.com/learn/learn-java)
    - [Learn X in Y Minutes](https://learnxinyminutes.com/docs/java/)
  2. Learn about Maven
    - [Maven in 5 Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
    - [Getting Started with Maven](http://spring.io/guides/gs/maven/)
  3. Spring:
    - [Spring Framework Quickstart](https://projects.spring.io/spring-framework/) -- Don't go deep
    - [Spring Boot Quickstart](http://projects.spring.io/spring-boot/)
