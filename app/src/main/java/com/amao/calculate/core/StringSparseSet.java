package com.amao.calculate.width.core;

import android.support.v4.util.SimpleArrayMap;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * 优化的hashSet
 * <br/>
 * <li>Author DaMao
 * <li>Email luzhiyong@vargo.com.cn
 * <li>Date 18/1/18 18:14
 */
public class StringSparseSet implements Externalizable {

    private static final Object THE_ONLY_VALID_VALUE = new Object();
    private final SimpleArrayMap<String, Object> mSet;

    public StringSparseSet() {
        mSet = new SimpleArrayMap<>();
    }

    public StringSparseSet(int capacity) {
        mSet = new SimpleArrayMap<>(capacity);
    }

    /**
     * @param key The element to check
     * @return True if the element is in the set, false otherwise
     */
    public boolean contains(String key) {
        return mSet.get(key) == THE_ONLY_VALID_VALUE;
    }

    /**
     * Add an element to the set
     *
     * @param key The element to add
     */
    public void add(String key) {
        mSet.put(key, THE_ONLY_VALID_VALUE);
    }

    public void addAll(StringSparseSet sparseSet) {
        if(sparseSet != null){
            mSet.putAll(sparseSet.mSet);
        }
    }

    /**
     * Remove an element from the set
     *
     * @param key The element to remove
     */
    public void remove(String key) {
        mSet.remove(key);
    }

    public int size(){
        return mSet.size();
    }

    public String get(int idx){
       return mSet.keyAt(idx);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(size());
        for (int i = 0; i < size(); i++) {
            out.writeChars(get(i));
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = in.readLine();
            add(key);
        }
    }

    @Override
    public String toString() {
        int size = mSet.size();
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if(i == 0){
                builder.append(mSet.keyAt(i));
            } else {
                builder.append(", ").append(mSet.keyAt(i));
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
