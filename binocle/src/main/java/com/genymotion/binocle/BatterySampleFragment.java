package com.genymotion.binocle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.genymotion.Battery;
import com.genymotion.GenymotionManager;

/**
 * Created by pascal on 1/2/14.
 */
public class BatterySampleFragment extends Fragment{

    GenymotionManager genymotion;

    Button btMode, btLevel, btStatus;
    RadioGroup rgMode;
    RadioButton rbModeHost, rbModeManual;
    SeekBar sbLevel;
    ToggleButton tbBatteryStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_battery_sample, container, false);
        genymotion = GenymotionManager.getGenymotionManager();

        //retrieve widgets
        btMode = (Button)rootView.findViewById(R.id.bt_getBatteryMode);
        rgMode = (RadioGroup)rootView.findViewById(R.id.rd_batteryMode);
        rbModeHost = (RadioButton)rootView.findViewById(R.id.rd_batteryModeHost);
        rbModeManual = (RadioButton)rootView.findViewById(R.id.rd_batteryModeManual);
        btLevel = (Button)rootView.findViewById(R.id.bt_getBatteryLevel);
        sbLevel = (SeekBar)rootView.findViewById(R.id.sb_batteryLevel);
        btStatus = (Button)rootView.findViewById(R.id.bt_getBatteryStatus);
        tbBatteryStatus = (ToggleButton)rootView.findViewById(R.id.tb_batteryStatusPlugged);

        //init state & values
        Battery.Mode batteryMode = genymotion.getBattery().getMode();
        activateWidgets(batteryMode);
        if (batteryMode == Battery.Mode.MANUAL) {
            rbModeManual.setChecked(true);
        } else {
            rbModeHost.setChecked(true);
        }
        int batteryLevel = genymotion.getBattery().getLevel();
        sbLevel.setProgress(batteryLevel);
        Battery.Status batteryStatus = genymotion.getBattery().getStatus();
        if (batteryStatus == Battery.Status.CHARGING) {
            tbBatteryStatus.setChecked(true);
        } else {
            tbBatteryStatus.setChecked(false);
        }


        // set up events
        btMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Battery.Mode batteryMode = genymotion.getBattery().getMode();
                Toast.makeText(getActivity(), getResources().getString(R.string.battery_mode, batteryMode.name()), Toast.LENGTH_SHORT).show();
            }
        });
        View.OnClickListener modeListener =  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Battery.Mode batteryMode = Battery.Mode.HOST;
                if (rbModeManual.isChecked()) {
                    batteryMode = Battery.Mode.MANUAL;
                }
                activateWidgets(batteryMode);
                genymotion.getBattery().setMode(batteryMode);
                Toast.makeText(getActivity(), getResources().getString(R.string.set_battery_mode, batteryMode.name()), Toast.LENGTH_SHORT).show();
            }
        };
        rbModeHost.setOnClickListener(modeListener);
        rbModeManual.setOnClickListener(modeListener);
        btLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int level = genymotion.getBattery().getLevel();
                Toast.makeText(getActivity(), getResources().getString(R.string.battery_level, level), Toast.LENGTH_SHORT).show();
            }
        });
        sbLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                genymotion.getBattery().setLevel(progress);
                Toast.makeText(getActivity(), getResources().getString(R.string.set_battery_level, progress), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        btStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Battery.Status batteryStatus = genymotion.getBattery().getStatus();
                Toast.makeText(getActivity(), getResources().getString(R.string.battery_status, batteryStatus.name()), Toast.LENGTH_SHORT).show();
            }
        });
        tbBatteryStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Battery.Status batteryStatus = Battery.Status.DISCHARGING;
                if (tbBatteryStatus.isChecked()) {
                    batteryStatus = Battery.Status.CHARGING;
                }
                genymotion.getBattery().setStatus(batteryStatus);
                Toast.makeText(getActivity(), getResources().getString(R.string.set_battery_status, batteryStatus.name()), Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    void activateWidgets(Battery.Mode batteryMode) {
        if (batteryMode == Battery.Mode.MANUAL) {
            sbLevel.setEnabled(true);
            tbBatteryStatus.setEnabled(true);
        } else {
            sbLevel.setEnabled(false);
            tbBatteryStatus.setEnabled(false);
        }
    }
}
