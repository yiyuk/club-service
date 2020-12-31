package net.lab1024.smartadmin.module.business.fund;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.imageio.plugins.bmp.BMPConstants;
import lombok.Data;
import net.lab1024.smartadmin.common.domain.BaseEntity;
import net.lab1024.smartadmin.module.business.activity.domain.entity.ActivityApproveEntity;
import net.lab1024.smartadmin.module.business.fund.domain.FundEntity;
import net.lab1024.smartadmin.module.business.fund.domain.dto.FundQueryDTO;
import net.lab1024.smartadmin.module.business.fund.domain.dto.FundResultDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yiyuzi
 * @date 2020/12/24 1:05
 */

@Mapper
@Component
public interface FundDao extends BaseMapper<FundEntity> {
    /**
     * 分页查询资金申请
     *
     * @param page
     * @param queryDTO
     * @return
     */
    List<FundResultDTO> selectByPage(Page page, @Param("queryDTO") FundQueryDTO queryDTO);

    /**
     * 更新状态
     *
     * @param entity
     * @return
     */
    Integer UpdateApprove(@Param("entity") FundEntity entity);
}
