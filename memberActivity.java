package com.example.logindb;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class memberActivity extends AppCompatActivity {
    private static final String TAG = "memberActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_init);

        findViewById(R.id.checkButton).setOnClickListener(onClickLitsener);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }


    View.OnClickListener onClickLitsener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.checkButton:
                    Log.e("클릭", "클릭");
                    profileUpdate();
                    break;
            }
        }
    };

    private void profileUpdate(){
        String name = ((EditText)findViewById(R.id.nameeditText)).getText().toString();
        String phone = ((EditText)findViewById(R.id.phoneeditText)).getText().toString();
        String date = ((EditText)findViewById(R.id.dateeditText)).getText().toString();
        String address = ((EditText)findViewById(R.id.addresseditText)).getText().toString();

        if(name.length() > 0 && phone.length() > 9 && date.length() > 5 && address.length()>0){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            memberinfo memberinfo = new memberinfo(name, phone, date, address);

            if (user != null) {
                db.collection("users").document(user.getUid()).set(memberinfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("회원정보 등록을 성공하였습니다.");
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startToast("회원정보 등록 실패");
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        }else{
            startToast("회원정보를 입력해주세요.");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}

