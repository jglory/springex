package page.aaws.springex.controller;

import jakarta.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;

import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;

import page.aaws.springex.controller.transformer.FailTransformer;
import page.aaws.springex.controller.transformer.OkTransformer;
import page.aaws.springex.dto.PageRequestDto;
import page.aaws.springex.dto.PageResponseDto;
import page.aaws.springex.dto.TodoDto;
import page.aaws.springex.service.TodoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/todo")
@RequiredArgsConstructor
@Log4j2
public class TodoController {
    private final TodoService todoService;

    private final OkTransformer okTransformer;

    private final FailTransformer failTransformer;

    @GetMapping("/list")
    public void list(@Valid PageRequestDto pageRequestDto,
                     BindingResult bindingResult,
                     Model model) throws UnsupportedEncodingException {
        log.info("GET /todo/list: " + pageRequestDto);

        String error = "";
        if (bindingResult.hasErrors()) {
            pageRequestDto = PageRequestDto.builder().build();
            error = bindingResult.getAllErrors().get(0).toString();
        }

        PageResponseDto<TodoDto> pageResponseDto = todoService.list(pageRequestDto);
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

        log.info("/todo/list?size=" + pageRequestDto.getSize()
                + "&finished=" + (pageRequestDto.isFinished() ? "true" : "false")
                + "&types=" + (pageRequestDto.getTypes().length > 0 ? pageRequestDto.getTypes()[0] : "")
                + "&types=" + (pageRequestDto.getTypes().length > 1 ? pageRequestDto.getTypes()[1] : "")
                + "&keyword=" + URLEncoder.encode(pageRequestDto.getKeyword(), StandardCharsets.UTF_8)
                + "&startDt=" + (pageRequestDto.getStartDt() == null ? "" : URLEncoder.encode(pageRequestDto.getStartDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), StandardCharsets.UTF_8))
                + "&finishDt=" + (pageRequestDto.getFinishDt() == null ? "" : URLEncoder.encode(pageRequestDto.getFinishDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), StandardCharsets.UTF_8))
                + "&page=###");

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

        model.addAttribute("error", error);
    }

    @PostMapping("/modify")
    public String modify(@Valid TodoDto dto,
                       BindingResult bindingResult,
                       PageRequestDto pageRequestDto,
                       RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        String backUrlData = "page=" + pageRequestDto.getPage()
                + "&size=" + pageRequestDto.getSize()
                + "&finished=" + pageRequestDto.isFinished()
                + "&types=" + (pageRequestDto.getTypes().length > 0 ? pageRequestDto.getTypes()[0] : "")
                + "&types=" + (pageRequestDto.getTypes().length > 1 ? pageRequestDto.getTypes()[1] : "")
                + "&keyword=" + URLEncoder.encode(pageRequestDto.getKeyword(), StandardCharsets.UTF_8)
                + "&startDt=" + (pageRequestDto.getStartDt() == null ? "" : URLEncoder.encode(pageRequestDto.getStartDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), StandardCharsets.UTF_8))
                + "&finishDt=" + (pageRequestDto.getFinishDt() == null ? "" : URLEncoder.encode(pageRequestDto.getFinishDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), StandardCharsets.UTF_8));

        if (bindingResult.hasErrors()) {
            log.info("POST /todo/modify : error. " + bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/todo/modify?tno=" + dto.getTno() + "&" + backUrlData;
        }
        log.info("POST /todo/modify : " + dto);
        todoService.modify(dto);
        return "redirect:/todo/read?tno=" + dto.getTno() + "&" + backUrlData;
    }

    @GetMapping("/read")
    public String read(Long tno, PageRequestDto pageRequestDto, Model model) {
        return this.okTransformer.process(pageRequestDto, todoService.get(tno), model);
    }

    @GetMapping("/remove")
    public String remove(Long tno, PageRequestDto pageRequestDto, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        log.info("---------------remove---------------");
        log.info("tno: " + tno);

        String backUrlData = "page=" + pageRequestDto.getPage()
                + "&size=" + pageRequestDto.getSize()
                + "&finished=" + pageRequestDto.isFinished()
                + "&types=" + (pageRequestDto.getTypes().length > 0 ? pageRequestDto.getTypes()[0] : "")
                + "&types=" + (pageRequestDto.getTypes().length > 1 ? pageRequestDto.getTypes()[1] : "")
                + "&keyword=" + URLEncoder.encode(pageRequestDto.getKeyword(), StandardCharsets.UTF_8)
                + "&startDt=" + (pageRequestDto.getStartDt() == null ? "" : URLEncoder.encode(pageRequestDto.getStartDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), StandardCharsets.UTF_8))
                + "&finishDt=" + (pageRequestDto.getFinishDt() == null ? "" : URLEncoder.encode(pageRequestDto.getFinishDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), StandardCharsets.UTF_8));

        todoService.remove(tno);

        return "redirect:/todo/list?" + backUrlData;
    }

    @PostMapping("/register")
    public String register(@Valid TodoDto dto,
                           PageRequestDto pageRequestDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        log.info("POST /todo/register" + dto);
        log.info("POST /todo/register" + pageRequestDto);

        String backUrlData = "page=" + pageRequestDto.getPage()
                + "&size=" + pageRequestDto.getSize()
                + "&finished=" + pageRequestDto.isFinished()
                + "&types=" + (pageRequestDto.getTypes().length > 0 ? pageRequestDto.getTypes()[0] : "")
                + "&types=" + (pageRequestDto.getTypes().length > 1 ? pageRequestDto.getTypes()[1] : "")
                + "&keyword=" + URLEncoder.encode(pageRequestDto.getKeyword(), StandardCharsets.UTF_8)
                + "&startDt=" + (pageRequestDto.getStartDt() == null ? "" : URLEncoder.encode(pageRequestDto.getStartDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), StandardCharsets.UTF_8))
                + "&finishDt=" + (pageRequestDto.getFinishDt() == null ? "" : URLEncoder.encode(pageRequestDto.getFinishDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), StandardCharsets.UTF_8));

        if (bindingResult.hasErrors()) {
            log.info("has errors..........");
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/todo/register?" + backUrlData;
        }

        log.info(dto);
        todoService.register(dto);

        return "redirect:/todo/list?" + backUrlData;
    }

    @GetMapping("/register")
    public void showRegisterForm(PageRequestDto pageRequestDto, Model model) {
        log.info("GET /todo/register" + pageRequestDto);
        model.addAttribute("type0", pageRequestDto.getTypes().length > 0 ? pageRequestDto.getTypes()[0] : "");
        model.addAttribute("type1", pageRequestDto.getTypes().length > 1 ? pageRequestDto.getTypes()[1] : "");
    }

    @GetMapping("/modify")
    public void showModifyForm(Long tno, PageRequestDto pageRequestDto, Model model, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        log.info("GET /todo/modify?tno=" + tno);
        model.addAttribute("dto", todoService.get(tno));
        model.addAttribute("type0", pageRequestDto.getTypes().length > 0 ? pageRequestDto.getTypes()[0] : "");
        model.addAttribute("type1", pageRequestDto.getTypes().length > 1 ? pageRequestDto.getTypes()[1] : "");
    }
}
