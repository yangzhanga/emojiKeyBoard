package adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.GridView;

/**
 * Created by zhangyang on 17/2/28.
 */

public class EmojiVpAdapter extends PagerAdapter {
    private Context mContext;

    private final GridView[] gridViewList;

    public EmojiVpAdapter(Context mContext,GridView[] gridViewList) {
        this.mContext=mContext;
        this.gridViewList = gridViewList;
    }

    @Override
    public int getCount() {
        return gridViewList.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(gridViewList[arg1]);
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(gridViewList[arg1]);
        return gridViewList[arg1];
    }



}