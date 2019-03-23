//package com.amao.calculate.activity;
//
//import java.util.List;
//
//import cn.com.vargo.mms.core.ContactsBaseVO;
//import cn.com.vargo.mms.core.StringSparseSet;
//import cn.com.vargo.mms.database.dao.ContactsDao;
//import cn.com.vargo.mms.database.dao.TalkieDao;
//
///**
// * 联系人界面配置类
// * <br/>
// * <li>Author DaMao
// * <li>Email luzhiyong@vargo.com.cn
// * <li>Date 18/1/30 18:08
// */
//public class ContactsConfig {
//
//    private static ContactsConfig CONFIG;
//
//    private StringSparseSet existSet;           // 已经存在的联系人,外部传入
//    private boolean isPickOne;
//    private boolean disableExist;
//    private int maxChoose = 500;
//    private boolean isVargoMShare;              // 是否为分享到联系人发送
//    private boolean isVargoMCircleShare;              // 是否为圈子分享到联系人发送
//    private String shareContent;              // 圈子分享到联系人内容
//    private boolean isDel;                      // 是否是删除
//    private boolean hasLetter = true;         // 是否有字母
//    private String title;                       // 标题
//    private ContactsConfig.ContactsQuery contactsQuery;
//
//    static void setConfig(ContactsConfig config) {
//        ContactsConfig.CONFIG = config;
//    }
//
//    static ContactsConfig getConfig() {
//        return CONFIG;
//    }
//
//    /**
//     * 默认配置,不配置查询回调
//     **/
//    public static ContactsConfig defaultConfig() {
//        ContactsConfig config = new ContactsConfig();
//        config.isPickOne = false;
//        config.disableExist = true;
//        return config;
//    }
//
//    /**
//     * 默认配置,查询所有联系人
//     **/
//    public static ContactsConfig build() {
//        ContactsConfig config = defaultConfig();
//        config.isPickOne = false;
//        config.disableExist = true;
//        config.contactsQuery = new ContactsQuery() {
//            @Override
//            public List<? extends ContactsBaseVO> queryContacts() {
//                return ContactsDao.findContactsSort();
//            }
//        };
//        return config;
//    }
//
//    /**
//     * 所有联系人,只选择一个
//     **/
//    public static ContactsConfig buildPickOne() {
//        ContactsConfig config = build();
//        config.isPickOne = true;
//        config.contactsQuery = new ContactsQuery() {
//            @Override
//            public List<? extends ContactsBaseVO> queryContacts() {
//                return ContactsDao.findContactsSort();
//            }
//        };
//        return config;
//    }
//
//    /**
//     * 查询本地所有的V用户
//     **/
//    public static ContactsConfig buildVUser() {
//        ContactsConfig config = defaultConfig();
//        config.contactsQuery = new ContactsQuery() {
//            @Override
//            public List<? extends ContactsBaseVO> queryContacts() {
//                return ContactsDao.findVContactsSort();
//            }
//        };
//        return config;
//    }
//
//    /**
//     * 短信分享,查询所有联系人
//     **/
//    public static ContactsConfig buildShare() {
//        ContactsConfig config = defaultConfig();
//        config.isPickOne = false;
//        config.disableExist = true;
//        config.isVargoMShare = true;
//        config.contactsQuery = new ContactsQuery() {
//            @Override
//            public List<? extends ContactsBaseVO> queryContacts() {
//                return ContactsDao.findContactsSort();
//            }
//        };
//        return config;
//    }
//
//
//    List<? extends ContactsBaseVO> queryContacts() {
//        if (contactsQuery != null) {
//            return contactsQuery.queryContacts();
//        }
//        return null;
//    }
//
//    public String getShareContent() {
//        return shareContent;
//    }
//
//    public void setShareContent(String shareContent) {
//        this.shareContent = shareContent;
//    }
//
//    public boolean isVargoMCircleShare() {
//        return isVargoMCircleShare;
//    }
//
//    public void setVargoMCircleShare(boolean vargoMCircleShare) {
//        isVargoMCircleShare = vargoMCircleShare;
//    }
//
//    public boolean isPickOne() {
//        return isPickOne;
//    }
//
//    public void setPickOne(boolean pickOne) {
//        isPickOne = pickOne;
//    }
//
//    public boolean isDisableExist() {
//        return disableExist;
//    }
//
//    public void setDisableExist(boolean disableExist) {
//        this.disableExist = disableExist;
//    }
//
//    public StringSparseSet getExistSet() {
//        return existSet;
//    }
//
//    public boolean existPhone(String phone) {
//        return existSet != null && existSet.contains(phone);
//    }
//
//    public void addExistPhone(String phone) {
//        if (existSet == null) {
//            existSet = new StringSparseSet();
//        }
//        existSet.add(phone);
//    }
//
//    public int getMaxChoose() {
//        return maxChoose;
//    }
//
//    public void setMaxChoose(int maxChoose) {
//        this.maxChoose = maxChoose;
//    }
//
//    public boolean isVargoMShare() {
//        return isVargoMShare;
//    }
//
//    public void setVargoMShare(boolean vargoMShare) {
//        isVargoMShare = vargoMShare;
//    }
//
//    public boolean isDel() {
//        return isDel;
//    }
//
//    public void setDel(boolean del) {
//        isDel = del;
//    }
//
//    public boolean isHasLetter() {
//        return hasLetter;
//    }
//
//    public void setHasLetter(boolean hasLetter) {
//        this.hasLetter = hasLetter;
//    }
//
//    interface ContactsQuery {
//        List<? extends ContactsBaseVO> queryContacts();
//    }
//}
