package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.ResourceLibraryData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface ResourceManager extends JpaRepository<ResourceLibraryData, Long> {
}
