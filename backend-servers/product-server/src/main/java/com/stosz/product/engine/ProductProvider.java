package com.stosz.product.engine;

import com.stosz.plat.hessian.HessianUtil;
import com.stosz.product.ext.service.*;
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
 * create time    2017-11-30
 */
@Configuration
public class ProductProvider {

	@Bean(IAttributeValueService.url)
	public RemoteExporter remoteIAttributeValueService(@Autowired IAttributeValueService iAttributeValueService) {
		HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
		hessianServiceExporter.setServiceInterface(IAttributeValueService.class);
		hessianServiceExporter.setService(iAttributeValueService);
		return hessianServiceExporter;
	}

	@Bean(ICurrencyService.url)
	public RemoteExporter remoteICurrencyService(@Autowired ICurrencyService iCurrencyService) {
		HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
		hessianServiceExporter.setServiceInterface(ICurrencyService.class);
		hessianServiceExporter.setService(iCurrencyService);
		return hessianServiceExporter;
	}

	@Bean(IProductNewService.url)
	public RemoteExporter remoteIProductNewService(@Autowired IProductNewService iProductNewService) {
		HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
		hessianServiceExporter.setServiceInterface(IProductNewService.class);
		hessianServiceExporter.setService(iProductNewService);
		return hessianServiceExporter;
	}

	@Bean(IProductService.url)
	public RemoteExporter remoteIProductService(@Autowired IProductService iProductService) {
		HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
		hessianServiceExporter.setServiceInterface(IProductService.class);
		hessianServiceExporter.setService(iProductService);
		return hessianServiceExporter;
	}

	@Bean(IProductSkuService.url)
	public RemoteExporter remoteIProductSkuService(@Autowired IProductSkuService iProductSkuService) {
		HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
		hessianServiceExporter.setServiceInterface(IProductSkuService.class);
		hessianServiceExporter.setService(iProductSkuService);
		return hessianServiceExporter;
	}

	@Bean(IPartnerService.url)
	public RemoteExporter remoteIPartnerService(@Autowired IPartnerService iPartnerService) {
		HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
		hessianServiceExporter.setServiceInterface(IPartnerService.class);
		hessianServiceExporter.setService(iPartnerService);
		return hessianServiceExporter;
	}

	@Bean(IWmsService.url)
	public RemoteExporter remoteIWmsService(@Autowired IWmsService iWmsService) {
		HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
		hessianServiceExporter.setServiceInterface(IWmsService.class);
		hessianServiceExporter.setService(iWmsService);
		return hessianServiceExporter;
	}

	@Bean(IZoneService.url)
	public RemoteExporter remoteIZoneService(@Autowired IZoneService iZoneService) {
		HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
		hessianServiceExporter.setServiceInterface(IZoneService.class);
		hessianServiceExporter.setService(iZoneService);
		return hessianServiceExporter;
	}

	@Bean(ILabelObjectServcie.url)
	public RemoteExporter remoteILabelObjectService(@Autowired ILabelObjectServcie LabelObjectServcie) {
		HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
		hessianServiceExporter.setServiceInterface(ILabelObjectServcie.class);
		hessianServiceExporter.setService(LabelObjectServcie);
		return hessianServiceExporter;
	}

	@Bean(IProductDeamonService.url)
	public RemoteExporter remoteIProductDeamonService(@Autowired IProductDeamonService iProductDeamonService) {
		HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();
		hessianServiceExporter.setServiceInterface(IProductDeamonService.class);
		hessianServiceExporter.setService(iProductDeamonService);
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
		Arrays.asList(new File(packageName).listFiles()).stream().forEachOrdered(file -> {

			String interfaceName = file.getName().substring(0, file.getName().lastIndexOf("."));

			String bianliang = interfaceName.replace("I", "i");

			stringBuffer.append("\n" + "    @Bean(" + interfaceName + ".url)\n" + "    public RemoteExporter remote" + interfaceName + "(@Autowired "
					+ interfaceName + " " + bianliang + ")\n" + "    {\n"
					+ "        HessianServiceExporter hessianServiceExporter = HessianUtil.getHessianServiceExporter();\n"
					+ "        hessianServiceExporter.setServiceInterface(" + interfaceName + ".class);\n"
					+ "        hessianServiceExporter.setService(" + bianliang + ");\n" + "        return hessianServiceExporter;\n" + "    }\n"
					+ "\n");

		});


	}

}
