package me.quann.taesunjpa.thread;

import me.quann.taesunjpa.channel.Channel;
import me.quann.taesunjpa.channel.ChannelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ThreadRepositoryTest {

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Test
    void insertSelectThreadTest() {
        var newChannel = Channel.builder().name("new-channel").build();
        var newThread = Thread.builder().message("new-message").build();
        var newThread2 = Thread.builder().message("new-message2").build();
        newThread.setChannel(newChannel);
        newThread2.setChannel(newChannel);

//        var savedThread = threadRepository.insertThread(newThread);
        var savedThread = threadRepository.save(newThread);
//        var savedThread2 = threadRepository.insertThread(newThread2);
        var savedThread2 = threadRepository.save(newThread2);
//        var savedChannel = channelRepository.insertChannel(newChannel);
        var savedChannel = channelRepository.save(newChannel);

//        Channel foundChannel = channelRepository.selectChannel(savedChannel.getId());
        Channel foundChannel = channelRepository.findById(savedChannel.getId()).get();
//        var foundThread = threadRepository.selectThread(savedThread.getId());
        var foundThread = threadRepository.findById(savedThread.getId());
        assert foundChannel.getThreads().containsAll(Set.of(savedThread, savedThread2));
        assert foundThread.get().getChannel().getName().equals(newChannel.getName());
    }

    @Test
    void deleteThreadByOrphanRemovalTest() throws InterruptedException {
        var newChannel = Channel.builder().name("new-channel").build();
        var newThread = Thread.builder().message("new-message").build();
        var newThread2 = Thread.builder().message("new-message2").build();
        newThread.setChannel(newChannel);
        newThread2.setChannel(newChannel);
//        var savedThread = threadRepository.insertThread(newThread);
        var savedThread = threadRepository.save(newThread);
//        var savedThread2 = threadRepository.insertThread(newThread2);
        var savedThread2 = threadRepository.save(newThread2);
//        var savedChannel = channelRepository.insertChannel(newChannel);
        var savedChannel = channelRepository.save(newChannel);

//        Channel foundChannel = channelRepository.selectChannel(newChannel.getId());
        Channel foundChannel = channelRepository.findById(newChannel.getId()).get();
        foundChannel.getThreads().remove(savedThread);
    }

}