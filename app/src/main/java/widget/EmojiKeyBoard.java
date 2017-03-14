package widget;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.example.zhangyang.emojidemo.R;

import bean.Emoji;
import fragment.EmojiFragment;

/**
 * Created by zhangyang on 17/2/28.
 */

public class EmojiKeyBoard extends RelativeLayout implements
        SoftKeyboardStateHelper.SoftKeyboardStateListener{


    public static final int LAYOUT_TYPE_HIDE = 0;
    public static final int LAYOUT_TYPE_FACE = 1;
    public static final int LAYOUT_TYPE_MORE = 2;

    /**
     * 最上层输入框
     */
    private EditText mEtMsg;
    private CheckBox mBtnFace;
    private CheckBox mBtnMore;
    private Button mBtnSend;

    /**
     * 表情
     */
    private FrameLayout mRlFace;

    private int layoutType = LAYOUT_TYPE_HIDE;


    private Context context;
    private OnOperationListener listener;
    private SoftKeyboardStateHelper mKeyboardHelper;
    public EmojiKeyBoard(Context context) {
        super(context);
        init(context);
    }

    public EmojiKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmojiKeyBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View root = View.inflate(context, R.layout.emojikeyboard, null);
        this.addView(root);

    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();
        this.initWidget();
    }


    private void initData() {
        mKeyboardHelper = new SoftKeyboardStateHelper(((Activity) getContext())
                .getWindow().getDecorView());
        mKeyboardHelper.addSoftKeyboardStateListener(this);
    }
    private void initWidget() {
        mEtMsg = (EditText) findViewById(R.id.Edit_emoji);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnFace = (CheckBox) findViewById(R.id.btn_emoji);
        mBtnMore = (CheckBox) findViewById(R.id.btn_more);
        mRlFace = (FrameLayout) findViewById(R.id.layout_emoji);

//        mEtMsg.setText(new String(Character.toChars(0x1f600)));

        FragmentManager manager=((FragmentActivity)getContext()).getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        EmojiFragment emojiFragment=new EmojiFragment();
        transaction.add(R.id.layout_emoji, emojiFragment);
        transaction.commit();
        emojiFragment.setOnOperationListener(new OnOperationListener() {
            @Override
            public void send(String content) {

            }

            @Override
            public void selectedEmoji(Emoji emoji) {
                Log.e("aaaa",emoji.getEmoji()+"");
                if(listener!=null) {
                    listener.selectedEmoji(emoji);
                }
            }

            @Override
            public void selectedBackSpace() {
                if(listener!=null) {
                    listener.selectedBackSpace();
                }
            }
        });
        mBtnSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    String content = mEtMsg.getText().toString();
                    listener.send(content);
                    mEtMsg.setText(null);
                }
            }
        });
        // 点击表情按钮
        mBtnFace.setOnClickListener(getFunctionBtnListener(LAYOUT_TYPE_FACE));
//        // 点击表情按钮旁边的加号
//        mBtnMore.setOnClickListener(getFunctionBtnListener(LAYOUT_TYPE_MORE));
        // 点击消息输入框
        mEtMsg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideLayout();
            }
        });
    }
    private OnClickListener getFunctionBtnListener(final int which) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow() && which == layoutType) {
                    hideLayout();
                    showKeyboard(context);
                } else {
                    layoutType = which;
                    showLayout();
                    mBtnFace.setChecked(layoutType == LAYOUT_TYPE_FACE);
                    mBtnMore.setChecked(layoutType == LAYOUT_TYPE_MORE);
                }
            }
        };
    }


    public EditText getEditTextBox() {
        return mEtMsg;
    }
    public void showLayout() {
        hideKeyboard(this.context);
        // 延迟一会，让键盘先隐藏再显示表情键盘，否则会有一瞬间表情键盘和软键盘同时显示
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mRlFace.setVisibility(View.VISIBLE);

            }
        }, 100);
    }

    public boolean isShow() {
        return mRlFace.getVisibility() == VISIBLE;
    }

    public void hideLayout() {
        mRlFace.setVisibility(View.GONE);
        if (mBtnFace.isChecked()) {
            mBtnFace.setChecked(false);
        }
        if (mBtnMore.isChecked()) {
            mBtnMore.setChecked(false);
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard(Context context) {
        Activity activity = (Activity) context;
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive() && activity.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }
    }

    /**
     * 显示软键盘
     */
    public static void showKeyboard(Context context) {
        Activity activity = (Activity) context;
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(activity.getCurrentFocus()
                    .getWindowToken(), 0);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {

        hideLayout();
    }

    @Override
    public void onSoftKeyboardClosed() {

    }

    public OnOperationListener getOnOperationListener() {
        return listener;
    }

    public void setOnOperationListener(OnOperationListener onOperationListener) {
        this.listener = onOperationListener;
    }
}
