package net.lab1024.smartadmin.module.business.news.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.smartadmin.common.domain.BaseEntity;

/**
 * @author yiyuzi
 * @date 2020/12/22 18:22
 */

@Data
@TableName("t_news")
public class NewsEntity extends BaseEntity {
    /**
     * 新闻标题
     */
    private String newsTitle;
    /**
     * 新闻内容
     */
    private String newsText;
    /**
     * 所属社团ID
     */
    private Long positionId;
    /**
     * 作者ID
     */
    private Long authorId;
    /**
     * 发布者ID
     */
    private Long employeeId;
    /**
     * 新闻状态
     */
    private Integer status;
}
