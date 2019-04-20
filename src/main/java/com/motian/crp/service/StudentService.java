package com.motian.crp.service;

import com.motian.crp.dao.data.StudentData;
import com.motian.crp.dao.manager.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public StudentData addUserInfo(String name) {
        StudentData studentData = StudentData
                .builder()
                .studentName(name)
                .build();
        studentRepository.save(studentData);
        return studentData;
    }

    public List<StudentData> listAll() {
        return studentRepository.findAll();
    }

    public StudentData getById(Long studuntId) {
        Optional<StudentData> studentData = studentRepository.findById(studuntId);
        return studentData.orElse(null);
    }
}
