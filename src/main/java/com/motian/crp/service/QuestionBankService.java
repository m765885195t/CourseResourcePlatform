package com.motian.crp.service;

import com.google.common.collect.Maps;
import com.motian.crp.constant.CrpConst;
import com.motian.crp.dao.data.QuestionBankData;
import com.motian.crp.dao.manager.QuestionBankManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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
    public void insert(String teacherId, List<String> content, String questionName) {
        manager.save(new QuestionBankData()
                .setTeacherId(teacherId)
                .setContent(StringUtils.join(content, CrpConst.SEPARATOR))
                .setQuestionName(questionName)
        );
    }


    public void delete(long id) {
        manager.findById(id).ifPresent((o) -> manager.delete(o));
    }

    public QuestionBankData get(long id) {
        return manager.findById(id).orElse(null);
    }


    public List<QuestionBankData> listAll(String teacherId, long questionBankId, int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent()
                .stream()
                .filter(o -> teacherId.equals(o.getTeacherId()))
                .filter(o -> questionBankId < 0 || questionBankId == o.getId())
                .collect(Collectors.toList());
    }

    public Map<Long, String> select(String teacherId) {
        Map<Long, String> map = Maps.newHashMap();
        manager.getByTeacherId(teacherId).forEach(o -> {
            map.put(o.getId(), o.getQuestionName());
        });

        return map;
    }
}
