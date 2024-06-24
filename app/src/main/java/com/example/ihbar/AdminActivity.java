package com.example.ihbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AdminActivity extends AppCompatActivity {

    private Button btnAddUser, btnViewUsers, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);

        // Fragment Manager'ı al
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Butonları bul
        btnAddUser = findViewById(R.id.btnAddUser);
        btnViewUsers = findViewById(R.id.btnViewUsers);
        btnLogout = findViewById(R.id.btnLogout);

        // Kullanıcı Ekle butonuna tıklama işlemi
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // UserAddFragment'ı yükle
                UserAddFragment userAddFragment = new UserAddFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, userAddFragment);
                fragmentTransaction.commit();
            }
        });

        // Kullanıcıları Görüntüle butonuna tıklama işlemi
        btnViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // KullanıcılarFragment'ı yükle
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                UsersFragment usersFragment = new UsersFragment();
                fragmentTransaction.replace(R.id.fragment_container, usersFragment);
                fragmentTransaction.commit();
            }
        });



        // Çıkış Yap butonuna tıklama işlemi
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Çıkış yap
                // Burada çıkış işlemi yapılacak, örneğin FirebaseAuth.getInstance().signOut() gibi
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
