package com.example.fbtpd.membershipmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;
    private  Activity parentActivity;

    public UserListAdapter(Context context, List<User> userList,Activity parentActivity)
    {
        this.context = context;
        this.userList = userList;
        this.parentActivity =parentActivity;
    }
    @Override
    public int getCount() {
        return  userList.size();

    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.user, null);
        final TextView userID = (TextView) v.findViewById(R.id.userID);
        TextView userPassword = (TextView) v.findViewById(R.id.userPassword);
        TextView userName = (TextView) v.findViewById(R.id.userName);
        TextView userAge = (TextView) v.findViewById(R.id.userAge);

        userID.setText(userList.get(position).getUserID());
        userPassword.setText(userList.get(position).getUserPassword());
        userName.setText(userList.get(position).getUserName());
        userAge.setText(userList.get(position).getUserAge());

        v.setTag(userList.get(position).getUserID());

        Button deleteButton =(Button)v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            final boolean success = jsonResponse.getBoolean("success");

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(parentActivity);
                                alertDialog.setMessage("정말 삭제 하시겠습니까?");
                                alertDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(success) {
                                                    userList.remove(position);
                                                    notifyDataSetChanged();
                                                }
                                            }
                                        });
                                alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(parentActivity,"취소",Toast.LENGTH_SHORT).show();

                                    }
                                });
                                alertDialog.show();

                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                DeleteRequest deleteRequest = new DeleteRequest(userID.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentActivity);
                queue.add(deleteRequest);
            }
        });
        return v;
    }
}
