package com.development.kernel.draft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LayoutParams layoutParams;
    private LinearLayout linearLayout;
    private Button[] buttons;
    private TextView[] textViews;
    private int countOfButtons=0;
    private int countOfTextViews=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout) findViewById(R.id.activity_main);
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        buttons = new Button[99];
        textViews = new TextView[99];
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
        ViewInspector viewInspector = new ViewInspector(layoutParams,linearLayout,item);
        switch (item.getItemId())
        {
            case 1:
                buttons[countOfButtons] = viewInspector.setDefaultViewOptions(new Button(this));
                countOfButtons++;
                break;
            case 2:
                textViews[countOfTextViews] = viewInspector.setDefaultViewOptions(new TextView(this));
                countOfTextViews++;
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
