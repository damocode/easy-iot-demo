package org.damocode.iot.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 雷达水位分流速数据表
 * @Author: zzg
 * @Date: 2021/10/29 10:52
 * @Version: 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IotRadarPropertiesTv implements Serializable {

    private static final long serialVersionUID = -3185604082016559283L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 主表id
     */
    private Long pid;

    /**
     * 站号
     */
    private String stcd;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 1#分流速
     */
    private Double tv;

    /**
     * 1#信号
     */
    private Double csq;

    /**
     * 创建时间
     */
    private Date createTime;

}
