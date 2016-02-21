package com.interaxon.test.libmuse;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Martin on 20/02/2016.
 */
public class SignalAnalyzer {
    private float coef0 = 0f;
    private float FFTWeight = 0.5212189f;
    private float ApEnWeight = 0.535182f;
    private float sampEnWeight = 0.3826242f;

    private FFTDetector fftd;


    SignalAnalyzer() {
        fftd = new FFTDetector(10, 10);

    }

    void analyze(Signal signal) {
        new ComputeTask().execute(signal);
    }

    private class ComputeTask extends AsyncTask<Signal, Void, Float> {
        protected Float doInBackground(Signal... params) {
            float[] s = new float[params.length];
            float[] si = params[0].toFloatArray();

            float value = 0;

            float apen = ApEn.computeF(si);
            //Compute sampEn
            float sampEn = SampEn.computeF(si);
            //Compute fft
            float fft = fftd.computeF(si);
            return coef0 + FFTWeight*fft + ApEnWeight*apen + sampEnWeight*sampEn;
        }

        protected void onPostExecute(Float result) {
            Log.d("Computed ",result.toString());
        }
    }
}
