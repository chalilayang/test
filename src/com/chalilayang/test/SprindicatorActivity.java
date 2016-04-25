package com.chalilayang.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.chalilayang.test.customview.springindicator.SpringIndicator;
import com.chalilayang.test.customview.springindicator.viewpager.ScrollerViewPager;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerManager;

import java.util.Arrays;
import java.util.List;


public class SprindicatorActivity extends ActionBarActivity {

    ScrollerViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_springindicator_layout);

        viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle(R.string.sprindicator_demo);
        setSupportActionBar(toolbar);

        PagerManager manager = new PagerManager();
        List<String> titles = getTitles();
        List<Integer> Ress = getBgRes();
        for (int index = 0, count = titles.size(); index < count; index ++) {
            GuideFragment mFragment = new GuideFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("data", Ress.get(index));
            mFragment.setArguments(bundle);
            manager.addFragment(mFragment, titles.get(index));
        }
        
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();

        // just set viewPager
        springIndicator.setViewPager(viewPager);

    }

    private List<String> getTitles(){
        return Arrays.asList("1", "2", "3", "4");
    }

    private List<Integer> getBgRes(){
        return Arrays.asList(R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sprindicator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
