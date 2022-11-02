package io.github.cellgain.dsp.gain.complex;

import io.github.cellgain.sample.complex.ComplexSamples;

/**
 * Interface for complex gain control
 */
public interface IComplexGainControl
{
    /**
     * Processes a buffer of complex I & Q samples and applies gain to achieve an objective unity gain.
     * @param i samples
     * @param q samples
     * @return amplified gain samples
     */
    ComplexSamples process(float[] i, float[] q);
}
