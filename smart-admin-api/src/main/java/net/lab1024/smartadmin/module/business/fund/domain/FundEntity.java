package net.lab1024.smartadmin.module.business.fund.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.smartadmin.common.domain.BaseEntity;

/**
 * 经费表
 *
 * @author yiyuzi
 * @date 2020/12/24 1:07
 */

@Data
@TableName("t_fund")
public class FundEntity extends BaseEntity {

    /**
     * 社团id
     */
    private Long positionId;

    /**
     * 申请者id（社团管理员）
     */
    private Long employeeId;

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
     * 审核状态
     */
    private Integer approveStatus;
}
