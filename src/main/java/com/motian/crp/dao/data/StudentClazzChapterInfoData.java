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
@Table(name = "crp_student_clazz_chapter_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StudentClazzChapterInfoData", description = "学生章节信息")
public class StudentClazzChapterInfoData extends BaseData {
    @ApiModelProperty(value = "用户id")
    private String studentId;

    @ApiModelProperty(value = "班课id")
    private long clazzCourseId;

    @ApiModelProperty(value = "班课名")
    private String clazzCourseName;

    @ApiModelProperty(value = "章节id")
    private long clazzChapterId;

    @ApiModelProperty(value = "章节名")
    private String clazzChapterName;

    @ApiModelProperty(value = "问题")
    private String content;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "成绩")
    private int results = -1;
}
