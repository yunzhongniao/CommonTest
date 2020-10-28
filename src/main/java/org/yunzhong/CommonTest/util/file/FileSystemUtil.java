package org.yunzhong.CommonTest.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author yunzhong
 *
 */
public class FileSystemUtil {

    public static void printSmallFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        System.out.println(Files.readString(path));
    }

    public static void printSmallFileByLine(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<String> readAllLines = Files.readAllLines(path);
        for (String line : readAllLines) {
            System.out.println(line);
        }
    }

    public static void printFileByStream(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
                System.out.println(readLine);
            }
        }
    }

    public static void printAsync(String filePath) throws IOException, InterruptedException, ExecutionException {
        Path file = Paths.get(filePath);
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(file)) {
            // 从jdk7开始，支持数字中下划线，增强数字的可读性
            // ByteBuffer buffer = ByteBuffer.allocate(4_000);
            ByteBuffer buffer = ByteBuffer.allocate(4);
            int position = 0;
            while (true) {
                Future<Integer> result = channel.read(buffer, position);
                while (!result.isDone()) {
                }
                if (result.get() <= 0) {
                    System.out.println(" ");
                    System.out.println("all data done.");
                    break;
                }
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                position += 4;
                buffer.clear();
            }
            System.out.println(" ");
        }
    }

    public static void printAsyncHandler(String filePath) throws IOException {
        Path file = Paths.get(filePath);
        ByteBuffer buffer = ByteBuffer.allocate(4_000_000);
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(file)) {
            int position = 0;
            channel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    attachment.flip();
                    while (attachment.hasRemaining()) {
                        System.out.print((char) attachment.get());
                    }
                    attachment.clear();
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.out.println("error" + exc.getLocalizedMessage());
                }
            });
        }
    }

    public static void writeSmallFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        String content = "Hello,this is new content.\r\n";
        Files.writeString(path, content, StandardOpenOption.APPEND);

        List<String> lines = new ArrayList<String>();
        lines.add("first line");
        lines.add("second line");
        Files.write(path, lines, StandardOpenOption.APPEND);
    }

    public static void writeFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            for (int i = 0; i < 10; i++) {
                bufferedWriter.write("Hello");
                bufferedWriter.newLine();
            }
        }
    }
}
