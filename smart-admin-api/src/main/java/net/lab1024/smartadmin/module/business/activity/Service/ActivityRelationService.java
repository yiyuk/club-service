package net.lab1024.smartadmin.module.business.activity.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;
import net.lab1024.smartadmin.common.constant.JudgeEnum;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.module.business.activity.constant.ActivityResponseCodeConst;
import net.lab1024.smartadmin.module.business.activity.dao.ActivityApproveDao;
import net.lab1024.smartadmin.module.business.activity.dao.ActivityDao;
import net.lab1024.smartadmin.module.business.activity.dao.ActivityRelationDao;
import net.lab1024.smartadmin.module.business.activity.domain.dto.*;
import net.lab1024.smartadmin.module.business.activity.domain.entity.ActivityEntity;
import net.lab1024.smartadmin.module.business.activity.domain.entity.ActivityRelationEntity;
import net.lab1024.smartadmin.module.business.message.MessageService;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageAddDTO;
import net.lab1024.smartadmin.module.business.notice.NoticeService;
import net.lab1024.smartadmin.module.business.notice.domain.dto.NoticeAddDTO;
import net.lab1024.smartadmin.module.system.employee.EmployeeDao;
import net.lab1024.smartadmin.module.system.position.PositionDao;
import net.lab1024.smartadmin.module.system.position.domain.entity.PositionEntity;
import net.lab1024.smartadmin.util.SmartBeanUtil;
import net.lab1024.smartadmin.util.SmartPageUtil;
import net.lab1024.smartadmin.util.SmartRequestTokenUtil;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 参与活动
 *
 * @author yiyuzi
 * @date 2020/12/23 10:15
 */

@Service
public class ActivityRelationService {
    @Autowired
    private ActivityRelationDao activityRelationDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private MessageService messageService;


    /**
     * 根据id查询参与活动信息
     *
     * @param id
     * @return
     */
    public ResponseDTO<ActivityRelationResultDTO> queryActivityRelationById(long id) {
        ActivityRelationEntity entity = activityRelationDao.selectById(id);
        ActivityRelationResultDTO resultDTO = SmartBeanUtil.copy(entity, ActivityRelationResultDTO.class);
        resultDTO.setPositionName(positionDao.selectPositionByID(entity.getPositionId()).getPositionName());
        resultDTO.setEmployeeName(employeeDao.selectById(entity.getEmployeeId()).getActualName());
        if (entity.getApproveId() != null) {
            resultDTO.setApproveName(employeeDao.selectById(entity.getApproveId()).getActualName());
        }
        return ResponseDTO.succData(resultDTO);
    }

    /**
     * 分页查询参与活动信息
     *
     * @param queryDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<PageResultDTO<ActivityRelationResultDTO>> queryActivityRelationByPage(ActivityRelationQueryDTO queryDTO) {
        Page page = SmartPageUtil.convert2QueryPage(queryDTO);
        List<ActivityRelationResultDTO> resultDTOList = activityRelationDao.selectActivityRelationByPage(page, queryDTO);
        page.setRecords(resultDTOList.stream().map(e -> SmartBeanUtil.copy(e, ActivityRelationResultDTO.class)).collect(Collectors.toList()));
        //PageResultDTO<ActivityApproveResultDTO> pageResultDTO = SmartPageUtil.convert2PageResult(page);
        return ResponseDTO.succData(SmartPageUtil.convert2PageResult(page));
    }

    /**
     * 报名活动
     *
     * @param addDTO
     * @return
     */
    public ResponseDTO<String> addActivityRelation(ActivityRelationAddDTO addDTO) {
        Long employeeId = SmartRequestTokenUtil.getRequestUser().getRequestUserId();

        //判断活动是否在进行中
        ActivityEntity activityEntity = activityDao.selectById(activityRelationDao.selectById(addDTO.getActivityId()).getPositionId());
        if (activityEntity.getStartTime().compareTo(new Date()) >= 0 || activityEntity.getStopTime().compareTo(new Date()) <= 0) {
            return ResponseDTO.wrap(ActivityResponseCodeConst.NOT_RUN_ERROR);
        }

        //判断同一活动之前是否有未审核或已通过的提交
        ActivityRelationQueryDTO queryDTO = new ActivityRelationQueryDTO();
        queryDTO.setEmployeeId(employeeId);
        queryDTO.setActivityId(addDTO.getActivityId());
        queryDTO.setStatus(ApproveTypeEnum.FAIL.getValue());
        List<ActivityRelationResultDTO> list = activityRelationDao.selectActivityRelation(queryDTO);
        if (list.isEmpty()) {
            return ResponseDTO.wrap(ActivityResponseCodeConst.RESUBMIT_ERROR);
        }

        ActivityRelationEntity entity = SmartBeanUtil.copy(addDTO, ActivityRelationEntity.class);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setEmployeeId(employeeId);
        entity.setStatus(ApproveTypeEnum.WAIT.getValue());
        entity.setPositionId(activityDao.selectById(addDTO.getActivityId()).getPositionId());
        activityRelationDao.insert(entity);
        return ResponseDTO.succ();
    }

    /**
     * 审核活动报名
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> approveActivityRelation(ActivityRelationUpdateDTO updateDTO) {
        ActivityRelationEntity entity = SmartBeanUtil.copy(updateDTO, ActivityRelationEntity.class);
        entity.setApproveId(SmartRequestTokenUtil.getRequestUser().getRequestUserId());

        ActivityRelationEntity activityRelationEntity = activityRelationDao.selectById(updateDTO.getId());
        ActivityEntity activityEntity = activityDao.selectById(activityRelationEntity.getActivityId());
        if (updateDTO.getStatus() == ApproveTypeEnum.SUCCESS.getValue()) {
            //当前活动人数不能超过人数上限
            if (activityEntity.getActivityNumber() + 1 >= activityEntity.getActivityMaximum()) {
                return ResponseDTO.wrap(ActivityResponseCodeConst.NUMBER_LIMIT_ERROR);
            }
            //申请通过，活动表人数+1
            activityDao.updateActivityNumber(activityRelationEntity.getActivityId());
            entity.setJoinStatus(JudgeEnum.NO.getValue());
            //发送通知
            MessageAddDTO messageAddDTO = new MessageAddDTO();
            messageAddDTO.setTitle("活动报名成功");
            messageAddDTO.setContent(activityEntity.getActivityName() + " 活动报名已通过。");
            messageAddDTO.setReceiverId(entity.getEmployeeId());
            messageAddDTO.setSendStatus(JudgeEnum.YES.getValue());
            messageService.addMessage(messageAddDTO);
        }else if (updateDTO.getStatus() == ApproveTypeEnum.FAIL.getValue()){
            //发送通知
            MessageAddDTO messageAddDTO = new MessageAddDTO();
            messageAddDTO.setTitle("活动报名失败");
            messageAddDTO.setContent("您的 " + activityEntity.getActivityName() + " 活动报名被拒绝。");
            messageAddDTO.setReceiverId(entity.getEmployeeId());
            messageAddDTO.setSendStatus(JudgeEnum.YES.getValue());
            messageService.addMessage(messageAddDTO);
        }

        activityRelationDao.approveActivityRelation(entity);
        return ResponseDTO.succ();
    }

    /**
     * 更新参与活动状态
     *
     * @param updateDTO
     * @return
     */
    public ResponseDTO<String> updateActivityRelationJoinStatus(ActivityRelationUpdateDTO updateDTO) {
        activityRelationDao.updateActivityRelationJoinStatus(updateDTO);
        return ResponseDTO.succ();
    }
}
