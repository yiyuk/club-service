package net.lab1024.smartadmin.module.system.position;

import net.lab1024.smartadmin.common.anno.NoNeedLogin;
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
import java.util.List;

/**
 * @author zzr
 */
@Api(tags = {SwaggerTagConst.Club.MANAGER_JOB})
@OperateLog
@RestController
public class PositionController {

    @Autowired
    private PositionService positionService;

    @ApiOperation(value = "分页查询所有社团", notes = "分页查询所有社团 @author zzr")
    @PostMapping("/position/getByPage")
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
    @GetMapping("/position/getById/{id}")
    public ResponseDTO<PositionResultVO> getPositionById(@PathVariable Long id) {
        return positionService.queryPositionById(id);
    }

    @ApiOperation(value = "删除社团", notes = "删除社团 @author zzr")
    @GetMapping("/position/delete/{id}")
    public ResponseDTO<String> deletePosition(@PathVariable Long id) {
        return positionService.removePosition(id);
    }




    @ApiOperation(value = "根据ID查询创建社团申请", notes = "根据ID查询创建社团申请 @author hxy")
    @GetMapping("/position/approve/getById/{id}")
    public ResponseDTO<PositionApproveResultDTO> getPositionApproveByID(@PathVariable Long id){
        return positionService.queryPositionApproveByID(id);
    }

    @ApiOperation(value = "查询社团审核状态", notes = "查询社团审核状态 @author hxy")
    @PostMapping("/position/approve/getList")
    public ResponseDTO<List<PositionApproveResultDTO>> getPositionApprove(@RequestBody @Valid PositionApproveQueryDTO queryDTO){
        return positionService.queryPositionApprove(queryDTO);
    }
@NoNeedLogin
    @ApiOperation(value = "提交创建社团申请", notes = "提交创建社团申请 @author hxy")
    @PostMapping("/position/approve/add")
    public ResponseDTO<String> addPositionApprove(@RequestBody @Valid PositionApproveAddDTO addDTO){
        return positionService.addPositionApprove(addDTO);
    }

    @ApiOperation(value = "更新社团审核状态", notes = "更新社团审核状态 @author hxy")
    @PostMapping("/position/approve/update")
     public ResponseDTO<String> updatePositionApprove(@RequestBody @Valid PositionApproveUpdateDTO updateDTO){
         return positionService.updatePositionApprove(updateDTO);
     }



    @ApiOperation(value = "根据ID查询社团-用户关联", notes = "根据ID查询社团-用户关联 @author hxy")
    @GetMapping("/position/relation/getById/{id}")
    public ResponseDTO<PositionRelationResultDTO> getRelationByID(@PathVariable Long id){
        return positionService.getRelationById(id);
    }

    @ApiOperation(value = "根据条件分页查询社团-用户关联", notes = "根据单一条件分页查询社团-用户关联 @author hxy")
    @PostMapping("/position/relation/getByPage")
    public ResponseDTO<PageResultDTO<PositionRelationResultDTO>> getRelationByPage(@RequestBody @Valid PositionRelationQueryDTO queryDTO) {
        return positionService.queryRelationByPage(queryDTO);
    }

    @ApiOperation(value = "用户入团退团申请", notes = "用户入团退团申请 @author hxy")
    @PostMapping("/position/relation/apply")
    public ResponseDTO<String> applyPositionRelation(@RequestBody @Valid PositionRelationUpdateDTO updateDTO){
        return positionService.applyPositionRelation(updateDTO);
    }

    @ApiOperation(value = "审批用户入团退团申请", notes = "审批用户入团退团申请 @author hxy")
    @PostMapping("/position/relation/approve")
    public ResponseDTO<String> approvePositionRelation(@RequestBody @Valid PositionRelationUpdateDTO updateDTO){
        return positionService.approvePositionRelation(updateDTO);
    }
}
