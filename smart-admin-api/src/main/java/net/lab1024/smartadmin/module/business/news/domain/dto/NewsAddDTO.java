package net.lab1024.smartadmin.module.business.news.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yiyuzi
 * @date 2020/12/22 18:23
 */

@Data
public class NewsAddDTO {

    @ApiModelProperty("新闻标题")
    @NotBlank(message = "新闻标题不能为空")
    private String newsTitle;

    @ApiModelProperty("新闻内容")
    @NotBlank(message = "新闻内容不能为空")
    private String newsText;

    @ApiModelProperty("所属社团ID")
    private Long positionId;

    @ApiModelProperty("作者ID")
    private Long authorId;

    @ApiModelProperty("发布者ID")
    private Long employeeId;

    @ApiModelProperty("新闻状态，是否发布")
    private Integer status;

    public NewsAddDTO() {
    }
}
