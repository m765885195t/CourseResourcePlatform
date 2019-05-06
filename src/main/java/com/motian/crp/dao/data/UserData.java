package com.motian.crp.dao.data;

import com.motian.crp.constant.CrpConst;
import com.motian.crp.constant.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "crp_user")
@ApiModel(value = "UserData", description = "用户信息")
public class UserData extends BaseData {
    @ApiModelProperty(value = "账户id")
    private String accountId;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "用户类型")
    @Enumerated(value = EnumType.STRING)
    private DataType.UserType userType;

    @ApiModelProperty(value = "用户昵称")
    private String nickname = CrpConst.DefaultValue.NICKNAME;

    @ApiModelProperty(value = "性别")
    @Enumerated(value = EnumType.STRING)
    private DataType.GenderType gender = DataType.GenderType.UNKNOWN;

    @ApiModelProperty(value = "生日")
    private String birthday = StringUtils.EMPTY;

    @ApiModelProperty(value = "手机号")
    private String cellphoneNumber = StringUtils.EMPTY;
}
