package net.lab1024.smartadmin.module.business.message;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.module.business.message.domain.MessageEntity;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageQueryDTO;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageResultDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yiyuzi
 * @date 2020/12/24 17:36
 */

@Mapper
@Component
public interface MessageDao extends BaseMapper<MessageEntity> {
    /**
     * 分页查询消息
     *
     * @param page
     * @param queryDTO
     * @return
     */
    List<MessageResultDTO> selectByPage(Page page, @Param("queryDTO") MessageQueryDTO queryDTO);

    /**
     * 更新消息
     *
     * @param entity
     * @return
     */
    Integer updateMessage(@Param("entity") MessageEntity entity);
}
