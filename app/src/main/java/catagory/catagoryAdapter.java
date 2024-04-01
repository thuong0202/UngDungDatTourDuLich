package catagory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import huflit.phn.dulichvietnam.R;
import layout.AnhbamienAdapter;

public class catagoryAdapter extends RecyclerView.Adapter<catagoryAdapter.categoryHolder>{
    private Context mContext;
    private List<catagory> mListCatagory;



    public catagoryAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<catagory> list){
        this.mListCatagory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public categoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hienthi,parent,false);
        return new categoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryHolder holder, int position) {
        catagory cata = mListCatagory.get(position);
        if(cata == null) {
            return;
        }
        holder.tvNameCategory.setText(cata.getNameCatagory());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false);
        holder.rcyAnh.setLayoutManager(linearLayoutManager);
        AnhbamienAdapter anhbamienAdapter = new AnhbamienAdapter();
        anhbamienAdapter.setData(cata.getAnhbamiens());
        holder.rcyAnh.setAdapter(anhbamienAdapter);
    }

    @Override
    public int getItemCount() {
        if(mListCatagory != null){
            return mListCatagory.size();
        }
        return 0;
    }
    public class categoryHolder extends RecyclerView.ViewHolder{
        private TextView tvNameCategory;
        private RecyclerView rcyAnh;

        public categoryHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCategory = itemView.findViewById(R.id.tvname);
            rcyAnh = itemView.findViewById(R.id.rcyanh);
        }
    }
}
