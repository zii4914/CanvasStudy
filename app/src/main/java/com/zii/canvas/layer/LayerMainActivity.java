package com.zii.canvas.layer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zii.canvas.R;

public class LayerMainActivity extends AppCompatActivity {

    private String mImageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layer_main);

        GenderAvatarView view = findViewById(R.id.roundview);
        view.setGender(false);

        mImageURI = "http://tp4.sinaimg.cn/1969176595/180/5677895051/1";
        view.setImageURI(mImageURI);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, LayerMainActivity.class);
        context.startActivity(starter);
    }
}
