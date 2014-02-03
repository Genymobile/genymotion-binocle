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

/**
 * Created by pascal on 1/2/14.
 */
public class BatterySampleFragment extends Fragment{

    public static final String TAG = "Battery";

    TextView tvBatteryWarning;
    BroadcastReceiver batteryChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(BatterySampleFragment.TAG, "battery value changed");
            handleBatteryStatus(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        //register battery listener
        Log.d(BatterySampleFragment.TAG, "register");
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getActivity().registerReceiver(batteryChangeReceiver, ifilter);
    }

    @Override
    public void onPause() {
        Log.d(BatterySampleFragment.TAG, "unregister");
        getActivity().unregisterReceiver(batteryChangeReceiver);

        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_battery_sample, container, false);

        //retrieve widgets
        tvBatteryWarning = (TextView)rootView.findViewById(R.id.tv_batteryWarning);

        //default values
        tvBatteryWarning.setVisibility(View.GONE);

        return rootView;
    }

    public void handleBatteryStatus(Intent batteryStatus) {

        // Are we charging / charged yet?
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

        // How much power?
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level / (float)scale;

        if (!isCharging && batteryPct < 0.10f) {
            Log.d(BatterySampleFragment.TAG, "show warning");
            tvBatteryWarning.setVisibility(View.VISIBLE);
        } else {
            Log.d(BatterySampleFragment.TAG, "hide warning");
            tvBatteryWarning.setVisibility(View.GONE);
        }
    }
}
