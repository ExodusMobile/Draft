package com.development.kernel.draft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewInspector viewInspector;
    private Button[] buttons;
    private TextView[] textViews;
    private int countOfButtons=0;
    private int countOfTextViews=0;
    private int ID;
    private boolean checkElement;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //назначаем начальные настройки
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.android_main_context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        buttons = new Button[99];
        textViews = new TextView[99];
        editText = (EditText) findViewById(R.id.edit_text);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        viewInspector = new ViewInspector(layoutParams, linearLayout,this); //создаем экземпляр нашего класса

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"Кнопка");
        menu.add(0,2,0,"Строка");
        menu.add(0,3,0,"Изображение");
        menu.add(0,4,0,"Макетный холст");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 1:
                buttons[countOfButtons] = viewInspector.setDefaultViewOptions(new Button(this)); //создаем кнопку и назначаем ей начальные найтроки в viewInspector
                registerForContextMenu(buttons[countOfButtons]);
                countOfButtons++;
                break;

            case 2:
                textViews[countOfTextViews] = viewInspector.setDefaultViewOptions(new TextView(this));// точно также с textView
                registerForContextMenu(textViews[countOfTextViews]);
                countOfTextViews++;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
            try {
                if (v.getTag().equals(buttons[v.getId()].getTag())) {
                    checkElement = true;
                }
                else {
                    checkElement = false;
                }
            }
            catch (Exception e) {
                checkElement = false;
            }
        menu.add(0, 1, 0, "Изменить ширину");
        menu.add(0, 2, 0, "Изменить высоту");
        menu.add(0, 3, 0, "Изменить текст");
        menu.add(0, 4 ,0, "Удалить объект");
        ID = v.getId();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        viewInspector.setContextOptions(item);
        editText.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        return super.onContextItemSelected(item);

    }

    @Override
    public void onClick(View view) {
        if(!checkElement) {

            textViews[ID] = viewInspector.setPropertiesForView(textViews[ID], editText, button);
        }
        else {
            buttons[ID] = viewInspector.setPropertiesForView(buttons[ID], editText, button);
        }
    }
}
