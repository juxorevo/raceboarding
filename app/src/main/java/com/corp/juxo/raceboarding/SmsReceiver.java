
package com.corp.juxo.raceboarding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;


public class SmsReceiver extends BroadcastReceiver {

    private final String ACTION_RECEIVE_SMS = "android.provider.Telephony.SMS_RECEIVED";
    public static boolean EXECUTE = true;
    private Intent mIntent;
    private String str = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        mIntent = intent;
        String action = intent.getAction();

        if (action.equals(ACTION_RECEIVE_SMS) && EXECUTE) {
            Bundle bundle;
            bundle = intent.getExtras();
            if (bundle != null) {
                extractMessage();
            }
        }
    }

    public void extractMessage(){


        SmsMessage[] msgs = getMessagesFromIntent(this.mIntent);
        if (msgs != null) {
            for (int i = 0; i < msgs.length; i++) {
                str += msgs[i].getMessageBody().toString();
                str += "\n";
            }

                    MainActivity.mainActivity.getHandler().post(new Runnable() {
                        public void run() {
                            MainActivity.mainActivity.getTextPrincipal().setText(str);
                        }
                    });

        }
    }

    public static SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
        byte[][] pduObjs = new byte[messages.length][];

        for (int i = 0; i < messages.length; i++) {
            pduObjs[i] = (byte[]) messages[i];
        }
        byte[][] pdus = new byte[pduObjs.length][];
        int pduCount = pdus.length;
        SmsMessage[] msgs = new SmsMessage[pduCount];
        for (int i = 0; i < pduCount; i++) {
            pdus[i] = pduObjs[i];
            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
        }
        return msgs;
    }

}
