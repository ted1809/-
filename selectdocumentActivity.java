package com.example.logindb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class selectdocumentActivity extends AppCompatActivity {
    private static final String TAG = "selectdocumentActivity";
    TextView boxnumTextView,userIDTextView, goodsNameTextView, detailTextView; //물건에 따라 이름을 바꿀 텍스트뷰
    documentInfo DI; //물건객체
    Spinner goodsSpinner; // 물건 RFID 목록을 띄울 스피너
    ArrayList<String> GNarray = new ArrayList<>();  //물건 RFID 이름 목록을 넣을 리스트객체
    String documentRFID;    //물건 RFID를 넣을 문자열

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_document);

        findViewById(R.id.checkButton).setOnClickListener(onClickLitsener);
        findViewById(R.id.backbutton).setOnClickListener(onClickLitsener);

        makeArray(); // 물건 RFID 리스트를 만들어주는 함수

        goodsSpinner = (Spinner)findViewById(R.id.goodsSpinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, GNarray);
        goodsSpinner.setAdapter(arrayAdapter);  // 스피너 연결

        boxnumTextView = (TextView)findViewById(R.id.boxnumTextView);
        userIDTextView = (TextView)findViewById(R.id.userIDTextView);
        goodsNameTextView = (TextView)findViewById(R.id.goodsNameTextView);
        detailTextView = (TextView)findViewById(R.id.detailTextView); // 각 텍스트뷰를 연결

        goodsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { // 스피너가 선택되었을 때 이벤트 설정
                documentRFID = (String)goodsSpinner.getSelectedItem();
                Log.i(TAG, "Spinner selected item = "+documentRFID);
                db_item(documentRFID);

                boxnumTextView.setText(get_docu_boxnum());
                userIDTextView.setText(get_docu_userID());
                goodsNameTextView.setText(get_docu_goodsName());
                detailTextView.setText(get_docu_detail());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    View.OnClickListener onClickLitsener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.checkButton:
                    Log.e("클릭", "클릭");
                    startDActivity(documentActivity.class);
                    break;
                case R.id.backbutton:
                    finish();
                    break;
            }
        }
    };

    private void makeArray(){
        db.collection("goods")
                .whereEqualTo("userID", "none")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                GNarray.add(document.getId());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void db_item(String id){
        DocumentReference docRef = db.collection("goods").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                DI = documentSnapshot.toObject(documentInfo.class);
            }
        });
    }

    public int get_docu_boxnum(){
        return DI.getBoxnum();
    }
    public String get_docu_userID(){
        return DI.getUserID();
    }
    public String get_docu_goodsName(){
        return DI.getGoodsName();
    }
    public String get_docu_detail(){
        return DI.getDetail();
    }

    private void startDActivity(Class c){
        Intent intent=new Intent(this,c);
        intent.putExtra("documentINFO",DI);
        Log.d(TAG, "documentINFO: "+ DI.getBoxnum());
        intent.putExtra("goodsRFID", documentRFID);
        startActivity(intent);
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

