# Making your own Testcontainers module for fun and profit!

This repo is for the [Testcontainers minilab at Devoxx UK 2023](https://www.devoxx.co.uk/talk/?id=11879).

## Setup 
1. Ensure you have a [compatible Docker environment](https://www.testcontainers.org/supported_docker_environment). 
    - To preserve conference wifi, consider installing [https://testcontainers.cloud](https://testcontainers.cloud?utm_medium=event&utm_source=devoxxuk23&utm_content=minilab-repo). It is free to try and takes less than 5 minutes!
2. Run `MyTest` test, it should pass. 

# Creating a Redis module for Testcontainers

Testcontainers provides a range of extension points to write your own modules and you are not limited to the modules that are provided out of the box. Such a container module can be used to encapsulate the logic of starting a container for a specific service, and configuring it accordingly.

Writing a custom container module is normally done by creating a new class that extends `GenericContainer`.

1. Create a new `RedisContainer` class that encapsulates the necessary Redis configuration.
   - For the inspiration you can look at `PostgresqlContainer`.

2. Explore the container lifecycle methods. 
   - Override `containerIsStarted` to print a helpful log line containing information how to connect to the Redis instance: host and port.
   
3. Create a helper method returning a Redis URI to help end-users use it without remembering the syntax. 
```
redis :// [[username :] password@] host [:port][/database]
[?[timeout=timeout[d|h|m|s|ms|us|ns]]
```

4. You can also make sure, a user can't use the container accidentally with a wrong Docker image. For this, in the `RedisContainer` class you can use the `dockerImage.assertCompatibleWith(compatibleImageName)` method. 

# Creating a Chaotic Postgres module for Testcontainers

Recently, we explored how one can use Toxiproxy to [inject latency and network failures into your tests](https://www.atomicjar.com/2023/03/developing-resilient-applications-with-toxiproxy-and-testcontainers/).
Combining toxiproxy and postgres into a single class can be an elegant solution to share this approach with your whole team. 

Write a custom module that creates and starts two containers, but to the end-user looks like a single abstraction. 

1. Create a `ToxicPostgres` class that encapsulates both a `PostgresqlContainer` and a `ToxiproxyContainer`
   - Don't forget to put them on the same `Network` to allow them to communicate.
   - Use `.dependsOn()` to ensure startup dependencies. 
2. Expose the `jbdcUrl` from the `PostgresqlContainer` to ensure the end user gets the convenience API
   - Remember to expose the proxy details and not the database directly. 
3. Expose the latency injecting API to simplify network manipulation. 
   - Use `proxy.toxics().latency()` from Toxiproxy to implement injecting latency.

   