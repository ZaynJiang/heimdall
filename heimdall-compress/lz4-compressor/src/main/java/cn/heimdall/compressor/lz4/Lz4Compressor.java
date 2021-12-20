package cn.heimdall.compressor.lz4;


import cn.heimdall.core.message.compress.Compressor;

public class Lz4Compressor implements Compressor {
    @Override
    public byte[] compress(byte[] bytes) {
        return Lz4Util.compress(bytes);
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        return Lz4Util.decompress(bytes);
    }
}
