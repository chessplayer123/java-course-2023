package edu.hw8.PasswordHacker;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PasswordHacker {
    private static final String ALPHABET  = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private Map<String, List<String>> users;
    private Map<String, String> passwords;

    public PasswordHacker() {
        users = new HashMap<>();
        passwords = new HashMap<>();
    }

    private void hackRange(long start, long end) throws NoSuchAlgorithmException {
        final MessageDigest hashFunction = MessageDigest.getInstance("MD5");

        for (long index = start; index < end; ++index) {
            String password = BinaryUtils.convertToRadix(index, ALPHABET);
            String hash = BinaryUtils.bytesToHex(hashFunction.digest(password.getBytes()));

            if (users.containsKey(hash)) {
                synchronized (password) {
                    for (String mail : users.get(hash)) {
                        passwords.put(mail, password);
                    }
                }
            }
        }
    }

    private Collection<Callable<Void>> divideRangeIntoTasks(int length, int threadsNum) {
        long limit = (long) Math.pow(ALPHABET.length(), length);

        Collection<Callable<Void>> workers = new ArrayList<>(threadsNum);

        long rangeSize = (long) Math.ceil((double) limit / threadsNum);
        for (int threadI = 0; threadI < threadsNum; ++threadI) {
            long start = threadI * rangeSize;
            long end = start + rangeSize;

            workers.add(() -> {
                hackRange(start, end);
                return null;
            });
        }

        return workers;
    }

    public Map<String, String> hackPasswords(
        BufferedReader data,
        int lengthLimit,
        int threadsNum
    ) throws IOException, ExecutionException, InterruptedException {
        users.clear();
        String line;
        while ((line = data.readLine()) != null) {
            String[] parts = line.split(" ");
            if (parts.length != 2) {
                throw new IOException("wrong data format");
            }

            users.putIfAbsent(parts[1], new ArrayList<>());
            users.get(parts[1]).add(parts[0]);
        }

        Collection<Callable<Void>> tasks = divideRangeIntoTasks(lengthLimit, threadsNum);

        passwords.clear();
        try (ExecutorService pool = Executors.newFixedThreadPool(threadsNum)) {
            List<Future<Void>> futures = pool.invokeAll(tasks);
            for (Future<Void> future : futures) {
                future.get();
            }
        }

        return passwords;
    }
}
