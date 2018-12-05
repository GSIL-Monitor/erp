import com.caucho.hessian.client.HessianProxyFactory;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IGreetingService;
import com.stosz.plat.hessian.HessianUtil;
import com.stosz.product.ext.enums.TypeEnum;
import com.stosz.product.ext.model.Partner;
import com.stosz.product.ext.service.IPartnerService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @auther carter
 * create time    2017-11-28
 */
public class TestHessian {

    public static final Logger logger = LoggerFactory.getLogger(TestHessian.class);

    @Test
    public void test3()
    {

        String url = "http://127.0.0.1:8081/product/remote/IPartnerService";

        try {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setSerializerFactory(HessianUtil.getSerializerFactory());
            IPartnerService service = (IPartnerService) factory.create(
                    IPartnerService.class, url);


//            String greeting = service.getNode().toString();
            List<Partner> list = service.findPartnerByType(TypeEnum.shipper);
            String greeting = service.findPartnerByType(TypeEnum.shipper).toString();


        }catch (Exception e)
        {
            logger.error(e.getMessage(),e);
        }


    }



    @Test
    public void test2()
    {

        String url = "http://127.0.0.1:8081/admin/remote/IDepartmentService";

        try {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setSerializerFactory(HessianUtil.getSerializerFactory());
            IDepartmentService service = (IDepartmentService) factory.create(
                    IDepartmentService.class, url);


//            String greeting = service.getNode().toString();
            String greeting = service.findAll().toString();

        }catch (Exception e)
        {
            logger.error(e.getMessage(),e);
        }


    }

    @Test
    public void test1()
    {

        String url = "http://127.0.0.1:8081/admin/remote/greet";

        try {
            HessianProxyFactory factory = new HessianProxyFactory();
            IGreetingService service = (IGreetingService) factory.create(
                    IGreetingService.class, url);


            String greeting = service.getGreeting();


        }catch (Exception e)
        {
            logger.error(e.getMessage(),e);
        }


    }

}
