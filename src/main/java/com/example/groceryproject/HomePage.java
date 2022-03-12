package com.example.groceryproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.groceryproject.Data.Info;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomePage extends AppCompatActivity {

    //FloatingActionButton class called fab_btn
    private FloatingActionButton fab_btn;
    //DatabaseReference class called newDatabase
    private DatabaseReference newDatabase;
    //FirebaseAuth class called newAuth
    private FirebaseAuth newAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocerylistapp);

        //Returns an instance of FirebaseAuth and ties it to newAuth
        newAuth=FirebaseAuth.getInstance();
        //Creates a FirebaseUser class called newUser and ties it to newAuth.getCurrentUser that will retrieve the current users credentials
        FirebaseUser newUser=newAuth.getCurrentUser();
        //Creates a string called uId and ties it to newUser.getUid that will retrieve the users generated ID.
        String uId=newUser.getUid();
        //Returns an instance of FirebaseDatabase, references the child node "Grocery List" and the user ID in this node and assigns it to newDatabase
        newDatabase= FirebaseDatabase.getInstance("https://grocerylist-c678c-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Grocery List").child(uId);
        //Assigns the Floating Action Button with the id of 'fab' from 'grocerylistapp.xml to fab_btn
        fab_btn=findViewById(R.id.fab);

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
        //Creates alert dialog on the homepage and assigns it to newdialog
        AlertDialog.Builder newdialog=new AlertDialog.Builder(HomePage.this);
        //Creates a layout inflater from HomePage
        LayoutInflater inflater=LayoutInflater.from(HomePage.this);
        //Inflates the 'input.xml' layout and assigns it to a view called newview
        View newview=inflater.inflate(R.layout.input,null);

        //Creates a new dialog box
        AlertDialog dialog=newdialog.create();
        //Sets this new dialog box to display newview aka the 'input.xml' layout
        dialog.setView(newview);

        //Assigns the field for a user to input the name of a grocery item with the ID input_text from 'input.xml' to the EditText inputText
        EditText inputText=newview.findViewById(R.id.input_text);
        //Assigns the field for a user to input the amount of a grocery item with the ID input_amount from 'input.xml' to the EditText amount
        EditText amount=newview.findViewById(R.id.input_amount);
        //Assigns the field for a user to input the price of a grocery item with the ID input_price from 'input.xml' to the EditText price
        EditText price=newview.findViewById(R.id.input_price);
        //Assigns the button that a user clicks to submit the data they've entered with the ID submit_btn from 'input.xml' to the Button submitBtn
        Button submitBtn=newview.findViewById(R.id.submit_btn);

        //Creates an onClickLister to listen for when the submitBtn is clicked
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //When clicked, a string called newText, newAmount and newPrice will be created. They will get the information from the EditText fields and when convert them to strings. When converted, trim will remove whitespice from before and after the data.
                String newText=inputText.getText().toString().trim();
                String newAmount=amount.getText().toString().trim();
                String newPrice=price.getText().toString().trim();

                //Because the EditText fields have been converted to strings, they now need to be converted into their respective data types. The amount field is converted into an Integer and named conAmount
                int conAmount=Integer.parseInt(newAmount);
                //The price field is converted to a double and named conPrice
                double conPrice=Double.parseDouble(newPrice);

                //Creates an error message if there is nothing entered in the text, amount or price fields that lets the user know nothing can be blank.
                if (TextUtils.isEmpty(newText)){
                    inputText.setError("Cannot be blank");
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

                //Ties the info entered in the input dialog box to the variables in info.java
                Info info=new Info(newText,conAmount,conPrice,id);

                //Sets the values in info to the id that is pushed to the database
                newDatabase.child(id).setValue(info);

                //Toast message that informs the user that the grocery item has been added
                Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT).show();

                //The input dialog box is dismissed
                dialog.dismiss();

            }
        });

        //Shows the input dialog box
        dialog.show();
    }

}