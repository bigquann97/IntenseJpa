package me.quann.taesunjpa.channel;

import com.querydsl.core.types.Predicate;
import me.quann.taesunjpa.thread.Thread;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ChannelRepositoryTest {

    @Autowired
    private ChannelRepository channelRepository;

//    @Autowired
//    ChannelJpaRepository channelJpaRepository;

    @Test
    void queryDslTest() {
        // given
        var newChannel = Channel.builder().name("teasun").build();
        channelRepository.save(newChannel);

        Predicate predicate = QChannel.channel
                .name.equalsIgnoreCase("TEASUN");

        // when
        Optional<Channel> optional = channelRepository.findOne(predicate);

        // then
        assert optional.get().getName().equals(newChannel.getName());
    }

    @Test
    void insertSelectChannel() {
        var newChannel = Channel.builder().name("new-channel").build();

//        var savedChannel = channelRepository.insertChannel(newChannel);
        var savedChannel = channelRepository.save(newChannel);

//        var foundChannel = channelRepository.selectChannel(savedChannel.getId());
        var foundChannel = channelRepository.findById(savedChannel.getId());
        assert foundChannel.get().getId().equals(savedChannel.getId());
    }
}