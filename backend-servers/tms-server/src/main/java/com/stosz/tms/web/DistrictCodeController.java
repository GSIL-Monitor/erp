package com.stosz.tms.web;

import com.google.common.collect.Lists;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.*;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.model.DistrictCode;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.service.DistrictCodeService;
import com.stosz.tms.vo.DistrictCodeExportVo;
import com.stosz.tms.vo.ShippingParcelListVo;
import com.stosz.tms.vo.TrackingTaskExportVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/tms/districtCode")
public class DistrictCodeController extends AbstractController {


    @Resource
    private DistrictCodeService service;


    @Resource
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "query",method = RequestMethod.POST)
    public RestResult queryList(DistrictCode districtCode){
        RestResult restResult = new RestResult();
        int count = service.count(districtCode);
        restResult.setTotal(count);
        if (count <= 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }

        final List<DistrictCode> districtCodes = service.queryList(districtCode);
        restResult.setItem(districtCodes);
        restResult.setCode(RestResult.OK);
        return restResult;
    }



    @RequestMapping(value = "/exportTemplate",method = RequestMethod.GET)
    public void exportTemplate(
            HttpServletResponse response
    ) throws Exception {
        List<DistrictCodeExportVo> exportVos = Lists.newArrayList();

        DistrictCodeExportVo districtCodeExportVo = new DistrictCodeExportVo();
        districtCodeExportVo.setCity("test");
        districtCodeExportVo.setCode("test");
        districtCodeExportVo.setProvince("test");
        districtCodeExportVo.setZoneName("test");

        exportVos.add(districtCodeExportVo);

        String[] headers = {"国家","省","城市","城市代码"};
        String[] fileds = {"zoneName","province","city","code"};

        ExcelUtil.exportExcelAndDownload(response,"地区代码导入模板","地区代码信息",headers,fileds,exportVos);

    }


    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public RestResult upload(
            @RequestParam(name = "file",required = true) MultipartFile file,
            HttpServletResponse response
    ) throws Exception {

        final List<List<String>> uploadData = ExeclRenderUtils.readExecl(file);

        final List<DistrictCodeExportVo> exportVos = service.importExeclData(uploadData);

        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);

        final String key = "districtCode_import_fail_" + new Date().getTime();

        if (CollectionUtils.isNotNullAndEmpty(exportVos)){
            redisTemplate.opsForValue().set(key, JsonUtils.toJson(exportVos),2, TimeUnit.MINUTES);
            restResult.setItem(key);
        }

        return restResult;
    }


    @RequestMapping(value = "/exportFailExecl",method = RequestMethod.POST)
    public void exportFailExecl(
            String key,
            HttpServletResponse response
    ) throws Exception {
        Assert.notNull(key,"key不能为空");

        if (redisTemplate.hasKey(key)){
            final String failDataJson = redisTemplate.opsForValue().get(key);
            final List failDataList = JsonUtils.jsonToObject(failDataJson, List.class);

            final List<DistrictCodeExportVo> exportVos = BeanMapper.mapList(failDataList, DistrictCodeExportVo.class);

            String[] headers = {"国家","省","城市","城市代码","错误原因"};
            String[] fileds = {"zoneName","province","city","code","failMessage"};

            ExcelUtil.exportExcelAndDownload(response,"地区代码导入错误数据","错误数据",headers,fileds,exportVos);
        }
    }


    @RequestMapping(value = "/updateEnable",method = RequestMethod.PUT)
    public RestResult updateState(
            @ModelAttribute DistrictCode districtCode
    ){
        Assert.notNull(districtCode.getId(),"id不能为空");
        Assert.notNull(districtCode.getEnable(),"状态不能为空");
        Assert.isTrue(districtCode.getEnable() == 1 || districtCode.getEnable() == 0,"非法状态值" );

        UserDto userDto= ThreadLocalUtils.getUser();
        districtCode.setModifierId(userDto.getId());
        districtCode.setModifier(userDto.getLastName());

        service.updateEnable(districtCode);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("更新地区代码状态成功");
        return restResult;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RestResult update(DistrictCode districtCode, HttpServletRequest request) {
        Assert.notNull(districtCode.getId(),"id不能为空");
        Assert.notNull(districtCode.getZoneId(),"国家不能为空");
        Assert.notNull(districtCode.getProvince(),"省不能为空");
        Assert.notNull(districtCode.getCity(),"城市不能为空");
        Assert.notNull(districtCode.getCode(),"城市代码不能为空");


        UserDto userDto=ThreadLocalUtils.getUser();
        districtCode.setModifierId(userDto.getId());
        districtCode.setModifier(userDto.getLastName());

        service.update(districtCode);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("修改成功");
        return restResult;
    }
}
