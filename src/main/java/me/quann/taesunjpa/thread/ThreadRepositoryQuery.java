package me.quann.taesunjpa.thread;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

//@RepositoryDefinition(domainClass = Thread.class, idClass = Long.class)
public interface ThreadRepositoryQuery {

    Page<Thread> search(ThreadSearchCond cond, Pageable pageable);
}
