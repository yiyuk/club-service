package net.lab1024.smartadmin.module.business.fund;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.smartadmin.common.anno.NoNeedLogin;
import net.lab1024.smartadmin.common.anno.OperateLog;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.constant.SwaggerTagConst;
import net.lab1024.smartadmin.module.business.fund.domain.dto.FundAddDTO;
import net.lab1024.smartadmin.module.business.fund.domain.dto.FundQueryDTO;
import net.lab1024.smartadmin.module.business.fund.domain.dto.FundResultDTO;
import net.lab1024.smartadmin.module.business.fund.domain.dto.FundUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yiyuzi
 * @date 2020/12/24 1:05
 */

@Api(tags = {SwaggerTagConst.Club.COMMON_FUND})
@OperateLog
@RestController
public class FundController {
    @Autowired
    FundService fundService;

    @ApiOperation(value = "根据id查询经费申请信息", notes = "根据id查询经费申请信息 @author hxy")
    @GetMapping("/fund/getById/{id}")
    public ResponseDTO<FundResultDTO> getFundById(@PathVariable Long id) {
        return fundService.queryFundById(id);
    }

    @ApiOperation(value = "分页查询经费申请信息", notes = "分页查询经费申请信息 @author hxy")
    @PostMapping("/fund/getListPage")
    public ResponseDTO<PageResultDTO<FundResultDTO>> getApproveListPage(@RequestBody @Valid FundQueryDTO queryDTO) {
        return fundService.queryFundByPage(queryDTO);
    }

    @ApiOperation(value = "提交经费申请", notes = "提交经费申请 @author hxy")
    @PostMapping("/fund/add")
    public ResponseDTO<String> addApprove(@RequestBody @Valid FundAddDTO addDTO) {
        return fundService.addFund(addDTO);
    }

    @ApiOperation(value = "审核经费申请", notes = "审核经费申请 @author hxy")
    @PostMapping("/fund/approve")
    public ResponseDTO<String> approveActivity(@RequestBody @Valid FundUpdateDTO updateDTO) {
        return fundService.approveFund(updateDTO);
    }
}
