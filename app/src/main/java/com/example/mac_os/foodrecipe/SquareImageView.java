package com.example.mac_os.foodrecipe;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


public class SquareImageView extends android.support.v7.widget.AppCompatImageView {
    public SquareImageView(Context context) {
        super(context);

    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //setOnTouchListener(listener_onTouch);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
        setOnTouchListener(listener_onTouch);
    }



    OnTouchListener listener_onTouch = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ImageView image = (ImageView) v;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    image.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                    image.invalidate();

                    break;

                case MotionEvent.ACTION_MOVE:
                    image.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                    image.invalidate();


                    break;

                case MotionEvent.ACTION_UP:
                    image.getDrawable().clearColorFilter();
                    image.invalidate();


                    break;

                default:
                    image.getDrawable().clearColorFilter();
                    image.invalidate();

                    break;
            }

            return true;
        }
    };

}

