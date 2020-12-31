package net.lab1024.smartadmin.module.business.message;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.smartadmin.common.constant.JudgeEnum;
import net.lab1024.smartadmin.common.constant.RoleTypeEnum;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.module.business.message.domain.MessageEntity;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageAddDTO;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageQueryDTO;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageResultDTO;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageUpdateDTO;
import net.lab1024.smartadmin.module.system.employee.EmployeeDao;
import net.lab1024.smartadmin.module.system.role.roleemployee.RoleEmployeeDao;
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
 * @date 2020/12/24 17:32
 */

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private RoleEmployeeDao roleEmployeeDao;

    /**
     * 根据id查询消息
     *
     * @param id
     * @return
     */
    public ResponseDTO<MessageResultDTO> getMessageById(long id) {
        MessageEntity entity = messageDao.selectById(id);
        MessageResultDTO resultDTO = SmartBeanUtil.copy(entity, MessageResultDTO.class);
        resultDTO.setSenderName(employeeDao.selectById(entity.getSenderId()).getActualName());
        resultDTO.setReceiverName(employeeDao.selectById(entity.getReceiverId()).getActualName());
        return ResponseDTO.succData(resultDTO);
    }

    /**
     * 分页查询消息
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<MessageResultDTO>> queryMessageByPage(MessageQueryDTO queryDTO) {
        Long roleId = roleEmployeeDao.selectOneRoleIdByEmployeeId(SmartRequestTokenUtil.getRequestUserId());
        queryDTO.setReceiverId(SmartRequestTokenUtil.getRequestUserId());
        Page page = SmartPageUtil.convert2QueryPage(queryDTO);
        List<MessageResultDTO> dtoList = SmartBeanUtil.copyList(messageDao.selectByPage(page, queryDTO), MessageResultDTO.class);
        page.setRecords(dtoList);
        PageResultDTO<MessageResultDTO> pageResultDTO = SmartPageUtil.convert2PageResult(page);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 添加消息
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> addMessage(MessageAddDTO addDTO){
        MessageEntity entity = SmartBeanUtil.copy(addDTO,MessageEntity.class);
        entity.setSenderId(SmartRequestTokenUtil.getRequestUser().getRequestUserId());
        entity.setUpdateTime(new Date());
        entity.setCreateTime(new Date());
        if (addDTO.getSendStatus() == JudgeEnum.YES.getValue()){
            entity.setSendTime(new Date());
            entity.setReceiveStatus(JudgeEnum.NO.getValue());
        }
        messageDao.insert(entity);

        return ResponseDTO.succ();
    }

    /**
     * 更新消息
     *
     * @param updateDTO
     * @return
     */
    public ResponseDTO<String> updateMessage(MessageUpdateDTO updateDTO) {
        MessageEntity entity = SmartBeanUtil.copy(updateDTO,MessageEntity.class);
        entity.setUpdateTime(new Date());
        //如果消息状态为已发送，但接受状态null，则更新发送时间和已读状态
        if(updateDTO.getSendStatus() == JudgeEnum.YES.getValue() && updateDTO.getReceiveStatus() == null){
            entity.setSendTime(new Date());
            entity.setReceiveStatus(JudgeEnum.NO.getValue());
        }
        messageDao.updateMessage(entity);
        return ResponseDTO.succ();
    }

    /**
     * 更新消息为发送状态
     *
     * @param id
     * @return
     */
    public ResponseDTO<String> sendMessage(long id) {
        MessageEntity entity = messageDao.selectById(id);
        entity.setSendStatus(JudgeEnum.YES.getValue());
        messageDao.updateMessage(entity);
        return ResponseDTO.succ();
    }

    /**
     * 更新消息为已读状态
     *
     * @param id
     * @return
     */
    public ResponseDTO<String> readMessage(long id) {
        MessageEntity entity = messageDao.selectById(id);
        entity.setReceiveStatus(JudgeEnum.YES.getValue());
        messageDao.updateMessage(entity);
        return ResponseDTO.succ();
    }

    public ResponseDTO<String> deleteMessage(long id){
        messageDao.deleteById(id);
        return ResponseDTO.succ();
    }
}
