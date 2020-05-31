package com.example.logindb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class logLookupActivity extends AppCompatActivity {
    private static final String TAG = "logLookupActivity";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<logInfo> logList;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        Intent intent = getIntent();
        final memberinfo memberRef = (memberinfo)intent.getSerializableExtra("memberRef");
        Log.d(TAG, " => " + get_member_boxnum(memberRef));


        recyclerView = findViewById(R.id.logRecyclerView);
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager((this));
        recyclerView.setLayoutManager(layoutManager);
        logList = new ArrayList<>();

        logList.clear();

        db.collection("inOutList")
                .whereEqualTo("boxnum", get_member_boxnum(memberRef))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                DocumentReference docRef = db.collection("inOutList").document(document.getId());
                                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        RawLogInfo RawLog = documentSnapshot.toObject(RawLogInfo.class);
                                        if (RawLog != null) {
                                            logInfo log = new logInfo(document.getId(),get_log_goodsName(RawLog), get_log_inOut(RawLog));
                                            Log.d(TAG, " => " + get_log_logTime(log));
                                            logList.add(log);
                                            adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        adapter = new logAdapter(logList, this);
        recyclerView.setAdapter(adapter);

    }
    private String get_member_boxnum(memberinfo memberinfo){
        return memberinfo.getBoxnum();
    }

    private String get_log_goodsName(RawLogInfo rawLogInfo){
        return rawLogInfo.getGoodsName();
    }
    private boolean get_log_inOut(RawLogInfo rawLogInfo){
        return rawLogInfo.isInOut();
    }

    private String get_log_logTime(logInfo loginfo){
        return loginfo.getLogTime();
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

