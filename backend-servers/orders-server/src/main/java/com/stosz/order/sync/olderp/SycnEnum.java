package com.stosz.order.sync.olderp;

import com.stosz.order.ext.model.SystemType;
import com.stosz.order.mapper.order.SystemTypeMapper;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.IEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @auther carter
 * create time    2017-11-16
 * 更新一下系统的枚举信息到表中；
 */
@Component
public class SycnEnum implements InitializingBean{

    private static final Logger logger = LoggerFactory.getLogger(SycnEnum.class);
    
    @Autowired
    private SystemTypeMapper systemTypeMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void sycnSystemType()
    {
        //1,扫描所有的实现了IEnum的类；
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        String pkg = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath("com.stosz") + "/**/*.class";
        Resource[] resources;
        try {
            resources = resolver.getResources(pkg);
            for (Resource resource : resources) 
            {
                if (!resource.isReadable())  continue;
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                String classname = metadataReader.getClassMetadata().getClassName();
                Class<?> cls = null;
                try {
                    cls = Class.forName(classname);
                } catch (ClassNotFoundException e)
                {
                    //找不到直接丢弃，不用抛出异常；
                }finally
                {
                    if(null == cls) continue;
                    if (IEnum.class.isAssignableFrom(cls))  updateSystemType(cls);
                }
            }
        } catch (IOException e) {
            logger.error("扫描注解出错,"+e.getMessage(),e);
        }

    }

    /*
    **更新枚举对应的数据库记录；
     */
    private void updateSystemType(Class<?> cls) {
        logger.info("枚举类型："+cls.getSimpleName() );
        SystemType systemTypeParam = new SystemType();
        systemTypeParam.setTypeKey(cls.getSimpleName());
        systemTypeParam.setParentId(null);
        List<SystemType> findParentSystemTypeList = systemTypeMapper.findByParam(systemTypeParam);

        Long parent_id;
        if(CollectionUtils.isNullOrEmpty(findParentSystemTypeList))
        {
            SystemType systemType = new SystemType();
            systemType.setTypeKey(cls.getSimpleName());
            systemType.setCreatorId(0);
            systemType.setCreator("系统");
            systemType.setTypeDesc(cls.getSimpleName()+"枚举");
            systemType.setTypeValue(cls.getName());
            systemType.setParentId(0L);
            systemTypeMapper.insert(systemType);
            parent_id = (long)systemType.getId();
        }else
        {
            parent_id = (long)findParentSystemTypeList.get(0).getId();
        }

        Method method;
        try {
            method = cls.getMethod("values");
            IEnum inter[] = (IEnum[]) method.invoke(null, new Object[]{});
            for (int i=0;i< inter.length;i++)
            {
                IEnum enumInstance = inter[i];

                SystemType param_findChildSystemType = new SystemType();
                param_findChildSystemType.setTypeKey(String.valueOf(i));
                param_findChildSystemType.setParentId(parent_id);
                List<SystemType>  systemTypeListFindByChildAndParentId = systemTypeMapper.findByParam(param_findChildSystemType);

                //2,提炼出枚举类型；

                SystemType systemType = new SystemType();
                systemType.setTypeKey(String.valueOf(i));
                systemType.setCreatorId(0);
                systemType.setCreator("系统");
                systemType.setTypeDesc(enumInstance.name());
                systemType.setTypeValue(enumInstance.display());
                systemType.setParentId(parent_id);


                /////存入redis;
                stringRedisTemplate.opsForValue().set("enum:" + enumInstance.getClass().getName()+":" +enumInstance.name(),enumInstance.display());

                //3,插入数据库；
                if(CollectionUtils.isNullOrEmpty(systemTypeListFindByChildAndParentId))
                {//插入
                    systemTypeMapper.insert(systemType);
                }else
                {//更新
                    systemType.setId( systemTypeListFindByChildAndParentId.get(0).getId());
                    systemTypeMapper.update(systemType);
                }
            }


        } catch (Exception e) {
//            logger.error("提取枚举方法更新系统类型失败,"+e.getMessage(),e);
        }
    }


    public static void main(String[] args) {
        new SycnEnum().sycnSystemType();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
//        sycnSystemType();
        // TODO: 2017/12/28  
    }
}
