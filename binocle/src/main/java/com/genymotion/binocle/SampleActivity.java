package com.genymotion.binocle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

/**
 * An activity representing a single ApiCall detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ApiCallListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ApiCallDetailFragment}.
 */
public class SampleActivity extends FragmentActivity {

    public static final String ARG_ITEM_ID = "item_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apicall_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            String tag = getIntent().getStringExtra(ARG_ITEM_ID);
            SampleActivity.createAndAddFragment(tag, getSupportFragmentManager());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, ApiCallListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void createAndAddFragment(String tag, FragmentManager fm) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_ITEM_ID, tag);
        Fragment fragment;
        if (BatterySampleFragment.TAG.equals(tag)) {
            fragment = new BatterySampleFragment();
        } else if (GpsSampleFragment.TAG.equals(tag)) {
            fragment = new GpsSampleFragment();
        } else if (RadioSampleFragment.TAG.equals(tag)) {
            fragment = new RadioSampleFragment();
        } else {
            fragment = new BatterySampleFragment();
        }
        fragment.setArguments(arguments);
        fm.beginTransaction()
                .add(R.id.apicall_detail_container, fragment, tag)
                .commit();
    }
}
