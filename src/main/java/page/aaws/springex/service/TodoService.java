package page.aaws.springex.service;

import page.aaws.springex.dto.PageResponseDto;
import page.aaws.springex.dto.PageRequestDto;
import page.aaws.springex.dto.TodoDto;

public interface TodoService {
    TodoDto get(Long tno);
    PageResponseDto<TodoDto> list(PageRequestDto pageRequestDto);
    void modify(TodoDto dto);
    void register(TodoDto dto);
    void remove(Long tno);
}
