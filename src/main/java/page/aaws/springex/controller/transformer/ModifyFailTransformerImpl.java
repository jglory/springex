package page.aaws.springex.controller.transformer;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import page.aaws.springex.dto.PageRequestDto;
import page.aaws.springex.dto.TodoDto;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ModifyFailTransformerImpl extends ModifyFailTransformer {

    @Override
    public String process(Object... data) {
        TodoDto todoDto = (TodoDto) data[0];
        BindingResult bindingResult = (BindingResult) data[1];
        PageRequestDto pageRequestDto = (PageRequestDto) data[2];
        RedirectAttributes redirectAttributes = (RedirectAttributes) data[3];

        redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
        return "redirect:/todo/modify?tno=" + todoDto.getTno()
                + "&page=" + pageRequestDto.getPage()
                + "&size=" + pageRequestDto.getSize()
                + "&finished=" + pageRequestDto.isFinished()
                + "&types=" + (pageRequestDto.getTypes().length > 0 ? pageRequestDto.getTypes()[0] : "")
                + "&types=" + (pageRequestDto.getTypes().length > 1 ? pageRequestDto.getTypes()[1] : "")
                + "&keyword=" + URLEncoder.encode(pageRequestDto.getKeyword(), StandardCharsets.UTF_8)
                + "&startDt=" + (pageRequestDto.getStartDt() == null ? "" : URLEncoder.encode(pageRequestDto.getStartDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), StandardCharsets.UTF_8))
                + "&finishDt=" + (pageRequestDto.getFinishDt() == null ? "" : URLEncoder.encode(pageRequestDto.getFinishDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), StandardCharsets.UTF_8));
    }
}
