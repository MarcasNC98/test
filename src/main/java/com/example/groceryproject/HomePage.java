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


public class HomePage extends AppCompatActivity {



    //FloatingActionButton class called fab_btn
    private FloatingActionButton fab_btn;
    //DatabaseReference class called newDatabase
    private DatabaseReference newDatabase;
    //FirebaseAuth class called newAuth
    private FirebaseAuth newAuth;
    RecyclerView recyclerView;
    ArrayList<Info> list;
    NewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocerylistapp);
        recyclerView = findViewById(R.id.main_list);

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewAdapter(this, list);
        recyclerView.setAdapter(adapter);

        //Returns an instance of FirebaseAuth and ties it to newAuth
        newAuth=FirebaseAuth.getInstance();
        //Creates a FirebaseUser class called newUser and ties it to newAuth.getCurrentUser that will retrieve the current users credentials
        FirebaseUser newUser=newAuth.getCurrentUser();



        //Creates a string called uId and ties it to newUser.getUid that will retrieve the users generated ID.
        String uId=newUser.getUid();
        //Returns an instance of FirebaseDatabase, references the child node "Grocery List" and the user ID in this node and assigns it to newDatabase
        newDatabase= FirebaseDatabase.getInstance("https://grocerylist-c678c-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Grocery List");
        //Assigns the Floating Action Button with the id of 'fab' from 'grocerylistapp.xml to fab_btn
        fab_btn=findViewById(R.id.fab);

            newDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Info info = dataSnapshot.getValue(Info.class);
                        list.add(info);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        //Creates an onClickListener that listens for the floating action button being clicked
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            //When clicked, the dialogBox view will be shown
            public void onClick(View view) {
                dialogBox();
            }
        });
    }

    //Dialog box for inputting grocery data
    private void dialogBox(){

        //Creates alert dialog on the homepage and assigns it to newDialog
        AlertDialog.Builder newDialog=new AlertDialog.Builder(HomePage.this);
        //Creates a layout inflater from HomePage
        LayoutInflater inflater=LayoutInflater.from(HomePage.this);
        //Inflates the 'input.xml' layout and assigns it to a view called newView
        View newView=inflater.inflate(R.layout.input,null);

        //Creates a new dialog box
        AlertDialog dialog=newDialog.create();
        //Sets this new dialog box to display newView aka the 'input.xml' layout
        dialog.setView(newView);

        //Assigns the field for a user to input the name of a grocery item with the ID input_text from 'input.xml' to the EditText text
        EditText text=newView.findViewById(R.id.input_text);
        //Assigns the field for a user to input the amount of a grocery item with the ID input_amount from 'input.xml' to the EditText amount
        EditText amount=newView.findViewById(R.id.input_amount);
        //Assigns the field for a user to input the price of a grocery item with the ID input_price from 'input.xml' to the EditText price
        EditText price=newView.findViewById(R.id.input_price);
        //Assigns the button that a user clicks to submit the data they've entered with the ID submit_btn from 'input.xml' to the Button submitBtn
        Button submitBtn=newView.findViewById(R.id.submit_btn);

        //Creates an onClickLister to listen for when the submitBtn is clicked
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //When clicked, a string called newText, newAmount and newPrice will be created. They will get the information from the EditText fields and when convert them to strings. When converted, trim will remove whitespice from before and after the data.
                String newText=text.getText().toString().trim();
                String newAmount=amount.getText().toString().trim();
                String newPrice=price.getText().toString().trim();

                //Because the EditText fields have been converted to strings, they now need to be converted into their respective data types. The amount field is converted into an Integer and named conAmount
                int conAmount=Integer.parseInt(newAmount);
                //The price field is converted to a double and named conPrice
                double conPrice=Double.parseDouble(newPrice);

                //Creates an error message if there is nothing entered in the text, amount or price fields that lets the user know nothing can be blank.
                if (TextUtils.isEmpty(newText)){
                    text.setError("Cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(newAmount)){
                    amount.setError("Cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(newPrice)){
                    price.setError("Cannot be blank");
                    return;
                }

                //String called id that pushes a key to the Firebase database
                String id=newDatabase.push().getKey();


                //Sends the date that the data was pushed
                String newDate= DateFormat.getDateInstance().format(new Date());

                //Ties the info entered in the input dialog box to the variables in info.java
                Info info=new Info(newDate, newText,conAmount,conPrice,id);

                //Sets the values in info to the id that is pushed to the database
                newDatabase.child(id).setValue(info)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //Toast message that informs the user that the grocery item has been added
                                    Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                //The input dialog box is dismissed
                dialog.dismiss();
            }
        });

        //Shows the input dialog box
        dialog.show();
    }
}