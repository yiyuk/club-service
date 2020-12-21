package net.lab1024.smartadmin.module.system.position;

import io.swagger.annotations.ApiModelProperty;
import net.lab1024.smartadmin.common.anno.OperateLog;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.constant.SwaggerTagConst;
import net.lab1024.smartadmin.module.system.position.domain.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zzr
 */
@Api(tags = {SwaggerTagConst.Admin.MANAGER_JOB})
@OperateLog
@RestController
public class PositionController {

    @Autowired
    private PositionService positionService;

    @ApiOperation(value = "分页查询所有社团", notes = "分页查询所有社团 @author zzr")
    @PostMapping("/position/getListPage")
    public ResponseDTO<PageResultDTO<PositionResultVO>> getJobPage(@RequestBody @Valid PositionQueryDTO queryDTO) {
        return positionService.queryPositionByPage(queryDTO);
    }

    @ApiOperation(value = "添加社团", notes = "添加社团 @author zzr")
    @PostMapping("/position/add")
    public ResponseDTO<String> addJob(@RequestBody @Valid PositionAddDTO addDTO) {
        return positionService.addPosition(addDTO);
    }

    @ApiOperation(value = "更新社团", notes = "更新社团 @author zzr")
    @PostMapping("/position/update")
    public ResponseDTO<String> updateJob(@RequestBody @Valid PositionUpdateDTO updateDTO) {
        return positionService.updatePosition(updateDTO);
    }

    @ApiOperation(value = "根据ID查询社团", notes = "根据ID查询社团 @author zzr")
    @GetMapping("/position/queryById/{id}")
    public ResponseDTO<PositionResultVO> queryJobById(@PathVariable Long id) {
        return positionService.queryPositionById(id);
    }

    @ApiOperation(value = "根据ID删除社团", notes = "根据ID删除社团 @author zzr")
    @GetMapping("/position/remove/{id}")
    public ResponseDTO<String> removeJob(@PathVariable Long id) {
        return positionService.removePosition(id);
    }


    @ApiModelProperty(value = "用户入团退团申请", notes = "用户入团退团申请 @author hxy")
    @PostMapping("/position/applyPositionRelation")
    public ResponseDTO<String> applyPositionRelation(@RequestBody @Valid PositionRelationUpdateDTO updateDTO){
        return positionService.applyPositionRelation(updateDTO);
    }

    @ApiModelProperty(value = "审批用户入团退团申请", notes = "审批用户入团退团申请 @author hxy")
    @PostMapping("/position/approvePositionRelation")
    public ResponseDTO<String> approvePositionRelation(@RequestBody @Valid PositionRelationUpdateDTO updateDTO){
        return positionService.approvePositionRelation(updateDTO);
    }
}
