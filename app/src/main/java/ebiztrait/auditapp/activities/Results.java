package ebiztrait.auditapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import ebiztrait.auditapp.adapters.AnswersAdapter;
import ebiztrait.auditapp.adapters.BaseSQLite;
import ebiztrait.auditapp.R;
import ebiztrait.auditapp.pojos.AllData;
import ebiztrait.auditapp.pojos.AnswersObject;
import ebiztrait.auditapp.pojos.QuestionObject;

public class Results extends AppCompatActivity {

    private RecyclerView rvAnswers;
    private BaseSQLite baseSQLite;
    private ArrayList<QuestionObject> questionObjects;
    private ArrayList<AnswersObject> answersObjects;
    private AnswersAdapter answersAdapter;
    private ArrayList<AllData> allDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        rvAnswers = findViewById(R.id.rv_answers);

        allDatas = new ArrayList<>();

        baseSQLite = new BaseSQLite(this);
        baseSQLite.open();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Results.this);
        rvAnswers.setLayoutManager(linearLayoutManager);

        answersAdapter = new AnswersAdapter(Results.this, allDatas);
        rvAnswers.setAdapter(answersAdapter);

        getResults();

    }

    private void getResults() {
        questionObjects = baseSQLite.getAllQuestions();
        answersObjects = baseSQLite.getAllAnswers();

        for (int i = 0; i < questionObjects.size(); i++) {
            AllData allData = new AllData();
            allData.setQuestion(questionObjects.get(i).getQuestion());
            allData.setOptions(questionObjects.get(i).getOptions());
            allData.setAnswers(answersObjects.get(i).getAnswers());
            allData.setImagePath(answersObjects.get(i).getImagePath());

            Log.e("QUESTIONS===", questionObjects.get(i).getQuestion());
            for (int j = 0; j < questionObjects.get(i).getOptions().size(); j++) {
                Log.e("OPTIONS===", questionObjects.get(i).getOptions().get(j) + "---------------" + answersObjects.get(i).getAnswers().get(j));

            }
            Log.e("IMAGE===", answersObjects.get(i).getImagePath());

            allDatas.add(allData);
        }

        answersAdapter.notifyDataSetChanged();
    }
}
