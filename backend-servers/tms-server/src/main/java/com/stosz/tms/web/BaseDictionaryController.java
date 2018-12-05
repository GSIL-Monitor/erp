package com.stosz.tms.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.model.BaseDictionary;
import com.stosz.tms.model.Shipping;
import com.stosz.tms.service.BaseDictionaryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tms/dic")
public class BaseDictionaryController extends AbstractController {

    @Resource
    private BaseDictionaryService dictionaryService;

    @RequestMapping(method = RequestMethod.GET)
    public RestResult selectShippingList(
            @RequestParam(name = "type",required = true) String type
    ){
        RestResult restResult = new RestResult();

        final List<BaseDictionary> dictionaries = dictionaryService.query(type);
        restResult.setDesc("查询成功");
        restResult.setItem(dictionaries);
        return restResult;

    }
}
