package com.motian.crp.dao.manager;

import com.motian.crp.dao.data.ItemBankData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface ItemBankManager extends JpaRepository<ItemBankData, Long> {
}
