package com.vrinsoft.emsat.utils.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vrinsoft.emsat.R;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by dorji on 20-Oct-17.
 */

public class CircleProgressView extends FrameLayout {
    private ProgressBar progressBarView;
    private TextView progressTextView;
    private Context mContext;

    public CircleProgressView(Context context) {
        super(context);
        init(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context ctx) {
        mContext = ctx;
        View rootView = inflate(mContext, R.layout.layout_progress_view, this);
        progressBarView = (ProgressBar) rootView.findViewById(R.id.view_progress_bar);
        progressTextView = (TextView) rootView.findViewById(R.id.view_progress_text);
//        RotateAnimation makeVertical = new RotateAnimation(270, 270, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
//        makeVertical.setFillAfter(true);
//        progressBarView.startAnimation(makeVertical);
    }

    public void setProgress(int obtained, int total) {
        progressBarView.setMax(total);
        progressBarView.setSecondaryProgress(total);
        progressBarView.setProgress(obtained);
        progressTextView.setText(obtained + "/" + total);
    }
}
