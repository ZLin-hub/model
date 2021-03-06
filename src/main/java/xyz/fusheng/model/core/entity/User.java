/**
 * @FileName: User
 * @Author: code-fusheng
 * @Date: 2020/4/26 16:24
 * Description: 用户实体类
 */
package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Data
@TableName("sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = -2556069831033871039L;
    /**
     * 用户ID
     */
    @TableId
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 头像
     */
    private String header;

    /**
     * 签名
     */
    private String signature;

    /**
     * 介绍 描述
     */
    private String description;

    /**
     * realName 真实名称
     */
    private String realname;

    /**
     * 性别 0:私密 1:男 2:女
     */
    private Integer sex;

    /**
     * address 地址
     */
    private String address;

    /**
     * 创建时间
     */
    private String createdTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 是否启用，0否1是 默认1
     */
    private Integer isEnabled;

    /**
     * 是否删除，0否1是 默认0
     */
    private Integer isDeleted;

    /**
     * 用户角色id
     */
    @TableField(exist = false)
    private Long roleId;

    /**
     * 用户权限列表
     */
    @TableField(exist = false)
    private List<Menu> menuList;


}
