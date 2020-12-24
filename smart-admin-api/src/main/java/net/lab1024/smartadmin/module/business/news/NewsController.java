package net.lab1024.smartadmin.module.business.news;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.smartadmin.common.anno.NoNeedLogin;
import net.lab1024.smartadmin.common.anno.NoValidPrivilege;
import net.lab1024.smartadmin.common.anno.OperateLog;
import net.lab1024.smartadmin.common.domain.PageResultDTO;
import net.lab1024.smartadmin.common.domain.ResponseDTO;
import net.lab1024.smartadmin.constant.SwaggerTagConst;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsAddDTO;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsQueryDTO;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsResultDTO;
import net.lab1024.smartadmin.module.business.news.domain.dto.NewsUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yiyuzi
 * @date 2020/12/19 15:47
 */

@RestController
@OperateLog
@Api(tags = {SwaggerTagConst.Club.COMMON_NEWS})
public class NewsController {

    @Autowired
    private NewsService newsService;

    @ApiOperation(value = "分页查询",notes = "@author hxy")
    @PostMapping("/news/page/query")
    @NoValidPrivilege
    public ResponseDTO<PageResultDTO<NewsResultDTO>> queryByPage(@RequestBody @Validated NewsQueryDTO queryDTO) {
        return newsService.queryByPage(queryDTO);
    }

    @ApiOperation(value = "添加",notes = "@author hxy")
    @PostMapping("/news/add")
    public ResponseDTO<Long> add(@RequestBody @Valid NewsAddDTO addTO){
        return newsService.add(addTO);
    }

    @ApiOperation(value="修改",notes = "status 0未发布 1发布 @author hxy")
    @PostMapping("/news/update")
    public ResponseDTO<Long> update(@RequestBody @Valid NewsUpdateDTO updateDTO){
        return newsService.update(updateDTO);
    }

    @ApiOperation(value="删除",notes = "@author hxy")
    @GetMapping("/news/delete/{id}")
    public ResponseDTO<String> delete(@PathVariable("id") Long id){
        return newsService.delete(id);
    }

    @ApiOperation(value="详情",notes = "@author hxy")
    @GetMapping("/news/detail/{id}")
    @NoValidPrivilege
    public ResponseDTO<NewsResultDTO> detail(@PathVariable("id") Long id){
        return newsService.queryById(id);
    }
}
