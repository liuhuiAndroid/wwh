package com.android.wwh.opensourceprojectanalysis.EventBus;

import java.util.List;

/**
 * Created by we-win on 2017/7/7.
 */

public class Event {

    /**
     * 列表加载事件
     */
    public static class ItemListEvent {
        private List<Item> items;

        public ItemListEvent(List<Item> items) {
            this.items = items;
        }

        public List<Item> getItems() {
            return items;
        }
    }

}
