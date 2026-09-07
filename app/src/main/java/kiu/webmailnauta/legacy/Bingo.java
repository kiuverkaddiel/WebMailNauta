package kiu.webmailnauta.legacy;

import java.util.HashSet;
import java.util.Set;

import kiu.webmailnauta.legacy.db.DataBaseHelper;

public class Bingo {
    public static final String TABLE_BINGO = "bingo";
    private Long id;
    private String[] tiro = new String[4];

    public Bingo(Long id2, String centene, String fijo, String corrido1, String corrido2) {
        this.id = id2;
        if (centene == null || !centene.isEmpty()) {
            this.tiro[0] = centene;
        } else {
            this.tiro[0] = null;
        }
        String[] strArr = this.tiro;
        strArr[1] = fijo;
        strArr[2] = corrido1;
        strArr[3] = corrido2;
    }

    public Bingo(String[] bingo) {
        this.id = Long.valueOf(bingo[0]);
        if (bingo[1] == null || !bingo[1].isEmpty()) {
            this.tiro[0] = bingo[1];
        } else {
            this.tiro[0] = null;
        }
        String[] strArr = this.tiro;
        strArr[1] = bingo[2];
        strArr[2] = bingo[3];
        strArr[3] = bingo[4];
    }

    public String[] toArrayString() {
        String[] convert = new String[5];
        convert[0] = this.id.toString();
        for (int i = 0; i < 4; i++) {
            convert[i + 1] = this.tiro[i];
        }
        return convert;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id2) {
        this.id = id2;
    }

    public String[] getParlletCombination() {
        Set<String> combination = new HashSet<>();
        for (int i = 1; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                combination.add(this.tiro[i] + Constants.SPACE + this.tiro[j]);
                combination.add(this.tiro[j] + Constants.SPACE + this.tiro[i]);
            }
        }
        return (String[]) combination.toArray(new String[combination.size()]);
    }

    public String getCentene() {
        return this.tiro[0];
    }

    public String getFijo() {
        return this.tiro[1];
    }

    public String getCorrido1() {
        return this.tiro[2];
    }

    public String getCorrido2() {
        return this.tiro[3];
    }

    public String getSeven() {
        String[] strArr = this.tiro;
        if (strArr[0] == null) {
            return null;
        }
        String seven = Constants.NULL_STRING;
        for (String n : strArr) {
            seven = seven + n;
        }
        return seven;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        if (getCentene() != null) {
            str = getCentene() + "-";
        } else {
            str = Constants.NULL_STRING;
        }
        sb.append(str);
        sb.append(getFijo());
        sb.append("-");
        sb.append(getCorrido1());
        sb.append("-");
        sb.append(getCorrido2());
        return sb.toString();
    }

    public int hashCode() {
        int i = 1 * 31;
        Long l = this.id;
        return i + (l == null ? 0 : l.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Bingo other = (Bingo) obj;
        Long l = this.id;
        if (l == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!l.equals(other.id)) {
            return false;
        }
        return true;
    }

    public void save(DataBaseHelper db) {
        db.insertBingo(this);
    }

    public void update(DataBaseHelper db) {
        db.updateBingo(this);
    }

    public boolean isDoubleBingo(String number) {
        int count = 0;
        for (int i = 1; i < 4; i++) {
            if (this.tiro[i].equals(number)) {
                count++;
            }
        }
        return count > 1;
    }
}
