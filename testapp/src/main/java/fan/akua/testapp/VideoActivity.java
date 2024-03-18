package fan.akua.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import fan.akuax.widget.fixes.FixKeyEventMediaController;

public class VideoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);
        VideoView viewById = findViewById(R.id.video_view);

        viewById.setVideoPath("https://vfx.mtime.cn/Video/2019/07/12/mp4/190712140656051701.mp4");
        viewById.setMediaController(new FixKeyEventMediaController(this));
    }
}
