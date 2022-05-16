package com.example.eurythmics.model.api.Review;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.eurythmics.R;

public class NumberPickerView extends ConstraintLayout {

    private ImageButton incrementButton;
    private ImageButton decrementButton;
    private EditText numberEditText;

    public NumberPickerView(@NonNull Context context) {
        super(context);
    }

    public NumberPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberPickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        incrementButton = findViewById(R.id.increment);
        decrementButton = findViewById(R.id.decrement);
        numberEditText = findViewById(R.id.number);
    }

}
