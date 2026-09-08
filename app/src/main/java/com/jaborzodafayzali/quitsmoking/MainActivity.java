package com.jaborzodafayzali.quitsmoking;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity {

    private SharedPreferences prefs;
    private Handler handler = new Handler();
    private TextView timerText;
    private TextView cigarettesText;
    private TextView moneyText;
    private boolean tajik = false;

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            updateTimer();
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("quit_smoking", MODE_PRIVATE);

        tajik = prefs.getBoolean("tajik", false);

        if (!prefs.contains("start_time")) {
            prefs.edit().putLong("start_time", System.currentTimeMillis()).apply();
        }

        createScreen();
        handler.post(timerRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(timerRunnable);
    }

    private void createScreen() {

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(30, 25, 30, 25);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(Color.WHITE);

        TextView logo = new TextView(this);
        logo.setText("🚭");
        logo.setTextSize(55);
        logo.setGravity(Gravity.CENTER);

        root.addView(logo);

        TextView title = new TextView(this);
        title.setText(tajik ? "ТАРКИ СИГОР" : "QUIT SMOKING");
        title.setTextSize(28);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.rgb(35, 125, 75));
        title.setGravity(Gravity.CENTER);

        root.addView(title);

        TextView creator = new TextView(this);
        creator.setText("JABORZODA");
        creator.setTextSize(12);
        creator.setTypeface(null, Typeface.BOLD);
        creator.setTextColor(Color.rgb(45, 143, 88));
        creator.setGravity(Gravity.CENTER);

        root.addView(creator);

        TextView subtitle = new TextView(this);
        subtitle.setText(
                tajik
                        ? "Роҳи ту ба сӯи ҳаёти бе сигор"
                        : "Твой путь к жизни без сигарет"
        );
        subtitle.setTextSize(16);
        subtitle.setTextColor(Color.DKGRAY);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 10, 0, 15);

        root.addView(subtitle);

        ImageView lungs = new ImageView(this);
        lungs.setImageResource(
                com.jaborzodafayzali.quitsmoking.R.drawable.quit_smoking_banner
        );
        lungs.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        LinearLayout.LayoutParams imageParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        240
                );

        root.addView(lungs, imageParams);

        timerText = new TextView(this);
        timerText.setTextSize(22);
        timerText.setTypeface(null, Typeface.BOLD);
        timerText.setTextColor(Color.rgb(30, 100, 60));
        timerText.setGravity(Gravity.CENTER);
        timerText.setPadding(0, 15, 0, 15);

        root.addView(timerText);

        cigarettesText = new TextView(this);
        cigarettesText.setTextSize(18);
        cigarettesText.setGravity(Gravity.CENTER);
        cigarettesText.setPadding(0, 8, 0, 8);

        root.addView(cigarettesText);

        moneyText = new TextView(this);
        moneyText.setTextSize(18);
        moneyText.setGravity(Gravity.CENTER);
        moneyText.setPadding(0, 8, 0, 15);

        root.addView(moneyText);

        Button languageButton = new Button(this);
        languageButton.setText(
                tajik ? "🇷🇺 Русский" : "🇹🇯 Тоҷикӣ"
        );

        languageButton.setOnClickListener(v -> {
            tajik = !tajik;
            prefs.edit().putBoolean("tajik", tajik).apply();
            createScreen();
        });

        root.addView(languageButton);

        Button cravingButton = new Button(this);
        cravingButton.setText(
                tajik
                        ? "🔥 Ман мехоҳам сигор кашам"
                        : "🔥 Мне хочется закурить"
        );

        cravingButton.setOnClickListener(v -> showMotivation());

        root.addView(cravingButton);

        Button healthButton = new Button(this);
        healthButton.setText(
                tajik
                        ? "❤️ Саломатии ман"
                        : "❤️ Моё здоровье"
        );

        healthButton.setOnClickListener(v -> showHealth());

        root.addView(healthButton);

        Button achievementsButton = new Button(this);
        achievementsButton.setText(
                tajik
                        ? "🏆 Дастовардҳо"
                        : "🏆 Достижения"
        );

        achievementsButton.setOnClickListener(v -> showAchievements());

        root.addView(achievementsButton);

        Button statisticsButton = new Button(this);
        statisticsButton.setText(
                tajik
                        ? "📊 Омор"
                        : "📊 Статистика"
        );

        statisticsButton.setOnClickListener(v -> showStatistics());

        root.addView(statisticsButton);

        Button resetButton = new Button(this);
        resetButton.setText(
                tajik
                        ? "🔄 Ман дубора сигор кашидам"
                        : "🔄 Я сорвался"
        );

        resetButton.setOnClickListener(v -> resetTimer());

        root.addView(resetButton);

        TextView footer = new TextView(this);
        footer.setText(
                tajik
                        ? "Ҳар рӯзи бе сигор — як пирӯзӣ. 💚"
                        : "Каждый день без сигарет — это победа. 💚"
        );
        footer.setTextSize(14);
        footer.setTextColor(Color.GRAY);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(0, 20, 0, 10);

        root.addView(footer);

        setContentView(root);
        updateTimer();
    }

    private void updateTimer() {

        long startTime = prefs.getLong(
                "start_time",
                System.currentTimeMillis()
        );

        long difference =
                System.currentTimeMillis() - startTime;

        long seconds = difference / 1000;

        long days = seconds / 86400;
        seconds %= 86400;

        long hours = seconds / 3600;
        seconds %= 3600;

        long minutes = seconds / 60;
        seconds %= 60;

        String time;

        if (tajik) {
            time = String.format(
                    Locale.getDefault(),
                    "⏱ Вақти бе сигор:\n%d рӯз %02d:%02d:%02d",
                    days,
                    hours,
                    minutes,
                    seconds
            );
        } else {
            time = String.format(
                    Locale.getDefault(),
                    "⏱ Время без сигарет:\n%d дней %02d:%02d:%02d",
                    days,
                    hours,
                    minutes,
                    seconds
            );
        }

        if (timerText != null) {
            timerText.setText(time);
        }

        long cigarettesPerDay = 10;

        long totalMinutes =
                difference / 60000;

        long cigarettesAvoided =
                (totalMinutes * cigarettesPerDay) / 1440;

        if (cigarettesText != null) {

            cigarettesText.setText(
                    tajik
                            ? "🚬 Сигорҳои накашида: "
                            + cigarettesAvoided
                            : "🚬 Сигарет не выкурено: "
                            + cigarettesAvoided
            );
        }

        double pricePerCigarette = 50;

        double saved =
                cigarettesAvoided * pricePerCigarette;

        if (moneyText != null) {

            moneyText.setText(
                    tajik
                            ? String.format(
                            Locale.getDefault(),
                            "💰 Пули сарфашуда: %.0f ₸",
                            saved
                    )
                            : String.format(
                            Locale.getDefault(),
                            "💰 Сэкономлено: %.0f ₸",
                            saved
                    )
            );
        }
    }

    private void showMotivation() {

        String title = tajik
                ? "🔥 Ҳозир сигор накаш"
                : "🔥 Не кури сейчас";

        String message = tajik
                ? "Ту то имрӯз тоқат кардӣ. Як сигор ҳамаи заҳмататро арзишманд намекунад. Якчанд дақиқа сабр кун — хоҳиш мегузарад. 💪"
                : "Ты уже столько продержался.
