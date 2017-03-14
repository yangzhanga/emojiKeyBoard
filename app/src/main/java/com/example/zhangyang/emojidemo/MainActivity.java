package com.example.zhangyang.emojidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import bean.Emoji;
import widget.EmojiKeyBoard;
import widget.OnOperationListener;

public class MainActivity extends AppCompatActivity {
    private EmojiKeyBoard mKeyBoard;
    private ListView mRealListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mKeyBoard = (EmojiKeyBoard) findViewById(R.id.input_layout);
        mRealListView = (ListView) findViewById(R.id.listview);

        mRealListView.setSelector(android.R.color.transparent);
        initMessageInputToolBox();
    }

    private void initMessageInputToolBox() {

        mKeyBoard.setOnOperationListener(new OnOperationListener() {
            @Override
            public void send(String content) {
                Log.e("send",content +"");
            }

            @Override
            public void selectedEmoji(Emoji emoji) {
                Log.e("emoji",emoji.getEmoji()+"-----" );
                mKeyBoard.getEditTextBox().append(emoji.getEmoji());
            }

            @Override
            public void selectedBackSpace() {

                Emoji.backspace(mKeyBoard.getEditTextBox());
            }
        });

        mRealListView.setOnTouchListener(getOnTouchListener());
    }


    /**
     * 若软键盘或表情键盘弹起，点击上端空白处应该隐藏输入法键盘
     *
     * @return 会隐藏输入法键盘的触摸事件监听器
     */
    private View.OnTouchListener getOnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mKeyBoard.hideLayout();
                mKeyBoard.hideKeyboard(MainActivity.this);
                return false;
            }
        };
    }
}
