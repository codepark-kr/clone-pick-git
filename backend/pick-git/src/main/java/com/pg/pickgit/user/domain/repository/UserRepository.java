package com.pg.pickgit.user.domain.repository;

import com.pg.pickgit.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByBasicProfile_Name(String name);

    @Query("select u from User u where u.basicProfile.name like %:username%")
    List<User> searchByUsernameLike(@Param("username") String username, Pageable pageable);

    @Query("select t from Follow f inner join f.target t on f.source = :user")
    List<User> searchFollowingsOf(@Param("user") User user, Pageable pageable);

    @Query("select s from Follow f inner join f.source s on f.target = :user")
    List<User> searchFollowersOf(@Param("user") User user, Pageable pageable);
}
