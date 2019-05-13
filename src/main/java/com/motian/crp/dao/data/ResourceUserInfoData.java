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
@Table(name = "crp_resource_user_info_data")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ResourceUserInfoData", description = "资源使用信息")
public class ResourceUserInfoData extends BaseData {
    @ApiModelProperty(value = "创建者id")
    private String creatorId;

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
}
