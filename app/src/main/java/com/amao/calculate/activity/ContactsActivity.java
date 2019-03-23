//package com.amao.calculate.activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.util.SimpleArrayMap;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SimpleItemAnimator;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Filter;
//import android.widget.TextView;
//
//import com.amao.calculate.AppConfig;
//import com.amao.calculate.R;
//import com.amao.calculate.core.BaseActivity;
//import com.amao.calculate.core.SwitchCase;
//import com.amao.calculate.core.VRecyclerAdapter;
//import com.amao.calculate.core.widget.TitleBarView;
//import com.amao.calculate.database.DBHelper;
//import com.amao.calculate.utils.FileUtils;
//import com.amao.calculate.utils.OptWhat;
//import com.amao.calculate.utils.TipUtil;
//import com.amao.calculate.utils.acp.AcpListener;
//
//import org.xutils.common.util.LogUtil;
//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.Event;
//import org.xutils.view.annotation.ViewInject;
//import org.xutils.x;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//
//
///**
// * 短信所有联系人选择界面
// * <br/>
// * <li>DaMao
// * <li>Email luzhiyong@vargo.com.cn
// * <li>Date 2017/12/18 15:27
// */
//@ContentView(R.layout.act_contact_list)
//public class ContactsActivity extends BaseActivity implements TextWatcher {
//
//    public static final String KEY_RESULT = "key_contacts_result";
//
//    @ViewInject(R.id.title_bar)
//    private TitleBarView titleBarView;
//
//    @ViewInject(R.id.index_bar)
//    private LetterView index_bar;
//
//    @ViewInject(R.id.sort_recycler)
//    private RecyclerView sort_recycler;
//
//    @ViewInject(R.id.text_position)
//    private TextView text_position;
//
//    @ViewInject(R.id.search_bar_et)
//    private EditText search_bar_et;
//
//    @ViewInject(R.id.text_no_msg)
//    private TextView textNoMsg;
//
//    private VRecyclerAdapter<ContactsBaseVO> mAdapter;
//    private SimpleArrayMap<String, Integer> letterMap = new SimpleArrayMap<>(27);
//    private List<? extends ContactsBaseVO> rawContactList;
//    private ContactsConfig config;
//    private ArrayList<ContactsBaseVO> resultList;
//    private Filter contactsFilter;
//    private int oldPos = -1;
//    private List<MsgWrapper> msgList = new ArrayList<>();        // 转发或者分享的消息
//    private String action;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        registerHandler();
//
//        config = ContactsConfig.getConfig();
//        if (config == null) {
//            config = ContactsConfig.build();
//        }
//        if (config.isDel()) {
//            titleBarView.setRightText(getString(R.string.text_delete));
//            index_bar.setVisibility(View.GONE);
//            titleBarView.setTitleText(getString(R.string.delete_member));
//        }
//        titleBarView.setRightEnabled(false);
//        index_bar.setDialogView(text_position);
//        search_bar_et.addTextChangedListener(this);
//
//        mAdapter = new VRecyclerAdapter<>(this);
//        mAdapter.setExtendData(config);
//        mAdapter.addViewHolder(ContactsBaseVO.VIEW_TYPE_TITLE, ContactTitleViewHolder.class);
//        mAdapter.addViewHolder(ContactsBaseVO.VIEW_TYPE_CONTENT, ContactViewHolder.class);
//        mAdapter.addViewHolder(0, ContactDelViewHolder.class);
//        sort_recycler.setLayoutManager(new NotifyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        sort_recycler.setAdapter(mAdapter);
//        ((SimpleItemAnimator) sort_recycler.getItemAnimator()).setSupportsChangeAnimations(false);
//
//        requestPermission(new AcpListener() {
//            @Override
//            public void onGranted() {
//                LoadDataService.startLoadDataService(ContactsActivity.this);
//
//                Intent intent = getIntent();
//                action = intent.getAction();
//                // 一个文件、文字分享
//                if (Intent.ACTION_SEND.equals(action)) {
//                    dealSysSend(intent);//更改了config
//                }
//                // 多个文件分享
//                else if (Intent.ACTION_SEND_MULTIPLE.equals(action)) {
//                    dealSysMultiSend(intent);//更改了config
//                } else {
//                }
//                config = ContactsConfig.getConfig();//更新config状态
//                initData();
//            }
//
//            @Override
//            public void onDenied(List<String> permissions) {
//
//            }
//        }, AppConfig.BASE_PERMISSION);
//    }
//
//    private void initData() {
//        x.task().start(new AbsTask<List<ContactsBaseVO>>() {
//
//            private List<? extends ContactsBaseVO> resultList;
//
//            @Override
//            protected List<ContactsBaseVO> doBackground() {
//                DBHelper.createTableIfNotExist(ContactsDto.class);
//                this.resultList = config.queryContacts();
//                return (config.isHasLetter() ? handleDataFirst(resultList) : new ArrayList<>(resultList));
//            }
//
//            @Override
//            protected void onSuccess(List<ContactsBaseVO> result) {
//                rawContactList = result;
//                mAdapter.update(result);
//                if (rawContactList == null || rawContactList.isEmpty()) {
//                    textNoMsg.setVisibility(View.VISIBLE);
//
//                } else {
//                    textNoMsg.setVisibility(View.GONE);
//                }
//
//                if (config.isDel()) {
//                    int size = resultList == null ? 0 : resultList.size();
//                    String title = size == 0 ? getString(R.string.delete_member) : getString(R.string.delete_member_num, size);
//                    titleBarView.setTitleText(title);
//                }
//            }
//        });
//    }
//
//    /**
//     * 对查询到的数据进行排序和添加字母分类标记
//     */
//    private List<ContactsBaseVO> handleDataFirst(List<? extends ContactsBaseVO> list) {
//        int size = (list == null ? 0 : list.size());
//        //添加字母分类标记
//        ContactsBaseVO oldDto = null;
//        for (int i = 0; i < size; i++) {
//            ContactsBaseVO dto = list.get(i);
//            if (oldDto == null) {//第一个
//                dto.setFirst(true);
//                letterMap.put(dto.getSortLabel(), i);
//            } else if (!Objects.equals(oldDto.getSortLabel(), dto.getSortLabel())) {
//                dto.setFirst(true);
//                letterMap.put(dto.getSortLabel(), i);
//            } else {
//                dto.setFirst(false);
//            }
//            oldDto = dto;
//        }
//        return list == null ? null : new ArrayList<>(list);
//    }
//
//    @Event(TitleBarView.LEFT_ID)
//    private void onTitleBackClick(View view) {
//        finish();
//    }
//
//    @Event(value = R.id.index_bar, type = LetterView.OnSlidingListener.class)
//    private void onLetterSliding(String str) {
//        text_position.setText(str);
//        Integer pos = letterMap.get(str);
//        if (pos != null) {
//            sort_recycler.scrollToPosition(pos);
//        }
//    }
//
//    @Event(TitleBarView.RIGHT_ID)
//    private void onTitleRightClick(View view) {
//        if (resultList != null && !resultList.isEmpty()) {
//            if (config.isPickOne()) {
//                dealPickOne();
//            } else {
//                dealPickMulti();
//            }
//        }
//    }
//
//    @SwitchCase(value = OptWhat.CONTACTS_ITEM_CLICK, info = "点击搜索到的联系人")
//    private void onContactsItemClick(int position) {
//        ContactsBaseVO dto = mAdapter.getItem(position);
//        if (dto == null || (config.existPhone(dto.getDisplayMobile()) && config.isDisableExist())) {
//            return;
//        }
//
//        if (resultList == null) resultList = new ArrayList<>();
//        boolean isChecked = dto.isChecked();
//        if (isChecked) {
//            resultList.remove(dto);
//        } else {
//            com.amao.calculate.width.core.StringSparseSet set = config.getExistSet();
//            int setSize = set == null ? 0 : set.size();
//            int maxSize = config.getMaxChoose();
//            if ((resultList.size() + setSize) >= maxSize) {
//                MessageDialog dialog = buildDialog(MessageDialog.class);
//                dialog.setMessage(getString(R.string.not_more_than_num, maxSize), getString(R.string.sure));
//                dialog.show();
//                return;
//            }
//            resultList.add(dto);
//        }
//        dto.setChecked(!isChecked);
//        titleBarView.setRightEnabled(!resultList.isEmpty());
//
//        if (config.isPickOne()) {
//            if (oldPos > -1 && oldPos != position) {
//                mAdapter.getItem(oldPos).setChecked(false);
//                resultList.remove(mAdapter.getItem(oldPos));
//                mAdapter.notifyItemChanged(oldPos);
//            }
//            this.oldPos = position;
//        }
//        mAdapter.notifyItemChanged(position);
//    }
//
//    @Override
//    public void afterTextChanged(final Editable s) {
//        if (contactsFilter == null) {
//            contactsFilter = new Filter() {
//                @Override
//                protected FilterResults performFiltering(CharSequence constraint) {
//                    FilterResults results = new FilterResults();
//                    if (constraint == null || constraint.length() <= 0) {
//                        // 初始化成原始数据
//                        results.values = rawContactList;
//                    } else {
//                        ArrayList<ContactsBaseVO> retList = new ArrayList<>();
//
//                        List<ContactsBaseVO> contactsList = mAdapter.getData();
//                        int size = (contactsList == null ? 0 : contactsList.size());
//                        for (int i = 0; i < size; i++) {
//                            ContactsBaseVO contact = contactsList.get(i);
//                            if (contact.contains(s)) {
//                                retList.add(contact);
//                            }
//                        }
//                        results.values = retList;
//                    }
//                    return results;
//                }
//
//                @Override
//                @SuppressWarnings("unchecked")
//                protected void publishResults(CharSequence constraint, FilterResults results) {
//                    List<ContactsBaseVO> contactsList = (List<ContactsBaseVO>) results.values;
//                    if (contactsList == null || contactsList.isEmpty()) {
//                        textNoMsg.setVisibility(View.VISIBLE);
//
//                    } else {
//                        textNoMsg.setVisibility(View.GONE);
//                        contactsList = handleDataFirst(contactsList);
//                        mAdapter.update(contactsList);
//                    }
//                }
//            };
//        }
//        contactsFilter.filter(s);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // 防止异步清空配置信息错误
//        if (config == ContactsConfig.getConfig()) {
//            ContactsConfig.setConfig(null);
//        }
//    }
//
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//    }
//
//    /**
//     * 跳转到联系人页面
//     **/
//    public static void doJump(Activity activity, int retCode, ContactsConfig config) {
//        ContactsConfig.setConfig(config);
//        Intent intent = new Intent(activity, ContactsActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        activity.startActivityForResult(intent, retCode);
//    }
//
//    /**
//     * 跳转到联系人页面
//     **/
//    public static void doJump(Fragment fragment, int retCode, ContactsConfig config) {
//        ContactsConfig.setConfig(config);
//        Intent intent = new Intent(fragment.getContext(), ContactsActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        fragment.startActivityForResult(intent, retCode);
//    }
//
//    public static void doJump(Activity activity, ContactsConfig config) {
//        doJump(activity, -1, config);
//    }
//
//    private static void doJump(Fragment activity, ContactsConfig config) {
//        doJump(activity, -1, config);
//    }
//
//    /**
//     * 单个文件或者文字的分享
//     */
//    private void dealSysSend(Intent intent) {
//        ContactsConfig config = ContactsConfig.buildPickOne();
//        ContactsConfig.setConfig(config);
//
//        Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
//        String type = intent.getType();
//        String body = intent.getStringExtra(Intent.EXTRA_TEXT);
//        LogUtil.i(" 单个文件的分享：system send type = " + type);
//        if (uri != null) {
//            String sharePath = FileUtils.getRealFilePath(this, uri);
//            if (!FileUtils.isFileExist(sharePath)) {
//                TipUtil.showShort(R.string.share_file_not_exist);
//                finish();
//                return;
//            }
//
//            MsgWrapper msgWrapper = new MsgWrapper();
//            byte contentType = (!TextUtils.isEmpty(type) && type.contains("image") ? AppConfig.ContentType.IMAGE : AppConfig.ContentType.FILE);
//            File file = new File(sharePath);
//            if (file.length() / 1048576 > 20) {
//                TipUtil.showShort(R.string.share_file_is_too_large);
//                LogUtil.e("share file is too large path is : " + sharePath);
//                finish();
//                return;
//            }
//            msgWrapper.setContentType(contentType);
//            msgWrapper.setFilePath(sharePath);
//            msgWrapper.setFileName(file.getName());
//            msgList.add(msgWrapper);
//        } else {
//            if ("text/plain".equals(type) && !TextUtils.isEmpty(body)) {
//                MsgWrapper msgWrapper = new MsgWrapper();
//                msgWrapper.setContentType(AppConfig.ContentType.TEXT);
//                msgWrapper.setMsgContent(body);
//                msgList.add(msgWrapper);
//            } else {
//                LogUtil.e("System share fail : type : " + type + " body : " + body);
//            }
//        }
//
//        if (msgList.isEmpty()) {
//            TipUtil.showShort(R.string.share_fail);
//            finish();
//        }
//    }
//
//    /**
//     * 多个文件的分享
//     */
//    private void dealSysMultiSend(Intent intent) {
//        LogUtil.i("多个文件的分享");
//        ContactsConfig config = ContactsConfig.buildPickOne();
//        ContactsConfig.setConfig(config);
//        ArrayList<Uri> uris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
//        for (Uri uri : uris) {
//            String sharePath = FileUtils.getRealFilePath(this, uri);
////            sharePath =  "/storage/emulated/0/DCIM/Camera/1535077918824.jpg";
//            if (!FileUtils.isFileExist(sharePath)) {
//                TipUtil.showShort(R.string.share_file_not_exist);
//                LogUtil.e("share file not exist path is : " + sharePath);
//                finish();
//                return;
//            }
//
//            MsgWrapper msgWrapper = new MsgWrapper();
//            File file = new File(sharePath);
//            if (file.length() / 1048576 > 20) {
//                TipUtil.showShort(R.string.share_file_is_too_large);
//                LogUtil.e("share file is too large path is : " + sharePath);
//                finish();
//                return;
//            }
//            msgWrapper.setContentType(AppConfig.ContentType.FILE);
//            msgWrapper.setFilePath(sharePath);
//            msgWrapper.setFileName(file.getName());
//            msgList.add(msgWrapper);
//        }
//
//        if (msgList.isEmpty()) {
//            TipUtil.showShort(R.string.share_fail);
//            finish();
//        }
//    }
//
//    /**
//     * 多选点击确认按钮
//     */
//    private void dealPickMulti() {
//        if (config.isVargoMCircleShare()) {
//            vargoEventShareToSms();
//            finish();
//        } else if (config.isVargoMShare()) {
//            vargoShareToSms();
//            finish();
//        } else if (config.isDel()) {
//            delContacts();
//        } else {
//            Intent intent = new Intent();
//            intent.putExtra(KEY_RESULT, resultList);
//            setResult(RESULT_OK, intent);
//            finish();
//        }
//    }
//
//    //我的-分享到联系人
//    private void vargoShareToSms() {
//        StringBuilder builder = new StringBuilder("smsto:");
//        for (ContactsBaseVO contact : resultList) {
//            if (!TextUtils.isEmpty(contact.getDisplayMobile())) {
//                builder.append(contact.getDisplayMobile());
//                builder.append(",");
//            }
//        }
//        String uri = builder.toString().substring(0, builder.lastIndexOf(","));
//        Intent inviteIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
//        String sendBody;
//        if (VargoManager.isOnlineVargo()) {
//            sendBody = getString(R.string.vargoyun_send_body);
//        } else {
//            sendBody = getString(R.string.send_sms_body);
//        }
//        inviteIntent.putExtra("sms_body", sendBody);
//        startActivity(inviteIntent);
//
//    }
//
//    //圈子-分享到联系人
//    private void vargoEventShareToSms() {
//        StringBuilder builder = new StringBuilder("smsto:");
//        for (ContactsBaseVO contact : resultList) {
//            if (!TextUtils.isEmpty(contact.getDisplayMobile())) {
//                builder.append(contact.getDisplayMobile());
//                builder.append(",");
//            }
//        }
//        String uri = builder.toString().substring(0, builder.lastIndexOf(","));
//        Intent inviteIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
//        String sendBody;
//        sendBody = config.getShareContent();
//        inviteIntent.putExtra("sms_body", sendBody);
//        startActivity(inviteIntent);
//    }
//
//    /**
//     * 单选点击确认按钮
//     */
//    private void dealPickOne() {
//        // 分享
//        if (Intent.ACTION_SEND.equals(action) || Intent.ACTION_SEND_MULTIPLE.equals(action)) {
//            sendSmsMsg();
//        }
//        // 回调选择的人
//        else {
//            Intent intent = new Intent();
//            intent.putExtra(KEY_RESULT, resultList.get(0));
//            setResult(RESULT_OK, intent);
//        }
//        finish();
//    }
//
//    /**
//     * 发送消息
//     */
//    private void sendSmsMsg() {
//        final boolean isDelAfterRead = ConfigDao.getBoolean(ConfigDto.KEY_DEL_READ, false);
//        int chooseSize = resultList == null ? 0 : resultList.size();
//        int msgSize = msgList == null ? 0 : msgList.size();
//        TelephonyInfo telephonyInfo = new TelephonyInfo();
//        telephonyInfo.initConfig();
//        int subId = (telephonyInfo.isOnlySim2Ready() ? telephonyInfo.getSubId2() : telephonyInfo.getSubId1());
//        for (int i = 0; i < chooseSize; i++) {
//            ContactsBaseVO dto = resultList.get(i);
//            String mobile = dto.getDisplayMobile();
//            long threadId = MmsSmsManager.getOrCreateThreadId(mobile);
//            for (int j = 0; j < msgSize; j++) {
//                MsgWrapper msgWrapper = msgList.get(j);
//                if (msgWrapper.getContentType() == AppConfig.ContentType.TEXT) {
//                    // 发送文字
//                    sendTextMsg(msgWrapper.getMsgContent(), mobile, threadId, subId, isDelAfterRead);
//                } else {
//                    // 发送文件
//                    sendFileMsg(msgWrapper, mobile, threadId, subId, isDelAfterRead);
//                }
//            }
//        }
//        setResult(RESULT_OK);
//    }
//
//    /**
//     * 发送文件消息
//     */
//    private void sendFileMsg(MsgWrapper msgWrapper, String mobile, long threadId, int subId, boolean isDelAfterRead) {
//        String path = msgWrapper.getFilePath();
//        File file = new File(path);
//        long currTime = System.currentTimeMillis();
//        String msgId = VMessageManager.getMsgId();
//        MmsSmsEntity paramsObj = new MmsSmsEntity();
//        paramsObj.setMsgId(msgId);
//        paramsObj.setContentType(msgWrapper.getContentType());
//
//        byte msgType = isDelAfterRead ? AppConfig.MsgType.DESTORY_VMSG : AppConfig.MsgType.VMSG;
//        paramsObj.setMsgType(msgType);
//        paramsObj.setMsgContent(null);
//        paramsObj.setMsgSendTime(currTime);
//        paramsObj.setType(AppConfig.SmsType.SENT);
//        paramsObj.setMsgStatus(AppConfig.MsgStatus.SENDING);
//        paramsObj.setFilePath(path);
//        paramsObj.setFileName(file.getName());
//        paramsObj.setFileSize(file.length());
//        paramsObj.setVoiceTimeLength(msgWrapper.getVoiceLongTime());
//        paramsObj.setSubId(subId);
//
//        paramsObj.setMobiles(mobile);
//        long smsId = MmsSmsManager.insert2Sms(paramsObj, threadId);
//        if (smsId > 0) {
//            paramsObj.setSmsId(smsId);
//            FileManager.startUploadMsgFileService(paramsObj, threadId);
//        }
//        TipUtil.showShort(R.string.send_complete);
//    }
//
//    /**
//     * 发送文字消息
//     */
//    private void sendTextMsg(String content, String mobile, long threadId, int subId, boolean isDelAfterRead) {
//        long currTime = System.currentTimeMillis();
//        String msgId = VMessageManager.getMsgId();
//        MmsSmsEntity paramsObj = new MmsSmsEntity();
//        paramsObj.setMsgId(msgId);
//        paramsObj.setContentType(AppConfig.ContentType.TEXT);
//        byte msgType = isDelAfterRead ? AppConfig.MsgType.DESTORY_VMSG : AppConfig.MsgType.VMSG;
//        paramsObj.setMsgType(msgType);
//        paramsObj.setMsgContent(content);
//        paramsObj.setMsgSendTime(currTime);
//        paramsObj.setType(AppConfig.SmsType.SENT);
//        paramsObj.setMsgStatus(AppConfig.MsgStatus.SENDING);
//        paramsObj.setSubId(subId);
//        paramsObj.setMobiles(mobile);
//        long smsId = MmsSmsManager.insert2Sms(paramsObj, threadId);
//        if (smsId > 0) {
//            paramsObj.setSmsId(smsId);
//            VMessageManager.startSendVmsgService(paramsObj);
//        }
//        TipUtil.showShort(R.string.send_complete);
//    }
//
//    private void delContacts() {
//        ConfirmDialog delDialog = buildDialog(ConfirmDialog.class);
//        int delNum = resultList.size();
//        String userName1 = resultList.get(0).getContactName();
//        String delMember;
//
//        if (delNum == 1) {
//            delMember = getString(R.string.talkie_del_member_one, userName1);
//        } else {
//            String userName2 = resultList.get(1).getContactName();
//            delMember = getString(R.string.talkie_del_member_more, userName1, userName2, delNum);
//        }
//        delDialog.setMessage(delMember);
//        delDialog.setCallback(new ConfirmDialog.Callback() {
//            @Override
//            public void onSure() {
//                Intent intent = new Intent();
//                intent.putExtra(KEY_RESULT, resultList);
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//
//            @Override
//            public void onCancel() {
//            }
//        });
//        delDialog.show();
//    }
//
//    @SwitchCase(value = OptWhat.SYNC_CONTACT_SUCCESS, info = "本地联系人变化")
//    private void onChangeContacts(){
//
//        if (resultList != null && !resultList.isEmpty()){
//            return;
//        }
//        initData();
//    }
//}
