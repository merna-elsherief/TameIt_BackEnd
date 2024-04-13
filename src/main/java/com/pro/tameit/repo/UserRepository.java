package com.pro.tameit.repo;

import com.pro.tameit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
    @Transactional
    @Modifying
    @Query("update User u set u.password = ?2 where u.userName = ?1")
    void updatePassword(String userName, String password);
}
