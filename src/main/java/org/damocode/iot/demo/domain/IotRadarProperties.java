package org.damocode.iot.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 雷达水位流速数据表
 * @Author: zzg
 * @Date: 2021/10/29 10:45
 * @Version: 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IotRadarProperties implements Serializable {

    private static final long serialVersionUID = -6873163802489120120L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 站号
     */
    private String stcd;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 时间
     */
    private Date tm;

    /**
     * 雷达水位
     */
    private Double uz;

    /**
     * 流量
     */
    private Double uq;

    /**
     * 流速
     */
    private Double uv;

    /**
     * 面积
     */
    private Double ua;

    /**
     * 左水边
     */
    private Double uLeftWaterSide;

    /**
     * 右水边
     */
    private Double uRightWaterSide;

    /**
     * 电压
     */
    private Double voltage;

    /**
     * 创建时间
     */
    private Date createTime;
}
