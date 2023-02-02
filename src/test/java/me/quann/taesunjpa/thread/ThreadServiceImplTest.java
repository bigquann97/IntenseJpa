package me.quann.taesunjpa.thread;

import me.quann.taesunjpa.channel.Channel;
import me.quann.taesunjpa.channel.ChannelRepository;
import me.quann.taesunjpa.common.PageDto;
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
        var user = getTestUser();
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

    private User getTestUser() {
        var newUser = User.builder().username("new").password("1").build();
        return userRepository.save(newUser);
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
}