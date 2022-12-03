package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    private Button btnReturn;
    private ImageView imgDetail;
    private TextView tvNameDetail;
    private TextView tvDescriptionDetail;
    private TextView tvPriceDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tvNameDetail = (TextView) findViewById(R.id.tvNameDetail);
        tvDescriptionDetail = (TextView) findViewById(R.id.tvDescriptionDetail);
        tvPriceDetail = (TextView) findViewById(R.id.tvPriceDetail);
        imgDetail = (ImageView) findViewById(R.id.imgDetail);
        btnReturn = (Button) findViewById(R.id.btnReturn);

        Intent intent = getIntent();
        tvNameDetail.setText(intent.getStringExtra("name"));
        tvDescriptionDetail.setText(intent.getStringExtra("description"));
        tvPriceDetail.setText(intent.getStringExtra("price"));
        imgDetail.setImageResource(intent.getIntExtra("image",R.drawable.ic_launcher_background));

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(intent1);
            }
        });
    }
}