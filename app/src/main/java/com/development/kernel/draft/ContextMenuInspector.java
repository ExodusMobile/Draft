package com.development.kernel.draft;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

class ContextMenuInspector {

    ContextMenu setContentMenuOptions(ContextMenu menu, View v, Button[] buttons, TextView[] textViews, ImageView[] imageViews, CardView[] cardViews) {

        if (buttons[v.getId()] != null && v.getTag().equals(buttons[v.getId()].getTag())) { //Кнопки
            menu.setHeaderTitle("Кнопка");
            menu.add(0, 1, 0, "Изменить ширину");
            menu.add(0, 2, 0, "Изменить высоту");
            menu.add(0, 3, 0, "Изменить текст");
            menu.add(0, 4, 0, "Удалить объект");
        }
        if (textViews[v.getId()] != null && v.getTag().equals(textViews[v.getId()].getTag())) { //TextView
            menu.setHeaderTitle("Строка");
            menu.add(0, 1, 0, "Изменить текст");
            menu.add(0, 2, 0, "Изменить размер текста");
            menu.add(0, 3, 0,"Центрировать");
            menu.add(0, 4 ,0,"Изменить цвет");
            menu.add(0, 5, 0, "Удалить объект");

        }
        if (imageViews[v.getId()] != null && v.getTag().equals(imageViews[v.getId()].getTag())) { //Изображение
            menu.setHeaderTitle("Изображение");
            menu.add(0, 1, 0, "Импорт из галереи");
            menu.add(0, 2, 0, "Сделать снимок");
            menu.add(0, 3, 0, "Изменить размеры");
            menu.add(0, 4, 0, "Удалить объект");
        }
        if (cardViews[v.getId()] != null && v.getTag().equals(cardViews[v.getId()].getTag())) { //Холст
            menu.setHeaderTitle("Макетный холст");
            menu.add(0, 1, 0, "Добавить картинку");
            menu.add(0, 2, 0, "Добавить задачу");
            menu.add(0, 3, 0, "Изменить цвет фона");
            menu.add(0, 4, 0, "Удалить объект");
        }


        return menu;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    void setContextMenuItemsOptions(MenuItem item, Object tag, TextView[] textViews, ImageView[] imageViews,
                                    Button[] buttons, CardView[] cardViews, int ID, EditText editText,
                                    EditText editText1, Button button, Button button_close, LinearLayout editField,
                                    ImageView[] imageViews1, ViewInspector viewInspector,
                                    HorizontalScrollView color_scroll) {
        if (textViews[ID] != null && tag.equals(textViews[ID].getTag())) {
            switch (item.getItemId())
            {
                case 1:
                    editField.setVisibility(View.VISIBLE);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    editText.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    button_close.setVisibility(View.VISIBLE);
                    editText.setHint("Напишите Ваш текст");

                    editText.setText(textViews[ID].getText().toString());
                    break;

                case 2:
                    editField.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    button_close.setVisibility(View.VISIBLE);
                    editText.setInputType(InputType.TYPE_CLASS_PHONE);
                    editText.setHint("Размер текста (sp)");
                    editText.setText("");
                    break;
                case 3:
                    textViews[ID] = viewInspector.setPropertiesForView(textViews[ID], editText, button);
                    break;
                case 4:
                    editField.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    button_close.setVisibility(View.VISIBLE);
                    color_scroll.setVisibility(View.VISIBLE);
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
                    editText.setHint("Пример ввода - \"#990061\"");
                    editText.setText("");
                    break;
                case 5:
                    textViews[ID].setVisibility(View.GONE);
                    break;
            }
        }
        if (buttons[ID] != null && tag.equals(buttons[ID].getTag())) {

            switch (item.getItemId()) {
                case 1:
                    editField.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    button_close.setVisibility(View.VISIBLE);

                    editText.setInputType(InputType.TYPE_CLASS_PHONE);
                    editText.setText("");
                    break;
                case 2:
                    editField.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    button_close.setVisibility(View.VISIBLE);

                    editText.setInputType(InputType.TYPE_CLASS_PHONE);
                    editText.setText("");
                    break;
                case 3:
                    editField.setVisibility(View.VISIBLE);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    editText.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    button_close.setVisibility(View.VISIBLE);

                    editText.setText(buttons[ID].getText().toString());
                    break;
                case 4:
                    buttons[ID].setVisibility(View.GONE);
                    break;
            }
        }
        if (imageViews[ID] != null && tag.equals(imageViews[ID].getTag())) {

            switch (item.getItemId()){
                case 1:
                    viewInspector.setPropertiesForView(imageViews[ID]);
                    break;
                case 2:
                    viewInspector.setPropertiesForView(imageViews[ID]);
                    break;
                case 3:
                    editField.setVisibility(View.VISIBLE);
                    button_close.setVisibility(View.VISIBLE);
                    editText.setHint("Высота");
                    editText1.setHint("Ширина");
                    editText1.setInputType(InputType.TYPE_CLASS_PHONE);
                    editText.setInputType(InputType.TYPE_CLASS_PHONE);
                    editText.setVisibility(View.VISIBLE);
                    editText1.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    editText.setText("");
                    editText1.setText("");
                    break;
                case 4:
                    imageViews[ID].setVisibility(View.GONE);
                    break;
            }

        }


        if (cardViews[ID] != null && tag.equals(cardViews[ID].getTag())) {
            switch (item.getItemId()){
                case 1:
                    viewInspector.setPropertiesForView(cardViews[ID],imageViews1,editText,ID, editField);
                    break;
                case 2:
                    viewInspector.setPropertiesForView(cardViews[ID],imageViews1,editText,ID, editField);
                    break;
                case 3:
                    viewInspector.setPropertiesForView(cardViews[ID],imageViews1,editText,ID, editField);
                    editField.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    color_scroll.setVisibility(View.VISIBLE);
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
                    editText.setHint("Пример ввода - \"#990061\"");
                    editText.setText("");
                    break;
                case 4:
                    cardViews[ID].setVisibility(View.GONE);
                    break;
            }
        }
    }
}
