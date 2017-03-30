/*
 *      Copyright (C) 2012-2015 DataStax Inc.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.datastax.driver.core;


import java.io.IOException;

class ChecksummingFrameCompressor extends FrameCompressor {

    static final ChecksummingFrameCompressor UNCOMPRESSED =
            new ChecksummingFrameCompressor(Checksummer.UNCOMPRESSED);

    static final ChecksummingFrameCompressor LZ4_COMPRESSED =
            new ChecksummingFrameCompressor(Checksummer.LZ4_COMPRESSED);

    private final Checksummer checksummer;

    ChecksummingFrameCompressor(Checksummer checksummer) {
        this.checksummer = checksummer;
    }

    @Override
    Frame compress(Frame frame) throws IOException {
        return frame.with(checksummer.transformOutbound(frame.body));
    }

    @Override
    Frame decompress(Frame frame) throws IOException {
        return frame.with(checksummer.transformInbound(frame.body));
    }
}
