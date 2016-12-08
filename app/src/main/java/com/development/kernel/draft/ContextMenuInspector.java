package com.development.kernel.draft;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

class ContextMenuInspector {

    ContextMenu setContentMenuOptions(ContextMenu menu, View v, Button[] buttons, TextView[] textViews, ImageView[] imageViews) {
            if (buttons[v.getId()] != null && v.getTag().equals(buttons[v.getId()].getTag()) ) {
                    menu.add(0, 1, 0, "Изменить ширину");
                    menu.add(0, 2, 0, "Изменить высоту");
                    menu.add(0, 3, 0, "Изменить текст");
                    menu.add(0, 4, 0, "Удалить объект");
            }
            if(textViews[v.getId()] != null && v.getTag().equals(textViews[v.getId()].getTag()))
            {
                    menu.add(0, 1, 0, "Изменить текст");
                    menu.add(0, 4, 0, "Удалить объект");
            }
            if(imageViews[v.getId()] != null && v.getTag().equals(imageViews[v.getId()].getTag()))
            {
                    menu.add(0, 5, 0, "Импорт из галереи");
                    menu.add(0, 4, 0, "Удалить объект");
            }
        return menu;
    }
    void setContextMenuItemsOptions(MenuItem item,Object tag, TextView[] textViews, ImageView[] imageViews,
                                    Button[] buttons, ViewInspector viewInspector, int ID, EditText editText, Button button) {
        if (item.getItemId() == 4 || item.getItemId() == 5) {
                if (textViews[ID] != null && tag.equals(textViews[ID].getTag())) {
                    textViews[ID].setVisibility(View.GONE);
                }
                if (imageViews[ID] != null && tag.equals(imageViews[ID].getTag())) {
                        if (item.getItemId() == 4) imageViews[ID].setVisibility(View.GONE);
                        else imageViews[ID] = viewInspector.setPropertiesForView(imageViews[ID]);
                }
                if(buttons[ID] != null && tag.equals(buttons[ID].getTag())) {
                     buttons[ID].setVisibility(View.GONE);
                }
        }
        else {
            editText.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
    }
}
