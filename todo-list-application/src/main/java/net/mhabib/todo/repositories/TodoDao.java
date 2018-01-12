package net.mhabib.todo.repositories;

import java.util.List;
import net.mhabib.todo.entity.Todo;

public interface TodoDao {

    void persist(Todo todo);

    void merge(Todo todo);

    void delete(Todo todo);

    Todo findById(Long todoId);

    List<Todo> findAllTodos(int offset, int limit);
}
