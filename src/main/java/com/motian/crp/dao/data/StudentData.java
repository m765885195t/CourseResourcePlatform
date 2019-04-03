package com.motian.crp.dao.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: gongzhanjing
 * @Email: gongzhanjing@xiyoulinux.org
 */

@Entity
@Table(name = "crp_student")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentData {
    @Id
    private long studentId;

    private String studentName;
}
