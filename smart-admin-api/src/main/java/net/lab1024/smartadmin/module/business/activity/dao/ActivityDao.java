package net.lab1024.smartadmin.module.business.activity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.module.business.activity.domain.dto.ActivityQueryDTO;
import net.lab1024.smartadmin.module.business.activity.domain.dto.ActivityResultDTO;
import net.lab1024.smartadmin.module.business.activity.domain.dto.ActivityUpdateDTO;
import net.lab1024.smartadmin.module.business.activity.domain.entity.ActivityEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yiyuzi
 * @date 2020/12/22 23:04
 */

@Mapper
@Component
public interface ActivityDao extends BaseMapper<ActivityEntity> {
    /**
     * 分页查询活动申请
     *
     * @param page
     * @param queryDTO
     * @return
     */
    List<ActivityResultDTO> selectByPage(Page page, @Param("queryDTO") ActivityQueryDTO queryDTO);

    /**
     * 更新活动
     *
     * @param updateDTO
     * @return
     */
    Integer updateActivity(@Param("updateDTO")ActivityUpdateDTO updateDTO);

    /**
     * 活动人数增加
     *
     * @param id
     * @return
     */
    Integer updateActivityNumber(@Param("id")Long id);
}
