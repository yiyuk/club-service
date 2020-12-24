package net.lab1024.smartadmin.module.business.news.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
import net.lab1024.smartadmin.common.constant.JudgeEnum;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author yiyuzi
 * @date 2020/12/22 18:47
 */

@Data
public class NewsUpdateDTO {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("新闻标题")
    @NotBlank(message = "新闻标题不能为空")
    private String newsTitle;

    @ApiModelProperty("新闻内容")
    @NotBlank(message = "新闻内容不能为空")
    private String newsText;

    @ApiModelPropertyEnum(JudgeEnum.class)//("新闻状态，是否发布")
    private Integer status;

    public NewsUpdateDTO() {
    }
}
