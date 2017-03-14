package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhangyang.emojidemo.R;

import java.util.List;

import adapter.EmojiGridViewAdapter;
import adapter.EmojiVpAdapter;
import bean.Emoji;
import widget.OnOperationListener;

/**
 * Created by zhangyang on 17/2/28.
 */

public class EmojiFragment extends Fragment {
    private static final int ITEM_PAGE_COUNT = 23;
    private ViewPager mPagerFace;
    private LinearLayout pagePointLayout;
    private GridView[] allPageViews;
    private RadioButton[] pointViews;
    private OnOperationListener listener;
    private List<Emoji> datas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = null   ;
        if (view == null) {
            view = inflater.inflate(R.layout.chat_frag_face, null);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        init(view);
        return view;
    }

    private void init(View view) {
        datas = Emoji.getEmojicons();
        initWidget(view);

    }

    private void initWidget(View view) {
        mPagerFace = (ViewPager) view.findViewById(R.id.frag_pager_face);
        pagePointLayout = (LinearLayout) view.findViewById(R.id.frag_point);

        int total = datas.size();
        int pages = total / ITEM_PAGE_COUNT
                + (total % ITEM_PAGE_COUNT == 0 ? 0 : 1);

        allPageViews = new GridView[pages];
        pointViews = new RadioButton[pages];

        for (int x = 0; x < pages; x++) {
            int start = x * ITEM_PAGE_COUNT;
            int end = (start + ITEM_PAGE_COUNT) > total ? total
                    : (start + ITEM_PAGE_COUNT);

            Log.e("end",end+"");

            final List<Emoji> itemDatas = datas.subList(start, end);
            GridView gridview = new GridView(getActivity());
            EmojiGridViewAdapter faceAdapter = new EmojiGridViewAdapter(getActivity(), itemDatas);

            gridview.setNumColumns(8);
            gridview.setHorizontalSpacing(1);
            gridview.setVerticalSpacing(1);
            gridview.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridview.setCacheColorHint(0);
            gridview.setPadding(2, 0, 2, 0);
            gridview.setBackgroundResource(android.R.color.transparent);
            gridview.setSelector(android.R.color.transparent);
            gridview.setVerticalScrollBarEnabled(false);
            gridview.setGravity(Gravity.CENTER);
            gridview.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT));
            gridview.setAdapter(faceAdapter);

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Log.e("POS",position+"`````"+itemDatas.size());
                    if (listener != null&&position<itemDatas.size()) {
                            Emoji emoji = itemDatas.get(position);
                            listener.selectedEmoji(emoji);

                    }else if(listener!=null&&position==23){
                        listener.selectedBackSpace();
                    }
                }
            });
            allPageViews[x] = gridview;

            RadioButton tip = new RadioButton(getActivity());
            tip.setEnabled(false);//设置小圆点不可点击  防止选中颜色切换
            tip.setBackgroundResource(R.drawable.selector_bg_tip);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                    15, 15);
            layoutParams.leftMargin = 10;
            pagePointLayout.addView(tip, layoutParams);
            if (x == 0) {
                tip.setChecked(true);
            }
            pointViews[x] = tip;
        }

        PagerAdapter facePagerAdapter = new EmojiVpAdapter(getActivity(),allPageViews);
        mPagerFace.setAdapter(facePagerAdapter);
        mPagerFace.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int index) {
                pointViews[index].setChecked(true);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
    public void setOnOperationListener(OnOperationListener onOperationListener) {
        this.listener = onOperationListener;
    }
}
