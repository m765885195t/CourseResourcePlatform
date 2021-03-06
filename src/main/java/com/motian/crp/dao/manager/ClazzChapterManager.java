package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.ClazzChapterData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface ClazzChapterManager extends JpaRepository<ClazzChapterData, Long> {
    Optional<ClazzChapterData> getByClazzCourseIdAndSequence(long clazzCourseId, int sequence);

    List<ClazzChapterData> getByClazzCourseId(long clazzCourseId);

    void deleteByClazzCourseId(long clazzCourseId);
}
