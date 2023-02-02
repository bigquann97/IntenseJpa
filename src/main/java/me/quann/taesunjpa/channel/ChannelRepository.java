package me.quann.taesunjpa.channel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.quann.taesunjpa.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

//@Repository
//public class ChannelRepository {
//
//    @PersistenceContext
//    EntityManager entityManager;
//
//    public Channel insertChannel(Channel channel) {
//        entityManager.persist(channel);
//        return channel;
//    }
//
//    public Channel selectChannel(Long id) {
//        return entityManager.find(Channel.class, id);
//    }
//
//}

public interface ChannelRepository extends JpaRepository<Channel, Long>, QuerydslPredicateExecutor<Channel> {

}