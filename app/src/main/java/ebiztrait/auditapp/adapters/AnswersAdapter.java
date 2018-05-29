package ebiztrait.auditapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import ebiztrait.auditapp.R;
import ebiztrait.auditapp.pojos.AllData;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AllData> allData;

    public AnswersAdapter(Context context, ArrayList<AllData> allData) {
        this.context = context;
        this.allData = allData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_answers, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvQuestion.setText(allData.get(position).getQuestion());

        for (int i = 0; i < allData.get(position).getOptions().size(); i++) {
            if (!TextUtils.isEmpty(allData.get(position).getOptions().get(i))) {
                View v = LayoutInflater.from(context).inflate(R.layout.layout_q_a, null);
                TextView qtv = v.findViewById(R.id.tv_option);
                TextView atv = v.findViewById(R.id.tv_answer);
                qtv.setText(allData.get(position).getOptions().get(i));
                if (allData.get(position).getAnswers().get(i).equals("true")) {
                    atv.setText("Yes");
                } else if (allData.get(position).getAnswers().get(i).equals("false")) {
                    atv.setText("No");
                }
                holder.llData.addView(v);

            }
        }

        if (!TextUtils.isEmpty(allData.get(position).getImagePath())) {
            Picasso.with(context).load(new File(allData.get(position).getImagePath())).placeholder(R.drawable.ic_image_black_24dp).into(holder.ivData);
        }
    }


    @Override
    public int getItemCount() {
        return allData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvQuestion;
        private LinearLayout llData;
        private ImageView ivData;


        public ViewHolder(View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            llData = itemView.findViewById(R.id.ll_data);
            ivData = itemView.findViewById(R.id.iv_data);

        }
    }
}
