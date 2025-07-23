# Spring boot & Spring Data JPA

## Beans

Spring boot has a powerful dependency injection container system, similar to the one provided by ASP.NET where you have a container that stores dependecies, and you can register dependecies like services into it, providing options like its life cycle (Scope, e.g singleton and request). Spring calls these dependencies "Beans".  
  
---
Spring gives you multiple ways of setting up beans, to see some of them we'll take a common example of Dependecy injection. Given you want to make a Users API, we would need a `UserService` class responsible for handling any **buissness logic** that may exist and then we'll need `UserRepository` to **fetch** the **users** from the database, here the `UserService` would deppend on the `UserRepository` to acces the users in the database.
  
### Configuring beans through annotations

We can tell spring that the `UserRepository` is a Bean (Dependecy) by adding the `@Component` annotation like so

```java
// THIS IS A DEPENDENCY
@Component
public class UserRepository() {
    public function getUser(int id) {
        ...
    }
}
```

```java
    
public class UserService() {
    // DEPENDANT ON UsersRepository
    private UserRepository userRepo;

    // Constructor injection
    public UsersService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public function getUser(int id) {
        ...
        return this.userRepo.getUser(id);
    }
}
```

Because we annotated the `UserRepository` class with `@Component`, spring will automatically provide an isntance of this class to the `UserService` class.
  
> [!NOTE]
> Spring provides the `@Repository` and `@Service` annotations, which has the same effect as `@Component`, use these for improved readability.

### Manually configuring bean instantiation

Previously we saw how to configure beans annotating a class with `@Component`, this way spring will automatically create the bean instance for you. but you can also provide bean instantiation **manually** creating a `ApplicationConfig` class, annotating it with `@Configuration` and having methods that provide the bean instance.

> ApplicationConfig.java

```java
    @Configuration
    public class ApplicationConfig {

        @Bean 
        public UserRepository userRepository() {
            return new UserRepository();
        }
    }
```

The `@Configuration` annotation tells Spring to use this config class then, it will use it's `@Bean` annotated methods to provide depenecies. In this example every time spring encounters a class with a dependency for `UserRepository`, it will use the `userRepository()` method to give that class an instance of the bean.

### Manually setting up dependencies

you can also resolve dependencies manually if you need to inside of the main class' `main()` method of your project found at `src/main/java/com.example.yourAppName/YourAppNameApplication.java`

> MyApplication.java

```java
    @SpringBootApplication
    public class MyApplication {
        public static void main(String[] args) {
            var ctx = SpringApplication.run(MyApplication.class, args);

            UserRepository userRepository = ctx.getBean(UserRepository.class);
            UserService userService = new UserService(userRepository);
        }
        
    }
```
  
### Envoriemental variables

You  can also inject envoriemental variables directly to classes with porperty files, you have a default one in `resources/application.properties`.

> application.properties

```yaml
weatherApi.uri.base = 127.0.0.1:8080
weatherApi.uri.forecast = /forecast
weatherApi.rateLimit.int = 1200 # You can also provide variable type
```

> WeatherClient.java

```java
public class WeatherClient {
    @Value("${weatherApi.uri.base}")
    private String baseUri;

    @Value("${weatherApi.uri.forecast}")
    private String forecastUri;

    @Value("${weatherApi.rateLimit.int}")
    private Integer rateLimit;

    public getForecast(Date forecastDate) {
        ...
    }
}
```

### Spring profiles

Spring profiles let you configure which beans are to be used, depending on the profile you make. For example, you could have a bean that cleans the database and seeds it with the same data every time, this would obiously be only for dev porpuses, so you could configure this bean to only be used in the `Dev` profile.

#### Creating a profile

previously we saw how to add envoriemental variable using the application.properties file, we'll use properties files again, each properties file represents a profile, for example you could have a `application-dev.properties` for de developement profile.

> application-dev.properties

```yaml
my.custom.property = "dev"
```

> application.properties

```yaml
weatherApi.uri.base = 127.0.0.1:8080
weatherApi.uri.forecast = /forecast
weatherApi.rateLimit.int = 1200 
```

#### Setting up spring to use your profile

You can easily set you current profile in the main `application.properties` file with the `spring.profiles.active`  property

> application.properties

```yaml
spring.profiles.active=dev,test
```

As you can see, you can also set multiple profiles to be used, **but be careful**, spring wont check or throw any errors if any given profile does not exist, so it is your responsability, and also, the order in which you include the profiles matter, as properties are overrided when one is found multiple times, so if a property named `host` is defined in the main profile, dev and test, the used property will be the one found first in the list of given profiles.

#### Setting up profile programatically

You can also set the current profile from the `main` project method.

> MyApplication.java

```java
    @SpringBootApplication
    public class MyApplication {
        public static void main(String[] args) {
            var app = new SpringApplication(MyApplication.class);
            app.setDefaultProperties(Collections.singletonMap("spring.profiles.active", "dev"));
            app.run(args);
        }
    }
```

In this example we are setting the SpringApplication's `spring.profiles.active` value to "dev".

#### Setting up profile specific beans

When you manually provide bean instantiation in the application config files you can set a bean's profile with the `Profile` annotation

> ApplicationConfig.java

```java
    @Configuration
    public class ApplicationConfig {

        @Bean
        @Profile("dev");
        public UserRepository userRepository() {
            return new UserRepository("dev");
        }
    }
```
