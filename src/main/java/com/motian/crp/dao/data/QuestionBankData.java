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

    private long questionBankId;

    private String teacherId;

    private String studentId;

    private long clazzCourseId;

    private long learningTime;

}