package net.lab1024.smartadmin.module.business.activity.Service;

import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;
import net.lab1024.smartadmin.common.constant.JudgeEnum;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.module.business.activity.constant.ActivityResponseCodeConst;
import net.lab1024.smartadmin.module.business.activity.dao.ActivityApproveDao;
import net.lab1024.smartadmin.module.business.activity.dao.ActivityDao;
import net.lab1024.smartadmin.module.business.activity.domain.dto.*;
import net.lab1024.smartadmin.module.business.activity.domain.entity.ActivityApproveEntity;
import net.lab1024.smartadmin.module.business.activity.domain.entity.ActivityEntity;
import net.lab1024.smartadmin.module.business.message.MessageService;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageAddDTO;
import net.lab1024.smartadmin.module.system.employee.EmployeeDao;
import net.lab1024.smartadmin.module.system.position.PositionDao;
import net.lab1024.smartadmin.util.SmartBeanUtil;
import net.lab1024.smartadmin.util.SmartRequestTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.util.SmartPageUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 创建活动申请
 *
 * @author yiyuzi
 * @date 2020/12/22 23:12
 */

@Service
public class ActivityApproveService {
    @Autowired
    private ActivityApproveDao activityApproveDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private MessageService messageService;

    private boolean checkTime(Date startTime, Date stopTime) {
        //开始时间不能大于结束时间，
        if (startTime == null || stopTime == null) {
            return true;
        }
        Date nowTime = new Date();
        if (startTime.compareTo(stopTime) >= 0 || startTime.compareTo(nowTime) <= 0 || stopTime.compareTo(nowTime) <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据id查询活动申请信息
     *
     * @param id
     * @return
     */
    public ResponseDTO<ActivityApproveResultDTO> queryActivityApproveById(Long id) {
        ActivityApproveEntity activityApproveEntity = activityApproveDao.selectById(id);
        ActivityApproveResultDTO resultDTO = SmartBeanUtil.copy(activityApproveEntity, ActivityApproveResultDTO.class);
        resultDTO.setPositionName(positionDao.selectPositionByID(activityApproveEntity.getPositionId()).getPositionName());
        resultDTO.setEmployeeName(employeeDao.selectById(activityApproveEntity.getEmployeeId()).getActualName());
        if (activityApproveEntity.getApproveId() != null) {
            resultDTO.setApproveName(employeeDao.selectById(activityApproveEntity.getApproveId()).getActualName());
        }
        return ResponseDTO.succData(resultDTO);
    }

    /**
     * 分页查询活动申请信息
     *
     * @param queryDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<PageResultDTO<ActivityApproveResultDTO>> queryActivityApproveByPage(ActivityApproveQueryDTO queryDTO) {
        Page page = SmartPageUtil.convert2QueryPage(queryDTO);
        List<ActivityApproveResultDTO> resultDTOList = activityApproveDao.selectByPage(page, queryDTO);
        page.setRecords(resultDTOList.stream().map(e -> SmartBeanUtil.copy(e, ActivityApproveResultDTO.class)).collect(Collectors.toList()));
        return ResponseDTO.succData(SmartPageUtil.convert2PageResult(page));
    }

    /**
     * 提交活动申请
     *
     * @param addDTO
     * @return
     */
    public ResponseDTO<String> addActivityApprove(ActivityApproveAddDTO addDTO) {
        //人数上限不能小于0
        if (addDTO.getActivityMaximum() != null && addDTO.getActivityMaximum() <= 0) {
            return ResponseDTO.wrap(ActivityResponseCodeConst.MAX_NUMBER_ERROR);
        }
        //开始时间不能晚于结束时间和当前时间
        if (!checkTime(addDTO.getStartTime(), addDTO.getStopTime())) {
            return ResponseDTO.wrap(ActivityResponseCodeConst.TIME_ERROR);
        }

        ActivityApproveEntity entity = SmartBeanUtil.copy(addDTO, ActivityApproveEntity.class);
        entity.setStatus(ApproveTypeEnum.WAIT.getValue());
        entity.setUpdateTime(new Date());
        entity.setCreateTime(new Date());
        entity.setEmployeeId(SmartRequestTokenUtil.getRequestUser().getRequestUserId());
        activityApproveDao.insert(entity);
        return ResponseDTO.succ();
    }

    /**
     * 审核活动申请
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> approveActivity(ActivityApproveUpdateDTO updateDTO) {
        //重复审核
        if (activityApproveDao.selectById(updateDTO.getId()).getStatus() != ApproveTypeEnum.WAIT.getValue()){
            return ResponseDTO.wrap(ActivityResponseCodeConst.RE_APPROVE_ERROR);
        }

        ActivityApproveEntity entity = SmartBeanUtil.copy(updateDTO, ActivityApproveEntity.class);
        entity.setUpdateTime(new Date());
        entity.setApproveId(SmartRequestTokenUtil.getRequestUser().getRequestUserId());
        activityApproveDao.UpdateApprove(entity);

        ActivityEntity activityEntity = SmartBeanUtil.copy(activityApproveDao.selectById(updateDTO.getId()), ActivityEntity.class);
        if (updateDTO.getStatus() == ApproveTypeEnum.SUCCESS.getValue()) {
            //向活动表里添加活动
            activityEntity.setActivityNumber((long) 0);
            activityDao.insert(activityEntity);

            //发送通知
            MessageAddDTO messageAddDTO = new MessageAddDTO();
            messageAddDTO.setTitle("活动申请成功");
            messageAddDTO.setContent("您的创建" + activityEntity.getActivityName() + "申请已通过。");
            messageAddDTO.setReceiverId(activityEntity.getEmployeeId());
            messageAddDTO.setSendStatus(JudgeEnum.YES.getValue());
            messageService.addMessage(messageAddDTO);
        } else if (updateDTO.getStatus() == ApproveTypeEnum.FAIL.getValue()) {
            activityApproveDao.deleteById(updateDTO.getId());
            //发送通知
            MessageAddDTO messageAddDTO = new MessageAddDTO();
            messageAddDTO.setTitle("活动申请失败");
            messageAddDTO.setContent("您的创建" + activityEntity.getActivityName() + "申请被拒绝。");
            messageAddDTO.setReceiverId(activityEntity.getEmployeeId());
            messageAddDTO.setSendStatus(JudgeEnum.YES.getValue());
            messageService.addMessage(messageAddDTO);
        }
        return ResponseDTO.succ();
    }
}
