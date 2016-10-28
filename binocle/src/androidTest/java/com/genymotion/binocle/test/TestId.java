package com.genymotion.binocle.test;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.TextView;

import com.genymotion.api.GenymotionManager;
import com.genymotion.binocle.IdSampleFragment;
import com.genymotion.binocle.R;
import com.genymotion.binocle.SampleActivity;

import junit.framework.Assert;
import org.hamcrest.core.StringEndsWith;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TestId {

    @Rule
    public ActivityTestRule<SampleActivity> mActivityRule = new ActivityTestRule<SampleActivity>(SampleActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent androidIdIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
            androidIdIntent.putExtra(SampleActivity.ARG_ITEM_ID, IdSampleFragment.TAG);
            return androidIdIntent;
        }
    };

    private IdSampleFragment mFragmentAndroidID;

    @Before
    public void setUp() {
        // Create activity and get fragment back
        FragmentManager fragmentManager = mActivityRule.getActivity().getSupportFragmentManager();
        mFragmentAndroidID = (IdSampleFragment) fragmentManager.findFragmentByTag(IdSampleFragment.TAG);
    }

    @Test
    public void testAndroidId() {
        if (!GenymotionManager.isGenymotionDevice()) {
            // Avoid test on non Genymotion devices.
            return;
        }

        TextView tvAndroidId = (TextView) mFragmentAndroidID.getView().findViewById(R.id.tv_androidId);

        GenymotionManager genymotion;
        genymotion = GenymotionManager.getGenymotionManager(getInstrumentation().getTargetContext());

        // Encoding with android id = deadbeefdeadbeef
        genymotion.getId().setAndroidId("deadbeefdeadbeef");
        Espresso.onView(withId(R.id.btn_encode)).perform(click());


        // Decoding with android id = deadbeefdeadbeef
        Espresso.onView(withId(R.id.btn_decode)).perform(click());

        // Must have been correctly decoded
        Espresso.onView(withId(R.id.tv_androidId))
                .check(ViewAssertions.matches(withText(StringEndsWith.endsWith(IdSampleFragment.SECRET_MESSAGE))));

        // Decoding with android id = baadcafebaadcafe
        genymotion.getId().setAndroidId("baadcafebaadcafe");
        try {
            Thread.sleep(1000); //Android needs time to poll sensors and broadcast event.
        } catch (InterruptedException ignored) {}
        Espresso.onView(withId(R.id.btn_decode)).perform(click());

        // Must NOT been correctly decoded
        String text = tvAndroidId.getText().toString();
        Assert.assertFalse(text.endsWith(IdSampleFragment.SECRET_MESSAGE));
    }
}
