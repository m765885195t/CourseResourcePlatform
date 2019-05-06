package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.ClazzChapterData;
import com.motian.crp.dao.data.StudentClazzCourseInfoData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface StudentClazzCourseInfoManager extends JpaRepository<StudentClazzCourseInfoData, Long> {
    Optional<StudentClazzCourseInfoData> getByStudentIdAndClazzCourseId(String studentId, long clazzCourseId);
}
