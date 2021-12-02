package cn.heimdall.core.utils.common;

import java.util.stream.Stream;

public class ArgsUtils {
    public static void parseArgs(String[] args) {
        Stream.of(args).forEach(arg -> {
            String[] param = arg.split("=");
            System.setProperty(param[0], param[1]);
        });
    }
}
