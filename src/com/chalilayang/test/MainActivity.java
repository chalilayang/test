package com.chalilayang.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.askerov.dynamicgrid.example.GridActivity;

import com.balysv.materialmenu.MaterialMenuView;
import com.balysv.materialmenu.MaterialMenuDrawable.IconState;
import com.mobeta.android.demodslv.Launcher;

public class MainActivity extends Activity implements OnItemClickListener {

    public static final String LIST_KEY_ICON = "icon";
    public static final String LIST_KEY_NAME = "name";

    public ListView mListView;
    private SimpleAdapter mListAdapter;
    public DisplayMetrics mDisplayMetrics;
    private MaterialMenuView mMaterialMenuView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initMetrics();
    }
    
    private void initMetrics() {
        mDisplayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        Log.e("displayMetrics", mDisplayMetrics.toString());
    }

    private void init() {
        if (mMaterialMenuView == null) {
            mMaterialMenuView = (MaterialMenuView) findViewById(R.id.action_bar_menu);
            mMaterialMenuView.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    IconState state = mMaterialMenuView.getState();
                    switch (state) {
                        case ARROW:
                            mMaterialMenuView.animateState(IconState.BURGER);
                            break;
                        case BURGER:
                            mMaterialMenuView.animateState(IconState.ARROW);
                            break;
                        default:
                            break;
                    }
                    
                }
            });
        }
        if (mListView == null) {
            mListView = (ListView) findViewById(R.id.activity_list_view);
            mListAdapter = new SimpleAdapter(getApplicationContext(), getData(), R.layout.list_item_layout,
                    new String[] {LIST_KEY_ICON, LIST_KEY_NAME},
                    new int[] {R.id.myDataImage, R.id.myDataString});
            mListView.setLayoutAnimation(getAnimationController());
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
        map.put(LIST_KEY_ICON, R.drawable.instagram);
        map.put(LIST_KEY_NAME, getString(R.string.clip_path_demo));
        list.add(map);
        
        map = new HashMap<String, Object>(1);
        // map.put("img", R.drawable.e002);
        map.put(LIST_KEY_ICON, R.drawable.deviantart);
        map.put(LIST_KEY_NAME, getString(R.string.title_activity_dynamic_gridview));
        list.add(map);
        
        map = new HashMap<String, Object>(1);
        // map.put("img", R.drawable.e002);
        map.put(LIST_KEY_ICON, R.drawable.linkdin);
        map.put(LIST_KEY_NAME, getString(R.string.dslv_demo));
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
                intent = new Intent(this, CustomViewActivity.class);
                break;
            case 6:
                intent = new Intent(this, GridActivity.class);
                break;
            case 7:
                intent = new Intent(this, Launcher.class);
                break;
            default:
                break;
        }
        if (intent != null) {
            this.startActivity(intent);
        }
    }
    
    /** 
     * Layout动画 
     *  
     * @return 
     */  
    protected LayoutAnimationController getAnimationController() {  
        int duration=400;  
        AnimationSet set = new AnimationSet(true);  
  
        Animation animation = new AlphaAnimation(0.0f, 1.0f);  
        animation.setDuration(duration);  
        set.addAnimation(animation);  
  
        animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,  
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_SELF,  
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(duration);  
        set.addAnimation(animation);  
  
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.1f);  
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);  
        return controller;  
    }
}
