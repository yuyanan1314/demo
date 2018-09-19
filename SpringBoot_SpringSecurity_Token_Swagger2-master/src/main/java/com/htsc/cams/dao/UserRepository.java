package com.htsc.cams.dao;

import com.htsc.cams.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by admin on 2018/1/23.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
