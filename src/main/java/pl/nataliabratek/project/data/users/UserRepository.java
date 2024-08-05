package pl.nataliabratek.project.data.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    //@Query("select user from UserEntity user where user.unconfirmedToken = :confirmationToken")
    Optional<UserEntity> findByUnconfirmedToken(String confirmationToken);
    //@Query("update UserEntity user set user.unconfirmedToken = null where user.unconfirmedToken = :token")
    //void updateUnconfirmedToken(String token);
}
