package org.damocode.iot.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.damocode.iot.core.device.DeviceOperatorInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 站点对象
 * @Author: zzg
 * @Date: 2021/10/7 17:30
 * @Version: 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IotStation implements DeviceOperatorInfo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 站点类型id
     */
    private Long sttpId;

    /**
     * 站点名称
     */
    private String stnm;

    /**
     * 站点代码
     */
    private String stcd;

    /**
     * 经度
     */
    private String lgtd;

    /**
     * 纬度
     */
    private String lttd;

    /**
     * 站址
     */
    private String stlc;

    /**
     * 站点状态
     */
    private byte state;

    /**
     * 服务器ID
     */
    private String serverId;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 设备地址
     */
    private String address;

    /**
     * 设备上线时间
     */
    private Date onlineTime;

    /**
     * 设备下线时间
     */
    private Date offlineTime;


    @Override
    public String getDeviceId() {
        return getId();
    }
}

