package com.digicore.banking.dao;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class MockORM {


    public static boolean create(String filename) {
        try {
            File f = new File(filename);
            return f.createNewFile();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean write(String filename, String s) {
        try (BufferedWriter bw = Files.newBufferedWriter(Path.of(filename))) {
            bw.write(s);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Stream<String> read(String filename) {
        try {
            BufferedReader br = Files.newBufferedReader(Path.of(filename));
            return br.lines();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void delete(String filename) {
        try {
            Files.deleteIfExists(Path.of(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}





















