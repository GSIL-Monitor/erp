package com.stosz.purchase.service;

import com.stosz.admin.ext.model.Department;
import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.purchase.ext.enums.ErrorGoodsItemEvent;
import com.stosz.purchase.ext.model.ErrorGoods;
import com.stosz.purchase.ext.model.ErrorGoodsItem;
import com.stosz.purchase.mapper.ErrorGoodsItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 错货单明细的service
 * @author xiongchenyang
 * @version [1.0 , 2017/11/11]
 */
@Service
public class ErrorGoodsItemService {

    @Resource
    private ErrorGoodsItemMapper mapper;
    @Resource
    private UserAuthorityService userAuthorityService;

    @Resource
    private FsmProxyService<ErrorGoodsItem> errorGoodsItemFsmProxyService;
    @Resource
    private FsmHistoryService fsmHistoryService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void insert(ErrorGoodsItem errorGoodsItem){
        Assert.notNull(errorGoodsItem,"要添加的错货单明细不允许为空！");
        int i = mapper.insert(errorGoodsItem);
        errorGoodsItemFsmProxyService.processNew(errorGoodsItem, errorGoodsItem.getMemo());
        errorGoodsItemFsmProxyService.processEvent(errorGoodsItem, ErrorGoodsItemEvent.create, errorGoodsItem.getMemo());
        Assert.isTrue(i==1,"添加错货单明细失败！");
        logger.info("添加错货单明细{}成功！",errorGoodsItem);
    }

    public void update(ErrorGoodsItem errorGoodsItem){
        Assert.notNull(errorGoodsItem, "要修改的错货单明细不允许为空！");
        Integer id = errorGoodsItem.getId();
        Assert.notNull(id, "要修改的错货单明细id不允许为空！");
        ErrorGoodsItem errorGoodsItemDB = mapper.getById(id);
        Assert.notNull(errorGoodsItemDB,"要修改的错货单明细在数据库中不存在！");
        int i = mapper.update(errorGoodsItem);
        Assert.isTrue(i==1,"修改错货单明细失败！");
        logger.info("将错货单明细{}修改为{}成功！",errorGoodsItemDB,errorGoodsItem);
    }

    public void delete(Integer id){
        Assert.notNull(id,"要删除的错货单明细不存在！");
        ErrorGoodsItem errorGoodsItemDB = mapper.getById(id);
        Assert.notNull(errorGoodsItemDB,"要删除的错货单明细在数据库中不存在！");
        int i = mapper.delete(id);
        Assert.isTrue(i==1,"删除错货单明细失败！");
        logger.info("将错货单明细{}删除成功！",errorGoodsItemDB);
    }

    public ErrorGoodsItem getById(Integer id ){
        Assert.notNull(id,"要查询的错货单明细id不允许为空！");
        return mapper.getById(id);
    }

    public List<ErrorGoodsItem> find(ErrorGoodsItem errorGoodsItem){
        Assert.notNull(errorGoodsItem, "查询条件不允许为null");
        List<Department> departmentList = userAuthorityService.findList();
        // 判断该用户是否有权限访问部门，如果获取的部门列表为空，那么直接抛错；
        if(CollectionUtils.isNullOrEmpty(departmentList)){
            throw new RuntimeException("您没有配置任何访问采购单的数据权限，如要访问，请联系管理员！！！");
        }
        List<Integer> deptIdList = departmentList.stream().map(Department::getId).collect(Collectors.toList());
        Integer deptId = errorGoodsItem.getDeptId();
        if(deptId != null && !deptIdList.contains(deptId)){
            throw new RuntimeException("您目前没有权限查询该部门的信息，如要访问，请联系管理员！！！");
        }
        //如果搜索条件中带了部门，那么不带数据权限的搜索
        if(deptId != null){
            deptIdList  = new ArrayList<>();
        }
        Assert.notNull(errorGoodsItem,"要查询的错货明细不允许为空！");
        return mapper.findByParam(errorGoodsItem,deptIdList);
    }

    public List<ErrorGoodsItem> findByErrorId(Integer errorId){
        Assert.notNull(errorId,"要查询的错货单id不允许为空！");
        return mapper.findByErrorId(errorId);
    }

    public Map<Integer,String> findProduct(List<ErrorGoods> errorGoods){
        List<ErrorGoodsItem> errorGoodsItemList = mapper.findProduct(errorGoods);
        Map<Integer,String > productMap = new HashMap<>();
        for (ErrorGoodsItem errorGoodsItem: errorGoodsItemList
             ) {
            productMap.put(errorGoodsItem.getErrorId(),errorGoodsItem.getProductTitle());
        }
        return productMap;
    }

    public Integer count(ErrorGoodsItem errorGoodsItem){
        Assert.notNull(errorGoodsItem, "查询条件不允许为null");
        List<Department> departmentList = userAuthorityService.findList();
//        判断该用户是否有权限访问部门，如果获取的部门列表为空，那么直接抛错；
        if(CollectionUtils.isNullOrEmpty(departmentList)){
            throw new RuntimeException("您没有配置任何访问采购单的数据权限，如要访问，请联系管理员！！！");
        }
        List<Integer> deptIdList = departmentList.stream().map(Department::getId).collect(Collectors.toList());
        Integer deptId = errorGoodsItem.getDeptId();
        if(deptId != null && !deptIdList.contains(deptId)){
            throw new RuntimeException("您目前没有权限查询该部门的信息，如要访问，请联系管理员！！！");
        }
        //如果搜索条件中带了部门，那么不带数据权限的搜索
        if(deptId != null){
            deptIdList  = new ArrayList<>();
        }
        Assert.notNull(errorGoodsItem,"要查询的错货明细不允许为空！");
        return mapper.countByParam(errorGoodsItem,null);
    }


    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void processEvent(Integer id, ErrorGoodsItemEvent errorGoodsItemEvent, String memo, String uid) {
        ErrorGoodsItem errorGoodsItem = getById(id);
        Assert.notNull(errorGoodsItem, "错货明细不允许为空！");
        if (StringUtils.isBlank(uid)) {
            uid = MBox.getSysUser();
        }
        this.errorGoodsItemFsmProxyService.processEvent(errorGoodsItem, errorGoodsItemEvent, uid, LocalDateTime.now(), memo);
    }

    /**
     * 根据错货明细查询状态机历史记录
     *
     * @param id id
     * @return 历史
     */
    public RestResult queryHistory(Integer id, Integer start, Integer limit) {
        return fsmHistoryService.queryHistory("ErrorGoodsItem", id, start, limit);
    }

}
