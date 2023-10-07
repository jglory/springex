package page.aaws.springex.controller.transformer;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ListFailTransformerImpl extends ListFailTransformer {

    @Override
    public String process(Object... data) {
        return null;
    }
}
