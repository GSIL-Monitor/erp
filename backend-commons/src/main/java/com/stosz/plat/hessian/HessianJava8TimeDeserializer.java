package com.stosz.plat.hessian;

import com.caucho.hessian.io.AbstractDeserializer;
import com.caucho.hessian.io.AbstractHessianInput;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @auther carter
 * create time    2017-11-29
 */
public class HessianJava8TimeDeserializer extends AbstractDeserializer {

    private Class _cl;
//    private Constructor _constructor;

    public HessianJava8TimeDeserializer(Class cl)
    {
//        try {
            _cl = cl;
//            _constructor = _cl.getc
//                    cl.getConstructor(/*new Class[] { long.class }*/);
//        } catch (NoSuchMethodException e) {
//            throw new HessianException(e);
//        }
    }

    public Class getType()
    {
        return _cl;
    }

    public Object readMap(AbstractHessianInput in)
            throws IOException
    {
        int ref = in.addRef(null);

        long initValue = Long.MIN_VALUE;

        while (! in.isEnd()) {
            String key = in.readString();

            if (key.equals("value"))
                initValue = in.readUTCDate();
            else
                in.readString();
        }

        in.readMapEnd();

        Object value = create(initValue);

        in.setRef(ref, value);

        return value;
    }

    public Object readObject(AbstractHessianInput in,
                             Object []fields)
            throws IOException
    {
        String []fieldNames = (String []) fields;

        int ref = in.addRef(null);

        long initValue = Long.MIN_VALUE;

        for (int i = 0; i < fieldNames.length; i++) {
            String key = fieldNames[i];

            if (key.equals("value"))
                initValue = in.readUTCDate();
            else
                in.readObject();
        }

        Object value = create(initValue);

        in.setRef(ref, value);

        return value;
    }

    private Object create(long initValue)
            throws IOException
    {
        if (initValue == Long.MIN_VALUE)
            throw new IOException(_cl.getName() + " expects name.");


//
//        if (_cl.equals(LocalDate.class))
//        {
//            //time = ((LocalDate) obj).toEpochDay();
//          return   LocalDate.ofEpochDay(initValue);
//
//        }else if(_cl.equals(LocalDateTime.class))
//        {
            //time = ((LocalDateTime) obj).toInstant(ZoneOffset.UTC).getEpochSecond();
            return LocalDateTime.ofEpochSecond(initValue,0,ZoneOffset.UTC);
//        }else if(_cl.equals(LocalTime.class))
//        {
//            //time = ((LocalTime) obj).toSecondOfDay();
//            return  LocalTime.ofSecondOfDay(initValue);
//        }else{

//        try {
//            return _constructor.newInstance(new Object[] { new Long(initValue) });
//        } catch (Exception e) {
//            throw new IOExceptionWrapper(e);
//        }
//
//            return initValue;
//
//        }
    }



}
