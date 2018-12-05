package com.stosz.purchase.sync;

import com.stosz.olderp.ext.model.OldErpSupplier;
import com.stosz.olderp.ext.service.IOldErpSupplierService;
import com.stosz.plat.utils.StringUtils;
import com.stosz.purchase.ext.model.Supplier;
import com.stosz.purchase.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/27]
 */
@Service
public class OldErpSupplierSyncService {

    @Resource
    private IOldErpSupplierService iOldErpSupplierService;
    @Resource
    private SupplierService supplierService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void pull(){
        List<OldErpSupplier> oldErpSupplierList = iOldErpSupplierService.findAll();
        Assert.notNull(oldErpSupplierList,"老erp的供应商查询出错！");
        for (OldErpSupplier oldErpSupplier: oldErpSupplierList) {
            Supplier supplier = new Supplier();
            String name = oldErpSupplier.getTitle();
            String phone = oldErpSupplier.getPhone();
            String address = oldErpSupplier.getAddress();
            supplier.setName(name);
            supplier.setAddress(address);
            supplier.setPhone(phone);
            if(StringUtils.isBlank(name)){
                break;
            }
            Supplier supplierDB = supplierService.getByName(name);
            if(supplierDB == null){
                supplier.setPlatId(1);
                supplier.setPlatName("阿里巴巴");
                supplier.setCreator("系统");
                supplier.setCreatorId(0);
                supplierService.insert(supplier);
            }else {
                supplierDB.setAddress(address);
                supplierDB.setPhone(phone);
                supplierService.update(supplierDB);
            }
        }
        logger.info("导入老erp供应商信息成功！");
    }
}
