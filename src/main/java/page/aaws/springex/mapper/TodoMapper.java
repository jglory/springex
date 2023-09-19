package page.aaws.springex.mapper;

import java.util.List;

import page.aaws.springex.domain.TodoVo;
import page.aaws.springex.dto.PageRequestDto;

public interface TodoMapper {
    void delete(Long tno);
    void insert(TodoVo vo);
    TodoVo select(Long tno);

    List<TodoVo> search(PageRequestDto pageRequestDto);
    List<TodoVo> selectAll(PageRequestDto pageRequestDto);
    int selectCount(PageRequestDto pageRequestDto);
    void update(TodoVo vo);
}
