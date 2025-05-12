package com.androidlearning.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

//need to register the BroadcastReceiver in AndroidManifest.xml for API level below 25
//for 26 and above, have to register receiver via code
public class AirplaneModeReceiver extends BroadcastReceiver {

    //called when Broadcast Receiver receives a broadcast
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent != null) {
            if(Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
                boolean isOn = intent.getBooleanExtra("airplane_mode_state", false);

                String msg = isOn ? "Airplane Mode is On" : "Airplane Mode is Off";

                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
