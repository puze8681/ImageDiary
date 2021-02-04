package kr.puze.imagediary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import kr.puze.imagediary.Data.DiaryData;
import kr.puze.imagediary.R;

public class DiaryRecyclerAdapter extends RecyclerView.Adapter<DiaryRecyclerAdapter.ItemViewHolder> {

    private ArrayList<DiaryData> listData = new ArrayList<>();
    private ViewGroup parent;

    public interface OnListItemLongSelectedInterface {
        void onItemLongSelected(View v, int position);
    }

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;
    private OnListItemLongSelectedInterface mLongListener;

    public DiaryRecyclerAdapter(OnListItemSelectedInterface listener, OnListItemLongSelectedInterface longListener) {
        this.mListener = listener;
        this.mLongListener = longListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_main, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(position, listData.get(position), parent);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void addItem(DiaryData data) {
        listData.add(data);
    }

    public void resetItem(){
        listData.clear();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textPosition;
        private TextView textDate;
        private TextView textTitle;

        ItemViewHolder(View itemView) {
            super(itemView);

            textPosition = itemView.findViewById(R.id.text_position_item_recycler);
            textDate = itemView.findViewById(R.id.text_name_item_recycler);
            textTitle = itemView.findViewById(R.id.text_description_item_recycler);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mLongListener.onItemLongSelected(view, getAdapterPosition());
                    return false;
                }
            });
        }

        void onBind(int position, DiaryData data, @NonNull ViewGroup parent) {
            textPosition.setText((position + 1) + " 장");
            textDate.setText(data.getDate());
            textTitle.setText(data.getTitle());
        }
    }
}