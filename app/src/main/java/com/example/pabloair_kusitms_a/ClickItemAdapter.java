package com.example.pabloair_kusitms_a;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClickItemAdapter extends RecyclerView.Adapter<ClickItemAdapter.ViewHolder> {

    private ArrayList<ClickItem> mClickList;

    @NonNull
    @Override
    public ClickItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.click_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClickItemAdapter.ViewHolder holder, int position) {
        holder.onBind(mClickList.get(position));
    }

    public void setClickList(ArrayList<ClickItem> list) {
        this.mClickList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mClickList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productPhoto;
        TextView productName;
        TextView productAmount;
        TextView productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productPhoto = (ImageView) itemView.findViewById(R.id.click_list_productPhoto);
            productName = (TextView) itemView.findViewById(R.id.click_list_productName);
            productAmount = (TextView) itemView.findViewById(R.id.click_list_productAmount);
            productPrice = (TextView) itemView.findViewById(R.id.click_list_productPrice);

        }

        public void onBind(ClickItem item) {
            productPhoto.setImageResource(item.getResourceId());
            productName.setText(item.getProductName());
            productAmount.setText(item.getProductName() +" " + item.getProductAmount()+ "개 (" + item.getProductPrice()+"원)");
            productPrice.setText(item.getProductAmount()*item.getProductPrice()+"원");
        }
    }
}
