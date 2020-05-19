package com.example.logindb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class documentActivity extends AppCompatActivity {
    private static final String TAG = "documentActivity";

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
            /*String docuRFID = (String)getIntent().getSerializableExtra("goodsRFID");

            DocumentReference docRef = db.collection("goods").document(docuRFID);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    DI = documentSnapshot.toObject(documentInfo.class);
                }
            });*/

            if(detail.length() == 0){
                detail = "없음";
            }

            Intent intent = getIntent();
            documentInfo goodsRef = (documentInfo) intent.getSerializableExtra("goodsRef");
            String goodsRfid = intent.getExtras().getString("goodsRfid");
            memberinfo memberRef = (memberinfo) intent.getSerializableExtra("memberRef");

            documentInfo documentInfo = new documentInfo(get_docu_boxnum(goodsRef), get_user_name(memberRef), dname, detail, false);

            db.collection("goods").document(goodsRfid).set(documentInfo)
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



    public String get_docu_boxnum(documentInfo DI){
        return DI.getBoxnum();
    }

    public String get_user_name(memberinfo user){
        return user.getName();
    }

    private void startDActivity(Class c, memberinfo a){
        Intent intent=new Intent(this,c);
        intent.putExtra("memberclass", a);
        startActivity(intent);
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

