package net.lab1024.smartadmin.module.business.activity.dao;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.smartadmin.module.business.activity.domain.dto.ActivityApproveQueryDTO;
import net.lab1024.smartadmin.module.business.activity.domain.dto.ActivityApproveResultDTO;
import net.lab1024.smartadmin.module.business.activity.domain.dto.ActivityApproveUpdateDTO;
import net.lab1024.smartadmin.module.business.activity.domain.entity.ActivityApproveEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yiyuzi
 * @date 2020/12/22 23:14
 */

@Mapper
@Component
public interface ActivityApproveDao extends BaseMapper<ActivityApproveEntity> {
    /**
     * 分页查询活动申请
     *
     * @param page
     * @param queryDTO
     * @return
     */
    List<ActivityApproveResultDTO> selectByPage(Page page, @Param("queryDTO") ActivityApproveQueryDTO queryDTO);

    /**
     * 更新活动状态
     *
     * @param entity
     * @return
     */
    Integer UpdateApprove(@Param("entity") ActivityApproveEntity entity);
}
