package com.example.eurythmics.adapters;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {

    private double min, max;

    public InputFilterMinMax(double min, double max) {
        this.min = min;
        this.max = max;
    }

    // overload to handle strings
    public InputFilterMinMax(String min, String max) {
        this.min = Double.parseDouble(min);
        this.max = Double.parseDouble(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            // Removes string that is to be replaced from destination
            // and adds the new string in.
            String newVal = dest.subSequence(0, dstart)
                    // Note that below "toString()" is the only required:
                    + source.subSequence(start, end).toString()
                    + dest.subSequence(dend, dest.length());
            double input = Double.parseDouble(newVal);
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(double min, double max, double input) {
        return max > min ? input >= min && input <= max : input >= max && input <= min;
    }
}
