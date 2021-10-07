package org.damocode.iot.demo.protocol.tcp;

import org.damocode.iot.core.message.DeviceMessage;

/**
 * @Description: Tcp设备消息
 * @Author: zzg
 * @Date: 2021/10/7 17:47
 * @Version: 1.0.0
 */
public interface TcpDeviceMessage {

    DeviceMessage toDeviceMessage();

}
