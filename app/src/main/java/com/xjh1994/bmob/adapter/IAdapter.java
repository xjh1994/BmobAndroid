package com.xjh1994.bmob.adapter;

import android.support.annotation.NonNull;

import com.xjh1994.bmob.base.IAdapterItem;

import java.util.List;

/**
 * Created by XJH on 16/3/24.
 */
public interface IAdapter<T> {
    /**
     * @param data 设置数据源
     */
    void setData(@NonNull List<T> data);

    List<T> getData();

    /**
     * @param t list中的一条数据
     * @return 强烈建议返回string, int, bool类似的基础对象做type
     */
    Object getItemType(T t);

    /**
     * 当缓存中无法得到所需item时才会调用
     *
     * @param type 通过{@link #getItemType(Object)}得到的type
     * @return 任意类型的 AdapterItem
     */
    @NonNull
    IAdapterItem createItem(Object type);

    /**
     * 如果放入item的最终数据和list中的每一条数据类型是不同的，可以通过此方法进行转换
     *
     * @param data 从原始的list中取得得数据
     * @param type item的类型
     * @return 放入adapterItem的最终数据
     */
    @NonNull
    Object getConvertedData(T data, Object type);

    /**
     * 通知adapter更新当前页面的所有数据
     */
    void notifyDataSetChanged();
}
