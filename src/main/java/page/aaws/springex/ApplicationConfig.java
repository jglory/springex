package page.aaws.springex;

import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import page.aaws.springex.controller.transformer.*;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final ApplicationContext applicationContext;

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
            .setFieldMatchingEnabled(true)
            .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
            .setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public FailTransformer failTransformer() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return switch (request.getMethod() + " " + request.getRequestURI()) {
            case "GET /todo/read" -> this.applicationContext.getBean("readFailTransformer", ReadFailTransformer.class);
            default -> null;
        };
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ReadFailTransformer readFailTransformer() {
        return new ReadFailTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public OkTransformer okTransformer() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return switch (request.getMethod() + " " + request.getRequestURI()) {
            case "GET /todo/read" -> this.applicationContext.getBean("readOkTransformer", ReadOkTransformer.class);
            default -> null;
        };
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ReadOkTransformer readOkTransformer() {
        return new ReadOkTransformerImpl();
    }
}
