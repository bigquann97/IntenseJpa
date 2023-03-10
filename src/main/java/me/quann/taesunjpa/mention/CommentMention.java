package me.quann.taesunjpa.mention;

import jakarta.persistence.*;
import lombok.*;
import me.quann.taesunjpa.comment.Comment;
import me.quann.taesunjpa.common.TimeStamp;
import me.quann.taesunjpa.thread.Thread;
import me.quann.taesunjpa.user.User;

// lombok
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서 빈 생성자 사용하지 못하도록

// jpa
@Entity
public class CommentMention extends TimeStamp {


    /**
     * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
     */

    @EmbeddedId
    private CommentMentionId commentMentionId;



    /**
     * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
     */
    @Builder
    public CommentMention(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    /**
     * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
     */
    @ManyToOne
    @MapsId("user_id")
    User user;

    @ManyToOne
    @MapsId("comment_id")
    private Comment comment;
    /**
     * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
     */


    /**
     * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
     */
}
