package com.stosz.tms.web;

import com.google.common.collect.Lists;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.BeanMapper;
import com.stosz.plat.utils.ExcelUtil;
import com.stosz.plat.utils.ExeclRenderUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.dto.ShippingZoneStoreRelQueryListDto;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.model.ShippingZoneStoreRel;
import com.stosz.tms.service.ShippingZoneStoreRelationService;
import com.stosz.tms.vo.ShippingStoreRelationListVo;
import com.stosz.tms.vo.ShippingWayStoreExportVo;
import com.stosz.tms.vo.ShippingZoneStoreRelationListVo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/tms/shippingZoneStoreRel")
public class ShippingZoneStoreRelationController extends AbstractController {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ShippingZoneStoreRelationService service;


    @RequestMapping(value = "query",method = RequestMethod.POST)
    public RestResult queryList(ShippingZoneStoreRelQueryListDto queryListDto){
        final ShippingZoneStoreRel zoneStoreRel = BeanMapper.map(queryListDto, ShippingZoneStoreRel.class);

        final String wmsIdStr = queryListDto.getWmsIdStr();

        if (!StringUtils.isEmpty(wmsIdStr)){
            final List<String> wmsIdStrList = Arrays.asList(wmsIdStr.split(","));
            final List<Integer>  wmsIdList  = Lists.newArrayList();
            wmsIdStrList.forEach(e -> {
                wmsIdList.add(Integer.valueOf(e));
            });
            zoneStoreRel.setWmsIdList(wmsIdList);
        }
        final String zoneIdStr = queryListDto.getZoneIdStr();

        if (!StringUtils.isEmpty(zoneIdStr)){
            final List<String> zoneIdStrList = Arrays.asList(zoneIdStr.split(","));
            final List<Integer>  zoneIdList  = Lists.newArrayList();
            zoneIdStrList.forEach(e -> {
                zoneIdList.add(Integer.valueOf(e));
            });
            zoneStoreRel.setZoneIdList(zoneIdList);
        }


        RestResult restResult = new RestResult();
        int count = service.count(zoneStoreRel);
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

        final List<ShippingZoneStoreRelationListVo> zoneStoreRelationListVos = service.queryList(zoneStoreRel);
        restResult.setItem(zoneStoreRelationListVos);
        restResult.setCode(RestResult.OK);
        return restResult;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public RestResult addShippinigStoreRel(ShippingZoneStoreRel shippingZoneStoreRel){
        Assert.notNull(shippingZoneStoreRel.getShippingWayId(),"物流线路不能为空");
        Assert.notNull(shippingZoneStoreRel.getWmsId(),"仓库不能为空");
        Assert.notNull(shippingZoneStoreRel.getAllowedProductType(),"货物类型不能为空");
        Assert.notNull(shippingZoneStoreRel.getZoneId(),"覆盖地区不能为空");

        UserDto userDto= ThreadLocalUtils.getUser();
        shippingZoneStoreRel.setCreatorId(userDto.getId());
        shippingZoneStoreRel.setCreator(userDto.getLastName());
        shippingZoneStoreRel.setEnable(0);

        service.add(shippingZoneStoreRel);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("新增物流配送区域成功");

        return restResult;
    }


    @RequestMapping(value = "/updateEnable",method = RequestMethod.PUT)
    public RestResult updateEnable(
            @ModelAttribute ShippingZoneStoreRel shippingZoneStoreRel
    ){
        Assert.notNull(shippingZoneStoreRel.getId(),"id不能为空");
        Assert.notNull(shippingZoneStoreRel.getEnable(),"状态不能为空");
        Assert.isTrue(shippingZoneStoreRel.getEnable() == 1 || shippingZoneStoreRel.getEnable() == 0,"非法状态值" );

        service.updateEnable(shippingZoneStoreRel);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("更新状态成功");
        return restResult;
    }


    @RequestMapping(method = RequestMethod.PUT)
    public RestResult update(ShippingZoneStoreRel shippingZoneStoreRel) {
        Assert.notNull(shippingZoneStoreRel.getId(),"ID不能为空");
        Assert.notNull(shippingZoneStoreRel.getShippingWayId(),"物流线路不能为空");
        Assert.notNull(shippingZoneStoreRel.getWmsId(),"仓库不能为空");
        Assert.notNull(shippingZoneStoreRel.getAllowedProductType(),"货物类型不能为空");
        Assert.notNull(shippingZoneStoreRel.getZoneId(),"覆盖地区不能为空");

        service.update(shippingZoneStoreRel);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("修改成功");
        return restResult;
    }



    @RequestMapping(value = "/exportTemplate",method = RequestMethod.GET)
    public void exportTemplate(
            HttpServletResponse response
    ) throws Exception {
        List<ShippingWayStoreExportVo> tempatelList = Lists.newArrayList();

        ShippingWayStoreExportVo wayStoreExportVo = new ShippingWayStoreExportVo();
        wayStoreExportVo.setWmsId("1");
        wayStoreExportVo.setShippingWayCode("test");
        wayStoreExportVo.setProductType("1");
        wayStoreExportVo.setZoneId("1");
        tempatelList.add(wayStoreExportVo);


        String[] headers = { "仓库ID", "物流线路代码", "货物类型", "地区ID" };
        String[] fileds = { "wmsId", "shippingWayCode", "productType", "zoneId" };

        ExcelUtil.exportExcelAndDownload(response, "物流配送区域导入模板", "物流配送区域信息", headers, fileds, tempatelList);

    }


    @RequestMapping(value = "/importExecl",method = RequestMethod.POST)
    public RestResult importExecl(
          @RequestParam(name = "file",required = true)   MultipartFile file
    ){
        UserDto userDto= ThreadLocalUtils.getUser();


        List<ShippingZoneStoreRel> zoneStoreRels = Lists.newArrayList();

        final List<List<String>> execlDataList = ExeclRenderUtils.readExecl(file);

        for (int i = 0; i < execlDataList.size() ; i++){
            final List<String> rowData = execlDataList.get(i);

            final String wmsIdStr = rowData.get(0);
            final String shippingWayCode = rowData.get(1);
            final String productTypeStr = rowData.get(2);
            final String zoneIdStr = rowData.get(3);

            Assert.isTrue(!wmsIdStr.isEmpty(),"第"+i+"行的仓库ID不能不为空");
            Assert.notNull(!shippingWayCode.isEmpty(),"第"+i+"行的物流线路代码不能不为空");
            Assert.notNull(!productTypeStr.isEmpty(),"第"+i+"行的货物类型不能不为空");
            Assert.notNull(!zoneIdStr.isEmpty(),"第"+i+"行的地区ID不能不为空");


            ShippingZoneStoreRel zoneStoreRel = new ShippingZoneStoreRel();
            zoneStoreRel.setWmsId(Integer.valueOf(wmsIdStr));
            zoneStoreRel.setShippingWayCode(shippingWayCode);
            zoneStoreRel.setAllowedProductType(Integer.valueOf(productTypeStr));
            zoneStoreRel.setZoneId(Integer.valueOf(zoneIdStr));
            zoneStoreRel.setCreator(userDto.getLastName());
            zoneStoreRel.setCreatorId(userDto.getId());
            zoneStoreRel.setEnable(0);

            zoneStoreRels.add(zoneStoreRel);
        }

        service.importExecl(zoneStoreRels);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("导入execl数据成功");
        return restResult;
    }

}
