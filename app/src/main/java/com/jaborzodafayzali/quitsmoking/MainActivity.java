package com.jaborzodafayzali.quitsmoking;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity {

    private SharedPreferences prefs;

    private TextView timer;
    private TextView cigarettes;
    private TextView money;

    private long startTime;

    private final Handler handler = new Handler();

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            updateScreen();
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences(
                "quit_smoking",
                MODE_PRIVATE
        );

        startTime = prefs.getLong(
                "start_time",
                0
        );

        if (startTime == 0) {
            startTime = System.currentTimeMillis();

            prefs.edit()
                    .putLong("start_time", startTime)
                    .apply();
        }

        createScreen();
        updateScreen();
    }

    private TextView createText(
            String text,
            int size,
            boolean bold
    ) {

        TextView view = new TextView(this);

        view.setText(text);
        view.setTextSize(size);
        view.setTextColor(
                Color.rgb(30, 30, 30)
        );

        if (bold) {
            view.setTypeface(
                    Typeface.DEFAULT,
                    Typeface.BOLD
            );
        }

        view.setPadding(
                10,
                10,
                10,
                10
        );

        return view;
    }

    private Button createButton(String text) {

        Button button = new Button(this);

        button.setText(text);
        button.setTextSize(16);
        button.setAllCaps(false);

        return button;
    }

    private void createScreen() {

        ScrollView scrollView =
                new ScrollView(this);

        LinearLayout main =
                new LinearLayout(this);

        main.setOrientation(
                LinearLayout.VERTICAL
        );

        main.setPadding(
                24,
                25,
                24,
                30
        );

        main.setBackgroundColor(
                Color.rgb(248, 252, 248)
        );

        scrollView.addView(main);

        // LOGO

        TextView logo = createText(
                "🚭",
                45,
                false
        );

        logo.setGravity(
                Gravity.CENTER
        );

        main.addView(logo);

        // TITLE

        TextView title = createText(
                "QUIT SMOKING",
                28,
                true
        );

        title.setTextColor(
                Color.rgb(25, 140, 75)
        );

        title.setGravity(
                Gravity.CENTER
        );

        main.addView(title);

        // LUNGS BANNER

        ImageView lungs =
                new ImageView(this);

        lungs.setImageResource(
                com.jaborzodafayzali.quitsmoking.R.drawable.quit_smoking_banner
        );

        lungs.setAdjustViewBounds(true);

        LinearLayout.LayoutParams lungsParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        240
                );

        lungsParams.setMargins(
                0,
                15,
                0,
                10
        );

        main.addView(
                lungs,
                lungsParams
        );

        // CREATOR

        TextView creator = createText(
                "JABORZODA",
                12,
                true
        );

        creator.setTextColor(
                Color.rgb(45, 143, 88)
        );

        creator.setGravity(
                Gravity.CENTER
        );

        main.addView(creator);

        main.addView(createText(
                "\nТвой путь к жизни без сигарет",
                20,
                true
        ));

        // TIMER

        timer = createText(
                "",
                22,
                true
        );

        timer.setGravity(
                Gravity.CENTER
        );

        main.addView(timer);

        // CIGARETTES

        cigarettes = createText(
                "",
                18,
                false
        );

        cigarettes.setGravity(
                Gravity.CENTER
        );

        main.addView(cigarettes);

        // MONEY

        money = createText(
                "",
                18,
                false
        );

        money.setGravity(
                Gravity.CENTER
        );

        main.addView(money);

        // MOTIVATION

        Button motivation = createButton(
                "🔥 Мне хочется закурить"
        );

        motivation.setOnClickListener(
                v -> showMotivation()
        );

        main.addView(motivation);

        // HEALTH

        Button health = createButton(
                "❤️ Моё здоровье"
        );

        health.setOnClickListener(
                v -> showHealth()
        );

        main.addView(health);

        // ACHIEVEMENTS

        Button achievements = createButton(
                "🏆 Достижения"
        );

        achievements.setOnClickListener(
                v -> showAchievements()
        );

        main.addView(achievements);

        // STATISTICS

        Button statistics = createButton(
                "📊 Статистика"
        );

        statistics.setOnClickListener(
                v -> showStatistics()
        );

        main.addView(statistics);

        // RESET

        Button reset = createButton(
                "🔄 Я сорвался"
        );

        reset.setOnClickListener(
                v -> resetTimer()
        );

        main.addView(reset);

        // FOOTER

        TextView footer = createText(
                "\nКаждый день без сигарет — это победа. 💚\n\n" +
                "JABORZODA FAYZALI",
                15,
                true
        );

        footer.setGravity(
                Gravity.CENTER
        );

        main.addView(footer);

        setContentView(scrollView);
    }

    private void updateScreen() {

        long difference =
                System.currentTimeMillis()
                        - startTime;

        long totalSeconds =
                difference / 1000;

        long days =
                totalSeconds / 86400;

        long hours =
                (totalSeconds % 86400) / 3600;

        long minutes =
                (totalSeconds % 3600) / 60;

        long seconds =
                totalSeconds % 60;

        timer.setText(
                "⏱ " +
                days + " дн. " +
                hours + " ч. " +
                minutes + " мин. " +
                seconds + " сек."
        );

        int cigarettesPerDay =
                prefs.getInt(
                        "cigarettes_per_day",
                        10
                );

        float pricePerCigarette =
                prefs.getFloat(
                        "price_per_cigarette",
                        50
                );

        long cigarettesAvoided =
                days * cigarettesPerDay;

        float saved =
                cigarettesAvoided
                        * pricePerCigarette;

        cigarettes.setText(
                "🚬 Не выкурено: " +
                cigarettesAvoided +
                " сигарет"
        );

        money.setText(
                String.format(
                        Locale.getDefault(),
                        "💰 Сэкономлено: %.0f ₸",
                        saved
                )
        );
    }

    private void showMotivation() {

        new android.app.AlertDialog.Builder(this)
                .setTitle(
                        "🔥 Не закуривай сейчас"
                )
                .setMessage(
                        "Подожди всего несколько минут.\n\n" +
                        "💧 Выпей воды.\n" +
                        "🌬 Сделай несколько глубоких вдохов.\n" +
                        "🚶 Пройдись.\n" +
                        "📱 Займись чем-нибудь другим.\n\n" +
                        "Ты уже начал свой путь. " +
                        "Не отдавай его одной сигарете. 💪"
                )
                .setPositiveButton(
                        "Я справлюсь!",
                        null
                )
                .show();
    }

    private void showHealth() {

        new android.app.AlertDialog.Builder(this)
                .setTitle(
                        "❤️ Восстановление"
                )
                .setMessage(
                        "После отказа от курения организм " +
                        "постепенно восстанавливается.\n\n" +
                        "🫁 Лёгкие получают возможность " +
                        "очищаться.\n\n" +
                        "❤️ Сердечно-сосудистая система " +
                        "постепенно получает преимущества.\n\n" +
                        "🌿 Чем дольше ты не куришь, " +
                        "тем больше пользы для здоровья."
                )
                .setPositiveButton(
                        "Продолжить",
                        null
                )
                .show();
    }

    private void showAchievements() {

        long days =
                (System.currentTimeMillis()
                        - startTime)
                        / 86400000;

        String result;

        if (days >= 90) {

            result =
                    "💎 90 дней!\n\n" +
                    "Ты достиг огромного результата.";

        } else if (days >= 30) {

            result =
                    "🏆 30 дней!\n\n" +
                    "Месяц без сигарет!";

        } else if (days >= 14) {

            result =
                    "🥇 14 дней!\n\n" +
                    "Две недели — отлично!";

        } else if (days >= 7) {

            result =
                    "🥇 7 дней!\n\n" +
                    "Целая неделя!";

        } else if (days >= 3) {

            result =
                    "🥉 3 дня!\n\n" +
                    "Ты уже сделал серьёзный шаг.";

        } else if (days >= 1) {

            result =
                    "⭐ 1 день!\n\n" +
                    "Первая победа!";

        } else {

            result =
                    "🚭 Начало пути!\n\n" +
                    "Твоя первая цель — прожить " +
                    "сегодня без сигарет.";
        }

        new android.app.AlertDialog.Builder(this)
                .setTitle(
                        "🏆 Достижения"
                )
                .setMessage(result)
                .setPositiveButton(
                        "Продолжить",
                        null
                )
                .show();
    }

    private void showStatistics() {

        long days =
                (System.currentTimeMillis()
                        - startTime)
                        / 86400000;

        new android.app.AlertDialog.Builder(this)
                .setTitle(
                        "📊 Статистика"
                )
                .setMessage(
                        "🚭 Дней без сигарет: " +
                        days +
                        "\n\n" +
                        "🔥 Продолжай двигаться вперёд!\n\n" +
                        "Создатель:\n" +
                        "JABORZODA FAYZALI"
                )
                .setPositiveButton(
                        "OK",
                        null
                )
                .show();
    }

    private void resetTimer() {

        new android.app.AlertDialog.Builder(this)
                .setTitle(
                        "🔄 Срыв"
                )
                .setMessage(
                        "Срыв — это не конец пути.\n\n" +
                        "Начать счётчик заново?"
                )
                .setNegativeButton(
                        "Нет",
                        null
                )
                .setPositiveButton(
                        "Начать заново",
                        (dialog, which) -> {

                            startTime =
                                    System.currentTimeMillis();

                            prefs.edit()
                                    .putLong(
                                            "start_time",
                                            startTime
                                    )
                                    .apply();

                            updateScreen();
                        }
                )
                .show();
    }

    @Override
    protected void onResume() {

        super.onResume();

        handler.post(timerRunnable);
    }

    @Override
    protected void onPause() {

        super.onPause();

        handler.removeCallbacks(
                timerRunnable
        );
    }
}
