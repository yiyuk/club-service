package net.lab1024.smartadmin.module.business.message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.smartadmin.common.anno.NoNeedLogin;
import net.lab1024.smartadmin.common.anno.OperateLog;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.constant.SwaggerTagConst;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageAddDTO;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageQueryDTO;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageResultDTO;
import net.lab1024.smartadmin.module.business.message.domain.dto.MessageUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yiyuzi
 * @date 2020/12/24 17:36
 */

@Api(tags = {SwaggerTagConst.Club.COMMON_MESSAGE})
@OperateLog
@RestController
public class MessageController {
    @Autowired
    MessageService messageService;

    @ApiOperation(value = "根据id查询消息", notes = "根据id查询消息 @author hxy")
    @GetMapping("/message/getById/{id}")
    public ResponseDTO<MessageResultDTO> getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }

    @ApiOperation(value = "分页查询消息", notes = "分页查询消息 @author hxy")
    @PostMapping("/message/getByPage")
    public ResponseDTO<PageResultDTO<MessageResultDTO>> queryMessageByPage(@RequestBody @Valid MessageQueryDTO queryDTO) {
        return messageService.queryMessageByPage(queryDTO);
    }

    @ApiOperation(value = "添加消息", notes = "添加消息 @author hxy")
    @PostMapping("/message/add")
    public ResponseDTO<String> addMessage(@RequestBody @Valid MessageAddDTO addDTO) {
        return messageService.addMessage(addDTO);
    }

    @ApiOperation(value = "更新消息", notes = "更新消息 @author hxy")
    @PostMapping("/message/update")
    public ResponseDTO<String> updateMessage(@RequestBody @Valid MessageUpdateDTO updateDTO) {
        return messageService.updateMessage(updateDTO);
    }

    @ApiOperation(value = "更新消息为已读状态", notes = "更新消息为已读状态 @author hxy")
    @GetMapping("/message/read/{id}")
    public ResponseDTO<String> readMessage(@PathVariable Long id) {
        return messageService.readMessage(id);
    }

    @ApiOperation(value = "删除消息", notes = "删除消息 @author hxy")
    @GetMapping("/message/delete/{id}")
    public ResponseDTO<String> deleteMessage(@PathVariable Long id) {
        return messageService.deleteMessage(id);
    }
}
