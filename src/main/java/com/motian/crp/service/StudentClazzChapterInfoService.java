package com.motian.crp.service;

import com.motian.crp.constant.CrpConst;
import com.motian.crp.dao.data.ClazzChapterData;
import com.motian.crp.dao.data.QuestionBankData;
import com.motian.crp.dao.data.StudentClazzChapterInfoData;
import com.motian.crp.dao.manager.ClazzChapterManager;
import com.motian.crp.dao.manager.StudentClazzChapterInfoManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentClazzChapterInfoService {
    @Autowired
    private StudentClazzChapterInfoManager studentClazzChapterInfoManager;
    @Autowired
    private ClazzChapterManager clazzChapterManager;
    @Autowired
    private ClazzCourseService clazzCourseService;
    @Autowired
    private ClazzChapterService clazzChapterService;
    @Autowired
    private StudentClazzCourseInfoService studentClazzCourseInfoService;
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ClazzChapterResourcesService clazzChapterResourcesService;

    @Transactional
    public boolean save(String studentId, long clazzChapterId, String[] answer) {
        ClazzChapterData clazzChapterData =
                clazzChapterService.getById(clazzChapterId);
        QuestionBankData questionBankData =
                questionBankService.get(clazzChapterData.getQuestionId());

        Optional<StudentClazzChapterInfoData> data = studentClazzChapterInfoManager
                .getByStudentIdAndContent(studentId, questionBankData.getContent());
        if (data.isPresent()) {
            log.info("save={}", false);
            return false;
        }

        studentClazzChapterInfoManager.save(new StudentClazzChapterInfoData()
                .setStudentId(studentId)
                .setContent(questionBankData.getContent())
                .setClazzCourseId(clazzChapterData.getClazzCourseId())
                .setClazzCourseName(clazzChapterData.getClazzCourseName())
                .setClazzChapterId(clazzChapterData.getId())
                .setClazzChapterName(clazzChapterData.getClazzChapterName())
                .setAnswer(StringUtils.join(answer, CrpConst.SEPARATOR))
        );
        return true;
    }

    public Optional<StudentClazzChapterInfoData> get(String student, long clazzChapterId) {
        return studentClazzChapterInfoManager.getByStudentIdAndClazzChapterId(student, clazzChapterId);
    }

    public boolean updateResults(String studentId, int results, long clazzChapterId) {

        Optional<StudentClazzChapterInfoData> data = studentClazzChapterInfoManager
                .getByStudentIdAndClazzChapterId(studentId, clazzChapterId);
        if (data.isPresent()) {
            data.get().setResults(results);
            studentClazzChapterInfoManager.save(data.get());
            return true;
        }
        return false;
    }

    public List<StudentClazzChapterInfoData> listByTeacherId(String userId, long clazzCourseId) {
        return studentClazzChapterInfoManager.findAll()
                .stream()
                .filter(o -> clazzCourseId < 0 || o.getClazzCourseId() == clazzCourseId)
                .collect(Collectors.toList());
    }
}
