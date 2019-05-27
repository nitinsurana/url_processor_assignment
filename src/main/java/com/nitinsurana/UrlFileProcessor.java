package com.nitinsurana;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

public class UrlFileProcessor {
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private File inputFile;
    private Result result = new Result();
    private final int MAX_TASKS_LENGTH = 1000;
    private int scheduled_tasks_length = 0;

    public UrlFileProcessor(String filePath) throws Exception {
        this.inputFile = new File(filePath);
        if (!this.inputFile.exists()) {
            throw new Exception("File/Dir not found : " + filePath);
        }
    }

    //This method call is asynchronous
    public Thread start() {
        Thread t = new Thread(() -> {
            if (inputFile.isDirectory()) {
                processDir(inputFile);
            } else {
                processFile(inputFile);
            }
        });
        t.start();
        return t;
    }

    private void processFile(File file) {
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String line = it.nextLine();
                CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(new UrlTask(line), executorService);
                future.thenApply(this::updateResult);
                scheduled_tasks_length++;
                if (scheduled_tasks_length == MAX_TASKS_LENGTH) {
                    synchronized (this) {
                        wait();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LineIterator.closeQuietly(it);
        }
    }

    private boolean updateResult(Boolean b) {
        if (b) {
            result.incSuccess();
        } else {
            result.incFailure();
        }
        scheduled_tasks_length--;
        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.NO_CLASS_NAME_STYLE));
        if (scheduled_tasks_length < MAX_TASKS_LENGTH / 2) {
            synchronized (this) {
                notify();
            }
        }
        return b;
    }

    private void processDir(File dir) {
        for (File f : dir.listFiles()) {
            processFile(f);
        }
    }

}
