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

    ViewInspector(LayoutParams layoutParams, LinearLayout linearLayout, MainActivity mainActivity) { //инициализация обязательных параметров
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

    public ImageView setDefaultViewOptions(ImageView imageView) { //TODO: реализовать метод setDefaultViewOptions
        imageView.setLayoutParams(viewParams);
        linearLayout.addView(imageView);
        return imageView;
    }

    void setContextOptions(MenuItem item)//запись выбранного элемента в контекстном меню в переменную
    {
        WIS = item.getItemId();
    }

    Button setPropertiesForView(Button button, EditText editText, Button buttonEdit) //настройки для кнопки
    {
        switch (WIS)
        {
            case 1: //изменить ширину
                try {
                    button.setWidth(Integer.valueOf(editText.getText().toString()));
                    editText.setVisibility(View.GONE);
                    buttonEdit.setVisibility(View.GONE);
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity,"Неверный формат ввода",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2: //изменить высоту
                try {
                    button.setHeight(Integer.valueOf(editText.getText().toString()));
                    editText.setVisibility(View.GONE); //удалить editText
                    buttonEdit.setVisibility(View.GONE);
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity,"Неверный формат ввода",Toast.LENGTH_SHORT).show();
                }
                break;
            case 3: //изменить название
                button.setText(editText.getText().toString());
                editText.setVisibility(View.GONE);
                buttonEdit.setVisibility(View.GONE);
                break;
            case 4: //удалить view элемент с экрана
                CountButtonsTags--; //понизить тег, чтобы предотратить ошибку
                break;

        }

        return button; //возвратить view элемент со всеми настройками
    }

    TextView setPropertiesForView(TextView textView, EditText editText, Button buttonEdit)//настройки для строки
    {

        switch (WIS)
        {

            case 3:
                textView.setText(editText.getText().toString());
                editText.setVisibility(View.GONE);
                buttonEdit.setVisibility(View.GONE);
                break;
            case 4:
                CountTextViewTags--;//понижение тега
                break;

        }

        return textView; //возвратить view элемент со всеми настройками
    }
}
