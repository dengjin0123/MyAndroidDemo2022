package com.xiangke.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.xiangke.bean.PageTitleBean;
import com.xiangke.common.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.abs.IPagerNavigator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;

/**
 * @Date:2022/2/28 11:10
 * @Author:dengjin
 * @Name: 指示器帮助类
 */
public class MagicIndicatorHelper {
    public static void initIndicator(Context context, final MagicIndicator magicIndicator,
                                     ViewPager viewPager, ArrayList<PageTitleBean> arrayList){
        initSelectableIndicator(context,magicIndicator,viewPager,arrayList,18,20,true);
    }

    public static void initSelectableIndicator(Context context, final MagicIndicator magicIndicator,
                                             ViewPager viewPager, ArrayList<PageTitleBean> arrayList,
                                               int defaultSize,int seSize,boolean isAdjust){
        magicIndicator.setBackgroundColor(context.getResources().getColor(R.color.color_f5f5f5));
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdjustMode(isAdjust);
        if (!isAdjust){
            commonNavigator.setEnablePivotScroll(true);
            commonNavigator.setSmoothScroll(true);
        }
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView simplePagerTitleView = new CommonPagerTitleView(context);
                simplePagerTitleView.setContentView(R.layout.layout_simple_pager_title);
                final TextView tv_title = simplePagerTitleView.findViewById(R.id.layout_tv_title);
                String title = arrayList.get(index).getTitle();
                String count = arrayList.get(index).getCount();
                tv_title.setText(title+" "+count);
                simplePagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        tv_title.setTextColor(context.getResources().getColor(R.color.main_color_00bbcc));
                        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,seSize);
                        tv_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        tv_title.setTextColor(context.getResources().getColor(R.color.gray666666));
                        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,defaultSize);
                        tv_title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                        tv_title.setScaleX(1.0f);
                        tv_title.setScaleY(1.0f);
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                        tv_title.setScaleX(1.0f);
                        tv_title.setScaleY(1.0f);
                    }
                });

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setYOffset(10);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
//                indicator.setRoundRadius(UIUtil.dip2px(context, 0));
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setLineWidth(UIUtil.dip2px(context, 35));
                indicator.setColors(context.getResources().getColor(R.color.main_color_00bbcc));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        bindVp(magicIndicator, viewPager);
    }

    public static void bindVp(final MagicIndicator magicIndicator, ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });
    }

    public static void updateTitle(MagicIndicator magicIndicator, int index, String title, String count) {
        IPagerNavigator navigator = magicIndicator.getNavigator();
        if (navigator instanceof CommonNavigator) {
            CommonNavigator commonNavigator = (CommonNavigator) navigator;
            IPagerTitleView pagerTitleView = commonNavigator.getPagerTitleView(index);
            if (pagerTitleView instanceof CommonPagerTitleView) {
                CommonPagerTitleView commonPagerTitleView = (CommonPagerTitleView) pagerTitleView;
                TextView tv_title = commonPagerTitleView.findViewById(R.id.layout_tv_title);
                tv_title.setText(title+" "+count);
            }
        }
    }
}
