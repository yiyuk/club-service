package net.lab1024.smartadmin.module.business.fund.domain.dto;

import lombok.Data;

/**
 * @author yiyuzi
 * @date 2020/12/24 1:09
 */

@Data
public class FundResultDTO {

    private Long id;

    /**
     * 社团id
     */
    private Long positionId;

    /**
     * 社团名
     */
    private String positionName;

    /**
     * 申请者id（社团管理员）
     */
    private Long employeeId;

    /**
     * 申请者姓名
     */
    private String employeeName;

    /**
     * 申请金额
     */
    private Double fund;

    /**
     * 申请理由
     */
    private String remark;

    /**
     * 审核者id（校方管理员）
     */
    private Long approveId;

    /**
     * 审核者姓名
     */
    private String approveName;

    /**
     * 审核状态
     */
    private Integer approveStatus;
}
