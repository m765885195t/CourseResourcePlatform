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

    @ApiModelProperty(value = "创建者id")
    private String teacherId;

    @ApiModelProperty(value = "班课号")
    private long clazzCourseId;

    @ApiModelProperty(value = "班课名")
    private String clazzCourseName;

    @ApiModelProperty(value = "容纳人数")
    private int galleryful;

    @ApiModelProperty(value = "提问列表")
    private String question = StringUtils.EMPTY;

    @ApiModelProperty(value = "回答列表")
    private String answer = StringUtils.EMPTY;
}
