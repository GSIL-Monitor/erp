package com.stosz.order.mapper.crm;

import com.stosz.crm.ext.CustomerSearchParam;
import com.stosz.crm.ext.model.Customers;
import com.stosz.crm.ext.model.SystemTimeDict;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author
 */
@Repository
public interface CustomersMapper {

    @InsertProvider(type = CustomersBuilder.class,method = "insert")
    int insert(Customers param);

    @DeleteProvider(type = CustomersBuilder.class, method = "delete")
    int delete(@Param("id") Integer id);

    @UpdateProvider(type = CustomersBuilder.class, method = "update")
    int update(Customers param);

    @SelectProvider(type = CustomersBuilder.class, method = "find")
    List<Customers> find(Customers param);

    @SelectProvider(type = CustomersBuilder.class, method = "count")
    int count(Customers param);

    @SelectProvider(type = CustomersBuilder.class, method = "getById")
    Customers getById(@Param("id") Integer id);

    @SelectProvider(type = CustomersBuilder.class, method = "getByTelphone")
    Customers getByTelphone(@Param("tel") String tel);


    @Select("select * from system_time_dict d where d.system = 'crm' and d.type = 'credit'")
    SystemTimeDict findCrmCreditDict();

    @Update("update system_time_dict d set last_time = #{endTime} where d.system = 'crm' and d.type = 'credit'")
    int updateCrmCreditDict(@Param("endTime") LocalDateTime endTime);


    @Insert("<script>\n" +
            "INSERT INTO customer (telphone,first_name, last_name, country, email, zipcode, level_enum, province, city, area, address, zone_id, code_type ,reject_qty, accept_qty , set_level_enum, state) \n" +
            "VALUES \n" +
            "<foreach collection =\"list\" item=\"customer\" index= \"index\" separator =\",\"> \n" +
            "\t(#{customer.telphone},#{customer.firstName},#{customer.lastName},#{customer.country},#{customer.email},#{customer.zipcode},#{customer.levelEnum},#{customer.province},#{customer.city}, #{customer.area}, #{customer.address}, #{customer.zoneId}, #{customer.codeType} ,#{customer.rejectQty}, #{customer.acceptQty} , #{customer.setLevelEnum}, #{customer.state}) \n" +
            "</foreach>\n" +
            "ON DUPLICATE KEY UPDATE \n" +
            "accept_qty = accept_qty + VALUES(accept_qty),\n" +
            "reject_qty = reject_qty +  VALUES(reject_qty),\n" +
            "level_enum = CASE WHEN reject_qty in (0,1) THEN 0\n" +
            "\t\t\t\t\t\t\t\t\tWHEN reject_qty in (2) THEN 1\n" +
            "\t\t\t\t\t\t\t\t\tELSE 2\n" +
            "\t\t\t\t\t\t END;\n" +
            "</script>\t")
    int insertBatchDuplicateKeyUpdate(List<Customers> list);

    @Select("<script>\n" +
            "select * from customer c\n" +
            "where 1=1  \n" +
            "<if test=\"telphone != null and telphone != '' \">  \n" +
            "and telphone = #{telphone}\n" +
            "</if>\n" +
            "<if test=\"levelEnum!= null\">  \n" +
            "and level_enum = #{levelEnum}\n" +
            "</if>\n" +
            "<if test=\"zoneId!= null\">  \n" +
            "and zone_id = #{zoneId}\n" +
            "</if>\n" +
//            "<if test=\"zoneCode!= null and zoneCode != '' \">  \n" +
//            "and zone_code = #{zoneCode}\n" +
//            "</if>\n" +
            "<if test=\"acceptQty!= null\">  \n" +
            "and accept_qty = #{acceptQty}\n" +
            "</if>  \n" +
            "<if test=\"rejectQty!= null \">  \n" +
            "and reject_qty = #{rejectQty}\n" +
            "</if> \n" +
            "<if test=\"codeType!= null\">  \n" +
            "and code_type = #{codeType}\n" +
            "</if> \n" +
            "<if test=\"keyWord!= null and keyWord!= '' \">  \n" +
            "and(concat( first_name, last_name) = #{keyWord}\n" +
            "or telphone = #{keyWord}\n" +
            "or email = #{keyWord})\n" +
            "</if>  \n" +
            "<if test=\"minCreateAt != null \">  \n" +
            "            and create_at &gt; #{minCreateAt}  \n" +
            " </if>  \n" +
            "<if test=\"maxCreateAt != null \">  \n" +
            "            and create_at  &lt;  #{maxCreateAt}  \n" +
            " </if>  \n" +
//            "<if test=\"limit!= null and start!=null \">  \n" +
//            "limit #{limit} offset #{start}"+
//            "</if>  \n" +
            "</script>")
    List<Customers> findCustomers(CustomerSearchParam param);


    @Select("<script>\n" +
            "select count(*) from customer c\n" +
            "where 1=1  \n" +
            "<if test=\"telphone != null and telphone != '' \">  \n" +
            "and telphone = #{telphone}\n" +
            "</if>\n" +
            "<if test=\"levelEnum!= null \">  \n" +
            "and level_enum = #{levelEnum}\n" +
            "</if>\n" +
            "<if test=\"zoneId!= null\">  \n" +
            "and zone_id = #{zoneId}\n" +
            "</if>\n" +
//            "<if test=\"zoneCode!= null and zoneCode != '' \">  \n" +
//            "and zone_code = #{zoneCode}\n" +
//            "</if>\n" +
            "<if test=\"acceptQty!= null\">  \n" +
            "and accept_qty = #{acceptQty}\n" +
            "</if>  \n" +
            "<if test=\"rejectQty!= null \">  \n" +
            "and reject_qty = #{rejectQty}\n" +
            "</if> \n" +
            "<if test=\"codeType!= null\">  \n" +
            "and code_type = #{codeType}\n" +
            "</if> \n" +
            "<if test=\"keyWord!= null and keyWord!= '' \">  \n" +
            "and(concat( first_name, last_name) = #{keyWord}\n" +
            "or telphone = #{keyWord}\n" +
            "or email = #{keyWord})\n" +
            "</if>  \n" +
            "<if test=\"minCreateAt != null\">  \n" +
            "            and create_at &gt; #{minCreateAt}  \n" +
            " </if>  \n" +
            "<if test=\"maxCreateAt != null \">  \n" +
            "            and create_at  &lt;  #{maxCreateAt}  \n" +
            " </if>  \n" +
            "</script>")
    Integer countCustomers(CustomerSearchParam param);

}
