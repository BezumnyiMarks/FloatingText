package com.example.floatingtext;

import static com.example.floatingtext.R.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.graphics.Interpolator;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int displayHeight = 0;
    private TextView textView;
    AnimatorSet animSet;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(id.textView);
        ConstraintLayout container = findViewById(id.container);

        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayHeight = size.y;

        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if (animSet != null)
                        animSet.cancel();

                    Log.d("htrdthrdhjdrthj", Locale.getDefault().getCountry());
                    switch (Locale.getDefault().getCountry()){
                        case "RU" -> textView.setTextColor(getResources().getColor(color.blue));
                        case "EN" -> textView.setTextColor(getResources().getColor(color.red));
                        case "US" -> textView.setTextColor(getResources().getColor(color.red));
                        case "UK" -> textView.setTextColor(getResources().getColor(color.red));
                    }

                    float x = event.getX();
                    float y = event.getY();
                    textView.setX(x);
                    textView.setY(y);

                    float topTranslationPoint = -(displayHeight / 2f - textView.getHeight() / 2f);
                    float bottomTranslationPoint = (displayHeight / 2f - textView.getHeight() / 2f);

                    PropertyValuesHolder translateDown = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, bottomTranslationPoint);
                    PropertyValuesHolder translateRepeatable = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, bottomTranslationPoint, topTranslationPoint);

                    @SuppressLint("Recycle") ObjectAnimator animTranslateDown = ObjectAnimator.ofPropertyValuesHolder(textView, translateDown)
                            .setDuration(12000);
                    animTranslateDown.setInterpolator(new LinearInterpolator());

                    @SuppressLint("Recycle") ObjectAnimator animTranslateRepeatable = ObjectAnimator.ofPropertyValuesHolder(textView, translateRepeatable)
                            .setDuration(12000);
                    animTranslateRepeatable.setRepeatCount(ObjectAnimator.INFINITE);
                    animTranslateRepeatable.setRepeatMode(ObjectAnimator.REVERSE);

                    animSet = new AnimatorSet();
                    animSet.setStartDelay(5000);
                    animSet.play(animTranslateRepeatable).after(animTranslateDown);
                    animSet.start();

                    return true;
                }
                return true;
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animSet != null)
                    animSet.cancel();
            }
        });
    }
}