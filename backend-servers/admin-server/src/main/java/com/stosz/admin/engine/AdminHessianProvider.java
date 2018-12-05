package com.stosz.admin.engine;

import com.stosz.AdminHessianConsumer;
import com.stosz.admin.ext.service.*;
import com.stosz.olderp.ext.service.*;
import com.stosz.plat.hessian.HessianUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @auther carter
 * create time    2017-11-28
 * hessian接口的服务化
 *
 */
@Configuration
public class AdminHessianProvider {

    public static final Logger logger = LoggerFactory.getLogger(AdminHessianConsumer.class);

    /**
     * 快捷生成模板代码；
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String packageName = "D:\\workspace\\erp-v2\\backend-exts\\admin-ext\\src\\main\\java\\com\\stosz\\olderp\\ext\\service";

        StringBuffer stringBuffer = new StringBuffer();
        Arrays.asList(new File(packageName).listFiles()).stream().forEachOrdered(file -> {

            String interfaceName = file.getName().substring(0, file.getName().lastIndexOf("."));

            String bianliang = interfaceName.replace("I", "i");

            stringBuffer.append("\n" +
                    "    @Bean(" + interfaceName + ".url)\n" +
                    "    public RemoteExporter remote" + interfaceName + "(@Autowired " + interfaceName + " " + bianliang + ")\n" +
                    "    {\n" +
                    "        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();\n" +
                    "        hessianServiceExporter.setServiceInterface(" + interfaceName + ".class);\n" +
                    "        hessianServiceExporter.setService(" + bianliang + ");\n" +
                    "        return hessianServiceExporter;\n" +
                    "    }\n" +
                    "\n");


        });


        logger.info(""+stringBuffer.toString());


    }

    @Bean(IErpDomainService.url)
    public RemoteExporter remoteIErpDomainService(@Autowired IErpDomainService iErpDomainService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IErpDomainService.class);
        hessianServiceExporter.setService(iErpDomainService);
        return hessianServiceExporter;
    }


    @Bean(IErpOrderInfoService.url)
    public RemoteExporter remoteIErpOrderInfoService(@Autowired IErpOrderInfoService iErpOrderinfoService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IErpOrderInfoService.class);
        hessianServiceExporter.setService(iErpOrderinfoService);
        return hessianServiceExporter;
    }


    @Bean(IErpOrderItemService.url)
    public RemoteExporter remoteIErpOrderItemService(@Autowired IErpOrderItemService iErpOrderitemService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IErpOrderItemService.class);
        hessianServiceExporter.setService(iErpOrderitemService);
        return hessianServiceExporter;
    }


    @Bean(IErpOrderShippingService.url)
    public RemoteExporter remoteIErpOrderShippingService(@Autowired IErpOrderShippingService iErpOrderShippingService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IErpOrderShippingService.class);
        hessianServiceExporter.setService(iErpOrderShippingService);
        return hessianServiceExporter;
    }


    @Bean(IErpWarehouseService.url)
    public RemoteExporter remoteIErpWarehouseService(@Autowired IErpWarehouseService iErpWarehouseService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IErpWarehouseService.class);
        hessianServiceExporter.setService(iErpWarehouseService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpAttributeService.url)
    public RemoteExporter remoteIOldErpAttributeService(@Autowired IOldErpAttributeService iOldErpAttributeService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpAttributeService.class);
        hessianServiceExporter.setService(iOldErpAttributeService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpAttributeValueService.url)
    public RemoteExporter remoteIOldErpAttributeValueService(@Autowired IOldErpAttributeValueService iOldErpAttributeValueService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpAttributeValueService.class);
        hessianServiceExporter.setService(iOldErpAttributeValueService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpBlackListService.url)
    public RemoteExporter remoteIOldErpBlackListService(@Autowired IOldErpBlackListService iOldErpBlackListService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpBlackListService.class);
        hessianServiceExporter.setService(iOldErpBlackListService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpCategoryService.url)
    public RemoteExporter remoteIOldErpCategoryService(@Autowired IOldErpCategoryService iOldErpCategoryService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpCategoryService.class);
        hessianServiceExporter.setService(iOldErpCategoryService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpCheckProductService.url)
    public RemoteExporter remoteIOldErpCheckProductService(@Autowired IOldErpCheckProductService iOldErpCheckProductService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpCheckProductService.class);
        hessianServiceExporter.setService(iOldErpCheckProductService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpCountryService.url)
    public RemoteExporter remoteIOldErpCountryService(@Autowired IOldErpCountryService iOldErpCountryService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpCountryService.class);
        hessianServiceExporter.setService(iOldErpCountryService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpCurrencyService.url)
    public RemoteExporter remoteIOldErpCurrencyService(@Autowired IOldErpCurrencyService iOldErpCurrencyService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpCurrencyService.class);
        hessianServiceExporter.setService(iOldErpCurrencyService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpDepartmentService.url)
    public RemoteExporter remoteIOldErpDepartmentService(@Autowired IOldErpDepartmentService iOldErpDepartmentService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpDepartmentService.class);
        hessianServiceExporter.setService(iOldErpDepartmentService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpOrderService.url)
    public RemoteExporter remoteIOldErpOrderService(@Autowired IOldErpOrderService iOldErpOrderService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpOrderService.class);
        hessianServiceExporter.setService(iOldErpOrderService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpProductService.url)
    public RemoteExporter remoteIOldErpProductService(@Autowired IOldErpProductService iOldErpProductService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpProductService.class);
        hessianServiceExporter.setService(iOldErpProductService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpProductSkuService.url)
    public RemoteExporter remoteIOldErpProductSkuService(@Autowired IOldErpProductSkuService iOldErpProductSkuService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpProductSkuService.class);
        hessianServiceExporter.setService(iOldErpProductSkuService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpProductZoneDomainService.url)
    public RemoteExporter remoteIOldErpProductZoneDomainService(@Autowired IOldErpProductZoneDomainService iOldErpProductZoneDomainService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpProductZoneDomainService.class);
        hessianServiceExporter.setService(iOldErpProductZoneDomainService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpProductZoneService.url)
    public RemoteExporter remoteIOldErpProductZoneService(@Autowired IOldErpProductZoneService iOldErpProductZoneService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpProductZoneService.class);
        hessianServiceExporter.setService(iOldErpProductZoneService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpQuantityService.url)
    public RemoteExporter remoteIOldErpQuantityService(@Autowired IOldErpQuantityService iOldErpQuantityService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpQuantityService.class);
        hessianServiceExporter.setService(iOldErpQuantityService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpStockService.url)
    public RemoteExporter remoteIOldErpStockService(@Autowired IOldErpStockService iOldErpStockService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpStockService.class);
        hessianServiceExporter.setService(iOldErpStockService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpSupplierService.url)
    public RemoteExporter remoteIOldErpSupplierService(@Autowired IOldErpSupplierService iOldErpSupplierService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpSupplierService.class);
        hessianServiceExporter.setService(iOldErpSupplierService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpUserService.url)
    public RemoteExporter remoteIOldErpUserService(@Autowired IOldErpUserService iOldErpUserService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpUserService.class);
        hessianServiceExporter.setService(iOldErpUserService);
        return hessianServiceExporter;
    }


    @Bean(IOldErpZoneService.url)
    public RemoteExporter remoteIOldErpZoneService(@Autowired IOldErpZoneService iOldErpZoneService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpZoneService.class);
        hessianServiceExporter.setService(iOldErpZoneService);
        return hessianServiceExporter;
    }



    @Bean(IDepartmentService.url)
    public RemoteExporter remoteIDepartmentService(@Autowired IDepartmentService iDepartmentService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IDepartmentService.class);
        hessianServiceExporter.setService(iDepartmentService);
        return hessianServiceExporter;
    }


    @Bean(IDepartmentSyncService.url)
    public RemoteExporter remoteIDepartmentSyncService(@Autowired IDepartmentSyncService iDepartmentSyncService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IDepartmentSyncService.class);
        hessianServiceExporter.setService(iDepartmentSyncService);
        return hessianServiceExporter;
    }


    @Bean(IGreetingService.url)
    public RemoteExporter remoteIGreetingService(@Autowired IGreetingService iGreetingService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IGreetingService.class);
        hessianServiceExporter.setService(iGreetingService);
        return hessianServiceExporter;
    }


    @Bean(IJobAuthorityRelService.url)
    public RemoteExporter remoteIJobAuthorityRelService(@Autowired IJobAuthorityRelService iJobAuthorityRelService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IJobAuthorityRelService.class);
        hessianServiceExporter.setService(iJobAuthorityRelService);
        return hessianServiceExporter;
    }


    @Bean(IJobSyncService.url)
    public RemoteExporter remoteIJobSyncService(@Autowired IJobSyncService iJobSyncService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IJobSyncService.class);
        hessianServiceExporter.setService(iJobSyncService);
        return hessianServiceExporter;
    }


//    @Bean(ISystemService.url)
//    public RemoteExporter remoteISystemService(@Autowired ISystemService iSystemService)
//    {
//        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
//        hessianServiceExporter.setServiceInterface(ISystemService.class);
//        hessianServiceExporter.setService(iSystemService);
//        return hessianServiceExporter;
//    }


    @Bean(IUserDepartmentRelService.url)
    public RemoteExporter remoteIUserDepartmentRelService(@Autowired IUserDepartmentRelService iUserDepartmentRelService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IUserDepartmentRelService.class);
        hessianServiceExporter.setService(iUserDepartmentRelService);
        return hessianServiceExporter;
    }


    @Bean(IUserService.url)
    public RemoteExporter remoteIUserService(@Autowired IUserService iUserService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IUserService.class);
        hessianServiceExporter.setService(iUserService);
        return hessianServiceExporter;
    }


    @Bean(IUserSyncService.url)
    public RemoteExporter remoteIUserSyncService(@Autowired IUserSyncService iUserSyncService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IUserSyncService.class);
        hessianServiceExporter.setService(iUserSyncService);
        return hessianServiceExporter;
    }

    @Bean(IErpZoneWarehouseService.url)
    public RemoteExporter remoteIErpZoneWarehouseService(@Autowired IErpZoneWarehouseService iErpZoneWarehouseService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IErpZoneWarehouseService.class);
        hessianServiceExporter.setService(iErpZoneWarehouseService);
        return hessianServiceExporter;
    }

    @Bean(IOldErpCategoryNewService.url)
    public RemoteExporter remoteIOldErpCategoryNewService(@Autowired IOldErpCategoryNewService iOldErpCategoryNewService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOldErpCategoryNewService.class);
        hessianServiceExporter.setService(iOldErpCategoryNewService);
        return hessianServiceExporter;
    }


}
