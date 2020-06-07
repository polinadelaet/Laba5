package router.screen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class StringFileDAO {
    public static void save(String data, String absolutePath, Charset charset) throws IOException {
        Path path = Paths.get(absolutePath);

        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        if (Files.isDirectory(path)) {
            throw new IOException("File: " + path + " is a directory.");
        }

        if (!Files.isWritable(path)) {
            throw new IOException("File: " + path + " is not writable.");
        }

        Files.write(path, data.getBytes(charset.toString()));
    }

    public static String read(String absolutePath, Charset charset) throws IOException {
        Path path = Paths.get(absolutePath);

        if (!Files.exists(path)) {
            throw new FileNotFoundException();
        }

        if (Files.isDirectory(path)) {
            throw new IOException("File: " + path + " is a directory.");
        }

        if (!Files.isReadable(path)) {
            throw new IOException("File: " + path + " is not readable.");
        }

        byte[] bytes = Files.readAllBytes(path);

        return new String(bytes, charset.toString());
    }
}
