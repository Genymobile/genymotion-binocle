package com.genymotion.binocle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * An activity representing a list of ApiCalls. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link SampleActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ApiCallListFragment} and the item details
 * (if present) is a *SampleFragment.
 * <p>
 * This activity also implements the required
 * {@link ApiCallListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ApiCallListActivity extends FragmentActivity
        implements ApiCallListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apicall_list);

        if (findViewById(R.id.apicall_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ApiCallListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.apicall_list))
                    .setActivateOnItemClick(true);
        }
    }

    /**
     * Callback method from {@link ApiCallListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            SampleActivity.createAndAddFragment(id, getSupportFragmentManager());

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent;
            detailIntent = new Intent(this, SampleActivity.class);
            detailIntent.putExtra(SampleActivity.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}