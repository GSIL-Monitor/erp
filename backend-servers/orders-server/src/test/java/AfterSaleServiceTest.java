import com.stosz.order.mapper.order.OrdersRmaBillItemMapper;
import com.stosz.order.service.AfterSaleService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class AfterSaleServiceTest extends BaseTest {
    public static final Logger logger = LoggerFactory.getLogger(AfterSaleServiceTest.class);

    @Autowired
    private AfterSaleService afterSaleService;

    @Autowired
    private OrdersRmaBillItemMapper ordersRmaBillItemMapper;


    @Test
    public void testCanReturnQty(){
        logger.info(""+afterSaleService.countRmaItemReturnQty(7, null));
    }

    @Test
    public void test(){
        List list = Arrays.asList(7,8);
        List l = ordersRmaBillItemMapper.findByRmaIds(list);
        logger.info(""+l.size());
    }
}
