package com.motian.crp.service;

import com.motian.crp.dao.data.ClazzCourseData;
import com.motian.crp.dao.manager.ClazzCourseManager;
import com.motian.crp.utils.CrpServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@Service
public class ClazzCourseService {
    @Autowired
    private ClazzCourseManager manager;

    @Transactional
    public void insert(String clazzName, String courseName, String cover) {
        manager.save(new ClazzCourseData()
                .setClazzCourseId(CrpServiceUtils.generateId())
                .setClazzName(clazzName)
                .setCourseName(courseName)
                .setCover(cover));
    }


    public ClazzCourseData update(long id, String clazzName, String courseName, String cover)
            throws Exception {
        Optional<ClazzCourseData> data = manager.findById(id);
        if (!data.isPresent()) {
            throw new Exception("The user does not exist. id=" + id);
        }
        if (!StringUtils.isBlank(clazzName)) {
            data.get().setClazzName(clazzName);
        }
        if (!StringUtils.isBlank(courseName)) {
            data.get().setCourseName(courseName);
        }

        if (!StringUtils.isBlank(cover)) {
            data.get().setCover(cover);
        }
        return manager.save(data.get());
    }

    public void delete(long id) {
        manager.findById(id).ifPresent((o) -> manager.delete(o));
    }

    public ClazzCourseData getByClazzCourseId(long clazzCourseId) {
        return manager.getByClazzCourseId(clazzCourseId).orElse(null);
    }

    public List<ClazzCourseData> listAll(int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent();
    }

}