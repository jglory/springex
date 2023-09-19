package page.aaws.springex.controller;

import jakarta.validation.Valid;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;

import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;

import page.aaws.springex.dto.PageRequestDto;
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

    @GetMapping("/list")
    public void list(@Valid PageRequestDto pageRequestDto,
                     BindingResult bindingResult,
                     Model model) {
        log.info("GET /todo/list: " + pageRequestDto);
        if (bindingResult.hasErrors()) {
            pageRequestDto = PageRequestDto.builder().build();
        }

        model.addAttribute("pageDto", todoService.list(pageRequestDto));

        HashMap<String, String> searchComponent = new HashMap<String, String>();
        searchComponent.put("action", "/todo/list?size=" + pageRequestDto.getSize());
        searchComponent.put("finished", pageRequestDto.isFinished() ? "true" : "false");
        searchComponent.put("title-checked", Arrays.asList(pageRequestDto.getTypes()).contains("t") ? "true" : "false");
        searchComponent.put("writer-checked", Arrays.asList(pageRequestDto.getTypes()).contains("w") ? "true" : "false");
        searchComponent.put("keyword", pageRequestDto.getKeyword());
        searchComponent.put("start-date",  pageRequestDto.getFrom().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        searchComponent.put("finish-date", pageRequestDto.getTo().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        model.addAttribute("searchComponent", searchComponent);
    }

    @PostMapping("/modify")
    public String modify(@Valid TodoDto dto,
                       BindingResult bindingResult,
                       PageRequestDto pageRequestDto,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("POST /todo/modify : error. " + bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/todo/modify?tno=" + dto.getTno() + "&page=" + pageRequestDto.getPage() + "&size=" + pageRequestDto.getSize();
        }
        log.info("POST /todo/modify : " + dto);
        todoService.modify(dto);
        return "redirect:/todo/read?tno=" + dto.getTno() + "&page=" + pageRequestDto.getPage() + "&size=" + pageRequestDto.getSize();
    }

    @GetMapping("/read")
    public void read(Long tno, PageRequestDto pageRequestDto, Model model) {
        TodoDto dto = todoService.get(tno);
        log.info(dto);

        model.addAttribute("dto", dto);
    }

    @GetMapping("/remove")
    public String remove(Long tno, PageRequestDto pageRequestDto, RedirectAttributes redirectAttributes) {
        log.info("---------------remove---------------");
        log.info("tno: " + tno);

        todoService.remove(tno);

        return "redirect:/todo/list?page=" + pageRequestDto.getPage() + "&size=" + pageRequestDto.getSize();
    }

    @PostMapping("/register")
    public String register(@Valid TodoDto dto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        log.info("POST /todo/register");

        if (bindingResult.hasErrors()) {
            log.info("has errors..........");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/todo/register";
        }

        log.info(dto);
        todoService.register(dto);

        return "redirect:/todo/list";
    }

    @GetMapping("/register")
    public void showRegisterForm(Model model) {
        log.info("GET /todo/register");
    }

    @GetMapping("/modify")
    public void showModifyForm(Long tno, PageRequestDto pageRequestDto, Model model) {
        log.info("GET /todo/modify?tno=" + tno);
        model.addAttribute("dto", todoService.get(tno));
    }
}
