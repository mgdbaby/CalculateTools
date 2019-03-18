package com.amao.calculate.utils;

import android.util.SparseArray;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xutils.common.util.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


public class ConstantUtil extends DefaultHandler {

    private static SparseArray<String> sparse;

    public static String getOptWhatName(int what) {
        if (sparse == null) {
            sparse = new SparseArray<>();
            Field[] fields = OptWhat.class.getDeclaredFields();
            int size = (fields == null ? 0 : fields.length);
            for (int i = 0; i < size; i++) {
                Field field = fields[i];
                try {
                    int mod = field.getModifiers();
                    if (!int.class.equals(field.getType()) || !Modifier.isFinal(mod) || !Modifier.isPublic(mod) || !Modifier.isStatic(mod)) {
                        continue;
                    }
                    int key = field.getInt(null);

                    if (sparse.indexOfKey(key) > -1) {
                        throw new RuntimeException("The OptWhat class has duplicate what : " + key);
                    }
                    sparse.put(key, field.getName());
                } catch (Exception e) {
                    LogUtil.e("Field error. " + field.getName(), e);
                }
            }
        }

        String name = sparse.get(what);
        return name == null ? String.valueOf(what) : name;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String name = attributes.getLocalName(i);
            String value = attributes.getValue(i);
            LogUtil.d(name + " = " + value);
        }
    }
}
