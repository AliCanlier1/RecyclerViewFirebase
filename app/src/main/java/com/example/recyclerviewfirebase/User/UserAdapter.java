package com.example.recyclerviewfirebase.User;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewfirebase.IRestaurantInterface;
import com.example.recyclerviewfirebase.MenuActivity;
import com.example.recyclerviewfirebase.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{
    Context context;
    ArrayList<User> userArrayList;
    public UserAdapter(Context context, ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.useritem,parent,false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.name.setText(user.getName());
        holder.surname.setText(user.getSurname());
        holder.age.setText(user.getAge());
    }
    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, surname, age;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            surname = itemView.findViewById(R.id.txtSurname);
            age = itemView.findViewById(R.id.txtAge);
        }
    }
}
