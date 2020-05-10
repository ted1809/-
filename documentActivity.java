package com.example.logindb;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class documentActivity extends AppCompatActivity {
    private static final String TAG = "documentActivity";
    memberinfo memberinfo;
    Spinner boxIDspinner;
    ArrayList<String> BNarray = new ArrayList<>();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        findViewById(R.id.checkButton).setOnClickListener(onClickLitsener);

        /*db.collection("locker")
                .whereEqualTo("userID", "none")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                BNarray.add(document.getId());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });*/
    }


    View.OnClickListener onClickLitsener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.checkButton:
                    Log.e("클릭", "클릭");
                    infoUpdate();
                    finish();
                    break;
            }
        }
    };

    private void infoUpdate(){
        String dname = ((EditText)findViewById(R.id.dnameEditText)).getText().toString();
        String detail = ((EditText)findViewById(R.id.detailEditText)).getText().toString();



        if(dname.length() > 0){
            /*DocumentReference docRef = db.collection("users").document(user.getEmail());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    memberinfo = documentSnapshot.toObject(memberinfo.class);
                }
            });*/

            if(detail.length() == 0){
                detail = "없음";
            }

            documentInfo GETdocumentInfo = (documentInfo)getIntent().getSerializableExtra("documentINFO");
            String docuRFID = (String)getIntent().getSerializableExtra("goodsRFID");

            documentInfo documentInfo = new documentInfo(get_docu_boxnum(GETdocumentInfo), get_docu_userID(GETdocumentInfo), dname, detail, false);

            db.collection("goods").document(docuRFID).set(documentInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startToast("물품 정보 갱신에 성공하였습니다");
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            startToast("물품 등록 실패");
                            Log.w(TAG, "Error adding document", e);
                        }
                    });


        }else{
            startToast("물건의 이름은 정해주어야 합니다.");
        }
    }

    public int get_docu_boxnum(documentInfo DI){
        return DI.getBoxnum();
    }
    public String get_docu_userID(documentInfo DI){
        return DI.getUserID();
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

