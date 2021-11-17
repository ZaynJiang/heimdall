package cn.heimdall.core.message.compress;


public interface Compressor {

    byte[] compress(byte[] bytes);

    byte[] decompress(byte[] bytes);

}
