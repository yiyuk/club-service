package net.lab1024.smartadmin.module.business.fund;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.smartadmin.common.domain.BaseEntity;

/**
 * @author yiyuzi
 * @date 2020/12/24 1:05
 */

@Data
@TableName("t_fund")
public class FundDao extends BaseEntity {
    private Integer approveStatus;
}
