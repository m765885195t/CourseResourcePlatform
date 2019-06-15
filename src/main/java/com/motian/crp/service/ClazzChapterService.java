package com.motian.crp.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.motian.crp.dao.data.ClazzChapterData;
import com.motian.crp.dao.data.ClazzCourseData;
import com.motian.crp.dao.manager.ClazzChapterManager;
import com.motian.crp.dao.manager.ClazzCourseManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
    public boolean insert(long clazzCourseId, int order, String clazzChapterName) {
        log.info("insert::clazzCourseId={},order={},clazzChapterName={}",
                clazzCourseId, order, clazzChapterName);
        Optional<ClazzCourseData> clazzCourseData = clazzCourseManager
                .getByClazzCourseId(clazzCourseId);
        if (!clazzCourseData.isPresent()) {
            return false;
        }
        if (clazzChapterManager.getByClazzCourseIdAndSequence(
                clazzCourseData.get().getClazzCourseId(), order).isPresent()) {
            return false;
        }
        clazzChapterManager.save(new ClazzChapterData()
                .setClazzCourseId(clazzCourseData.get().getClazzCourseId())
                .setSequence(order)
                .setClazzCourseName(clazzCourseData.get().getClazzCourseName())
                .setClazzChapterName(clazzChapterName)
        );
        return true;
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

    public ClazzChapterData updateQuestionId(long id, long questionId) throws Exception {
        Optional<ClazzChapterData> data = clazzChapterManager.findById(id);
        if (!data.isPresent()) {
            throw new Exception("The ClazzChapterData does not exist. id=" + id);
        }
        data.get().setQuestionId(questionId);
        return clazzChapterManager.save(data.get());
    }

    public void delete(long id) {
        clazzChapterManager.findById(id).ifPresent(clazzChapterManager::delete);
    }

    public void deleteByClazzCourseId(long clazzCourseId) {
        clazzChapterManager.deleteByClazzCourseId(clazzCourseId);
    }

    public ClazzChapterData getById(long id) {
        return clazzChapterManager.findById(id).orElse(null);
    }

    public List<ClazzChapterData> listAll(String teacherId, String clazzCourseId,
                                          String clazzCourseName, int pageNumber, int pageSize) {

        Set<ClazzChapterData> dataList = Sets.newHashSet();
        dataList.addAll(listByClazzCourseId(teacherId, clazzCourseId, pageNumber, pageSize));
        dataList.addAll(listByClazzCourseName(teacherId, clazzCourseName, pageNumber, pageSize));
        return new ArrayList<>(dataList);
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

        public Map<Long, String> selectClazzCourseChapter(String userId, long clazzCourseId) {
        Map<Long, String> map = Maps.newHashMap();
        if (clazzCourseId == 0) {
            clazzCourseManager.getByTeacherId(userId)
                    .forEach(c -> clazzChapterManager
                            .findAll()
                            .stream()
                            .filter(o -> o.getClazzCourseId() == c.getClazzCourseId())
                            .forEach(o -> map.put(o.getId(), o.getClazzChapterName()))
                    );
        } else {
            clazzChapterManager.getByClazzCourseId(clazzCourseId).forEach(o -> {
                map.put(o.getId(), o.getClazzChapterName());
            });
        }

        return map;
    }
}
