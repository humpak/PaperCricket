package com.example.ashaikh1.papercricket;

import com.example.ashaikh1.papercricket.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.*;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Set;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class PlayActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    private static int NO_OF_BALLS = 10;

    private static int NO_OF_WICKETS = 5;

    private static final String player1Name = "Player1";

    private static final String player2Name = "Player2";

    private static boolean istwoPlayerGame = true;

    private int currentPlayer = 1;

    private int score1 = 0;

    private int score2 = 0;

    private int ballsPlayed = 0;

    private int currentWickets = 0;

    TextView textRuns;
    TextView textBalls;
    TextView textWickets;
    TextView playerName;
    TextView labelTarget;
    TextView textTarget;
    ImageView imageSwing;

    /**
     *
     * This function updates the settings from the settings activity.
     *
     *
     * @param noOfBalls
     * @param noOfWickets
     * @param flag
     */
    public static void updateSettings(int noOfBalls,int noOfWickets, boolean flag){
        NO_OF_BALLS = noOfBalls;
        NO_OF_WICKETS = noOfWickets;
        istwoPlayerGame = flag;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }

    /**
     *
     * This function gets all the widgets from thr UI.
     *
     */
    private void getWidgets(){
        textRuns = (TextView) findViewById(R.id.textRuns);
        textBalls = (TextView) findViewById(R.id.textBalls);
        textWickets = (TextView) findViewById(R.id.textWickets);
        playerName = (TextView) findViewById(R.id.textPlayerName);
        labelTarget = (TextView) findViewById(R.id.labelTarget);
        textTarget = (TextView) findViewById(R.id.textTarget);
        imageSwing = (ImageView) findViewById(R.id.imageSwing);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getWidgets();
        setDefaults(1);
        playerName.setText(player1Name);
        if (!istwoPlayerGame){
            currentPlayer = 2;
            setTargetForOnePlayer();
        }
        else{
            labelTarget.setVisibility(View.INVISIBLE);
            textTarget.setVisibility(View.INVISIBLE);
        }
    }


    private void setTargetForOnePlayer(){
        for (int i = 0; i < NO_OF_BALLS; i++) {
            int run = (int) (Math.random() * 100) % 8;
            score1 += (run != 7) ? (run) : 0;
        }
        textTarget.setText(""+score1);
    }


    public void setDefaults(int player){
        textBalls.setText(""+NO_OF_BALLS);
        textRuns.setText("0");
        textWickets.setText("0");
        ballsPlayed = 0;
        currentWickets = 0;
        currentPlayer = player;
    }

    public void swingBat(View view){

        if( ballsPlayed < NO_OF_BALLS ) {


            if (currentWickets < NO_OF_WICKETS) {

                ballsPlayed++;
                textBalls.setText(""+(NO_OF_BALLS-ballsPlayed));

                int run = (int) (Math.random() * 100) % 8;
                showDialog(run+" runs.","Score");
                if (run == 7) {
                    currentWickets++;
                    textWickets.setText(""+currentWickets);
                }
                else if (currentPlayer == 1) {
                    score1+=run;
                    textRuns.setText(""+score1);
                }
                else {
                    score2+=run;
                    textRuns.setText(""+score2);
                }
            }else{
                showDialog("All out","All the batsmen are out");
            }

        }else
            // CHANGE TO PLAYER 1
            if( currentPlayer == 1 ) {

                showDialog("The final score is "+score1+" for "+currentWickets+" " +
                        "wickets."+player2Name+" will play now","Innings over");
                playerName.setText(player2Name);
                setDefaults(2);
                labelTarget.setVisibility(View.VISIBLE);
                textTarget.setVisibility(View.VISIBLE);
                textTarget.setText(""+score1);

            }
           // FINAL RESULT. BOTH PLAYERS HAVE COMPLETED PLAYING
           else if(currentPlayer == 2 ){
               String winner = (score1 > score2 )? player1Name: player2Name;
               imageSwing.setClickable(false);
               showDialogwithTwoButtons(winner +" is the winner","Game over","New Game","Exit");
           }
     }





    private void showDialog(String message, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void showDialogwithTwoButtons(String message,String title,String pButton, String nButton){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton(pButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(new WelcomeScreen(), SettingsActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(nButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        exit();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void exit(View view){
        exit();
    }

    public void exit(){
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}

