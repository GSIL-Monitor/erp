package com.stosz.store.engine;

import com.stosz.plat.hessian.HessianUtil;
import com.stosz.store.ext.service.IStockService;
import com.stosz.store.ext.service.IStorehouseService;
import com.stosz.store.ext.service.ITransitStockService;
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
 * create time    2017-12-01
 */
@Configuration
public class StoreProvider {


    @Bean(ITransitStockService.url)
    public RemoteExporter remoteITransitStockService(@Autowired ITransitStockService iTransitStockService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(ITransitStockService.class);
        hessianServiceExporter.setService(iTransitStockService);
        return hessianServiceExporter;
    }


    @Bean(IStockService.url)
    public RemoteExporter remoteIStockService(@Autowired IStockService iStockService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IStockService.class);
        hessianServiceExporter.setService(iStockService);
        return hessianServiceExporter;
    }

    @Bean(IStorehouseService.url)
    public RemoteExporter remoteIWmsService(@Autowired IStorehouseService iStorehouseService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IStorehouseService.class);
        hessianServiceExporter.setService(iStorehouseService);
        return hessianServiceExporter;
    }

    /**
     * 快捷生成模板代码；
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String packageName = "D:\\workspace\\erp-v2\\backend-exts\\store-ext\\src\\main\\java\\com\\stosz\\store\\ext\\service";

        StringBuffer stringBuffer = new StringBuffer();
        Arrays.asList(new File(packageName).listFiles()).stream().forEachOrdered(file->{

            String interfaceName = file.getName().substring(0,file.getName().lastIndexOf("."));

            String  bianliang = interfaceName.replace("I","i");

            stringBuffer.append("\n" +
                    "    @Bean("+interfaceName+".url)\n" +
                    "    public RemoteExporter remote"+interfaceName+"(@Autowired "+interfaceName+" "+bianliang+")\n" +
                    "    {\n" +
                    "        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();\n" +
                    "        hessianServiceExporter.setServiceInterface("+interfaceName+".class);\n" +
                    "        hessianServiceExporter.setService("+bianliang+");\n" +
                    "        return hessianServiceExporter;\n" +
                    "    }\n" +
                    "\n");



        });

    }

}
