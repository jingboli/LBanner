package com.lijingbo.lbanner;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lijingbo.lbanner.bean.NewsBean;
import com.lijingbo.lbanner.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List< NewsBean > newsLists;
    private ViewPager vp_image;
    private TextView tv_intro;
    private LinearLayout ll_donot_group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        vp_image = (ViewPager) findViewById(R.id.vp_image);
        tv_intro = (TextView) findViewById(R.id.tv_intro);
        ll_donot_group = (LinearLayout) findViewById(R.id.ll_donot_group);
    }

    private void initData() {
        newsLists = new ArrayList<>();
        newsLists.add(new NewsBean(R.drawable.a, "新西兰风景"));
        newsLists.add(new NewsBean(R.drawable.b, "大峡谷地质公园"));
        newsLists.add(new NewsBean(R.drawable.c, "阿西尼伯阴山风景"));
        newsLists.add(new NewsBean(R.drawable.d, "大新疆风光"));
        newsLists.add(new NewsBean(R.drawable.e, "美好的大草原，风景，摄影"));

        //初始化point
        for ( int i = 0; i < newsLists.size(); i++ ) {
            View view = new View(this);
            view.setBackgroundResource(R.drawable.point_unfocus);
            int dipValue = DensityUtil.dip2px(MainActivity.this, 8);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dipValue, dipValue);
            if ( i > 0 ) {
                params.leftMargin = DensityUtil.dip2px(MainActivity.this, 6);
            }
            view.setLayoutParams(params);
            ll_donot_group.addView(view);
        }

    }

    private void initListener() {
        vp_image.setAdapter(new NewsImageAdapter());
        vp_image.addOnPageChangeListener(new VpOnPageChangeListener());
        updateIntroAndPoint(0);
    }

    class NewsImageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return newsLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int newPosition = position % newsLists.size();
            View View = new View(MainActivity.this);
            View.setBackgroundResource(newsLists.get(newPosition).getImageResource());
            container.addView(View);
            //点击当前显示的view,进入点击操作。
            View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "点击了：" + newsLists.get(newPosition).getName(), Toast.LENGTH_SHORT).show();
                }
            });
            return View;
        }
    }

    class VpOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(final int position) {
            //根据不同的页面，显示不同的文本和设置point的颜色
            updateIntroAndPoint(position % newsLists.size());

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void updateIntroAndPoint(int position) {
        tv_intro.setText(newsLists.get(position).getName());
        for ( int i = 0; i < ll_donot_group.getChildCount(); i++ ) {
            //当前选中的point设置为白色
            if ( position == i ) {
                ll_donot_group.getChildAt(i).setBackgroundResource(R.drawable.point_focus);
            } else {
                ll_donot_group.getChildAt(i).setBackgroundResource(R.drawable.point_unfocus);
            }
        }
    }


}
