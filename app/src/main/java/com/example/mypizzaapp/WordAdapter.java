package com.example.mypizzaapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordAdapterVh> implements Filterable {

    public List<WordModel> wordModelList;
    public List<WordModel> getWordModelListFilter;
    public Context context;
    public UserClickListener userClickListener;

    public WordAdapter(List<WordModel> wordModels, Context context, WordAdapter.UserClickListener userClickListener){
        this.wordModelList = wordModels;
        this.getWordModelListFilter = wordModels;
        this.context = context;
        this.userClickListener = userClickListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.values = getWordModelListFilter;
                    filterResults.count = getWordModelListFilter.size();
                }else{
                    String searchStr = constraint.toString().toLowerCase();
                    List<WordModel> wordModels = new ArrayList<>();
                    for(WordModel wordModel: getWordModelListFilter){
                        if(wordModel.getOrdSvenska().toLowerCase().contains(searchStr) || wordModel.getOrdRyska().toLowerCase().contains(searchStr)){
                            wordModels.add(wordModel);
                        }
                    }

                    filterResults.values = wordModels;
                    filterResults.count = wordModels.size();
                }
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                wordModelList = Collections.unmodifiableList( (List<WordModel>) results.values );
                notifyDataSetChanged();
            }
        };
    }

    public interface UserClickListener{
        void selectedUser(WordModel wordModel);
    }

    @NonNull
    @Override
    public WordAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate( R.layout.row_word,parent,false);
        return new WordAdapterVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordAdapterVh holder, int position) {

        WordModel wordModel = wordModelList.get(position);
        String ordSvenska = wordModel.getOrdSvenska();
        String ordRyska = wordModel.getOrdRyska();
        String ordPrefix = ordSvenska.charAt(0) +" ";

        holder.ordPrefix.setText(ordPrefix);
        holder.ordRyska.setText(ordRyska);
        holder.ordSvenska.setText(ordSvenska);

        holder.itemView.setOnClickListener( v -> userClickListener.selectedUser(wordModel) );

    }

    @Override
    public int getItemCount() {
        return wordModelList.size();
    }

    public static class WordAdapterVh extends RecyclerView.ViewHolder {
        private final TextView ordPrefix;
        private final TextView ordRyska;
        private final TextView ordSvenska;

        public WordAdapterVh(@NonNull View itemView) {
            super(itemView);
            ordRyska = itemView.findViewById(R.id.tvRyska );
            ordSvenska = itemView.findViewById(R.id.tvSvenska );
            ordPrefix = itemView.findViewById(R.id.tvPrefix );
        }
    }


}
