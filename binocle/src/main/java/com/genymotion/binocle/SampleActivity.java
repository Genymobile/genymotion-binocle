package com.genymotion.binocle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * An activity representing a single ApiCall detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ApiCallListActivity}.
 */
public class SampleActivity extends AppCompatActivity {

    public static final String ARG_ITEM_ID = "item_id";

    public static void createAndReplaceFragment(String tag, FragmentManager fm) {

        Fragment fragment = fm.findFragmentByTag(tag);

        // Create fragment if it does not already exists
        if (fragment == null) {
            switch (tag) {
                case BatterySampleFragment.TAG:
                    fragment = new BatterySampleFragment();
                    break;
                case GpsSampleFragment.TAG:
                    fragment = new GpsSampleFragment();
                    break;
                case RadioSampleFragment.TAG:
                    fragment = new RadioSampleFragment();
                    break;
                case IdSampleFragment.TAG:
                    fragment = new IdSampleFragment();
                    break;
                case PhoneSampleFragment.TAG:
                    fragment = new PhoneSampleFragment();
                    break;
                case DiskIOSampleFragment.TAG:
                    fragment = new DiskIOSampleFragment();
                    break;
                default:
                    break;
            }
        }

        if (fragment != null) {
            // Replace current fragment by new one
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.apicall_detail_container, fragment, tag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apicall_detail);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            createAndReplaceFragment(tag, getSupportFragmentManager());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.binocle, menu);
        return true;
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
        } else if (id == R.id.menu_refresh) {
            String tag = getIntent().getStringExtra(ARG_ITEM_ID);
            createAndReplaceFragment(tag, getSupportFragmentManager());
        }
        return super.onOptionsItemSelected(item);
    }
}
