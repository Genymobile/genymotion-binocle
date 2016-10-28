package com.genymotion.binocle.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.genymotion.api.GenymotionManager;
import com.genymotion.binocle.R;
import com.genymotion.binocle.PhoneSampleFragment;
import com.genymotion.binocle.SampleActivity;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class TestPhone {

    @Rule
    public ActivityTestRule<SampleActivity> mActivityRule = new ActivityTestRule<SampleActivity>(SampleActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent phoneIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
            phoneIntent.putExtra(SampleActivity.ARG_ITEM_ID, PhoneSampleFragment.TAG);
            return phoneIntent;
        }
    };

    private PhoneSampleFragment mFragmentPhone;

    @Before
    public void setUp() {
        // Create activity and get fragment back
        FragmentManager fragmentManager = mActivityRule.getActivity().getSupportFragmentManager();
        mFragmentPhone = (PhoneSampleFragment) fragmentManager.findFragmentByTag(PhoneSampleFragment.TAG);
    }

    @Test
    public void testSmsReception() {
        if (!GenymotionManager.isGenymotionDevice()) {
            // Avoid test on non Genymotion devices.
            return;
        }

        GenymotionManager genymotion;
        genymotion = GenymotionManager.getGenymotionManager(getInstrumentation().getTargetContext());

        // Send an SMS containing the 666 string
        genymotion.getRadio().sendSms("123456", "666");
        ImageView picto = (ImageView) mFragmentPhone.getView().findViewById(R.id.phone_activation_picto);

        // Check that the image is now visible
        Assert.assertEquals(picto.getVisibility(), View.VISIBLE);

        // Send an SMS that doesnot contain "666" string
        genymotion.getRadio().sendSms("123456", "123");

        // Check that the image is now gone
        Assert.assertEquals(picto.getVisibility(), View.GONE);
    }
}
