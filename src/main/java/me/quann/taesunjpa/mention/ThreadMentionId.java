package me.quann.taesunjpa.mention;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Setter
public class ThreadMentionId implements Serializable {

    @Serial
    private static final long serialVersionUID = 932813899396663626L;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "thread_id")
    private Long threadId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThreadMentionId mentionId = (ThreadMentionId) o;
        return Objects.equals(getUserId(), mentionId.getUserId()) && Objects.equals(getThreadId(), mentionId.getThreadId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getThreadId());
    }
}
