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
@Table(name = "crp_study_data")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StudyData", description = "学生学习信息")
public class StudyData extends BaseData {
    @ApiModelProperty(value = "学生id")
    private String studentId;

    @ApiModelProperty(value = "老师id")
    private String teacherId;

    @ApiModelProperty(value = "课程id")
    private long clazzCourseId;

    @ApiModelProperty(value = "章节id")
    private long clazzChapterId;

    @ApiModelProperty(value = "资源id")
    private long resourceId;

    @ApiModelProperty(value = "下载次数")
    private long downloads;

    @ApiModelProperty(value = "观看次数")
    private long watchNumber;

    @ApiModelProperty(value = "总排名")
    private long ranking;
}
