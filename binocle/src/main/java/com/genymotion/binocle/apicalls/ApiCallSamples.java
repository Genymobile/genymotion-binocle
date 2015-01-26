package com.genymotion.binocle.apicalls;

import com.genymotion.binocle.PhoneSampleFragment;
import com.genymotion.binocle.IdSampleFragment;
import com.genymotion.binocle.BatterySampleFragment;
import com.genymotion.binocle.GpsSampleFragment;
import com.genymotion.binocle.RadioSampleFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing api calls sample title
 */
public class ApiCallSamples {

    /**
     * An array of sample (apiCalls) items.
     */
    public static final List<ApiCallSamplesItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (apiCalls) items, by ID.
     */
    private static final Map<String, ApiCallSamplesItem> ITEM_MAP = new HashMap<>();

    static {
        // Add all api calls sample items.
        addItem(new ApiCallSamplesItem(BatterySampleFragment.TAG, "Battery"));
        addItem(new ApiCallSamplesItem(GpsSampleFragment.TAG, "Gps"));
        addItem(new ApiCallSamplesItem(RadioSampleFragment.TAG, "Radio"));
        addItem(new ApiCallSamplesItem(IdSampleFragment.TAG, "Id"));
        addItem(new ApiCallSamplesItem(PhoneSampleFragment.TAG, "Phone"));
    }

    private static void addItem(ApiCallSamplesItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A apiCallsSample item representing a piece of title.
     */
    public static class ApiCallSamplesItem {
        public final String id;
        public final String title;

        public ApiCallSamplesItem(String id, String title) {
            this.id = id;
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
