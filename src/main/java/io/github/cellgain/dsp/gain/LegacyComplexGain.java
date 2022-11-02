package io.github.cellgain.dsp.gain;

import io.github.cellgain.sample.complex.Complex;
import io.github.cellgain.sample.Listener;

public class LegacyComplexGain implements Listener<Complex>
{
    private float mGain;
    private Listener<Complex> mListener;

    public LegacyComplexGain(float gain)
    {
        mGain = gain;
    }

    public Complex apply(Complex sample)
    {
        sample.multiply(mGain);

        return sample;
    }

    @Override
    public void receive(Complex sample)
    {
        if(mListener != null)
        {
            apply(sample);

            mListener.receive(sample);
        }
    }

    public void setListener(Listener<Complex> listener)
    {
        mListener = listener;
    }
}
