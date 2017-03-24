package com.development.kernel.draft;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.squareup.picasso.Picasso;


class ViewInspector {

    private LayoutParams viewParams = null;
    private LinearLayout linearLayout = null;

    private int buttonsId = 0;
    private int CountButtonsTags = 0;

    private int CountTextViewTags = 0;
    private int textViewsId = 0;

    private int CountImageViewTags =0;
    private int imageViewsId = 0;

    private int CountCardViewTags = 0;
    private int cardViewsId = 0;

    private int WIS;
    private MainActivity mainActivity;

    private int countOfLinearLayouts = 0;
    private LinearLayout[] linearLayouts;

    ViewInspector(LayoutParams layoutParams, LinearLayout linearLayout, LinearLayout[] linearLayouts, MainActivity mainActivity) { //инициализация обязательных параметров
        this.viewParams = layoutParams;
        this.linearLayout = linearLayout;
        this.mainActivity = mainActivity;
        this.linearLayouts = linearLayouts;

    }

    Button setDefaultViewOptions(Button button) {  //назначает начальные настройки кнопке и тексту перегружен (2)
        button.setText("New button");
        button.setLayoutParams(viewParams);
        button.setId(buttonsId);//теги и id для массива кнопок
        button.setBackgroundColor(Color.parseColor("#ff6347"));
        button.setTextColor(Color.parseColor("#ffffff"));
        button.setMinimumWidth(400);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setStateListAnimator(null);
            button.setElevation(10);
            button.setTranslationZ(10);
        }
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

    ImageView setDefaultViewOptions(ImageView imageView) {
        imageView.setLayoutParams(viewParams);
        linearLayout.addView(imageView);
        imageView.setMinimumWidth(300);
        imageView.setMinimumHeight(300);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setStateListAnimator(null);
            imageView.setElevation(10);
            imageView.setTranslationZ(10);
        }
        Picasso.with(mainActivity).load(R.drawable.add_image).resize(1080,608).into(imageView);
        String imageViewsTags = "ImageView:";
        imageView.setId(imageViewsId);
        imageView.setTag(imageViewsTags + String.valueOf(CountImageViewTags++));
        imageViewsId++;
        return imageView;
    }

    CardView setDefaultViewOptions(CardView cardView)
    {
        cardView.setLayoutParams(viewParams);
        linearLayout.addView(cardView);
        cardView.setMinimumWidth(1080);
        cardView.setMinimumHeight(500);

        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(mainActivity);
        linearLayouts[countOfLinearLayouts] = new LinearLayout(mainActivity);
        linearLayouts[countOfLinearLayouts].setOrientation(LinearLayout.HORIZONTAL);
        linearLayouts[countOfLinearLayouts].setLayoutParams(layoutParams1);
        linearLayouts[countOfLinearLayouts].setId(CountCardViewTags);
        horizontalScrollView.addView(linearLayouts[countOfLinearLayouts]);
        cardView.addView(horizontalScrollView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cardView.setElevation(10f);
        }
        countOfLinearLayouts++;
        cardView.setId(cardViewsId);
        cardView.setTag(CountCardViewTags++);
        cardViewsId++;

        return cardView;
    }

    void setContextOptions(MenuItem item)//запись выбранного элемента в контекстном меню в переменную
    {
        WIS = item.getItemId();
    }
    private static final int NOTIFY_ID = 101;

    public void startNotification(){ //TODO: реализовать использование этой функции
        Context context = mainActivity.getApplicationContext();

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_done_white_64dp_2x)
                // большая картинка
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
                .setTicker("Последнее китайское предупреждение!")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                .setContentTitle("Напоминание")
                //.setContentText(res.getString(R.string.notifytext))
                .setContentText("Пора покормить кота"); // Текст уведомления

        // Notification notification = builder.getNotification(); // до API 16
        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);
    }

    Button setPropertiesForView(Button button, EditText editText) //настройки для кнопки
    {
        switch (WIS)
        {
            case 1: //изменить ширину
                try {
                    button.setWidth(Integer.valueOf(editText.getText().toString()));
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity,"Неверный формат ввода",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2: //изменить высоту
                try {
                    button.setHeight(Integer.valueOf(editText.getText().toString()));
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity,"Неверный формат ввода",Toast.LENGTH_SHORT).show();
                }
                break;
            case 3: //изменить название
                button.setText(editText.getText().toString());
                break;
            case 4: //удалить view элемент с экрана
                CountButtonsTags--; //понизить тег, чтобы предотратить ошибку
                break;
        }
        return button; //возвратить view элемент со всеми настройками
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    TextView setPropertiesForView(TextView textView, EditText editText, Button buttonEdit)//настройки для строки
    {
        switch (WIS)
        {
            case 1:
                textView.setText(editText.getText().toString());
                break;
            case 2:
                try {
                    editText.setInputType(InputType.TYPE_CLASS_PHONE);
                    textView.setTextSize(Integer.valueOf(editText.getText().toString()));
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity, "Неверный формат ввода", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                MainActivity m = new MainActivity();
                Display display = m.getWindowManager().getDefaultDisplay();
                int width = display.getWidth();  // deprecated
                int height = display.getHeight();  // deprecated
                textView.setMinimumWidth(width);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setText(textView.getText().toString());
                break;
            case 4:
                try {
                textView.setTextColor(Color.parseColor(editText.getText().toString()));
                }
                catch (Exception e){
                    Toast.makeText(mainActivity,"Невверный формат ввода", Toast.LENGTH_SHORT).show();
                }
                break;
            case 5:
                CountTextViewTags--;//понижение тега
                break;
        }
        editText.setVisibility(View.GONE);
        buttonEdit.setVisibility(View.GONE);
        return textView; //возвратить view элемент со всеми настройками
    }

    ImageView setPropertiesForView(ImageView imageView, Uri selectImage, EditText editText, EditText editText1) //настройки для строки
    {
        switch (WIS)
        {

            case 3:
                try {

                    Picasso.with(mainActivity).load(selectImage).resize(Integer.valueOf(editText1.getText().toString()), Integer.valueOf(editText.getText().toString())).into(imageView);
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity,"Неверный формат ввода", Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                CountImageViewTags--;//понижение тега
                break;
        }
        return imageView; //возвратить view элемент со всеми настройками
    }
    ImageView setPropertiesForView(ImageView imageView) //настройки для строки
    {
        switch (WIS)
        {

            case 1:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                mainActivity.startActivityForResult(photoPickerIntent, 1);
                break;
            case 2:
                Intent photoPickerIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mainActivity.startActivityForResult(photoPickerIntent1, 2);
                break;
        }
        return imageView; //возвратить view элемент со всеми настройками
    }

    CardView setPropertiesForView(CardView cardView, ImageView[] imageViews, EditText editText, int ID, LinearLayout linearLayout) //настройки для строки
    {
        switch (WIS)
        {
            case 1:

                    linearLayouts[ID].setOrientation(LinearLayout.HORIZONTAL);
                    linearLayouts[ID].addView(imageViews[ID] = new ImageView(mainActivity));
                    Picasso.with(mainActivity).load(R.drawable.rectangle).resize(360, 360).into(imageViews[ID]);
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    mainActivity.startActivityForResult(photoPickerIntent, 3);
                    linearLayout.setVisibility(View.GONE);
                break;
            case 2:
                    linearLayouts[ID].setOrientation(LinearLayout.VERTICAL);
                    linearLayouts[ID].addView(new CheckBox(mainActivity));
                break;
            case 3:
                try {
                    cardView.setBackgroundColor(Color.parseColor(editText.getText().toString()));
                }
                catch (Exception e){
                    Toast.makeText(mainActivity,"Неверный формат ввода", Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                CountImageViewTags--;//понижение тега
                break;
        }
        return cardView; //возвратить view элемент со всеми настройками
    }

}