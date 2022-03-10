package com.test.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.RequiresApi;

@SuppressLint("AppCompatCustomView")
public class FocusButton extends Button {
    public FocusButton(Context context) {
        super(context);
        init();
    }

    public FocusButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FocusButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FocusButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setTextColor(focused ? Color.RED : Color.WHITE);
    }

    private void init() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        setTextColor(Color.WHITE);
    }
}
