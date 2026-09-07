package kiu.webmailnauta.legacy.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kiu.webmailnauta.legacy.Constants;

public class Cursor {
    private final List<String> lines = new ArrayList();
    private int pos = -1;

    public Cursor(File file_path) {
        try {
            createCursor(file_path);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Cursor(String[] lines2) {
        try {
            createCursor(lines2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Cursor(String list) {
        try {
            createCursor(list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean isCreated() {
        return !this.lines.isEmpty();
    }

    private void createCursor(File list_file) throws Exception {
        if (list_file != null) {
            try {
                FileReader r = new FileReader(list_file);
                String line = Constants.NULL_STRING;
                for (int c = r.read(); c != -1; c = r.read()) {
                    char cc = (char) c;
                    if (cc == 10) {
                        if (line.length() > 2) {
                            this.lines.add(line);
                        }
                        line = Constants.NULL_STRING;
                    } else {
                        line = line + cc;
                    }
                }
                if (!isCreated()) {
                    throw new Exception("Can't create cursor becouse file is empty");
                }
            } catch (FileNotFoundException e) {
                throw new Exception("Can't create cursor becouse can't read file " + list_file);
            }
        } else {
            throw new Exception("Can't create cursor becouse file_path is null");
        }
    }

    private void createCursor(String list_string) {
        createCursor(list_string.split("\n"));
    }

    private void createCursor(String[] lines2) {
        Collections.addAll(this.lines, lines2);
    }

    private void createCursor(List<String> lines2) {
        this.lines.addAll(lines2);
    }

    public void moveToFirstLine() throws Exception {
        if (isCreated()) {
            this.pos = -1;
            return;
        }
        throw new Exception("Can't move, cursor is uncreated");
    }

    public void moveToLastLine() throws Exception {
        if (isCreated()) {
            this.pos = this.lines.size() - 2;
            return;
        }
        throw new Exception("Can't move, cursor is uncreated");
    }

    public String getNextLine() throws Exception {
        if (isCreated()) {
            int i = this.pos + 1;
            this.pos = i;
            if (i < this.lines.size()) {
                return this.lines.get(this.pos);
            }
            throw new Exception("Cursor can't move");
        }
        throw new Exception("Can't move, cursor is uncreated");
    }

    public String getLine() throws Exception {
        if (isCreated()) {
            int i = this.pos;
            if (i != -1) {
                return this.lines.get(i);
            }
            throw new Exception("Out of bounds, pos is -1");
        }
        throw new Exception("Non values, cursor is uncreated");
    }

    public boolean hasNext() {
        return this.pos < this.lines.size() - 1;
    }

    public int countLines() {
        return this.lines.size();
    }

    public int getLineNumber() {
        return this.pos;
    }
}
