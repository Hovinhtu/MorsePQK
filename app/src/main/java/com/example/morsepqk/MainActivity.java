package com.example.morsepqk;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.MessageQueue;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.media.PlaybackParams;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static final int default_wpm = 15;
    private static int wpmSpeed = 15;
    private static int speed = 15;
    Button[] buttons = new Button[36];
    String[] arrayOfKeys = new String[36];
    int indexOfMedia = 0;
    int indexOfButton = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        for(int i = 97; i < 123; i++){
            arrayOfKeys[indexOfMedia] = Character.toString((char) i);
            indexOfMedia++;
        }


        arrayOfKeys[26] = "zero";
        arrayOfKeys[27] = "one";
        arrayOfKeys[28] = "two";
        arrayOfKeys[29] = "three";
        arrayOfKeys[30] = "four";
        arrayOfKeys[31] = "five";
        arrayOfKeys[32] = "six";
        arrayOfKeys[33] = "seven";
        arrayOfKeys[34] = "eight";
        arrayOfKeys[35] = "nine";

        for(int i = 65; i < 91; i++){
            buttons[indexOfButton] = findViewById(getResources().getIdentifier("playButton" + Character.toString((char) i), "id", getPackageName()));
            indexOfButton++;
        }

        for(int i = 48; i < 58; i++){
            buttons[indexOfButton] = findViewById(getResources().getIdentifier("playButton" + Character.toString((char) i), "id", getPackageName()));
            indexOfButton++;
        }

        for (int i = 0; i < buttons.length; i++) {
            final int buttonIndex = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int audioResourceId = getResources().getIdentifier(arrayOfKeys[buttonIndex], "raw", getPackageName());
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer = MediaPlayer.create(MainActivity.this, audioResourceId);
                    setPlaybackSpeedByWPM(wpmSpeed, mediaPlayer);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                            mp = null;
                        };
                    });
                    mediaPlayer = null;
                }
            });

        }

        Button buttonMinus = findViewById(R.id.playButtonMinus);
        Button buttonPlus = findViewById(R.id.playButtonPlus);
        TextView textSpeed = findViewById(R.id.textSpeed);


        textSpeed.setText("" + speed);

        buttonPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(wpmSpeed >= 15 && wpmSpeed <= 100){
                    wpmSpeed += 1;
                    speed = speed + 5;
                    textSpeed.setText("" + speed);
                }
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(wpmSpeed >= 15 && wpmSpeed <= 100){
                    wpmSpeed -= 1;
                    speed = speed - 5;
                    textSpeed.setText("" + speed);
                }
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void setPlaybackSpeedByWPM(int targetWPM, MediaPlayer mPlayer) {
        if (mPlayer != null) {
            float speed = (float) targetWPM / default_wpm; // Tính toán tốc độ phát mới
            PlaybackParams playbackParams = new PlaybackParams();
            playbackParams.setSpeed(speed);
            mPlayer.setPlaybackParams(playbackParams);
        }
    }


}
