package org.damocode.iot.demo.protocol.tcp;

/**
 * @Description:
 * @Author: zzg
 * @Date: 2021/10/7 17:47
 * @Version: 1.0.0
 */
public interface TcpPayload {

    void fromByte(byte[] bytes,int offset);

}

