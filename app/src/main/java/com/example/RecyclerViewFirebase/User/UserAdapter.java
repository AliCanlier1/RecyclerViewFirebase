package com.example.RecyclerViewFirebase.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.RecyclerViewFirebase.IRestaurantInterface;
import com.example.RecyclerViewFirebase.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>implements IRestaurantInterface{
    IRestaurantInterface iRestaurantInterface;
    Context context;
    ArrayList<User> userArrayList;
    public UserAdapter(Context context, ArrayList<User> userArrayList,IRestaurantInterface iRestaurantInterface) {
        this.userArrayList = userArrayList;
        this.context = context;
        this.iRestaurantInterface = iRestaurantInterface;
    }
    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.useritem,parent,false);
        return new MyViewHolder(v, iRestaurantInterface);
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

    @Override
    public void onItemClicked(int position) {


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        IRestaurantInterface iRestaurantInterface;
        TextView name, surname, age;
        public MyViewHolder(@NonNull View itemView, IRestaurantInterface iRestaurantInterface) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            surname = itemView.findViewById(R.id.txtSurname);
            age = itemView.findViewById(R.id.txtAge);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iRestaurantInterface !=null){
                        int position= getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION)
                            iRestaurantInterface.onItemClicked(position);
                    }
                }
            });
        }
    }
}
