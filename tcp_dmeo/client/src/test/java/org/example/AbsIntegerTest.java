package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author by hyb
 * @date 2022/4/10 14:23
 */
public class AbsIntegerTest {

    @Test
    public void testAbsInteger() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i <= 10; i++) {
            buf.writeInt(-i);
        }
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        assertTrue(channel.writeOutbound(buf));
        assertTrue(channel.finish());
        for (int i = 1; i <= 10; i++) {
            assertEquals(i, channel.readOutbound());
        }
    }
}
