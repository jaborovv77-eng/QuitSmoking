package com.jaborzodafayzali.quitsmoking;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity {

    private SharedPreferences prefs;

    private TextView timerText;
    private TextView cigarettesText;
    private TextView moneyText;

    private long startTime;
    private int cigarettesPerDay;
    private float pricePerCigarette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("quit_smoking", MODE_PRIVATE);

        startTime = prefs.getLong("start_time", 0);

        cigarettesPerDay = prefs.getInt("cigarettes_per_day", 10);
        pricePerCigarette = prefs.getFloat("price_per_cigarette", 50f);

        if (startTime == 0) {
            startTime = System.currentTimeMillis();

            prefs.edit()
                    .putLong("start_time", startTime)
                    .apply();
        }

        createInterface();
    }

    private TextView text(String value, int size) {

        TextView t = new TextView(this);

        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(Color.rgb(25, 25, 25));
        t.setPadding(20, 15, 20, 15);

        return t;
    }

    private Button button(String value) {

        Button b = new Button(this);

        b.setText(value);
        b.setTextSize(16);

        return b;
    }

    private void createInterface() {

        ScrollView scroll = new ScrollView(this);

        LinearLayout main = new LinearLayout(this);

        main.setOrientation(LinearLayout.VERTICAL);
        main.setPadding(25, 25, 25, 30);

        scroll.addView(main);

        TextView title = text("🚭 QUIT SMOKING", 30);
        title.setGravity(Gravity.CENTER);

        main.addView(title);

        TextView creator = text(
                "Created by JABORZODA FAYZALI",
                15
        );

        creator.setGravity(Gravity.CENTER);

        main.addView(creator);

        main.addView(text(
                "\nТВОЙ ПУТЬ К ЖИЗНИ БЕЗ СИГАРЕТ\n",
                20
        ));

        timerText = text("", 24);
        main.addView(timerText);

        cigarettesText = text("", 19);
        main.addView(cigarettesText);

        moneyText = text("", 19);
        main.addView(moneyText);

        Button motivation = button(
                "🔥 Мне хочется закурить"
        );

        motivation.setOnClickListener(v -> showMotivation());

        main.addView(motivation);

        Button health = button(
                "❤️ Что происходит с организмом"
        );

        health.setOnClickListener(v -> showHealth());

        main.addView(health);

        Button achievements = button(
                "🏆 Мои достижения"
        );

        achievements.setOnClickListener(v -> showAchievements());

        main.addView(achievements);

        Button statistics = button(
                "📊 Статистика"
        );

        statistics.setOnClickListener(v -> showStatistics());

        main.addView(statistics);

        Button relapse = button(
                "🔄 Я сорвался"
        );

        relapse.setOnClickListener(v -> resetTimer());

        main.addView(relapse);

        main.addView(text(
                "\nПомни: один срыв не означает поражение.\n" +
                "Продолжай путь. 🚭\n\n" +
                "JABORZODA FAYZALI",
                16
        ));

        setContentView(scroll);

        updateStats();
    }

    private void updateStats() {

        long now = System.currentTimeMillis();

        long difference = now - startTime;

        long totalSeconds = difference / 1000;

        long days = totalSeconds / 86400;

        long hours = (totalSeconds % 86400) / 3600;

        long minutes = (totalSeconds % 3600) / 60;

        long seconds = totalSeconds % 60;

        timerText.setText(
                "⏱ Без сигарет:\n" +
                days + " дн. " +
                hours + " ч. " +
                minutes + " мин. " +
                seconds + " сек."
        );

        long cigarettesAvoided =
                (days * cigarettesPerDay) +
                (hours * cigarettesPerDay / 24);

        float moneySaved =
                cigarettesAvoided * pricePerCigarette;

        cigarettesText.setText(
                "🚬 Не выкурено примерно: " +
                cigarettesAvoided
        );

        moneyText.setText(
                String.format(
                        Locale.getDefault(),
                        "💰 Сэкономлено: %.0f",
                        moneySaved
                )
        );
    }

    private void showMotivation() {

        new android.app.AlertDialog.Builder(this)
                .setTitle("🔥 Сильная тяга?")
                .setMessage(
                        "Подожди 5 минут.\n\n" +
                        "Сделай несколько глубоких вдохов.\n" +
                        "Выпей воды.\n" +
                        "Выйди на улицу или пройдись.\n\n" +
                        "Тяга обычно проходит. " +
                        "Тебе не нужна эта сигарета. 🚭"
                )
                .setPositiveButton("Я справлюсь 💪", null)
                .show();
    }

    private void showHealth() {

        new android.app.AlertDialog.Builder(this)
                .setTitle("❤️ Восстановление организма")
                .setMessage(
                        "После отказа от курения организм " +
                        "начинает восстанавливаться.\n\n" +

                        "⏱ Первые часы — уровень никотина " +
                        "начинает снижаться.\n\n" +

                        "📅 Дни — постепенно улучшается " +
                        "работа организма.\n\n" +

                        "🫁 Недели и месяцы — организм " +
                        "продолжает восстанавливаться.\n\n" +

                        "Каждый день без сигарет имеет значение."
                )
                .setPositiveButton("Понятно", null)
                .show();
    }

    private void showAchievements() {

        long difference =
                System.currentTimeMillis() - startTime;

        long days = difference / 86400000;

        String achievement;

        if (days >= 30) {
            achievement =
                    "🏆 30 дней — невероятный результат!\n\n" +
                    "Ты уже прошёл огромный путь.";
        } else if (days >= 7) {
            achievement =
                    "🥇 7 дней — целая неделя без сигарет!";
        } else if (days >= 3) {
            achievement =
                    "🥉 3 дня — отличный старт!";
        } else if (days >= 1) {
            achievement =
                    "⭐ 1 день — ты уже начал!";
        } else {
            achievement =
                    "🚭 Твоя первая цель — продержаться сегодня.";
        }

        new android.app.AlertDialog.Builder(this)
                .setTitle("🏆 Достижения")
                .setMessage(achievement)
                .setPositiveButton("Продолжить", null)
                .show();
    }

    private void showStatistics() {

        long difference =
                System.currentTimeMillis() - startTime;

        long days = difference / 86400000;

        new android.app.AlertDialog.Builder(this)
                .setTitle("📊 Статистика")
                .setMessage(
                        "🚭 Дней без сигарет: " +
                        days + "\n\n" +

                        "🎯 Цель: жить без никотина\n\n" +

                        "🔥 Главное достижение — " +
                        "ты продолжаешь бороться."
                )
                .setPositiveButton("Назад", null)
                .show();
    }

    private void resetTimer() {

        new android.app.AlertDialog.Builder(this)
                .setTitle("🔄 Срыв")

                .setMessage(
                        "Срыв — это не конец пути.\n\n" +
                        "Начнём счётчик заново?"
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

                            updateStats();
                        }
                )

                .show();
    }
          }
