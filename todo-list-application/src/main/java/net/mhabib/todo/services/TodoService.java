package net.mhabib.todo.services;

import java.util.List;
import net.mhabib.todo.entity.Todo;
import net.mhabib.todo.enums.TodoStatus;
import net.mhabib.todo.exceptions.EntityNotFoundException;

public interface TodoService {

    void save(Todo todo) throws EntityNotFoundException;

    void update(Todo todo) throws EntityNotFoundException;

    void updateStatus(Long todoId, TodoStatus todoStatus) throws EntityNotFoundException;

    void delete(Long todoId) throws EntityNotFoundException;

    Todo findById(Long todoId) throws EntityNotFoundException;

    List<Todo> findAllTodos(int offset, int limit);
}
