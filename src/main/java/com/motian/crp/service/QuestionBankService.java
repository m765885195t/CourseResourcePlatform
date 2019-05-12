package com.motian.crp.service;

import com.motian.crp.dao.data.QuestionBankData;
import com.motian.crp.dao.manager.QuestionBankManager;
import org.apache.commons.lang3.StringUtils;
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

@Service
public class QuestionBankService {
    @Autowired
    private QuestionBankManager manager;

    @Transactional
    public void insert(String teacherId, String content) {
        manager.save(new QuestionBankData()
                .setTeacherId(teacherId)
                .setContent(content)
        );
    }


    public QuestionBankData update(long id, String teacherId, String studentId, long clazzCourseId,
                                   long learningTime)
            throws Exception {
        Optional<QuestionBankData> data = manager.findById(id);
        if (!data.isPresent()) {
            throw new Exception("The user does not exist. id=" + id);
        }
        if (!StringUtils.isBlank(teacherId)) {
            data.get().setTeacherId(teacherId);
        }

        return manager.save(data.get());
    }

    public void delete(long id) {
        manager.findById(id).ifPresent((o) -> manager.delete(o));
    }


    public List<QuestionBankData> listAll(String teacherId, long questionBankId, int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent()
                .stream()
                .filter(o -> teacherId.equals(o.getTeacherId()))
                .filter(o -> questionBankId < 0 || questionBankId == o.getId())
                .collect(Collectors.toList());
    }
}
