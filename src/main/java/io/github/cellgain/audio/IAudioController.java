package io.github.cellgain.audio;

import io.github.cellgain.audio.playback.AudioOutput;
import io.github.cellgain.sample.Listener;
import io.github.cellgain.source.mixer.MixerChannelConfiguration;

import java.util.List;

/**
 * Interface for controlling one or more audio channels
 */
public interface IAudioController
{
	/* Current Mixer and MixerChannel configuration */
	public void setMixerChannelConfiguration( MixerChannelConfiguration entry ) throws AudioException;
	public MixerChannelConfiguration getMixerChannelConfiguration() throws AudioException;
	
	/* Audio Output(s) */
	public List<AudioOutput> getAudioOutputs();

	/* Controller Audio Event Listener */
	public void addControllerListener( Listener<AudioEvent> listener );
	public void removeControllerListener( Listener<AudioEvent> listener );
}
