package com.heng.ioc.utils;

public class TypeConvertUtils {

    public static Object typeConvert(String orginValue,Class<?> type){

        if (String.class.equals(type)) {
            return String.valueOf(orginValue);
        }else if (Integer.class.equals(type) || int.class.equals(type)){
            return Integer.valueOf(orginValue);
        }else if (Long.class.equals(type) || long.class.equals(type)){
            return Long.valueOf(orginValue);
        }else if (Float.class.equals(type) || float.class.equals(type)){
            return Float.valueOf(orginValue);
        }else if (Double.class.equals(orginValue) || double.class.equals(type)){
            return Double.valueOf(orginValue);
        }

        return null;
    }
}
