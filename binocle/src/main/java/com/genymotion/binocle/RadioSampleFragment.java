package com.genymotion.binocle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RadioSampleFragment extends Fragment {

    public static final String TAG = "Radio";

    private static final String NEXUS_4_TAC = "35391805";
    private static final String NEXUS_5_TAC = "35824005";

    private TextView tvRadioDeviceType = null;
    private TextView tvRadioDeviceImei = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_radio_sample, container, false);

        //retrieve widgets
        tvRadioDeviceType = (TextView) rootView.findViewById(R.id.tv_radioDeviceType);
        tvRadioDeviceImei = (TextView) rootView.findViewById(R.id.tv_radioDeviceImei);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        String deviceType = getDeviceType(imei);

        tvRadioDeviceType.setText(getResources().getString(R.string.radio_device_type, deviceType));
        tvRadioDeviceImei.setText(getResources().getString(R.string.radio_device_imei, imei));
    }

    private String getDeviceType(String imei) {
        if (imei.startsWith(NEXUS_4_TAC)) {
            return "Nexus 4";
        } else if (imei.startsWith(NEXUS_5_TAC)) {
            return "Nexus 5";
        } else {
            return "Unknown";
        }
    }
}
