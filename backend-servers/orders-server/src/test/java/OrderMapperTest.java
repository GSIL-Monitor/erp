import com.stosz.order.ext.service.IOrderService;
import com.stosz.order.mapper.order.OrdersMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderMapperTest extends BaseTest {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private IOrderService rderService;

    @Test
    public void test(){
        List list = new ArrayList<>();
        list.add(1);
        list.add(2);
        ordersMapper.updateIpIOrderQtyByOrderId(list,10);
    }

    @Test
    public void test1(){
        ordersMapper.queryOrdersByOrderIds(Arrays.asList("11,45"));
        //ordersMapper.updateIpIOrderQtyByOrderId(list,10);
    }

}
