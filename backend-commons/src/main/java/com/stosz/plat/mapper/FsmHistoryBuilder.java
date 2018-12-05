package com.stosz.plat.mapper;

import com.stosz.plat.common.SpringContextHolder;
import com.stosz.plat.model.FsmHistory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Optional;

public class FsmHistoryBuilder extends AbstractBuilder<FsmHistory>{
    private Logger logger = LoggerFactory.getLogger(FsmHistoryBuilder.class);


//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    public String createHistory(){
        SQL sql = new SQL();
        sql.INSERT_INTO(getTableName());
        sql.VALUES("create_at", "current_timestamp()");
        sql.VALUES( "fsm_name", "#{fsmName}");
        sql.VALUES( "object_id", "#{objectId}");
        sql.VALUES( "parent_id", "#{parentId}");
        sql.VALUES( "event_name", "#{eventName}");
        sql.VALUES( "src_state", "#{srcState}");
        sql.VALUES( "dst_state", "#{dstState}");
        sql.VALUES("opt_uid","#{optUid}");
        sql.VALUES( "memo", "#{memo}");
        return sql.toString();
    }

    public String createHistoryDisplay(@Param("fsmName") String fsmName, @Param("objectId") Integer objectId, @Param("parentId") Integer parentId, @Param("eventName") String eventName, @Param("srcState") String srcState, @Param("dstState") String dstState, @Param("optUid") String optUid, @Param("memo") String memo, @Param("id") Integer id){
        SQL sql = new SQL();
        sql.INSERT_INTO(getTableName());
        sql.VALUES("create_at", "current_timestamp()");

        sql.VALUES( "fsm_name", append(fsmName));
        sql.VALUES( "object_id", String.valueOf(objectId));
        sql.VALUES( "parent_id", String.valueOf(parentId));
        sql.VALUES( "event_name", append(eventName));
        sql.VALUES( "event_name_display", getEventDisplay(eventName,fsmName));
        sql.VALUES( "src_state", append(srcState));

        String srcStateDisplay = getStateDisplay(srcState,fsmName);
        sql.VALUES( "src_state_display", srcStateDisplay);
        sql.VALUES( "dst_state", append(dstState));
        String dstStateDisplay = getStateDisplay(dstState,fsmName);
        sql.VALUES( "dst_state_display", dstStateDisplay);
        sql.VALUES("opt_uid",append(optUid));
        sql.VALUES( "memo", append(memo));
        return sql.toString();




    }


    private static String getFsmName(String fsmName)
    {
        String[] fsmAndEnumTypeArray= fsmName.split("|");
        if(fsmAndEnumTypeArray.length == 3)
        {
            fsmName = fsmAndEnumTypeArray[0];
        }
        return "\'"+fsmName+"\'";
    }

    private static String append(String value)
    {
        return "\'"+value+"\'";
    }

    private String getStateDisplay(String stateName, String fsmName) {
        return getFromRedis(stateName,fsmName,false);
    }

    private String getEventDisplay(String stateName,String fsmName) {
        return getFromRedis(stateName,fsmName,true);
    }

    private String getFromRedis(String stateName,String fsmName,boolean isEvent) {

        final String[] enumName = {stateName};
        StringRedisTemplate stringRedisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);

        Optional.ofNullable(stringRedisTemplate).ifPresent(item->{

            String key = "enum:"+item.opsForValue().get("fsm."+fsmName+".stateEnum")+":"+stateName;
            if(isEvent)
            {
                 key = "enum:"+item.opsForValue().get("fsm."+fsmName+".eventEnum")+":"+stateName;
            }

          enumName[0] =Optional.ofNullable(item.opsForValue().get(key)).orElse(stateName);
//           logger.info("枚举的名字："+enumName[0]);
        });

        return "\'"+enumName[0]+"\'";

    }



    public String queryByNameAndId() {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(getTableName());
        where(sql, _this("fsm_name"), "fsmName");
        where(sql, _this("object_id"), "objectId");
        sql.ORDER_BY("create_at desc");
        StringBuilder sb = new StringBuilder(sql.toString());
//        if (searchPage != null && (searchPage.getOffset() != null || searchPage.getLimit() != null)) {
//            sb.append(" limit ");
//            if (searchPage.getOffset() == null) {
//                searchPage.setOffset(SearchPage.DEFAULT_OFFSET);
//            }
//            if (searchPage.getLimit() == null) {
//                searchPage.setLimit(SearchPage.DEFAULT_LIMIT);
//            }
//            sb.append(" limit ").append(searchPage.getOffset()).append(",");
//            sb.append(searchPage.getLimit());
//        }
        return sb.toString();
    }

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, FsmHistory param) {

    }
}
