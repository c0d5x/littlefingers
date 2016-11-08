package c0d5x.littlefingers;

import android.graphics.Paint;

import java.util.Random;

public class Bubble {
    float x = 0;
    float y = 0;
    float dx = 0;
    float dy = 0;
    float radio = 0;

    Paint paint;

    final float DX_FACTOR = 25.0f;
    final float DY_FACTOR = 25.0f;
    final float INTENSITY_FACTOR = 120f;

    Bubble(float x_, float y_, float intensity_, Random rnd){
        x = x_;
        y = y_;

        radio = intensity_ * INTENSITY_FACTOR;

        dx = rnd.nextFloat() * DX_FACTOR - (DX_FACTOR / 2.0f);
        dy = rnd.nextFloat() * DY_FACTOR - (DY_FACTOR / 2.0f);

        paint = new Paint();
        int alpha = 150 + (int)(205 * rnd.nextFloat());
        paint.setARGB(alpha, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
    }
}
