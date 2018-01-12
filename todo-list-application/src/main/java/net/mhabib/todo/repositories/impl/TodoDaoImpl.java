package net.mhabib.todo.repositories.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import net.mhabib.todo.entity.Todo;
import net.mhabib.todo.repositories.TodoDao;
import org.springframework.stereotype.Repository;

@Repository
public class TodoDaoImpl implements TodoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void persist(Todo todo) {
        entityManager.persist(todo);
    }

    @Override
    @Transactional
    public void merge(Todo todo) {
        entityManager.merge(todo);
    }

    @Override
    @Transactional
    public void delete(Todo todo) {
        entityManager.merge(todo);
    }

    @Override
    public Todo findById(final Long todoId) {
        return entityManager.find(Todo.class, todoId);
    }

    @Override
    public List<Todo> findAllTodos(final int offset, final int limit) {
        return entityManager.createQuery("SELECT t FROM Todo t ORDER BY t.priority ASC", Todo.class)
                            .setFirstResult(offset)
                            .setMaxResults(limit)
                            .getResultList();
    }

}
