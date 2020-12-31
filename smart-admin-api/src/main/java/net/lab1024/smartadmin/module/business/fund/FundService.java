package net.lab1024.smartadmin.module.business.fund;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;
import net.lab1024.smartadmin.common.constant.JudgeEnum;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.module.business.fund.domain.FundEntity;
import net.lab1024.smartadmin.module.business.fund.domain.dto.FundAddDTO;
import net.lab1024.smartadmin.module.business.fund.domain.dto.FundQueryDTO;
import net.lab1024.smartadmin.module.business.fund.domain.dto.FundResultDTO;
import net.lab1024.smartadmin.module.business.fund.domain.dto.FundUpdateDTO;
import net.lab1024.smartadmin.module.business.message.MessageService;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageAddDTO;
import net.lab1024.smartadmin.module.business.notice.NoticeService;
import net.lab1024.smartadmin.module.business.notice.domain.dto.NoticeAddDTO;
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
 * @author yiyuzi
 * @date 2020/12/24 1:05
 */

@Service
public class FundService {

    @Autowired
    private FundDao fundDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private MessageService messageService;

    /**
     * 根据id查询经费申请信息
     *
     * @param id
     * @return
     */
    public ResponseDTO<FundResultDTO> queryFundById(long id) {
        FundEntity entity = fundDao.selectById(id);
        FundResultDTO resultDTO = SmartBeanUtil.copy(entity, FundResultDTO.class);
        resultDTO.setPositionName(positionDao.selectPositionByID(entity.getPositionId()).getPositionName());
        resultDTO.setEmployeeName(employeeDao.selectById(entity.getEmployeeId()).getActualName());
//        if(entity.getApproveId() != null){
//            resultDTO.setApproveName(employeeDao.selectById(entity.getApproveId()).getActualName());
//        }
        return ResponseDTO.succData(resultDTO);
    }

    /**
     * 分页查询经费申请信息
     *
     * @param queryDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<PageResultDTO<FundResultDTO>> queryFundByPage(FundQueryDTO queryDTO) {
            System.out.println(queryDTO);
        Page page = SmartPageUtil.convert2QueryPage(queryDTO);
        List<FundResultDTO> resultDTOList = fundDao.selectByPage(page, queryDTO);
        page.setRecords(resultDTOList.stream().map(e -> SmartBeanUtil.copy(e, FundResultDTO.class)).collect(Collectors.toList()));
        return ResponseDTO.succData(SmartPageUtil.convert2PageResult(page));
    }

    /**
     * 提交经费申请
     *
     * @param addDTO
     * @return
     */
    public ResponseDTO<String> addFund(FundAddDTO addDTO) {
        FundEntity entity = SmartBeanUtil.copy(addDTO, FundEntity.class);
        entity.setApproveStatus(ApproveTypeEnum.WAIT.getValue());
        entity.setUpdateTime(new Date());
        entity.setCreateTime(new Date());
        entity.setEmployeeId(SmartRequestTokenUtil.getRequestUser().getRequestUserId());
        fundDao.insert(entity);
        return ResponseDTO.succ();
    }

    /**
     * 审核经费申请
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> approveFund(FundUpdateDTO updateDTO) {
        FundEntity entity = SmartBeanUtil.copy(updateDTO, FundEntity.class);
        entity.setUpdateTime(new Date());
        entity.setApproveId(SmartRequestTokenUtil.getRequestUserId());
        fundDao.UpdateApprove(entity);

        entity = fundDao.selectById(updateDTO.getId());
        if (updateDTO.getApproveStatus() == ApproveTypeEnum.SUCCESS.getValue()) {
            //发送通知
            MessageAddDTO messageAddDTO = new MessageAddDTO();
            messageAddDTO.setTitle("经费申请成功");
            messageAddDTO.setContent("您的经费" + entity.getRemark() + "申请已通过。");
            messageAddDTO.setReceiverId(entity.getEmployeeId());
            messageAddDTO.setSendStatus(JudgeEnum.YES.getValue());
            messageService.addMessage(messageAddDTO);
        }else if (updateDTO.getApproveStatus() == ApproveTypeEnum.FAIL.getValue()){
            //发送通知
            MessageAddDTO messageAddDTO = new MessageAddDTO();
            messageAddDTO.setTitle("经费申请失败");
            messageAddDTO.setContent("您的经费" + entity.getRemark() + "申请被拒绝。");
            messageAddDTO.setReceiverId(entity.getEmployeeId());
            messageAddDTO.setSendStatus(JudgeEnum.YES.getValue());
            messageService.addMessage(messageAddDTO);
        }
        return ResponseDTO.succ();
    }
}
