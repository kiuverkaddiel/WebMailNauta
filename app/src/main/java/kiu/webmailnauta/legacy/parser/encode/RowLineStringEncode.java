package kiu.webmailnauta.legacy.parser.encode;

import kiu.webmailnauta.legacy.parser.RowLineString;
import kiu.webmailnauta.legacy.parser.RowType;

public class RowLineStringEncode extends RowLineString {
    public RowLineStringEncode(RowType type, String data, String prise) {
        super(type, data, prise);
    }

    public String process() {
        return encode();
    }

    public String encode() {
        String str;
        String str2;
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
                            if (!this.data.startsWith("(")) {
                                return this.data + convertPrice(this.prise);
                            }
                            throw new Exception();
                        }
                    }
                    if (!this.data.startsWith("(")) {
                        String prise_aux = convertPrice(this.prise);
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.data);
                        if (prise_aux.endsWith("!")) {
                            str2 = prise_aux;
                        } else {
                            str2 = " " + prise_aux;
                        }
                        sb.append(str2);
                        return sb.toString();
                    }
                    throw new Exception();
                }
            }
            String data_aux = this.data;
            if (data_aux.startsWith("(")) {
                data_aux = data_aux.substring(1, data_aux.length() - 1) + "!";
            } else if (data_aux.contains("+")) {
                data_aux = data_aux.replace("+", "!!");
            }
            String price_aux = this.prise;
            if (price_aux.startsWith("X")) {
                return data_aux + "X" + convertPrice(price_aux.split(" ")[1]);
            }
            String[] s_price = price_aux.split(" ");
            if (s_price.length == 2) {
                String price1 = convertPrice(s_price[0]);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(data_aux);
                if (price1.endsWith("!")) {
                    str = price1;
                } else {
                    str = price1 + " ";
                }
                sb2.append(str);
                sb2.append(convertPrice(s_price[1]));
                return sb2.toString();
            }
            return data_aux + convertPrice(price_aux);
        } catch (Exception e) {
            System.out.println("data: " + this);
            return "Error " + this.type.toString() + ", " + this + ": ";
        }
    }

    private String convertPrice(String price) {
        if (price.startsWith("(")) {
            return price.substring(1, price.length() - 1);
        }
        return price + "!";
    }
}
