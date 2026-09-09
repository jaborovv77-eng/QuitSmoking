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

        prefs = getSharedPreferences(
                "quit_smoking",
                MODE_PRIVATE
        );

        tajik = prefs.getBoolean(
                "tajik",
                false
        );

        if (!prefs.contains("start_time")) {
            prefs.edit()
                    .putLong(
                            "start_time",
                            System.currentTimeMillis()
                    )
                    .apply();
        }

        createScreen();
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

        view.setGravity(
                Gravity.CENTER
        );

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

        LinearLayout root =
                new LinearLayout(this);

        root.setOrientation(
                LinearLayout.VERTICAL
        );

        root.setPadding(
                24,
                20,
                24,
                25
        );

        root.setBackgroundColor(
                Color.rgb(248, 252, 248)
        );

        // LOGO

        TextView logo =
                createText(
                        "🚭",
                        48,
                        false
                );

        root.addView(logo);

        // TITLE

        TextView title =
                createText(
                        tajik
                                ? "ТАРКИ СИГОР"
                                : "QUIT SMOKING",
                        28,
                        true
                );

        title.setTextColor(
                Color.rgb(25, 140, 75)
        );

        root.addView(title);

        // CREATOR

        TextView creator =
                createText(
                        "JABORZODA",
                        12,
                        true
                );

        creator.setTextColor(
                Color.rgb(45, 143, 88)
        );

        root.addView(creator);

        // SUBTITLE

        TextView subtitle =
                createText(
                        tajik
                                ? "Роҳи ту ба ҳаёти бе сигор"
                                : "Твой путь к жизни без сигарет",
                        17,
                        true
                );

        root.addView(subtitle);

        // LUNGS

        ImageView lungs =
                new ImageView(this);

        lungs.setImageResource(
                R.drawable.quit_smoking_banner
        );

        lungs.setAdjustViewBounds(true);

        lungs.setScaleType(
                ImageView.ScaleType.CENTER_INSIDE
        );

        LinearLayout.LayoutParams lungsParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        240
                );

        lungsParams.setMargins(
                0,
                10,
                0,
                10
        );

        root.addView(
                lungs,
                lungsParams
        );

        // TIMER

        timerText =
                createText(
                        "",
                        21,
                        true
                );

        timerText.setTextColor(
                Color.rgb(25, 120, 65)
        );

        root.addView(timerText);

        // CIGARETTES

        cigarettesText =
                createText(
                        "",
                        18,
                        false
                );

        root.addView(
                cigarettesText
        );

        // MONEY

        moneyText =
                createText(
                        "",
                        18,
                        false
                );

        root.addView(
                moneyText
        );

        // LANGUAGE

        Button language =
                createButton(
                        tajik
                                ? "🇷🇺 Русский"
                                : "🇹🇯 Тоҷикӣ"
                );

        language.setOnClickListener(
                v -> {

                    tajik = !tajik;

                    prefs.edit()
                            .putBoolean(
                                    "tajik",
                                    tajik
                            )
                            .apply();

                    createScreen();
                }
        );

        root.addView(language);

        // CRAVING

        Button craving =
                createButton(
                        tajik
                                ? "🔥 Ман мехоҳам сигор кашам"
                                : "🔥 Мне хочется закурить"
                );

        craving.setOnClickListener(
                v -> showMotivation()
        );

        root.addView(craving);

        // HEALTH

        Button health =
                createButton(
                        tajik
                                ? "❤️ Саломатии ман"
                                : "❤️ Моё здоровье"
                );

        health.setOnClickListener(
                v -> showHealth()
        );

        root.addView(health);

        // ACHIEVEMENTS

        Button achievements =
                createButton(
                        tajik
                                ? "🏆 Дастовардҳо"
                                : "🏆 Достижения"
                );

        achievements.setOnClickListener(
                v -> showAchievements()
        );

        root.addView(
                achievements
        );

        // STATISTICS

        Button statistics =
                createButton(
                        tajik
                                ? "📊 Омор"
                                : "📊 Статистика"
                );

        statistics.setOnClickListener(
                v -> showStatistics()
        );

        root.addView(
                statistics
        );

        // RESET

        Button reset =
                createButton(
                        tajik
                                ? "🔄 Аз нав оғоз кардан"
                                : "🔄 Начать заново"
                );

        reset.setOnClickListener(
                v -> resetTimer()
        );

        root.addView(reset);

        // FOOTER

        TextView footer =
                createText(
                        tajik
                                ? "Ҳар рӯзи бе сигор — як пирӯзӣ. 💚"
                                : "Каждый день без сигарет — это победа. 💚",
                        14,
                        true
                );

        footer.setTextColor(
                Color.GRAY
        );

        root.addView(footer);

        setContentView(root);

        updateTimer();
    }

    private void updateTimer() {

        long startTime =
                prefs.getLong(
                        "start_time",
                        System.currentTimeMillis()
                );

        long difference =
                System.currentTimeMillis()
                        - startTime;

        long totalSeconds =
                difference / 1000;

        long days =
                totalSeconds / 86400;

        long hours =
                (totalSeconds % 86400)
                        / 3600;

        long minutes =
                (totalSeconds % 3600)
                        / 60;

        long seconds =
                totalSeconds % 60;

        if (timerText == null) {
            return;
        }

        if (tajik) {

            timerText.setText(
                    String.format(
                            Locale.getDefault(),
                            "⏱ Вақти бе сигор\n%d рӯз %02d:%02d:%02d",
                            days,
                            hours,
                            minutes,
                            seconds
                    )
            );

        } else {

            timerText.setText(
                    String.format(
                            Locale.getDefault(),
                            "⏱ Время без сигарет\n%d дней %02d:%02d:%02d",
                            days,
                            hours,
                            minutes,
                            seconds
                    )
            );
        }

        long cigarettesPerDay =
                prefs.getLong(
                        "cigarettes_per_day",
                        10
                );

        long cigarettesAvoided =
                (days * cigarettesPerDay);

        if (tajik) {

            cigarettesText.setText(
                    "🚬 Сигорҳои накашида: "
                            + cigarettesAvoided
            );

        } else {

            cigarettesText.setText(
                    "🚬 Не выкурено: "
                            + cigarettesAvoided
            );
        }

        double price =
                prefs.getFloat(
                        "price_per_cigarette",
                        50
                );

        double saved =
                cigarettesAvoided * price;

        if (tajik) {

            moneyText.setText(
                    String.format(
                            Locale.getDefault(),
                            "💰 Пули сарфашуда: %.0f ₸",
                            saved
                    )
            );

        } else {

            moneyText.setText(
                    String.format(
                            Locale.getDefault(),
                            "💰 Сэкономлено: %.0f ₸",
                            saved
                    )
            );
        }
    }

    private void showMotivation() {

        String title =
                tajik
                        ? "🔥 Ҳозир сигор накаш"
                        : "🔥 Не кури сейчас";

        String message =
                tajik
                        ? "Ту то ин ҷо расидӣ. Як сигор тамоми кӯшишҳоятро арзиш надорад.\n\n"
                        + "💧 Об бинӯш.\n"
                        + "🌬 Чуқур нафас гир.\n"
                        + "🚶 Каме роҳ рав.\n\n"
                        + "Ту қавитар аз ин хоҳиш ҳастӣ. 💪"
                        : "Ты уже дошёл до этого момента. Одна сигарета не стоит всех твоих усилий.\n\n"
                        + "💧 Выпей воды.\n"
                        + "🌬 Сделай глубокий вдох.\n"
                        + "🚶 Немного пройдись.\n\n"
                        + "Ты сильнее этого желания. 💪";

        new android.app.AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                        tajik
                                ? "Ман тоқат мекунам!"
                                : "Я справлюсь!",
                        null
                )
                .show();
    }

    private void showHealth() {

        String title =
                tajik
                        ? "❤️ Саломатии ман"
                        : "❤️ Моё здоровье";

        String message =
                tajik
                        ? "Ҳар рӯзи бе сигор ба баданат фоида меорад.\n\n"
                        + "🫁 Шушат имкони барқарор шудан мегирад.\n\n"
                        + "❤️ Дилу рагҳоят низ аз тарки сигор манфиат мегиранд.\n\n"
                        + "Ҳар рӯз идома деҳ! 💚"
                        : "Каждый день без сигарет приносит пользу организму.\n\n"
                        + "🫁 Лёгкие получают возможность восстанавливаться.\n\n"
                        + "❤️ Сердечно-сосудистая система тоже получает пользу.\n\n"
                        + "Продолжай каждый день! 💚";

        new android.app.AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                        tajik
                                ? "Хуб"
                                : "Хорошо",
                        null
                )
                .show();
    }

    private void showAchievements() {

        long startTime =
                prefs.getLong(
                        "start_time",
                        System.currentTimeMillis()
                );

        long days =
                (System.currentTimeMillis()
                        - startTime)
                        / 86400000;

        String result;

        if (days >= 90) {

            result =
                    tajik
                            ? "💎 90 рӯз!\n\nТу ба натиҷаи бузург расидӣ!"
                            : "💎 90 дней!\n\nТы достиг огромного результата!";

        } else if (days >= 30) {

            result =
                    tajik
                            ? "🏆 30 рӯз!\n\nЯк моҳ бе сигор!"
                            : "🏆 30 дней!\n\nМесяц без сигарет!";

        } else if (days >= 7) {

            result =
                    tajik
                            ? "🥇 7 рӯз!\n\nЯк ҳафта бе сигор!"
                            : "🥇 7 дней!\n\nЦелая неделя без сигарет!";

        } else if (days >= 1) {

            result =
                    tajik
                            ? "⭐ 1 рӯз!\n\nАввалин пирӯзӣ!"
                            : "⭐ 1 день!\n\nПервая победа!";

        } else {

            result =
                    tajik
                            ? "🚭 Оғози роҳ!\n\nИмрӯзро бе сигор гузарон."
                            : "🚭 Начало пути!\n\nПроживи сегодня без сигарет.";
        }

        new android.app.AlertDialog.Builder(this)
                .setTitle(
                        tajik
                                ? "🏆 Дастовардҳо"
                                : "🏆 Достижения"
                )
                .setMessage(result)
                .setPositiveButton(
                        tajik
                                ? "Хуб"
                                : "Хорошо",
                        null
                )
                .show();
    }

    private void showStatistics() {

        long startTime =
                prefs.getLong(
                        "start_time",
                        System.currentTimeMillis()
                );

        long days =
                (System.currentTimeMillis()
                        - startTime)
                        / 86400000;

        long cigarettes =
                days *
                        prefs.getLong(
                                "cigarettes_per_day",
                                10
                        );

        String message =
                tajik
                        ? "⏱ Рӯзҳои бе сигор: "
                        + days
                        + "\n🚬 Сигорҳои накашида: "
                        + cigarettes
                        + "\n\n💚 Идома деҳ!"
                        : "⏱ Дней без сигарет: "
                        + days
                        + "\n🚬 Не выкурено: "
                        + cigarettes
                        + "\n\n💚 Продолжай!";

        new android.app.AlertDialog.Builder(this)
                .setTitle(
                        tajik
                                ? "📊 Омор"
                                : "📊 Статистика"
                )
                .setMessage(message)
                .setPositiveButton(
                        "OK",
                        null
                )
                .show();
    }

    private void resetTimer() {

        new android.app.AlertDialog.Builder(this)
                .setTitle(
                        tajik
                                ? "🔄 Аз нав оғоз кардан?"
                                : "🔄 Начать заново?"
                )
                .setMessage(
                        tajik
                                ? "Ин мағлубият нест. Боз кӯшиш кун! 💪"
                                : "Это не поражение. Попробуй снова! 💪"
                )
                .setNegativeButton(
                        tajik
                                ? "Не"
                                : "Нет",
                        null
                )
                .setPositiveButton(
                        tajik
                                ? "Аз нав"
                                : "Начать заново",
                        (dialog, which) -> {

                            prefs.edit()
                                    .putLong(
                                            "start_time",
                                            System.currentTimeMillis()
                                    )
                                    .apply();

                            updateTimer();
                        }
                )
                .show();
    }

    @Override
    protected void onResume() {

        super.onResume();

        handler.post(
                timerRunnable
        );
    }

    @Override
    protected void onPause() {

        super.onPause();

        handler.removeCallbacks(
                timerRunnable
        );
    }
}
