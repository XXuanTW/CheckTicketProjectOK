package com.example.user.checkticketproject;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



public class NFCActivity extends AppCompatActivity {
    NavigationView liftmenu;
    DrawerLayout mainlayout;
    Toolbar toolbar;
    TextView textNFC;
    TextView textDec;
    String [] arrayNFC = new String [4];
    private final String[][] techList = new String[][] {
            new String[] {
                    NfcA.class.getName(),
                    NfcB.class.getName(),
                    NfcF.class.getName(),
                    NfcV.class.getName(),
                    IsoDep.class.getName(),
                    MifareClassic.class.getName(),
                    MifareUltralight.class.getName(), Ndef.class.getName()
            }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        liftmenu = (NavigationView)findViewById(R.id.liftmenu);
        mainlayout = (DrawerLayout)findViewById(R.id.NFC_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        textNFC = (TextView)findViewById(R.id.textNFC);
        textDec = (TextView)findViewById(R.id.textDec);

        SetToolBar();
        SetLifeMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // creating pending intent:
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // creating intent receiver for NFC events:
        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        // enabling foreground dispatch for getting intent from NFC event:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.techList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // disabling foreground dispatch:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            String NFC = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
            int x = 0;
            for (int i=0 ; i<NFC.length() ; i+=2)
            {
                arrayNFC[x]= NFC.substring(i,i+2);
            x=x+1;
            }
            String stringNFC ="";
            for (int j=3 ; j>=0;j--){
                stringNFC+=arrayNFC[j];
            }
            textNFC.setText(stringNFC);
            String []arrayHex = new String[stringNFC.length()];

            for (int i = 0 ; i < stringNFC.length() ; i++){
                arrayHex[i]=stringNFC.substring(i,i+1);
            }
            int y = 1;
            long dec = 0;
            for(int i = stringNFC.length()-1 ; i >= 0 ; i--){
                dec = Long.valueOf(Integer.valueOf(arrayHex[i],16))*y +dec;
                y=y*16;
            }
            textDec.setText(String.valueOf(dec));
        }
    }

    private String ByteArrayToHexString(byte [] inarray) {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out = out + hex[i];
            i = in & 0x0f;
            out = out + hex[i];
        }
//        for(i =0; i<inarray.length; i++){
//            out += Integer.toHexString(inarray[i] & 0xFF);
//        }

        return out;
    }


    //toolbat設定
    private void SetToolBar() {

        toolbar.setTitle("NFC");

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
                if (id == R.id.action_camera) {
                    // 按下「剪票模式」要做的事
                    Intent intent = new Intent();
                    intent.setClass(NFCActivity.this,MainActivity.class);
                    NFCActivity.this.finish();
                    startActivity(intent);
                    return true;
                } else if (id == R.id.action_qrcode) {
                    // 按下「QRcode模式」要做的事
                    //傳送數值
                    // Bundle
                    Intent intent = new Intent();
                    intent.setClass(NFCActivity.this, DataViewActivity.class);
                    NFCActivity.this.finish();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("QRcodeID",textView.getText().toString());
//                    intent.putExtras(bundle);
                    startActivity(intent);
                    return true;
                }else if(id == R.id.action_NFC){
                    Toast.makeText(NFCActivity.this, "目前顯示頁面：NFC", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}
