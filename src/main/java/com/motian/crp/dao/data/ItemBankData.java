package com.motian.crp.dao.data;

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
@Table(name = "crp_Item_bank")
@EqualsAndHashCode(callSuper = true)
public class ItemBankData extends BaseData {

    private long itemBankId;

    private String teacherId;

    private String studentId;

    private long clazzCourseId;

    private long learningTime;

}