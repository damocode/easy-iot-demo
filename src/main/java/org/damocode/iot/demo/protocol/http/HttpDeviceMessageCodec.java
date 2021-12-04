package org.damocode.iot.demo.protocol.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.damocode.iot.core.message.DeviceMessage;
import org.damocode.iot.core.message.codec.*;
import org.damocode.iot.core.message.codec.http.HttpExchangeMessage;
import org.damocode.iot.core.message.codec.http.SimpleHttpResponseMessage;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

/**
 * @Description: Http设备消息编码及解码器
 * @Author: zzg
 * @Date: 2021/10/27 9:18
 * @Version: 1.0.0
 */
@Slf4j
public class HttpDeviceMessageCodec implements DeviceMessageCodec {
    @Override
    public Transport getSupportTransport() {
        return DefaultTransport.HTTP;
    }

    @Override
    public DeviceMessage decode(MessageDecodeContext context) {
        HttpExchangeMessage message = (HttpExchangeMessage) context.getMessage();
        String topic = message.getUrl();
        log.info("http topic: " + topic);
        JSONObject payload = JSON.parseObject(message.getPayload().toString(StandardCharsets.UTF_8));
        String deviceId = payload.getString("MeterAddr");
        log.info("http message: " + payload.toJSONString());
        DeviceMessage deviceMessage = doDecode(deviceId,topic,payload);
        if(deviceMessage == null){
            message.response(SimpleHttpResponseMessage
                    .builder()
                    .status(404)
                    .contentType(MediaType.APPLICATION_JSON)
                    .payload(Unpooled.wrappedBuffer("{\"success\":false}".getBytes()))
                    .build());
            return null;
        }
        message.response(SimpleHttpResponseMessage
                .builder()
                .status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .payload(Unpooled.wrappedBuffer("{\"success\":true}".getBytes()))
                .build());
        return deviceMessage;
    }

    private DeviceMessage doDecode(String deviceId, String topic, JSONObject payload) {
        return null;
    }

    @Override
    public EncodedMessage encode(MessageEncodeContext messageEncodeContext) {
        return null;
    }
}
