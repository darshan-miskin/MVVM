package com.gne.www.mvvm.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gne.www.mvvm.R;
import com.gne.www.mvvm.vo.Item;

public class AdapterRecycler extends ListAdapter<Item, AdapterRecycler.MyHolder> {

    OnItemClickListener onItemClickListener;
    interface OnItemClickListener {
        void OnItemClick(Item item, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private static DiffUtil.ItemCallback<Item> diffUtil=new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem==newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getProduct_name().equals(newItem.getProduct_name()) && oldItem.getSupplier().equals(newItem.getSupplier()) &&
                    oldItem.getQuantity()==newItem.getQuantity();
        }
    };

    protected AdapterRecycler() {
        super(diffUtil);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler,parent,false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Item item =getItem(position);
        holder.txtProductName.setText(item.getProduct_name());
        holder.txtSupplier.setText(item.getSupplier());
        holder.txtQuantity.setText(item.getQuantity()+"");
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView txtProductName, txtSupplier, txtQuantity;
        CardView cardView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.card);

            txtProductName =itemView.findViewById(R.id.txt_product_name);
            txtSupplier =itemView.findViewById(R.id.txt_supplier);
            txtQuantity =itemView.findViewById(R.id.txt_quantity);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null && getAdapterPosition()!=-1){
                        onItemClickListener.OnItemClick(getItem(getAdapterPosition()),getAdapterPosition());
                    }
                }
            });
        }
    }
}
