package com.stosz.plat.hessian;

import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.service.ProjectConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @auther carter
 * create time    2017-12-09
 */
public abstract class AbstractHessianProviderUrlConfig implements EnvironmentAware {


    protected Environment environment;

    public static final Logger logger = LoggerFactory.getLogger(AbstractHessianProviderUrlConfig.class);


    public void setEnvironment(Environment environment) {

        this.environment = environment;
    }
    @Autowired
    ProjectConfigService projectConfigService;

    public abstract SystemEnum getProviderSystemEnum();

    public <T> HessianProxyFactoryBean getHessianProxyFactoryBean(String url , Class<T> clazz){
        String fullUrl = projectConfigService.getSystemHttp(getProviderSystemEnum()) + url;
        return HessianUtil.getHessianProxyFactoryBean( fullUrl ,clazz);
    }



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
                    "    @Bean\n" +
                    "    public HessianProxyFactoryBean " + bianliang + "(){\n" +
                    "        return HessianUtil.getHessianProxyFactoryBean(getProviderUrl(" + interfaceName + ".url)," + interfaceName + ".class);\n" +
                    "    }\n" +
                    "\n");


        });


        stringBuffer.append("   /**\n" +
                "     * 组装远程前缀\n" +
                "     * @return\n" +
                "     */\n" +
                "    protected   String getProviderUrl(String url)\n" +
                "    {\n" +
                "        return  \"http://127.0.0.1:8081\" + url;\n" +
                "    }");


        logger.info(stringBuffer.toString());


    }


    /**
     * 快捷生成模板代码； 提供者代码
     * @param args
     * @throws IOException
     */
    public static void main2(String[] args) throws IOException {
        String packageName = "D:\\workspace\\new_erp\\erp-v2\\backend-exts\\purchase-ext\\src\\main\\java\\com\\stosz\\purchase\\ext\\service";

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
