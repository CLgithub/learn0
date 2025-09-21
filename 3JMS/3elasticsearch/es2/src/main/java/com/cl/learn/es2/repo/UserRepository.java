package com.cl.learn.es2.repo;

import com.cl.learn.es2.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {
    List<User> findByName(String name);
    List<User> findByNameContaining(String keyword);
}
