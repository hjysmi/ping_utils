package com.lenovo.pingtools;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SPUtils {

    private static final String PREFERENCE_NAME = "config";

    private static SPUtils INSTANCE;
    private final SharedPreferences mSharedPf;

    private SPUtils(Context context) {
        mSharedPf = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static SPUtils get(Context context) {
        if (INSTANCE == null) {
            synchronized (PREFERENCE_NAME) {
                if (INSTANCE == null) {
                    INSTANCE = new SPUtils(context);
                }
            }
        }

        return INSTANCE;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public void putValue(String key, Object object) {
        SharedPreferences.Editor editor = mSharedPf.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    /***
     * 批量存储数据的方法
     * @param map
     */
    public void putManyValue(Map<String, Object> map) {
        SharedPreferences.Editor editor = mSharedPf.edit();
        // 循环遍历map
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            // 获取键
            String key = entry.getKey();
            // 获取值
            Object object = entry.getValue();
            // 判断值的类型然后存储
            if (object instanceof String) {
                editor.putString(key, (String) object);
            } else if (object instanceof Integer) {
                editor.putInt(key, (Integer) object);
            } else if (object instanceof Boolean) {
                editor.putBoolean(key, (Boolean) object);
            } else if (object instanceof Float) {
                editor.putFloat(key, (Float) object);
            } else if (object instanceof Long) {
                editor.putLong(key, (Long) object);
            } else {
                editor.putString(key, object.toString());
            }
        }
        editor.apply();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public <T> T getValue(String key, T defaultObject) {
        if (defaultObject instanceof String || defaultObject == null) {
            return (T) mSharedPf.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return (T) Integer.valueOf(mSharedPf.getInt(key, (Integer) defaultObject));
        } else if (defaultObject instanceof Boolean) {
            return (T) Boolean.valueOf(mSharedPf.getBoolean(key, (Boolean) defaultObject));
        } else if (defaultObject instanceof Float) {
            return (T) Float.valueOf(mSharedPf.getFloat(key, (Float) defaultObject));
        } else if (defaultObject instanceof Long) {
            return (T) Long.valueOf(mSharedPf.getLong(key, (Long) defaultObject));
        } else {
            return null;
        }
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = mSharedPf.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有数据
     */
    public void clearAll() {
        SharedPreferences.Editor editor = mSharedPf.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return mSharedPf.contains(key);
    }

    public SharedPreferences getSharedPf() {
        return mSharedPf;
    }
}
