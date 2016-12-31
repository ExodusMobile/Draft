package com.development.kernel.draft;

import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

class ContextMenuInspector {

    ContextMenu setContentMenuOptions(ContextMenu menu, View v, Button[] buttons, TextView[] textViews, ImageView[] imageViews, CardView[] cardViews) {

        if (buttons[v.getId()] != null && v.getTag().equals(buttons[v.getId()].getTag())) {
            menu.setHeaderTitle("Кнопка");
            menu.add(0, 1, 0, "Изменить ширину");
            menu.add(0, 2, 0, "Изменить высоту");
            menu.add(0, 3, 0, "Изменить текст");
            menu.add(0, 4, 0, "Удалить объект");
        }
        if (textViews[v.getId()] != null && v.getTag().equals(textViews[v.getId()].getTag())) {
            menu.setHeaderTitle("Строка");
            menu.add(0, 1, 0, "Изменить текст");
            menu.add(0, 2, 0, "Изменить размер текста");
            menu.add(0, 12, 0,"Центрировать");
            menu.add(0, 4, 0, "Удалить объект");
        }
        if (imageViews[v.getId()] != null && v.getTag().equals(imageViews[v.getId()].getTag())) {
            menu.setHeaderTitle("Изображение");
            menu.add(0, 5, 0, "Импорт из галереи");
            menu.add(0, 6, 0, "Сделать снимок");
            menu.add(0, 7, 0, "Изменить размеры");
            menu.add(0, 4, 0, "Удалить объект");
        }
        if (cardViews[v.getId()] != null && v.getTag().equals(cardViews[v.getId()].getTag())) {
            menu.setHeaderTitle("Макетный холст");
            menu.add(1, 10, 0, "Добавить картинку");
            menu.add(2, 11, 0, "Добавить задачу");
            menu.add(0, 4, 0, "Удалить объект");
        }

        return menu;
    }

    void setContextMenuItemsOptions(MenuItem item, Object tag, TextView[] textViews, ImageView[] imageViews,
                                    Button[] buttons, CardView[] cardViews, int ID, EditText editText, EditText editText1, Button button, Button button1) {
        if (textViews[ID] != null && tag.equals(textViews[ID].getTag())) {
            if (item.getItemId() == 4) textViews[ID].setVisibility(View.GONE);
            else if (item.getItemId() == 1) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                editText.setHint("Напишите Ваш текст");
                editText.setText(textViews[ID].getText().toString());
            }
            else {
                editText.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                editText.setHint("Размер текста");
                editText.setText("");
            }
        }

        if (imageViews[ID] != null && tag.equals(imageViews[ID].getTag())) {
            if (item.getItemId() == 4) imageViews[ID].setVisibility(View.GONE);
            else if (item.getItemId() == 7 || item.getItemId() == 8) {
                button1.setVisibility(View.VISIBLE);
                editText.setHint("Высота (Минимальные размеры: 300px)");
                editText1.setHint("Ширина (Минимальные размеры: 300px)");
                editText1.setInputType(InputType.TYPE_CLASS_PHONE);
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                editText.setVisibility(View.VISIBLE);
                editText1.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                editText.setText("");
                editText1.setText("");
            }
        }

        if (buttons[ID] != null && tag.equals(buttons[ID].getTag())) {
            if (item.getItemId() == 3) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                button1.setVisibility(View.VISIBLE);
                editText.setText(buttons[ID].getText().toString());
            } else if (item.getItemId() == 1 || item.getItemId() == 2) {
                editText.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                button1.setVisibility(View.VISIBLE);
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                editText.setText("");
            } else buttons[ID].setVisibility(View.GONE);
        }
        if (cardViews[ID] != null && tag.equals(cardViews[ID].getTag())) {
            if (item.getItemId() == 4) cardViews[ID].setVisibility(View.GONE);
        }
    }
}
