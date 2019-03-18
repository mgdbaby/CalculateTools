package com.amao.calculate.width;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amao.calculate.R;

import java.util.ArrayList;
import java.util.List;

public class AllListActivity extends AppCompatActivity {
    private int numSize = 49;
    private int columnSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateTestData();
    }

    private void generateTestData() {
        List<StringBean> numList = new ArrayList<>();
        for (int i = 0; i < numSize; i++) {
            StringBean numBean = new StringBean();
            numBean.setContent(String.valueOf(i + 1));
            numList.add(numBean);
        }

        List<StringBean> personList = new ArrayList<>();
        for (int i = 0; i < columnSize; i++) {
            StringBean personBean = new StringBean();
            personBean.setContent("张三" + String.valueOf(i + 1));
            personList.add(personBean);
        }

        List<List<StringBean>> ordersList = new ArrayList<>();
        for (int i = 0; i < numSize; i++) {
            List<StringBean> orderInfoList = new ArrayList<>();
            for (int j = 0; j < columnSize; j++) {
                StringBean orderInfo = new StringBean();
                if(i == j){
                    orderInfo.setContent(String.valueOf(i));
                }
                orderInfoList.add(orderInfo);
            }
            ordersList.add(orderInfoList);
        }

        ScrollablePanelAdapter scrollablePanelAdapter = new ScrollablePanelAdapter(this, numList, personList, ordersList);
        ScrollablePanel scrollablePanel = findViewById(R.id.scrollable_panel);
        scrollablePanel.setPanelAdapter(scrollablePanelAdapter);
    }
}
