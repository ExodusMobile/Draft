package com.development.kernel.draft;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Anton on 05.12.2016.
 */

class TagControl {

    private Button[] buttons;
    private TextView[] textViews;
    private int ID;

    TagControl(int ID,Button[] buttons, TextView[] textViews)//инициализация обязательных параметров
    {
        this.buttons = buttons;
        this.textViews = textViews;
        this.ID = ID;
    }

    boolean isButtonTag(View view) //метод, проверяющий кнопки ли этот тег
     {
        boolean trueOrNot = false;
        try {
            if(view.getTag().equals(buttons[view.getId()].getTag()))
            {
                trueOrNot = true;
            }
        }
        catch (Exception ignored) {}
        return trueOrNot;
    }
    boolean isButtonTag(Object tag) //перегрузка прошлого метода, для удобства работы (если нет аргумента (View view))
    {
        boolean trueOrNot = false;
        try {
            if(tag.equals(buttons[ID].getTag()))
            {
                trueOrNot = true;
            }
        }
        catch (Exception ignored) {}
        return trueOrNot;
    }

    boolean isTextViewTag(View view)//проверка, строки ли этот тег
    {
        boolean trueOrNot = false;
        try {
            if(view.getTag().equals(textViews[view.getId()].getTag()))
            {
                trueOrNot = true;
            }
        }
        catch (Exception ignored) {}
        return trueOrNot;
    }
    boolean isTextViewTag(Object tag)//перегрузка прошлого метода, для удобства работы (если нет аргумента (View view))
    {
        boolean trueOrNot = false;
        try {
            if(tag.equals(textViews[ID].getTag()))
            {
                trueOrNot = true;
            }
        }
        catch (Exception ignored) {}
        return trueOrNot;
    }
}
