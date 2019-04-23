package com.motian.crp.dao.data;

import com.motian.crp.dao.data.BaseData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "crp_resource_data")
@EqualsAndHashCode(callSuper = true)
public class ResourceData extends BaseData {

    private long resourceId;

    private String teacherId;

    private String studentId;

    private long clazzCourseId;

    private long learningTime;

}