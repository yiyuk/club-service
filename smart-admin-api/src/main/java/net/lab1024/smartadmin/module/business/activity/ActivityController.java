package net.lab1024.smartadmin.module.business.activity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.smartadmin.common.anno.NoNeedLogin;
import net.lab1024.smartadmin.common.anno.OperateLog;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.constant.SwaggerTagConst;
import net.lab1024.smartadmin.module.business.activity.Service.ActivityApproveService;
import net.lab1024.smartadmin.module.business.activity.Service.ActivityRelationService;
import net.lab1024.smartadmin.module.business.activity.Service.ActivityService;
import net.lab1024.smartadmin.module.business.activity.domain.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yiyuzi
 * @date 2020/12/22 23:03
 */

@Api(tags = {SwaggerTagConst.Admin.COMMON_ACTIVITY})
@OperateLog
@RestController
public class ActivityController {
    @Autowired
    ActivityApproveService activityApproveService;

    @Autowired
    ActivityRelationService activityRelationService;

    @Autowired
    ActivityService activityService;
@NoNeedLogin
    @ApiOperation(value = "根据ID查询活动申请记录", notes = "根据ID查询活动申请记录 @author hxy")
    @GetMapping("/activity/getApproveById/{id}")
    public ResponseDTO<ActivityApproveResultDTO> getApproveById(@PathVariable Long id) {
        return activityApproveService.queryActivityApproveById(id);
    }
@NoNeedLogin
    @ApiOperation(value = "分页查询活动申请记录", notes = "分页查询活动申请记录 @author hxy")
    @PostMapping("/activity/getApproveListPage")
    public ResponseDTO<PageResultDTO<ActivityApproveResultDTO>> getApproveListPage(@RequestBody @Valid ActivityApproveQueryDTO queryDTO) {
        return activityApproveService.queryActivityApproveByPage(queryDTO);
    }
@NoNeedLogin
    @ApiOperation(value = "活动申请", notes = "活动申请 @author hxy")
    @PostMapping("/activity/addApprove")
    public ResponseDTO<String> addApprove(@RequestBody @Valid ActivityApproveAddDTO addDTO) {
        return activityApproveService.addActivityApprove(addDTO);
    }
    @NoNeedLogin
    @ApiOperation(value = "审核活动申请", notes = "审核活动申请 @author hxy")
    @PostMapping("/activity/approveActivity")
    public ResponseDTO<String> approveActivity(@RequestBody @Valid ActivityApproveUpdateDTO updateDTO) {
        return activityApproveService.approveActivity(updateDTO);
    }


    @NoNeedLogin
    @ApiOperation(value = "根据ID查询活动记录", notes = "根据ID查询活动申请记录 @author hxy")
    @GetMapping("/activity/queryActivityById/{id}")
    public ResponseDTO<ActivityResultDTO> queryActivityById(@PathVariable Long id) {
        return activityService.queryActivityById(id);
    }
    @NoNeedLogin
    @ApiOperation(value = "分页查询活动记录", notes = "分页查询活动申请记录 @author hxy")
    @PostMapping("/activity/queryActivityByPage")
    public ResponseDTO<PageResultDTO<ActivityResultDTO>> queryActivityByPage(@RequestBody @Valid ActivityQueryDTO queryDTO) {
        return activityService.queryActivityByPage(queryDTO);
    }
    @NoNeedLogin
    @ApiOperation(value = "更新活动", notes = "更新活动 @author hxy")
    @PostMapping("/activity/updateActivity")
    public ResponseDTO<String> updateActivity(@RequestBody @Valid ActivityUpdateDTO updateDTO) {
        return activityService.updateActivity(updateDTO);
    }
}
