package com.amao.calculate.width.core;

import android.util.SparseArray;

/**
 * 性能更优,int型的 HashSet
 * <br/>
 * <li>Author DaMao
 * <li>Email luzhiyong@vargo.com.cn
 * <li>Date 18/5/22 18:20
 */
public class SparseSet {

    private static final Object THE_ONLY_VALID_VALUE = new Object();
    private final SparseArray<Object> mSet = new SparseArray<>();

    /**
     * @param key The element to check
     * @return True if the element is in the set, false otherwise
     */
    public boolean contains(int key) {
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
    public void add(int key) {
        mSet.put(key, THE_ONLY_VALID_VALUE);
    }

    /**
     * Remove an element from the set
     *
     * @param key The element to remove
     */
    public void remove(int key) {
        mSet.delete(key);
    }

    public int size() {
        return mSet.size();
    }

    public int get(int idx) {
        return mSet.keyAt(idx);
    }

    public void clear() {
        mSet.clear();
    }
}
