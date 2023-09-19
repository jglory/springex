package page.aaws.springex.sample;

import java.time.LocalDate;

import lombok.extern.log4j.Log4j2;

import page.aaws.springex.dto.PageResponseDto;
import page.aaws.springex.dto.PageRequestDto;
import page.aaws.springex.dto.TodoDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import page.aaws.springex.service.TodoService;

@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/root-context.xml")
@ExtendWith(SpringExtension.class)
@Log4j2
public class TodoServiceTests {
    @Autowired
    private TodoService todoService;

    @Test
    void testPaging() {
        PageResponseDto<TodoDto> pageDto = todoService.list(PageRequestDto.builder()
                .page(1)
                .size(10)
                .build());
        log.info(pageDto);
        pageDto.getDtoList().forEach(log::info);
    }

    @Test
    public void testRegister() {
        todoService.register(TodoDto.builder()
                .title("test")
                .dueDate(LocalDate.now())
                .writer("user01")
                .build());
    }
}
