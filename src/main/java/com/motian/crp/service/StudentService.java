package com.motian.crp.service;

import com.motian.crp.dao.data.StudentData;
import com.motian.crp.dao.manager.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: gongzhanjing
 * @Email: gongzhanjing@xiyoulinux.org
 */
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public StudentData addUserInfo(String name) {
        StudentData studentData = StudentData
                .builder()
                .studentId(1)
                .studentName(name)
                .build();
        studentRepository.save(studentData);
        return studentData;
    }
}
