package com.motian.crp.service;

import com.motian.crp.dao.data.StudentClazzCourseInfoData;
import com.motian.crp.dao.manager.StudentClazzCourseInfoManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@Service
public class StudentClazzCourseInfoService {
    private final StudentClazzCourseInfoManager manager;

    @Autowired
    public StudentClazzCourseInfoService(StudentClazzCourseInfoManager manager) {
        this.manager = manager;
    }

    @Transactional
    public void insert(String studentId, long clazzCourseId) {
        manager.getByStudentIdAndClazzCourseId(studentId, clazzCourseId)
                .orElseGet(() ->
                        manager.save(new StudentClazzCourseInfoData()
                                .setStudentId(studentId))
                                .setClazzCourseId(clazzCourseId)
                );
    }


    public StudentClazzCourseInfoData update(long id) throws Exception {
        Optional<StudentClazzCourseInfoData> data = manager.findById(id);
        if (!data.isPresent()) {
            throw new Exception("The StudentClazzCourseInfoData does not exist. id=" + id);
        }

        return manager.save(data.get());
    }

    public void delete(long id) {
        manager.findById(id).ifPresent(manager::delete);
    }


    public List<StudentClazzCourseInfoData> listAllByStudentId(String studentId, int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent()
                .stream().filter(o -> o.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<StudentClazzCourseInfoData> listAllByClazzCourseId(long clazzCourseId, int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent()
                .stream().filter(o -> o.getClazzCourseId() == clazzCourseId)
                .collect(Collectors.toList());
    }
}
