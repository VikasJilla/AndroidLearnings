package com.jilla.rxjavasample.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jilla.rxjavasample.R;
import com.jilla.rxjavasample.network.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListAdapter extends RecyclerView.Adapter {

    public List<User> users;
    View.OnClickListener listener;

    public UserListAdapter(View.OnClickListener clickListener){
        listener = clickListener;
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_item,null);
        view.setOnClickListener(listener);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((UserViewHolder) holder).updateUI(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.username_txt)
        TextView left;

        @BindView(R.id.name_txt)
        TextView name;

        @BindView(R.id.email_txt)
        TextView email;

        @BindView(R.id.phone_no)
        TextView phoneNo;

        private UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void updateUI(User user){
            name.setText(user.name);
            email.setText(user.email);
            phoneNo.setText(user.phone);
            left.setText(user.username.toUpperCase().substring(0,2));
        }
    }
}

