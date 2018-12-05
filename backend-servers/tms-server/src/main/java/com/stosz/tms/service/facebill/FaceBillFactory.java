package com.stosz.tms.service.facebill;

import com.stosz.tms.service.facebill.impl.BaseFaceBill;
import com.stosz.tms.service.facebill.impl.FaceBillNIM;
import com.stosz.tms.service.facebill.impl.FaceBillTaiwan;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2018/1/24 15:34
 */
public class FaceBillFactory {

    public IFaceBillArrange facebillByApiCord(String apiCode) {
        IFaceBillArrange faceBillArrange;
        switch (apiCode) {
            case "Taiwan":
                faceBillArrange = new FaceBillTaiwan();
            case "NIM":
                faceBillArrange = new FaceBillNIM();
            default:
                faceBillArrange = new BaseFaceBill();
        }
        return faceBillArrange;
    }
}
