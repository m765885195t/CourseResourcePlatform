package com.motian.crp.service;

import com.google.common.collect.Sets;
import com.motian.crp.dao.data.ClazzChapterResourcesData;
import com.motian.crp.dao.data.ResourceLibraryData;
import com.motian.crp.dao.manager.ClazzChapterManager;
import com.motian.crp.dao.manager.ClazzChapterResourcesManager;
import com.motian.crp.dao.manager.ResourceManager;
import com.motian.crp.utils.CrpServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@Service
public class ClazzChapterResourcesService {
    @Autowired
    private ClazzChapterResourcesManager manager;

    @Autowired
    private ClazzChapterManager clazzChapterManager;
    @Autowired
    private ResourceManager resourceManager;

    @Transactional
    public boolean insert(long clazzChapterId, long resourceLibraryId) {
        if (manager.getByClazzChapterIdAndResourceLibraryId(clazzChapterId, resourceLibraryId).isPresent()) {
            return false;
        }
        ResourceLibraryData resourceLibraryData = resourceManager.getOne(resourceLibraryId);

        log.info("resourceLibraryData.getResource={}", resourceLibraryData.getResourceType());

        Optional<ClazzChapterResourcesData> clazzChapterResourcesData =
                getByClazzChapterIdAndVideoIs(clazzChapterId, CrpServiceUtils.isVideo(resourceLibraryData.getResourceType()));
        if (clazzChapterResourcesData.isPresent()) {
            clazzChapterResourcesData.get().setClazzChapterId(clazzChapterId)
                    .setClazzChapterName(clazzChapterManager.getOne(clazzChapterId).getClazzChapterName())
                    .setResourceLibraryId(resourceLibraryId)
                    .setResourceLibraryName(resourceLibraryData.getResourceName())
                    .setResourceType(resourceLibraryData.getResourceType())
                    .setVideoFlag(CrpServiceUtils.isVideo(resourceLibraryData.getResourceType()));
        } else {
            manager.save(new ClazzChapterResourcesData()
                    .setClazzChapterId(clazzChapterId)
                    .setClazzChapterName(clazzChapterManager.getOne(clazzChapterId).getClazzChapterName())
                    .setResourceLibraryId(resourceLibraryId)
                    .setResourceLibraryName(resourceLibraryData.getResourceName())
                    .setResourceType(resourceLibraryData.getResourceType())
                    .setVideoFlag(CrpServiceUtils.isVideo(resourceLibraryData.getResourceType()))
            );
        }
        return true;
    }


    public void delete(long id) {
        manager.findById(id).ifPresent(manager::delete);
    }

    public ClazzChapterResourcesData getById(long id) {
        return manager.findById(id).orElse(null);
    }

    public List<ClazzChapterResourcesData> getByClazzChapterId(long clazzChapterId) {
        return manager.getByClazzChapterId(clazzChapterId);
    }

    public Optional<ClazzChapterResourcesData> getByClazzChapterIdAndVideoIs(long clazzChapterId, boolean isVideo) {
        return manager.getByClazzChapterIdAndVideoFlag(clazzChapterId, isVideo);
    }


    public List<ClazzChapterResourcesData> listAll(String clazzChapterName, String resourceLibraryName, int pageNumber, int pageSize) {
        Set<ClazzChapterResourcesData> dataList = Sets.newHashSet();
        dataList.addAll(listByClazzChapterName(clazzChapterName, pageNumber, pageSize));
        dataList.addAll(listByResourceLibraryName(resourceLibraryName, pageNumber, pageSize));

        return new ArrayList<>(dataList);
    }


    private List<ClazzChapterResourcesData> listByClazzChapterName(
            String clazzChapterName, int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize))
                .stream()
                .filter(o -> StringUtils.isBlank(clazzChapterName) || o.getClazzChapterName().equals(clazzChapterName))
                .collect(Collectors.toList());
    }

    private List<ClazzChapterResourcesData> listByResourceLibraryName(
            String resourceLibraryName, int pageNumber, int pageSize) {

        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize))
                .stream()
                .filter(o -> StringUtils.isBlank(resourceLibraryName) || o.getResourceLibraryName().equals(resourceLibraryName))
                .collect(Collectors.toList());
    }
}
