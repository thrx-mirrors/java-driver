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

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

import java.io.IOException;

/**
 * A LZ4 implementation of {@link ChunkCompressor}.
 */
class LZ4ChunkCompressor implements ChunkCompressor {
    private final LZ4Compressor compressor;
    private final LZ4FastDecompressor decompressor;

    public LZ4ChunkCompressor() {
        final LZ4Factory lz4Factory = LZ4Factory.fastestInstance();
        compressor = lz4Factory.fastCompressor();
        decompressor = lz4Factory.fastDecompressor();
    }

    @Override
    public int maxCompressedLength(int length) {
        return compressor.maxCompressedLength(length);
    }

    @Override
    public int compressChunk(byte[] src, int srcOffset, int length, byte[] dest, int destOffset) throws IOException {
        return compressor.compress(src, srcOffset, length, dest, destOffset);
    }

    @Override
    public byte[] decompressChunk(byte[] src, int expectedDecompressedLength) throws IOException {
        return decompressor.decompress(src, expectedDecompressedLength);
    }
}
