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

@Api(tags = {SwaggerTagConst.Club.COMMON_ACTIVITY})
@OperateLog
@RestController
public class ActivityController {
    @Autowired
    ActivityApproveService activityApproveService;

    @Autowired
    ActivityRelationService activityRelationService;

    @Autowired
    ActivityService activityService;

    @ApiOperation(value = "根据ID查询活动申请记录", notes = "根据ID查询活动申请记录 @author hxy")
    @GetMapping("/activity/approve/getById/{id}")
    public ResponseDTO<ActivityApproveResultDTO> getApproveById(@PathVariable Long id) {
        return activityApproveService.queryActivityApproveById(id);
    }

    @ApiOperation(value = "分页查询活动申请记录", notes = "分页查询活动申请记录 @author hxy")
    @PostMapping("/activity/approve/getListPage")
    public ResponseDTO<PageResultDTO<ActivityApproveResultDTO>> getApproveListPage(@RequestBody @Valid ActivityApproveQueryDTO queryDTO) {
        return activityApproveService.queryActivityApproveByPage(queryDTO);
    }

    @ApiOperation(value = "活动申请", notes = "活动申请 @author hxy")
    @PostMapping("/activity/approve/add")
    public ResponseDTO<String> addApprove(@RequestBody @Valid ActivityApproveAddDTO addDTO) {
        return activityApproveService.addActivityApprove(addDTO);
    }

    @ApiOperation(value = "审核活动申请", notes = "审核活动申请 @author hxy")
    @PostMapping("/activity/approve/approve")
    public ResponseDTO<String> approveActivity(@RequestBody @Valid ActivityApproveUpdateDTO updateDTO) {
        return activityApproveService.approveActivity(updateDTO);
    }



    @ApiOperation(value = "根据ID查询活动", notes = "根据ID查询活动申请记录 @author hxy")
    @GetMapping("/activity/getById/{id}")
    public ResponseDTO<ActivityResultDTO> queryActivityById(@PathVariable Long id) {
        return activityService.queryActivityById(id);
    }

    @ApiOperation(value = "分页查询活动", notes = "分页查询活动申请记录 @author hxy")
    @PostMapping("/activity/getByPage")
    public ResponseDTO<PageResultDTO<ActivityResultDTO>> queryActivityByPage(@RequestBody @Valid ActivityQueryDTO queryDTO) {
        return activityService.queryActivityByPage(queryDTO);
    }

    @ApiOperation(value = "更新活动", notes = "更新活动 @author hxy")
    @PostMapping("/activity/update")
    public ResponseDTO<String> updateActivity(@RequestBody @Valid ActivityUpdateDTO updateDTO) {
        return activityService.updateActivity(updateDTO);
    }

    @NoNeedLogin
    @ApiOperation(value = "删除活动", notes = "删除活动 @author hxy")
    @GetMapping("/activity/delete")
    public ResponseDTO<String> deleteActivity(@PathVariable Long id) {
        return activityService.deleteActivity(id);
    }



    @ApiOperation(value = "根据id查询参与活动信息", notes = "根据id查询参与活动信息 @author hxy")
    @GetMapping("/activity/relation/getById/{id}")
    public ResponseDTO<ActivityRelationResultDTO> queryActivityRelationById(@PathVariable Long id) {
        return activityRelationService.queryActivityRelationById(id);
    }

    @ApiOperation(value = "分页查询参与活动信息", notes = "分页查询参与活动信息 @author hxy")
    @PostMapping("/activity/relation/getByPage")
    public ResponseDTO<PageResultDTO<ActivityRelationResultDTO>> queryActivityRelationByPage(@RequestBody @Valid ActivityRelationQueryDTO queryDTO) {
        return activityRelationService.queryActivityRelationByPage(queryDTO);
    }

    @ApiOperation(value = "报名活动", notes = "报名活动 @author hxy")
    @PostMapping("/activity/relation/add")
    public ResponseDTO<String> addActivityRelation(@RequestBody @Valid ActivityRelationAddDTO addDTO) {
        return activityRelationService.addActivityRelation(addDTO);
    }

    @ApiOperation(value = "审核活动报名", notes = "审核活动报名 @author hxy")
    @PostMapping("/activity/relation/approve")
    public ResponseDTO<String> approveActivityRelation(@RequestBody @Valid ActivityRelationUpdateDTO updateDTO) {
        return activityRelationService.approveActivityRelation(updateDTO);
    }

    @ApiOperation(value = "更新参与活动状态", notes = "更新参与活动状态 @author hxy")
    @PostMapping("/activity/relation/updateJoinStatus")
    public ResponseDTO<String> updateActivityRelationJoinStatus(@RequestBody @Valid ActivityRelationUpdateDTO updateDTO) {
        return activityRelationService.updateActivityRelationJoinStatus(updateDTO);
    }
}
