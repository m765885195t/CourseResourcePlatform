package com.motian.crp.service;

import com.motian.crp.dao.data.ClazzChapterResourcesData;
import com.motian.crp.dao.manager.ClazzChapterResourcesManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@Service
public class ClazzChapterResourcesService {
    private final ClazzChapterResourcesManager manager;

    @Autowired
    public ClazzChapterResourcesService(ClazzChapterResourcesManager manager) {
        this.manager = manager;
    }

    @Transactional
    public void insert(long clazzChapterId, long resourceLibraryId) {
        manager.getByClazzChapterIdAndResourceLibraryId(clazzChapterId, resourceLibraryId)
                .orElseGet(() ->
                        manager.save(new ClazzChapterResourcesData()
                                .setClazzChapterId(clazzChapterId)
                                .setResourceLibraryId(resourceLibraryId))
                );
    }


    public void delete(long id) {
        manager.findById(id).ifPresent(manager::delete);
    }

    public ClazzChapterResourcesData getById(long id) {
        return manager.findById(id).orElse(null);
    }

    public List<ClazzChapterResourcesData> listAll(long clazzChapterId, int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent()
                .stream().filter(o -> o.getClazzChapterId() == clazzChapterId)
                .collect(Collectors.toList());
    }
}
