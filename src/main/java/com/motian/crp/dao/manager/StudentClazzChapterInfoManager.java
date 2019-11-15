package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.StudentClazzChapterInfoData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface StudentClazzChapterInfoManager extends JpaRepository<StudentClazzChapterInfoData, Long> {
    Optional<StudentClazzChapterInfoData> getByStudentIdAndContent(String studentId, String content);
    Optional<StudentClazzChapterInfoData> getByStudentIdAndClazzChapterId(String studentId, long clazzChapterId);
}
