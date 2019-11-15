package com.motian.crp.service;

import com.motian.crp.dao.data.StudyData;
import com.motian.crp.dao.manager.StudyManager;
import com.motian.crp.utils.CrpServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */

@Service
public class StudyService {
    @Autowired
    private StudyManager manager;
    @Autowired
    private ResourceService service;

    @Transactional
    public void update(String studentId, String teacherId, long clazzCourseId,
                       long clazzChapterId, long resourceId) {
        StudyData data = manager.getByTeacherIdAndStudentIdAndClazzChapterIdAndClazzCourseIdAndResourceId(
                teacherId, studentId, clazzChapterId, clazzCourseId, resourceId);
        if (data == null) {
            data = manager.save(new StudyData()
                    .setTeacherId(teacherId)
                    .setClazzChapterId(clazzChapterId)
                    .setClazzCourseId(clazzCourseId)
                    .setStudentId(studentId)
                    .setResourceId(resourceId)
            );
        }
        if (CrpServiceUtils.isVideo(service.get(resourceId).getResourceType())) {
            data.setWatchNumber(data.getWatchNumber() + 1);
        } else {
            data.setDownloads(data.getDownloads() + 1);
        }
        manager.save(data);
        updateRanking(teacherId, clazzCourseId, clazzChapterId, resourceId);
    }


    private void updateRanking(String teacherId, long clazzCourseId, long clazzChapterId, long resourceId) {
        List<StudyData> sortData = manager.getByTeacherIdAndClazzChapterIdAndClazzCourseIdAndResourceId(
                teacherId, clazzChapterId, clazzCourseId, resourceId)
                .stream()
                .sorted(Comparator.comparing(StudyData::getDownloads))
                .collect(Collectors.toList());
        AtomicInteger i = new AtomicInteger(1);
        sortData.forEach(o -> {
            o.setRanking(i.getAndIncrement());
            manager.save(o);
        });
    }

    public StudyData get(long id) {
        return manager.getOne(id);
    }

    public List<StudyData> listByTeacherId(String teacherId, long clazzCourseId, int pageNumber, int pageSize) {
        return manager.getByTeacherId(teacherId, PageRequest.of(pageNumber - 1, pageSize))
                .stream()
                .filter(o -> clazzCourseId < 0 || clazzCourseId == o.getClazzCourseId())
                .collect(Collectors.toList());
    }
}
