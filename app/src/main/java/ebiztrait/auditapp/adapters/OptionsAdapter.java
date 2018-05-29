package ebiztrait.auditapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ebiztrait.auditapp.R;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> options;

    public OptionsAdapter(Context context, ArrayList<String> options) {
        this.context = context;
        this.options = options;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_options, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvOption.setText(options.get(position));
        holder.ivRemoveOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.remove(position);
                notifyDataSetChanged();
            }
        });
    }



    @Override
    public int getItemCount() {
        return options.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvOption;
        private ImageView ivRemoveOption;


        public ViewHolder(View itemView) {
            super(itemView);
            tvOption = itemView.findViewById(R.id.tv_option);
            ivRemoveOption = itemView.findViewById(R.id.iv_remove_option);
        }
    }
}
