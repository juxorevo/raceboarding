package com.corp.juxo.raceboarding;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static MainActivity mainActivity;
    private Handler handler;
    private TextView textPrincipal;
    private SmsReceiver receiverSms;
    private static final String SMSSENT = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        handler = new Handler();
        textPrincipal = (TextView) findViewById(R.id.PrincipalText);
        try {
            this.unregisterReceiver(receiverSms);
            receiverSms = null;
        }catch(IllegalArgumentException e){

        }
        receiverSms = new SmsReceiver();
        this.registerReceiver(receiverSms, new IntentFilter(SMSSENT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            this.unregisterReceiver(receiverSms);
            receiverSms = null;
        }catch(IllegalArgumentException e){

        }
    }

    public TextView getTextPrincipal() {
        return textPrincipal;
    }

    public void setTextPrincipal(TextView textPrincipal) {
        this.textPrincipal = textPrincipal;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
