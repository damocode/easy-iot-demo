package org.damocode.iot.demo.protocol.tcp.radar;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.damocode.iot.core.message.DeviceMessage;
import org.damocode.iot.demo.protocol.tcp.TcpDeviceMessage;
import org.damocode.iot.demo.protocol.tcp.TcpPayload;
import org.damocode.iot.utils.BytesUtils;
import org.damocode.iot.utils.DateTimeUtils;

import java.io.Serializable;

/**
 * @Description: 雷达水位流速属性消息
 * @Author: zzg
 * @Date: 2021/10/7 17:48
 * @Version: 1.0.0
 */
@Slf4j
@Data
public class RadarPropertyMessage implements TcpPayload, TcpDeviceMessage, Serializable {

    private static final long serialVersionUID = 7203227271660605141L;

    private int start;

    // 站号
    private String deviceId;

    // 时间
    private String tm;

    // 雷达水位
    private String uz;

    // 流量
    private String uq;

    // 流速
    private String uv;

    // 面积
    private String ua;

    // 左水边
    private String uLeftWaterSide;

    // 右水边
    private String uRightWaterSide;

    // 电压
    private String voltage;

    // 1#分流速
    private String tmpV;

    // 1#信号
    private String csq;

    @Override
    public void fromByte(byte[] bytes, int offset) {
        this.deviceId = HexUtil.toHex(BytesUtils.beToInt(bytes, offset, 4));
        log.info("radar deviceId:" + deviceId);
        String smStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 4, 6));
        if(StringUtils.isNotBlank(smStr)){
            this.tm = DateTimeUtils.format(DateTimeUtils.formatDateString(smStr,"yyMMddHHmm"),"yyyy-MM-dd HH:mm");
        }
        String uzStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 10,3));
        if(StringUtils.isNotBlank(uzStr)){
            this.uz =  String.valueOf(NumberUtil.div(Integer.parseInt(uzStr),100,2));
        }
        String uqStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 13,4));
        if(StringUtils.isNotBlank(uqStr)){
            this.uq = String.valueOf(NumberUtil.div(Integer.parseInt(uqStr),1000,3));
        }
        String uvStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 17,3));
        if(StringUtils.isNotBlank(uvStr)){
            this.uv = String.valueOf(NumberUtil.div(Integer.parseInt(uvStr),1000,3));
        }
        String uaStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 20,4));
        if(StringUtils.isNotBlank(uaStr)){
            this.ua = String.valueOf(NumberUtil.div(Integer.parseInt(uaStr),1000,3));
        }
        String uLeftWaterSideStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 24,3));
        if(StringUtils.isNotBlank(uLeftWaterSideStr)){
            this.uLeftWaterSide = String.valueOf(NumberUtil.div(Integer.parseInt(uLeftWaterSideStr),100,2));
        }
        String uRightWaterSideStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 24,3));
        if(StringUtils.isNotBlank(uRightWaterSideStr)){
            this.uRightWaterSide = String.valueOf(NumberUtil.div(Integer.parseInt(uRightWaterSideStr),100,2));
        }
        String voltageStr = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + 30,2));
        if(StringUtils.isNotBlank(voltageStr)){
            this.voltage = String.valueOf(NumberUtil.div(Integer.parseInt(voltageStr),100,2));
        }
        this.tmpV = JSONUtil.toJsonStr(decodeArr(bytes,offset + 32,10,3,1000));
        this.csq = JSONUtil.toJsonStr(decodeArr(bytes,offset + 62,10,2,100));
    }

    private String[] decodeArr(byte[] bytes,int offset,int size, int len,int percent) {
        String[] str = new String[size];
        for(int i =0 ; i< size; i++) {
            String tmp = HexUtil.toHex(BytesUtils.beToInt(bytes,offset + i * len,len));
            str[i] = String.valueOf(NumberUtil.div(Integer.parseInt(tmp),percent,len));
        }
        return str;
    }

    @Override
    public DeviceMessage toDeviceMessage() {
        RadarReportPropertyMessage message = new RadarReportPropertyMessage();
        message.setDeviceId(deviceId);
        message.setRadarPropertyMessage(this);
        return message;
    }

}

