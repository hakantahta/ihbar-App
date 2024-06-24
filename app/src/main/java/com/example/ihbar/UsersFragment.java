package com.example.ihbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersFragment extends Fragment {

    private LinearLayout usersLayout;
    private DatabaseReference databaseReference;
    private ArrayList<User> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        // LinearLayout tanımlaması
        usersLayout = view.findViewById(R.id.usersLayout);

        // FirebaseDatabase referansı al
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Kullanıcı listesini oluştur
        userList = new ArrayList<>();

        // Kullanıcıları oku ve listele
        readUsers();

        return view;
    }

    private void readUsers() {
        // Firebase'den kullanıcıları bir kere oku
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear(); // Kullanıcı listesini temizle

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Her bir kullanıcı için verileri al
                    String username = dataSnapshot.child("username").getValue().toString();
                    String password = dataSnapshot.child("password").getValue().toString();
                    String status = dataSnapshot.child("status").getValue().toString();

                    // Yeni bir User nesnesi oluştur
                    User user = new User(username, password, status);

                    // Kullanıcıyı listeye ekle
                    userList.add(user);
                }

                // Listeyi ekranda göster
                displayUsers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Veritabanı okuma hatası
            }
        });
    }

    private void displayUsers() {
        // Önceki kullanıcıları temizle
        usersLayout.removeAllViews();

        // Her bir kullanıcı için TextView oluştur ve ekrana ekle
        for (User user : userList) {
            TextView textView = new TextView(getContext());
            textView.setText("Username: " + user.getUsername() + "\nPassword: " + user.getPassword() + "\nStatus: " + user.getStatus());
            textView.setPadding(20, 20, 20, 20);
            usersLayout.addView(textView);
        }
    }
}

