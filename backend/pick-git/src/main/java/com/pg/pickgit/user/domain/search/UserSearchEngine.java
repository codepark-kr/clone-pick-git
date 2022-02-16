package com.pg.pickgit.user.domain.search;

import com.pg.pickgit.user.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserSearchEngine extends ElasticsearchRepository<User, Long>, CustomUserSearchEngine{
}
