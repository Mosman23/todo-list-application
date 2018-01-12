package net.mhabib.todo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import net.mhabib.todo.dto.TodoDto;

public class TodoResponse extends Response<TodoDto> {

    public TodoResponse(final TodoDto entity) {
        super(entity);
    }

    public TodoResponse(final List<TodoDto> entities) {
        super(entities);
    }

    @JsonProperty("todo")
    @Override
    public TodoDto getEntity() {
        return super.getEntity();
    }

    @JsonProperty("todos")
    @Override
    public List<TodoDto> getEntities() {
        return super.getEntities();
    }
}
