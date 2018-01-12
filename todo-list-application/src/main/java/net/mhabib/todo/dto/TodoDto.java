package net.mhabib.todo.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mhabib.todo.enums.TodoStatus;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto implements Serializable {

    private Long id;

    @NotNull(message = "Task can not be null.")
    private String task;

    private String details;

    private Integer priority;

    @NotNull(message = "Status can not be null.")
    private TodoStatus todoStatus;

    @NotNull(message = "CreatedAt can not be null.")
    private LocalDateTime createdAt;

    @NotNull(message = "UpdatedAt can not be null.")
    private LocalDateTime updatedAt;

}
