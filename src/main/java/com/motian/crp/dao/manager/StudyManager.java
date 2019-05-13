package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.StudyData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface StudyManager extends JpaRepository<StudyData, Long> {
    StudyData getByTeacherIdAndStudentIdAndClazzChapterIdAndClazzCourseIdAndResourceId(
            String studentId, String teacherId, long clazzCourseId,
            long clazzChapterId, long resourceId);

    List<StudyData> getByTeacherIdAndClazzChapterIdAndClazzCourseIdAndResourceId(
            String teacherId, long clazzCourseId,
            long clazzChapterId, long resourceId);

    List<StudyData> getByTeacherId(String teacherId, Pageable pageable);
}
