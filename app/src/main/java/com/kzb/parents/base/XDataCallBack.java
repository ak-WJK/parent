package com.kzb.parents.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by wanghaofei on 16/10/18.
 */

public abstract class XDataCallBack<T extends XBaseResponse>  {

    public abstract void onComplete(int result, T responseData, String errMsg);

    public abstract void onFail(int code,String msg);

    //获取类的泛型对象类型（T的对象类型）
    @SuppressWarnings("rawtypes")
    public Class getGenericType() {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (!(params[0] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[0];
    }


}
