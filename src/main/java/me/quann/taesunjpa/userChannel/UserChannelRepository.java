package me.quann.taesunjpa.userChannel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserChannelRepository {

    @PersistenceContext
    EntityManager entityManager;

    public UserChannel insertUserChannel(UserChannel userChannel) {
        entityManager.persist(userChannel);
        return userChannel;
    }

    public UserChannel selectUserChannel(Long id) {
        return entityManager.find(UserChannel.class, id);
    }

}


// cascade, orphanremoval 시 생명주기를 함게하기때문에 Repository 자체가필요없어질 수 있다 !