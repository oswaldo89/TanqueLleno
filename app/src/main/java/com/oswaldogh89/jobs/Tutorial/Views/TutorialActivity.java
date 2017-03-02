package com.oswaldogh89.jobs.Tutorial.Views;

import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.oswaldogh89.jobs.R;

public class TutorialActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setButtonBackVisible(false);
        setButtonNextVisible(false);
        setButtonCtaVisible(true);
        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_BACKGROUND);
        TypefaceSpan labelSpan = new TypefaceSpan(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? "sans-serif-medium" : "sans serif");
        SpannableString label = SpannableString.valueOf("Omitir Tutorial");
        label.setSpan(labelSpan, 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setButtonCtaLabel(label);

        setPageScrollDuration(500);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setPageScrollInterpolator(android.R.interpolator.fast_out_slow_in);
        }

        addSlide(new SimpleSlide.Builder()
                .title("Busca fácil")
                .description("La busqueda de empleos ahora es facil y rapida.")
                .image(R.drawable.intro_one)
                .background(R.color.color_canteen)
                .backgroundDark(R.color.color_dark_canteen)
                .layout(R.layout.slide_canteen)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Dinero Rapido")
                .description("Trabajos de ocación, en el que puedes obetener dinero en pocas horas.")
                .image(R.drawable.intro_two)
                .background(R.color.color_canteen)
                .backgroundDark(R.color.color_dark_canteen)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Clientes Felices")
                .description("Obten calificación por tus trabajos y gana una mejor presentacón laboral.")
                .image(R.drawable.intro_tree)
                .background(R.color.color_canteen)
                .backgroundDark(R.color.color_dark_canteen)
                .build());

        autoplay(2500, INFINITE);

    }
}
