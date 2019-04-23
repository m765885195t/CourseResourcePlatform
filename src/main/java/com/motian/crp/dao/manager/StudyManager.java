package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.StudyData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface StudyManager extends JpaRepository<StudyData, Long> {
}
