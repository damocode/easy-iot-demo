package org.damocode.iot.demo.protocol.tcp.radar;

import cn.hutool.core.util.HexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.damocode.iot.core.message.DeviceMessage;
import org.damocode.iot.core.message.Headers;
import org.damocode.iot.core.message.codec.*;
import org.damocode.iot.core.server.session.DeviceSession;
import org.damocode.iot.core.utils.BytesUtils;

/**
 * @Description: 雷达水位流速设备消息编码器
 * @Author: zzg
 * @Date: 2021/10/7 17:48
 * @Version: 1.0.0
 */
@Slf4j
public class RadarDeviceMessageCodec implements DeviceMessageCodec {

    @Override
    public Transport getSupportTransport() {
        return DefaultTransport.TCP;
    }

    @Override
    public DeviceMessage decode(MessageDecodeContext context) {
        FromDeviceMessageContext ctx = ((FromDeviceMessageContext) context);
        ByteBuf byteBuf = ctx.getMessage().getPayload();
        byte[] payload = ByteBufUtil.getBytes(byteBuf, 0, byteBuf.readableBytes(), false);
        log.info("radar message: " + Hex.encodeHexString(payload));
        RadarPropertyMessage radarPropertyMessage = new RadarPropertyMessage();
        String beginStr = HexUtil.toHex(BytesUtils.beToInt(payload,0,1));
        DeviceSession session = ctx.getSession();
        if(!"5a".equalsIgnoreCase(beginStr)){
            session.ping();
            return null;
        }
        DeviceMessage deviceMessage = radarPropertyMessage.toDeviceMessage(payload,1);
        deviceMessage.addHeader(Headers.keepOnline,true);
        deviceMessage.addHeader(Headers.keepOnlineTimeoutSeconds,2 * 60);
        String hex = HexUtil.encodeHexStr("Water Receive OK");
        session.send(EncodedMessage.simple(Unpooled.wrappedBuffer(HexUtil.decodeHex(hex))));
        return deviceMessage;
    }

    @Override
    public EncodedMessage encode(MessageEncodeContext context) {
        return null;
    }

}

