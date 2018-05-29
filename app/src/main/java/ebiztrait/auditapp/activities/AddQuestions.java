package ebiztrait.auditapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import ebiztrait.auditapp.adapters.BaseSQLite;
import ebiztrait.auditapp.adapters.OptionsAdapter;
import ebiztrait.auditapp.R;
import ebiztrait.auditapp.pojos.QuestionObject;

public class AddQuestions extends AppCompatActivity {

    private EditText etQuestion;
    private RecyclerView rvOptions;
    private EditText etOption;
    private ImageView btnOptionAdd;
    private Button btnAddQuestion;


    private OptionsAdapter optionsAdapter;
    private BaseSQLite baseSQLite;
    private ArrayList<String> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        options = new ArrayList<>();


        etQuestion = findViewById(R.id.et_question);
        rvOptions = findViewById(R.id.rv_options);
        etOption = findViewById(R.id.et_option);
        btnOptionAdd = findViewById(R.id.btn_option_add);
        btnAddQuestion = findViewById(R.id.btn_add_question);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddQuestions.this);
        rvOptions.setLayoutManager(linearLayoutManager);

        optionsAdapter = new OptionsAdapter(AddQuestions.this, options);
        rvOptions.setAdapter(optionsAdapter);

        baseSQLite = new BaseSQLite(this);
        baseSQLite.open();

        Log.d("DATABASE_TEST", getIntent().getStringExtra("questionNo"));


        btnOptionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etOption.getText().toString())) {
                    Toast.makeText(AddQuestions.this, "Please Enter text into option field", Toast.LENGTH_LONG).show();
                } else if (options.size() > 3) {
                    Toast.makeText(AddQuestions.this, "You cant add more than 4 fields.", Toast.LENGTH_LONG).show();
                } else {
                    options.add(etOption.getText().toString());
                    optionsAdapter.notifyDataSetChanged();
                    etOption.setText("");
                }
            }
        });

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etQuestion.getText().toString())) {
                    Toast.makeText(AddQuestions.this, "Please Enter a Question", Toast.LENGTH_LONG).show();
                } else if (options.size() < 1) {
                    Toast.makeText(AddQuestions.this, "Please Add Atleat one Option", Toast.LENGTH_LONG).show();
                } else {
                    if (baseSQLite.insertQuestion(new QuestionObject(getIntent().getStringExtra("questionNo"), etQuestion.getText().toString(), options)) != -1) {
                        startActivity(new Intent(AddQuestions.this, MainActivity.class));
                        supportFinishAfterTransition();
                    }
                }
            }
        });
    }


}
