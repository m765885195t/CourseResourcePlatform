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
@Table(name = "crp_question_bank")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "QuestionBankData", description = "题库")
public class QuestionBankData extends BaseData {
    @ApiModelProperty(value = "创建者id")
    private String teacherId;

    @ApiModelProperty(value = "题目内容")
    private String content;

    @ApiModelProperty(value = "题目名称")
    private String questionName;
}