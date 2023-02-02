package me.quann.taesunjpa.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
//public class UserRepository {
//
//    @PersistenceContext
//    EntityManager entityManager;
//
//    public User insertUser(User user) {
//        entityManager.persist(user);
//        return user;
//    }
//
//    public User selectUser(Long id) {
//        return entityManager.find(User.class, id);
//    }
//
//}

// 변경 후
public interface UserRepository extends JpaRepository<User, Long> {

}

// 기능 제한법
//@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
//public interface UserRepository {
//    public Optional<User> findByUsername(String username);
//    // findByPassword를 막기위해 - 필요한 기능만을 열어둘 수 있음 - 레포지토리가 가진 기능을 제한함
//}