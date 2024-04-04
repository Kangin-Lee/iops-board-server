package org.example.boardserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(insertable = false)
    @JsonFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime updatedTime;
}
