package net.mhabib.todo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import net.mhabib.todo.converter.TodoConverter;
import net.mhabib.todo.dto.TodoDto;
import net.mhabib.todo.entity.Todo;
import net.mhabib.todo.enums.TodoStatus;
import net.mhabib.todo.exceptions.EntityNotFoundException;
import net.mhabib.todo.response.Response;
import net.mhabib.todo.response.TodoResponse;
import net.mhabib.todo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import static net.mhabib.todo.common.ConverterUtils.mapInternalToExternal;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(path = "/todos")
@Api("Todo List")
public class TodoController {

    private final TodoConverter todoConverter = TodoConverter.INSTANCE;

    @Autowired
    private TodoService todoService;

    @ApiOperation(value = "Get todo by id")
    @RequestMapping(method = GET, path = "/{todoId}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TodoResponse> getTodo(@Valid @PathVariable final long todoId) throws EntityNotFoundException {
        Todo todo = todoService.findById(todoId);
        return new ResponseEntity<>(new TodoResponse(todoConverter.internalToExternal(todo)), OK);
    }

    @ApiOperation(value = "List all todos")
    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TodoResponse> listAllTodos(@RequestParam final int offset, @RequestParam final int limit) {
        List<Todo> todos = todoService.findAllTodos(offset, limit);
        if (todos.isEmpty()) {
            return new ResponseEntity<>(NO_CONTENT);
        }
        return new ResponseEntity<>(new TodoResponse(mapInternalToExternal(todos, todoConverter)), OK);
    }

    @ApiOperation(value = "Add todo")
    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Response<String>> createTodo(@Valid @RequestBody TodoDto todoDto, UriComponentsBuilder ucBuilder) throws EntityNotFoundException {

        final Todo todo = todoConverter.externalToInternal(todoDto);

        todoService.save(todo);

        return getResponseEntity(todo.getId(), CREATED, ucBuilder);
    }

    @ApiOperation(value = "Update todo")
    @RequestMapping(method = PUT, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Response<String>> updateTodo(@Valid @RequestBody TodoDto todoDto, UriComponentsBuilder ucBuilder) throws EntityNotFoundException {

        final Todo todo = todoConverter.externalToInternal(todoDto);

        todoService.update(todo);

        return getResponseEntity(todo.getId(), OK, ucBuilder);
    }

    @ApiOperation(value = "Update todo status for id")
    @RequestMapping(method = PUT, path = "/{todoId}/", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Response<String>> updateTodoStatus(@Valid @PathVariable long todoId,
                                                             @Valid @RequestParam TodoStatus todoStatus,
                                                             UriComponentsBuilder ucBuilder) throws EntityNotFoundException {

        todoService.updateStatus(todoId, todoStatus);

        return getResponseEntity(todoId, OK, ucBuilder);
    }

    @ApiOperation(value = "Delete todo for id")
    @RequestMapping(method = DELETE, path = "/{todoId}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity deleteTodo(@Valid @PathVariable final long todoId) throws EntityNotFoundException {
        todoService.delete(todoId);
        return new ResponseEntity(NO_CONTENT);
    }

    private ResponseEntity<Response<String>> getResponseEntity(final long todoId, final HttpStatus httpStatus, final UriComponentsBuilder ucBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/todos/{id}").buildAndExpand(todoId).toUri());
        return new ResponseEntity<>(headers, httpStatus);
    }
}
