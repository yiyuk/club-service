package net.lab1024.smartadmin.module.business.news.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author yiyuzi
 * @date 2020/12/22 18:47
 */

@Data
public class NewsResultDTO {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("新闻标题")
    @NotBlank(message = "新闻标题不能为空")
    private String newsTitle;

    @ApiModelProperty("新闻内容")
    @NotBlank(message = "新闻内容不能为空")
    private String newsText;

    @ApiModelProperty("所属社团ID")
    private Long positionId;

    @ApiModelProperty("社团名称")
    private String positionName;

    @ApiModelProperty("作者ID")
    private Long authorId;

    @ApiModelProperty("作者姓名")
    private String actualName;

    @ApiModelProperty("发布者ID")
    private Long employeeId;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty("新闻状态，是否发布")
    private Integer status;

    public NewsResultDTO() {
    }

    public NewsResultDTO(@NotBlank(message = "新闻标题不能为空") String newsTitle, @NotBlank(message = "新闻内容不能为空") String newsText, Long positionId, Long authorId, Long employeeId) {
        this.newsTitle = newsTitle;
        this.newsText = newsText;
        this.positionId = positionId;
        this.authorId = authorId;
        this.employeeId = employeeId;
    }
}
