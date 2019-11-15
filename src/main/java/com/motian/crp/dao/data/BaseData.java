package com.motian.crp.dao.data;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    private Timestamp addTime;

    @LastModifiedDate
    private Timestamp updateTime;
}
