package fan.akua.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import fan.akua.wrapper.effi.cache.Instance;
import fan.akua.wrapper.effi.cache.pool.IdleCachePool;

public class ShellActivity extends Activity {
    public IdleCachePool<Shell> shellIdleCachePool = new IdleCachePool<>(new Instance<Shell>() {
        @Override
        public Shell makeInstance() {
            Shell shell = new Shell();
            String logMsg = "shell create";
            Log.d("simon", logMsg);
            tv1.post(() -> tv2.setText(tv2.getText() + "\n" + logMsg));
            return shell;
        }

        @Override
        public boolean isUsing(Shell instance) {
            String logMsg = "shell check " + instance.isRunning();
            Log.d("simon", logMsg);
            tv1.post(() -> tv2.setText(tv2.getText() + "\n" + logMsg));
            return instance.isRunning();
        }

        @Override
        public void destroyInstance(Shell instance) {
            String logMsg = "shell destroy";
            Log.d("simon", logMsg);
            tv1.post(() -> tv2.setText(tv2.getText() + "\n" + logMsg));
            instance.destroy();
        }

        @Override
        public long checkCount() {
            return 1;
        }

        @Override
        public long checkInterval() {
            return 3 * 1000; // 3s
        }
    });
    TextView tv1;
    TextView tv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Trace.end("Effi");
        setContentView(R.layout.shell_layout);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
    }

    public void runShell(View view) {
        new Thread(() -> {
            String run = shellIdleCachePool.get().run();
            String logMsg = "shell run finish: " + run;
            Log.d("simon", logMsg);
            tv1.post(() -> tv2.setText(tv2.getText() + "\n" + logMsg));
            tv1.post(() -> tv1.setText(tv1.getText() + "\n" + run));
        }).start();
    }
}
