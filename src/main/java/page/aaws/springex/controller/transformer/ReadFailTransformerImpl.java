package page.aaws.springex.controller.transformer;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ReadFailTransformerImpl extends ReadFailTransformer {

    @Override
    public String process(Object... data) {
        return null;
    }
}
