package com.arnoldas.mathteacher;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //MainActivity.java

    //hiding the student button after clicking it
    Button studentStartButton;
    Button restartButton;
    int ansLoc;
    int numCorrect = 0;
    //double score = 0.0;
    TextView textViewResult;
    //answers array list
    ArrayList<Integer> answers = new ArrayList<>();
    int numQuestions = 0;
    TextView textViewPoints;
    TextView textViewSum;
    Button button0 ;
    Button button1;
    Button button2;
    Button button3 ;
    TextView textViewTimer;

    //left to do *generate new question, get timmer working,
    public void newQuestion() {
        Random randInt = new Random();

        int first = randInt.nextInt(11);
        int second = randInt.nextInt(11);
        System.out.println("first : "+first);
        System.out.println("second : "+second);

        textViewSum.setText(Integer.toString(first)+" + "+Integer.toString(second));
        //randomly place for correct answer
        ansLoc = randInt.nextInt(4);


        //for scenario of having the correct answer accidently randomly chosen
        int ansWrong;
        answers.clear();
        for(int a = 0 ; a<4 ; a++){
            if(a == ansLoc)
                answers.add(first+second);
            else {
                ansWrong = randInt.nextInt(31);
                while(ansWrong == first+second){
                    ansWrong = randInt.nextInt(31);
                }
                answers.add(ansWrong);
            }
        }
        //updating answer buttons with numbers from above
        System.out.println("0 : "+answers.get(0));
        System.out.println("1 : "+answers.get(1));
        System.out.println("2 : "+answers.get(2));
        System.out.println("3 : "+answers.get(3));

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }


    //buttons now clickable and returning correct location w/o crashing
    //method to print out if answer was selected correct or not and updates score
    public void selectAns(View view){
        //printing each tag to log when clicked on in app
        Log.i("Tag", (String) view.getTag());

        if(view.getTag().toString().equals(Integer.toString(ansLoc))){
            //checking if log prints correct if select correct ans
            Log.i("correct", "correct");
            //add 1 to the number of correct choices when user selects correct answer and then generate new question
            numCorrect++;
            textViewResult.setText(R.string.correctAnswer);


        }else
            textViewResult.setText(R.string.wrongAnswer);
        numQuestions++;
        textViewPoints.setText(Integer.toString(numCorrect)+ " / "+Integer.toString(numQuestions));
        //score = numCorrect/numQuestions;
        //System.out.println("score is "+ score);
        newQuestion();
    }

    //Play again button
    public void restart(View view){
        //set score back to zero
        numCorrect = 0;
        //num questions back to zero
        numQuestions = 0;
        //textViewTimer.setText("30s");
        textViewResult.setText("");
        textViewPoints.setText("0/0");
        restartButton.setVisibility(View.INVISIBLE);

        //generating new question
        newQuestion();

        //update timer
        new CountDownTimer(9999, 999) {


            @Override
            public void onTick(long l) {
                textViewTimer.setText(String.format(getString(R.string.sec), String.valueOf(l / 1000)));
            }

            @Override
            public void onFinish() {
                restartButton.setVisibility(View.VISIBLE);
                textViewTimer.setText(R.string.done);
                double score = numQuestions == 0 ? 0.0 : (1.0* numCorrect  / numQuestions) * 100.0;
					/*
					int wrong = numQuestions - numCorrect;
					int per = 100 - (Math.round((100/numQuestions)*wrong));
					//100 - (Round((100/Questions)*Wrong))
					System.out.println("my score is " + per);
					int total = 100;
					float percentage = (float)(score * 100/ total);
					*/
                textViewResult.setText("Grade : " +Integer.toString((int) score) +"%");

            }
        }.start();

    }

    //student start button
    public void studentStart(View view){
        studentStartButton.setVisibility(View.INVISIBLE);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //find the student start button on starting/ creating app
        studentStartButton = (Button)findViewById(R.id.studentStartButton);
        textViewSum = (TextView)findViewById(R.id.textViewSum);

        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);

        textViewResult = (TextView)findViewById(R.id.textViewResult);
        textViewTimer = (TextView)findViewById(R.id.textViewTimer);
        textViewPoints = (TextView)findViewById(R.id.textViewPoints);
        restartButton  = (Button)findViewById(R.id.restartButton);

        //newQuestion();
        restart(findViewById(R.id.restartButton));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void manageStudents(View view) {
        Intent intent = new Intent(this, ManageStudents.class);
        startActivity(intent);
    }
}
