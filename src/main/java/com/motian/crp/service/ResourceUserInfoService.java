package com.motian.crp.service;

import com.motian.crp.dao.data.ResourceUserInfoData;
import com.motian.crp.dao.manager.ResourceUserInfoManager;
import com.motian.crp.utils.CrpServiceUtils;
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
public class ResourceUserInfoService {
    @Autowired
    private ResourceUserInfoManager manager;
    @Autowired
    private ResourceService service;

    @Transactional
    public void update(String creatorId, long clazzCourseId,
                       long clazzChapterId, long resourceId) {
        ResourceUserInfoData data = manager
                .getByCreatorIdAndClazzChapterIdAndClazzCourseIdAndResourceId(
                        creatorId,  clazzChapterId, clazzCourseId,resourceId);
        if (data == null) {
            data = manager.save(new ResourceUserInfoData()
                    .setClazzChapterId(clazzChapterId)
                    .setClazzCourseId(clazzCourseId)
                    .setCreatorId(creatorId)
                    .setResourceId(resourceId)
            );
        }
        if (CrpServiceUtils.isVideo(service.get(resourceId).getResourceType())) {
            data.setWatchNumber(data.getWatchNumber() + 1);
        } else {
            data.setDownloads(data.getDownloads() + 1);
        }

        manager.save(data);
    }


    public ResourceUserInfoData get(long id) {
        return manager.getOne(id);
    }

    public List<ResourceUserInfoData> listByTeacherId(String teacherId, long clazzCourseId, int pageNumber, int pageSize) {
        return manager.getByCreatorId(teacherId, PageRequest.of(pageNumber - 1, pageSize))
                .stream()
                .filter(o -> clazzCourseId < 0 || o.getClazzCourseId() == clazzCourseId)
                .collect(Collectors.toList());
    }
}
