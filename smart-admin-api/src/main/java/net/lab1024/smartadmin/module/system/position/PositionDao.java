package net.lab1024.smartadmin.module.system.position;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.module.system.position.domain.dto.*;
import net.lab1024.smartadmin.module.system.position.domain.entity.PositionEntity;
import net.lab1024.smartadmin.module.system.position.domain.entity.PositionRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zzr
 */
@Mapper
@Component
public interface PositionDao extends BaseMapper<PositionEntity> {

    /**
     * 查询社团列表
     *
     * @param page
     * @param queryDTO
     * @return
     */
    List<PositionEntity> selectByPage(Page page, @Param("queryDTO") PositionQueryDTO queryDTO);

    /**
     * 查询社团
     *
     * @param id
     * @return
     */
    PositionEntity selectPositionByID(@Param("id")Long id);

    /**
     * 根据社团ID或用户ID 查询社团与用户关系
     *
     * @param positionRelationQueryDTO
     * @return
     */
    List<PositionRelationResultDTO> selectRelation(PositionRelationQueryDTO positionRelationQueryDTO);

    /**
     * 批量查询用户社团信息
     * @param employeeIdList
     * @return
     */
    List<PositionRelationResultDTO> selectEmployeesRelation(@Param("employeeIdList") List<Long> employeeIdList);

    List<PositionRelationEntity> selectRelByPage(Page page, @Param("queryDTO") PositionRelationQueryDTO queryDTO);

    /**
     * 批量添加社团 用户 关联关系
     *
     * @param positionRelationAddDTO
     * @return
     */
    Integer insertBatchRelation(@Param("batchDTO")PositionRelationAddDTO positionRelationAddDTO);

    /**
     * 添加社团 用户 关联关系
     *
     * @param positionRelationAddDTO
     * @return
     */
    Integer insertRelation(@Param("addDTO")PositionRelationAddDTO positionRelationAddDTO);

    /**
     * 删除关联关系
     *
     * @param positionQueryDTO
     * @return
     */
    Integer deleteRelation(@Param("positionQueryDTO") PositionRelationQueryDTO positionQueryDTO);

    /**
     * 删除指定用户的 关联关系
     *
     * @param employeeId
     * @return
     */
    Integer deleteRelationByEmployeeId(@Param("employeeId") Long employeeId);

    /**
     * 删除指定社团的 关联关系
     *
     * @param positionId
     * @return
     */
    Integer deleteRelationByPositionId(@Param("positionId") Long positionId);

    /**
     * 更新关系状态
     *
     * @param updateDTO
     * @return
     */
    Integer updateRelation(@Param("updateDTO") PositionRelationUpdateDTO updateDTO);
}
