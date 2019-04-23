package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.QuestionBankData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface QuestionBankManager extends JpaRepository<QuestionBankData, Long> {
}
