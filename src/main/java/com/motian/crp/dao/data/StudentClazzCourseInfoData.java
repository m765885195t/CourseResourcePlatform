package com.motian.crp.dao.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@Table(name = "crp_student_clazz_course_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StudentClazzCourseInfoData", description = "学生班课信息")
public class StudentClazzCourseInfoData extends BaseData {
    @ApiModelProperty(value = "用户id")
    private String studentId;

    @ApiModelProperty(value = "班课id")
    private long clazzCourseId;
}
