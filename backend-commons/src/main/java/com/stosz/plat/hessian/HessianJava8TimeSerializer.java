package com.stosz.plat.hessian;

import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.AbstractSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @auther carter
 * create time    2017-11-29
 * 参考sql.data 写一个解析器；
 */
public class HessianJava8TimeSerializer extends AbstractSerializer {

    @Override
    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        if (obj == null)
            out.writeNull();
        else {
            Class cl = obj.getClass();

            if (out.addRef(obj))
                return;
            
            int ref = out.writeObjectBegin(cl.getName());

            if (ref < -1) {
                out.writeString("value");
                writeDate(obj, out);
                out.writeMapEnd();
            } else {
                if (ref == -1) {
                    out.writeInt(1);
                    out.writeString("value");
                    out.writeObjectBegin(cl.getName());
                }
                writeDate(obj, out);
            }
        }
    }

    private void writeDate(Object obj, AbstractHessianOutput out) throws IOException {

//        long  time;
//        if (obj instanceof LocalDate)
//        {
//            time = ((LocalDate) obj).toEpochDay();
//        }else if(obj instanceof LocalDateTime)
//        {
        long   time = ((LocalDateTime) obj).toInstant(ZoneOffset.UTC).getEpochSecond();
//        }else if(obj instanceof LocalTime)
//        {
//            time = ((LocalTime) obj).toSecondOfDay();
//        }else{
//           time = ((Date) obj).getTime();
//        }

        out.writeUTCDate(time);
    }
}
