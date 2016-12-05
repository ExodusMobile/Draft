package com.development.kernel.draft;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

class ViewInspector {


    private LayoutParams viewParams = null;
    private LinearLayout linearLayout = null;

    private int buttonsId = 0;
    private int CountButtonsTags = 0;


    private int CountTextViewTags = 0;
    private int textViewsId = 0;

    private int WIS;
    private MainActivity mainActivity;

    ViewInspector(LayoutParams layoutParams, LinearLayout linearLayout, MainActivity mainActivity) {
        this.viewParams = layoutParams;
        this.linearLayout = linearLayout;
        this.mainActivity = mainActivity;
    }

    Button setDefaultViewOptions(Button button) {  //назначает начальные настройки кнопке и тексту перегружен (2)
        button.setText("New button");
        button.setLayoutParams(viewParams);
        button.setId(buttonsId); //теги и id для массива кнопок
        String buttonTags = "Button:";
        button.setTag(buttonTags + String.valueOf(CountButtonsTags++));
        linearLayout.addView(button);
        buttonsId++;
        return button;
    }

    TextView setDefaultViewOptions(TextView textView) {
        textView.setText("New text");
        textView.setTextSize(30);
        textView.setLayoutParams(viewParams);
        textView.setId(textViewsId); //теги и id для массива кнопок
        String textViewsTags = "TextView:";
        textView.setTag(textViewsTags + String.valueOf(CountTextViewTags++)); //Была создана переменная, чтобы упростить читаемость кода, можно все сделать и с 2 переменными
        linearLayout.addView(textView);
        textViewsId++;
        return textView;
    }

    public ImageView setDefaultViewOptions(ImageView imageView) {
        imageView.setLayoutParams(viewParams);
        linearLayout.addView(imageView);
        return imageView;
    }

    void setContextOptions(MenuItem item)
    {
        WIS = item.getItemId();
    }

    Button setPropertiesForView(Button button, EditText editText, Button buttonEdit)
    {
        switch (WIS)
        {
            case 1:
                try {
                    button.setWidth(Integer.valueOf(editText.getText().toString()));
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity,"Неверный формат ввода",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                try {
                    button.setHeight(Integer.valueOf(editText.getText().toString()));
                    editText.setVisibility(View.GONE);
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity,"Неверный формат ввода",Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                button.setText(editText.getText().toString());
                editText.setVisibility(View.GONE);
                break;
            case 4:
                button.setVisibility(View.GONE);
                CountButtonsTags--;
                break;

        }
        editText.setVisibility(View.GONE);
        buttonEdit.setVisibility(View.GONE);
        return button;
    }

    TextView setPropertiesForView(TextView textView, EditText editText, Button buttonEdit)
    {
        switch (WIS)
        {
            case 1:
                try {
                    textView.setWidth(Integer.valueOf(editText.getText().toString()));
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity,"Неверный формат ввода",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                try {
                    textView.setHeight(Integer.valueOf(editText.getText().toString()));
                    editText.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity,"Неверный формат ввода",Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                textView.setText(editText.getText().toString());
                editText.setVisibility(View.GONE);
                buttonEdit.setVisibility(View.GONE);
                break;
            case 4:
                textView.setVisibility(View.GONE);
                CountTextViewTags--;
                break;

        }
        editText.setVisibility(View.GONE);
        buttonEdit.setVisibility(View.GONE);
        return textView;
    }
}
