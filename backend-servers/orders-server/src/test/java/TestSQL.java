import com.google.common.collect.Sets;
import com.stosz.order.ext.enums.OrderStateEnum;
import org.apache.ibatis.jdbc.SQL;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @auther carter
 * create time    2017-12-09
 * mybatic的 SQL对象研究
 */
public class TestSQL {

    public static final Logger logger = LoggerFactory.getLogger(TestSQL.class);
    AtomicInteger atomicInteger = new AtomicInteger(1);

    @Test
    public void testSql()
    {

        SQL sql = new SQL();

        //更新
        print(sql.UPDATE("src/main/webapp/test"));
        print(sql.SET("a=2","b=3","c=4"));
        print(sql.WHERE("a=1"));


        //插入
        sql = new SQL();
        print(sql.INSERT_INTO("test1"));
        print(sql.VALUES("a,b","1,2"));


        //删除
        sql = new SQL();
        print(sql.DELETE_FROM("test2"));
        print(sql.WHERE("a=1"));

        //查询
        sql = new SQL();
        print(sql.SELECT("a,b,c"));
        print(sql.FROM("test3"));
        print(sql.WHERE("a=1"));
        print(sql.AND().WHERE("b=3"));
        print(sql.WHERE("b=3"));


    }


    @Test
    public void test2()
    {

        SQL sql =
        new SQL().SELECT("o.*")
                .FROM("orders o ")
                .LEFT_OUTER_JOIN("orders_item oi ON oi.order_id = o.id ")
                .WHERE("o.bu_department_id=#{dept} ")
                .AND()
                .WHERE("oi.sku = #{sku}")
                .AND()
                .WHERE("o.order_status_enum in("+ OrderStateEnum.auditPass.ordinal()+","+OrderStateEnum.waitPurchase.ordinal()+")")
                .ORDER_BY("o.createAt asc ");

        logger.info(sql.toString());
    }

    @Test
    public void test3()
    {
      String str  =   String.format("转寄仓库存占用成功,转寄仓id:{},转寄仓名称：{},原来的订单id：{},原来的运单号{},库位：{}，当前包裹信息：{}",
              "a","b","c","d","e","xxx");

        logger.info(str);


        String collect = Sets.newHashSet(1, 2, 100).stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",", "(", ")"));
        logger.info(collect);



        SQL sql = new SQL()
                .SELECT(" o.seo_department_id as dept , sum(item.qty) as qty")
                .FROM("orders o ")
                .JOIN("orders_item item ON item.order_id = o.id ")
                .WHERE("item.sku ='xxxx'")
                .AND()
                .WHERE("o.zone_id in "+Sets.newHashSet(1,2).stream().map(item->String.valueOf(item)).collect(Collectors.joining(",","(",")")))
                .AND()
                .WHERE("o.order_status_enum in "+Sets.newHashSet(OrderStateEnum.auditPass,OrderStateEnum.waitPurchase).stream().map(item->String.valueOf(item.ordinal())).collect(Collectors.joining(",","(",")")))
                .GROUP_BY("o.seo_department_id ");

        print(sql);

    }

    private void print(SQL sql) {

        logger.info(atomicInteger.getAndIncrement() +  ",  "+sql.toString());
        logger.info("=======================================================================");



    }

}
