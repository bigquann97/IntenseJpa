package me.quann.taesunjpa.thread;

import me.quann.taesunjpa.channel.Channel;
import me.quann.taesunjpa.common.PageDto;
import me.quann.taesunjpa.user.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ThreadService {

    List<Thread> getMentionedThreadList(User user);

    Thread insert(Thread thread);

    List<Thread> selectNotEmptyThreadList(Channel channel);

    Page<Thread> selectMentionedThreadList(Long userId, PageDto pageDto);

    Page<Thread> selectUserThreads(Long userId, PageDto pageDto);
}
