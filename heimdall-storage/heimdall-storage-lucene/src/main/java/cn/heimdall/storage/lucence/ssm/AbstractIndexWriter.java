package cn.heimdall.storage.lucence.ssm;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public abstract class AbstractIndexWriter {
    protected Directory dir;
    protected IndexWriterConfig indexWriterConfig;

    public AbstractIndexWriter(String path) throws IOException {
        this.dir = FSDirectory.open(Paths.get(path));
    }

    protected IndexWriter getWriter() throws Exception {
        IndexWriter writer = new IndexWriter(dir, indexWriterConfig);
        return writer;
    }
}
