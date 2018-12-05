package com.stosz.order.sync.olderp;

import com.google.common.base.Strings;
import com.stosz.olderp.ext.model.OldErpBlackList;
import com.stosz.olderp.ext.service.IOldErpBlackListService;
import com.stosz.order.ext.enums.BlackLevelEnum;
import com.stosz.order.ext.enums.BlackTypeEnum;
import com.stosz.order.ext.model.BlackList;
import com.stosz.order.mapper.order.BlackListMapper;
import com.stosz.order.sync.olderp.core.AbstractSycn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther carter
 * create time    2017-11-07
 * 同步黑名单数据
 */
@Component
public class SycnBlackList extends AbstractSycn<OldErpBlackList>{


//    @Scheduled(fixedRate = 30000)
    public void pullBlackList()
    {
        super.pullById();
    }

    @Autowired
    private IOldErpBlackListService oldErpBlackListService;

    @Autowired
    private BlackListMapper blackListMapper;


    @Override
    protected List<OldErpBlackList> fetchByIdRegion(int start, int fetch_count) {
        return oldErpBlackListService.getByIdRegion(start, fetch_count);
    }

//    @Override
//    protected int insertNewTable(OldErpBlackList blackList) {
//
//        Assert.notNull(blackList);
//        Assert.isTrue(null!=blackList.getId() && blackList.getId()>0);
//        BlackList newBlackList = blackListMapper.getById(blackList.getId());
//        if(null == newBlackList || null == newBlackList.getId())
//        {
//
//            newBlackList = new BlackList();
//            newBlackList.setId(blackList.getIdBlacklist());
//            newBlackList.setContent(blackList.getTitle());
//            newBlackList.setBlackTypeEnum(getBlackType(blackList.getField()));
//            newBlackList.setBlackLevelEnum(getBlackLevel(blackList.getLevel()));
//            newBlackList.setCreateAt(LocalDateTime.now());
//            newBlackList.setUpdateAt(newBlackList.getCreateAt());
//            newBlackList.setCreatorId(0);
//            newBlackList.setCreator("系统");
//            return blackListMapper.insert(newBlackList);
//        }else
//        {
//            newBlackList = new BlackList();
//            newBlackList.setId(blackList.getIdBlacklist());
//            newBlackList.setContent(blackList.getTitle());
//            newBlackList.setBlackTypeEnum(getBlackType(blackList.getField()));
//            newBlackList.setBlackLevelEnum(getBlackLevel(blackList.getLevel()));
//            newBlackList.setCreateAt(LocalDateTime.now());
//            newBlackList.setCreatorId(0);
//            newBlackList.setCreator("系统");
//            return blackListMapper.update(newBlackList);
//        }
//
//    }

    private BlackTypeEnum getBlackType(String field) {
        if(Strings.isNullOrEmpty(field)) return BlackTypeEnum.address;
        if("email".equalsIgnoreCase(field)  || field.contains("@")) return BlackTypeEnum.email;
        if ("ip".equalsIgnoreCase(field) || field.contains(".")) return BlackTypeEnum.ip;
        if("phone".equalsIgnoreCase(field) || field.contains("phone")) return BlackTypeEnum.phone;

        return BlackTypeEnum.address;
    }

    private static BlackLevelEnum getBlackLevel(String levelString)
    {
        if("1".equalsIgnoreCase(levelString)) return BlackLevelEnum.warning;
        if("10".equalsIgnoreCase(levelString)) return BlackLevelEnum.blacklist;
        return BlackLevelEnum.other;

    }


    @Override
    protected String dataDesc() {
        return "黑名单数据（blacklist）";
    }
    @Override
    protected int getOldMaxId() {
        return oldErpBlackListService.getOldMaxId();
    }

    @Override
    protected int getNewMaxId() {
        return blackListMapper.getNewMaxId();
    }

    @Override
    protected Integer batchInsert(List<OldErpBlackList> itemList) {

        Assert.notNull(itemList,"批量插入的数据不能为空");
        List<BlackList> newBlackLists = itemList.stream().map(old -> {
            BlackList newBlackList = new BlackList();
            newBlackList.setId(old.getIdBlacklist());
            newBlackList.setContent(old.getTitle());
            newBlackList.setBlackTypeEnum(getBlackType(old.getField()));
            newBlackList.setBlackLevelEnum(getBlackLevel(old.getLevel()));
            newBlackList.setCreateAt(LocalDateTime.now());
            newBlackList.setCreatorId(0);
            newBlackList.setCreator("系统");
            return newBlackList;
        }).collect(Collectors.toList());

        return blackListMapper.insertBatch2(newBlackLists);
    }
}
