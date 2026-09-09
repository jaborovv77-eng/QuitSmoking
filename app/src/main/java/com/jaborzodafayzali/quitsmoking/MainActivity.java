package com.jaborzodafayzali.quitsmoking;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.graphics.drawable.GradientDrawable;

public class MainActivity extends Activity {

    int green = Color.rgb(46, 160, 92);
    int dark = Color.rgb(30, 45, 38);
    int light = Color.rgb(246, 250, 247);
    int white = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setPadding(24, 30, 24, 20);
        main.setBackgroundColor(light);

        // HEADER
        TextView brand = new TextView(this);
        brand.setText("JABORZODA");
        brand.setTextColor(green);
        brand.setTextSize(13);
        brand.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        brand.setGravity(Gravity.CENTER);

        main.addView(brand, new LinearLayout.LayoutParams(
                -1, 35
        ));

        TextView title = new TextView(this);
        title.setText("QUIT SMOKING");
        title.setTextColor(dark);
        title.setTextSize(29);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);

        main.addView(title, new LinearLayout.LayoutParams(
                -1, 55
        ));

        TextView subtitle = new TextView(this);
        subtitle.setText("Твой путь к жизни без сигарет");
        subtitle.setTextColor(Color.GRAY);
        subtitle.setTextSize(15);
        subtitle.setGravity(Gravity.CENTER);

        main.addView(subtitle, new LinearLayout.LayoutParams(
                -1, 45
        ));

        // LUNGS
        ImageView lungs = new ImageView(this);
        lungs.setImageResource(R.drawable.quit_smoking_banner);
        lungs.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        LinearLayout.LayoutParams lungsParams =
                new LinearLayout.LayoutParams(-1, 210);
        lungsParams.setMargins(0, 10, 0, 10);

        main.addView(lungs, lungsParams);

        // TIMER CARD
        LinearLayout timerCard = createCard();

        TextView timerIcon = makeText("⏱️", 28, dark);
        timerIcon.setGravity(Gravity.CENTER);

        TextView timerTitle = makeText("Без сигарет", 15, Color.GRAY);
        timerTitle.setGravity(Gravity.CENTER);

        TextView timer = makeText("0 дней", 30, dark);
        timer.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        timer.setGravity(Gravity.CENTER);

        timerCard.addView(timerIcon);
        timerCard.addView(timerTitle);
        timerCard.addView(timer);

        main.addView(timerCard, new LinearLayout.Layout
