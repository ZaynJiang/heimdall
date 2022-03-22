package cn.heimdall.core.utils.common;

public class ArgsUtils {
    public static void parseArgs(String[] args) {
        for (String arg : args) {
            String[] param = arg.split("=");
            System.setProperty(param[0], param[1]);
        }
    }
}
