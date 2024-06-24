package com.example.ihbar;

import static com.example.ihbar.R.id.edtUsername;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAddFragment extends Fragment {

    private EditText edtUsername, edtPassword, edtStatus;
    private Button btnAddUser;

    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_add, container, false);

        // EditText ve Button bileşenlerini tanımlama
        edtUsername = view.findViewById(R.id.edtUsername);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtStatus = view.findViewById(R.id.edtStatus);
        btnAddUser = view.findViewById(R.id.btnAddUser);

        // Firebase veritabanı referansı al
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Kullanıcı ekle butonuna tıklama işlemi
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        return view;
    }

    private void addUser() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String status = edtStatus.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Kullanıcı adı ve şifre alanları doldurulmalıdır.", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(username, password, status);
        databaseReference.child(username).setValue(user);

        Toast.makeText(getActivity(), "Kullanıcı başarıyla eklendi.", Toast.LENGTH_SHORT).show();

        // Yeni kullanıcı eklendikten sonra UsersFragment'ı yükle
        UsersFragment usersFragment = new UsersFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, usersFragment);
        transaction.commit();
    }

}
