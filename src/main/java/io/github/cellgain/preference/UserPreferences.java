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

package io.github.cellgain.preference;

import io.github.cellgain.preference.calibration.VectorCalibrationPreference;
import io.github.cellgain.preference.decoder.JmbeLibraryPreference;
import io.github.cellgain.preference.directory.DirectoryPreference;
import io.github.cellgain.preference.duplicate.DuplicateCallDetectionPreference;
import io.github.cellgain.preference.event.DecodeEventPreference;
import io.github.cellgain.preference.identifier.TalkgroupFormatPreference;
import io.github.cellgain.preference.javafx.JavaFxPreferences;
import io.github.cellgain.preference.mp3.MP3Preference;
import io.github.cellgain.preference.playback.PlaybackPreference;
import io.github.cellgain.preference.playlist.PlaylistPreference;
import io.github.cellgain.preference.radioreference.RadioReferencePreference;
import io.github.cellgain.preference.record.RecordPreference;
import io.github.cellgain.preference.source.ChannelMultiFrequencyPreference;
import io.github.cellgain.preference.source.TunerPreference;
import io.github.cellgain.preference.swing.SwingPreference;
import io.github.cellgain.eventbus.MyEventBus;
import io.github.cellgain.sample.Listener;

/**
 * User Preferences.  A collection of preferences that can be accessed by preference type.
 *
 * Note: user preference updates are broadcast throughout the system using the Google Guava Event Bus.  Each component
 * can register with the event bus and annotate a method to receive updates:
 *
 * To register a component to receive events, add this in the constructor:
 * MyEventBus.getEventBus().register(this);
 *
 * To receive preference update notifications, annotate a method with @Subscribe and use a PreferenceType argument:
 *
 * @Subscribe
 * public void preferenceUpdated(PreferenceType preferenceType)
 * {
 * }
 */
public class UserPreferences implements Listener<PreferenceType>
{
    private VectorCalibrationPreference mVectorCalibrationPreference;
    private ChannelMultiFrequencyPreference mChannelMultiFrequencyPreference;
    private DecodeEventPreference mDecodeEventPreference;
    private DirectoryPreference mDirectoryPreference;
    private DuplicateCallDetectionPreference mDuplicateCallDetectionPreference;
    private JmbeLibraryPreference mJmbeLibraryPreference;
    private MP3Preference mMP3Preference;
    private PlaybackPreference mPlaybackPreference;
    private PlaylistPreference mPlaylistPreference;
    private RadioReferencePreference mRadioReferencePreference;
    private RecordPreference mRecordPreference;
    private TalkgroupFormatPreference mTalkgroupFormatPreference;
    private TunerPreference mTunerPreference;

    private SwingPreference mSwingPreference = new SwingPreference();
    private JavaFxPreferences mJavaFxPreferences = new JavaFxPreferences();

    /**
     * Constructs a new user preferences instance
     */
    public UserPreferences()
    {
        loadPreferenceTypes();
    }

    public VectorCalibrationPreference getVectorCalibrationPreference()
    {
        return mVectorCalibrationPreference;
    }

    /**
     * Java FX window management preferences
     */
    public JavaFxPreferences getJavaFxPreferences()
    {
        return mJavaFxPreferences;
    }

    /**
     * Decode Event preferences
     */
    public DecodeEventPreference getDecodeEventPreference()
    {
        return mDecodeEventPreference;
    }

    /**
     * Decoder preferences
     */
    public JmbeLibraryPreference getJmbeLibraryPreference()
    {
        return mJmbeLibraryPreference;
    }

    /**
     * Directory preferences
     */
    public DirectoryPreference getDirectoryPreference()
    {
        return mDirectoryPreference;
    }

    /**
     * Multiple frequency channel source preferences
     */
    public ChannelMultiFrequencyPreference getChannelMultiFrequencyPreference()
    {
        return mChannelMultiFrequencyPreference;
    }

    /**
     * Audio playback preferences
     */
    public PlaybackPreference getPlaybackPreference()
    {
        return mPlaybackPreference;
    }

    /**
     * Playlist preferences
     */
    public PlaylistPreference getPlaylistPreference()
    {
        return mPlaylistPreference;
    }

    /**
     * Radio reference web services preferences
     */
    public RadioReferencePreference getRadioReferencePreference()
    {
        return mRadioReferencePreference;
    }

    /**
     * Recording preferences
     */
    public RecordPreference getRecordPreference()
    {
        return mRecordPreference;
    }

    /**
     * MP3 preferences
     */
    public MP3Preference getMP3Preference()
    {
        return mMP3Preference;
    }

    /**
     * Identifier preferences
     */
    public TalkgroupFormatPreference getTalkgroupFormatPreference()
    {
        return mTalkgroupFormatPreference;
    }

    /**
     * Tuner preferences
     */
    public TunerPreference getTunerPreference()
    {
        return mTunerPreference;
    }


    /**
     * Swing window location/size user preferences
     */
    public SwingPreference getSwingPreference()
    {
        return mSwingPreference;
    }

    /**
     * Duplicate call detection preferences
     */
    public DuplicateCallDetectionPreference getDuplicateCallDetectionPreference()
    {
        return mDuplicateCallDetectionPreference;
    }

    /**
     * Loads the managed preferences
     */
    private void loadPreferenceTypes()
    {
        mVectorCalibrationPreference = new VectorCalibrationPreference(this::receive);
        mChannelMultiFrequencyPreference = new ChannelMultiFrequencyPreference(this::receive);
        mDecodeEventPreference = new DecodeEventPreference(this::receive);
        mDirectoryPreference = new DirectoryPreference(this::receive);
        mDuplicateCallDetectionPreference = new DuplicateCallDetectionPreference(this::receive);
        mJmbeLibraryPreference = new JmbeLibraryPreference(this::receive);
        mMP3Preference = new MP3Preference(this::receive);
        mPlaybackPreference = new PlaybackPreference(this::receive);
        mPlaylistPreference = new PlaylistPreference(this::receive, mDirectoryPreference);
        mRadioReferencePreference = new RadioReferencePreference(this::receive);
        mRecordPreference = new RecordPreference(this::receive);
        mTalkgroupFormatPreference = new TalkgroupFormatPreference(this::receive);
        mTunerPreference = new TunerPreference(this::receive);
    }

    /**
     * Primary method for receiving notification from a managed preference that a preference type
     * has been changed/updated.
     *
     * @param preferenceType that is updated
     */
    @Override
    public void receive(PreferenceType preferenceType)
    {
        MyEventBus.getGlobalEventBus().post(preferenceType);
    }
}
