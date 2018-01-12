package net.mhabib.todo.converter;

import net.mhabib.todo.dto.TodoDto;
import net.mhabib.todo.entity.Todo;

public enum TodoConverter implements Converter<Todo, TodoDto> {
    INSTANCE;

    @Override
    public TodoDto internalToExternal(final Todo todo) {
        return TodoDto.builder()
                      .id(todo.getId())
                      .task(todo.getTask())
                      .details(todo.getDetails())
                      .priority(todo.getPriority())
                      .todoStatus(todo.getTodoStatus())
                      .createdAt(todo.getCreatedAt())
                      .updatedAt(todo.getUpdatedAt())
                      .build();

    }

    @Override
    public Todo externalToInternal(final TodoDto todoDto) {
        return Todo.builder()
                   .id(todoDto.getId())
                   .task(todoDto.getTask())
                   .details(todoDto.getDetails())
                   .priority(todoDto.getPriority())
                   .todoStatus(todoDto.getTodoStatus())
                   .createdAt(todoDto.getCreatedAt())
                   .updatedAt(todoDto.getUpdatedAt())
                   .build();
    }
}
