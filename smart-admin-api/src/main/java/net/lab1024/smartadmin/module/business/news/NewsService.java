package net.lab1024.smartadmin.module.business.news;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.common.constant.JudgeEnum;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsAddDTO;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsQueryDTO;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsResultDTO;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsUpdateDTO;
import net.lab1024.smartadmin.module.business.news.domain.entity.NewsEntity;
import net.lab1024.smartadmin.module.system.position.PositionDao;
import net.lab1024.smartadmin.module.system.position.PositionRelationTypeEnum;
import net.lab1024.smartadmin.module.system.position.domain.dto.PositionRelationQueryDTO;
import net.lab1024.smartadmin.module.system.position.domain.dto.PositionRelationResultDTO;
import net.lab1024.smartadmin.module.system.role.roleemployee.RoleEmployeeDao;
import net.lab1024.smartadmin.util.SmartBeanUtil;
import net.lab1024.smartadmin.util.SmartPageUtil;
import net.lab1024.smartadmin.util.SmartRequestTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private RoleEmployeeDao roleEmployeeDao;

    private NewsQueryDTO isClubAdmin(NewsQueryDTO queryDTO){
        //验证是否为某社团管理员，并把管理社团id合集写入list
        PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
        positionRelationQueryDTO.setEmployeeId(SmartRequestTokenUtil.getRequestUserId());
        positionRelationQueryDTO.setStatus(PositionRelationTypeEnum.ADMIN.getValue());
        List<PositionRelationResultDTO> list = positionDao.selectRelation(positionRelationQueryDTO);
        if(list != null) {
            List<Long> positionIdList = new ArrayList<Long>();
            for (PositionRelationResultDTO resultDTO : list) {
                positionIdList.add(resultDTO.getPositionId());
            }
            queryDTO.setPositionIdList(positionIdList);
        }
        return queryDTO;
    }

    /**
     * @description 分页查询
     */
    public ResponseDTO<PageResultDTO<NewsResultDTO>> queryByPage(NewsQueryDTO queryDTO) {
        if(queryDTO.getIsShow() == JudgeEnum.NO.getValue()) {
            queryDTO = this.isClubAdmin(queryDTO);
        }
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
        entity.setEmployeeId(SmartRequestTokenUtil.getRequestUserId());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        if(entity.getStatus() == null){
            entity.setStatus(JudgeEnum.NO.getValue());
        }
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
