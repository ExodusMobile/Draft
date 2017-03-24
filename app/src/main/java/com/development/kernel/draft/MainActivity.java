package com.development.kernel.draft;



import android.content.Intent;
import android.graphics.Color;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


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
    private String TOKEN_USER;
    private Object tag;
    private EditText editText;
    private EditText editText1;
    private Button button;
    private Button button_close;
    private ContextMenuInspector contextMenuInspector;
    private LinearLayout hintLayout;
    private LinearLayout linearLayout1;
    private ImageView[] imageViews1;
    private ImageView headerImage;

    private FloatingActionsMenu floatingActionsMenu;
    private HorizontalScrollView color_scroll;

    private DrawerLayout mDrawerLayout;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) { //назначаем начальные настройки
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent1 = getIntent();
        TOKEN_USER = intent1.getStringExtra("TOKEN");
        Log.d("Id_User1",String.valueOf(TOKEN_USER));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
// Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        int id = menuItem.getItemId();

                        switch (id) {
                            case R.id.first:
                                intent = new Intent(MainActivity.this, ProfileActivity.class);
                                intent.putExtra("TOKEN",TOKEN_USER);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.second:
                                intent = new Intent(MainActivity.this, ProjectsActivity.class);
                                intent.putExtra("TOKEN",TOKEN_USER);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.third:
                                break;
                            case R.id.settings:
                                intent = new Intent(MainActivity.this, SettingsActivity.class);
                                intent.putExtra("TOKEN",TOKEN_USER);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.exit:
                                intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                        // Closing drawer on item click

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        color_scroll = (HorizontalScrollView) findViewById(R.id.color_scroll);

        //Standart_Colors--------------------------------------------

        Button color_black = (Button) findViewById(R.id.color_black);
        Button color_green = (Button) findViewById(R.id.color_green);
        Button color_blue = (Button) findViewById(R.id.color_blue);
        Button color_red = (Button) findViewById(R.id.color_red);
        Button color_gray = (Button) findViewById(R.id.color_gray);
        Button color_purple = (Button) findViewById(R.id.color_purple);
        Button color_orange = (Button) findViewById(R.id.color_orange);
        

        color_black.setOnClickListener(this);
        color_green.setOnClickListener(this);
        color_blue.setOnClickListener(this);
        color_red.setOnClickListener(this);
        color_gray.setOnClickListener(this);
        color_purple.setOnClickListener(this);
        color_orange.setOnClickListener(this);


        //Standart_Colors--------------------------------------------


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.android_main_context);
        linearLayout1 = (LinearLayout) findViewById(R.id.linear_layout);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 15, 0, 15);
        buttons = new Button[99];
        textViews = new TextView[99];
        imageViews = new ImageView[99];
        cardViews = new CardView[99];
        LinearLayout[] linearLayouts = new LinearLayout[99];
        editText = (EditText) findViewById(R.id.edit_text);
        editText1 = (EditText) findViewById(R.id.edit_text1);
        button = (Button) findViewById(R.id.button);
        button_close = (Button) findViewById(R.id.button_close);
        button.setOnClickListener(this);
        button_close.setOnClickListener(this);
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


        viewInspector = new ViewInspector(layoutParams, linearLayout, linearLayouts, this); //создаем экземпляр нашего класса
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getTag().equals("Header"))
        {
            menu.add(0,0,0,"Изменить шапку");
        }
        else {
            editText1.setVisibility(View.GONE);
            button_close.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
            color_scroll.setVisibility(View.GONE);
            contextMenuInspector = new ContextMenuInspector();
            contextMenuInspector.setContentMenuOptions(menu, v, buttons, textViews, imageViews, cardViews);
            linearLayout1.setVisibility(View.GONE);
            ID = v.getId();
            tag = v.getTag();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        viewInspector.setContextOptions(item);
        floatingActionsMenu.collapse();

        if(item.getItemId() == 0) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, HEADER_REQUEST);
        }
        else {
            contextMenuInspector.setContextMenuItemsOptions(item, tag, textViews, imageViews, buttons,
                    cardViews, ID, editText, editText1, button, button_close, linearLayout1,
                    imageViews1, viewInspector, color_scroll); //управляем действиями
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
                            .resize(800, 900)
                            .into(imageViews[ID]);
                }
                    break;
                case CAMERA_REQUEST:
                    if (resultCode == RESULT_OK) {
                        Uri selectedImage = imageReturnedIntent.getData();
                        selectImagePublic = selectedImage;
                        MainActivity m = new MainActivity();
                        Display display = m.getWindowManager().getDefaultDisplay();
                        int width = display.getWidth();  // deprecated
                        Picasso.with(this)
                                .load(selectedImage)
                                .resize(width, 700)
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
                        Display display = getWindowManager().getDefaultDisplay();
                        int width = display.getWidth();  // deprecated
                        // deprecated
                        Picasso.with(this)
                                .load(selectedImage)
                                .resize(width, 700)
                                .into(headerImage);
                    }
                    break;
            }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View view) {
        floatingActionsMenu.collapse();
        switch (view.getId()) {
            case R.id.button:

            if (textViews[ID] != null && tag.equals(textViews[ID].getTag())) textViews[ID] = viewInspector.setPropertiesForView(textViews[ID], editText, button);

            else if (buttons[ID] != null && tag.equals(buttons[ID].getTag())) buttons[ID] = viewInspector.setPropertiesForView(buttons[ID], editText);

            else if (imageViews[ID] != null && tag.equals(imageViews[ID].getTag())) imageViews[ID] = viewInspector.setPropertiesForView(imageViews[ID], selectImagePublic, editText, editText1);

            else if(cardViews[ID] != null && tag.equals(cardViews[ID].getTag())) cardViews[ID] = viewInspector.setPropertiesForView(cardViews[ID],imageViews,editText,ID,linearLayout1);
                break;

            //Standart_Colors--------------------------------------------

            case R.id.color_black:
                if(textViews[ID]!= null && tag.equals(textViews[ID].getTag())) textViews[ID].setTextColor(Color.parseColor("#000000"));
                if(cardViews[ID] != null && tag.equals(cardViews[ID].getTag())) cardViews[ID].setBackgroundColor(Color.parseColor("#000000"));
                break;
            case R.id.color_green:
                if(textViews[ID]!= null && tag.equals(textViews[ID].getTag())) textViews[ID].setTextColor(Color.parseColor("#8CCB5E"));
                if(cardViews[ID] != null && tag.equals(cardViews[ID].getTag())) cardViews[ID].setBackgroundColor(Color.parseColor("#8CCB5E"));
                break;
            case R.id.color_blue:
                if(textViews[ID]!= null && tag.equals(textViews[ID].getTag())) textViews[ID].setTextColor(Color.parseColor("#000080"));
                if(cardViews[ID] != null && tag.equals(cardViews[ID].getTag())) cardViews[ID].setBackgroundColor(Color.parseColor("#000080"));
                break;
            case R.id.color_red:
                if(textViews[ID]!= null && tag.equals(textViews[ID].getTag())) textViews[ID].setTextColor(Color.parseColor("#EE3B3B"));
                if(cardViews[ID] != null && tag.equals(cardViews[ID].getTag())) cardViews[ID].setBackgroundColor(Color.parseColor("#EE3B3B"));
                break;
            case R.id.color_gray:
                if(textViews[ID]!= null && tag.equals(textViews[ID].getTag())) textViews[ID].setTextColor(Color.parseColor("#BEBEBE"));
                if(cardViews[ID] != null && tag.equals(cardViews[ID].getTag())) cardViews[ID].setBackgroundColor(Color.parseColor("#BEBEBE"));
                break;
            case R.id.color_purple:
                if(textViews[ID]!= null && tag.equals(textViews[ID].getTag())) textViews[ID].setTextColor(Color.parseColor("#A020F0"));
                if(cardViews[ID] != null && tag.equals(cardViews[ID].getTag())) cardViews[ID].setBackgroundColor(Color.parseColor("#A020F0"));
                break;
            case R.id.color_orange:
                if(textViews[ID]!= null && tag.equals(textViews[ID].getTag())) textViews[ID].setTextColor(Color.parseColor("#FF8C00"));
                if(cardViews[ID] != null && tag.equals(cardViews[ID].getTag())) cardViews[ID].setBackgroundColor(Color.parseColor("#FF8C00"));
                break;

            //Standart_Colors--------------------------------------------

            case R.id.button_close:
                linearLayout1.setVisibility(View.GONE);
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

