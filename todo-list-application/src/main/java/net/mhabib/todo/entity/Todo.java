package net.mhabib.todo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.mhabib.todo.enums.TodoStatus;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "todo")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Todo implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "task")
    private String task;

    @Column(name = "details")
    private String details;

    @Column(name = "priority")
    private Integer priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "todo_status", nullable = false)
    private TodoStatus todoStatus;

    @Column(name = "created_at", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public boolean isNew() {
        return id == null;
    }
}
