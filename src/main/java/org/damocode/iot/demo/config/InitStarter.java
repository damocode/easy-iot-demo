package org.damocode.iot.demo.config;

import cn.hutool.core.util.IdUtil;
import io.vertx.core.Vertx;
import io.vertx.mqtt.MqttClientOptions;
import lombok.extern.slf4j.Slf4j;
import org.damocode.iot.core.device.*;
import org.damocode.iot.core.message.codec.DefaultTransport;
import org.damocode.iot.core.message.codec.MqttMessage;
import org.damocode.iot.core.message.codec.SimpleMqttMessage;
import org.damocode.iot.core.protocol.CompositeProtocolSupport;
import org.damocode.iot.core.protocol.ProtocolSupport;
import org.damocode.iot.demo.protocol.http.HttpDeviceMessageCodec;
import org.damocode.iot.demo.protocol.mqtt.MqttDeviceMessageCodec;
import org.damocode.iot.demo.protocol.tcp.radar.RadarDeviceMessageCodec;
import org.damocode.iot.device.message.DeviceMessageConnector;
import org.damocode.iot.network.http.gateway.HttpServerDeviceGateway;
import org.damocode.iot.network.http.server.HttpServer;
import org.damocode.iot.network.http.server.vertx.HttpServerConfig;
import org.damocode.iot.network.http.server.vertx.HttpServerProvider;
import org.damocode.iot.network.mqtt.client.MqttClient;
import org.damocode.iot.network.mqtt.client.MqttClientProperties;
import org.damocode.iot.network.mqtt.client.MqttClientProvider;
import org.damocode.iot.network.mqtt.device.MqttClientDeviceGateway;
import org.damocode.iot.network.tcp.device.TcpServerDeviceGateway;
import org.damocode.iot.network.tcp.parser.DirectRecordParser;
import org.damocode.iot.network.tcp.server.TcpServer;
import org.damocode.iot.network.tcp.server.TcpServerProperties;
import org.damocode.iot.network.tcp.server.TcpServerProvider;
import org.damocode.iot.starter.autoconfigure.DefaultDeviceSessionManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 启动初始化
 * @Author: zzg
 * @Date: 2021/10/7 17:46
 * @Version: 1.0.0
 */
@Slf4j
@Component
public class InitStarter implements CommandLineRunner {

    @Resource
    private TcpServerProvider tcpServerProvider;

    @Resource
    private MqttClientProvider mqttClientProvider;

    @Resource
    private HttpServerProvider httpServerProvider;

    @Resource
    private DefaultDeviceSessionManager deviceSessionManager;

    @Resource
    private DeviceOperatorManager deviceOperatorManager;

    @Resource
    private DeviceMessageConnector deviceMessageConnector;

    @Resource
    private Vertx vertx;

    public void run(String... args) {
        CompositeProtocolSupport support = new CompositeProtocolSupport();
        support.setId("demo-v1");
        support.setName("demo 协议");

        support.addMessageCodecSupport(DefaultTransport.TCP, () -> new RadarDeviceMessageCodec());
        support.addMessageCodecSupport(DefaultTransport.MQTT,() -> new MqttDeviceMessageCodec());
        support.addMessageCodecSupport(DefaultTransport.HTTP,() -> new HttpDeviceMessageCodec());

        support.addAuthenticator(DefaultTransport.MQTT, request -> {
            MqttAuthenticationRequest mqttRequest = ((MqttAuthenticationRequest) request);
            if (mqttRequest.getUsername().equals("admin") && mqttRequest.getPassword().equals("admin")) {
                return AuthenticationResponse.success();
            }
            return AuthenticationResponse.error(400, "密码错误");
        });

        startupTcp(support);
        startupMqttClient(support);
//        startupMqttClient();
        Map<String,ProtocolSupport> protocolMap = new HashMap<>();
        protocolMap.put("/v1/water/meter/property",support);
        startupHttpServer(protocolMap);
    }

    public void startupTcp(ProtocolSupport protocolSupport) {
        TcpServerProperties properties = TcpServerProperties.builder()
                .id(IdUtil.fastUUID())
                .port(8888)
                .host("0.0.0.0")
                .parserSupplier(() -> new DirectRecordParser())
                .build();
        TcpServer tcpServer = tcpServerProvider.createNetwork(properties);

        TcpServerDeviceGateway deviceGateway = new TcpServerDeviceGateway(tcpServer,
                deviceSessionManager,
                protocolSupport,
                deviceOperatorManager,
                deviceMessageConnector);
        deviceGateway.startup();
    }

    // 测试方法
    public void startupMqttClient() {
        MqttClientOptions options = new MqttClientOptions();
//        options.setKeepAliveInterval(1);
//        options.setAutoKeepAlive(true);
        io.vertx.mqtt.MqttClient client = io.vertx.mqtt.MqttClient.create(vertx, options);
        client.connect(MqttClientOptions.DEFAULT_PORT, "10.4.56.45", handler -> {
            AtomicInteger pongs = new AtomicInteger();
            client.subscribe("demo/plc1", 0);
            AtomicInteger count = new AtomicInteger();
            client.publishHandler(msg -> {
//                log.debug("topic: " + msg.topicName());
                MqttMessage mqttMessage = SimpleMqttMessage.builder().messageId(msg.messageId()).topic(msg.topicName()).payload(msg.payload().getByteBuf()).dup(msg.isDup()).retain(msg.isRetain()).qosLevel(msg.qosLevel().value()).build();
                log.debug("handle mqtt message \n{}", mqttMessage);
            });
            client.pingResponseHandler(v -> pongs.incrementAndGet());
            client.closeHandler(v -> log.debug("client close"));
        });
    }

    public void startupMqttClient(ProtocolSupport protocolSupport) {

        MqttClientOptions options = new MqttClientOptions();
        options.setClientId("mqtt-test-0311");
//        options.setKeepAliveInterval(1);
//        options.setAutoKeepAlive(true);

        // EMQ地址及端口
        MqttClientProperties properties = MqttClientProperties.builder()
                .id(IdUtil.fastUUID())
                .options(options)
                .host("10.4.56.45")
                .port(1883)
                .build();

        MqttClient mqttClient = mqttClientProvider.createNetwork(properties);
        List<String> topics = new ArrayList<>();
        topics.add("demo/plc5");
        MqttClientDeviceGateway deviceGateway = new MqttClientDeviceGateway(IdUtil.fastUUID(), mqttClient, deviceOperatorManager, deviceSessionManager,protocolSupport, deviceMessageConnector,topics);
        deviceGateway.startup();
    }

    public void startupHttpServer(Map<String,ProtocolSupport> protocolSupportMap) {
        HttpServerConfig config = new HttpServerConfig();
        config.setPort(8089);
        HttpServer httpServer = httpServerProvider.createNetwork(config);
        HttpServerDeviceGateway deviceGateway = new HttpServerDeviceGateway(IdUtil.fastUUID(),
                httpServer,
                deviceSessionManager,
                deviceOperatorManager,
                protocolSupportMap,
                deviceMessageConnector);
        deviceGateway.startup();
    }

}
