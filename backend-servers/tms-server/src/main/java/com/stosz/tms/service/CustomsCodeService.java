package com.stosz.tms.service;

import com.google.common.collect.Lists;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import com.stosz.tms.mapper.CustomsCodeMapper;
import com.stosz.tms.mapper.DistrictCodeMapper;
import com.stosz.tms.model.CustomsCode;
import com.stosz.tms.model.DistrictCode;
import com.stosz.tms.vo.CustomsCodeExportVo;
import com.stosz.tms.vo.DistrictCodeExportVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CustomsCodeService {

    @Resource
    private CustomsCodeMapper mapper;

    @Resource
    private IZoneService zoneService;

    public int count(CustomsCode customsCode) {
        return mapper.count(customsCode);
    }

    public List<CustomsCode> queryList(CustomsCode customsCode) {
        if (!StringUtils.hasText(customsCode.getOrderBy())) {
            customsCode.setOrderBy(" update_at desc,create_at");
        }
        return mapper.queryList(customsCode);
    }



    @Transactional(transactionManager = "tmsTransactionManager",rollbackFor = Exception.class)
    public List<CustomsCodeExportVo> importExeclData(List<List<String>> importDatas){

        final List<Zone> zones = zoneService.findAll();

        CustomsCode paramBean = new CustomsCode();
        paramBean.setStart(0);
        paramBean.setLimit(Integer.MAX_VALUE);

        final List<CustomsCode> customsCodes = mapper.queryList(paramBean);

        final Map<String, List<Zone>> zoneMapByName = zones.stream().collect(Collectors.groupingBy(Zone::getTitle));
        final Map<String, List<CustomsCode>> codeMapByZoneName = customsCodes.stream().collect(Collectors.groupingBy(CustomsCode::getZoneName));


        List<CustomsCodeExportVo> customsCodeExportVos = Lists.newArrayList();


        List<CustomsCode> customsCodeList = Lists.newArrayList();

        UserDto userDto= ThreadLocalUtils.getUser();

        Pattern pattern = Pattern.compile("^\\w+$");

        importDatas.forEach( e -> {
            CustomsCodeExportVo codeExportVo = new CustomsCodeExportVo();

            final String zoneName = e.get(0);
            final String sku = e.get(1);
            final String code = e.get(2);

            codeExportVo.setZoneName(zoneName);
            codeExportVo.setSku(sku);
            codeExportVo.setCode(code);

            if (StringUtils.isEmpty(zoneName)){
                codeExportVo.setFailMessage("国家不能为空");
                customsCodeExportVos.add(codeExportVo);
                return;
            }
            if (StringUtils.isEmpty(sku)){
                codeExportVo.setFailMessage("sku不能为空");
                customsCodeExportVos.add(codeExportVo);
                return;
            }
            if (StringUtils.isEmpty(code)){
                codeExportVo.setFailMessage("海关代码不能为空");
                customsCodeExportVos.add(codeExportVo);
                return;
            }

            if (code.length() > 30){
                codeExportVo.setFailMessage("海关代码的长度不能超过30个字符");
                customsCodeExportVos.add(codeExportVo);
                return;
            }

            if (!pattern.matcher(code).matches()) {
                codeExportVo.setFailMessage("海关代码仅可输入英文、数字、下划线。");
                customsCodeExportVos.add(codeExportVo);
                return;
            }

            final List<CustomsCode> codeByZoneNameList = codeMapByZoneName.get(zoneName);
            if (CollectionUtils.isNotNullAndEmpty(codeByZoneNameList)){
                final List<String> skuList = codeByZoneNameList.stream().map(CustomsCode::getSku).collect(Collectors.toList());

                if (skuList.contains(sku)){
                    codeExportVo.setFailMessage("该SKU已存在");
                    customsCodeExportVos.add(codeExportVo);
                    return;
                }
            }

            final List<Zone> zoneList = zoneMapByName.get(zoneName);

            if (CollectionUtils.isNullOrEmpty(zoneList)){
                codeExportVo.setFailMessage("该国家不存在");
                customsCodeExportVos.add(codeExportVo);
                return;
            }

            CustomsCode customsCode = new CustomsCode();
            customsCode.setCode(code);
            customsCode.setEnable(1);
            customsCode.setSku(sku);
            customsCode.setZoneId(zoneList.get(0).getId());
            customsCode.setZoneName(zoneName);
            customsCode.setCreator(userDto.getLastName());
            customsCode.setCreatorId(userDto.getId());

            customsCodeList.add(customsCode);

        });

        if (CollectionUtils.isNotNullAndEmpty(customsCodeList))
            Assert.isTrue( mapper.batchInsert(customsCodeList) == customsCodeList.size(),"保存海关代码失败");

        return customsCodeExportVos;
    }


    public void updateEnable(CustomsCode customsCode) {
        Assert.isTrue(mapper.update(customsCode) > 0, "更新海关代码状态失败!");
    }

    public void update(CustomsCode customsCode) {
        CustomsCode paramBean = new CustomsCode();
        paramBean.setZoneId(customsCode.getZoneId());
        paramBean.setSku(customsCode.getSku());

        List<CustomsCode> districtCodes = mapper.queryList(paramBean);

        if (CollectionUtils.isNotNullAndEmpty(districtCodes)) {
            districtCodes = districtCodes.stream().filter(e -> !e.getId().equals(customsCode.getId())).collect(Collectors.toList());

            if (CollectionUtils.isNotNullAndEmpty(districtCodes)) {
                {
                    final List<String> skus = districtCodes.stream().map(CustomsCode::getSku).collect(Collectors.toList());

                    if (skus.contains(customsCode.getSku())) {
                        throw new RuntimeException("同一国家下，sku已存在");
                    }
                }
            }
        }
        Assert.isTrue(mapper.update(customsCode) > 0, "更新海关代码失败!");
    }
}
