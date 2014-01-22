package com.genymotion.binocle.apicalls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing api calls sample content
 */
public class ApiCallSamplesContent {

    /**
     * An array of sample (apiCalls) items.
     */
    public static List<ApiCallSamplesItem> ITEMS = new ArrayList<ApiCallSamplesItem>();

    /**
     * A map of sample (apiCalls) items, by ID.
     */
    public static Map<Integer, ApiCallSamplesItem> ITEM_MAP = new HashMap<Integer, ApiCallSamplesItem>();

    static {
        // Add all api calls sample items.
        addItem(new ApiCallSamplesItem(1, "Battery"));
        addItem(new ApiCallSamplesItem(2, "Gps"));
        addItem(new ApiCallSamplesItem(3, "Radio"));
        addItem(new ApiCallSamplesItem(4, "Orientation"));
    }

    private static void addItem(ApiCallSamplesItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A apiCallsSample item representing a piece of content.
     */
    public static class ApiCallSamplesItem {
        public Integer id;
        public String content;

        public ApiCallSamplesItem(Integer id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
