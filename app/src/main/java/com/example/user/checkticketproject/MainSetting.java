
package com.example.user.checkticketproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainSetting extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView liftmenu;
    DrawerLayout mainlayout;
    EditText urlEditText;
    EditText pwdEditText;
    Button urlButton;
    Button pwdButton;
    RelativeLayout urlLayout;
    RelativeLayout pwdLayout;
    SQLdata DH = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_setting);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        liftmenu = (NavigationView)findViewById(R.id.liftmenu);
        mainlayout = (DrawerLayout)findViewById(R.id.Setting_layout);
        urlEditText = (EditText)findViewById(R.id.urlEditText);
        urlButton = (Button)findViewById(R.id.urlButton);
        pwdButton = (Button)findViewById(R.id.pwdButton);
        pwdEditText = (EditText)findViewById(R.id.pwdEditText);
        urlLayout = (RelativeLayout)findViewById(R.id.urlLayout);
        pwdLayout = (RelativeLayout)findViewById(R.id.pwdLayout);
        SetToolBar();
        SetLifeMenu();
        DH = new SQLdata(this);
        adddata();

        pwdButton.setOnClickListener(new Button.OnClickListener(){
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                String pwd = pwdEditText.getText().toString().trim();
                if ( pwd.equals("0937966664") == true){
                    pwdLayout.setVisibility(4);
                    urlLayout.setVisibility(0);
                }else{
                    Toast.makeText(MainSetting.this, "請輸入正確的密碼", Toast.LENGTH_SHORT).show();
                }
            }
        });

        urlButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                addurl();
            }
        });


    }

    private void adddata() {
        SQLiteDatabase db = DH.getWritableDatabase();
        Cursor cursor = db.query("data", new String[]{"_id", "_url"}, null, null, null, null, null);
        while (cursor.moveToNext()){
            urlEditText.setText(cursor.getString(1));
        }
    }


    private void addurl() {
        SQLiteDatabase db = DH.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_url",urlEditText.getText().toString());
        db.insert("data",null,values);
    }

    //ToolBar
    private void SetToolBar() {

        toolbar.setTitle("設定");

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
        toolbar.inflateMenu(R.menu.menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);//toolbar 上面


        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mainlayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mActionBarDrawerToggle.syncState();
    }

    //左側表單
    private void SetLifeMenu() {
        liftmenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 點選時收起選單
                mainlayout.closeDrawer(GravityCompat.START);
                // 取得選項id
                int id = item.getItemId();
                // 依照id判斷點了哪個項目並做相應事件
                if (id == R.id.action_setting){
                    Toast.makeText(MainSetting.this, "目前顯示頁面：設定", Toast.LENGTH_SHORT).show();
                }else if(id == R.id.action_camera){
                    Intent intent = new Intent();
                    intent.setClass(MainSetting.this, MainActivity.class);
                    MainSetting.this.finish();
                    startActivity(intent);
                    return true;
                }else if(id == R.id.action_qrcode){
                    Intent intent = new Intent();
                    intent.setClass(MainSetting.this, DataViewActivity.class);
                    MainSetting.this.finish();
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

}
