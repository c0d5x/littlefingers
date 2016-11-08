package c0d5x.littlefingers;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    BubbleView bubbleview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("onStart", "onStart");
        getBubbleView().start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop", "onStop");
        getBubbleView().stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause", "onPause");
        getBubbleView().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume", "onResume");
        getBubbleView().resume();
    }

    protected BubbleView getBubbleView(){
        if (bubbleview==null){
            bubbleview = (BubbleView) findViewById(R.id.bubbleviewid);
        }
        return bubbleview;
    }

}
