package com.kzb.parents.view.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.util.DensityUtil;

/********************
 * 作者：malus
 * 日期：16/11/30
 * 时间：下午9:48
 * 注释：标题栏
 ********************/
public class Topbar extends RelativeLayout {
    // 分别定义左边按钮，右边按钮，中间标题及他们所需要的属性
    private Button leftButton;
    private Button rightButton;
    private int rightTextSize;
    private int leftTextSize;
    private int rightTextColor;
    private int leftTextColor;
    private String rightTextTitle;
    private String leftTextTitle;

    private TextView textView;

    private Drawable leftButtonSrc;
    private int leftButtonWidth;
    private Drawable rightButtonSrc;
    private int rightButtonWidth;
    private Drawable searchButtonSrc;
    private String text;
    private int textSize;
    private int textColor;
    private int textBackground;
    private LayoutParams leftParams, rightParams, textParams,iconParams;
    // 设置回调机制用来响应左右按钮点击事件
    onTopbarClickListener listener;

    private ImageView rightIcon;

    private boolean showIcon;
    /**
     * 构造函数，获取并设置参数。添加组件
     *
     * @param context
     * @param attrs
     */
    public Topbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取所有属性
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.Topbar);

        leftButtonSrc = ta.getDrawable(R.styleable.Topbar_leftButton_src);
        leftButtonWidth = (int) ta.getDimension(
                R.styleable.Topbar_leftButton_width, 0);
        leftTextTitle = ta.getString(R.styleable.Topbar_leftButton_text);
        rightTextTitle = ta.getString(R.styleable.Topbar_rightButton_text);
        leftTextColor = ta.getInt(R.styleable.Topbar_leftText_color, 0);
        rightTextColor = ta.getInt(R.styleable.Topbar_rightText_color, 0);

        leftTextSize = (int) ta.getDimension(R.styleable.Topbar_leftText_size,
                10);
        rightTextSize = (int) ta.getDimension(
                R.styleable.Topbar_rightText_size, 10);
        leftTextSize = px2dip(context, leftTextSize);
        rightTextSize = px2dip(context, rightTextSize);

        rightButtonSrc = ta.getDrawable(R.styleable.Topbar_rightButton_src);
        rightButtonWidth = (int) ta.getDimension(
                R.styleable.Topbar_rightButton_width, 0);

        searchButtonSrc = ta.getDrawable(R.styleable.Topbar_searchButton_src);

        showIcon = ta.getBoolean(R.styleable.Topbar_showIcon,false);

        text = ta.getString(R.styleable.Topbar_text);
        textSize = (int) ta.getDimension(R.styleable.Topbar_text_size, 12);
        textSize = px2dip(context, textSize);
        textColor = ta.getColor(R.styleable.Topbar_text_color, 0);
        textBackground = ta.getColor(R.styleable.Topbar_text_background, 0);

        // 关闭获取属性流
        ta.recycle();
        // 创建组件
        leftButton = new Button(context);
        // 设置组件自定义属性
        leftButton.setBackground(leftButtonSrc);
        leftButton.setPadding(0,0,0,5);
        leftButton.setTextColor(leftTextColor);
        leftButton.setTextSize(leftTextSize);
        leftButton.setGravity(Gravity.CENTER);
        leftButton.setText(leftTextTitle);
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLeftButtonClick();
                }
            }
        });

        if (leftButtonWidth == 0) {
            leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT);
        } else {
            leftParams = new LayoutParams(leftButtonWidth,
                    leftButtonWidth/2);
//            leftParams = new RelativeLayout.LayoutParams(leftButtonWidth,
//                    LayoutParams.MATCH_PARENT);
        }
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        leftParams.setMargins(0, DensityUtil.dip2px(context,10), 0, DensityUtil.dip2px(context,10));
        leftButton.setLayoutParams(leftParams);

        rightButton = new Button(context);
//        rightButton.setId(R.id.topbar_left_btn);

        rightButton.setTextColor(rightTextColor);
        rightButton.setTextSize(rightTextSize);
        rightButton.setBackground(rightButtonSrc);
        rightButton.setText(rightTextTitle);
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onRightButtonClick();
                }
            }
        });
        if (rightButtonWidth == 0) {
            rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT);
        } else {
            rightParams = new LayoutParams(rightButtonWidth,
                    LayoutParams.MATCH_PARENT);
        }
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);

        rightParams.setMargins(0, DensityUtil.dip2px(context,10), 0, DensityUtil.dip2px(context,10));
        rightButton.setLayoutParams(rightParams);

        textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        textView.setBackgroundColor(textBackground);

        // 设置组件位置及大小
        textView.setGravity(Gravity.CENTER);
        textParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        textParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);


        rightIcon = new ImageView(context);
//        rightIcon.setImageResource(R.mipmap.naozhong_icon);
        iconParams = new LayoutParams(DensityUtil.dip2px(context,25),
                DensityUtil.dip2px(context,25));
        iconParams.addRule(RelativeLayout.LEFT_OF, rightButton.getId());
        iconParams.addRule(RelativeLayout.CENTER_VERTICAL,TRUE);
        rightIcon.setLayoutParams(iconParams);
        if(showIcon){
            rightIcon.setVisibility(VISIBLE);
        }else{
            rightIcon.setVisibility(GONE);
        }

        addView(leftButton,leftParams);
        addView(rightButton,rightParams);
        addView(textView, textParams);
        addView(rightIcon,iconParams);
    }

    // 设置左右点击事件
    public void setOnTopbarClickListener(onTopbarClickListener listener) {
        this.listener = listener;
    }

    // 左右点击事件回调接口
    public interface onTopbarClickListener {
        public void onLeftButtonClick();

        public void onRightButtonClick();

    }

    /**
     * 关闭时间计算
     */
    public void hideClock(){
        if (rightIcon != null) {
            rightIcon.setVisibility(View.INVISIBLE);
        }
        if (rightButton != null) {
            rightButton.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置topbar的标题
     *
     * @param title
     */
    public void setTitle(String title) {
        textView.setText(title);
    }

    public Button getLeftButton(){
        return leftButton;
    }

    public Button getRightButton(){
        return rightButton;
    }

    /**
     * 设置topbar的背景颜色该背景<br/>
     * 该背景仅限于topbar中标题的背景
     *
     * @param resid
     *            资源
     */
    public void setTopbarBackground(int resid) {
        textView.setBackgroundResource(resid);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
