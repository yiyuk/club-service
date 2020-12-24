package net.lab1024.smartadmin.module.business.fund.domain;

import net.lab1024.smartadmin.common.domain.BaseEntity;

/**
 * @author yiyuzi
 * @date 2020/12/24 1:07
 */

public class FundEntity extends BaseEntity {
    private long positionId;
    private long employeeId;
    private double fund;
    private String remark;
    private long arroveId;
    private Integer approveStatus;
}
