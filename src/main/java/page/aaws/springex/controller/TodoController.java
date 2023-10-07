package page.aaws.springex.controller;

import jakarta.validation.Valid;

import java.io.UnsupportedEncodingException;

import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;

import page.aaws.springex.controller.transformer.FailTransformer;
import page.aaws.springex.controller.transformer.OkTransformer;
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

    private final OkTransformer okTransformer;

    private final FailTransformer failTransformer;

    @GetMapping("/list")
    public void list(@Valid PageRequestDto pageRequestDto,
                     BindingResult bindingResult,
                     Model model) throws UnsupportedEncodingException {
        this.okTransformer.process(bindingResult, pageRequestDto, this.todoService.list(pageRequestDto), model);
    }

    @PostMapping("/modify")
    public String modify(@Valid TodoDto todoDto,
                       BindingResult bindingResult,
                       PageRequestDto pageRequestDto,
                       RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        if (bindingResult.hasErrors()) {
            return this.failTransformer.process(todoDto, bindingResult, pageRequestDto, redirectAttributes);
        }

        todoService.modify(todoDto);
        return this.okTransformer.process(pageRequestDto, todoDto);
    }

    @GetMapping("/read")
    public String read(Long tno, PageRequestDto pageRequestDto, Model model) {
        return this.okTransformer.process(pageRequestDto, todoService.get(tno), model);
    }

    @GetMapping("/remove")
    public String remove(Long tno, PageRequestDto pageRequestDto, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        todoService.remove(tno);
        return this.okTransformer.process(pageRequestDto);
    }

    @PostMapping("/register")
    public String register(@Valid TodoDto dto,
                           PageRequestDto pageRequestDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        if (bindingResult.hasErrors()) {
            return this.failTransformer.process(dto, pageRequestDto, bindingResult, redirectAttributes);
        }

        todoService.register(dto);
        return this.okTransformer.process(pageRequestDto);
    }

    @GetMapping("/register")
    public void showRegisterForm(PageRequestDto pageRequestDto, Model model) {
        this.okTransformer.process(pageRequestDto, model);
    }

    @GetMapping("/modify")
    public void showModifyForm(Long tno, PageRequestDto pageRequestDto, Model model, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        this.okTransformer.process(pageRequestDto, todoService.get(tno), model);
    }
}
