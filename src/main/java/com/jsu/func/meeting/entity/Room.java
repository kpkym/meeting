package com.jsu.func.meeting.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 会议室名
     */
    private String name;

    /**
     * 会议室地点
     */
    private String location;

    /**
     * 会议室规模
     */
    private String scale;

    /**
     * 会议室座位
     */
    private Integer seats;

    /**
     * 是否有投影仪 0没有 1有
     */
    private Integer projector;

    private String img;

}
