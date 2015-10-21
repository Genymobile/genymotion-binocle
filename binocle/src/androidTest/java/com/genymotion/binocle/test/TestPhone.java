package com.genymotion.binocle.test;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageView;

import com.genymotion.api.GenymotionManager;
import com.genymotion.binocle.PhoneSampleFragment;
import com.genymotion.binocle.R;
import com.genymotion.binocle.SampleActivity;

import junit.framework.Assert;


public class TestPhone extends ActivityInstrumentationTestCase2<SampleActivity> {

    private PhoneSampleFragment fragmentPhone;

    public TestPhone() {
        super(SampleActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        if (!GenymotionManager.isGenymotionDevice()) {
            // Avoid test on non Genymotion devices.
            return;
        }

        // Add parameter to allow activity to start and create fragment PhoneSampleFragment.
        Intent phoneIntent;
        phoneIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
        phoneIntent.putExtra(SampleActivity.ARG_ITEM_ID, PhoneSampleFragment.TAG);
        setActivityIntent(phoneIntent);

        // Create activity and get fragment back
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentPhone = (PhoneSampleFragment) fragmentManager.findFragmentByTag(PhoneSampleFragment.TAG);
    }

    public void testSmsReception() {

        if (!GenymotionManager.isGenymotionDevice()) {
            // Avoid test on non Genymotion devices.
            return;
        }

        GenymotionManager genymotion;
        genymotion = GenymotionManager.getGenymotionManager(getInstrumentation().getTargetContext());

        // Send an SMS containing the 666 string
        genymotion.getRadio().sendSms("123456", "666");
        ImageView picto = (ImageView) fragmentPhone.getView().findViewById(R.id.phone_activation_picto);

        // Check that the image is now visible
        Assert.assertEquals(picto.getVisibility(), View.VISIBLE);

        // Send an SMS that doesnot contain "666" string
        genymotion.getRadio().sendSms("123456", "123");

        // Check that the image is now gone
        Assert.assertEquals(picto.getVisibility(), View.GONE);
    }
}
