package com.genymotion.binocle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BatterySampleFragment extends Fragment {

    public static final String TAG = "Battery";
    BroadcastReceiver batteryChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(BatterySampleFragment.TAG, "Battery value changed");
            handleBatteryStatus(intent);
        }
    };
    TextView tvBatteryWarning;

    @Override
    public void onResume() {
        super.onResume();

        //register battery listener
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getActivity().registerReceiver(batteryChangeReceiver, ifilter);
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(batteryChangeReceiver);

        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_battery_sample, container, false);

        //retrieve widgets
        tvBatteryWarning = (TextView) rootView.findViewById(R.id.tv_batteryWarning);

        //default values
        tvBatteryWarning.setVisibility(View.GONE);

        return rootView;
    }

    public void handleBatteryStatus(Intent batteryStatus) {

        // Are we charging / charged yet?
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = ( (status == BatteryManager.BATTERY_STATUS_CHARGING) 
                || (status == BatteryManager.BATTERY_STATUS_FULL) );

        // How much power?
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level / (float) scale;

        if (!isCharging && batteryPct < 0.10f) {
            Log.d(BatterySampleFragment.TAG, "Show battery warning");
            tvBatteryWarning.setVisibility(View.VISIBLE);
        } else {
            Log.d(BatterySampleFragment.TAG, "Hide battery warning");
            tvBatteryWarning.setVisibility(View.GONE);
        }
    }
}
