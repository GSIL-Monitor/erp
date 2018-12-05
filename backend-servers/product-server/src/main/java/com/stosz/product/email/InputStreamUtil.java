package com.stosz.product.email;

import com.stosz.product.ext.model.ExcludeRepeat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 流转换
 * @author he_guitang
 * @version [1.0 , 2017/11/21]
 */
public class InputStreamUtil {
    public static final Logger logger = LoggerFactory.getLogger(InputStreamUtil.class);

    public static InputStream getInputStream(List<ExcludeRepeat> list) {
        try {
            ExportExcel e = new ExportExcel();
            ByteArrayOutputStream byteOut = e.export(list);
            byte[] byt = byteOut.toByteArray();
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byt);
            return byteIn;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

}
