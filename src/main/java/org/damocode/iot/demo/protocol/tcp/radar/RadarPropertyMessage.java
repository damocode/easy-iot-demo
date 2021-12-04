package org.damocode.iot.demo.protocol.tcp.radar;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.NumberUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.damocode.iot.core.message.DeviceMessage;
import org.damocode.iot.core.message.property.ReportPropertyMessage;
import org.damocode.iot.core.utils.BytesUtils;
import org.damocode.iot.core.utils.DateTimeUtils;
import org.damocode.iot.demo.domain.IotRadarProperties;
import org.damocode.iot.demo.domain.IotRadarPropertiesTv;
import org.damocode.iot.demo.protocol.MessageIdConst;
import org.damocode.iot.demo.protocol.tcp.TcpDeviceMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 雷达水位流速属性消息
 * @Author: zzg
 * @Date: 2021/10/7 17:48
 * @Version: 1.0.0
 */
@Slf4j
@Data
public class RadarPropertyMessage implements TcpDeviceMessage, Serializable {

    private static final long serialVersionUID = 7203227271660605141L;

    private Double[] decodeArr(byte[] bytes,int offset,int size, int len,int percent) {
        Double[] result = new Double[size];
        for(int i =0 ; i< size; i++) {
            String tmp = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + i * len,len));
            result[i] = NumberUtil.div(Integer.parseInt(tmp),percent,len);
        }
        return result;
    }

    private IotRadarProperties decodeProperties(byte[] bytes, int offset) {
        IotRadarProperties properties = new IotRadarProperties();
        properties.setDeviceId(HexUtil.toHex(BytesUtils.beToInt(bytes, offset, 4)));
        String smStr = HexUtil.toHex(BytesUtils.beToLong(bytes,offset + 4, 6));
        if(StringUtils.isNotBlank(smStr)){
            properties.setTm(DateTimeUtils.formatDateString(smStr,"yyMMddHHmmss"));
        }
        String uzStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 10,3));
        if(StringUtils.isNotBlank(uzStr)){
            properties.setUz(NumberUtil.div(Integer.parseInt(uzStr),100,2));
        }
        String uqStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 13,4));
        if(StringUtils.isNotBlank(uqStr)){
            properties.setUq(NumberUtil.div(Integer.parseInt(uqStr),1000,3));
        }
        String uvStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 17,3));
        if(StringUtils.isNotBlank(uvStr)){
            properties.setUv(NumberUtil.div(Integer.parseInt(uvStr),1000,3));
        }
        String uaStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 20,4));
        if(StringUtils.isNotBlank(uaStr)){
            properties.setUa(NumberUtil.div(Integer.parseInt(uaStr),1000,3));
        }
        String uLeftWaterSideStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 24,3));
        if(StringUtils.isNotBlank(uLeftWaterSideStr)){
            properties.setULeftWaterSide(NumberUtil.div(Integer.parseInt(uLeftWaterSideStr),100,2));
        }
        String uRightWaterSideStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 24,3));
        if(StringUtils.isNotBlank(uRightWaterSideStr)){
            properties.setURightWaterSide(NumberUtil.div(Integer.parseInt(uRightWaterSideStr),100,2));
        }
        String voltageStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 30,2));
        if(StringUtils.isNotBlank(voltageStr)){
            properties.setVoltage(NumberUtil.div(Integer.parseInt(voltageStr),100,2));
        }
        return properties;
    }

    private List<IotRadarPropertiesTv> decodePropertiesTv(byte[] bytes, int offset, String deviceId) {
        List<IotRadarPropertiesTv> result = new ArrayList<>();
        Double[] tvs = decodeArr(bytes,offset + 32,10,3,1000);
        Double[] csqs = decodeArr(bytes,offset + 62,10,2,100);
        IotRadarPropertiesTv radarPropertiesTv;
        for(int i = 0 ; i< tvs.length; i++){
            radarPropertiesTv = new IotRadarPropertiesTv();
            radarPropertiesTv.setTv(tvs[i]);
            radarPropertiesTv.setDeviceId(deviceId);
            radarPropertiesTv.setCsq(csqs[i]);
            result.add(radarPropertiesTv);
        }
        return result;
    }

    @Override
    public DeviceMessage toDeviceMessage(byte[] bytes, int offset) {
        RadarReportProperty reportProperty = new RadarReportProperty();
        IotRadarProperties properties = decodeProperties(bytes,offset);
        String deviceId = properties.getDeviceId();
        reportProperty.setRadarProperties(properties);
        reportProperty.setIotRadarPropertiesTvs(decodePropertiesTv(bytes,offset,deviceId));

        ReportPropertyMessage message = ReportPropertyMessage.create();
        message.setDeviceId(deviceId);
        message.setData(reportProperty);
        message.setMessageId(MessageIdConst.RADAR_REPORT_MESSAGE_ID);

        return message;
    }

}

