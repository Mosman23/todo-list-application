package net.mhabib.todo.services.impl;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import net.mhabib.todo.entity.Todo;
import net.mhabib.todo.enums.TodoStatus;
import net.mhabib.todo.exceptions.EntityNotFoundException;
import net.mhabib.todo.repositories.TodoDao;
import net.mhabib.todo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static net.mhabib.todo.enums.TodoStatus.DELETED;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoDao todoDao;

    @Override
    public void save(final Todo todo) throws EntityNotFoundException {
        if (todo == null) {
            throw new EntityNotFoundException("Todo not found.");
        }

        if (!todo.isNew()) {
            throw new EntityNotFoundException(MessageFormat.format("Unable to create. A Todo with id {} already exist.", todo.getId()));
        }
        todoDao.persist(todo);
    }

    @Override
    public void update(final Todo todo) throws EntityNotFoundException {
        if (todo == null) {
            throw new EntityNotFoundException("Todo not found.");
        }

        if (todo.isNew()) {
            throw new EntityNotFoundException(MessageFormat.format("Unable to update. A Todo with id {} not exist.", todo.getId()));
        }

        setUpdatedAt(todo);
        todoDao.merge(todo);
    }

    @Override
    public void updateStatus(final Long todoId, final TodoStatus todoStatus) throws EntityNotFoundException {
        final Todo todo = findById(todoId);
        todo.setTodoStatus(todoStatus);
        setUpdatedAt(todo);
        todoDao.merge(todo);
    }

    @Override
    public void delete(final Long todoId) throws EntityNotFoundException {
        updateStatus(todoId, DELETED);
    }

    @Override
    public Todo findById(final Long todoId) throws EntityNotFoundException {
        final Todo todo = todoDao.findById(todoId);
        if (todo == null) {
            throw new EntityNotFoundException(MessageFormat.format("Todo not found for Id: {}.", todoId));
        }
        return todo;
    }

    @Override
    public List<Todo> findAllTodos(final int offset, final int limit) {
        return todoDao.findAllTodos(offset, limit);
    }


    private void setUpdatedAt(Todo todo) {
        todo.setUpdatedAt(LocalDateTime.now());
    }
}
