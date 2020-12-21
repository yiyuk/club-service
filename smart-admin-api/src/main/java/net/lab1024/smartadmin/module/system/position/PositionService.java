package net.lab1024.smartadmin.module.system.position;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.module.system.position.domain.dto.*;
import net.lab1024.smartadmin.module.system.position.domain.entity.PositionEntity;
import net.lab1024.smartadmin.module.system.privilege.constant.PrivilegeTypeEnum;
import net.lab1024.smartadmin.util.SmartBeanUtil;
import net.lab1024.smartadmin.util.SmartPageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 查询社团
     *
     * @param queryDTO
     * @return
     */
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
            for (PositionRelationResultDTO resultDTO:dtoList ) {
                positionDao.deleteRelationByPositionId(resultDTO.getPositionId());
            }
        }
        positionDao.deleteById(id);
        return ResponseDTO.succ();
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
    public ResponseDTO<String> applyPositionRelation(PositionRelationUpdateDTO updateDTO) {
        PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
        positionRelationQueryDTO.setPositionId(updateDTO.getPositionId());
        positionRelationQueryDTO.setEmployeeId(updateDTO.getEmployeeId());
        List<PositionRelationResultDTO> dtoList = positionDao.selectRelation(positionRelationQueryDTO);
        if (CollectionUtils.isNotEmpty(dtoList)) {
            PositionRelationAddDTO positionRelAddDTO = new PositionRelationAddDTO();
            positionRelAddDTO.setEmployeeId(updateDTO.getEmployeeId());
            positionRelAddDTO.setPositionId(updateDTO.getPositionId());
            positionRelAddDTO.setStatus(PositionRelationTypeEnum.JOIN_WAIT.toString());
            positionDao.insertRelation(positionRelAddDTO);
        } else {
            PositionRelationTypeEnum positionRelationTypeEnum = PositionRelationTypeEnum.valueOf(updateDTO.getStatus());
            switch (positionRelationTypeEnum) {
                case JOIN_SUCCESS:
                    updateDTO.setStatus(PositionRelationTypeEnum.EXIT_WAIT.toString());
                    break;
                case EXIT_FAIL:
                    updateDTO.setStatus(PositionRelationTypeEnum.EXIT_WAIT.toString());
                    break;

                default:
                    //return ResponseDTO.wrap(PositionResponseCodeConst.UPDATE_RELATION_DEFINE);
            }

            positionDao.updateRelation(updateDTO);
        }
        return ResponseDTO.succ();
    }

    /**
     * 更新社团-用户关联
     *
     * @param updateDTO
     * @return
     */
    public ResponseDTO<String> approvePositionRelation(PositionRelationUpdateDTO updateDTO){
        PositionRelationTypeEnum positionRelationTypeEnum = PositionRelationTypeEnum.valueOf(updateDTO.getStatus());
        switch (positionRelationTypeEnum){
            case JOIN_WAIT:
                if(updateDTO.getApplyResult()){
                    updateDTO.setJoinTime(new Date());
                    updateDTO.setStatus(PositionRelationTypeEnum.JOIN_SUCCESS.toString());
                }else {
                    PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
                    positionRelationQueryDTO.setPositionId(updateDTO.getPositionId());
                    positionRelationQueryDTO.setEmployeeId(updateDTO.getEmployeeId());
                    positionDao.deleteRelation(positionRelationQueryDTO);
                    //TODO 发送站内信
                }
                break;
            case EXIT_WAIT:
                if(updateDTO.getApplyResult()){
                    updateDTO.setJoinTime(new Date());
                    updateDTO.setStatus(PositionRelationTypeEnum.EXIT_SUCCESS.toString());
                }else {
                    updateDTO.setStatus(PositionRelationTypeEnum.EXIT_FAIL.toString());
                }
                break;

            default:
                //return ResponseDTO.wrap(PositionResponseCodeConst.UPDATE_RELATION_DEFINE);
        }

        positionDao.updateRelation(updateDTO);
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
