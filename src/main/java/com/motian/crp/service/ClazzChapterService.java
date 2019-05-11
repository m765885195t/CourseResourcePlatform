package com.motian.crp.service;

import com.google.common.collect.Lists;
import com.motian.crp.dao.data.ClazzChapterData;
import com.motian.crp.dao.manager.ClazzChapterManager;
import com.motian.crp.dao.manager.ClazzCourseManager;
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
    private final ClazzCourseManager clazzCourseManager;
    private final ClazzChapterManager clazzChapterManager;

    @Autowired
    public ClazzChapterService(ClazzCourseManager clazzCourseManager,
                               ClazzChapterManager clazzChapterManager) {
        this.clazzCourseManager = clazzCourseManager;
        this.clazzChapterManager = clazzChapterManager;
    }

    @Transactional
    public void insert(long clazzCourseId, int sequence, String clazzChapterName) {
        clazzChapterManager.getByClazzCourseIdAndSequenceAndClazzChapterName(clazzCourseId, sequence, clazzChapterName)
                .orElseGet(() ->
                        clazzChapterManager.save(new ClazzChapterData()
                                .setClazzCourseId(clazzCourseId)
                                .setSequence(sequence)
                                .setClazzChapterName(clazzChapterName)
                        ));
    }


    public ClazzChapterData update(long id, String clazzChapterName) throws Exception {
        Optional<ClazzChapterData> data = clazzChapterManager.findById(id);
        if (!data.isPresent()) {
            throw new Exception("The ClazzChapterData does not exist. id=" + id);
        }
        if (StringUtils.isNotBlank(clazzChapterName)) {
            data.get().setClazzChapterName(clazzChapterName);
        }

        return clazzChapterManager.save(data.get());
    }

    public void delete(long id) {
        clazzChapterManager.findById(id).ifPresent(clazzChapterManager::delete);
    }

    public ClazzChapterData getById(long id) {
        return clazzChapterManager.findById(id).orElse(null);
    }


    public List<ClazzChapterData> listAll(String teacherId, String clazzCourseId,
                                          String clazzCourseName, int pageNumber, int pageSize) {

        List<ClazzChapterData> dataList = Lists.newArrayList();
        dataList.addAll(listByClazzCourseId(teacherId, clazzCourseId, pageNumber, pageSize));
        dataList.addAll(listByClazzCourseName(teacherId, clazzCourseName, pageNumber, pageSize));
        return dataList;
    }

    private List<ClazzChapterData> listByClazzCourseId(
            String teacherId, String clazzCourseId, int pageNumber, int pageSize) {
        log.info("listAll::clazzCourseId={}", clazzCourseId);
        int ccId = -1;
        try {
            ccId = Integer.parseInt(clazzCourseId);
        } catch (NumberFormatException ignore) {
        }

        List<ClazzChapterData> dataList = Lists.newArrayList();
        int finalCcId = ccId;
        clazzCourseManager.getByTeacherId(teacherId)
                .stream()
                .filter(o -> o.getClazzCourseId() == finalCcId)
                .forEach(cc -> {
                    dataList.addAll(clazzChapterManager
                            .findAll(PageRequest.of(pageNumber - 1, pageSize))
                            .getContent()
                            .stream()
                            .filter(o -> o.getClazzCourseId() == cc.getClazzCourseId())
                            .collect(Collectors.toList())
                    );
                });
        return dataList;
    }

    private List<ClazzChapterData> listByClazzCourseName(
            String teacherId, String clazzCourseName, int pageNumber, int pageSize) {

        List<ClazzChapterData> dataList = Lists.newArrayList();
        clazzCourseManager.getByTeacherId(teacherId)
                .stream()
                .filter(o -> StringUtils.isBlank(clazzCourseName) || o.getClazzCourseName().equals(clazzCourseName))
                .forEach(cc -> {
                    dataList.addAll(clazzChapterManager
                            .findAll(PageRequest.of(pageNumber - 1, pageSize))
                            .getContent()
                            .stream()
                            .filter(o -> o.getClazzCourseId() == cc.getClazzCourseId())
                            .collect(Collectors.toList())
                    );
                });
        return dataList;
    }
}
