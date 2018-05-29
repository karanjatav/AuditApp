package ebiztrait.auditapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import ebiztrait.auditapp.pojos.AnswersObject;
import ebiztrait.auditapp.pojos.QuestionObject;

public class BaseSQLite extends SQLiteOpenHelper {

    private SQLiteDatabase bdd;

    private static final String TABLE_QUESTIONS = "table_questions";
    private static final String COL_Q_ID = "id";
    private static final String COL_QUESTION = "question";
    private static final String COL_OPT_ = "opt_";

    private static final String COL_OPT_0 = "opt_0";
    private static final String COL_OPT_1 = "opt_1";
    private static final String COL_OPT_2 = "opt_2";
    private static final String COL_OPT_3 = "opt_3";

    private static final String TABLE_USER = "table_user";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_ANS_ = "ans_";
    private static final String COL_ANS_0 = "ans_0";
    private static final String COL_ANS_1 = "ans_1";
    private static final String COL_ANS_2 = "ans_2";
    private static final String COL_ANS_3 = "ans_3";
    private static final String COL_IMAGE = "col_image";

    public BaseSQLite(Context context) {
        super(context, "questions.db", null, 1);
    }

    public BaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_QUESTIONS + " (" + COL_Q_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_QUESTION + " TEXT, " + COL_OPT_0 + " TEXT, " + COL_OPT_1 + " TEXT, " + COL_OPT_2 + " TEXT, " + COL_OPT_3 + " TEXT);");


        db.execSQL("CREATE TABLE " + TABLE_USER + " (" + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_Q_ID + " INTEGER,"
                + COL_ANS_0 + " TEXT, " + COL_ANS_1 + " TEXT, " + COL_ANS_2 + " TEXT, " + COL_ANS_3 + " TEXT, " + COL_IMAGE + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_QUESTIONS + ";");
        db.execSQL("DROP TABLE " + TABLE_USER + ";");
        onCreate(db);
    }

    public void open() {
        bdd = this.getWritableDatabase();
    }

    public void close() {
        bdd.close();
    }


    public long insertQuestion(QuestionObject question) {
        if (getQuestion(question.getQuestionId()) == null) {
            ContentValues values = new ContentValues();

            values.put(COL_Q_ID, question.getQuestionId());
            values.put(COL_QUESTION, question.getQuestion());

            for (int i = 0; i < question.getOptions().size(); i++) {
                values.put(COL_OPT_ + i, question.getOptions().get(i));
            }
            return bdd.insert(TABLE_QUESTIONS, null, values);
        } else {
            Log.d("DATABASE_TEST_Q", getQuestion(question.getQuestionId()).getQuestion());
        }
        return -1;
    }


    public long insertUserAnswer(AnswersObject answersObject) {
        if (getAnswer(answersObject.getQuestionId()) == null) {

            ContentValues values = new ContentValues();
            values.put(COL_Q_ID, answersObject.getQuestionId());

            for (int i = 0; i < answersObject.getAnswers().size(); i++) {
                values.put(COL_ANS_ + i, answersObject.getAnswers().get(i));
            }
            values.put(COL_IMAGE, answersObject.getImagePath());
            return bdd.insert(TABLE_USER, null, values);
        } else {
            Log.d("DATABASE_TEST====", "Already Answered");
            updateUserAnswer(answersObject);
        }
        return 0;
    }


    public int updateUserAnswer(AnswersObject answersObject) {
        ContentValues values = new ContentValues();

        for (int i = 0; i < answersObject.getAnswers().size(); i++) {
            values.put(COL_ANS_ + i, answersObject.getAnswers().get(i));
        }
        values.put(COL_IMAGE, answersObject.getImagePath());
        return bdd.update(TABLE_USER, values, COL_Q_ID + " = '" + answersObject.getQuestionId() + "'", null);
    }


    public AnswersObject getAnswer(String questionId) {
        Cursor c = bdd.query(TABLE_USER, new String[]{COL_Q_ID, COL_ANS_0, COL_ANS_1, COL_ANS_2, COL_ANS_3, COL_IMAGE}, COL_Q_ID + " = '" + questionId + "'", null, null, null, null);
        if (c.moveToFirst()) {
            ArrayList<String> answers = new ArrayList<>();
            answers.add(c.getString(1));
            answers.add(c.getString(2));
            answers.add(c.getString(3));
            answers.add(c.getString(4));

            return new AnswersObject(c.getString(0), answers, c.getString(5));
        }
        return null;
    }

    public ArrayList<AnswersObject> getAllAnswers() {

        ArrayList<AnswersObject> answersObjects = new ArrayList<>();
        Cursor c = bdd.query(TABLE_USER, new String[]{COL_Q_ID, COL_ANS_0, COL_ANS_1, COL_ANS_2, COL_ANS_3, COL_IMAGE}, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                ArrayList<String> answers = new ArrayList<>();
                answers.add(c.getString(1));
                answers.add(c.getString(2));
                answers.add(c.getString(3));
                answers.add(c.getString(4));
                AnswersObject answersObject = new AnswersObject(c.getString(0), answers, c.getString(5));
                answersObjects.add(answersObject);
            } while (c.moveToNext());
        }
        return answersObjects;
    }


    public QuestionObject getQuestion(String questionId) {
        Cursor c = bdd.query(TABLE_QUESTIONS, new String[]{COL_Q_ID, COL_QUESTION, COL_OPT_0, COL_OPT_1, COL_OPT_2, COL_OPT_3}, COL_Q_ID + " = '" + questionId + "'", null, null, null, null);
        //   Log.d("DATABASE_TEST====", "" + c.getCount());

        if (c.moveToFirst()) {
            ArrayList<String> options = new ArrayList<>();
            options.add(c.getString(2));
            options.add(c.getString(3));
            options.add(c.getString(4));
            options.add(c.getString(5));
            return new QuestionObject(c.getString(0), c.getString(1), options);
        }
        return null;
    }

    public ArrayList<QuestionObject> getAllQuestions() {

        ArrayList<QuestionObject> questionObjects = new ArrayList<>();
        Cursor c = bdd.query(TABLE_QUESTIONS, new String[]{COL_Q_ID, COL_QUESTION, COL_OPT_0, COL_OPT_1, COL_OPT_2, COL_OPT_3}, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                ArrayList<String> options = new ArrayList<>();
                options.add(c.getString(2));
                options.add(c.getString(3));
                options.add(c.getString(4));
                options.add(c.getString(5));
                QuestionObject cat = new QuestionObject(c.getString(0), c.getString(1), options);
                questionObjects.add(cat);
            } while (c.moveToNext());
        }
        return questionObjects;
    }
}