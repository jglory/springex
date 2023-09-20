package page.aaws.springex.sample;

import java.time.LocalDate;
import java.util.List;

import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import page.aaws.springex.domain.TodoVo;
import page.aaws.springex.dto.PageRequestDto;
import page.aaws.springex.mapper.TodoMapper;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/root-context.xml")
public class TodoMapperTests {
    @Autowired(required=false)
    private TodoMapper todoMapper;

    @Test
    public void testInsert() {
        todoMapper.insert(TodoVo.builder()
                .title("스프링 테스트")
                .dueDt(LocalDate.of(2023, 9, 14))
                .writer("user00")
                .build());
    }

    @Test
    void testSelect() {
        TodoVo vo = todoMapper.select(1L);
        log.info(vo);
    }

    @Test
    void testSelectAll() {
        List<TodoVo> list = todoMapper.selectAll(PageRequestDto.builder()
                .page(1)
                .size(5)
                .build());
        list.forEach(vo -> log.info(vo));
    }

    @Test
    void testSearch() {
        todoMapper.search(
                PageRequestDto.builder()
                        .page(1)
                        .size(10)
                        .types(new String[]{"t", "w"})
                        .keyword("user00")
                        .finished(false)
                        .startDt(LocalDate.of(2023,10,10))
                        .finishDt(LocalDate.of(2023, 10, 17))
                        .build()
        ).forEach(log::info);
    }
}
