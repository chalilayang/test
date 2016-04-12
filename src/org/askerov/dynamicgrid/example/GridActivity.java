package org.askerov.dynamicgrid.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Toast;
import org.askerov.dynamicgrid.DynamicGridView;

import com.chalilayang.test.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridActivity extends Activity {

    private static final String TAG = GridActivity.class.getName();

    private DynamicGridView gridView;
    private List<CheeseItem> mList;

    static class CheeseItem {
        private String name;
        private int resID;
        public CheeseItem(String nameTmp, int id) {
            name = nameTmp;
            resID = id;
        }
        public String getName() {
            return name;
        }
        public int getResID() {
            return resID;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        initData();
        gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);
        gridView.setLayoutAnimation(getAnimationController());
        gridView.setAdapter(new CheeseDynamicAdapter(this, mList,
                getResources().getInteger(R.integer.column_count)));
//        add callback to stop edit mode if needed
        gridView.setOnDropListener(new DynamicGridView.OnDropListener()
        {
            @Override
            public void onActionDrop()
            {
                gridView.stopEditMode();
            }
        });
        gridView.setOnDragListener(new DynamicGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {
                Log.d(TAG, "drag started at position " + position);
            }

            @Override
            public void onDragPositionsChanged(int oldPosition, int newPosition) {
                Log.d(TAG, String.format("drag item position changed from %d to %d", oldPosition, newPosition));
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridView.startEditMode(position);
                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GridActivity.this, parent.getAdapter().getItem(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (gridView.isEditMode()) {
            gridView.stopEditMode();
        } else {
            super.onBackPressed();
        }
    }
    
    public void initData() {
        if (mList == null) {
            mList = new ArrayList<GridActivity.CheeseItem>(50);
        }
        mList.clear();
        String[] datas = getResources().getStringArray(R.array.countries);
        for(int index = 0, count = datas.length; index < count; index ++) {
            int resId = R.drawable.behance;
            switch (index % 11) {
                case 0:
                    resId = R.drawable.behance;
                    break;
                case 1:
                    resId = R.drawable.pintrest;
                    break;
                case 2:
                    resId = R.drawable.youtube;
                    break;
                case 3:
                    resId = R.drawable.facebook;
                    break;
                case 4:
                    resId = R.drawable.dropbox;
                    break;
                case 5:
                    resId = R.drawable.googleplus;
                    break;
                case 6:
                    resId = R.drawable.instagram;
                    break;
                case 7:
                    resId = R.drawable.deviantart;
                    break;
                case 8:
                    resId = R.drawable.linkdin;
                    break;
                case 9:
                    resId = R.drawable.soundcloud;
                    break;
                case 10:
                    resId = R.drawable.twitter;
                    break;
                default:
                    break;
            }
            mList.add(new CheeseItem(datas[index], resId));
        }        
    }
    
    @Override
    public void onEnterAnimationComplete() {
        // TODO Auto-generated method stub
        super.onEnterAnimationComplete();
    }

    /** 
     * Layout动画 
     *  
     * @return 
     */  
    protected LayoutAnimationController getAnimationController() {  
        int duration = 300;
        AnimationSet set = new AnimationSet(true);  
  
        Animation animation = new AlphaAnimation(0.0f, 1.0f);  
        animation.setDuration(duration);  
        set.addAnimation(animation);  
  
        animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,  
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,  
                1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);  
        animation.setDuration(duration);  
        set.addAnimation(animation);  
  
        GridLayoutAnimationController controller = new GridLayoutAnimationController(set, 0.1f, 0.9f);
        controller.setDirectionPriority(GridLayoutAnimationController.PRIORITY_ROW);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);  
        return controller;  
    }
}
