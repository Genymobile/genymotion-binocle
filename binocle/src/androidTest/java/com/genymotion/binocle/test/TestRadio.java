package com.genymotion.binocle.test;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.genymotion.api.GenymotionManager;
import com.genymotion.binocle.R;
import com.genymotion.binocle.RadioSampleFragment;
import com.genymotion.binocle.SampleActivity;

import junit.framework.Assert;


public class TestRadio extends ActivityInstrumentationTestCase2<SampleActivity> {

    RadioSampleFragment fragmentRadio;

    public TestRadio() {
        super(SampleActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        if (!GenymotionManager.isGenymotionDevice()) {
            // Avoid test on non Genymotion devices.
            return;
        }

        // Change IMEI before creating activity as the imei is read inside onStart()
        GenymotionManager genymotion;
        genymotion = GenymotionManager.getGenymotionManager(getInstrumentation().getTargetContext());
        // Faking a Google Nexus 4
        genymotion.getRadio().setDeviceId("353918050000000");

        // Add parameter to allow activity to start and create fragment GpsSampleFragment.
        Intent radioIntent;
        radioIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
        radioIntent.putExtra(SampleActivity.ARG_ITEM_ID, RadioSampleFragment.TAG);
        setActivityIntent(radioIntent);

        // Create activity and get fragment back
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentRadio = (RadioSampleFragment) fragmentManager.findFragmentByTag(RadioSampleFragment.TAG);
    }

    public void testDeviceId() {

        if (!GenymotionManager.isGenymotionDevice()) {
            // Avoid test on non Genymotion devices.
            return;
        }

        TextView tvDeviceType = (TextView) fragmentRadio.getView().findViewById(R.id.tv_radioDeviceType);
        String text = tvDeviceType.getText().toString();
        Assert.assertTrue(text.endsWith("Nexus 4"));
    }
}
