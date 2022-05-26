package ru.yandex.practicum.contacts.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.yandex.practicum.contacts.R;
import ru.yandex.practicum.contacts.databinding.ViewFlatButtonBinding;


public class FlatButton extends FrameLayout {

    private ViewFlatButtonBinding binding;

    public FlatButton(@NonNull Context context) {
        this(context, null, 0);
    }

    public FlatButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlatButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithAttrs(context, attrs);
    }

    private void initWithAttrs(Context context, AttributeSet attrs) {
        binding = ViewFlatButtonBinding.inflate(LayoutInflater.from(context), this);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlatButton);

        final String text = array.getString(R.styleable.FlatButton_android_text);
        binding.text.setText(text);

        final Drawable background = array.getDrawable(R.styleable.FlatButton_android_background);
        if (background != null) {
            setBackground(background);
        }
        final ColorStateList textColor = array.getColorStateList(R.styleable.FlatButton_android_textColor);
        if (textColor != null) {
            binding.text.setTextColor(textColor);
        }


        array.recycle();
    }
}
