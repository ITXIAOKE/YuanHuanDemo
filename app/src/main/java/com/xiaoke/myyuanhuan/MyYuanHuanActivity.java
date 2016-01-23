package com.xiaoke.myyuanhuan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyYuanHuanActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_yuan_huan);
        this.context=context;
        initUI();
    }

    private void initUI() {
       findViewById(R.id.bt_yuanhuan).setOnClickListener(this);
       findViewById(R.id.bt_zhuxing).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_yuanhuan:
                Intent intent1 =new Intent(this,YuanHuanActivity.class);
                startActivity(intent1);
              break;
            case R.id.bt_zhuxing:
                Intent intent2 =new Intent(this,ZhuXingActivity.class);
                startActivity(intent2);
              break;
        }
    }
}
