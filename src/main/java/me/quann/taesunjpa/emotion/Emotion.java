package me.quann.taesunjpa.emotion;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.quann.taesunjpa.thread.Thread;
import me.quann.taesunjpa.user.User;

// lombok
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서 빈 생성자 사용하지 못하도록

// jpa
@MappedSuperclass
public class Emotion {

    /**
     * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
     */
    @Getter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    protected String body;
    /**
     * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
     */

    /**
     * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
     */

    @ManyToOne
    @MapsId("user_id")
    protected User user;

    /**
     * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
     */


    /**
     * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
     */
}
