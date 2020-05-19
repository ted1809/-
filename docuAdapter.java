package com.example.logindb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class docuAdapter extends RecyclerView.Adapter<docuAdapter.CustomViewHolder> {

    private ArrayList<documentInfo> arrayList;
    private Context context;

    public docuAdapter(ArrayList<documentInfo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contents, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.docuName.setText(arrayList.get(position).getGoodsName());
        holder.docuDetail.setText(arrayList.get(position).getDetail());
    }

    @Override
    public int getItemCount() {
        //삼항연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView docuName;
        TextView docuDetail;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.docuName = itemView.findViewById((R.id.docuName));
            this.docuDetail = itemView.findViewById((R.id.docuDetail));
        }
    }
}
