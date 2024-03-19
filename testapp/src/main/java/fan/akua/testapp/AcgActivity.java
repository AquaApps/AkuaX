package fan.akua.testapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import fan.akua.widget.acgview.AcgImgProcessor;

public class AcgActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acg_activity);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_acg4);
        ImageView raw = findViewById(R.id.raw_img);
        ImageView canny = findViewById(R.id.canny_img);
        Bitmap cannyBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        raw.setImageBitmap(bitmap);
        long start = System.nanoTime();

        Bitmap process = AcgImgProcessor.process(cannyBitmap,  255);
        Log.e("simon", "process " + (System.nanoTime() - start));
        canny.setImageBitmap(process);
    }
}
