package com.edutrack.repository;

import com.edutrack.model.Student;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FileDataStore implements Repository<Student, String> {
    private final Path file;

    public FileDataStore(String filename) {
        this.file = Paths.get(filename);
    }

    @Override
    public synchronized void save(Student student) {
        try {
            Path parent = file.getParent();
            if (parent != null) Files.createDirectories(parent);
            List<String> lines = new ArrayList<>();
            for (Student s : findAll()) {
                if (!s.getId().equals(student.getId())) lines.add(encode(s));
            }
            lines.add(encode(student));
            Files.write(file, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save student data.", e);
        }
    }

    private String encode(Student s) {
        return String.join("|", s.getId(), s.getName(), s.getEmail(), s.getDepartment(), String.valueOf(s.getMaxCredits()));
    }

    private Student decode(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length != 5) throw new IllegalArgumentException("Invalid student record.");
        return new Student(p[0], p[1], p[2], p[3], Integer.parseInt(p[4]));
    }

    @Override
    public synchronized Optional<Student> findById(String id) {
        return findAll().stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    @Override
    public synchronized List<Student> findAll() {
        if (!Files.exists(file)) return new ArrayList<>();
        try {
            List<Student> result = new ArrayList<>();
            for (String line : Files.readAllLines(file)) {
                if (!line.isBlank()) result.add(decode(line));
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Unable to read student data.", e);
        }
    }
}
