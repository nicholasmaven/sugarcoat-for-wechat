package com.github.nicholasmaven.sugarcoat.wechat;


import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * Wrap TypeReference and simple java type to provide flexible support between generics
 * and simple java type.
 * </p>
 * Generics is hold by {@link com.fasterxml.jackson.core.type.TypeReference}
 * <p>
 * For performance consideration, this class hold a cache of all instances
 * </p>
 *
 * @author mawen
 * @create 2019-03-11 15:04
 */
public class TypeWrapper<T> {
    private static ConcurrentHashMap<String, TypeWrapper> typeMap = new ConcurrentHashMap<>();

    private Class<T> clazz;
    private TypeReference<T> genericType;

    private TypeWrapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    private TypeWrapper(TypeReference<T> genericType) {
        this.genericType = genericType;
    }

    @SuppressWarnings("unchecked")
    public static <T> TypeWrapper<T> of(Class<T> clazz) {
        Assert.notNull(clazz, "clazz is null");
        return createIfNotExists(clazz, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> TypeWrapper<T> of(TypeReference<T> genericType) {
        Assert.notNull(genericType, "type reference is null");
        return createIfNotExists(null, genericType);
    }

    @SuppressWarnings("unchecked")
    private static <T> TypeWrapper<T> createIfNotExists(Class<T> clazz,
                                                        TypeReference<T> genericType) {
        Assert.isTrue(clazz != null || genericType != null, "Both clazz and genericType are null");
        String name = clazz == null ? genericType.getType().getTypeName() : clazz.getName();
        TypeWrapper<T> type = typeMap.get(name);
        if (type != null) {
            return type;
        }
        type = clazz == null ? new TypeWrapper<>(genericType) : new TypeWrapper<>(clazz);
        return typeMap.putIfAbsent(name, type) == null ? type : typeMap.get(name);
    }

    public boolean isRegular() {
        return genericType == null;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public TypeReference<T> getGenericType() {
        return genericType;
    }
}
