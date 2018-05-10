# Versioniser

This little library works with Spring and Springboot, and helps with header based versioning of REST APIs. This can work for "basic" versioning, but also for intermediate versioning (think: X-Support style of header)

Usage:

```java
@GetMapping("/test")
public String testNoVersion() {
    return "Test no version provided";
}

@GetMapping("/test")
@Versioniser(value = "v2") // it will read from the default X-Versioniser header
public String testVersion2() {
    return "Same endpoint for the client, but different handler on the server!";
}

@GetMapping("/test")
@Versioniser(value = "v3", header="X-Custom-Versioning-Header")
public String testVersion3() {
    return "Still the same endpoint for the client, using yet a different handler and header";
}
```

Setting up:

You will need to extend Spring's `WebMvcConfigurationSupport` like so:

```java
@Configuration
public class TestWebConfig extends WebMvcConfigurationSupport {

    @Override
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new VersioniserHandlerMapping();
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        return handlerMapping;
    }
}
```

That's it, you are good to go!
