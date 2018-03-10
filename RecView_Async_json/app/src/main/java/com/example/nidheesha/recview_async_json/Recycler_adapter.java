package com.example.nidheesha.recview_async_json;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nidheesha on 10-Mar-18.
 */

public class Recycler_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater layoutInflater ;
    List<Item> data;
    public Recycler_adapter(Context context, List<Item> data) {
        super();
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder h = (Holder) holder;
        Item s = data.get(position);
        h.item.setText(s.data);
    }
}

class Holder extends RecyclerView.ViewHolder{
    TextView item;
    public Holder(View v){
        super(v);
        item = (TextView)v.findViewById(R.id.list_item);
    }
}