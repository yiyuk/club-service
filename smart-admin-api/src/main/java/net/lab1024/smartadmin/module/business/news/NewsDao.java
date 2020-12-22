package net.lab1024.smartadmin.module.business.news;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsQueryDTO;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsResultDTO;
import net.lab1024.smartadmin.module.business.news.domain.entity.NewsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yiyuzi
 * @date 2020/12/22 18:22
 */

@Mapper
@Component
public interface NewsDao extends BaseMapper<NewsEntity> {
    /**
     * 分页查询
     * @param queryDTO
     * @return EmailEntity
     */
    List<NewsResultDTO> queryByPage(Page page, @Param("queryDTO") NewsQueryDTO queryDTO);

    NewsResultDTO queryNewsById(@Param("id")Long id);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    void deleteById(@Param("id") Long id);

    /**
     * 批量删除
     * @param idList
     * @return
     */
    void deleteByIds(@Param("idList") List<Long> idList);
}
