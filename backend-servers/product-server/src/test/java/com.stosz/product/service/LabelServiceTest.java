package com.stosz.product.service;

import com.stosz.BaseTest;
import org.junit.Test;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author heguitang
 * @version [1.0 , 2018/1/5]
 */
public class LabelServiceTest extends BaseTest {

    @Resource
    private LabelService service;

    @Test
    public void update() throws Exception {
        service.findLabel("啊啊");
    }


}