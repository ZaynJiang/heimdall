package cn.heimdall.core.message.compress;



import cn.heimdall.core.utils.common.CollectionUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CompressorFactory {


    protected static final Map<CompressorType, Compressor> COMPRESSOR_MAP = new ConcurrentHashMap<>();

    static {
        COMPRESSOR_MAP.put(CompressorType.NONE, new NoneCompressor());
    }

    public static Compressor getCompressor(byte code) {
        CompressorType type = CompressorType.getByCode(code);
        return CollectionUtil.computeIfAbsent(COMPRESSOR_MAP, type,
            key -> null);
    }

    public static class NoneCompressor implements Compressor {
        @Override
        public byte[] compress(byte[] bytes) {
            return bytes;
        }

        @Override
        public byte[] decompress(byte[] bytes) {
            return bytes;
        }
    }

}
