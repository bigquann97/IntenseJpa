package me.quann.taesunjpa.thread;

import me.quann.taesunjpa.common.SearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//@RepositoryDefinition(domainClass = Thread.class, idClass = Long.class)
public interface ThreadRepositoryQuery {

    Page<Thread> search(ThreadSearchCond cond, Pageable pageable);

    Page<Thread> searchUserThreads(SearchCond cond, Pageable pageable);

}
