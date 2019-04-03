package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.StudentData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: gongzhanjing
 * @Email: gongzhanjing@xiyoulinux.org
 */

public interface StudentRepository extends JpaRepository<StudentData, String> {
}
