package me.quann.taesunjpa.userChannel;

import me.quann.taesunjpa.channel.Channel;
import me.quann.taesunjpa.channel.ChannelRepository;
import me.quann.taesunjpa.thread.ThreadRepository;
import me.quann.taesunjpa.user.User;
import me.quann.taesunjpa.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserChannelRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserChannelRepository userChannelRepository;

    @Test
    void userJoinChannelTest() {
        var newChannel = Channel.builder().name("new-channel").build();
        var newUser = User.builder().username("new_user").password("new-pass").build();
        var newUserChannel = newChannel.joinUser(newUser);

        var savedChannel = channelRepository.save(newChannel);
        var savedUser = userRepository.save(newUser);
        var savedUserChannel = userChannelRepository.insertUserChannel(newUserChannel);

        var foundChannel = channelRepository.findById(savedChannel.getId()).get();
        assert foundChannel.getUserChannels().stream()
                .map(UserChannel::getChannel)
                .map(Channel::getName)
                .anyMatch(name -> name.equals(newChannel.getName()));
    }

    @Test
    void userJoinChannelWithCascadeTest() {
        var newChannel = Channel.builder().name("new-channel").build();
        var newUser = User.builder().username("new_user").password("new-pass").build();
        newChannel.joinUser(newUser);

        var savedChannel = channelRepository.save(newChannel);
        var savedUser = userRepository.save(newUser);

        var foundChannel = channelRepository.findById(savedChannel.getId()).get();
        assert foundChannel.getUserChannels().stream()
                .map(UserChannel::getChannel)
                .map(Channel::getName)
                .anyMatch(name -> name.equals(newChannel.getName()));
    }

}