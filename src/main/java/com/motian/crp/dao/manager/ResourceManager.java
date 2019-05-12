package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.ResourceLibraryData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface ResourceManager extends JpaRepository<ResourceLibraryData, Long> {
    List<ResourceLibraryData> getByCommitter(String committer, Pageable pageable);
}
