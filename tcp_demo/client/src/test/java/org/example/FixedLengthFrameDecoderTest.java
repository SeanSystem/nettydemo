package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author by hyb
 * @date 2022/4/10 14:04
 */
public class FixedLengthFrameDecoderTest {

    @Test
    public void testFixedLengthFrameDecoder() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        ByteBuf input = buf.duplicate();
        assertTrue(embeddedChannel.writeInbound(input.retain()));
        assertTrue(embeddedChannel.finish());
        ByteBuf read = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(embeddedChannel.readInbound());
        buf.release();
    }

    @Test
    public void testFixedLengthFrameDecoder2() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        ByteBuf input = buf.duplicate();
        assertFalse(embeddedChannel.writeInbound(input.readBytes(2)));
        assertTrue(embeddedChannel.writeInbound(input.readBytes(7)));
        assertTrue(embeddedChannel.finish());

        ByteBuf read = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(embeddedChannel.readInbound());
        buf.release();
    }
}
