package net.lab1024.smartadmin.module.business.activity.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;
import net.lab1024.smartadmin.common.constant.JudgeEnum;
import net.lab1024.smartadmin.common.constant.RoleTypeEnum;
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
import net.lab1024.smartadmin.module.system.position.PositionRelationTypeEnum;
import net.lab1024.smartadmin.module.system.position.domain.dto.PositionRelationQueryDTO;
import net.lab1024.smartadmin.module.system.position.domain.dto.PositionRelationResultDTO;
import net.lab1024.smartadmin.module.system.position.domain.entity.PositionEntity;
import net.lab1024.smartadmin.module.system.role.roleemployee.RoleEmployeeDao;
import net.lab1024.smartadmin.util.SmartBeanUtil;
import net.lab1024.smartadmin.util.SmartPageUtil;
import net.lab1024.smartadmin.util.SmartRequestTokenUtil;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private RoleEmployeeDao roleEmployeeDao;

    private boolean isClubAdmin(){
        Long roleId = roleEmployeeDao.selectOneRoleIdByEmployeeId(SmartRequestTokenUtil.getRequestUserId());
        if(roleId == (long) RoleTypeEnum.CLUB_ADMIN.getValue()) {
            return true;
        }
        return false;
    }

    /**
     * 根据id查询参与活动信息
     *
     * @param id
     * @return
     */
    public ResponseDTO<ActivityRelationResultDTO> queryActivityRelationById(long id) {
        ActivityRelationResultDTO resultDTO = activityRelationDao.selectRelationById(id);
        return ResponseDTO.succData(resultDTO);
    }

    /**
     * 分页查询参与活动信息
     *
     * @param queryDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<PageResultDTO<ActivityRelationResultDTO>> queryActivityByRelAndPage(ActivityRelationQueryDTO queryDTO) {
        Page page = SmartPageUtil.convert2QueryPage(queryDTO);
        if(queryDTO.getIsShow()){
            queryDTO.setEmployeeId(SmartRequestTokenUtil.getRequestUserId());
        }
        List<ActivityRelationResultDTO> resultDTOList = activityRelationDao.selectActivityRelationByPage(page, queryDTO);
//        List<Long> positionIdList = new ArrayList<>();
//        resultDTOList.forEach(e -> {
//            if(e.getPositionId() != null){
//                positionIdList.add(e.getPositionId());
//            }
//        });
//        ActivityQueryDTO activityQueryDTO = SmartBeanUtil.copy(queryDTO, ActivityQueryDTO.class);
//        activityQueryDTO.setEmployeeId(SmartRequestTokenUtil.getRequestUserId());
//        if(positionIdList.size() > 0){
//            activityQueryDTO.setPositionIdList(positionIdList);
//        }
//        List<Long> idList = new ArrayList<>();
//        resultDTOList.forEach(e -> {
//            if(e.getActivityId() != null){
//                idList.add(e.getActivityId());
//            }
//        });
//        ActivityQueryDTO activityQueryDTO = SmartBeanUtil.copy(queryDTO, ActivityQueryDTO.class);
//
//        List<ActivityResultDTO> activityResultDTOList = new ArrayList<>();
//        if(idList.size() > 0){
//            activityQueryDTO.setActivityIdList(idList);
//            page = SmartPageUtil.convert2QueryPage(activityQueryDTO);
//            activityResultDTOList = activityDao.selectByPage(page, activityQueryDTO);
//        }
        page.setRecords(resultDTOList.stream().map(e -> SmartBeanUtil.copy(e, ActivityRelationResultDTO.class)).collect(Collectors.toList()));
        //PageResultDTO<ActivityApproveResultDTO> pageResultDTO = SmartPageUtil.convert2PageResult(page);
        return ResponseDTO.succData(SmartPageUtil.convert2PageResult(page));
    }

    /**
     * 分页查询参与活动信息
     *
     * @param queryDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<PageResultDTO<ActivityRelationResultDTO>> queryActivityRelationByPage(ActivityRelationQueryDTO queryDTO) {
        if(!queryDTO.getIsShow() && isClubAdmin()){
            PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
            positionRelationQueryDTO.setEmployeeId(SmartRequestTokenUtil.getRequestUserId());
            positionRelationQueryDTO.setStatus(PositionRelationTypeEnum.ADMIN.getValue());
            List<PositionRelationResultDTO> list = positionDao.selectRelation(positionRelationQueryDTO);
            if(list != null && list.size() > 0) {
                List<Long> positionIdList = new ArrayList<Long>();
                for (PositionRelationResultDTO resultDTO : list) {
                    positionIdList.add(resultDTO.getPositionId());
                }
                queryDTO.setPositionIdList(positionIdList);
            }
        }
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
        ActivityEntity activityEntity = activityDao.selectById(addDTO.getActivityId());
        if(activityEntity == null){
            return ResponseDTO.wrap(ActivityResponseCodeConst.NOT_EXIST_ERROR);
        }
        if (activityEntity.getStartTime().compareTo(new Date()) >= 0 || activityEntity.getStopTime().compareTo(new Date()) <= 0) {
            return ResponseDTO.wrap(ActivityResponseCodeConst.NOT_RUN_ERROR);
        }

        //判断同一活动之前是否有未审核或已通过的报名
        ActivityRelationQueryDTO queryDTO = new ActivityRelationQueryDTO();
        queryDTO.setEmployeeId(employeeId);
        queryDTO.setActivityId(addDTO.getActivityId());
        queryDTO.setStatus(ApproveTypeEnum.WAIT.getValue());
        List<ActivityRelationResultDTO> list = activityRelationDao.selectActivityRelation(queryDTO);
        queryDTO.setStatus(ApproveTypeEnum.SUCCESS.getValue());
        List<ActivityRelationResultDTO> list2 = activityRelationDao.selectActivityRelation(queryDTO);
        if (!list.isEmpty() || !list2.isEmpty()) {
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
