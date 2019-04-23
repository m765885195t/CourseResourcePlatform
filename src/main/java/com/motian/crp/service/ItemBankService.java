package com.motian.crp.service;

import com.motian.crp.dao.data.ItemBankData;
import com.motian.crp.dao.manager.ItemBankManager;
import com.motian.crp.utils.CrpServiceUtils;
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
public class ItemBankService {
    @Autowired
    private ItemBankManager manager;

    @Transactional
    public void insert(String teacherId, String studentId, long clazzCourseId,
                       long learningTime) {
        manager.save(new ItemBankData()
                .setItemBankId(CrpServiceUtils.generateId())
                .setTeacherId(teacherId)
                .setStudentId(studentId)
                .setClazzCourseId(clazzCourseId)
                .setLearningTime(learningTime));
    }


    public ItemBankData update(long id, String teacherId, String studentId, long clazzCourseId,
                               long learningTime)
            throws Exception {
        Optional<ItemBankData> data = manager.findById(id);
        if (!data.isPresent()) {
            throw new Exception("The user does not exist. id=" + id);
        }
        if (!StringUtils.isBlank(teacherId)) {
            data.get().setTeacherId(teacherId);
        }
        if (!StringUtils.isBlank(studentId)) {
            data.get().setStudentId(studentId);
        }
        if (clazzCourseId > 0) {
            data.get().setClazzCourseId(clazzCourseId);
        }
        if (learningTime > 0) {
            data.get().setLearningTime(learningTime);
        }

        return manager.save(data.get());
    }

    public void delete(long id) {
        manager.findById(id).ifPresent((o) -> manager.delete(o));
    }


    public List<ItemBankData> listAllByTeacherId(String teacherId, int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent()
                .stream().filter(o -> o.getTeacherId().equals(teacherId))
                .collect(Collectors.toList());
    }

    public List<ItemBankData> listAll(int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent();
    }
}
