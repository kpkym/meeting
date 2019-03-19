package com.jsu.func.login.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author kpkym
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证
     */
    private String identification;

    /**
     * 工号
     */
    private String workNum;

    /**
     * 部门
     */
    private String department;

    /**
     * 职位
     */
    private String position;

    /**
     * 脸部数据
     */
    private byte[] face;

    /**
     * 签到次数
     */
    private Integer signNum;

    /**
     * 组织次数
     */
    private Integer organNum;

    /**
     * 参与次数
     */
    private Integer participateNum;
}
