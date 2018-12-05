package com.stosz.tms.web;

import com.google.common.collect.Lists;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.*;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStorehouseService;
import com.stosz.tms.dto.ShippingZoneStoreRelQueryListDto;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.model.ShippingZoneBackStore;
import com.stosz.tms.model.ShippingZoneStoreRel;
import com.stosz.tms.service.ShippingWayService;
import com.stosz.tms.service.ShippingZoneBackStoreService;
import com.stosz.tms.vo.ShippingZoneBackStoreExportVo;
import com.stosz.tms.vo.ShippingZoneStoreRelationListVo;
import com.stosz.tms.vo.TrackingTaskExportVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/tms/shippingZoneBackStore")
public class ShippingZoneBackStoreController extends AbstractController {

    @Resource
    private ShippingZoneBackStoreService service;

    @Resource
    private IStorehouseService storehouseService;

    @Resource
    private IZoneService zoneService;

    @Resource
    private StringRedisTemplate redisTemplate;


    @Resource
    private ShippingWayService shippingWayService;

    @RequestMapping(value = "query",method = RequestMethod.POST)
    public RestResult queryList(ShippingZoneBackStore zoneBackStore){

        final String wmsIdStr = zoneBackStore.getWmsIdStr();

        if (!StringUtils.isEmpty(wmsIdStr)){
            final List<String> wmsIdStrList = Arrays.asList(wmsIdStr.split(","));
            final List<Integer>  wmsIdList  = Lists.newArrayList();
            wmsIdStrList.forEach(e -> {
                wmsIdList.add(Integer.valueOf(e));
            });
            zoneBackStore.setWmsIdList(wmsIdList);
        }
        final String zoneIdStr = zoneBackStore.getZoneIdStr();

        if (!StringUtils.isEmpty(zoneIdStr)){
            final List<String> zoneIdStrList = Arrays.asList(zoneIdStr.split(","));
            final List<Integer>  zoneIdList  = Lists.newArrayList();
            zoneIdStrList.forEach(e -> {
                zoneIdList.add(Integer.valueOf(e));
            });
            zoneBackStore.setZoneIdList(zoneIdList);
        }

        final String backWmsIdStr = zoneBackStore.getBackWmsIdStr();

        if (!StringUtils.isEmpty(backWmsIdStr)){
            final List<String> wmsIdStrList = Arrays.asList(backWmsIdStr.split(","));
            final List<Integer>  wmsIdList  = Lists.newArrayList();
            wmsIdStrList.forEach(e -> {
                wmsIdList.add(Integer.valueOf(e));
            });
            zoneBackStore.setBackWmsIdList(wmsIdList);
        }

        RestResult restResult = new RestResult();
        int count = service.count(zoneBackStore);
        restResult.setTotal(count);
        if (count <= 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }

//        final Integer start = queryListDto.getStart();
//        final Integer limit = queryListDto.getLimit();
//
//        //开始位置
//        zoneStoreRel.setStart((start == null || start <= 0) ? 0 : (start -1)* limit);
//        //需要显示的条数
//        zoneStoreRel.setLimit((limit == null ) ? 10 : limit);

        final List<ShippingZoneBackStore> zoneBackStores = service.queryList(zoneBackStore);
        restResult.setItem(zoneBackStores);
        restResult.setCode(RestResult.OK);
        return restResult;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public RestResult addShippinigStoreRel(ShippingZoneBackStore shippingZoneBackStore){
        Assert.notNull(shippingZoneBackStore.getShippingWayId(),"物流线路不能为空");
        Assert.notNull(shippingZoneBackStore.getWmsId(),"仓库不能为空");
        Assert.notNull(shippingZoneBackStore.getBackWmsId(),"退回仓库不能为空");
        Assert.notNull(shippingZoneBackStore.getZoneId(),"覆盖地区不能为空");

        UserDto userDto= ThreadLocalUtils.getUser();
        shippingZoneBackStore.setCreatorId(userDto.getId());
        shippingZoneBackStore.setCreator(userDto.getLastName());
        shippingZoneBackStore.setEnable(0);

        service.add(shippingZoneBackStore);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("新增物流线路退回仓成功");

        return restResult;
    }


    @RequestMapping(value = "/updateEnable",method = RequestMethod.PUT)
    public RestResult updateEnable(
            @ModelAttribute ShippingZoneBackStore shippingZoneBackStore
    ){
        Assert.notNull(shippingZoneBackStore.getId(),"id不能为空");
        Assert.notNull(shippingZoneBackStore.getEnable(),"状态不能为空");
        Assert.isTrue(shippingZoneBackStore.getEnable() == 1 || shippingZoneBackStore.getEnable() == 0,"非法状态值" );

        UserDto userDto=ThreadLocalUtils.getUser();
        shippingZoneBackStore.setModifierId(userDto.getId());
        shippingZoneBackStore.setModifier(userDto.getLastName());

        service.updateEnable(shippingZoneBackStore);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("更新状态成功");
        return restResult;
    }


    @RequestMapping(method = RequestMethod.PUT)
    public RestResult update(ShippingZoneBackStore shippingZoneBackStore) {
        Assert.notNull(shippingZoneBackStore.getId(),"ID不能为空");
        Assert.notNull(shippingZoneBackStore.getShippingWayId(),"物流线路不能为空");
        Assert.notNull(shippingZoneBackStore.getWmsId(),"仓库不能为空");
        Assert.notNull(shippingZoneBackStore.getBackWmsId(),"退回仓库不能为空");
        Assert.notNull(shippingZoneBackStore.getZoneId(),"覆盖地区不能为空");

        UserDto userDto=ThreadLocalUtils.getUser();
        shippingZoneBackStore.setModifierId(userDto.getId());
        shippingZoneBackStore.setModifier(userDto.getLastName());

        service.update(shippingZoneBackStore);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("修改成功");
        return restResult;
    }


    @RequestMapping(value = "/exportTemplate", method = RequestMethod.GET)
    public void exportTemplate(HttpServletResponse response) throws Exception {
        List<ShippingZoneBackStoreExportVo> exportVos = Lists.newArrayList();

        ShippingZoneBackStoreExportVo zoneBackStoreExportVo = new ShippingZoneBackStoreExportVo();
        zoneBackStoreExportVo.setBackWmsId("1");
        zoneBackStoreExportVo.setShippingWayCode("test");
        zoneBackStoreExportVo.setWmsId("1");
        zoneBackStoreExportVo.setZoneId("1");

        exportVos.add(zoneBackStoreExportVo);

        String[] headers = { "仓库ID", "物流线路代码", "覆盖区域ID", "退回仓ID" };
        String[] fileds = { "wmsId", "shippingWayCode", "zoneId", "backWmsId" };

        ExcelUtil.exportExcelAndDownload(response, "退回仓导入模板", "退回仓信息", headers, fileds, exportVos);
    }

    @RequestMapping(value = "/exportWmsInfo", method = RequestMethod.GET)
    public void exportWmsInfo(HttpServletResponse response) throws Exception {
        Wms paramBean = new Wms();
        paramBean.setStart(0);
        paramBean.setLimit(Integer.MAX_VALUE);
        final List<Wms> wmsList = storehouseService.wmsTransferRequest(paramBean);

        String[] headers = { "仓库ID", "仓库名称" };
        String[] fileds = { "id", "name"};

        ExcelUtil.exportExcelAndDownload(response, "仓库信息", "仓库信息", headers, fileds, wmsList);
    }

    @RequestMapping(value = "/exportShippingWayInfo", method = RequestMethod.GET)
    public void exportShippingWayInfo(HttpServletResponse response) throws Exception {
        ShippingWay paramBean = new ShippingWay();
        paramBean.setStart(0);
        paramBean.setLimit(Integer.MAX_VALUE);
        final List<ShippingWay> shippingWays = shippingWayService.queryList(paramBean);

        String[] headers = { "物流线路代码", "物流线路名称" };
        String[] fileds = { "shippingWayCode", "shippingWayName"};

        ExcelUtil.exportExcelAndDownload(response, "物理线路信息", "物理线路信息", headers, fileds, shippingWays);
    }


    @RequestMapping(value = "/exportZoneInfo", method = RequestMethod.GET)
    public void exportZoneInfo(HttpServletResponse response) throws Exception {
        final List<Zone> zoneList = zoneService.findAll();

        String[] headers = { "区域ID", "区域名称" };
        String[] fileds = { "id", "title"};

        ExcelUtil.exportExcelAndDownload(response, "区域信息", "区域信息", headers, fileds, zoneList);
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RestResult upload(
            @RequestParam(name = "file", required = true) MultipartFile file,
            HttpServletResponse response) throws Exception {

        final List<List<String>> uploadData = ExeclRenderUtils.readExecl(file);

        final List<ShippingZoneBackStoreExportVo> exportVos = service.importExeclData(uploadData);

        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);

        final String key = "back_store_import_fail_" + new Date().getTime();

        if (CollectionUtils.isNotNullAndEmpty(exportVos)) {
            redisTemplate.opsForValue().set(key, JsonUtils.toJson(exportVos), 2, TimeUnit.MINUTES);
            restResult.setItem(key);
        }

        return restResult;
    }

    @RequestMapping(value = "/exportFailExecl", method = RequestMethod.POST)
    public void exportFailExecl(String key, HttpServletResponse response) throws Exception {
        Assert.notNull(key, "key不能为空");

        if (redisTemplate.hasKey(key)) {
            final String failDataJson = redisTemplate.opsForValue().get(key);
            final List failDataList = JsonUtils.jsonToObject(failDataJson, List.class);

            final List<ShippingZoneBackStoreExportVo> exportVos = BeanMapper.mapList(failDataList, ShippingZoneBackStoreExportVo.class);

            String[] headers = { "仓库ID", "物流线路代码", "覆盖区域ID", "退回仓ID","失败原因" };
            String[] fileds = { "wmsId", "shippingWayCode", "zoneId", "backWmsId" ,"failMessage"};

            ExcelUtil.exportExcelAndDownload(response, "退回仓导入错误信息", "退回仓信息", headers, fileds, exportVos);
        }
    }

}
