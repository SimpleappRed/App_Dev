package com.example.nidheesha.load_json;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nidheesha on 10-Mar-18.
 */

public class Recycler_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    List<Contact>contacts;
    public Recycler_adapter(Context context, List<Contact>list) {
        super();
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        contacts = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        Holder viewHolder = (Holder)holder;
        viewHolder.name.setText(contact.name);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
    class Holder extends RecyclerView.ViewHolder{
        TextView name;
        public Holder(View v){
            super(v);
            name = v.findViewById(R.id.name);
        }
    }
}
