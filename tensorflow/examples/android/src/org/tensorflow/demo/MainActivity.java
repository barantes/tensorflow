package org.tensorflow.demo;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTF();
    }

    private void initTF() {
        BirdImageClassifier bic = BirdImageClassifier.getInstance().configure(getAssets());
    }
}
