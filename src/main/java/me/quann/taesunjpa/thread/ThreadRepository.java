package me.quann.taesunjpa.thread;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.quann.taesunjpa.channel.Channel;
import me.quann.taesunjpa.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

//@Repository
//public class ThreadRepository {
//
//    @PersistenceContext
//    EntityManager entityManager;
//
//    public Thread insertThread(Thread thread) {
//        entityManager.persist(thread);
//        return thread;
//    }
//
//    public Thread selectThread(Long id) {
//        return entityManager.find(Thread.class, id);
//    }
//
//}

@RepositoryDefinition(domainClass = Thread.class, idClass = Long.class)
public interface ThreadRepository extends JpaRepository<Thread, Long>, QuerydslPredicateExecutor<Thread>, ThreadRepositoryQuery {

}