package net.lab1024.smartadmin.module.business.news.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.domain.PageParamDTO;

import javax.validation.constraints.NotBlank;

/**
 * @author yiyuzi
 * @date 2020/12/22 18:46
 */

@Data
public class NewsQueryDTO  extends PageParamDTO {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("新闻标题")
    private String newsTitle;

    @ApiModelProperty("所属社团ID")
    private Long positionId;

    @ApiModelProperty("作者ID")
    private Long authorId;

    @ApiModelProperty("发布者ID")
    private Long employeeId;

    @ApiModelProperty("新闻状态，是否发布")
    private Integer status;

    public NewsQueryDTO() {
    }
}
