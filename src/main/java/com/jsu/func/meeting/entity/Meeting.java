package com.jsu.func.meeting.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jsu.func.login.entity.User;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author kpkym
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meeting implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 会议标题
     */
    private String title;

    /**
     * 会议主持人
     */
    private Integer host;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date start;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date end;

    /**
     * 会议细节
     */
    private String detail;

    private Integer rid;

    /**
     * 会议参与者
     */
    private String uids;

    /////////////////////////
    @TableField(exist=false)
    private User hoster;
    @TableField(exist=false)
    private List<User> participants;
    @TableField(exist=false)
    private Room room;
}
