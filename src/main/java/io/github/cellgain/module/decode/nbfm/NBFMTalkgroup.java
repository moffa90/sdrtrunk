package io.github.cellgain.module.decode.nbfm;

import io.github.cellgain.identifier.Role;
import io.github.cellgain.identifier.talkgroup.TalkgroupIdentifier;
import io.github.cellgain.protocol.Protocol;

/**
 * Narrowband FM talkgroup.  Note: this is a logical identifier that is assignable from a user-specified
 * configuration so that NBFM audio events are compatible with other features of the sdrapp system.
 */
public class NBFMTalkgroup extends TalkgroupIdentifier
{
    /**
     * Constructs an instance
     * @param value 1-65,535
     */
    public NBFMTalkgroup(int value)
    {
        super(value, Role.TO);
    }

    @Override
    public Protocol getProtocol()
    {
        return Protocol.NBFM;
    }
}
