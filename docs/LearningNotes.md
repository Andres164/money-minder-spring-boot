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
        public static void main()
        
    }
```
