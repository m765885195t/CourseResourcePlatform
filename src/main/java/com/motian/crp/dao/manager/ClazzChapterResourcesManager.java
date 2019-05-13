package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.ClazzChapterResourcesData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface ClazzChapterResourcesManager extends JpaRepository<ClazzChapterResourcesData, Long> {
    Optional<ClazzChapterResourcesData> getByClazzChapterIdAndResourceLibraryId(long clazzChapterId, long resourceLibraryId);

    Optional<ClazzChapterResourcesData> getByClazzChapterIdAndVideoFlag(long clazzChapterId, boolean videoFlag);

    List<ClazzChapterResourcesData> getByResourceLibraryName(String resourceLibraryName, Pageable pageable);

    List<ClazzChapterResourcesData> getByClazzChapterName(String cazzChapterName, Pageable pageable);

    List<ClazzChapterResourcesData> getByClazzChapterId(long clazzChapterId);
}
