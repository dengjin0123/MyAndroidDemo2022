package com.xiangke.myandroiddemo;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.xiangke.adapter.MyFragmentPagerAdapter;
import com.xiangke.helper.DialogHelper;
import com.xiangke.myandroiddemo.activity.TestMvpActivity;
import com.xiangke.base.ui.activity.BaseSimpleActivity;
import com.xiangke.myandroiddemo.fragment.FindFragment;
import com.xiangke.myandroiddemo.fragment.HomeFragment;
import com.xiangke.myandroiddemo.fragment.UserFragment;
import com.xiangke.utilcode.LogUtils;
import com.xiangke.utilcode.PermissionUtils;
import com.xiangke.utilcode.UtilsTransActivity;
import com.xiangke.utilcode.constant.PermissionConstants;
import com.xiangke.view.bar.BottomBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseSimpleActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.main_vp)
    ViewPager2 viewPager;
    @BindView(R.id.main_btbar)
    BottomBar bottomBar;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private int pos = 0;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        fragments.add(new HomeFragment());
        fragments.add(new FindFragment());
        fragments.add(new UserFragment());
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(this, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.size());

        Resources resources = getResources();
        String[] stringArray = resources.getStringArray(R.array.home_button_bar);
        String[] homeDefaultIds = resources.getStringArray(R.array.home_img_default);
        String[] homePressIds = resources.getStringArray(R.array.home_img_check);
        for (int i = 0; i < stringArray.length; i++) {
            int defaultIds = resources.getIdentifier(homeDefaultIds[i], "mipmap", this.getPackageName());
            int pressIds = resources.getIdentifier(homePressIds[i], "mipmap", this.getPackageName());
            bottomBar.addItem(stringArray[i], defaultIds, pressIds);
        }
        bottomBar.setTextColor(R.color.black, R.color.main_app_color);
        setCurrentItem(pos);

        bottomBar.setOnCheckedChangeListener(new BottomBar.OnCheckedChangeListener() {
            @Override
            public void onChange(int index) {
                pos = index;
                setCurrentItem(pos);
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 滑动到第几个
                pos = position;
                setCurrentItem(pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    protected void initData() {
        PermissionUtils
                .permission(PermissionConstants.LOCATION)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(@NonNull UtilsTransActivity activity, @NonNull ShouldRequest shouldRequest) {
                        DialogHelper.showRationaleDialog(shouldRequest);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
            @Override
            public void onGranted(@NonNull List<String> granted) {
                LogUtils.i(TAG, "granted = "+ granted);
            }

            @Override
            public void onDenied(@NonNull List<String> deniedForever, @NonNull List<String> denied) {
                if (!deniedForever.isEmpty()) {
                    DialogHelper.showOpenAppSettingDialog();
                }
                LogUtils.i(TAG, "deniedForever = "+ deniedForever, "\n denied = " + denied);
            }
        }).request();
    }

    private void setCurrentItem(int pos) {
        bottomBar.setCurrentItem(pos);
        viewPager.setCurrentItem(pos);
    }

    public void jumpPage(View view) {
//        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        Intent intent = new Intent(MainActivity.this, TestMvpActivity.class);
        startActivity(intent);
    }
}