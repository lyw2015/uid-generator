package com.baidu.fsg.uid.handler;

import com.baidu.fsg.uid.core.buffer.RejectedPutBufferHandler;
import com.baidu.fsg.uid.core.buffer.RingBuffer;

/**
 * @ProjectName uid-generator
 * @Author Laiyw
 * @CreateTime 2022/5/6 9:32
 * @Description TODO
 */

public class CustomRejectedPutBufferHandler implements RejectedPutBufferHandler {

    @Override
    public void rejectPutBuffer(RingBuffer ringBuffer, long uid) {
        // TODO:
    }
}
