package net.lab1024.smartadmin.module.system.position;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;
import net.lab1024.smartadmin.common.constant.JudgeEnum;
import net.lab1024.smartadmin.common.constant.RoleTypeEnum;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.module.business.message.MessageService;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageAddDTO;
import net.lab1024.smartadmin.module.business.notice.NoticeService;
import net.lab1024.smartadmin.module.business.notice.domain.dto.NoticeAddDTO;
import net.lab1024.smartadmin.module.system.position.domain.dto.*;
import net.lab1024.smartadmin.module.system.position.domain.entity.PositionApproveEntity;
import net.lab1024.smartadmin.module.system.position.domain.entity.PositionEntity;
import net.lab1024.smartadmin.module.system.position.domain.entity.PositionRelationEntity;
import net.lab1024.smartadmin.module.system.privilege.constant.PrivilegeTypeEnum;
import net.lab1024.smartadmin.module.system.role.roleemployee.RoleEmployeeDao;
import net.lab1024.smartadmin.util.SmartBeanUtil;
import net.lab1024.smartadmin.util.SmartPageUtil;
import net.lab1024.smartadmin.util.SmartRequestTokenUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zzr
 */
@Service
public class PositionService {

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private RoleEmployeeDao roleEmployeeDao;

    @Autowired
    private MessageService messageService;


    public String getPosRelTypeByValue(Integer integer) {
        switch (integer) {
            case 1:
                return PositionRelationTypeEnum.JOIN_WAIT.toString();
            case 2:
                return PositionRelationTypeEnum.JOIN_FAIL.toString();
            case 3:
                return PositionRelationTypeEnum.JOIN_SUCCESS.toString();
            case 4:
                return PositionRelationTypeEnum.EXIT_WAIT.toString();
            case 5:
                return PositionRelationTypeEnum.EXIT_FAIL.toString();
            case 6:
                return PositionRelationTypeEnum.EXIT_SUCCESS.toString();

            default:
                ResponseDTO.wrap(PositionResponseCodeConst.RELATION_STATUS_DEFINE);
        }
        return "";
    }

    private boolean isClubAdmin(){
        Long roleId = roleEmployeeDao.selectOneRoleIdByEmployeeId(SmartRequestTokenUtil.getRequestUserId());
        if(roleId == (long) RoleTypeEnum.CLUB_ADMIN.getValue()) {
            return true;
        }
        return false;
    }

    /**
     * 根据社团名查询社团
     *
     * @param name
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<List<PositionResultVO>> queryPositionByName(String name) {
        List<PositionEntity> entityList = positionDao.selectPositionListByName(name);
        List<PositionResultVO> positionResultVOList =
                entityList.stream().map(e -> SmartBeanUtil.copy(e, PositionResultVO.class)).collect(Collectors.toList());
        return ResponseDTO.succData(positionResultVOList);
    }

    /**
     * 返回当前登录者管理的社团
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<List<PositionResultVO>> queryPositionByAdmin() {
        List<PositionEntity> entityList = new ArrayList<PositionEntity>();
        if(SmartRequestTokenUtil.getRequestUser().getEmployeeBO().getIsSuperman()){
            entityList = positionDao.selectPositionListByName("");
        }else{
            //查询用户管理的社团
            PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
            positionRelationQueryDTO.setEmployeeId(SmartRequestTokenUtil.getRequestUserId());
            positionRelationQueryDTO.setStatus(PositionRelationTypeEnum.ADMIN.getValue());
            List<PositionRelationResultDTO> positionRelationResultDTOList = positionDao.selectRelation(positionRelationQueryDTO);

            for(PositionRelationResultDTO resultDTO:positionRelationResultDTOList){
                entityList.add(positionDao.selectPositionByID(resultDTO.getPositionId()));
            }
        }
        List<PositionResultVO> positionResultVOList =
                entityList.stream().map(e -> SmartBeanUtil.copy(e, PositionResultVO.class)).collect(Collectors.toList());
        return ResponseDTO.succData(positionResultVOList);
    }

    /**
     * 分页查询社团
     *
     * @param queryDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<PageResultDTO<PositionResultVO>> queryPositionByPage(PositionQueryDTO queryDTO) {
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
        List<PositionEntity> entityList = positionDao.selectByPage(page, queryDTO);
        page.setRecords(entityList.stream().map(e -> SmartBeanUtil.copy(e, PositionResultVO.class)).collect(Collectors.toList()));
        PageResultDTO<PositionResultVO> pageResultDTO = SmartPageUtil.convert2PageResult(page);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 新增社团
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> addPosition(PositionAddDTO addDTO) {
        PositionEntity positionEntity = SmartBeanUtil.copy(addDTO, PositionEntity.class);
        positionDao.insert(positionEntity);
        return ResponseDTO.succ();
    }

    /**
     * 修改社团
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> updatePosition(PositionUpdateDTO updateDTO) {
        PositionEntity positionEntity = SmartBeanUtil.copy(updateDTO, PositionEntity.class);
        positionDao.updateById(positionEntity);
        return ResponseDTO.succ();
    }

    /**
     * 根据ID查询社团
     *
     * @param id
     * @return
     */
    public ResponseDTO<PositionResultVO> queryPositionById(Long id) {
        return ResponseDTO.succData(SmartBeanUtil.copy(positionDao.selectById(id), PositionResultVO.class));
    }

    /**
     * 删除社团
     */
    public ResponseDTO<String> removePosition(Long id) {
        //查询是否还有人关联该社团
        PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
        positionRelationQueryDTO.setPositionId(id);
        List<PositionRelationResultDTO> dtoList = positionDao.selectRelation(positionRelationQueryDTO);
        if (CollectionUtils.isNotEmpty(dtoList)) {
            //删除这些用户与该社团的关联关系
            for (PositionRelationResultDTO resultDTO : dtoList) {
                positionDao.deleteRelationByPositionId(resultDTO.getPositionId());
            }
        }
        positionDao.deleteById(id);
        return ResponseDTO.succ();
    }




    /**
     * 查询社团审核状态
     *
     * @param id
     * @return
     */
    public ResponseDTO<PositionApproveResultDTO> queryPositionApproveByID(Long id) {
        return ResponseDTO.succData(positionDao.selectPositionApproveByID(id));
    }

    /**
     * 查询社团审核状态
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<List<PositionApproveResultDTO>> queryPositionApprove(PositionApproveQueryDTO queryDTO) {
        List<PositionApproveResultDTO> list = positionDao.selectPositionApprove(queryDTO);
        return ResponseDTO.succData(list);
    }

    /**
     * 提交创建社团申请，添加社团审核状态
     *
     * @param addDTO
     * @return
     */
    public ResponseDTO<String> addPositionApprove(PositionApproveAddDTO addDTO) {
        //判断是否存在同名社团
        List<PositionEntity> list = positionDao.selectPositionListByName(addDTO.getPositionName());
        if(!list.isEmpty()){
            return ResponseDTO.wrap(PositionResponseCodeConst.POSITION_NAME_DEFINE);
        }

        addDTO.setEmployeeId(SmartRequestTokenUtil.getRequestUser().getRequestUserId());
        positionDao.insertPositionApprove(addDTO);
        return ResponseDTO.succ();
    }

    /**
     * 更新社团审核状态
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> updatePositionApprove(PositionApproveUpdateDTO updateDTO) {
        PositionApproveResultDTO resultDTO = positionDao.selectPositionApproveByID(updateDTO.getId());
        String positionName = positionDao.selectPositionApproveByID(updateDTO.getId()).getPositionName();
        Long approverId = SmartRequestTokenUtil.getRequestUserId();
        updateDTO.setApproverId(approverId);
        if (updateDTO.getStatus() == ApproveTypeEnum.SUCCESS.getValue()) {
            //将角色更改为社团管理员
            roleEmployeeDao.updateRoleById(SmartRequestTokenUtil.getRequestUserId(), RoleTypeEnum.CLUB_ADMIN.getValue());
            //新建社团
            positionDao.insert(SmartBeanUtil.copy(resultDTO, PositionEntity.class));
            //创建社团关系
            PositionRelationAddDTO positionRelationAddDTO = new PositionRelationAddDTO();
            List<PositionEntity> list = positionDao.selectPositionListByName(positionName);
            positionRelationAddDTO.setPositionId(list.get(0).getId());
            positionRelationAddDTO.setEmployeeId(SmartRequestTokenUtil.getRequestUserId());
            positionRelationAddDTO.setStatus(PositionRelationTypeEnum.ADMIN.getValue());
            positionDao.insertRelation(positionRelationAddDTO);
            //发送通知
            MessageAddDTO messageAddDTO = new MessageAddDTO();
            messageAddDTO.setTitle("创建社团成功");
            messageAddDTO.setContent("您的创建" + positionName + "申请已通过！");
            messageAddDTO.setReceiverId(resultDTO.getEmployeeId());
            messageAddDTO.setSendStatus(JudgeEnum.YES.getValue());
            messageService.addMessage(messageAddDTO);
        } else if (updateDTO.getStatus() == ApproveTypeEnum.FAIL.getValue()) {
            //发送通知
            MessageAddDTO messageAddDTO = new MessageAddDTO();
            messageAddDTO.setTitle("创建社团失败");
            messageAddDTO.setContent("您的创建" + positionName + "申请被拒绝！");
            messageAddDTO.setReceiverId(resultDTO.getEmployeeId());
            messageAddDTO.setSendStatus(JudgeEnum.YES.getValue());
            messageService.addMessage(messageAddDTO);
        } else {
            return ResponseDTO.wrap(PositionResponseCodeConst.APPROVE_STATUS_DEFINE);
        }
        positionDao.updatePositionApprove(updateDTO);
        return ResponseDTO.succ();
    }



    /**
     * 分页查询关联信息
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<PositionRelationResultDTO>> queryRelationByPage(PositionRelationQueryDTO queryDTO) {
        if (!queryDTO.getIsShow()) {
            queryDTO.setEmployeeId(SmartRequestTokenUtil.getRequestUserId());
        }
        Page page = SmartPageUtil.convert2QueryPage(queryDTO);
        List<PositionRelationResultDTO> entityList = positionDao.selectRelByPage(page, queryDTO);
        page.setRecords(entityList.stream().map(e -> SmartBeanUtil.copy(e, PositionRelationResultDTO.class)).collect(Collectors.toList()));
        PageResultDTO<PositionRelationResultDTO> pageResultDTO = SmartPageUtil.convert2PageResult(page);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 添加社团-用户关联关系
     *
     * @param positionRelAddDTO
     * @return
     */
    public ResponseDTO<String> addPositionRelation(PositionRelationAddDTO positionRelAddDTO) {
        positionDao.insertBatchRelation(positionRelAddDTO);
        return ResponseDTO.succ();
    }

    /**
     * 入社退社申请，更新社团-用户关联
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> applyPositionRelation(PositionRelationUpdateDTO updateDTO) {
        if (updateDTO.getId() == null) {
            // 检查用户是否已经提交过申请或已经入团
            PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
            positionRelationQueryDTO.setEmployeeId(SmartRequestTokenUtil.getRequestUserId());
            List<PositionRelationResultDTO> list = positionDao.selectRelation(positionRelationQueryDTO);
            for(PositionRelationResultDTO item : list){
                if(item.getPositionId() == updateDTO.getPositionId() && item.getStatus() != PositionRelationTypeEnum.EXIT_SUCCESS.getValue()){
                    return ResponseDTO.wrap(PositionResponseCodeConst.JOIN_POSITION_DEFINE);
                }
            }

            updateDTO.setEmployeeId(SmartRequestTokenUtil.getRequestUserId());
            PositionRelationAddDTO positionRelAddDTO = SmartBeanUtil.copy(updateDTO, PositionRelationAddDTO.class);
            positionRelAddDTO.setStatus(PositionRelationTypeEnum.JOIN_WAIT.getValue());
            positionDao.insertRelation(positionRelAddDTO);
        } else {
            updateDTO = SmartBeanUtil.copy(positionDao.selectRelationById(updateDTO.getId()), PositionRelationUpdateDTO.class);
            if(updateDTO.getStatus() == PositionRelationTypeEnum.JOIN_SUCCESS.getValue()){
                updateDTO.setStatus(PositionRelationTypeEnum.EXIT_WAIT.getValue());
            }else {
                return ResponseDTO.wrap(PositionResponseCodeConst.JOIN_POSITION_DEFINE);
            }

            positionDao.updateRelation(updateDTO);
        }
        return ResponseDTO.succ();
    }

    /**
     * 审核用户入团退团，更新社团-用户关联
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> approvePositionRelation(PositionRelationUpdateDTO updateDTO) {
        //判断关联是否存在
        PositionRelationResultDTO positionRelationResultDTO = positionDao.selectRelationById(updateDTO.getId());
        if (positionRelationResultDTO == null) {
            return ResponseDTO.wrap(PositionResponseCodeConst.RELATION_EXITS_DEFINE);
        }

        Boolean applyResult = updateDTO.getApplyResult();
        updateDTO = SmartBeanUtil.copy(positionRelationResultDTO, PositionRelationUpdateDTO.class);
        PositionEntity positionEntity = positionDao.selectPositionByID(positionRelationResultDTO.getPositionId());
        PositionRelationTypeEnum positionRelationTypeEnum = PositionRelationTypeEnum.valueOf(this.getPosRelTypeByValue(positionRelationResultDTO.getStatus()));
        MessageAddDTO messageAddDTO = new MessageAddDTO();

        switch (positionRelationTypeEnum) {
            case JOIN_WAIT:
                if (applyResult) {
                    //成功加入
                    updateDTO.setJoinTime(new Date());
                    updateDTO.setStatus(PositionRelationTypeEnum.JOIN_SUCCESS.getValue());
                    updateDTO.setJoinApproverId(SmartRequestTokenUtil.getRequestUserId());
                    positionDao.updateRelation(updateDTO);

                    //发送通知
                    messageAddDTO.setTitle("入团通知");
                    messageAddDTO.setContent("恭喜你加入" + positionEntity.getPositionName() + "！");
                } else {
                    //拒绝加入则删除关联
                    PositionRelationQueryDTO positionRelationQueryDTO = SmartBeanUtil.copy(updateDTO, PositionRelationQueryDTO.class);
                    positionDao.deleteRelation(positionRelationQueryDTO);

                    //发送通知
                    messageAddDTO.setTitle("入团失败");
                    messageAddDTO.setContent("您的加入" + positionEntity.getPositionName() + "申请被拒绝。");
                }
                break;
            case EXIT_WAIT:
                if (applyResult) {
                    updateDTO.setExitTime(new Date());
                    updateDTO.setStatus(PositionRelationTypeEnum.EXIT_SUCCESS.getValue());
                    updateDTO.setExitApproverId(SmartRequestTokenUtil.getRequestUserId());
                    positionDao.updateRelation(updateDTO);

                    messageAddDTO.setTitle("退团成功");
                    messageAddDTO.setContent("您已退出" + positionEntity.getPositionName() + "！");
                } else {
                    updateDTO.setStatus(PositionRelationTypeEnum.JOIN_SUCCESS.getValue());
                    updateDTO.setExitApproverId(SmartRequestTokenUtil.getRequestUserId());
                    positionDao.updateRelation(updateDTO);

                    //发送通知
                    messageAddDTO.setTitle("退团失败");
                    messageAddDTO.setContent("您的退出" + positionEntity.getPositionName() + "申请被拒绝。");
                }
                break;

            default:
                return ResponseDTO.wrap(PositionResponseCodeConst.UPDATE_RELATION_DEFINE);
        }
        messageAddDTO.setReceiverId(positionRelationResultDTO.getEmployeeId());
        messageAddDTO.setSendStatus(JudgeEnum.YES.getValue());
        messageService.addMessage(messageAddDTO);

        return ResponseDTO.succ();
    }

    /**
     * 删除指定用户的社团关联关系
     *
     * @param employeeId
     * @return
     */
    public ResponseDTO<String> removePositionRelation(Long employeeId) {
        positionDao.deleteRelationByEmployeeId(employeeId);
        return ResponseDTO.succ();
    }

    /**
     * 根据id查询关系
     *
     * @param id
     * @return
     */
    public ResponseDTO<PositionRelationResultDTO> getRelationById(Long id){
        PositionRelationResultDTO resultDTO = positionDao.selectRelationById(id);
        return ResponseDTO.succData(resultDTO);
    }

    /**
     * 退出社团
     *
     * @param id
     * @return
     */
    public ResponseDTO<String> exitPosition(Long id){
        PositionRelationUpdateDTO updateDTO = SmartBeanUtil.copy(positionDao.selectRelationById(id), PositionRelationUpdateDTO.class);
        updateDTO.setStatus(PositionRelationTypeEnum.EXIT_SUCCESS.getValue());
        updateDTO.setExitApproverId(SmartRequestTokenUtil.getRequestUserId());
        updateDTO.setExitTime(new Date());
        positionDao.updateRelation(updateDTO);

        return ResponseDTO.succ();
    }

}
