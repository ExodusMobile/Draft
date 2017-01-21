package com.development.kernel.draft;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    static final int GALLERY_REQUEST = 1;
    static final int CAMERA_REQUEST = 2;
    static final int CARD_IMAGE_REQUEST = 3;
    static final int HEADER_REQUEST = 4;
    private Uri selectImagePublic = null;

    private ViewInspector viewInspector;
    private Button[] buttons;
    private TextView[] textViews;
    private ImageView[] imageViews;
    private CardView[] cardViews;
    private int countOfButtons = 0;
    private int countOfTextViews = 0;
    private int countOfImageViews = 0;
    private int countOfCardViews = 0;
    private int ID;
    private Object tag;
    private EditText editText;
    private EditText editText1;
    private Button button;
    private Button button1;
    private ContextMenuInspector contextMenuInspector;
    private LinearLayout hintLayout;
    private LinearLayout linearLayout1;
    private ImageView[] imageViews1;
    private ImageView headerImage;

    private FloatingActionsMenu floatingActionsMenu;
    private BottomNavigationBar bottomNavigationBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) { //назначаем начальные настройки
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_recent_actors_white_36dp,"Companies"))
                .addItem(new BottomNavigationItem(R.drawable.ic_settings_white_36dp,"Settings"))
                .addItem(new BottomNavigationItem(R.drawable.ic_reorder_white_36dp,"Edit"))
                .addItem(new BottomNavigationItem(R.drawable.ic_info_white_36dp,"Help"))
                .addItem(new BottomNavigationItem(R.drawable.ic_account_circle_white_36dp,"Profile"))
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setMode(BottomNavigationBar.MODE_DEFAULT)
                .initialise();


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.android_main_context);
        linearLayout1 = (LinearLayout) findViewById(R.id.linear_layout);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 30, 0, 30);
        buttons = new Button[99];
        textViews = new TextView[99];
        imageViews = new ImageView[99];
        cardViews = new CardView[99];
        LinearLayout[] linearLayouts = new LinearLayout[99];
        editText = (EditText) findViewById(R.id.edit_text);
        editText1 = (EditText) findViewById(R.id.edit_text1);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button_close);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        imageViews1 = new ImageView[300];
        hintLayout = (LinearLayout) findViewById(R.id.hint_layout);
        headerImage = (ImageView) findViewById(R.id.main_backdrop);
        registerForContextMenu(headerImage);
        headerImage.setTag("Header");
        floatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.floatingbutton);



        FloatingActionButton action1 = (FloatingActionButton) findViewById(R.id.action1);
        FloatingActionButton action2 = (FloatingActionButton) findViewById(R.id.action2);
        FloatingActionButton action3 = (FloatingActionButton) findViewById(R.id.action3);
        FloatingActionButton action4 = (FloatingActionButton) findViewById(R.id.action4);

        action1.setOnClickListener(this);
        action2.setOnClickListener(this);
        action3.setOnClickListener(this);
        action4.setOnClickListener(this);


        viewInspector = new ViewInspector(layoutParams, linearLayout, linearLayouts, this, bottomNavigationBar); //создаем экземпляр нашего класса
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        bottomNavigationBar.show();
        if(v.getTag().equals("Header"))
        {
            menu.add(0,0,0,"Изменить шапку");
        }
        else {
            editText1.setVisibility(View.GONE);
            button1.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
            contextMenuInspector = new ContextMenuInspector();
            contextMenuInspector.setContentMenuOptions(menu, v, buttons, textViews, imageViews, cardViews);
            linearLayout1.setVisibility(View.GONE);
            ID = v.getId();
            tag = v.getTag();
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        viewInspector.setContextOptions(item);
        floatingActionsMenu.collapse();
        linearLayout1.setVisibility(View.VISIBLE);
        if(item.getItemId() == 0) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, HEADER_REQUEST);
        }
        else {
            contextMenuInspector.setContextMenuItemsOptions(item, tag, textViews, imageViews, buttons,
                    cardViews, ID, editText, editText1, button, button1, linearLayout1, imageViews1, viewInspector, bottomNavigationBar); //управляем действиями
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
            switch (requestCode) {
                case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    selectImagePublic = selectedImage;
                    Picasso.with(this)
                            .load(selectedImage)
                            .resize(1080, 750)
                            .into(imageViews[ID]);
                }
                    break;
                case CAMERA_REQUEST:
                    if (resultCode == RESULT_OK) {
                        Uri selectedImage = imageReturnedIntent.getData();
                        selectImagePublic = selectedImage;
                        Picasso.with(this)
                                .load(selectedImage)
                                .resize(1080, 700)
                                .into(imageViews[ID]);
                    }
                    break;
                case CARD_IMAGE_REQUEST:
                    if (resultCode == RESULT_OK) {
                        Uri selectedImage = imageReturnedIntent.getData();
                        selectImagePublic = selectedImage;
                        Picasso.with(this)
                                .load(selectedImage)
                                .resize(460, 400)
                                .into(imageViews1[ID]);
                    }
                    break;
                case HEADER_REQUEST:
                    if (resultCode == RESULT_OK) {
                        Uri selectedImage = imageReturnedIntent.getData();
                        selectImagePublic = selectedImage;
                        Picasso.with(this)
                                .load(selectedImage)
                                .resize(1080, 700)
                                .into(headerImage);
                    }
                    break;
            }
    }

    @Override
    public void onClick(View view) {
        floatingActionsMenu.collapse();
        switch (view.getId()) {
            case R.id.button:
                bottomNavigationBar.show();
            if (textViews[ID] != null && tag.equals(textViews[ID].getTag())) textViews[ID] = viewInspector.setPropertiesForView(textViews[ID], editText, button);

            else if (buttons[ID] != null && tag.equals(buttons[ID].getTag())) buttons[ID] = viewInspector.setPropertiesForView(buttons[ID], editText);

            else if (imageViews[ID] != null && tag.equals(imageViews[ID].getTag())) imageViews[ID] = viewInspector.setPropertiesForView(imageViews[ID], selectImagePublic, editText, editText1);
                break;
            case R.id.button_close:
                editText1.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                linearLayout1.setVisibility(View.GONE);
                bottomNavigationBar.show();
                break;
            case R.id.action1:
                buttons[countOfButtons] = viewInspector.setDefaultViewOptions(new Button(this)); //создаем кнопку и назначаем ей начальные найтроки в viewInspector
                registerForContextMenu(buttons[countOfButtons]);
                countOfButtons++;
                hintLayout.setVisibility(View.GONE);
                break;
            case R.id.action2:
                textViews[countOfTextViews] = viewInspector.setDefaultViewOptions(new TextView(this));// точно также с textView
                registerForContextMenu(textViews[countOfTextViews]);
                countOfTextViews++;
                hintLayout.setVisibility(View.GONE);
                break;
            case R.id.action3:
                imageViews[countOfImageViews] = viewInspector.setDefaultViewOptions(new ImageView(this));
                registerForContextMenu(imageViews[countOfImageViews]);
                countOfImageViews++;
                hintLayout.setVisibility(View.GONE);
                break;
            case R.id.action4:
                cardViews[countOfCardViews] = viewInspector.setDefaultViewOptions(new CardView(this));
                registerForContextMenu(cardViews[countOfCardViews]);
                countOfCardViews++;
                hintLayout.setVisibility(View.GONE);
                break;

        }
        linearLayout1.setVisibility(View.GONE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}

