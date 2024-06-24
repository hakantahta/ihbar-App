package com.example.ihbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private EditText editText;
    private Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        imageView = findViewById(R.id.img_ihbarresim);
        Button btn_resimsec = findViewById(R.id.btn_resimsec);
        Button btn_ihbar = findViewById(R.id.btn_ihbar);
        Button btn_guncelsikayet = findViewById(R.id.btn_guncelsikayet);
        editText=findViewById(R.id.edittxt_sikayet);

        btn_resimsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        btn_ihbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ihbarEt();
            }
        });

        // "Güncel Şikayetler" butonuna OnClickListener ekleyelim
        btn_guncelsikayet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yeni aktiviteye geçiş için Intent oluşturalım ve başlatalım
                Intent intent = new Intent(MainActivity.this, guncelSikayetlerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void ihbarEt() {
        String sikayetMetni = editText.getText().toString().trim(); // Şikayet metnini al

        if (!sikayetMetni.isEmpty() && selectedImage != null) { // Hem metin hem de resim seçili ise devam et
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();
            String resimUrl = Base64.encodeToString(imageData, Base64.DEFAULT);

            String kullaniciId = "user123"; // Kullanıcı kimliği
            boolean aktiflikDurumu = true; // Varsayılan olarak aktif olsun

            Ihbar ihbar = new Ihbar(kullaniciId, sikayetMetni, resimUrl, aktiflikDurumu); // Aktiflik durumunu ihbara ekle

            // Firebase'e gönder
            String ihbarKey = mDatabase.child("ihbarlar").push().getKey(); // Yeni bir ihbar için benzersiz bir key al
            mDatabase.child("ihbarlar").child(ihbarKey).setValue(ihbar);

            Toast.makeText(MainActivity.this, "İhbar başarıyla gönderildi.", Toast.LENGTH_SHORT).show();
        } else if (sikayetMetni.isEmpty()) {
            Toast.makeText(MainActivity.this, "Lütfen bir şikayet metni girin.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Lütfen bir resim seçin.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            selectedImage = (Bitmap) extras.get("data");
            imageView.setImageBitmap(selectedImage);
        }
    }
}