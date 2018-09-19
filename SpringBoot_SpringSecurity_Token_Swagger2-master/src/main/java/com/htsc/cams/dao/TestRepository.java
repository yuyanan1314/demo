package com.htsc.cams.dao;

import com.htsc.cams.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by admin on 2017/12/19.
 */
public interface TestRepository extends JpaRepository<Test, String> {

    Test findById(Integer id);

}
