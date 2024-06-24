package com.example.ihbar;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class guncelSikayetlerActivity extends AppCompatActivity {

    private List<Ihbar> ihbarList;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guncelsikayetler);

        // Firebase'den verileri almak için referans oluştur
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ihbarlar");

        // Aktiflik durumu true olan şikayetleri çek
        Query query = databaseReference.orderByChild("aktiflikDurumu").equalTo(true);

        ihbarList = new ArrayList<>();
        linearLayout = findViewById(R.id.linearLayout);

        // Veritabanından verileri çek
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ihbarList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Ihbar ihbar = snapshot.getValue(Ihbar.class);
                    ihbarList.add(ihbar);
                }

                // Verileri göstermek için metodu çağır
                showData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(guncelSikayetlerActivity.this, "Veriler yüklenirken bir hata oluştu.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Verileri göstermek için metot
    private void showData() {
        Log.d("ShowData", "showData() metodu çalıştı.");
        // LinearLayout içerisine dinamik olarak layout ekleyerek verileri göster
        for (Ihbar ihbar : ihbarList) {
            // Yeni bir layout oluştur
            LinearLayout complaintLayout = new LinearLayout(this);
            complaintLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 0, 0, 20); // Boşluk eklemek için layout parametrelerini ayarla
            complaintLayout.setLayoutParams(layoutParams);
            complaintLayout.setBackgroundResource(R.drawable.rounded_corners); // Köşeleri yuvarlak yap ve arka plan olarak ayarla
            complaintLayout.setPadding(20, 20, 20, 20); // Kenar boşluğunu ayarla
            linearLayout.addView(complaintLayout);

            // Kullanıcı ID TextView'ini ekle
            TextView textViewUserId = new TextView(this);
            textViewUserId.setText("Kullanıcı ID: " + ihbar.getKullaniciId());
            textViewUserId.setTextSize(18); // Yazı boyutunu ayarla
            complaintLayout.addView(textViewUserId);

            // Şikayet Metni TextView'ini ekle
            TextView textViewComplaint = new TextView(this);
            textViewComplaint.setText("Şikayet Metni: " + ihbar.getSikayetMetni());
            textViewComplaint.setTextSize(18); // Yazı boyutunu ayarla
            complaintLayout.addView(textViewComplaint);

            // Şikayet metni textinin altına boşluk eklemek için bir TextView daha ekle
            TextView textViewEmptySpace = new TextView(this);
            textViewEmptySpace.setText(""); // Boş bir metin ekleyerek boşluğu oluştur
            textViewEmptySpace.setTextSize(10); // Boşluğun boyutunu ayarla
            complaintLayout.addView(textViewEmptySpace);

            // Resmi yükle ve ImageView'i ekle
            ImageView imageView = new ImageView(this);
            // Base64 formatındaki resmi byte dizisine çevir
            byte[] decodedString = Base64.decode(ihbar.getResimUrl(), Base64.DEFAULT);
            // Byte dizisinden resmi oluştur ve ImageView'e yükle
            Glide.with(this).load(decodedString).into(imageView);
            complaintLayout.addView(imageView);
        }
    }

}
