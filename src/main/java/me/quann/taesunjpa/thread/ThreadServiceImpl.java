package me.quann.taesunjpa.thread;

import com.mysema.commons.lang.IteratorAdapter;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import me.quann.taesunjpa.channel.Channel;
import me.quann.taesunjpa.common.PageDto;
import me.quann.taesunjpa.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThreadServiceImpl implements ThreadService {

    private final ThreadRepository threadRepository;


    @Override
    public List<Thread> getMentionedThreadList(User user) {
        QThread thread = QThread.thread;
        BooleanExpression predicate = thread.mentions.any().user.eq(user);
        Iterable<Thread> threads = threadRepository.findAll(predicate);
        return IteratorAdapter.asList(threads.iterator());
    }

    // 메세지가 비어있지 않은 해당 채널의 쓰레드 목록
    @Override
    @Transactional(readOnly = true)
    public List<Thread> selectNotEmptyThreadList(Channel channel) {
        QThread thread = QThread.thread;
        BooleanExpression predicate = thread.channel.eq(channel).and(thread.message.isNotEmpty());
        Iterable<Thread> threads = threadRepository.findAll(predicate);
        return IteratorAdapter.asList(threads.iterator());
    }

    @Override
    public Thread insert(Thread thread) {
        return threadRepository.save(thread);
    }

    @Override
    @Transactional
    public Page<Thread> selectMentionedThreadList(Long userId, PageDto pageDTO) {
        ThreadSearchCond cond = ThreadSearchCond.builder().mentionedUserId(userId).build();
        return threadRepository.search(cond, pageDTO.toPageable());
    }
}
