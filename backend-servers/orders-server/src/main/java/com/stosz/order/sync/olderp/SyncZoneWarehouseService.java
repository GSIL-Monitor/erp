package com.stosz.order.sync.olderp;

import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.olderp.ext.model.ErpWarehouse;
import com.stosz.olderp.ext.model.ErpZoneWarehouse;
import com.stosz.olderp.ext.model.OldErpZone;
import com.stosz.olderp.ext.service.IErpWarehouseService;
import com.stosz.olderp.ext.service.IErpZoneWarehouseService;
import com.stosz.olderp.ext.service.IOldErpZoneService;
import com.stosz.order.ext.enums.UseableEnum;
import com.stosz.order.ext.model.ZoneWarehousePriority;
import com.stosz.order.service.ZoneWarehousePriorityService;
import com.stosz.order.sync.olderp.core.AbstractTimeSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SyncZoneWarehouseService extends AbstractTimeSync<ZoneWarehousePriority> {

    @Autowired
    private IErpZoneWarehouseService erpZoneWarehouseService ;

    @Autowired
    private ZoneWarehousePriorityService zoneWarehousePriorityService;

    @Autowired
    private IErpWarehouseService erpWarehouseService;

    @Autowired
    private IOldErpZoneService erpZoneService;

    @Autowired
    private IUserService userService;

    @Override
    protected List<ZoneWarehousePriority> fetch(Integer offset, Integer limit, LocalDateTime beginTime, LocalDateTime endTime) {
        List<ErpZoneWarehouse> list = erpZoneWarehouseService.findErpWarehouse(offset, limit, beginTime, endTime);
        if(list == null){
            return new ArrayList<>();
        }
        //list  去重
        Set<ErpZoneWarehouse> set = new HashSet<>(list);

//        List<Integer> warehouseIdList = set.stream().map(e -> e.getIdWarehouse()).collect(Collectors.toList());
        List<ErpWarehouse> erpWarehouseList = erpWarehouseService.findAll();
        Map<Integer,OldErpZone> oldErpZoneMap = erpZoneService.findAll().stream().collect(Collectors.toMap(OldErpZone::getId,Function.identity()));

        Map<Integer,ErpWarehouse> erpWarehouseMap = erpWarehouseList.stream().collect( Collectors.toMap(ErpWarehouse::getIdWarehouse, Function.identity()));

        Set userIdSet = erpWarehouseList.stream().map(e -> e.getCreatorId()).collect(Collectors.toSet());
        List<User> userList =  userService.findByIds(userIdSet);
        Map<Integer,User> userMap =  userList.stream().collect(  Collectors.toMap(User::getId, Function.identity()));


        List<ErpZoneWarehouse> newList = set.stream().collect(Collectors.toList());
        return newList.stream().filter(a -> oldErpZoneMap.get(a.getIdZone()) != null &&  erpWarehouseMap.get(a.getIdWarehouse()) != null).map(e-> {
            ZoneWarehousePriority z = new ZoneWarehousePriority();
            z.setZoneId(e.getIdZone());
            z.setWarehouseId(e.getIdWarehouse());
            z.setPriority(e.getLevel());
            z.setZoneName(oldErpZoneMap.get(e.getIdZone()) == null ? "异常数据" : oldErpZoneMap.get(e.getIdZone()).getTitle());
            z.setWarehouseName(erpWarehouseMap.get(e.getIdWarehouse()) == null ? "数据异常" : erpWarehouseMap.get(e.getIdWarehouse()).getTitle());
            z.setForbidSku("");
            z.setCreateAt(e.getCreateTime());
            z.setStatus(UseableEnum.use);
            z.setUpdateAt(e.getCreateTime());
            z.setCreatorId(e.getCreateUserId());
            z.setCreator(userMap.get(e.getCreateUserId()) == null ? "系统" : userMap.get(e.getCreateUserId()).getLastname());
            z.setForbidSpu("");
            return z;
        }).collect(Collectors.toList());
    }

    @Override
    protected Integer count() {
        return erpZoneWarehouseService.countErpWarehouse(beginTime, endTime);
    }

    @Override
    protected Integer insertBatch(List<ZoneWarehousePriority> list) {
        return zoneWarehousePriorityService.insertBatch(list);
    }

    public void setSyncSizeEachTime(Integer size){
        super.syncSizeEachTime = size;
    }

    public void setBeginTime(LocalDateTime beginTime){
        super.beginTime = beginTime;
    }

    public void setEndTime(LocalDateTime endTime){
        super.endTime = endTime;
    }

}
