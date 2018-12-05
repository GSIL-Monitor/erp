package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpBlackListMapper;
import com.stosz.olderp.ext.model.OldErpBlackList;
import com.stosz.olderp.ext.service.IOldErpBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther carter
 * create time    2017-12-03
 */
@Service
public class OldErpBlackListService implements IOldErpBlackListService {

    @Autowired
    private OldErpBlackListMapper oldErpBlackListMapper;

    @Override
    public List<OldErpBlackList> getByIdRegion(int start, int fetch_count) {
        return oldErpBlackListMapper.getByIdRegion(start, fetch_count);
    }

    @Override
    public int getOldMaxId() {
        return oldErpBlackListMapper.getOldMaxId();
    }
}
