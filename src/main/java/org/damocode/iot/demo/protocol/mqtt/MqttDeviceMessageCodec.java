package org.damocode.iot.demo.protocol.mqtt;

import cn.hutool.core.util.HexUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.damocode.iot.core.message.DeviceMessage;
import org.damocode.iot.core.message.DisconnectDeviceMessage;
import org.damocode.iot.core.message.Message;
import org.damocode.iot.core.message.codec.*;
import org.damocode.iot.demo.protocol.TopicMessage;

import java.nio.charset.StandardCharsets;

/**
 * @Description: Mqtt设备消息编码器
 * @Author: zzg
 * @Date: 2021/10/15 15:00
 * @Version: 1.0.0
 */
@Slf4j
public class MqttDeviceMessageCodec implements DeviceMessageCodec {

    @Override
    public Transport getSupportTransport() {
        return DefaultTransport.MQTT;
    }

    @Override
    public DeviceMessage decode(MessageDecodeContext context) {
        FromDeviceMessageContext ctx = ((FromDeviceMessageContext) context);
        //转为mqtt消息
        MqttMessage mqttMessage = (MqttMessage) ctx.getMessage();
        String topic = mqttMessage.getTopic();
        //消息体转为json
        JSONObject payload = JSON.parseObject(mqttMessage.getPayload().toString(StandardCharsets.UTF_8));
        String deviceId = ctx.getDevice() != null ? ctx.getDevice().getDeviceId() : null;
        log.info("topic: " + topic);
        log.info("deviceId: " + deviceId);
        log.info("message: " + payload.toJSONString());
        ctx.getSession().send(SimpleMqttMessage.builder()
                                .topic("demo/plc4")
                                .qosLevel(2)
                                .payload(Unpooled.wrappedBuffer("test".getBytes()))
                                .build());
        return null;
    }

    @Override
    public EncodedMessage encode(MessageEncodeContext context) {
        Message message = context.getMessage();
        if (message instanceof DeviceMessage) {
            if (message instanceof DisconnectDeviceMessage) {
                 ((ToDeviceMessageContext) context).disconnect();
                 return null;
            }
            TopicMessage msg = doEncode((DeviceMessage) message);
            if(msg != null){
                return SimpleMqttMessage.builder()
                        .topic(msg.getTopic())
                        .payload(Unpooled.wrappedBuffer(JSON.toJSONBytes(msg.getMessage())))
                        .build();
            }
        }
        return null;
    }

    protected TopicMessage doEncode(DeviceMessage message) {
        return new TopicMessage("demo/plc4",JSON.toJSON(message));
    }
}
