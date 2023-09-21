package page.aaws.springex.service;

import java.util.List;

import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import page.aaws.springex.domain.TodoVo;
import page.aaws.springex.dto.PageResponseDto;
import page.aaws.springex.dto.PageRequestDto;
import page.aaws.springex.dto.TodoDto;
import page.aaws.springex.mapper.TodoMapper;

@Log4j2
@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {
    private final TodoMapper todoMapper;
    private final ModelMapper modelMapper;

    @Override
    public TodoDto get(Long tno) {
        return modelMapper.map(todoMapper.select(tno), TodoDto.class);
    }

    @Override
    public PageResponseDto<TodoDto> list(PageRequestDto pageRequestDto) {
        return PageResponseDto.<TodoDto>withAll()
                .items(
                        todoMapper.selectAll(pageRequestDto).stream()
                                .map(vo -> modelMapper.map(vo, TodoDto.class))
                                .toList()
                )
                .itemCount(todoMapper.selectCount(pageRequestDto))
                .pageRequestDto(pageRequestDto)
                .build();
    }

    @Override
    public void modify(TodoDto dto) {
        todoMapper.update(modelMapper.map(dto, TodoVo.class));
    }

    @Override
    public void register(TodoDto dto) {
        log.info(modelMapper);

        TodoVo vo = modelMapper.map(dto, TodoVo.class);
        log.info(vo);

        todoMapper.insert(vo);
    }

    @Override
    public void remove(Long tno) {
        todoMapper.delete(tno);
    }
}
