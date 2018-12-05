package com.stosz.tms.web;

import com.google.common.collect.Lists;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.*;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.model.CustomsCode;
import com.stosz.tms.model.DistrictCode;
import com.stosz.tms.service.CustomsCodeService;
import com.stosz.tms.service.DistrictCodeService;
import com.stosz.tms.vo.CustomsCodeExportVo;
import com.stosz.tms.vo.DistrictCodeExportVo;
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
@RequestMapping("/tms/customsCode")
public class CustomsCodeController extends AbstractController {

    @Resource
    private CustomsCodeService service;


    @Resource
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "query",method = RequestMethod.POST)
    public RestResult queryList(CustomsCode customsCode){
        RestResult restResult = new RestResult();
        int count = service.count(customsCode);
        restResult.setTotal(count);
        if (count <= 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }

        final List<CustomsCode> customsCodes = service.queryList(customsCode);
        restResult.setItem(customsCodes);
        restResult.setCode(RestResult.OK);
        return restResult;
    }


    @RequestMapping(value = "/exportTemplate",method = RequestMethod.GET)
    public void exportTemplate(
            HttpServletResponse response
    ) throws Exception {
        List<CustomsCodeExportVo> exportVos = Lists.newArrayList();

        CustomsCodeExportVo districtCodeExportVo = new CustomsCodeExportVo();
        districtCodeExportVo.setSku("test");
        districtCodeExportVo.setCode("test");
        districtCodeExportVo.setZoneName("test");

        exportVos.add(districtCodeExportVo);

        String[] headers = {"国家","sku","海关代码"};
        String[] fileds = {"zoneName","sku","code"};

        ExcelUtil.exportExcelAndDownload(response,"海关代码导入模板","海关代码信息",headers,fileds,exportVos);

    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public RestResult upload(
            @RequestParam(name = "file",required = true) MultipartFile file,
            HttpServletResponse response
    ) throws Exception {

        final List<List<String>> uploadData = ExeclRenderUtils.readExecl(file);

        final List<CustomsCodeExportVo> exportVos = service.importExeclData(uploadData);

        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);

        final String key = "customsCode_import_fail_" + new Date().getTime();

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

            final List<CustomsCodeExportVo> exportVos = BeanMapper.mapList(failDataList, CustomsCodeExportVo.class);

            String[] headers = {"国家","sku","海关代码","错误原因"};
            String[] fileds = {"zoneName","sku","code","failMessage"};

            ExcelUtil.exportExcelAndDownload(response,"海关代码导入错误数据","错误数据",headers,fileds,exportVos);
        }
    }


    @RequestMapping(value = "/updateEnable",method = RequestMethod.PUT)
    public RestResult updateState(
            @ModelAttribute CustomsCode customsCode
    ){
        Assert.notNull(customsCode.getId(),"id不能为空");
        Assert.notNull(customsCode.getEnable(),"状态不能为空");
        Assert.isTrue(customsCode.getEnable() == 1 || customsCode.getEnable() == 0,"非法状态值" );

        UserDto userDto= ThreadLocalUtils.getUser();
        customsCode.setModifierId(userDto.getId());
        customsCode.setModifier(userDto.getLastName());

        service.updateEnable(customsCode);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("更新海关代码状态成功");
        return restResult;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RestResult update(CustomsCode customsCode, HttpServletRequest request) {
        Assert.notNull(customsCode.getId(),"id不能为空");
        Assert.notNull(customsCode.getZoneId(),"国家不能为空");
        Assert.notNull(customsCode.getSku(),"sku不能为空");
        Assert.notNull(customsCode.getCode(),"城市代码不能为空");


        UserDto userDto=ThreadLocalUtils.getUser();
        customsCode.setModifierId(userDto.getId());
        customsCode.setModifier(userDto.getLastName());

        service.update(customsCode);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("修改成功");
        return restResult;
    }

}
