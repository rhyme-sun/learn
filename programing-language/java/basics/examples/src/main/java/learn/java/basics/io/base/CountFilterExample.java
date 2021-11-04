package learn.java.basics.io.base;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

/**
 * CountInputStream.
 */
@Slf4j
public class CountFilterExample {

    public static void main(String[] args) {
        byte[] data = "Hello Simon!".getBytes(StandardCharsets.UTF_8);
        try (CountInputStream input = new CountInputStream(new ByteArrayInputStream(data))) {
            int n;
            while ((n = input.read()) != -1) {
                log.info("{}", (char) n);
            }
            log.info("Total bytes: {}", input.getBytesRead());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class CountInputStream extends FilterInputStream {

    private int count = 0;

    protected CountInputStream(InputStream in) {
        super(in);
    }

    public int getBytesRead() {
        return this.count;
    }

    @Override
    public int read() throws IOException {
        int n = in.read();
        if (n != -1) {
            this.count++;
        }
        return n;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int n = in.read(b, off, len);
        if (n != -1) {
            this.count += n;
        }
        return n;
    }
}