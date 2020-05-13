package com.bookworm.bookworm;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bookworm.bookworm.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    String email;

    TextView userName;
    TextView emailtxt;
    EditText phone;
    EditText pincode;
    ImageButton editpin;
    ImageButton editphone;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        userName = findViewById(R.id.username);
        emailtxt = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        pincode = findViewById(R.id.pincode);

        editphone = findViewById(R.id.editPhone);
        editphone.setTag(R.drawable.ic_edit_24px);

        editpin = findViewById(R.id.editPin);
        editpin.setTag(R.drawable.ic_edit_24px);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = firebaseUser.getEmail().replaceAll("[.]", "_");
        reference = FirebaseDatabase.getInstance().getReference("users").child(email).child("details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userName.setText(user.getFullname());
                emailtxt.setText(user.getEmail());
                phone.setText(user.getPhone());
                pincode.setText(user.getPincode());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer resource = (Integer) editpin.getTag();

                if (resource == R.drawable.ic_edit_24px) {
                    pincode.setEnabled(true);
                    pincode.setBackgroundColor(getColor(android.R.color.holo_purple));
                    editpin.setImageResource(R.drawable.ic_done_24px);
                    editpin.setTag(R.drawable.ic_done_24px);
                } else {
                    String pn = pincode.getText().toString();
                    updateProfile("pincode", pn);
                    pincode.setEnabled(false);
                    pincode.setBackgroundColor(getColor(android.R.color.transparent));
                    editpin.setImageResource(R.drawable.ic_edit_24px);
                    editpin.setTag(R.drawable.ic_edit_24px);
                }

//                editpin.setBackgroundResource(R.drawable.ic_done_24px);
            }
        });

        editphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer resource = (Integer) editphone.getTag();

                if (resource == R.drawable.ic_edit_24px) {
                    phone.setEnabled(true);
                    phone.setBackgroundColor(getColor(android.R.color.holo_purple));
                    editphone.setImageResource(R.drawable.ic_done_24px);
                    editphone.setTag(R.drawable.ic_done_24px);
                } else {
                    String ph = phone.getText().toString();
                    updateProfile("phone", ph);
                    phone.setEnabled(false);
                    phone.setBackgroundColor(getColor(android.R.color.transparent));
                    editphone.setImageResource(R.drawable.ic_edit_24px);
                    editphone.setTag(R.drawable.ic_edit_24px);
                }

            }
        });

    }

    private void updateProfile(String fParam_name, String fParam_value) {

        reference.child(fParam_name).setValue(fParam_value);

    }

    ;

}
