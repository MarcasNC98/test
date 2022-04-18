package com.example.groceryproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.groceryproject.Data.Info;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChooseHouseActivity extends AppCompatActivity {

    private Button createButton;
    private Button joinButton;
    private FirebaseDatabase newDatabase;
    private DatabaseReference newReference;
    String houseID;
    private FirebaseAuth newAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_house);

        createButton=findViewById(R.id.createHouse);
        joinButton=findViewById(R.id.joinHouse);
        newDatabase= FirebaseDatabase.getInstance("https://grocerylist-c678c-default-rtdb.europe-west1.firebasedatabase.app/");
        newReference= newDatabase.getReference();
        //Returns an instance of FirebaseAuth and ties it to newAuth
        newAuth=FirebaseAuth.getInstance();
        //Creates a FirebaseUser class called newUser and ties it to newAuth.getCurrentUser that will retrieve the current users credentials
        FirebaseUser newUser=newAuth.getCurrentUser();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //When clicked, the dialogBox view will be shown
            public void onClick(View view) {
                houseID=newReference.push().getKey();
                newReference.child("Homes").child(houseID).child("blank").setValue("");

                String uId=newUser.getUid();
                newReference.child("NewUsers").child(uId).child("home").setValue(houseID);
            }
        });



    }


}