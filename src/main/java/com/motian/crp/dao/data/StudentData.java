package com.motian.crp.dao.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */

@Entity
@Table(name = "crp_student")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long studentId;

    private String studentName;
}
