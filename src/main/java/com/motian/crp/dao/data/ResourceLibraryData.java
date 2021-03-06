package com.motian.crp.dao.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@Table(name = "crp_resource_library_data")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ResourceLibraryData", description = "资源库信息")
public class ResourceLibraryData extends BaseData {
    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "资源真实名称")
    private String resourceRealName;

    @ApiModelProperty(value = "资源类型")
    private String resourceType;

    @ApiModelProperty(value = "提交用户")
    private String committer;

    @ApiModelProperty(value = "资源存储的uri")
    private String resourceUri;
}