package page.aaws.springex.controller.transformer;

import lombok.NoArgsConstructor;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import page.aaws.springex.dto.PageRequestDto;
import page.aaws.springex.dto.TodoDto;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@NoArgsConstructor
public class ReadOkTransformerImpl extends ReadOkTransformer {
    @Override
    public String process(Object... data) {
        PageRequestDto pageRequestDto = (PageRequestDto) data[0];
        TodoDto todoDto = (TodoDto) data[1];
        Model model = (Model) data[2];

        model.addAttribute("todoDto", todoDto);
        model.addAttribute("pageRequestDto", pageRequestDto);

        HashMap<String, String> queryString = new HashMap<>();
        queryString.put("finished", pageRequestDto.isFinished() ? "true" : "false");
        queryString.put("titleChecked", Arrays.asList(pageRequestDto.getTypes()).contains("t") ? "true" : "false");
        queryString.put("writerChecked", Arrays.asList(pageRequestDto.getTypes()).contains("w") ? "true" : "false");
        queryString.put("keyword", pageRequestDto.getKeyword());
        queryString.put("startDt",  pageRequestDto.getStartDt() == null ? null : pageRequestDto.getStartDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        queryString.put("finishDt", pageRequestDto.getFinishDt() == null ? null : pageRequestDto.getFinishDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        model.addAttribute("queryString", queryString);

        return null;
    }
}
