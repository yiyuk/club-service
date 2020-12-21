package net.lab1024.smartadmin.module.system.position.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import net.lab1024.smartadmin.common.domain.BaseEntity;
import lombok.Data;

/**
 * 社团（岗位
 *
 * @author zzr
 */
@Data
@TableName("t_position")
public class PositionEntity extends BaseEntity {

    /**
     * 社团名称
     */
    private String positionName;

    /**
     * 社团描述
     */
    private String remark;

//    /**
//     * 社团经费
//     */
//    private Double funds;
}
