package tyc.allen.com.foldablelayout;

import android.app.Activity;
import android.os.Bundle;

import tyc.allen.com.foldablelayoutlibrary.FoldableLayout;

public class DemoActivity extends Activity {
    private FoldableLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        fl = (FoldableLayout) findViewById(R.id.fl);
        fl.init();
        fl.setOnClickListener(fl.getFoldControlListener());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
