package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.ClazzChapterData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface ClazzChapterManager extends JpaRepository<ClazzChapterData, Long> {
    Optional<ClazzChapterData> getByOrder(int order);

    Optional<ClazzChapterData> getByClazzCourseIdAndClazzChapterName(long clazzCourseId, String clazzChapterName);

}