package com.chalilayang.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity implements OnItemClickListener {

    public static final String LIST_KEY_ICON = "icon";
    public static final String LIST_KEY_NAME = "name";

    public ListView mListView;
    private SimpleAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        if (mListView == null) {
            mListView = (ListView) findViewById(R.id.activity_list_view);
            mListAdapter = new SimpleAdapter(getApplicationContext(), getData(), R.layout.list_item_layout,
                    new String[] {LIST_KEY_ICON, LIST_KEY_NAME},
                    new int[] {R.id.myDataImage, R.id.myDataString});
            mListView.setAdapter(mListAdapter);
            mListView.setOnItemClickListener(this);
        }
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>(1);
        // map.put("img", R.drawable.e001);
        map.put(LIST_KEY_ICON, R.drawable.behance);
        map.put(LIST_KEY_NAME, getString(R.string.explosion));
        list.add(map);

        map = new HashMap<String, Object>(1);
        // map.put("img", R.drawable.e002);
        map.put(LIST_KEY_ICON, R.drawable.youtube);
        map.put(LIST_KEY_NAME, getString(R.string.waveloading));
        list.add(map);

        map = new HashMap<String, Object>(1);
        // map.put("img", R.drawable.e002);
        map.put(LIST_KEY_ICON, R.drawable.facebook);
        map.put(LIST_KEY_NAME, getString(R.string.sticky_list_view));
        list.add(map);
        
        map = new HashMap<String, Object>(1);
        // map.put("img", R.drawable.e002);
        map.put(LIST_KEY_ICON, R.drawable.dropbox);
        map.put(LIST_KEY_NAME, getString(R.string.lable_tag_sample));
        list.add(map);
        
        map = new HashMap<String, Object>(1);
        // map.put("img", R.drawable.e002);
        map.put(LIST_KEY_ICON, R.drawable.googleplus);
        map.put(LIST_KEY_NAME, getString(R.string.sticky_list_view_extra));
        list.add(map);
        
        map = new HashMap<String, Object>(1);
        // map.put("img", R.drawable.e002);
        map.put(LIST_KEY_ICON, R.drawable.dribble);
        map.put(LIST_KEY_NAME, getString(R.string.clip_path_demo));
        list.add(map);

        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, ExplosionActivity.class);
                break;
            case 1:
                intent = new Intent(this, WaveLoadingActivity.class);
                break;
            case 2:
                intent = new Intent(this, StickyListActivity.class);
                break;
            case 3:
                intent = new Intent(this, LableTagActivity.class);
                break;
            case 4:
                intent = new Intent(this, StickyListTestActivity.class);
                break;
            case 5:
                intent = new Intent(this, ClipPathTestActivity.class);
                break;
            default:
                break;
        }
        if (intent != null) {
            this.startActivity(intent);
        }
    }
}
