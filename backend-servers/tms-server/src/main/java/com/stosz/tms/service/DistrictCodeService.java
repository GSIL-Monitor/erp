package com.stosz.tms.service;

import com.google.common.collect.Lists;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import com.stosz.tms.mapper.DistrictCodeMapper;
import com.stosz.tms.model.DistrictCode;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.vo.DistrictCodeExportVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DistrictCodeService {

    @Resource
    private DistrictCodeMapper mapper;

    @Resource
    private IZoneService zoneService;

    public int count(DistrictCode districtCode) {
        return mapper.count(districtCode);
    }

    public List<DistrictCode> queryList(DistrictCode districtCode) {
        if (!StringUtils.hasText(districtCode.getOrderBy())) {
            districtCode.setOrderBy(" update_at desc,create_at");
        }
        return mapper.queryList(districtCode);
    }



    @Transactional(transactionManager = "tmsTransactionManager",rollbackFor = Exception.class)
    public List<DistrictCodeExportVo> importExeclData(List<List<String>> importDatas){

        final List<Zone> zones = zoneService.findAll();

        DistrictCode codeParamBean = new DistrictCode();
        codeParamBean.setStart(0);
        codeParamBean.setLimit(Integer.MAX_VALUE);

        final List<DistrictCode> districtCodes = mapper.queryList(codeParamBean);

        final Map<String, List<Zone>> zoneMapByName = zones.stream().collect(Collectors.groupingBy(Zone::getTitle));
        final Map<String, List<DistrictCode>> codeMapByZoneName = districtCodes.stream().collect(Collectors.groupingBy(DistrictCode::getZoneName));


        List<DistrictCodeExportVo> districtCodeExportVos = Lists.newArrayList();


        List<DistrictCode> districtCodeList = Lists.newArrayList();

        UserDto userDto= ThreadLocalUtils.getUser();

        importDatas.forEach( e -> {
            DistrictCodeExportVo codeExportVo = new DistrictCodeExportVo();

            final String zoneName = e.get(0);
            final String province = e.get(1);
            final String city = e.get(2);
            final String code = e.get(3);

            codeExportVo.setZoneName(zoneName);
            codeExportVo.setProvince(province);
            codeExportVo.setCode(code);
            codeExportVo.setCity(city);

            if (StringUtils.isEmpty(zoneName)){
                codeExportVo.setFailMessage("国家不能为空");
                districtCodeExportVos.add(codeExportVo);
                return;
            }
            if (StringUtils.isEmpty(province)){
                codeExportVo.setFailMessage("省不能为空");
                districtCodeExportVos.add(codeExportVo);
                return;
            }
            if (StringUtils.isEmpty(city)){
                codeExportVo.setFailMessage("城市不能为空");
                districtCodeExportVos.add(codeExportVo);
                return;
            }
            if (StringUtils.isEmpty(code)){
                codeExportVo.setFailMessage("城市代码不能为空");
                districtCodeExportVos.add(codeExportVo);
                return;
            }

            if (province.length() > 30){
                codeExportVo.setFailMessage("省份不能超过30字符");
                districtCodeExportVos.add(codeExportVo);
                return;
            }
            if (city.length() > 30){
                codeExportVo.setFailMessage("城市不能超过30字符");
                districtCodeExportVos.add(codeExportVo);
                return;
            }
            if (code.length() > 20){
                codeExportVo.setFailMessage("城市代码不能超过20字符");
                districtCodeExportVos.add(codeExportVo);
                return;
            }


            final List<DistrictCode> codeByZoneNameList = codeMapByZoneName.get(zoneName);
            if (CollectionUtils.isNotNullAndEmpty(codeByZoneNameList)){
                final Map<String, List<DistrictCode>> codeMapByProvince = codeByZoneNameList.stream().collect(Collectors.groupingBy(DistrictCode::getProvince));

                final List<DistrictCode> codeByProvinceList = codeMapByProvince.get(province);

                if (CollectionUtils.isNotNullAndEmpty(codeByProvinceList)){
                    final List<String> cityLists = codeByProvinceList.stream().map(DistrictCode::getCity).collect(Collectors.toList());
                    final List<String> codeLists = codeByProvinceList.stream().map(DistrictCode::getCode).collect(Collectors.toList());

                    if (cityLists.contains(city)){
                        codeExportVo.setFailMessage("在该省份下，已有该城市存在");
                        districtCodeExportVos.add(codeExportVo);
                        return;
                    }
                    if (codeLists.contains(code)){
                        codeExportVo.setFailMessage("在该省份下，已有该城市代码存在");
                        districtCodeExportVos.add(codeExportVo);
                        return;
                    }
                }
            }

            final List<Zone> zoneList = zoneMapByName.get(zoneName);

            if (CollectionUtils.isNullOrEmpty(zoneList)){
                codeExportVo.setFailMessage("该国家不存在");
                districtCodeExportVos.add(codeExportVo);
                return;
            }

            DistrictCode districtCode = new DistrictCode();
            districtCode.setCity(city);
            districtCode.setCode(code);
            districtCode.setEnable(1);
            districtCode.setProvince(province);
            districtCode.setZoneId(zoneList.get(0).getId());
            districtCode.setZoneName(zoneName);
            districtCode.setCreator(userDto.getLastName());
            districtCode.setCreatorId(userDto.getId());

            districtCodeList.add(districtCode);

        });

        if (CollectionUtils.isNotNullAndEmpty(districtCodeList))
            Assert.isTrue( mapper.batchInsert(districtCodeList) == districtCodeList.size(),"保存地区代码失败");

        return districtCodeExportVos;
    }


    public void updateEnable(DistrictCode districtCode) {
        Assert.isTrue(mapper.update(districtCode) > 0, "更新地区代码状态失败!");
    }

    public void update(DistrictCode districtCode) {
        DistrictCode paramBean = new DistrictCode();
        paramBean.setZoneId(districtCode.getZoneId());
        paramBean.setProvince(districtCode.getProvince());

        List<DistrictCode> districtCodes = mapper.queryList(paramBean);

        if (CollectionUtils.isNotNullAndEmpty(districtCodes)) {
            districtCodes = districtCodes.stream().filter(e -> !e.getId().equals(districtCode.getId())).collect(Collectors.toList());

            if (CollectionUtils.isNotNullAndEmpty(districtCodes)) {
                {
                    final List<String> citys = districtCodes.stream().map(DistrictCode::getCity).collect(Collectors.toList());
                    final List<String> codes = districtCodes.stream().map(DistrictCode::getCode).collect(Collectors.toList());

                    if (citys.contains(districtCode.getCity())) {
                        throw new RuntimeException("城市已存在");
                    }
                    if (codes.contains(districtCode.getCode())) {
                        throw new RuntimeException("城市代码已存在");
                    }

                }

            }
        }
        Assert.isTrue(mapper.update(districtCode) > 0, "更新地区代码失败!");
    }


}
