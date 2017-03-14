package adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhangyang.emojidemo.R;
import java.util.List;

import bean.Emoji;

/**
 * Created by zhangyang on 17/2/28.
 */

public class EmojiGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Emoji> mEmojis;
    public EmojiGridViewAdapter(Context mContext, List<Emoji> mEmojis) {
        this.mContext = mContext;
        this.mEmojis = mEmojis;
    }

    @Override
    public int getCount() {
        return mEmojis != null ? 24 : 0;
    }

    @Override
    public Object getItem(int position) {
        return mEmojis.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_emoji, null);
            holder.emojiTv = (TextView) convertView.findViewById(R.id.emojicon_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position<mEmojis.size()){
            Emoji emoji = (Emoji) getItem(position);
            holder.emojiTv.setText(emoji.getEmoji());
        }else if (position ==23) {
            //第24个显示删除按钮
            holder.emojiTv.setText("x");
//            holder.emojiTv.setBackgroundResource(R.drawable.delete);
//            holder.emojiTv.setBackgroundColor(Color.parseColor("#e0000000"));
        }
        return convertView;
    }

    private static class ViewHolder {
        private TextView emojiTv;
    }


}
