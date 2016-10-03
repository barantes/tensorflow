package org.tensorflow.demo;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.IOException;
import java.util.List;

/**
 * Created by Bruno on 31/08/2016.
 */
public class BirdImageClassifier {
    private static final String TAG = "BirdImgClassifier";
    private TensorFlowImageClassifier mTFClassifier;
    private static final BirdImageClassifier mInstance = new BirdImageClassifier();
    private boolean mConfigured;

    private static final int NUM_CLASSES = 404;
    private static final int INPUT_SIZE = 299;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;
    private static final String INPUT_NAME = "images";
    private static final  String OUTPUT_NAME = "softmax/logits";

    private static final String MODEL_FILE = "file:///android_asset/bird_model.pb";
    private static final String LABEL_FILE = "file:///android_asset/bird_labels.txt";

    public static BirdImageClassifier getInstance() {
        return mInstance;
    }

    private BirdImageClassifier() {
        mTFClassifier = new TensorFlowImageClassifier();
    }

    public BirdImageClassifier configure(AssetManager am) {
        if(!mConfigured) {
            initTF(am);
            mConfigured = true;
        }
        return this;
    }

    public List<Classifier.Recognition> identify(Bitmap bitmap) {
        Bitmap inputImage = resize(bitmap);
        List<Classifier.Recognition> results = mTFClassifier.recognizeImage(inputImage);
        return results;
    }

    private Bitmap resize(Bitmap bitmap) {
       return Bitmap.createScaledBitmap(bitmap, INPUT_SIZE,  INPUT_SIZE, false);
    }

    private void initTF(AssetManager am) {
        try {
            mTFClassifier.initializeTensorFlow(
                    am,
                    MODEL_FILE,
                    LABEL_FILE,
                    NUM_CLASSES,
                    INPUT_SIZE,
                    IMAGE_MEAN,
                    IMAGE_STD,
                    INPUT_NAME,
                    OUTPUT_NAME);
        } catch (IOException e) {
            Log.e(TAG, "initTF()", e);
        }
    }
}
