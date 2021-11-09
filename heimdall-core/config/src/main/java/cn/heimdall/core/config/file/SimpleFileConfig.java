package cn.heimdall.core.config.file;

import cn.heimdall.core.utils.annotation.LoadLevel;
import cn.heimdall.core.utils.enums.Scope;

import java.io.File;

@LoadLevel(name = FileConfigFactory.DEFAULT_TYPE, scope = Scope.PROTOTYPE)
public class SimpleFileConfig implements FileConfig {

    public SimpleFileConfig(File file, String name) {
    }

    @Override
    public String getString(String path) {
      //TODO
        return null;
    }
}
