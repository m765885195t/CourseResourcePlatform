package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface UserManager extends JpaRepository<UserData, Long> {
    Optional<UserData> getByAccountId(String accountId);
}
