package by.test2rest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class BigImageActivity extends AppCompatActivity{

    private ImageView imageViewBig;
    private TextView textViewBig;
    private String urlImage, textBody;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);

        intent = getIntent();
        urlImage = intent.getStringExtra("url");
        textBody = intent.getStringExtra("text");

        imageViewBig = findViewById(R.id.imageViewBig);
        textViewBig = findViewById(R.id.textViewBig);

        textViewBig.setText(textBody);

        Glide.with(this)
                .load(urlImage)
                .into(imageViewBig);
    }
}
