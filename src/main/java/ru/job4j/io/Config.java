package ru.job4j.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

public class Config {
    private final String path;
    private final Map<String, String> values = new HashMap<String, String>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines()
                    .filter(line -> !line.startsWith("#"))
                    .filter(line -> !line.equals(""))
                    .forEach(line -> {
                    String[] s = line.split("=");
                    try {
                        if (s.length == 1) {
                            throw new IllegalArgumentException();
                        }
                        values.put(s[0], s[1]);
                    } catch (Exception error) {
                        throw new RuntimeException(error);
                    }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException();
        }
    }

    public String value(String key) {
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Config("app.properties"));
    }
}