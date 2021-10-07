package org.damocode.iot.demo.protocol.tcp.radar;

import lombok.Data;
import org.damocode.iot.core.message.CommonDeviceMessage;

/**
 * @Description: 雷达水位流速属性上报消息
 * @Author: zzg
 * @Date: 2021/10/7 17:49
 * @Version: 1.0.0
 */
@Data
public class RadarReportPropertyMessage extends CommonDeviceMessage {

    private RadarPropertyMessage radarPropertyMessage;

}
