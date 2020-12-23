package net.lab1024.smartadmin.module.business.news;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsAddDTO;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsQueryDTO;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsResultDTO;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsUpdateDTO;
import net.lab1024.smartadmin.module.business.news.domain.entity.NewsEntity;
import net.lab1024.smartadmin.util.SmartBeanUtil;
import net.lab1024.smartadmin.util.SmartPageUtil;
import net.lab1024.smartadmin.util.SmartRequestTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yiyuzi
 * @date 2020/12/22 18:23
 */

@Service
public class NewsService {
    @Autowired
    private NewsDao newsDao;
    /**
     * @description 分页查询
     */
    public ResponseDTO<PageResultDTO<NewsResultDTO>> queryByPage(NewsQueryDTO queryDTO) {
        Page page = SmartPageUtil.convert2QueryPage(queryDTO);
        List<NewsResultDTO> dtoList = SmartBeanUtil.copyList(newsDao.queryByPage(page, queryDTO), NewsResultDTO.class);
        page.setRecords(dtoList);
        PageResultDTO<NewsResultDTO> pageResultDTO = SmartPageUtil.convert2PageResult(page);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * @description 根据ID查询
     */
    public ResponseDTO<NewsResultDTO> queryById(Long id) {
        return ResponseDTO.succData(newsDao.queryNewsById(id));
    }

    /**
     * 添加
     */
    public ResponseDTO<Long> add(NewsAddDTO addDTO) {
        NewsEntity entity = SmartBeanUtil.copy(addDTO, NewsEntity.class);
        entity.setEmployeeId(SmartRequestTokenUtil.getRequestUser().getRequestUserId());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        newsDao.insert(entity);
        return ResponseDTO.succData(entity.getId());
    }

    /**
     * 编辑
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<Long> update(NewsUpdateDTO updateDTO) {
        NewsEntity entity = SmartBeanUtil.copy(updateDTO, NewsEntity.class);
        entity.setUpdateTime(new Date());
        newsDao.updateById(entity);
        return ResponseDTO.succData(entity.getId());
    }

    /**
     * @description 删除
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long id) {
        newsDao.deleteById(id);
        return ResponseDTO.succ();
    }

}
