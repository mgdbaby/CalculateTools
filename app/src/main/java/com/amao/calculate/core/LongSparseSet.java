package com.amao.calculate.width.core;

import android.support.v4.util.LongSparseArray;

/**
 * 性能更优,Long型的 HashSet
 * <br/>
 * <li>Author DaMao
 * <li>Email luzhiyong@vargo.com.cn
 * <li>Date 18/1/18 18:20
 */
public class LongSparseSet {

    private static final Object THE_ONLY_VALID_VALUE = new Object();
    private final LongSparseArray<Object> mSet = new LongSparseArray<>();

    /**
     * @param key The element to check
     * @return True if the element is in the set, false otherwise
     */
    public boolean contains(long key) {
        if (mSet.get(key, null/*default*/) == THE_ONLY_VALID_VALUE) {
            return true;
        }
        return false;
    }

    /**
     * Add an element to the set
     *
     * @param key The element to add
     */
    public void add(long key) {
        mSet.put(key, THE_ONLY_VALID_VALUE);
    }

    /**
     * Remove an element from the set
     *
     * @param key The element to remove
     */
    public void remove(long key) {
        mSet.delete(key);
    }

    public int size(){
        return mSet.size();
    }

    public long get(int idx){
        return mSet.keyAt(idx);
    }

}
