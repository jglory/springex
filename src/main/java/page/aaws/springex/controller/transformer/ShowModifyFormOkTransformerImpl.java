package page.aaws.springex.controller.transformer;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import page.aaws.springex.dto.PageRequestDto;
import page.aaws.springex.dto.TodoDto;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@NoArgsConstructor
public class ShowModifyFormOkTransformerImpl extends ShowModifyFormOkTransformer {
    @Override
    public String process(Object... data) {
        PageRequestDto pageRequestDto = (PageRequestDto) data[0];
        TodoDto todoDto = (TodoDto) data[1];
        Model model = (Model) data[2];

        model.addAttribute("dto", todoDto);
        model.addAttribute("type0", pageRequestDto.getTypes().length > 0 ? pageRequestDto.getTypes()[0] : "");
        model.addAttribute("type1", pageRequestDto.getTypes().length > 1 ? pageRequestDto.getTypes()[1] : "");

        return null;
    }
}
