package com.genymotion.binocle.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.genymotion.api.Battery;
import com.genymotion.api.GenymotionManager;
import com.genymotion.binocle.BatterySampleFragment;
import com.genymotion.binocle.R;
import com.genymotion.binocle.SampleActivity;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class TestBattery {

    @Rule
    public ActivityTestRule<SampleActivity> mActivityRule = new ActivityTestRule<SampleActivity>(SampleActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent batteryIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
            batteryIntent.putExtra(SampleActivity.ARG_ITEM_ID, BatterySampleFragment.TAG);
            return batteryIntent;
        }
    };

    private BatterySampleFragment mFragmentBattery;

    @Before
    public void setUp() {
        // Create activity and get fragment back
        FragmentManager fragmentManager = mActivityRule.getActivity().getSupportFragmentManager();
        mFragmentBattery = (BatterySampleFragment) fragmentManager.findFragmentByTag(BatterySampleFragment.TAG);
    }

    @Test
    public void testBatteryWarning() {
        if (!GenymotionManager.isGenymotionDevice()) {
            // Avoid test on non Genymotion devices.
            return;
        }
        TextView tvWarning = (TextView) mFragmentBattery.getView().findViewById(R.id.tv_batteryWarning);
        GenymotionManager genymotion = GenymotionManager.getGenymotionManager(mActivityRule.getActivity());
        // Change battery level and charging status
        Log.d(BatterySampleFragment.TAG, "Force full battery + charging");
        genymotion.getBattery()
                .setLevel(100)
                .setStatus(Battery.Status.CHARGING);
        // Then ensure warning is hidden
        Assert.assertEquals(tvWarning.getVisibility(), View.GONE);
        // Change battery level and charging status
        Log.d(BatterySampleFragment.TAG, "Force low battery");
        genymotion.getBattery()
                .setLevel(3)
                .setStatus(Battery.Status.DISCHARGING);
        // Then ensure warning is visible
        Assert.assertEquals(tvWarning.getVisibility(), View.VISIBLE);
        // set battery mode back to HOST
        genymotion.getBattery().setMode(Battery.Mode.HOST);
    }
}
