package com.development.kernel.draft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final int GALLERY_REQUEST = 1;
    static final int CAMERA_REQUEST = 2;

    private Uri selectImagePublic = null;

    private ViewInspector viewInspector;
    private Button[] buttons;
    private TextView[] textViews;
    private ImageView[] imageViews;
    private int countOfButtons = 0;
    private int countOfTextViews = 0;
    private int countOfImageViews = 0;
    private int ID;
    private Object tag;
    private EditText editText;
    private EditText editText1;
    private Button button;
    private Button button1;
    private ContextMenuInspector contextMenuInspector;

    SharedPreferences sPref;

    private final int SaveViews = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //назначаем начальные настройки
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.android_main_context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        buttons = new Button[99];
        textViews = new TextView[99];
        imageViews = new ImageView[99];
        editText = (EditText) findViewById(R.id.edit_text);
        editText1 = (EditText) findViewById(R.id.edit_text1);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button_close);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        viewInspector = new ViewInspector(layoutParams, linearLayout, this); //создаем экземпляр нашего класса
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Кнопка");
        menu.add(0, 2, 0, "Строка");
        menu.add(0, 3, 0, "Изображение");
        menu.add(0, 4, 0, "Макетный холст");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
            case 3:
                imageViews[countOfImageViews] = viewInspector.setDefaultViewOptions(new ImageView(this));
                registerForContextMenu(imageViews[countOfImageViews]);
                countOfImageViews++;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        editText1.setVisibility(View.GONE);
        button1.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);
        contextMenuInspector = new ContextMenuInspector();
        contextMenuInspector.setContentMenuOptions(menu, v, buttons, textViews, imageViews);
        ID = v.getId();
        tag = v.getTag();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        viewInspector.setContextOptions(item);
        contextMenuInspector.setContextMenuItemsOptions(item, tag, textViews, imageViews, buttons, ID, editText, editText1,button, button1);
        if(item.getItemId() == 5) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        }
        else if(item.getItemId() == 6)
        {
            Intent photoPickerIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(photoPickerIntent1, CAMERA_REQUEST);
        }
        return super.onContextItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
                if(resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    selectImagePublic = selectedImage;
                    Picasso.with(this)
                            .load(selectedImage)
                            .resize(600, 600)
                            .into(imageViews[ID]);
                }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
            if (textViews[ID] != null && tag.equals(textViews[ID].getTag()))
                textViews[ID] = viewInspector.setPropertiesForView(textViews[ID], editText, button);
            else if (buttons[ID] != null && tag.equals(buttons[ID].getTag()))
                buttons[ID] = viewInspector.setPropertiesForView(buttons[ID], editText, button);
            else
                imageViews[ID] = viewInspector.setPropertiesForView(imageViews[ID], selectImagePublic, editText, editText1, button);
                break;
            case R.id.button_close:
                editText1.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                break;
        }
    }
}

