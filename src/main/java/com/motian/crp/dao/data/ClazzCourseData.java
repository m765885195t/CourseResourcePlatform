package com.motian.crp.dao.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

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
@Table(name = "crp_clazz_course")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ClazzCourseData", description = "班课信息")
public class ClazzCourseData extends BaseData {
    @ApiModelProperty(value = "班课号")
    private long clazzCourseId;

    @ApiModelProperty(value = "班级名")
    private String clazzName;

    @ApiModelProperty(value = "课程名")
    private String courseName;


    @ApiModelProperty(value = "封面图")
    private String cover = StringUtils.EMPTY;
}
