package me.quann.taesunjpa.user;

import jakarta.persistence.*;
import lombok.*;
import me.quann.taesunjpa.common.TimeStamp;
import me.quann.taesunjpa.mention.CommentMention;
import me.quann.taesunjpa.mention.ThreadMention;
import me.quann.taesunjpa.userChannel.UserChannel;

import java.util.LinkedHashSet;
import java.util.Set;

// lombok
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서 빈 생성자 사용하지 못하도록
@ToString

// jpa
@Entity
@Table(name = "users")
public class User extends TimeStamp {

    /**
     * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String username;

    private String password;

    /**
     * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
     */
    @Builder
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    @ToString.Exclude // ToString Exclude 하기
    private Set<UserChannel> userChannels = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentMention> commentMentions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ThreadMention> threadMentions = new LinkedHashSet<>();

    /**
     * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
     */

    /**
     * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
     */
    public void updateUserName(String username) {
        this.username = username;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}