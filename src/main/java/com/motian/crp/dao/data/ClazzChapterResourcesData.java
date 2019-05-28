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

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "crp_clazz_chapter_resources")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ClazzChapterResourcesData", description = "班课章节资源挂载关系")
public class ClazzChapterResourcesData extends BaseData {
    @ApiModelProperty(value = "章节id")
    private long clazzChapterId;

    @ApiModelProperty(value = "章节名")
    private String clazzChapterName;

    @ApiModelProperty(value = "资源id")
    private long resourceLibraryId;

    @ApiModelProperty(value = "资源名")
    private String resourceLibraryName;

    @ApiModelProperty(value = "资源类型")
    private String resourceType;

    @ApiModelProperty(value = "true视频资源/false非视频")
    private boolean videoFlag;

}