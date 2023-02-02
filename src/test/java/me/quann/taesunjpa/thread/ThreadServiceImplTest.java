package me.quann.taesunjpa.thread;

import me.quann.taesunjpa.channel.Channel;
import me.quann.taesunjpa.channel.ChannelRepository;
import me.quann.taesunjpa.comment.Comment;
import me.quann.taesunjpa.comment.CommentRepository;
import me.quann.taesunjpa.common.PageDto;
import me.quann.taesunjpa.emotion.Emotion;
import me.quann.taesunjpa.user.User;
import me.quann.taesunjpa.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ThreadServiceImplTest {

    @Autowired
    ThreadService threadService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    void getMentionedThreadTest() {
        // given
        var newChannel = Channel.builder().name("c1").type(Channel.Type.PUBLIC).build();
        var savedChannel = channelRepository.save(newChannel);
        getTestThread("message", savedChannel);

        Thread newThread2 = getTestThread("", newChannel);

        // when
        List<Thread> notEmptyThreads = threadService.selectNotEmptyThreadList(savedChannel);

        // queryDsl 은 조인쿼리가 안된다.

        // then
//        System.out.println(mentionedThreads);
        assert !notEmptyThreads.contains(newThread2);
//        assert mentionedThreads.containsAll(List.of(newThread, newThread2));
    }

    @DisplayName("2. ")
    @Test
    void test_2() {

    }

    @DisplayName("3. 전체 채널에서 내가 멘션된 쓰레드 상세정보 목록 테스트")
    @Test
    void test_3 () {
        // given
        var user = getTestUser("1", "1");
        var newChannel = Channel.builder().name("c1").type(Channel.Type.PUBLIC).build();
        var savedChannel = channelRepository.save(newChannel);
        var thread1 = getTestThread("message", savedChannel, user);
        var thread2 = getTestThread("", newChannel, user);

        // when
        PageDto pageDto = PageDto.builder().currentPage(1).size(100).build();
        var mentionedThreadList = threadService.selectMentionedThreadList(user.getId(), pageDto);

        // then
        assert mentionedThreadList.getTotalElements() == 2;
    }

    @DisplayName("4. 전체 채널에서 내가 멘션된 쓰레드 상세정보 목록 테스트")
    @Test
    void test_4 () {
        // given
        var user1 = getTestUser("1", "1");
        var user2 = getTestUser("2", "2");
        var newChannel = Channel.builder().name("c1").type(Channel.Type.PUBLIC).build();
        var savedChannel = channelRepository.save(newChannel);
        var thread1 = getTestThread("message", savedChannel, user1, user2, "e1");
        var thread2 = getTestThread("", newChannel, user1, user2, "e2");

        // when
        PageDto pageDto = PageDto.builder().currentPage(1).size(100).build();
        var mentionedThreadList = threadService.selectMentionedThreadList(user1.getId(), pageDto);

        // then
        assert mentionedThreadList.getTotalElements() == 2;
    }

    @Test
    @DisplayName("전체 채널에서 내가 멘션된 쓰레드 상세정보 목록 테스트")
    void selectMentionedThreadListTest() {
        // given
        var user = getTestUser("1", "1");
        var user2 = getTestUser("2", "2");
        var user3 = getTestUser("3", "3");
        var user4 = getTestUser("3", "4");
        var newChannel = Channel.builder().name("c1").type(Channel.Type.PUBLIC).build();
        var savedChannel = channelRepository.save(newChannel);
        var thread2 = getTestThread("", savedChannel, user
                , user2, "e2", user3, "c2", user4, "ce2");
        var thread1 = getTestThread("message", savedChannel, user
                , user2, "e1", user3, "c1", user4, "ce1");

        // when
        var pageDTO = PageDto.builder().currentPage(1).size(100).build();
        var mentionedThreadList = threadService.selectMentionedThreadList(user.getId(), pageDTO);

        // then
        assert mentionedThreadList.getTotalElements() == 2;
    }

    private User getTestUser(String username, String password) {
        var newUser = User.builder().username(username).password(password).build();
        return userRepository.save(newUser);
    }

    private Comment getTestComment(User user, String message) {
        var newComment = Comment.builder().message(message).build();
        newComment.setUser(user);
        return commentRepository.save(newComment);
    }

    private Thread getTestThread(String message, Channel savedChannel) {
        var newThread = Thread.builder().message(message).build();
        newThread.setChannel(savedChannel);
        return threadService.insert(newThread);
    }

    private Thread getTestThread(String message, Channel channel, User mentionedUser) {
        var newThread = getTestThread(message, channel);
        newThread.addMention(mentionedUser);
        return threadService.insert(newThread);
    }

    private Thread getTestThread(String message, Channel channel, User mentionedUser,
                                 User emotionUser, String emotionValue) {
        var newThread = getTestThread(message, channel, mentionedUser);
        newThread.addEmotion(emotionUser, emotionValue);
        return threadService.insert(newThread);
    }

    private Thread getTestThread(String message, Channel channel, User mentionedUser,
                                 User emotionUser, String emotionValue, User commentUser, String commentMessage) {
        var newThread = getTestThread(message, channel, mentionedUser, emotionUser, emotionValue);
        newThread.addComment(getTestComment(commentUser, commentMessage));
        return threadService.insert(newThread);
    }

    private Thread getTestThread(String message, Channel channel, User mentionedUser,
                                 User emotionUser, String emotionValue, User commentUser, String commentMessage,
                                 User commentEmotionUser, String commentEmotionValue) {
        var newThread = getTestThread(message, channel, mentionedUser, emotionUser, emotionValue,
                commentUser, commentMessage);
        newThread.getComments()
                .forEach(comment -> comment.addEmotion(commentEmotionUser, commentEmotionValue));
        return threadService.insert(newThread);
    }
}