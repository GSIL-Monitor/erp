package com.stosz.purchase.engine;

import com.stosz.plat.hessian.HessianUtil;
import com.stosz.purchase.ext.service.IOrderRequiredService;
import com.stosz.purchase.ext.service.IPurchaseService;
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
 * create time    2017-12-01
 */
@Configuration
public class PurchaseProvider {

    public static final Logger logger = LoggerFactory.getLogger(PurchaseProvider.class);
    @Bean(IOrderRequiredService.url)
    public RemoteExporter remoteIOrderRequiredService(@Autowired IOrderRequiredService iOrderRequiredService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IOrderRequiredService.class);
        hessianServiceExporter.setService(iOrderRequiredService);
        return hessianServiceExporter;
    }

    @Bean(IPurchaseService.url)
    public RemoteExporter remoteIPurchaseService(@Autowired IPurchaseService iPurchaseService)
    {
        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
        hessianServiceExporter.setServiceInterface(IPurchaseService.class);
        hessianServiceExporter.setService(iPurchaseService);
        return hessianServiceExporter;
    }

    /**
     * 快捷生成模板代码；
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String packageName = "E:\\stosz\\erp-v2\\backend-exts\\product-ext\\src\\main\\java\\com\\stosz\\product\\ext\\service";

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



        logger.info(stringBuffer.toString());


    }


}
