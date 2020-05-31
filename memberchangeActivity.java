package com.example.logindb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class memberchangeActivity extends AppCompatActivity {
    private static final String TAG = "memberchangeActivity";
    private FirebaseAuth mAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_change);

        mAuth = FirebaseAuth.getInstance();                                         //유저 객체를 받아온다

        findViewById(R.id.checkButton).setOnClickListener(onClickLitsener);
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    View.OnClickListener onClickLitsener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.checkButton:
                    Log.e("클릭", "클릭");
                    memberChange();
                    startToast("정보변경 완료");
                    finish();
                    break;
            }
        }
    };

    private void memberChange(){
        String name = ((EditText)findViewById(R.id.nameEditText)).getText().toString();
        String phone = ((EditText)findViewById(R.id.phoneEditText)).getText().toString();

        if(name.length() > 0 && phone.length() > 0){
            DocumentReference memberRef = db.collection("users").document(user.getEmail());
            memberRef.update("name", name);
            memberRef.update("phoneNumber", phone);
        }else{
            startToast("변경할 이름과 전화번호를 입력해주세요");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(Class c){
        Intent intent=new Intent(this,c);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
