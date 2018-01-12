package net.mhabib.todo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<E extends Serializable> {

    private E entity;
    private List<E> entities;

    public Response(final E entity) {
        this.entity = entity;
    }

    public Response(final List<E> entities) {
        this.entities = entities;
    }
}
