package com.motian.crp.service;

import com.motian.crp.dao.data.ClazzChapterData;
import com.motian.crp.dao.manager.ClazzChapterManager;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class ClazzChapterService {
    private final ClazzChapterManager manager;

    @Autowired
    public ClazzChapterService(ClazzChapterManager manager) {
        this.manager = manager;
    }

    @Transactional
    public void insert(long clazzCourseId, int order, String clazzChapterName) {
        manager.getByClazzCourseIdAndClazzChapterName(clazzCourseId, clazzChapterName)
                .orElseGet(() ->
                        manager.save(new ClazzChapterData()
                                .setClazzCourseId(clazzCourseId)
                                .setClazzChapterName(clazzChapterName)
                                .setOrder(order))
                );
    }


    public ClazzChapterData update(long id, String clazzChapterName) throws Exception {
        Optional<ClazzChapterData> data = manager.findById(id);
        if (!data.isPresent()) {
            throw new Exception("The ClazzChapterData does not exist. id=" + id);
        }
        if (StringUtils.isNotBlank(clazzChapterName)) {
            data.get().setClazzChapterName(clazzChapterName);
        }

        return manager.save(data.get());
    }

    public void delete(long id) {
        manager.findById(id).ifPresent(manager::delete);
    }

    public ClazzChapterData getById(long id) {
        return manager.findById(id).orElse(null);
    }

    public List<ClazzChapterData> listAll(long clazzCourseId, int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent()
                .stream().filter(o -> o.getClazzCourseId() == clazzCourseId)
                .collect(Collectors.toList());
    }
}
