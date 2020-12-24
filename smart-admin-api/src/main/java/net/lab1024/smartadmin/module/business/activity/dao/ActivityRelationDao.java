package net.lab1024.smartadmin.module.business.activity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.module.business.activity.domain.dto.*;
import net.lab1024.smartadmin.module.business.activity.domain.entity.ActivityRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yiyuzi
 * @date 2020/12/23 9:37
 */

@Mapper
@Component
public interface ActivityRelationDao extends BaseMapper<ActivityRelationEntity> {
    /**
     * 分页查询活动申请
     *
     * @param page
     * @param queryDTO
     * @return
     */
    List<ActivityRelationResultDTO> selectActivityRelationByPage(Page page, @Param("queryDTO") ActivityRelationQueryDTO queryDTO);

    /**
     * 查询活动申请
     *
     * @param queryDTO
     * @return
     */
    List<ActivityRelationResultDTO> selectActivityRelation( @Param("queryDTO") ActivityRelationQueryDTO queryDTO);

    /**
     * 审核参与活动申请
     *
     * @param entity
     * @return
     */
    Integer approveActivityRelation(@Param("entity") ActivityRelationEntity entity);

    /**
     * 更新参与活动状态
     *
     * @param updateDTO
     * @return
     */
    Integer updateActivityRelationJoinStatus(@Param("updateDTO") ActivityRelationUpdateDTO updateDTO);

}
