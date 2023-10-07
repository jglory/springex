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
            case "GET /todo/list" -> this.applicationContext.getBean("listFailTransformer", ListFailTransformer.class);
            case "POST /todo/modify" -> this.applicationContext.getBean("modifyFailTransformer", ModifyFailTransformer.class);
            case "GET /todo/read" -> this.applicationContext.getBean("readFailTransformer", ReadFailTransformer.class);
            case "POST /todo/register" -> this.applicationContext.getBean("registerFailTransformer", RegisterFailTransformer.class);
            default -> null;
        };
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ListFailTransformer listFailTransformer() {
        return new ListFailTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ModifyFailTransformer modifyFailTransformer() {
        return new ModifyFailTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ReadFailTransformer readFailTransformer() {
        return new ReadFailTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RegisterFailTransformer registerFailTransformer() {
        return new RegisterFailTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public OkTransformer okTransformer() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return switch (request.getMethod() + " " + request.getRequestURI()) {
            case "GET /todo/list" -> this.applicationContext.getBean("listOkTransformer", ListOkTransformer.class);
            case "GET /todo/read" -> this.applicationContext.getBean("readOkTransformer", ReadOkTransformer.class);
            case "GET /todo/modify" -> this.applicationContext.getBean("showModifyFormOkTransformer", ShowModifyFormOkTransformer.class);
            case "GET /todo/register" -> this.applicationContext.getBean("showRegisterFormOkTransformer", ShowRegisterFormOkTransformer.class);
            case "GET /todo/remove" -> this.applicationContext.getBean("removeOkTransformer", RemoveOkTransformer.class);
            case "POST /todo/modify" -> this.applicationContext.getBean("modifyOkTransformer", ModifyOkTransformer.class);
            case "POST /todo/register" -> this.applicationContext.getBean("registerOkTransformer", RegisterOkTransformer.class);
            default -> null;
        };
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ListOkTransformer listOkTransformer() {
        return new ListOkTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ModifyOkTransformer modifyOkTransformer() {
        return new ModifyOkTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ReadOkTransformer readOkTransformer() {
        return new ReadOkTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RegisterOkTransformer registerOkTransformer() {
        return new RegisterOkTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RemoveOkTransformer removeOkTransformer() {
        return new RemoveOkTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ShowModifyFormOkTransformer showModifyFormOkTransformer() {
        return new ShowModifyFormOkTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ShowRegisterFormOkTransformer showRegisterFormOkTransformer() {
        return new ShowRegisterFormOkTransformerImpl();
    }
}
