package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.ResourceUserInfoData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface ResourceUserInfoManager extends JpaRepository<ResourceUserInfoData, Long> {
    ResourceUserInfoData getByCreatorIdAndClazzChapterIdAndClazzCourseIdAndResourceId(
            String creatorId, long clazzChapterId,  long clazzCourseId,long resourceId);

    List<ResourceUserInfoData> getByCreatorId(String creatorId, Pageable pageable);
}
