package com.pg.pickgit.user.domain.search;

import com.pg.pickgit.user.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomUserSearchEngine {

    List<User> searchByUsernameLike(String username, Pageable pageable);
}
