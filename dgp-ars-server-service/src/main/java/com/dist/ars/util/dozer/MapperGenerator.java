package com.dist.ars.util.dozer;

import cn.hutool.core.util.StrUtil;
import com.dist.base.utils.IdUtil;
import com.dist.base.utils.ObjectUtil;
import com.google.common.collect.ImmutableMap;
import javassist.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-工具类：dozer对象之间的转换接口实现类
 */
@Component
public class MapperGenerator implements IGenerator {

    private static final Logger log = LoggerFactory.getLogger(MapperGenerator.class);

    @Autowired
    protected Mapper dozerMapper;

    @Override
    public <T, S> T convert(S s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        return this.dozerMapper.map(s, clz);
    }

    @Override
    public <T, S> List<T> convert(List<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        List<T> list = new ArrayList<T>();
        for (S vs : s) {
            list.add(this.dozerMapper.map(vs, clz));
        }
        return list;
    }

    @Override
    public <T, S> Set<T> convert(Set<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        Set<T> set = new HashSet<T>();
        for (S vs : s) {
            set.add(this.dozerMapper.map(vs, clz));
        }
        return set;
    }

    @Override
    public <T, S> T[] convert(S[] s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(clz, s.length);
        for (int i = 0; i < s.length; i++) {
            arr[i] = this.dozerMapper.map(s[i], clz);
        }
        return arr;
    }

    /**
     * map转对象
     *
     * @param mapList
     * @param beanClass
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> mapConvert2Obj(List<? extends Map<String, ?>> mapList, Class<T> beanClass) {

        if (mapList == null) {
            return null;
        }

        List<T> objList = new ArrayList<>();

        for (Map<String, ?> map : mapList) {
            try {
                T obj = beanClass.newInstance();
                BeanUtils.populate(obj, map);
                objList.add(obj);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("map->dto转换失败！");
            }
        }

        return objList;
    }

    /**
     * 对象转map
     *
     * @param o
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> objConvert2Map(Object o) {
        return (Map<String, Object>) dozerMapper.map(o, Map.class);
    }


    private static final Map<Class<?>, String> typeCovertMap;

    private static final ClassPool classPool = ClassPool.getDefault();

    static {
        ImmutableMap.Builder<Class<?>, String> typeCovertMapBuilder = ImmutableMap.builder();

        typeCovertMapBuilder.put(Integer.class, "toInt");
        typeCovertMapBuilder.put(Double.class, "toDouble");
        typeCovertMapBuilder.put(Date.class, "toDate");
        typeCovertMapBuilder.put(Long.class, "toLong");
        typeCovertMapBuilder.put(BigDecimal.class, "toBigDecimal");
        typeCovertMapBuilder.put(Boolean.class, "toBool");

        typeCovertMap = typeCovertMapBuilder.build();
    }

    /**
     * 对象之间转换，忽略对象中的类型，根据name进行映射
     *
     * @param s
     * @param clz
     * @return
     */
    @Override
    public <T, S> T convertIgnoreType(S s, Class<T> clz) {
        // 找到字节码中的set方法，并按照参数类型分类
        Map<Method, Class<?>> basicTypeMethodClassMap = new HashMap<>();
        Map<Method, Class<?>> otherTypeMethodClassMap = new HashMap<>();
        this.classifyMethods(clz, basicTypeMethodClassMap, otherTypeMethodClassMap);

        try {
            Class<?> proxyClass = this.getProxyClass(clz, basicTypeMethodClassMap);
            return this.generateProxyObject(s, clz, otherTypeMethodClassMap, proxyClass);
        } catch (Exception e) {
            log.error("生成代理对象时出错:{}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T, S> List<T> convertIgnoreType(List<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        List<T> list = new ArrayList<>();

        this.generateProxyObjectCollection(s, clz, list);
        return list;
    }

    @Override
    public <T, S> Set<T> convertIgnoreType(Set<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        Set<T> set = new HashSet<>();
        this.generateProxyObjectCollection(s, clz, set);

        return set;
    }

    @SuppressWarnings("unchecked")
    private <T, S> T generateProxyObject(S s, Class<T> clz, Map<Method, Class<?>> otherTypeMethodClassMap, Class<?> proxyClass) throws Exception {
        Object proxyObject = proxyClass.newInstance();
        dozerMapper.map(s, proxyObject);

        for (Map.Entry<Method, Class<?>> entry : otherTypeMethodClassMap.entrySet()) {
            Method targetObjectSetMethod = entry.getKey();
            Class<?> argType = entry.getValue();

            // 获取源对象中get方法的入参对象
            String targetObjectSetMethodName = targetObjectSetMethod.getName(); // setxxx
            StringBuilder sb = new StringBuilder(targetObjectSetMethodName);
            sb.setCharAt(0, 'g');
            String sourceObjectGetMethodName = sb.toString();   // getxxx

            Method sourceObjectGetMethod;
            try {
                sourceObjectGetMethod = s.getClass().getMethod(sourceObjectGetMethodName);
            } catch (NoSuchMethodException | SecurityException ignored) {
                // 允许目标类中set方法没有源类对应的get方法
                continue;
            }
            if (Objects.nonNull(sourceObjectGetMethod)) {
                Object returnValue = sourceObjectGetMethod.invoke(s);

                Object o = null;

                if (Collection.class.isAssignableFrom(argType)) {

                    String fieldName = sourceObjectGetMethodName.substring(3, sourceObjectGetMethodName.length());
                    fieldName = StringUtils.uncapitalize(fieldName);

                    Class<?> collectionArgType = this.getGenericTypeWithCollection(clz, fieldName);

                    if (List.class.isAssignableFrom(argType)) {
                        o = this.convertIgnoreType((List<?>) returnValue, collectionArgType);
                    } else if (Set.class.isAssignableFrom(argType)) {
                        o = this.convertIgnoreType((Set<?>) returnValue, argType);
                    }
                } else {
                    o = this.convertIgnoreType(returnValue, argType);
                }
                if (Objects.nonNull(o)) {
                    targetObjectSetMethod.invoke(proxyObject, o);
                }
            }
        }

        return (T) proxyObject;
    }

    private <T, S> void generateProxyObjectCollection(Collection<S> sourceCollection, Class<T> clz, Collection<T> targetCollection) {
        // 找到字节码中的set方法，并按照参数类型分类
        Map<Method, Class<?>> basicTypeMethodClassMap = new HashMap<>();
        Map<Method, Class<?>> otherTypeMethodClassMap = new HashMap<>();
        this.classifyMethods(clz, basicTypeMethodClassMap, otherTypeMethodClassMap);

        try {
            Class<?> proxyClass = this.getProxyClass(clz, basicTypeMethodClassMap);
            for (S vs : sourceCollection) {
                targetCollection.add(this.generateProxyObject(vs, clz, otherTypeMethodClassMap, proxyClass));
            }
        } catch (Exception e) {
            log.error("生成代理对象时出错:{}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取集合的范性
     *
     * @param clz
     * @param fieldName
     * @param <T>
     * @return
     * @throws NoSuchFieldException
     */
    private <T> Class<?> getGenericTypeWithCollection(Class<T> clz, String fieldName) throws NoSuchFieldException {
        Field field = clz.getDeclaredField(fieldName);
        field.setAccessible(true);

        Type genericType = field.getGenericType();
        if (genericType == null) {
            return null;
        }
        // 如果是泛型参数的类型
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericType;
            //得到泛型里的class类型对象
            return (Class<?>) pt.getActualTypeArguments()[0];
        }
        return null;
    }

    /**
     * 对类中方法参数类型的不同分类
     *
     * @param clz
     * @param basicTypeMethodClassMap
     * @param otherTypeMethodClassMap
     * @param <T>
     */
    private <T> void classifyMethods(Class<T> clz, Map<Method, Class<?>> basicTypeMethodClassMap, Map<Method, Class<?>> otherTypeMethodClassMap) {
        // 获取类的所有以set方法开头，且参数只有一个，参数类型不为String类型
        for (Method method : clz.getMethods()) {
            String methodName = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();

            if (methodName.startsWith("set") && parameterTypes.length == 1 && !parameterTypes[0].getClass().isAssignableFrom(String.class)) {
                Class<?> argType = method.getParameterTypes()[0];
                if (typeCovertMap.containsKey(argType)) {
                    basicTypeMethodClassMap.put(method, argType);
                } else {
                    otherTypeMethodClassMap.put(method, argType);
                }
            }
        }

    }

    /**
     * 获取添加set方法后的代理对象
     *
     * @param clz
     * @param basicTypeMethodClassMap
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> Class<?> getProxyClass(Class<T> clz, Map<Method, Class<?>> basicTypeMethodClassMap) throws CannotCompileException, NotFoundException {

        if (ObjectUtil.isAnyNull(basicTypeMethodClassMap)) {
            return clz;
        }

        classPool.importPackage(clz.getClass().getPackage().toString());

        String clzName = clz.getName();
        CtClass cc = classPool.makeClass(
                StrUtil.format("{}$$XinGeProxy$${}", clzName, IdUtil.uuid6()),
                classPool.get(clzName)
        );

        // 添加一个同名方法，参数为String类型，，调用重载
        for (Map.Entry<Method, Class<?>> entry : basicTypeMethodClassMap.entrySet()) {
            Method method = entry.getKey();
            Class<?> argType = entry.getValue();

            String src = StrUtil.format("public void {} (String arg) {\n" +
                    "                {} convert = cn.hutool.core.convert.Convert.{}(arg);\n" +
                    "                $proceed(convert);\n" +
                    "            }", method.getName(), argType.getSimpleName(), typeCovertMap.get(argType));


            CtMethod ctMethod = CtNewMethod.make(src, cc, "super", method.getName());

            cc.addMethod(ctMethod);
        }

        return cc.toClass();
    }

}