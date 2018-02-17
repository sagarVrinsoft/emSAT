package com.vrinsoft.emsat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.vrinsoft.emsat.R;



/*
* Font style 1-light, 2-medium, 3-regular, 4-semi bold, 5-bold
* */

public class CustomEditTextView extends AppCompatEditText {
    public CustomEditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomEditTextView(Context context) {
        super(context);
        init(null);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontStyle);
            int fontStyle = a.getInt(R.styleable.FontStyle_fontTextStyle, 0);
            Typeface myTypeface = null;
            switch (fontStyle) {
                case 1:
                    myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Gothici_regular.ttf");
                    break;
                case 2:
                    myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/gothici_bold_cursive.ttf");
                    break;
                case 3:
                    myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/gothici_bold.ttf");
                    break;
                case 5:
                    myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Blenda_Script.otf");
                    break;
            }
            setTypeface(myTypeface);
            a.recycle();
        }
    }
}
