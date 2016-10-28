package com.genymotion.binocle.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.genymotion.api.GenymotionManager;
import com.genymotion.binocle.R;
import com.genymotion.binocle.RadioSampleFragment;
import com.genymotion.binocle.SampleActivity;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class TestRadio {

    @Rule
    public ActivityTestRule<SampleActivity> mActivityRule = new ActivityTestRule<SampleActivity>(SampleActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent radioIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
            radioIntent.putExtra(SampleActivity.ARG_ITEM_ID, RadioSampleFragment.TAG);
            return radioIntent;
        }
    };

    private RadioSampleFragment mFragmentRadio;

    @Before
    public void setUp() {
        if (!GenymotionManager.isGenymotionDevice()) {
            // Avoid test on non Genymotion devices.
            return;
        }

        // Change IMEI before creating activity as the imei is read inside onStart()
        GenymotionManager genymotion;
        genymotion = GenymotionManager.getGenymotionManager(getInstrumentation().getTargetContext());
        // Faking a Google Nexus 4
        genymotion.getRadio().setDeviceId("353918050000000");

        // Create activity and get fragment back
        FragmentManager fragmentManager = mActivityRule.getActivity().getSupportFragmentManager();
        mFragmentRadio = (RadioSampleFragment) fragmentManager.findFragmentByTag(RadioSampleFragment.TAG);
    }

    @Test
    public void testDeviceId() {
        if (!GenymotionManager.isGenymotionDevice()) {
            // Avoid test on non Genymotion devices.
            return;
        }

        TextView tvDeviceType = (TextView) mFragmentRadio.getView().findViewById(R.id.tv_radioDeviceType);
        String text = tvDeviceType.getText().toString();
        Assert.assertTrue(text.endsWith("Nexus 4"));
    }
}
