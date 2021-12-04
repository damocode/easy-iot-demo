package org.damocode.iot.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.damocode.iot.demo.domain.IotRadarPropertiesTv;
import org.damocode.iot.demo.mapper.IotRadarPropertiesTvMapper;
import org.damocode.iot.demo.service.IIotRadarPropertiesTvService;
import org.springframework.stereotype.Service;

/**
 * @Description: 雷达水位分流速数据表实现类
 * @Author: zzg
 * @Date: 2021/10/29 11:03
 * @Version: 1.0.0
 */
@Service
public class IotRadarPropertiesTvService extends ServiceImpl<IotRadarPropertiesTvMapper, IotRadarPropertiesTv> implements IIotRadarPropertiesTvService {
}
