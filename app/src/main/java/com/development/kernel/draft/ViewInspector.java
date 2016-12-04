package com.development.kernel.draft;

import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

class ViewInspector {

    private MenuItem item = null;
    private LayoutParams viewParams = null;
    private LinearLayout linearLayout = null;
    private int CountOfButtons = 0;

    ViewInspector(LayoutParams layoutParams, LinearLayout linearLayout, MenuItem item) {
        this.item = item;
        this.viewParams = layoutParams;
        this.linearLayout = linearLayout;
    }

    Button setDefaultViewOptions(Button button) {  //назначает начальные настройки кнопке и тексту перегружен (2)
        button.setText("New button");
        button.setLayoutParams(viewParams);
        button.setId(CountOfButtons); //теги и id для массива кнопок
        button.setTag(CountOfButtons);
        linearLayout.addView(button);
        return button;
    }

    TextView setDefaultViewOptions(TextView textView) {
        textView.setText("New text");
        textView.setLayoutParams(viewParams);
        textView.setId(CountOfButtons); //теги и id для массива кнопок
        textView.setTag(CountOfButtons);
        linearLayout.addView(textView);
        return textView;
    }

    public ImageView setDefaultViewOptions(ImageView imageView) {
        imageView.setLayoutParams(viewParams);
        imageView.setId(CountOfButtons); //теги и id для массива кнопок
        imageView.setTag(CountOfButtons);
        linearLayout.addView(imageView);
        return imageView;
    }

}
