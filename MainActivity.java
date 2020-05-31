package com.example.logindb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    memberinfo sendmember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        if (user == null) {
            startActivity(signUpActivity.class);
        }else{
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(user.getEmail());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if(document != null){
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                             }
                            else {
                                Log.d(TAG, "No such document");
                                startActivity(memberActivity.class);
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    memberinfo memberinfo = documentSnapshot.toObject(memberinfo.class);
                    sendpack(memberinfo);
                }
            });
        }

        findViewById(R.id.logout).setOnClickListener(onClickListener);
        findViewById(R.id.documentButton).setOnClickListener(onClickListener);
        findViewById(R.id.memberchangeButton).setOnClickListener(onClickListener);
        findViewById(R.id.logLookupButton).setOnClickListener(onClickListener);

    }

    private void sendpack(memberinfo memberinfo){
        sendmember = new memberinfo(memberinfo);
        Log.d(TAG, memberinfo.getName());
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            switch ((v.getId())){
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(signUpActivity.class);
                    finish();
                    break;
                case R.id.documentButton:
                    startDActivity(selectdocumentActivity.class);
                    break;
                case R.id.memberchangeButton:
                    startDActivity(memberchangeActivity.class);
                    break;
                case R.id.logLookupButton:
                    startDActivity(logLookupActivity.class);
                    break;
            }
        }
    };

    private void startActivity(Class c){
        Intent intent=new Intent(this,c);
        startActivity(intent);
    }

    private void startDActivity(Class c){
        Intent intent=new Intent(this,c);
        intent.putExtra("memberRef", sendmember);
        startActivity(intent);
    }
}