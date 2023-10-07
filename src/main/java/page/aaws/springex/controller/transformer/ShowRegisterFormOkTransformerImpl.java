package page.aaws.springex.controller.transformer;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import page.aaws.springex.dto.PageRequestDto;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@NoArgsConstructor
public class ShowRegisterFormOkTransformerImpl extends ShowRegisterFormOkTransformer {
    @Override
    public String process(Object... data) {
        PageRequestDto pageRequestDto = (PageRequestDto) data[0];
        Model model = (Model) data[1];

        model.addAttribute("type0", pageRequestDto.getTypes().length > 0 ? pageRequestDto.getTypes()[0] : "");
        model.addAttribute("type1", pageRequestDto.getTypes().length > 1 ? pageRequestDto.getTypes()[1] : "");

        return null;
    }
}
