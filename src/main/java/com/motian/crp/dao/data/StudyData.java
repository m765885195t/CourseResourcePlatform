package com.motian.crp.dao.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "crp_study_data")
@EqualsAndHashCode(callSuper = true)
public class StudyData extends BaseData {

    private long studyId;

    private String teacherId;

    private String studentId;

    private long clazzCourseId;

    private long learningTime;
}
