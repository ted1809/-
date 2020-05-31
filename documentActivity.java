package com.example.logindb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class documentActivity extends AppCompatActivity {
    private static final String TAG = "documentActivity";
    private ImageView imageView;
    private final int GET_GALLERY_IMAGE = 200;
    String photoURL;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        imageView = (ImageView)findViewById(R.id.docuImage);

        findViewById(R.id.checkButton).setOnClickListener(onClickLitsener);
        findViewById(R.id.docuImage).setOnClickListener(onClickLitsener);
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
                case R.id.docuImage:
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        }
    };

    private void infoUpdate(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();
        final StorageReference ImagesRef = storageRef.child("images/"+user.getEmail()+"_profile.jpg");

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = ImagesRef.putBytes(data);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return ImagesRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    String dname = ((EditText)findViewById(R.id.dnameEditText)).getText().toString();
                    String detail = ((EditText)findViewById(R.id.detailEditText)).getText().toString();
                    Uri downloadUri = task.getResult();
                    Log.d(TAG, "SUCCESS"+ downloadUri);

                    if(dname.length() > 0){
                        if(detail.length() == 0){
                            detail = "없음";
                        }

                        Intent intent = getIntent();
                        documentInfo goodsRef = (documentInfo) intent.getSerializableExtra("goodsRef");
                        String goodsRfid = intent.getExtras().getString("goodsRfid");
                        memberinfo memberRef = (memberinfo) intent.getSerializableExtra("memberRef");

                        documentInfo documentInfo = new documentInfo(get_docu_boxnum(goodsRef), get_user_name(memberRef),
                                dname, detail, false, downloadUri.toString());

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

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            /*try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                imageView.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
             */
            imageView.setImageURI(selectedImageUri);
        }

    }


    public String get_docu_boxnum(documentInfo DI){
        return DI.getBoxnum();
    }

    public String get_user_name(memberinfo user){
        return user.getName();
    }


    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

