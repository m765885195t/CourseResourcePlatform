package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.ClazzCourseData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface ClazzCourseManager extends JpaRepository<ClazzCourseData, Long> {
    Optional<ClazzCourseData> getByClazzCourseId(long clazzCourseId);
}
