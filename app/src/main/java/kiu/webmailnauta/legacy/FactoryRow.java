package kiu.webmailnauta.legacy;

import kiu.webmailnauta.legacy.db.RowDb;

public class FactoryRow {
    public static Row getRow(int type) {
        switch (type) {
            case Constants.BALL:
                return new BallRow();
            case Constants.PARLLET:
                return new ParlletRow();
            case Constants.CENTENE:
                return new CenteneRow();
            case Constants.BALL_BOTE:
                return new BallBoteRow();
            case Constants.PARLLET_BOTE:
                return new ParlletBoteRow();
            case Constants.CENTENE_BOTE:
                return new CenteneBoteRow();
            default:
                return null;
        }
    }

    private static ContentValues getContentRow(String rowString) throws Exception {
        try {
            String[] parts = rowString.split(String.valueOf(ConstantsParserRowList.COLUMN_ROW_SEPARATOR));
            if (parts.length == 4) {
                ContentValues vrow = new ContentValues();
                vrow.put(RowDb.Columns._ID, Long.valueOf(parts[0]));
                vrow.put(RowDb.Columns.CREATED_AT, Long.valueOf(parts[1]));
                vrow.put(RowDb.Columns.DATA, parts[2]);
                vrow.put(RowDb.Columns.TYPE, Integer.valueOf(parts[3]));
                return vrow;
            }
            throw new Exception("invalid row format!");
        } catch (Exception e) {
            throw new Exception("invalid row format");
        }
    }

    public static Row getRow(ContentValues vrow) {
        switch (vrow.getAsInteger(RowDb.Columns.TYPE).intValue()) {
            case Constants.BALL:
                return new BallRow(vrow);
            case Constants.PARLLET:
                return new ParlletRow(vrow);
            case Constants.CENTENE:
                return new CenteneRow(vrow);
            case Constants.BALL_BOTE:
                return new BallBoteRow(vrow);
            case Constants.PARLLET_BOTE:
                return new ParlletBoteRow(vrow);
            case Constants.CENTENE_BOTE:
                return new CenteneBoteRow(vrow);
            default:
                return null;
        }
    }

    public static Row getRow(String rowString) throws Exception {
        return getRow(getContentRow(rowString));
    }

    public static String preProsDataString(StringBuilder data) {
        while (!Row.isANumber(data.charAt(0))) {
            data.deleteCharAt(0);
        }
        String text = Constants.NULL_STRING;
        if (data.indexOf("AL") != -1) {
            for (int i = 0; i < data.length(); i++) {
                char c = data.charAt(i);
                if (c != '(') {
                    if (c == ' ' || c == 10) {
                        c = Constants.SPACE;
                    }
                    text = text + c;
                }
            }
            String text2 = text.replace("SS", "S");
            int lastSpace = text2.lastIndexOf("S");
            String to = text2.substring(lastSpace + 1);
            return text2.substring(0, lastSpace).replaceAll("SAL", "t" + to);
        } else if (data.indexOf("\n") != -1) {
            for (int i2 = 0; i2 < data.length(); i2++) {
                char c2 = data.charAt(i2);
                if (c2 != '(') {
                    if (c2 == ' ') {
                        c2 = 'P';
                    }
                    text = text + c2;
                }
            }
            String[] parts = text.split("\n");
            String text3 = Constants.NULL_STRING;
            String price = Constants.NULL_STRING;
            for (int i3 = 0; i3 < parts.length - 1; i3++) {
                String s = parts[i3];
                int index = s.lastIndexOf("P");
                if (index > 0) {
                    text3 = text3 + s.substring(0, index);
                    price = s.substring(index);
                } else {
                    text3 = text3 + s;
                }
            }
            return text3 + price;
        } else {
            for (int i4 = 0; i4 < data.length(); i4++) {
                char c3 = data.charAt(i4);
                if (c3 != '(') {
                    if (c3 == ' ') {
                        c3 = Constants.SPACE;
                    }
                    text = text + c3;
                }
            }
            return text;
        }
    }

    public static Row getRow(StringBuilder data) {
        String pre = preProsDataString(data);
        try {
            if (pre.indexOf("P") != -1) {
                Row row = new ParlletRow();
                row.setData(pre.replaceAll("P", "S"));
                return row;
            } else if (Row.isANumber(data.charAt(2))) {
                Row row2 = new CenteneRow();
                row2.setData(pre);
                return row2;
            } else {
                Row row3 = new BallRow();
                row3.setData(pre);
                return row3;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Row getRow(Row row) {
        return getRow(row.getType());
    }
}
