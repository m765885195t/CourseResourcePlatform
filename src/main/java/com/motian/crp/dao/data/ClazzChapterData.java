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
@Table(name = "crp_clazz_chapter")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ClazzChapterData", description = "班课章节信息")
public class ClazzChapterData extends BaseData {
    @ApiModelProperty(value = "班课Id")
    private long clazzCourseId;

    @ApiModelProperty(value = "所在班课的下标")
    private int sequence;

    @ApiModelProperty(value = "章节名")
    private String clazzChapterName;
}
