package com.kzb.parents;

import com.google.gson.Gson;
import com.kzb.baselibrary.network.callback.IGenericsSerializator;

/**
 * Created by JimGong on 2016/6/23.
 */
public class JsonGenericsSerializator implements IGenericsSerializator {
    Gson mGson = new Gson();
    @Override
    public <T> T transform(String response, Class<T> classOfT) {
        return mGson.fromJson(response, classOfT);
    }
}
