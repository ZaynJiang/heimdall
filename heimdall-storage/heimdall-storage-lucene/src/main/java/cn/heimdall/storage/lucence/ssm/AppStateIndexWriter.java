package cn.heimdall.storage.lucence.ssm;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;

import java.io.IOException;

public class AppStateIndexWriter extends AbstractIndexWriter {

    public AppStateIndexWriter(String path) throws IOException {
        super(path);
        indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
    }
}
