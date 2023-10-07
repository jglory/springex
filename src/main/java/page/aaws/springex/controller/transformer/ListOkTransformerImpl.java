package page.aaws.springex.controller.transformer;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import page.aaws.springex.dto.PageRequestDto;
import page.aaws.springex.dto.PageResponseDto;
import page.aaws.springex.dto.TodoDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@NoArgsConstructor
public class ListOkTransformerImpl extends ListOkTransformer {
    @Override
    public String process(Object... data) {
        BindingResult bindingResult = (BindingResult) data[0];
        PageRequestDto pageRequestDto = (PageRequestDto) data[1];
        PageResponseDto<TodoDto> pageResponseDto = (PageResponseDto<TodoDto>) data[2];
        Model model = (Model) data[3];

        String error = "";
        if (bindingResult.hasErrors()) {
            pageRequestDto = PageRequestDto.builder().build();
            error = bindingResult.getAllErrors().get(0).toString();
        }
        model.addAttribute("error", error);

        model.addAttribute("pageDto", pageResponseDto);

        HashMap<String, String> searchComponent = new HashMap<>();
        searchComponent.put("action", "/todo/list?size=" + pageRequestDto.getSize());
        searchComponent.put("finished", pageRequestDto.isFinished() ? "true" : "false");
        searchComponent.put("titleChecked", Arrays.asList(pageRequestDto.getTypes()).contains("t") ? "true" : "false");
        searchComponent.put("writerChecked", Arrays.asList(pageRequestDto.getTypes()).contains("w") ? "true" : "false");
        searchComponent.put("keyword", pageRequestDto.getKeyword());
        searchComponent.put("startDt",  pageRequestDto.getStartDt() == null ? null : pageRequestDto.getStartDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        searchComponent.put("finishDt", pageRequestDto.getFinishDt() == null ? null : pageRequestDto.getFinishDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        model.addAttribute("searchComponent", searchComponent);

        HashMap<String, String> pageNavigatorComponent = new HashMap<>();
        pageNavigatorComponent.put("start", String.valueOf(pageResponseDto.getStartPage()));
        pageNavigatorComponent.put("end", String.valueOf(pageResponseDto.getEndPage()));
        pageNavigatorComponent.put("current", String.valueOf(pageResponseDto.getPage()));
        pageNavigatorComponent.put("count", String.valueOf(pageResponseDto.getCount()));
        pageNavigatorComponent.put("url",
                "/todo/list?size=" + pageRequestDto.getSize()
                        + "&finished=" + (pageRequestDto.isFinished() ? "true" : "false")
                        + "&types=" + (pageRequestDto.getTypes().length > 0 ? pageRequestDto.getTypes()[0] : "")
                        + "&types=" + (pageRequestDto.getTypes().length > 1 ? pageRequestDto.getTypes()[1] : "")
                        + "&keyword=" + URLEncoder.encode(pageRequestDto.getKeyword(), StandardCharsets.UTF_8)
                        + "&startDt=" + (pageRequestDto.getStartDt() == null ? "" : URLEncoder.encode(pageRequestDto.getStartDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), StandardCharsets.UTF_8))
                        + "&finishDt=" + (pageRequestDto.getFinishDt() == null ? "" : URLEncoder.encode(pageRequestDto.getFinishDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), StandardCharsets.UTF_8))
                        + "&page=###"
        );
        model.addAttribute("pageNavigatorComponent", pageNavigatorComponent);

        return null;
    }
}
