package com.genymotion.binocle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PhoneSampleFragment extends Fragment {

    public static final String TAG = "Phone";

    private final BroadcastReceiver smsReceivedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();

            if (extras == null) {
                return;
            }

            Object[] rawMessages = (Object[])extras.get("pdus");

            for (Object rawMessage : rawMessages) {
                SmsMessage message = SmsMessage.createFromPdu((byte[])rawMessage);

                checkSMS(message.getMessageBody());
            }
        }
    };

    private TextView activation_label = null;
    private ImageView activation_picto = null;

    @Override
    public void onResume() {
        super.onResume();

        // Register sms listener
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        getActivity().registerReceiver(smsReceivedReceiver, filter);
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(smsReceivedReceiver);

        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_phone_sample, container, false);

        activation_label = (TextView)rootView.findViewById(R.id.phone_activation_label);
        activation_picto = (ImageView)rootView.findViewById(R.id.phone_activation_picto);

        return rootView;
    }

    private void checkSMS(String message) {
        if (message.contains("666")) {
            activation_label.setText(R.string.phone_received);
            activation_picto.setVisibility(View.VISIBLE);
        } else {
            activation_label.setText(R.string.phone_waiting);
            activation_picto.setVisibility(View.GONE);
        }
    }

}