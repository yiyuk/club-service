package net.lab1024.smartadmin.module.business.activity.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.module.business.activity.constant.ActivityResponseCodeConst;
import net.lab1024.smartadmin.module.business.activity.dao.ActivityApproveDao;
import net.lab1024.smartadmin.module.business.activity.dao.ActivityDao;
import net.lab1024.smartadmin.module.business.activity.dao.ActivityRelationDao;
import net.lab1024.smartadmin.module.business.activity.domain.dto.*;
import net.lab1024.smartadmin.module.business.activity.domain.entity.ActivityEntity;
import net.lab1024.smartadmin.module.system.employee.EmployeeDao;
import net.lab1024.smartadmin.module.system.position.PositionDao;
import net.lab1024.smartadmin.util.SmartBeanUtil;
import net.lab1024.smartadmin.util.SmartPageUtil;
import net.lab1024.smartadmin.util.SmartRequestTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动
 *
 * @author yiyuzi
 * @date 2020/12/22 23:09
 */

@Service
public class ActivityService {
    @Autowired
    private ActivityApproveDao activityApproveDao;

    @Autowired
    private ActivityRelationDao activityRelationDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private EmployeeDao employeeDao;

    private boolean checkTime(Date startTime, Date stopTime) {
        //开始时间不能大于结束时间，
        if (startTime == null || stopTime == null) {
            return true;
        }
        Date nowTime = new Date();
        if (startTime.compareTo(stopTime) >= 0 || startTime.compareTo(nowTime) <= 0 || stopTime.compareTo(nowTime) <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 根据id查询活动信息
     *
     * @param id
     * @return
     */
    public ResponseDTO<ActivityResultDTO> queryActivityById(long id) {
        ActivityEntity activityEntity = activityDao.selectById(id);
        ActivityResultDTO resultDTO = SmartBeanUtil.copy(activityEntity, ActivityResultDTO.class);
        resultDTO.setPositionName(positionDao.selectPositionByID(activityEntity.getPositionId()).getPositionName());
        resultDTO.setEmployeeName(employeeDao.selectById(activityEntity.getEmployeeId()).getActualName());
        return ResponseDTO.succData(resultDTO);
    }

    /**
     * 分页查询活动信息
     *
     * @param queryDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<PageResultDTO<ActivityResultDTO>> queryActivityByPage(ActivityQueryDTO queryDTO) {
        Page page = SmartPageUtil.convert2QueryPage(queryDTO);
        List<ActivityResultDTO> resultDTOList = activityDao.selectByPage(page, queryDTO);
        page.setRecords(resultDTOList.stream().map(e -> SmartBeanUtil.copy(e, ActivityResultDTO.class)).collect(Collectors.toList()));
        //PageResultDTO<ActivityApproveResultDTO> pageResultDTO = SmartPageUtil.convert2PageResult(page);
        return ResponseDTO.succData(SmartPageUtil.convert2PageResult(page));
    }

    /**
     * 更新活动
     *
     * @param updateDTO
     * @return
     */
    public ResponseDTO<String> updateActivity(ActivityUpdateDTO updateDTO) {
        //人数上限不能小于0或小于当前活动人数
        if (updateDTO.getActivityMaximum() != null && (updateDTO.getActivityMaximum() <= 0
                || updateDTO.getActivityMaximum() <= activityDao.selectById(updateDTO.getId()).getActivityNumber())) {
            return ResponseDTO.wrap(ActivityResponseCodeConst.MAX_NUMBER_ERROR);
        }
        //活动开始时间不能晚于结束时间和当前时间，
        if (!checkTime(updateDTO.getStartTime(), updateDTO.getStopTime())) {
            return ResponseDTO.wrap(ActivityResponseCodeConst.TIME_ERROR);
        }

        activityDao.updateActivity(updateDTO);
        return ResponseDTO.succ();
    }

    /**
     * 删除活动
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> deleteActivity(Long id) {
        //删除关联表里的相关记录
        ActivityRelationQueryDTO activityRelationQueryDTO = new ActivityRelationQueryDTO();
        activityRelationQueryDTO.setActivityId(activityDao.selectById(id).getId());
        List<ActivityRelationResultDTO> list = activityRelationDao.selectActivityRelation(activityRelationQueryDTO);
        for (ActivityRelationResultDTO item:list) {
            activityRelationDao.deleteById(item.getId());
        }

        activityDao.deleteById(id);

        return ResponseDTO.succ();
    }
}
