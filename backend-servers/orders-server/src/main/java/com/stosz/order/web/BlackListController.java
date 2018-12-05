package com.stosz.order.web;


import com.stosz.order.ext.enums.BlackTypeEnum;
import com.stosz.order.ext.model.BlackList;
import com.stosz.order.service.BlackListService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.ExcelUtil;
import com.stosz.plat.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders/base/black_list/")
public class BlackListController extends AbstractController {

    @Autowired
    private BlackListService blackListService;


    @RequestMapping(value = "find")
    public RestResult find(BlackList blackList) {
        return blackListService.findBlackList(blackList);
    }

    @PostMapping(value = "delete")
    public RestResult delete(@RequestParam("id") Integer id) {
        RestResult result = new RestResult();
        blackListService.deleteBlackList(id);
        result.setCode(RestResult.NOTICE);
        result.setDesc("删除成功");
        return result;
    }

    /**
     * 黑名单所有创建人
     * @return
     */
    @RequestMapping(value = "creators")
    public RestResult findBlackListCreator() {
        return blackListService.findBlackListCreator();
    }

    /**
     * 导出动作，从session中获取查询条件并查询导出
     * @param request
     * @param response
     */
    @RequestMapping(value = "export")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        BlackList blackList = (BlackList) request.getSession().getAttribute("export");
        List<BlackList> blackLists = blackListService.findByBlackList(blackList);
        String[] excludeFields = {"blackLevelKey","creatorId"};
        String[] headers = {"ID","类型","内容","时间","创建人"};
        try {
            ExcelUtil.exportExcelAndDownload(response,"黑名单列表","导出数据",headers,blackLists,excludeFields);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ;
    }

    /**
     * 导出预操作，目的是将查询条件存在session中。ajax无法下载文件。
     * @param blackList
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("preExport")
    public RestResult preExport(BlackList blackList, HttpServletRequest request) throws IOException {
        request.getSession().setAttribute("export", blackList);
        RestResult result = new RestResult();
        result.setCode(RestResult.OK);
        result.setDesc("请求成功");
        return result;
    }



    /**
     * 导入数据
     * @param content
     * @return
     */
    @PostMapping(value = "import")
    public RestResult importBlackList(@RequestParam("content") String content) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(content));

        UserDto user = ThreadLocalUtils.getUser();

        List<FailedImport> failResult = new ArrayList<>();
        List<String> succResult = new ArrayList<>();
        Integer totalCount = 0;

        String line = "";
        BlackTypeEnum type;
        String item = "";
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            totalCount ++;
            String[] arr = line.split("\t");
            if (arr.length != 2) {
                failResult.add(new FailedImport(line,"数据格式不正确"));
                continue;
            }
            try {
                Assert.hasText(arr[0], "类型不能为空");
                Assert.hasText(arr[1], "数据不存在");
                type = BlackTypeEnum.fromName(arr[0]);
                item = arr[1];
                Assert.notNull(type, "类型不正确");
            } catch (Exception e) {
                failResult.add(new FailedImport(line,e.getMessage()));
                continue;
            }

            // 检查重复
            List<BlackList> existedBlackList = blackListService.findByContent(arr[1]);
            if (existedBlackList != null && existedBlackList.size() != 0) {
                failResult.add(new FailedImport(arr[0] + " " + arr[1] ,"数据已存在"));
                continue;
            }

            BlackList  newBlackList = new BlackList();
            newBlackList.setBlackTypeEnum(type);
            newBlackList.setContent(item);
            newBlackList.setCreatorId(user.getId());
            newBlackList.setCreator(user.getLastName());
            int count = blackListService.addBlackList(newBlackList);
            if(count != 1){
                failResult.add(new FailedImport(line,"导入异常"));
                continue;
            }
            succResult.add(line);
        }

        Map map = new HashMap<>();
        map.put("succList",succResult);
        map.put("failList",failResult);
        map.put("totalCount",totalCount);
        RestResult result = new RestResult();
        result.setCode(RestResult.NOTICE);
        result.setItem(map);
        result.setDesc("数据导入成功！");
        logger.info("导入黑名单成功：{}", succResult);
        return result;
    }

    public static class FailedImport{
        private String item;
        private String reason;

        public FailedImport(String item, String reason) {
            this.item = item;
            this.reason = reason;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        @Override
        public String toString() {
            return "FailedImport{" +
                    "item='" + item + '\'' +
                    ", reason='" + reason + '\'' +
                    '}';
        }
    }

}
