package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.ClazzChapterResourcesData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface ClazzChapterResourcesManager extends JpaRepository<ClazzChapterResourcesData, Long> {
    Optional<ClazzChapterResourcesData> getByClazzChapterIdAndResourceLibraryId(long clazzChapterId, long resourceLibraryId);
}
