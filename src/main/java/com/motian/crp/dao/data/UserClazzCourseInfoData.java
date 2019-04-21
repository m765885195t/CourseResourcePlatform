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
@Table(name = "crp_clazz_course")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserClazzCourseInfoData", description = "用户班课信息")
public class UserClazzCourseInfoData extends BaseData {
    @ApiModelProperty(value = "用户id")
    private String accountId;

    @ApiModelProperty(value = "班课号")
    private long clazzCourseId;
}
