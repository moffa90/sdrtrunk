/*
 * *****************************************************************************
 * Copyright (C) 2014-2022 Dennis Sheirer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 * ****************************************************************************
 */
package io.github.cellgain.module.decode.ltrnet;

import io.github.cellgain.bits.MessageFramer;
import io.github.cellgain.bits.SyncPattern;
import io.github.cellgain.dsp.fsk.LTRDecoder;
import io.github.cellgain.message.MessageDirection;
import io.github.cellgain.module.decode.Decoder;
import io.github.cellgain.module.decode.DecoderType;
import io.github.cellgain.sample.Listener;
import io.github.cellgain.sample.real.IRealBufferListener;

public class LTRNetDecoder extends Decoder implements IRealBufferListener, Listener<float[]>
{
    public static final int LTR_NET_MESSAGE_LENGTH = 40;
    protected LTRDecoder mLTRDecoder;
    private MessageFramer mLTRMessageFramer;
    private LTRNetMessageProcessor mLTRMessageProcessor;

    /**
     * LTR-Net Decoder.  Decodes unfiltered (e.g. demodulated but with no DC or
     * audio filtering) samples and produces LTR-Net messages.
     */
    public LTRNetDecoder(DecodeConfigLTRNet config, LTRDecoder ltrDecoder)
    {
        mLTRDecoder = ltrDecoder;

        if(config.getMessageDirection() == MessageDirection.OSW)
        {
            mLTRMessageFramer = new MessageFramer(SyncPattern.LTR_STANDARD_OSW.getPattern(), LTR_NET_MESSAGE_LENGTH);
        }
        else
        {
            mLTRMessageFramer = new MessageFramer(SyncPattern.LTR_STANDARD_ISW.getPattern(), LTR_NET_MESSAGE_LENGTH);
        }

        mLTRDecoder.setMessageFramer(mLTRMessageFramer);
        mLTRMessageFramer.setSyncDetectListener(mLTRDecoder);
        mLTRMessageProcessor = new LTRNetMessageProcessor(config.getMessageDirection());
        mLTRMessageFramer.addMessageListener(mLTRMessageProcessor);
        mLTRMessageProcessor.setMessageListener(getMessageListener());
    }

    /**
     * LTR-Net Decoder.  Decodes unfiltered (e.g. demodulated but with no DC or
     * audio filtering) samples and produces LTR-Net messages.
     */
    public LTRNetDecoder(DecodeConfigLTRNet config)
    {
        this(config, new LTRDecoder(LTR_NET_MESSAGE_LENGTH));
    }

    @Override
    public DecoderType getDecoderType()
    {
        return DecoderType.LTR_NET;
    }

    @Override
    public Listener<float[]> getBufferListener()
    {
        return mLTRDecoder;
    }

    @Override
    public void receive(float[] reusableFloatBuffer)
    {
        mLTRDecoder.receive(reusableFloatBuffer);
    }

    @Override
    public void reset()
    {
        mLTRMessageFramer.reset();
    }
}