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

package io.github.cellgain.record;

import io.github.cellgain.audio.convert.InputAudioFormat;
import io.github.cellgain.audio.convert.MP3AudioConverter;
import io.github.cellgain.audio.convert.MP3Setting;
import io.github.cellgain.preference.UserPreferences;
import io.github.cellgain.record.wave.AudioMetadata;
import io.github.cellgain.record.wave.AudioMetadataUtils;
import io.github.cellgain.record.wave.WaveWriter;
import io.github.cellgain.sample.ConversionUtils;
import io.github.cellgain.audio.AudioFormats;
import io.github.cellgain.audio.AudioSegment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Recording utility for audio segments
 */
public class AudioSegmentRecorder
{
    private final static Logger mLog = LoggerFactory.getLogger(AudioSegmentRecorder.class);

    public static final int MP3_BIT_RATE = 16;
    public static final boolean CONSTANT_BIT_RATE = false;

    /**
     * Records the audio segment to the specified path using the specified recording format
     * @param audioSegment to record
     * @param path for the recording
     * @param recordFormat to use (WAVE, MP3)
     * @throws IOException on any errors
     */
    public static void record(AudioSegment audioSegment, Path path, RecordFormat recordFormat,
                              UserPreferences userPreferences) throws IOException
    {
        switch(recordFormat)
        {
            case MP3:
                recordMP3(audioSegment, path, userPreferences);
                break;
            case WAVE:
                recordWAVE(audioSegment, path);
                break;
            default:
                throw new IllegalArgumentException("Unrecognized recording format [" + recordFormat.name() + "]");
        }
    }

    /**
     * Records the audio segment as an MP3 file to the specified path.
     * @param audioSegment to record
     * @param path for the recording
     * @throws IOException on any errors
     */
    public static void recordMP3(AudioSegment audioSegment, Path path, UserPreferences userPreferences) throws IOException
    {
        if(audioSegment.hasAudio())
        {
            OutputStream outputStream = new FileOutputStream(path.toFile());

            //Write ID3 metadata
            Map<AudioMetadata,String> metadataMap = AudioMetadataUtils.getMetadataMap(audioSegment.getIdentifierCollection(),
                audioSegment.getAliasList());

            byte[] id3Bytes = AudioMetadataUtils.getMP3ID3(metadataMap);
            outputStream.write(id3Bytes);

            //Convert audio to MP3 and write to file
            InputAudioFormat inputAudioFormat = userPreferences.getMP3Preference().getAudioSampleRate();
            MP3Setting mp3Setting = userPreferences.getMP3Preference().getMP3Setting();

            boolean normalizeAudio = userPreferences.getMP3Preference().isNormalizeAudioBeforeEncode();

            MP3AudioConverter converter = new MP3AudioConverter(inputAudioFormat, mp3Setting, normalizeAudio);
            List<byte[]> mp3Frames = converter.convert(audioSegment.getAudioBuffers());
            for(byte[] mp3Frame: mp3Frames)
            {
                outputStream.write(mp3Frame);
            }

            List<byte[]> lastFrames = converter.flush();

            if(!lastFrames.isEmpty())
            {
                for(byte[] lastFrame: lastFrames)
                {
                    outputStream.write(lastFrame);
                }
            }

            outputStream.flush();
            outputStream.close();
        }
    }

    /**
     * Records the audio segment as a WAVe file to the specified path.
     * @param audioSegment to record
     * @param path for the recording
     * @throws IOException on any errors
     */
    public static void recordWAVE(AudioSegment audioSegment, Path path) throws IOException
    {
        if(audioSegment.hasAudio())
        {
            WaveWriter writer = new WaveWriter(AudioFormats.PCM_SIGNED_8000_HZ_16_BIT_MONO, path);

            for(float[] audioBuffer: audioSegment.getAudioBuffers())
            {
                writer.writeData(ConversionUtils.convertToSigned16BitSamples(audioBuffer));
            }

            Map<AudioMetadata,String> metadataMap = AudioMetadataUtils.getMetadataMap(audioSegment.getIdentifierCollection(),
                audioSegment.getAliasList());

            ByteBuffer listChunk = AudioMetadataUtils.getLISTChunk(metadataMap);
            byte[] id3Bytes = AudioMetadataUtils.getMP3ID3(metadataMap);
            ByteBuffer id3Chunk = AudioMetadataUtils.getID3Chunk(id3Bytes);
            writer.writeMetadata(listChunk, id3Chunk);
            writer.close();
        }
    }
}
