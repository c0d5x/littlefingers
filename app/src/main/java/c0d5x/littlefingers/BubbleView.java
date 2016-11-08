package c0d5x.littlefingers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.MotionEvent;


import java.util.ArrayList;
import java.util.Random;

public class BubbleView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    final float INTENSITY_FACTOR = 2.0f;

    Thread babyThread = null;
    boolean threadRunning = false;
    SurfaceHolder holder = null;

    ArrayList<Bubble> objList;

    Paint backgroundPaint;
    int emptyCycles = 0;

    Random rnd;

    private void init(){
        holder = getHolder();
        holder.addCallback(this);

        objList = new ArrayList<Bubble>();
        rnd = new Random();
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
    }

    public BubbleView(Context context) {
        super(context);
        Log.i("Created", "Constructor");
        init();
    }

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i("Created", "Constructor with attrs");
        init();
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i("Created", "Constructor with attrs and defStyleAttr");
        init();
    }

    @Override
    public void run(){
        Log.i("Run", "Running");
        while (threadRunning){
            if (!holder.getSurface().isValid()){
                Log.e("Run", "Surface not valid yet");
                try{
                    Thread.sleep(50);
                }catch(InterruptedException exp){
                    Log.e("Run", "Exception waiting for surface to be valid - can be ignored");
                }
                continue;
            }else{
                Canvas canvas = holder.lockCanvas();
                canvas.drawColor(backgroundPaint.getColor());
                drawCycle(canvas);
                holder.unlockCanvasAndPost(canvas);
                objCycle();
            }
        }
    }

    public void start(){
        Log.i("Start", "Started");
    }

    public void stop(){
        Log.i("Stop", "Stopped");
    }

    public void pause(){
        Log.i("Pause", "Paused");
        threadRunning = false;
        while(true){
            Log.i("Pause", "One cycle in while");
            try{
                if (babyThread!=null)
                    babyThread.join();
                break;
            }catch(InterruptedException exp){
                Log.e("Pause", exp.getMessage());
            }
        }
        babyThread = null;
        Log.i("Pause", "Thread has finished");
    }

    public void resume(){
        Log.i("Resume", "Resumed");
        threadRunning = true;
        babyThread = new Thread(this);
        babyThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("onTouch", "onTouch: "+event.toString());
        boolean processed = false;

        switch(event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                objList.add(new Bubble(event.getX(), event.getY(), event.getPressure(), rnd));
                processed = true;
                break;
        }
        return processed;
    }

    public void objCycle(){
        // manage the decay of objects, instead of age
        for (int idx=0; idx < objList.size(); idx++){

            Bubble obj = objList.get(idx);
            obj.radio-= INTENSITY_FACTOR;

            if (obj.radio > 1){

                obj.x += obj.dx;
                obj.y += obj.dy;

                if (obj.x < 0 || obj.x > this.getWidth()){
                    obj.dx = -obj.dx;
                    obj.x += obj.dx;
                }
                if (obj.y < 0 || obj.y > this.getHeight()){
                    obj.dy = -obj.dy;
                    obj.y += obj.dy;
                }

            }else{
                objList.remove(idx);
                obj = null;
            }
        }

        if (objList.size()==0){
            ++emptyCycles;
            if (emptyCycles > 100){
                // generate random bubbles
                for (int i = 0; i < rnd.nextInt(50); i++){
                    objList.add( (new Bubble(rnd.nextFloat() * this.getWidth(), rnd.nextFloat() * this.getHeight(), rnd.nextFloat() * 2, rnd)) );
                }
                emptyCycles = 0;
            }
        }else{
            emptyCycles = 0;
        }
    }

    public void drawCycle(Canvas canvas) {
        for (int idx = 0; idx < objList.size(); idx++) {
            Bubble obj = objList.get(idx);
            canvas.drawCircle(obj.x, obj.y, obj.radio, obj.paint);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(backgroundPaint.getColor());
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
