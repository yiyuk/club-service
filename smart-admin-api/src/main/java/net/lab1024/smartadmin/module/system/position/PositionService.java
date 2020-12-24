package net.lab1024.smartadmin.module.system.position;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;
import net.lab1024.smartadmin.common.constant.JudgeEnum;
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
    private NoticeService noticeService;

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

    /**
     * 查询社团
     *
     * @param queryDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<PageResultDTO<PositionResultVO>> queryPositionByPage(PositionQueryDTO queryDTO) {
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
            //return ResponseDTO.wrap(PositionResponseCodeConst.REMOVE_DEFINE);
            //将报错改为删除这些用户与该社团的关联关系
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
     * 添加社团审核状态
     *
     * @param addDTO
     * @return
     */
    public ResponseDTO<String> addPositionApprove(PositionApproveAddDTO addDTO) {
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
        NoticeAddDTO noticeAddDTO = new NoticeAddDTO();
        PositionApproveResultDTO resultDTO = positionDao.selectPositionApproveByID(updateDTO.getId());
        String positionName = positionDao.selectPositionApproveByID(updateDTO.getId()).getPositionName();
        Long approverId = SmartRequestTokenUtil.getRequestUser().getRequestUserId();
        updateDTO.setApproverId(approverId);
        if (updateDTO.getStatus() == ApproveTypeEnum.SUCCESS.getValue()) {
            //将角色更改为社团管理员
            roleEmployeeDao.updateRoleById(SmartRequestTokenUtil.getRequestUser().getRequestUserId(),53);
            //新建社团
            positionDao.insert(SmartBeanUtil.copy(resultDTO, PositionEntity.class));
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
            messageAddDTO.setContent("您的创建" + positionName + "申请已通过！");
            messageAddDTO.setReceiverId(resultDTO.getEmployeeId());
            messageAddDTO.setSendStatus(JudgeEnum.YES.getValue());
            messageService.addMessage(messageAddDTO);
        } else {
            return ResponseDTO.wrap(PositionResponseCodeConst.APPROVE_STATUS_DEFINE);
        }
        positionDao.updatePositionApprove(updateDTO);
        return ResponseDTO.succ();
    }

    ;

    /**
     * 根据社团Id查询关联信息
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<PositionRelationResultDTO>> queryRelationByPage(PositionRelationQueryDTO queryDTO) {
        if (queryDTO.getEmployeeId() <= 0) {
            queryDTO.setEmployeeId(null);
        }
        if (queryDTO.getPositionId() <= 0) {
            queryDTO.setPositionId(null);
        }
        if (queryDTO.getStatus() <= 0) {
            queryDTO.setStatus(null);
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
        //查询是否存在相关的社团关联
        List<PositionRelationResultDTO> dtoList = positionDao.selectRelation(SmartBeanUtil.copy(updateDTO, PositionRelationQueryDTO.class));
        if (CollectionUtils.isEmpty(dtoList)) {
            PositionRelationAddDTO positionRelAddDTO = SmartBeanUtil.copy(updateDTO, PositionRelationAddDTO.class);//new PositionRelationAddDTO();
            positionRelAddDTO.setStatus(PositionRelationTypeEnum.JOIN_WAIT.getValue());
            positionDao.insertRelation(positionRelAddDTO);
        } else {
            PositionRelationTypeEnum positionRelationTypeEnum = PositionRelationTypeEnum.valueOf(this.getPosRelTypeByValue(updateDTO.getStatus()));
            switch (positionRelationTypeEnum) {
                case JOIN_SUCCESS:
                    updateDTO.setStatus(PositionRelationTypeEnum.EXIT_WAIT.getValue());
                    break;

                default:
                    return ResponseDTO.wrap(PositionResponseCodeConst.UPDATE_RELATION_DEFINE);
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
        List<PositionRelationResultDTO> dtoList = positionDao.selectRelation(SmartBeanUtil.copy(updateDTO, PositionRelationQueryDTO.class));
        if (CollectionUtils.isEmpty(dtoList)) {
            return ResponseDTO.wrap(PositionResponseCodeConst.RELATION_EXITS_DEFINE);
        }

        PositionEntity positionEntity = positionDao.selectPositionByID(updateDTO.getPositionId());
        NoticeAddDTO noticeAddDTO = new NoticeAddDTO();
        PositionRelationTypeEnum positionRelationTypeEnum = PositionRelationTypeEnum.valueOf(this.getPosRelTypeByValue(updateDTO.getStatus()));
        switch (positionRelationTypeEnum) {
            case JOIN_WAIT:
                if (updateDTO.getApplyResult()) {
                    //成功加入
                    updateDTO.setJoinTime(new Date());
                    updateDTO.setStatus(PositionRelationTypeEnum.JOIN_SUCCESS.getValue());
                    updateDTO.setJoinApproverId(SmartRequestTokenUtil.getRequestUser().getRequestUserId());
                    positionDao.updateRelation(updateDTO);

                    noticeAddDTO.setTitle("入团通知");
                    noticeAddDTO.setContent("恭喜你加入" + positionEntity.getPositionName() + "！");
                } else {
                    //拒绝加入则删除关联
                    PositionRelationQueryDTO positionRelationQueryDTO = SmartBeanUtil.copy(updateDTO, PositionRelationQueryDTO.class);
                    positionDao.deleteRelation(positionRelationQueryDTO);

                    //发送通知
                    MessageAddDTO messageAddDTO = new MessageAddDTO();
                    messageAddDTO.setTitle("入团失败");
                    messageAddDTO.setContent("您的加入" + positionEntity.getPositionName() + "申请被拒绝。");
                    messageAddDTO.setReceiverId(updateDTO.getEmployeeId());
                    messageAddDTO.setSendStatus(JudgeEnum.YES.getValue());
                    messageService.addMessage(messageAddDTO);
                }
                break;
            case EXIT_WAIT:
                if (updateDTO.getApplyResult()) {
                    updateDTO.setExitTime(new Date());
                    updateDTO.setStatus(PositionRelationTypeEnum.EXIT_SUCCESS.getValue());
                    updateDTO.setExitApproverId(SmartRequestTokenUtil.getRequestUser().getRequestUserId());
                    positionDao.updateRelation(updateDTO);

                    noticeAddDTO.setTitle("退团成功");
                    noticeAddDTO.setContent("您已退出" + positionEntity.getPositionName() + "！");
                } else {
                    updateDTO.setStatus(PositionRelationTypeEnum.JOIN_SUCCESS.getValue());
                    updateDTO.setExitApproverId(SmartRequestTokenUtil.getRequestUser().getRequestUserId());
                    positionDao.updateRelation(updateDTO);

                    //发送通知
                    MessageAddDTO messageAddDTO = new MessageAddDTO();
                    messageAddDTO.setTitle("退团失败");
                    messageAddDTO.setContent("您的退出" + positionEntity.getPositionName() + "申请被拒绝。");
                    messageAddDTO.setReceiverId(updateDTO.getEmployeeId());
                    messageAddDTO.setSendStatus(JudgeEnum.YES.getValue());
                    messageService.addMessage(messageAddDTO);
                }
                break;

            default:
                return ResponseDTO.wrap(PositionResponseCodeConst.UPDATE_RELATION_DEFINE);
        }
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
     * 根据用户ID查询 所关联的社团信息
     *
     * @param employeeId
     * @return
     */
    public List<PositionRelationResultDTO> queryPositionByEmployeeId(Long employeeId) {
        PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
        positionRelationQueryDTO.setEmployeeId(employeeId);
        List<PositionRelationResultDTO> positionRelationList = positionDao.selectRelation(positionRelationQueryDTO);
        return positionRelationList;
    }

    /**
     * 根据社团ID查询 所关联的用户信息
     *
     * @param positionId
     * @return
     */
    public List<PositionRelationResultDTO> queryPositionByPositionId(Long positionId) {
        PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
        positionRelationQueryDTO.setPositionId(positionId);
        List<PositionRelationResultDTO> positionRelationList = positionDao.selectRelation(positionRelationQueryDTO);
        return positionRelationList;
    }

}
