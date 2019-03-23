package com.amao.calculate.database.dao;

import com.amao.calculate.database.DBHelper;
import com.amao.calculate.database.entity.ContactEntity;

import java.util.List;

/**
 * @TODO 写类注释
 * <br/>
 * <li>Author 毛该得
 * <li>Email maogaide@vargo.com.cn
 * <li>Date 2019/3/23 16:30
 */
public class ContactDao extends DBHelper {


    public static List<ContactEntity> findAllContacts() {
        return findAll(ContactEntity.class);
    }
}
