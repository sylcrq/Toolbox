package com.syl.toolbox.views.activities;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.syl.toolbox.R;
import com.syl.toolbox.utils.SkinManager;


public class SkinActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = SkinActivity.class.getSimpleName();

    private ImageView mImage;
    private Button mSkinApkButton;
    private Button mSkinApkRenameButton;
    private Button mSkinZipButton;
    private Button mSkinZipResButton;
    private Button mSkinZipArscButton;

    public static final String SD_CARD_PATH = "/sdcard/";
    public static final String SKIN_APK_PATH = SD_CARD_PATH + "skin_plugin.apk";
    public static final String SKIN_APK_RENAME_PATH = SD_CARD_PATH + "skin_plugin.skin";
    public static final String SKIN_ZIP_PATH = SD_CARD_PATH + "skin_plugin.zip";
    public static final String SKIN_ZIP_RES_PATH = SD_CARD_PATH + "skin_plugin_res.zip";
    public static final String SKIN_ZIP_ARSC_PATH = SD_CARD_PATH + "skin_plugin_arsc.zip";

    public static final String drawableName= "left_menu_icon";
    public static final String packageName = "com.imooc.skin_plugin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);

        mImage = (ImageView) findViewById(R.id.image);
        mSkinApkButton = (Button) findViewById(R.id.skin_apk_button);
        mSkinApkRenameButton = (Button) findViewById(R.id.skin_apk_rename_button);
        mSkinZipButton = (Button) findViewById(R.id.skin_zip_button);
        mSkinZipResButton = (Button) findViewById(R.id.skin_zip_res_button);
        mSkinZipArscButton = (Button) findViewById(R.id.skin_zip_arsc_button);

        mSkinApkButton.setOnClickListener(this);
        mSkinApkRenameButton.setOnClickListener(this);
        mSkinZipButton.setOnClickListener(this);
        mSkinZipResButton.setOnClickListener(this);
        mSkinZipArscButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skin_apk_button:
                changeSkinTest(SKIN_APK_PATH);
                break;
            case R.id.skin_apk_rename_button:
                changeSkinTest(SKIN_APK_RENAME_PATH);
                break;
            case R.id.skin_zip_button:
                changeSkinTest(SKIN_ZIP_PATH);
                break;
            case R.id.skin_zip_res_button:
                changeSkinTest(SKIN_ZIP_RES_PATH);
                break;
            case R.id.skin_zip_arsc_button:
                changeSkinTest(SKIN_ZIP_ARSC_PATH);
                break;
        }
    }

    private void changeSkinTest(String path) {
        Log.d(TAG, "changeSkinTest # " + path);

        SkinManager skinManager = SkinManager.getInstance(getApplicationContext());
        try {
            skinManager.init(path);

            mImage.setImageDrawable(skinManager.getDrawableByName(drawableName, packageName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void findRes() {
//        Resources resources = getResources();
//
//        int resId = resources.getIdentifier(drawableName, "drawable", packageName);
////        resources.getDrawable(resId);
//
//        Log.d(TAG, ""+packageName+":"+drawableName+" # resId="+resId);
//    }
}
