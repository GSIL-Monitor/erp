package com.stosz.plat.web;

import com.stosz.plat.common.CommonException;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.SpringContextHolder;
import com.stosz.plat.enums.UploadTypeEnum;
import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.EnumUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.plat.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Description:
 * 
 * @author shiqiangguo
 * @date 2016年6月2日 下午6:43:18
 */
//@Controller
//@RequestMapping(value = "/commons")
public class CommonsController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Value("${file.uploadDir}")
    private String fileUploadDir;

    /**
     * 通用枚举查询方法，url:/commons/enums?enumCls=com.xike.backend.commons.enums.AppEnum
     *
     * @param enumCls
     * @param res
     */
    @RequestMapping(value = "/enums2", method = RequestMethod.GET)
    @ResponseBody
    public RestResult enums(@RequestParam String enumCls,
                            @RequestParam(defaultValue = "com.stosz.plat.enums") String enumPkg,
                            HttpServletResponse res) {
        // String enumCls = "com.xike.backend.commons.enums.AppEnum";
        res.setCharacterEncoding("utf-8");
        RestResult rs = getEnumJsonString(enumPkg, enumCls);
        return rs;
    }

    public RestResult getEnumJsonString(String pkg, String enumCls) {
        RestResult rs = new RestResult();
        String clsName = pkg + "." + enumCls;
        try {
            Class<?> cls = Class.forName(clsName);
            rs.setCode("OK");
            rs.setDesc(enumCls);
            List<Map<String, Object>> result = EnumUtils.enumValueToMap(cls);
            rs.setItem(result);
            return rs;
        } catch (SecurityException | IllegalArgumentException | ClassNotFoundException e) {
            logger.error("枚举转换字符串出现异常，类名：" + clsName, e);
            throw new CommonException("枚举类转换字符串时异常，类名:" + clsName + "异常：" + e.getMessage());
        }
    }

    // http://localhost:8080/kindom-server/system/getActiveProfiles
    @RequestMapping(method = RequestMethod.GET, value = "/getActiveProfiles")
    @ResponseBody
    public RestResult getActiveProfiles() {
        Environment e = SpringContextHolder.getApplicationContext().getEnvironment();
        String activeProfiles = String.join(",", e.getActiveProfiles());
        if (activeProfiles == null || activeProfiles.trim().length() <= 0) {
            activeProfiles = "DefaultProfiles : " + String.join(",", e.getDefaultProfiles());
        }
        return new RestResult(activeProfiles);
    }

    
    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public RestResult upload(@RequestParam(value ="file",required = true) MultipartFile file,@RequestParam(value = "type",required = true) UploadTypeEnum uploadTypeEnum) {
        RestResult result = new RestResult();
        File targetFile = null;
        //用于数据库存放的文件名称和返回值格式:/enum.name/yyyy/mm/yyyyMMddhhmmss_4位随机字母数字.jpg
        String absoluteFileName = "" ;
        try {
            String path = fileUploadDir + File.separator + uploadTypeEnum.name() + File.separator + DateUtils.getYearMonth();
            logger.info("文件存放目录{}", path);
            String fileName = file.getOriginalFilename();
            logger.info("fileName文件名称{}", fileName);
            //获取文件的后缀名称
    		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
    		//获取中文+字母4随机数
            String newFileName = StringUtil.getRandomCharAndNum(4);
            //获取
            String fileUploadDate = DateUtils.getUploadFileName();
            //拼装新的文件名次
            fileName = fileUploadDate + newFileName + "." + suffix;
            //拼装需要返回和存储在数据库中的文件名称
            absoluteFileName = File.separator + uploadTypeEnum.name() + File.separator + DateUtils.getYearMonth() + File.separator + fileName;
            logger.info("absoluteFileName文件名称{}", absoluteFileName);
            targetFile = new File(path, fileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            file.transferTo(targetFile);
        } catch (Exception e) {
            result.setCode(RestResult.FAIL);
            result.setDesc(e.getMessage());
            logger.error(e.getMessage(),e);
        }
        result.setCode(RestResult.OK);
        result.setItem(StringUtils.isEmpty(absoluteFileName)? "" : absoluteFileName);
//        Map<String,String> map = new HashMap<>();
//        map.put("mainImageUrl", StringUtils.isEmpty(absoluteFileName)? "" : absoluteFileName);
//        map.put("mainImageShowUrl", StringUtils.isEmpty(absoluteFileName)? "" : imagePrefix + absoluteFileName);
//        result.setItem(map);
        return result;
    }
    
    
}
