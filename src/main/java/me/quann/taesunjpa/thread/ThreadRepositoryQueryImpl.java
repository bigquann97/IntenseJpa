package me.quann.taesunjpa.thread;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.quann.taesunjpa.channel.QChannel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Objects;

import static me.quann.taesunjpa.thread.QThread.*;

@RequiredArgsConstructor
public class ThreadRepositoryQueryImpl implements ThreadRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Thread> search(ThreadSearchCond cond, Pageable pageable) {
        var query =
                query(thread, cond)
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize());

//        var countQuery = query(Wildcard.count, cond);

        var threads = query.fetch();
        long totalSize = countQuery(cond).fetch().get(0);

        return PageableExecutionUtils.getPage(threads, pageable, () -> totalSize);
    }

    private <T> JPAQuery<T> query(Expression<T> expr, ThreadSearchCond cond) {
        return jpaQueryFactory.select(expr)
                .from(thread)
                .leftJoin(thread.channel, QChannel.channel).fetchJoin()
                .where(
                        channelIdEq(cond.getChannelId()),
                        mentionedUserIdEq(cond.getMentionedUserId())
                );
    }

    private JPAQuery<Long> countQuery(ThreadSearchCond cond) {
        return jpaQueryFactory.select(Wildcard.count)
                .from(thread)
                .where(
                        channelIdEq(cond.getChannelId()),
                        mentionedUserIdEq(cond.getMentionedUserId())
                );
    }

    private BooleanExpression channelIdEq(Long channelId) {
        return Objects.nonNull(channelId) ? thread.channel.id.eq(channelId) : null;
    }

    private BooleanExpression mentionedUserIdEq(Long mentionedUserId) {
        return Objects.nonNull(mentionedUserId) ? thread.mentions.any().user.id.eq(mentionedUserId) : null;
    }

}
