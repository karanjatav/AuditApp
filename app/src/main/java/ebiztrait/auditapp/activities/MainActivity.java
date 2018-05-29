package ebiztrait.auditapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

import ebiztrait.auditapp.adapters.BaseSQLite;
import ebiztrait.auditapp.R;
import ebiztrait.auditapp.pojos.AnswersObject;
import ebiztrait.auditapp.pojos.QuestionObject;

public class MainActivity extends AppCompatActivity {


    private TextView tvQuestion;
    private LinearLayout llSubqa;
    private Button btnPrevious;
    private Button btnNext;
    private Button btnAddPic;
    private Button btnAddQuestion;

    private ImageView ivAddPic;

    private String currentImageUri = "";

    private BaseSQLite baseSQLite;

    ArrayList<QuestionObject> questionObjects;
    ArrayList<AnswersObject> answersObjects;

    private int questionNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvQuestion = findViewById(R.id.tv_question);
        llSubqa = findViewById(R.id.ll_subqa);
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);
        btnAddPic = findViewById(R.id.btn_add_pic);
        ivAddPic = findViewById(R.id.iv_add_pic);
        btnAddQuestion = findViewById(R.id.btn_add_question);

        baseSQLite = new BaseSQLite(this);
        baseSQLite.open();

        btnPrevious.setEnabled(false);
        btnNext.setEnabled(false);

        addQuestions();
    }


    private void addQuestions() {

        questionObjects = baseSQLite.getAllQuestions();
        answersObjects = baseSQLite.getAllAnswers();

        if (questionObjects.size() != 0) {
            setQuestionAnswer(questionObjects.get(questionNo));
        }


        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndInsertAnswer();
                if (questionNo - 1 > -1) {
                    questionNo = questionNo - 1;
                    setQuestionAnswer(questionObjects.get(questionNo));
                    btnNext.setText("Next");
                    if (questionNo - 1 == -1) {
                        btnPrevious.setEnabled(false);
                    }
                }
            }
        });

        if (questionObjects.size() > 0) {
            btnNext.setEnabled(true);
        }
        if (questionObjects.size() == 1) {
            btnNext.setText("Submit");
        }


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndInsertAnswer();
                if (questionNo + 1 < questionObjects.size()) {
                    btnPrevious.setEnabled(true);
                    questionNo = questionNo + 1;
                    setQuestionAnswer(questionObjects.get(questionNo));
                    if (questionNo + 1 == questionObjects.size()) {
                        btnNext.setText("Submit");
                    }
                } else if (questionNo + 1 == questionObjects.size()) {
                    startActivity(new Intent(MainActivity.this, Results.class));
                }

            }
        });


        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddQuestions.class);
                if (questionObjects.size() != 0) {
                    Log.d("DATABASE_TEST", String.valueOf(questionObjects.size()));
                    i.putExtra("questionNo", String.valueOf(questionObjects.size()));
                } else {
                    Log.d("DATABASE_TEST", "NO_DATA");
                    i.putExtra("questionNo", "0");
                }
                startActivity(i);
                supportFinishAfterTransition();
            }
        });


        btnAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(MainActivity.this);
            }
        });
    }


    private void setQuestionAnswer(QuestionObject question) {
        tvQuestion.setText(question.getQuestion());
        llSubqa.removeAllViews();

        LayoutInflater vi2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < question.getOptions().size(); i++) {
            if (!TextUtils.isEmpty(question.getOptions().get(i))) {
                CheckBox questionCb = (CheckBox) vi2.inflate(R.layout.layout_cb_type, null);
                questionCb.setText(question.getOptions().get(i));

                if (baseSQLite.getAnswer(question.getQuestionId()) != null) {
                    questionCb.setChecked(Boolean.parseBoolean(baseSQLite.getAnswer(question.getQuestionId()).getAnswers().get(i)));
                }

                llSubqa.addView(questionCb);
            }
        }

    }


    private void getAndInsertAnswer() {
        ArrayList<String> answers = new ArrayList<>();
        //    Log.d("DATA_BASE_k===", "Child_count==" + llSubqa.getChildCount());

        for (int i = 0; i < llSubqa.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) llSubqa.getChildAt(i);
            Log.e("DATA_BASE_k==" + i + "===", String.valueOf(checkBox.isChecked()));
            answers.add(String.valueOf(checkBox.isChecked()));
        }
        Log.e("DATA_BASE_k==Q_ID===", String.valueOf(questionNo));

        baseSQLite.insertUserAnswer(new AnswersObject(String.valueOf(questionNo), answers, currentImageUri));
        answersObjects.add(questionNo, new AnswersObject(String.valueOf(questionNo), answers, currentImageUri));
        currentImageUri = "";
        Picasso.with(MainActivity.this).load("asdfasdf").into(ivAddPic);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.d("@requestCode", String.valueOf(requestCode));

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                currentImageUri = resultUri.getPath();
                Picasso.with(MainActivity.this).load(resultUri).into(ivAddPic);
            }
        }
    }
}
