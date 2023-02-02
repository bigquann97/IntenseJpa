package me.quann.taesunjpa.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import me.quann.taesunjpa.user.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimeStamp {
    @CreatedDate
    private LocalDateTime createdAt;

//    @CreatedBy
//    @ManyToOne
//    private User createdBy;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

//    @LastModifiedBy
//    @ManyToOne
//    private User modifiedBy;
}