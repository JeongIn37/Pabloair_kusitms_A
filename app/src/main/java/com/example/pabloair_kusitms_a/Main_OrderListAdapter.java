package com.example.pabloair_kusitms_a;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Main_OrderListAdapter extends RecyclerView.Adapter<Main_OrderListAdapter.ViewHolder> {

    private ArrayList<ListOrderItem> mOrderList;
    private onItemClickListener itemClickListener;

    @NonNull
    @Override //ViewHolder 객체 생성
    public Main_OrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Main_OrderListAdapter.ViewHolder holder, int position) {
        holder.onBind(mOrderList.get(position));

    }

    //리스트 생성
    public void setOrderList(ArrayList<ListOrderItem> list) {
        this.mOrderList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    //리스트뷰 아이템 클릭 리스너
    public interface onItemClickListener {
        void onItemClick(View v,  int pos);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.itemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //요소 연결
        TextView name;
        TextView serializedNumber;
        TextView station;
        TextView takeTime;
        TextView weight;
        ImageView QrCode;


        //ViewHolder 생성자
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //요소 연결
            name = (TextView) itemView.findViewById(R.id.list_name);
            serializedNumber = (TextView) itemView.findViewById(R.id.list_orderNumber);
            station = (TextView) itemView.findViewById(R.id.list_station);
            weight =  (TextView) itemView.findViewById(R.id.list_weight);
            takeTime = (TextView) itemView.findViewById(R.id.list_takeTime);

            //전체 클릭시
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //아이템 존재 포지션 확인
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(itemClickListener != null) {
                            itemClickListener.onItemClick(v,pos);
                        }
                    }
                }
            });

        }

        void onBind(ListOrderItem item) {
            name.setText(item.getName() + "님 - ");
            serializedNumber.setText("주문번호 "+item.getSerializedNumber());
            station.setText(item.getStation());
            weight.setText("상품이동중 (중량 " + item.getWeight() + "kg)");
            takeTime.setText("예상시간 ("+ item.getTakeTime() + "분)");
        }

    }
}
