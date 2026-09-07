package kiu.webmailnauta.legacy;

import kiu.webmailnauta.legacy.db.DataBaseHelper;
import kiu.webmailnauta.legacy.db.RowDb;

public abstract class Row implements RowDb {
    protected static Config config = Config.getInstance();
    protected StringBuilder _data;
    protected long _id;
    protected StringBuilder _unlockKeys = new StringBuilder(78);
    protected long createdAt;

    public abstract void AddCharacter(char c);

    public abstract void addPriceMap(Price price);

    /* access modifiers changed from: protected */
    public abstract boolean canAddChar();

    public abstract void delPriceMap(Price price);

    public abstract Float[] getBasePrice();

    public abstract int getColor(Bingo bingo, String[] strArr);

    public abstract String getError(Price price);

    /* access modifiers changed from: protected */
    public abstract String getError(Price price, int i);

    public abstract void getPay(Bingo bingo, Price price, String[] strArr, String[] strArr2);

    public abstract Float[] getPrice();

    /* access modifiers changed from: protected */
    public abstract void updateKeyboard();

    public Row() {
        KeyboardOnlyNumbers();
        this._data = new StringBuilder(Constants.NULL_STRING);
        this.createdAt = System.currentTimeMillis();
    }

    public Row(ContentValues vrow) {
        this._id = vrow.getAsLong(Columns._ID).longValue();
        this.createdAt = vrow.getAsLong(Columns.CREATED_AT).longValue();
        this._data = vrow.getAsStringBuilder(Columns.DATA);
    }

    public String getData() {
        return this._data.toString();
    }

    public void setData(String data) {
        this._data = new StringBuilder(data);
    }

    public String getRowString() {
        StringBuilder row = new StringBuilder();
        row.append(this._id);
        row.append(',');
        row.append(this.createdAt);
        row.append(',');
        row.append(this._data);
        row.append(',');
        row.append(getType());
        return row.toString();
    }

    public long getId() {
        return this._id;
    }

    public long getCreatedAt() {
        return this.createdAt;
    }

    public static String insertCharInString(int index, char c, String s) {
        String part1 = s.substring(0, index);
        String part2 = s.substring(index);
        return part1 + c + part2;
    }

    public boolean isDataEmpty() {
        return this._data.length() == 0;
    }

    public String GetUnlockKeys() {
        return this._unlockKeys.toString();
    }

    /* access modifiers changed from: protected */
    public void KeyboardOnlyNumbers() {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.NUMBER);
        this._unlockKeys = sb;
    }

    /* access modifiers changed from: protected */
    public void KeyboardOnlyNumbersAndX() {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.NUMBER);
        sb.append(Constants.EMPTY);
        this._unlockKeys = sb;
    }

    /* access modifiers changed from: protected */
    public void KeyboardOnlyNumbersWichoutZero() {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.NUMBER1);
        sb.append(Constants.NUMBER2);
        sb.append(Constants.NUMBER3);
        sb.append(Constants.NUMBER4);
        sb.append(Constants.NUMBER5);
        sb.append(Constants.NUMBER6);
        sb.append(Constants.NUMBER7);
        sb.append(Constants.NUMBER8);
        sb.append(Constants.NUMBER9);
        this._unlockKeys = sb;
    }

    /* access modifiers changed from: protected */
    public void KeyboardOnlyOK() {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.OK);
        this._unlockKeys = sb;
    }

    /* access modifiers changed from: protected */
    public void KeyboardOnlyCircleAndPoint() {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.CIRCLE);
        sb.append(Constants.POINT);
        this._unlockKeys = sb;
    }

    public void deleteBackspace() {
        if (!isDataEmpty()) {
            if (getLastChar() == 'S') {
                StringBuilder sb = this._data;
                sb.deleteCharAt(sb.length() - 1);
            }
            if (getLastChar() == ')') {
                StringBuilder sb2 = this._data;
                sb2.deleteCharAt(sb2.length() - 1);
            }
            StringBuilder sb3 = this._data;
            sb3.deleteCharAt(sb3.length() - 1);
            updateKeyboard();
        }
    }

    /* access modifiers changed from: protected */
    public int getPosCursor() {
        return countChar(Constants.SPACE);
    }

    public static boolean isANumber(char c) {
        int ichar = c;
        if (ichar <= 47 || ichar >= 58) {
            return false;
        }
        return true;
    }

    public static boolean isCircle(char c) {
        return c == ')';
    }

    public static boolean isSpace(char c) {
        return c == 'S';
    }

    public static boolean isEmpty(char c) {
        return c == 'X';
    }

    public char getLastChar() {
        StringBuilder sb = this._data;
        return sb.charAt(sb.length() - 1);
    }

    public int countChar(char c) {
        int count = 0;
        for (char i : this._data.toString().toCharArray()) {
            if (i == c) {
                count++;
            }
        }
        return count;
    }

    public static int integerPart(String number) {
        int indexPoint = number.indexOf(46);
        if (indexPoint == -1) {
            return number.length();
        }
        return indexPoint;
    }

    public static int decimalPart(String number) {
        int indexPoint = number.indexOf(46);
        if (indexPoint == -1) {
            return 0;
        }
        return (number.length() - indexPoint) - 1;
    }

    private String insertParentesisInBefereSpace(String aux, int index) {
        while (index > 0) {
            if (aux.charAt(index) == 'S') {
                return insertCharInString(index + 1, Constants.LEFT_CIRCLE, aux);
            }
            index--;
        }
        return Constants.LEFT_CIRCLE + aux;
    }

    public String correctParentesis(String data) {
        if (data == null) {
            data = this._data.toString();
        }
        String aux = new String(data);
        int i = 0;
        while (i < aux.length()) {
            if (aux.charAt(i) == ')') {
                aux = insertParentesisInBefereSpace(aux, i);
                i++;
            }
            i++;
        }
        return aux;
    }

    public static Float convertToFloat(String number) {
        if (number.equals(Constants.NULL_STRING) || number.equals(String.valueOf(Constants.EMPTY))) {
            return Float.valueOf(0.0f);
        }
        if (number.endsWith(String.valueOf(Constants.CIRCLE))) {
            return Float.valueOf(number.substring(0, number.length() - 1));
        }
        return Float.valueOf(Float.valueOf(number).floatValue() / 100.0f);
    }

    public String[] getParts() {
        return this._data.toString().split(String.valueOf(Constants.SPACE));
    }

    public String getLastPart() {
        String[] parts = getParts();
        return parts[parts.length - 1];
    }

    public String toString() {
        if (isDataEmpty()) {
            return Constants.NULL_STRING;
        }
        return " " + correctParentesis(this._data.toString()).replace(Constants.SPACE, ' ');
    }

    public String getPriceString() {
        int index = this._data.indexOf(String.valueOf(Constants.SPACE));
        return index == -1 ? Constants.NULL_STRING : this._data.substring(index + 1);
    }

    public boolean hasPrice() {
        return this._data.indexOf(String.valueOf(Constants.SPACE)) != -1;
    }

    public void fillPrice(String priceString) {
        StringBuilder sb = this._data;
        sb.append(Constants.SPACE);
        sb.append(priceString);
    }

    public String getNumbersString(String data) {
        int index = data.lastIndexOf(String.valueOf(Constants.SPACE));
        return index == -1 ? Constants.NULL_STRING : data.substring(0, index);
    }

    public boolean save(DataBaseHelper db) {
        boolean aux = !config.getBoolean(Config.IS_IN_CLOUD).booleanValue();
        long insertRow = db.insertRow(this, aux, Boolean.valueOf(aux));
        this._id = insertRow;
        if (insertRow != -1) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this._unlockKeys.hashCode() * 31) + this._data.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this._id == ((Row) obj).getId()) {
            return true;
        }
        return false;
    }

    public void onOkButtonPressed() {
    }
}
