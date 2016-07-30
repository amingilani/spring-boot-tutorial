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

2. **Adding Sign Up functionality**  
  Creating Models for our User and saving them to a database on registration.

3. **Securing The Web App**  
  Using Spring Security to enable authentication for the with an in-memory user

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


### Making A Web App

We're going to make a web application with no security.

#### Removing Security

First, go ahead and temporarily comment the `spring-boot-starter-security` dependency, until we configure the `security` package later on. It defaults to restricting
everything behind a `401 Unauthorized` error, which isn't needed at the moment.

```xml
<!-- <dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency> -->
```

#### Building the Advice page

The first and page we'll build is the simples, a page that displays advice to the visitor, this will later on be our
password protected page

##### Adding template

```html
<!-- main/resources/templates/advice.html -->
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
    <title>Spring Boot Tutorial | Login</title>
    <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css' />
    <link rel="stylesheet" type="text/css" href="/assets/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="/assets/css/style.css" />

</head>

<body>
    <h2 id="advice">Wait for it...</h2>
    <button class="advice">more</button>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    <script src="/assets/js/advice.js"></script>
</body>

</html>
```

The HTML above should be quite simple to understand. It displays a line of text that says 'Wait for it...' and a 'more button'

```js
var getAdvice = function() {
    $.getJSON('http://api.adviceslip.com/advice', function(data) {
        $("h2#advice").replaceWith('<h2 id="advice">' + data.slip.advice + '</h2>');
    });
};

getAdvice();

$('button.advice').on('click', function() {
    getAdvice();
});
```
This uses jQuery to fetch a JSON object from the api at adviceslips.com, and replaces the "Wait for it..." on the html. It also adds functionality to our button.

##### Adding an advice controller

```java
// main/java/myapp/controller/AdviceController.java
@Controller
public class AdviceController {

@RequestMapping(value = "/advice", method = RequestMethod.GET)
public String showAdvice() {
        return "advice";
}

}

```
This controller maps on the Thymeleaf template onto `GET /advice`

### Adding Sign Up functionality

We'll now be:

1. Creating a User Entity
2. Creating a User Repository
3. Creating a Signup page

#### Creating a User Entity

```java
// main/java/myapp/entity/User.java
@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String userName;
    private String password;

    protected User() {}

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

// standard getters and setters

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, userName='%s', password='%s']",
                id, userName, password);
    }

}

```

This is the entity that will be saved to our database, a User only has a `username` and `password`

#### Creating a User Repository

```java
public interface UserRepository extends CrudRepository<User, Long> {

User findByUserName(String UserName);
}
```

This repository inherits from the `CrudRepository` in the `data` dependency, and loads users from their usernames

#### Creating a Signup page


#### Add the template

First add [normalize.css](github.com/necolas/normalize.css) to `main/resources/static/css/normalize.css`

```html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
    <title>Spring Boot Tutorial | Login</title>
    <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css' />
    <link rel="stylesheet" type="text/css" href="/assets/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="/assets/css/style.css" />

</head>

<body>
    <div th:if="${param.error}">
        Invalid username and password.
    </div>
    <div th:if="${param.logout}">
        You have been logged out.
    </div>
    <div class="form">
        <ul class="tab-group">
            <li class="tab active"><a href="#login">Log In</a></li>
            <li class="tab"><a href="#signup">Sign Up</a></li>
        </ul>
        <div class="tab-content">
            <div id="login">
                <h1>Get your advice inside!</h1>
                <form th:action="@{/}" method="post">
                    <div class="field-wrap">
                        <label>
                            Username<span class="req">*</span>
                        </label>
                        <input name="username" type="text" required="true" autocomplete="off" />
                    </div>
                    <div class="field-wrap">
                        <label>
                            Password<span class="req">*</span>
                        </label>
                        <input name="password" type="password" required="true" autocomplete="off" />
                    </div>
                    <button class="button button-block">Log In</button>
                </form>
            </div>
            <div id="signup">
                <h1>Sign Up for some advice.</h1>
                <form th:action="@{/register}" th:object="${user}" method="post">
                    <div class="field-wrap">
                        <label>
                            Username<span class="req">*</span>
                        </label>
                        <input name="userName" type="text" required="true" autocomplete="off" />
                    </div>
                    <div class="field-wrap">
                        <label>
                            Set A Password<span class="req">*</span>
                        </label>
                        <input name="password" type="password" required="true" autocomplete="off" />
                    </div>
                    <button type="submit" class="button button-block">Get Started</button>
                </form>
            </div>
        </div>
        <!-- tab-content -->
    </div>
    <!-- /form -->
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    <script src="/assets/js/login.js"></script>
</body>

</html>
```
This adds a sign up and sign in page in one fell swoop.

```css
*, *:before, *:after {
  box-sizing: border-box;
}

html {
  overflow-y: scroll;
}

body {
  background: #c1bdba;
  font-family: 'Titillium Web', sans-serif;
}

a {
  text-decoration: none;
  color: #1ab188;
  -webkit-transition: .5s ease;
  transition: .5s ease;
}
a:hover {
  color: #179b77;
}

.form {
  background: rgba(19, 35, 47, 0.9);
  padding: 40px;
  max-width: 600px;
  margin: 40px auto;
  border-radius: 4px;
  box-shadow: 0 4px 10px 4px rgba(19, 35, 47, 0.3);
}

.tab-group {
  list-style: none;
  padding: 0;
  margin: 0 0 40px 0;
}
.tab-group:after {
  content: "";
  display: table;
  clear: both;
}
.tab-group li a {
  display: block;
  text-decoration: none;
  padding: 15px;
  background: rgba(160, 179, 176, 0.25);
  color: #a0b3b0;
  font-size: 20px;
  float: left;
  width: 50%;
  text-align: center;
  cursor: pointer;
  -webkit-transition: .5s ease;
  transition: .5s ease;
}
.tab-group li a:hover {
  background: #179b77;
  color: #ffffff;
}
.tab-group .active a {
  background: #1ab188;
  color: #ffffff;
}

.tab-content > div:last-child {
  display: none;
}

h1 {
  text-align: center;
  color: #ffffff;
  font-weight: 300;
  margin: 0 0 40px;
}

label {
  position: absolute;
  -webkit-transform: translateY(6px);
          transform: translateY(6px);
  left: 13px;
  color: rgba(255, 255, 255, 0.5);
  -webkit-transition: all 0.25s ease;
  transition: all 0.25s ease;
  -webkit-backface-visibility: hidden;
  pointer-events: none;
  font-size: 22px;
}
label .req {
  margin: 2px;
  color: #1ab188;
}

label.active {
  -webkit-transform: translateY(50px);
          transform: translateY(50px);
  left: 2px;
  font-size: 14px;
}
label.active .req {
  opacity: 0;
}

label.highlight {
  color: #ffffff;
}

input, textarea {
  font-size: 22px;
  display: block;
  width: 100%;
  height: 100%;
  padding: 5px 10px;
  background: none;
  background-image: none;
  border: 1px solid #a0b3b0;
  color: #ffffff;
  border-radius: 0;
  -webkit-transition: border-color .25s ease, box-shadow .25s ease;
  transition: border-color .25s ease, box-shadow .25s ease;
}
input:focus, textarea:focus {
  outline: 0;
  border-color: #1ab188;
}

textarea {
  border: 2px solid #a0b3b0;
  resize: vertical;
}

.field-wrap {
  position: relative;
  margin-bottom: 40px;
}

.top-row:after {
  content: "";
  display: table;
  clear: both;
}
.top-row > div {
  float: left;
  width: 48%;
  margin-right: 4%;
}
.top-row > div:last-child {
  margin: 0;
}

.button {
  border: 0;
  outline: none;
  border-radius: 0;
  padding: 15px 0;
  font-size: 2rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: .1em;
  background: #1ab188;
  color: #ffffff;
  -webkit-transition: all 0.5s ease;
  transition: all 0.5s ease;
  -webkit-appearance: none;
}
.button:hover, .button:focus {
  background: #179b77;
}

.button-block {
  display: block;
  width: 100%;
}

.forgot {
  margin-top: -20px;
  text-align: right;
}
```

Standard styling.

```js
$('.form').find('input, textarea').on('keyup blur focus', function(e) {

    var $this = $(this),
        label = $this.prev('label');

    if (e.type === 'keyup') {
        if ($this.val() === '') {
            label.removeClass('active highlight');
        } else {
            label.addClass('active highlight');
        }
    } else if (e.type === 'blur') {
        if ($this.val() === '') {
            label.removeClass('active highlight');
        } else {
            label.removeClass('highlight');
        }
    } else if (e.type === 'focus') {

        if ($this.val() === '') {
            label.removeClass('highlight');
        } else if ($this.val() !== '') {
            label.addClass('highlight');
        }
    }

});

$('.tab a').on('click', function(e) {

    e.preventDefault();

    $(this).parent().addClass('active');
    $(this).parent().siblings().removeClass('active');

    target = $(this).attr('href');

    $('.tab-content > div').not(target).hide();

    $(target).fadeIn(600);

});

```
Standard Javascript.

#### Creating a controller

```java
@Controller
public class UserController {

@Autowired
private UserRepository repository;

@RequestMapping(value = "/", method = RequestMethod.GET)
public String showLoginForm() {
        return "login";
}

@RequestMapping(value = "/register", method = RequestMethod.POST)
public String registerUserAccount(WebRequest request,
                                  @ModelAttribute("user") @Valid User user,
                                  BindingResult result) {
        if (!result.hasErrors()) {
                repository.save(user);

                System.out.println("saved the user!");
                System.out.println(
                        repository.findByUserName(user.getUserName() ));


        }
        return "redirect:/advice";

}
}
```

This will map our login and registration form to the index.

### Securing Our Web App

To let our registered users use Spring Security

#### Uncomment Spring Security

Remember when we first commented out Spring Security in our POM file? Well, it's time to uncomment it.

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

#### Create a custom UserDetailsService

```java
// main/java/myapp/service/MyUserDetailsService.java
@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

@Autowired
private UserRepository userRepository;


public MyUserDetailsService() {
        super();
}

@Override
public UserDetails loadUserByUsername(String userName)
throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
                return null;
        }
        List<GrantedAuthority> auth = AuthorityUtils
                                      .commaSeparatedStringToAuthorityList("ROLE_USER");
        String password = user.getPassword();
        return new org.springframework.security.core.userdetails.User(userName, password,                                                                      auth);
}
}
```

This returns looks up and returns a version of User compatible with Spring Security

#### Configure Spring Security

```java
// main/java/myapp/configuration/WebSecurityConfig.java
Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
@Override
protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        .antMatchers("/", "/register", "/assets/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/")
        .defaultSuccessUrl("/advice")
        .permitAll()
        .and()
        .logout()
        .permitAll();
}

@Autowired
private UserDetailsService userDetailsService;

@Autowired
public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
}
}
```
Finally we configure with pages are public and which pages require a login.

## Further Steps

We now have a basic application with authentication, but this is still not secure, things you should try yourself are:

1. Using Bcrypt to salt and hash passwords
2. Add email and other information to Users
3. Send an activation email
4. Password Reset options

Or you could skip all this and try out [Auth0](https://auth0.com)


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
