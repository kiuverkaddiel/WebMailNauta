package kiu.webmailnauta.legacy.parser.decode;

import kiu.webmailnauta.legacy.Constants;
import kiu.webmailnauta.legacy.parser.RowLineString;
import kiu.webmailnauta.legacy.parser.RowType;

public class RowLineStringDecode extends RowLineString {
    public RowLineStringDecode(RowType type, String data, String prise) {
        super(type, data, prise);
    }

    public String process() {
        return decode();
    }

    public String decode() {
        try {
            if (!this.type.equals(RowType.BALL)) {
                if (!this.type.equals(RowType.BALL_BOTE)) {
                    if (!this.type.equals(RowType.PARLET)) {
                        if (!this.type.equals(RowType.PARLET_BOTE)) {
                            if (!this.type.equals(RowType.CENTENE)) {
                                if (!this.type.equals(RowType.CENTENE_BOTE)) {
                                    return "Error, invalid Row Type";
                                }
                            }
                            return this.data + convertPrice(this.prise);
                        }
                    }
                    return this.data + convertPrice(this.prise);
                }
            }
            if (this.prise.startsWith("X")) {
                return this.data + "XS" + convertPrice(this.prise.substring(1));
            }
            String[] s_price = this.prise.split(" ");
            if (s_price.length == 2) {
                return this.data + convertPrice(s_price[0]) + "S" + convertPrice(s_price[1]);
            }
            String[] aux = this.prise.split("!");
            if (aux.length == 1) {
                if (this.prise.endsWith("!")) {
                    return this.data + aux[0];
                }
                return this.data + aux[0] + ")";
            } else if (aux.length == 2) {
                String data_row = this.data + aux[0] + "S";
                if (this.prise.endsWith("!")) {
                    return data_row + aux[1];
                }
                return data_row + aux[1] + ")";
            } else {
                return this.data + this.prise;
            }
        } catch (Exception e) {
            System.out.println("data: " + this);
            return "Error " + this.type.toString() + ", " + this + ": ";
        }
    }

    private String convertPrice(String price) {
        if (price.endsWith("!")) {
            return price.replace("!", Constants.NULL_STRING);
        }
        return price + ")";
    }
}
