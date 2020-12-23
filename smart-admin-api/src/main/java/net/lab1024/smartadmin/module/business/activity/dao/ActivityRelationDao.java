package net.lab1024.smartadmin.module.business.activity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.smartadmin.module.business.activity.domain.entity.ActivityRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author yiyuzi
 * @date 2020/12/23 9:37
 */

@Mapper
@Component
public interface ActivityRelationDao extends BaseMapper<ActivityRelationEntity> {
}
