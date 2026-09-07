package kiu.webmailnauta.legacy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Price {
    private static final int OPERATION_ADD = 1;
    private static final int OPERATION_DEL = -1;
    private float _bruto_ball = 0.0f;
    private float _bruto_ball_bote = 0.0f;
    private float _bruto_centene = 0.0f;
    private float _bruto_centene_bote = 0.0f;
    private float _bruto_parllet = 0.0f;
    private float _bruto_parllet_bote = 0.0f;
    public HashMap<String, Float> ballCorrido = new HashMap<>();
    public HashMap<String, Float> ballCorridoBote = new HashMap<>();
    public HashMap<String, Float> ballCorridoDouble = new HashMap<>();
    public HashMap<String, Float> ballCorridoDoubleBote = new HashMap<>();
    public HashMap<String, Float> ballFijo = new HashMap<>();
    public HashMap<String, Float> ballFijoBote = new HashMap<>();
    private HashMap<String, Float> centene = new HashMap<>();
    private HashMap<String, Float> centeneBote = new HashMap<>();
    private final Config config;
    private HashMap<String, Float> countBallCorrido = new HashMap<>();
    private HashMap<String, Float> countBallCorridoBote = new HashMap<>();
    private HashMap<String, Float> countBallFijo = new HashMap<>();
    private HashMap<String, Float> countBallFijoBote = new HashMap<>();
    private int[] countBallMoreThan;
    private HashMap<String, Float> countCentene = new HashMap<>();
    private HashMap<String, Float> countCenteneBote = new HashMap<>();
    private HashMap<String, Float> countParllet = new HashMap<>();
    private HashMap<String, Float> countParlletBote = new HashMap<>();
    private int[] countParlletMoreThan;
    private HashMap<String, Set<String>> numbersBallPassOfPri = new HashMap<>();
    private HashMap<String, Set<String>> numbersParlletPassOfPri = new HashMap<>();
    private HashMap<String, Float> parllet = new HashMap<>();
    private HashMap<String, Float> parlletBote = new HashMap<>();
    private int[] priceBallMoreThan;
    private int[] priceParlletMoreThan;

    public Price() {
        Config instance = Config.getInstance();
        this.config = instance;
        String sBallCountMoreThan = instance.getString(Config.BALL_COUNT_PASS);
        sBallCountMoreThan = sBallCountMoreThan == null ? "100" : sBallCountMoreThan;
        String sBallMoreThan = instance.getString(Config.BALL_MORE_THAN);
        String[] stringPriceMoreThan = (sBallMoreThan == null ? "1000" : sBallMoreThan).split("\\.");
        String[] stringCountMoreThan = sBallCountMoreThan.split("\\.");
        int minSizeBall = Math.min(stringPriceMoreThan.length, stringCountMoreThan.length);
        this.priceBallMoreThan = new int[minSizeBall];
        this.countBallMoreThan = new int[minSizeBall];
        for (int i = 0; i < minSizeBall; i++) {
            String price = stringPriceMoreThan[i];
            this.priceBallMoreThan[i] = Integer.parseInt(price);
            HashMap<String, Set<String>> hashMap = this.numbersBallPassOfPri;
            hashMap.put(price + "-0", new HashSet());
            HashMap<String, Set<String>> hashMap2 = this.numbersBallPassOfPri;
            hashMap2.put(price + "-1", new HashSet());
            this.countBallMoreThan[i] = Integer.parseInt(stringCountMoreThan[i]);
        }
        String sParlletMoreThan = this.config.getString(Config.PARLLET_MORE_THAN);
        sParlletMoreThan = sParlletMoreThan == null ? "4000" : sParlletMoreThan;
        String sParlletCountMoreThan = this.config.getString(Config.PARLLET_COUNT_PASS);
        sParlletCountMoreThan = sParlletCountMoreThan == null ? "100" : sParlletCountMoreThan;
        String[] stringParlletPriceMoreThan = sParlletMoreThan.split("\\.");
        String[] stringParlletCountMoreThan = sParlletCountMoreThan.split("\\.");
        int minSizeParllet = Math.min(stringParlletPriceMoreThan.length, stringParlletCountMoreThan.length);
        this.priceParlletMoreThan = new int[minSizeParllet];
        this.countParlletMoreThan = new int[minSizeParllet];
        for (int i2 = 0; i2 < minSizeParllet; i2++) {
            String price2 = stringParlletPriceMoreThan[i2];
            this.priceParlletMoreThan[i2] = Integer.parseInt(price2);
            this.numbersParlletPassOfPri.put(price2, new HashSet());
            this.countParlletMoreThan[i2] = Integer.parseInt(stringParlletCountMoreThan[i2]);
        }
    }

    private String convertCent(String c) {
        if (c.equals("0")) {
            return "0";
        }
        if (c.length() == 1) {
            return c + "0";
        } else if (c.equals("05")) {
            return "5";
        } else {
            return c;
        }
    }

    private String convertFloatToString(Float f) {
        String[] split = Pattern.compile(".", 16).split(f.toString());
        split[1] = convertCent(split[1]);
        if (split[0].equals("0")) {
            return " tiene " + split[1] + " centavos pasados\n";
        } else if (split[1].equals("0")) {
            return " tiene " + split[0] + " pesos pasados\n";
        } else {
            return " tiene " + split[0] + " pesos con " + split[1] + " centavos pasados\n";
        }
    }

    public void addNumberBallPassOfPri(String number) {
        Float[] ballPrice = fillFloatArray(getCountPriceBall(number));
        Float[] ballPriceBote = fillFloatArray(getCountPriceBallBote(number));
        Float[] fijo_corrido = {Float.valueOf(ballPrice[0].floatValue() + ballPriceBote[0].floatValue()), Float.valueOf(ballPrice[1].floatValue() + ballPriceBote[1].floatValue())};
        for (int price : this.priceBallMoreThan) {
            for (int i = 0; i < 2; i++) {
                if (fijo_corrido[i].floatValue() >= ((float) price)) {
                    this.numbersBallPassOfPri.get(price + "-" + i).add(number);
                }
            }
        }
    }

    public void delNumberBallPassOfPri(String number) {
        Float[] ballPrice = fillFloatArray(getCountPriceBall(number));
        Float[] ballPriceBote = fillFloatArray(getCountPriceBallBote(number));
        Float[] fijo_corrido = {Float.valueOf(ballPrice[0].floatValue() + ballPriceBote[0].floatValue()), Float.valueOf(ballPrice[1].floatValue() + ballPriceBote[1].floatValue())};
        for (int price : this.priceBallMoreThan) {
            for (int i = 0; i < 2; i++) {
                if (fijo_corrido[i].floatValue() < ((float) price)) {
                    this.numbersBallPassOfPri.get(price + "-" + i).remove(number);
                }
            }
        }
    }

    public void addNumberParlletPassOfPri(String number) {
        Float total = Float.valueOf(fillNumber(getCountPriceParllet(number)).floatValue() + fillNumber(getCountPriceParlletBote(number)).floatValue());
        for (int price : this.priceParlletMoreThan) {
            if (total.floatValue() >= ((float) price)) {
                this.numbersParlletPassOfPri.get(String.valueOf(price)).add(number);
            }
        }
    }

    public void delNumberParlletPassOfPri(String number) {
        Float total = Float.valueOf(fillNumber(getCountPriceParllet(number)).floatValue() + fillNumber(getCountPriceParlletBote(number)).floatValue());
        for (int price : this.priceParlletMoreThan) {
            if (total.floatValue() < ((float) price)) {
                this.numbersParlletPassOfPri.get(String.valueOf(price)).remove(number);
            }
        }
    }

    public String getBallPassMoreThan(String[] var1, Float[] var2) {
        int var6 = var1.length;

        for(int var3 = 0; var3 < var6; ++var3) {
            String var10 = var1[var3];
            Float[] var12 = fillFloatArray(getCountPriceBall(var10));
            Float[] var13 = fillFloatArray(getCountPriceBallBote(var10));
            Float[] var11 = fillFloatArray(var2);
            Float[] var9 = new Float[]{var12[0] + var13[0] + var11[0], var12[1] + var13[1] + var11[1]};
            int var4 = 0;

            while(true) {
                int[] var16 = this.priceBallMoreThan;
                if (var4 >= var16.length) {
                    break;
                }

                int var7 = var16[var4];

                for(int var5 = 0; var5 < 2; ++var5) {
                    if (var9[var5] >= (float)var7) {
                        HashMap var19 = this.numbersBallPassOfPri;
                        StringBuilder var17 = new StringBuilder();
                        var17.append(var7);
                        var17.append("-");
                        var17.append(var5);
                        Set var18 = (Set)var19.get(var17.toString());
                        if (!var18.contains(var10)) {
                            int var8 = var18.size() + var1.length;
                            if (var8 > this.countBallMoreThan[var4]) {
                                String var15 = "Vas a tener {current_count_pass} números {number_type} con más de {more_than} pesos. Solo pudes tener {count_pass} números con más de {more_than} pesos. El {number} va a tener {current} pesos.".replace("{current_count_pass}", String.valueOf(var8));
                                String var14;
                                if (var5 == 0) {
                                    var14 = "fijos";
                                } else {
                                    var14 = "corridos";
                                }

                                return var15.replace("{number_type}", var14).replace("{count_pass}", String.valueOf(this.countBallMoreThan[var4])).replace("{more_than}", String.valueOf(var7)).replace("{number}", var10).replace("{current}", String.valueOf(var9[var5]));
                            }
                        }
                    }
                }

                ++var4;
            }
        }

        return null;
    }

    public String getParlletPassMoreThan(String[] var1, Float var2) {
        int var5 = var1.length;

        for(int var3 = 0; var3 < var5; ++var3) {
            String var8 = var1[var3];
            Float var9 = this.fillNumber(this.getCountPriceParllet(var8));
            Float var10 = this.fillNumber(this.getCountPriceParlletBote(var8));
            var9 = var9 + var10 + var2;
            int var4 = 0;

            while(true) {
                int[] var11 = this.priceParlletMoreThan;
                if (var4 >= var11.length) {
                    break;
                }

                int var7 = var11[var4];
                if (var9 >= (float)var7) {
                    Set var12 = (Set)this.numbersParlletPassOfPri.get(String.valueOf(var7));
                    if (!var12.contains(var8)) {
                        int var6 = var12.size() + var1.length;
                        if (var6 > this.countParlletMoreThan[var4]) {
                            return "Vas a tener {current_count_pass} parleses con más de {more_than} pesos. Solo pudes tener {count_pass} parleses con más de {more_than} pesos. El {parllet} va a tener {current} pesos.".replace("{current_count_pass}", String.valueOf(var6)).replace("{count_pass}", String.valueOf(this.countParlletMoreThan[var4])).replace("{more_than}", String.valueOf(var7)).replace("{parllet}", var8.replace("S", "-")).replace("{current}", String.valueOf(var9));
                        }
                    }
                }

                ++var4;
            }
        }

        return null;
    }

    private Float fillNumber(Float number) {
        return Float.valueOf(number != null ? number.floatValue() : 0.0f);
    }

    private Float[] fillFloatArray(Float[] aux) {
        Float[] fArr = new Float[2];
        float f = 0.0f;
        fArr[0] = Float.valueOf(aux[0] != null ? aux[0].floatValue() : 0.0f);
        if (aux[1] != null) {
            f = aux[1].floatValue();
        }
        fArr[1] = Float.valueOf(f);
        return fArr;
    }

    public String getParlletPassWith(int count) {
        String aux = Constants.NULL_STRING;
        for (String parllet2 : this.countParllet.keySet()) {
            Float price = this.countParllet.get(parllet2);
            if (price.floatValue() > ((float) count)) {
                float pass = round(Float.valueOf(price.floatValue() - ((float) count))).floatValue();
                aux = aux + "El parle " + parllet2.replace(Constants.SPACE, Constants.SHOW_NUMBER_SEPARATOR) + convertFloatToString(Float.valueOf(pass));
            }
        }
        if (aux.isEmpty()) {
            return aux;
        }
        return "---------------------------------\nPasados en parle con mas de: " + count + " pesos\n---------------------------------\n" + aux;
    }

    public String getBallFijoPassWith(int count) {
        String aux = Constants.NULL_STRING;
        for (String ball : this.countBallFijo.keySet()) {
            Float price = this.countBallFijo.get(ball);
            if (price.floatValue() > ((float) count)) {
                float pass = round(Float.valueOf(price.floatValue() - ((float) count))).floatValue();
                aux = aux + "El numero " + ball + convertFloatToString(Float.valueOf(pass));
            }
        }
        if (aux.isEmpty()) {
            return aux;
        }
        return "---------------------------------\nPasados en bola fija con mas de: " + count + " pesos\n---------------------------------\n" + aux;
    }

    public String getBallCorridoPassWith(int count) {
        String aux = Constants.NULL_STRING;
        for (String ball : this.countBallCorrido.keySet()) {
            Float price = this.countBallCorrido.get(ball);
            if (price.floatValue() > ((float) count)) {
                float pass = round(Float.valueOf(price.floatValue() - ((float) count))).floatValue();
                aux = aux + "El numero " + ball + convertFloatToString(Float.valueOf(pass));
            }
        }
        if (aux.isEmpty()) {
            return aux;
        }
        return "---------------------------------\nPasados en bola corrida con mas de: " + count + " pesos\n---------------------------------\n" + aux;
    }

    public Float[] getCountPriceBall(String ballNumber) {
        return new Float[]{this.countBallFijo.get(ballNumber), this.countBallCorrido.get(ballNumber)};
    }

    public Float[] getCountPriceBallBote(String ballNumber) {
        return new Float[]{this.countBallFijoBote.get(ballNumber), this.countBallCorridoBote.get(ballNumber)};
    }

    public void addCountPriceBall(String number, Float[] price) {
        if (price[0].floatValue() != 0.0f) {
            if (this.countBallFijo.containsKey(number)) {
                HashMap<String, Float> hashMap = this.countBallFijo;
                hashMap.put(number, Float.valueOf(hashMap.get(number).floatValue() + price[0].floatValue()));
            } else {
                this.countBallFijo.put(number, price[0]);
            }
        }
        if (price[1].floatValue() == 0.0f) {
            return;
        }
        if (this.countBallCorrido.containsKey(number)) {
            HashMap<String, Float> hashMap2 = this.countBallCorrido;
            hashMap2.put(number, Float.valueOf(hashMap2.get(number).floatValue() + price[1].floatValue()));
            return;
        }
        this.countBallCorrido.put(number, price[1]);
    }

    public void addCountPriceBallBote(String number, Float[] price) {
        if (price[0].floatValue() != 0.0f) {
            if (this.countBallFijoBote.containsKey(number)) {
                HashMap<String, Float> hashMap = this.countBallFijoBote;
                hashMap.put(number, Float.valueOf(hashMap.get(number).floatValue() + price[0].floatValue()));
            } else {
                this.countBallFijoBote.put(number, price[0]);
            }
        }
        if (price[1].floatValue() == 0.0f) {
            return;
        }
        if (this.countBallCorridoBote.containsKey(number)) {
            HashMap<String, Float> hashMap2 = this.countBallCorridoBote;
            hashMap2.put(number, Float.valueOf(hashMap2.get(number).floatValue() + price[1].floatValue()));
            return;
        }
        this.countBallCorridoBote.put(number, price[1]);
    }

    public void delCountPriceBall(String number, Float[] price) {
        if (price[0].floatValue() != 0.0f) {
            HashMap<String, Float> hashMap = this.countBallFijo;
            hashMap.put(number, Float.valueOf(hashMap.get(number).floatValue() - price[0].floatValue()));
        }
        if (price[1].floatValue() != 0.0f) {
            HashMap<String, Float> hashMap2 = this.countBallCorrido;
            hashMap2.put(number, Float.valueOf(hashMap2.get(number).floatValue() - price[1].floatValue()));
        }
    }

    public void delCountPriceBallBote(String number, Float[] price) {
        if (price[0].floatValue() != 0.0f) {
            HashMap<String, Float> hashMap = this.countBallFijoBote;
            hashMap.put(number, Float.valueOf(hashMap.get(number).floatValue() - price[0].floatValue()));
        }
        if (price[1].floatValue() != 0.0f) {
            HashMap<String, Float> hashMap2 = this.countBallCorridoBote;
            hashMap2.put(number, Float.valueOf(hashMap2.get(number).floatValue() - price[1].floatValue()));
        }
    }

    public Float getCountPriceParllet(String combination) {
        return this.countParllet.get(combination);
    }

    public Float getCountPriceParlletBote(String combination) {
        return this.countParlletBote.get(combination);
    }

    private String reverseCombination(String parlletCombination) {
        String[] numbers = parlletCombination.split(String.valueOf(Constants.SPACE));
        return numbers[1] + Constants.SPACE + numbers[0];
    }

    public void addCountPriceParllet(String combination, Float price) {
        if (price.floatValue() != 0.0f) {
            String reverse = reverseCombination(combination);
            if (this.countParllet.containsKey(combination)) {
                Float havePrice = this.countParllet.get(combination);
                this.countParllet.put(combination, Float.valueOf(havePrice.floatValue() + price.floatValue()));
                this.countParllet.put(reverse, Float.valueOf(havePrice.floatValue() + price.floatValue()));
                return;
            }
            this.countParllet.put(combination, price);
            this.countParllet.put(reverse, price);
        }
    }

    public void addCountPriceParlletBote(String combination, Float price) {
        if (price.floatValue() != 0.0f) {
            String reverse = reverseCombination(combination);
            if (this.countParlletBote.containsKey(combination)) {
                Float havePrice = this.countParlletBote.get(combination);
                this.countParlletBote.put(combination, Float.valueOf(havePrice.floatValue() + price.floatValue()));
                this.countParlletBote.put(reverse, Float.valueOf(havePrice.floatValue() + price.floatValue()));
                return;
            }
            this.countParlletBote.put(combination, price);
            this.countParlletBote.put(reverse, price);
        }
    }

    public void delCountPriceParllet(String combination, Float price) {
        Float newPrice = Float.valueOf(Math.abs(this.countParllet.get(combination).floatValue() - price.floatValue()));
        this.countParllet.put(combination, newPrice);
        this.countParllet.put(reverseCombination(combination), newPrice);
    }

    public void delCountPriceParlletBote(String combination, Float price) {
        Float newPrice = Float.valueOf(Math.abs(this.countParlletBote.get(combination).floatValue() - price.floatValue()));
        this.countParlletBote.put(combination, newPrice);
        this.countParlletBote.put(reverseCombination(combination), newPrice);
    }

    public Float getCountPriceCentene(String centeneNumber) {
        float aux = 0.0f;
        for (String cent : this.countCentene.keySet()) {
            if (cent.startsWith(centeneNumber)) {
                aux += this.countCentene.get(cent).floatValue();
            }
        }
        return round(Float.valueOf(aux));
    }

    public Float getCountPriceCenteneBote(String centeneNumber) {
        float aux = 0.0f;
        for (String cent : this.countCenteneBote.keySet()) {
            if (cent.startsWith(centeneNumber)) {
                aux += this.countCenteneBote.get(cent).floatValue();
            }
        }
        return round(Float.valueOf(aux));
    }

    public void addCountPriceCentene(String number, Float price) {
        if (price.floatValue() == 0.0f) {
            return;
        }
        if (this.countCentene.containsKey(number)) {
            HashMap<String, Float> hashMap = this.countCentene;
            hashMap.put(number, Float.valueOf(hashMap.get(number).floatValue() + price.floatValue()));
            return;
        }
        this.countCentene.put(number, price);
    }

    public void addCountPriceCenteneBote(String number, Float price) {
        if (price.floatValue() == 0.0f) {
            return;
        }
        if (this.countCenteneBote.containsKey(number)) {
            HashMap<String, Float> hashMap = this.countCenteneBote;
            hashMap.put(number, Float.valueOf(hashMap.get(number).floatValue() + price.floatValue()));
            return;
        }
        this.countCenteneBote.put(number, price);
    }

    public void delCountPriceCentene(String number, Float price) {
        HashMap<String, Float> hashMap = this.countCentene;
        hashMap.put(number, Float.valueOf(hashMap.get(number).floatValue() - price.floatValue()));
    }

    public void delCountPriceCenteneBote(String number, Float price) {
        HashMap<String, Float> hashMap = this.countCenteneBote;
        hashMap.put(number, Float.valueOf(hashMap.get(number).floatValue() - price.floatValue()));
    }

    public static String getIntegerString(Float f) {
        String[] parts = f.toString().split("\\.");
        return parts.length == 2 ? parts[0] : "0";
    }

    public static String getDecimalString(Float f) {
        String[] parts = f.toString().split("\\.");
        return parts[parts.length - 1];
    }

    public static String inc(String number) {
        return Integer.valueOf(Integer.valueOf(number).intValue() + 1).toString();
    }

    public static Float round(Float f) {
        String d2;
        if (f.floatValue() > 0.0f && f.floatValue() < 0.05f) {
            return Float.valueOf(0.0f);
        }
        String decimal = getDecimalString(f);
        if (decimal.length() == 1) {
            return f;
        }
        String integer = getIntegerString(f);
        String d1 = String.valueOf(decimal.charAt(0));
        int id2 = Integer.valueOf(String.valueOf(decimal.charAt(1))).intValue();
        if (id2 > 3 && id2 < 7) {
            d2 = "5";
        } else if (id2 < 4) {
            d2 = "0";
        } else {
            d2 = "0";
            if (Integer.valueOf(d1).intValue() == 9) {
                integer = inc(integer);
                d1 = "0";
            } else {
                d1 = inc(d1);
            }
        }
        return Float.valueOf(integer + Constants.POINT + d1 + d2);
    }

    public static String floatToString(Float number) {
        String aux = number.toString();
        if (aux.split("\\.")[1].length() != 1) {
            return aux;
        }
        return aux + "0";
    }

    public float getBruto(int type) {
        switch (type) {
            case Constants.BALL:
                return this._bruto_ball;
            case Constants.PARLLET:
                return this._bruto_parllet;
            case Constants.CENTENE:
                return this._bruto_centene;
            default:
                return Float.MIN_VALUE;
        }
    }

    public float getBruto(Row row) {
        return getBruto(row.getType());
    }

    public boolean passNumberBall() {
        int count_fijo = 0;
        for (Float floatValue : this.countBallFijo.values()) {
            if (floatValue.floatValue() != 0.0f) {
                count_fijo++;
            }
        }
        if (count_fijo > this.config.getInteger(Config.BALL_COUNT_NUMBER).intValue()) {
            return true;
        }
        int count_corrido = 0;
        for (Float floatValue2 : this.countBallCorrido.values()) {
            if (floatValue2.floatValue() != 0.0f) {
                count_corrido++;
            }
        }
        if (count_corrido > this.config.getInteger(Config.BALL_COUNT_NUMBER).intValue()) {
            return true;
        }
        return false;
    }

    public boolean passNumberParllet() {
        int count = 0;
        for (Float floatValue : this.countParllet.values()) {
            if (floatValue.floatValue() != 0.0f) {
                count++;
            }
        }
        return count > this.config.getInteger(Config.PARLLET_COUNT_NUMBER).intValue();
    }

    public float getLimpio(int type) {
        switch (type) {
            case Constants.BALL:
                float ball_percent = this.config.getFloat(Config.BALL_PERCENT).floatValue();
                if (passNumberBall()) {
                    ball_percent = (float) this.config.getInteger(Config.BALL_PERCENT_100).intValue();
                } else if (!this.config.getBoolean(Config.SEPARATE_LIMPIO).booleanValue() && passNumberParllet()) {
                    ball_percent = this.config.getFloat(Config.BALL_PERCENT_100).floatValue();
                }
                return round(Float.valueOf((this._bruto_ball * (100.0f - ball_percent)) / 100.0f)).floatValue();
            case Constants.PARLLET:
                float parllet_percent = this.config.getFloat(Config.PARLLET_PERCENT).floatValue();
                if (passNumberParllet()) {
                    parllet_percent = this.config.getFloat(Config.PARLLET_PERCENT_4850).floatValue();
                } else if (!this.config.getBoolean(Config.SEPARATE_LIMPIO).booleanValue() && passNumberBall()) {
                    parllet_percent = this.config.getFloat(Config.PARLLET_PERCENT_4850).floatValue();
                }
                return round(Float.valueOf((this._bruto_parllet * (100.0f - parllet_percent)) / 100.0f)).floatValue();
            case Constants.CENTENE:
                return round(Float.valueOf((this._bruto_centene * ((float) (100 - this.config.getInteger(Config.CENTENE_PERCENT).intValue()))) / 100.0f)).floatValue();
            default:
                return Float.MIN_VALUE;
        }
    }

    public boolean passNumberBallBote() {
        int count_fijo = 0;
        for (Float floatValue : this.countBallFijoBote.values()) {
            if (floatValue.floatValue() != 0.0f) {
                count_fijo++;
            }
        }
        if (count_fijo > this.config.getInteger(Config.BALL_COUNT_NUMBER_BOTE).intValue()) {
            return true;
        }
        int count_corrido = 0;
        for (Float floatValue2 : this.countBallCorridoBote.values()) {
            if (floatValue2.floatValue() != 0.0f) {
                count_corrido++;
            }
        }
        if (count_corrido > this.config.getInteger(Config.BALL_COUNT_NUMBER_BOTE).intValue()) {
            return true;
        }
        return false;
    }

    public boolean passNumberParlletBote() {
        int count = 0;
        for (Float floatValue : this.countParlletBote.values()) {
            if (floatValue.floatValue() != 0.0f) {
                count++;
            }
        }
        return count > this.config.getInteger(Config.PARLLET_COUNT_NUMBER_BOTE).intValue();
    }

    public float getLimpioBote(int type) {
        switch (type) {
            case Constants.BALL_BOTE:
                float ball_percent = this.config.getFloat(Config.BALL_PERCENT_BOTE).floatValue();
                if (passNumberBallBote()) {
                    ball_percent = this.config.getFloat(Config.BALL_PERCENT_100_BOTE).floatValue();
                } else if (!this.config.getBoolean(Config.SEPARATE_LIMPIO_BOTE).booleanValue() && passNumberParlletBote()) {
                    ball_percent = this.config.getFloat(Config.BALL_PERCENT_100_BOTE).floatValue();
                }
                return round(Float.valueOf((this._bruto_ball_bote * (100.0f - ball_percent)) / 100.0f)).floatValue();
            case Constants.PARLLET_BOTE:
                float parllet_percent = this.config.getFloat(Config.PARLLET_PERCENT_BOTE).floatValue();
                if (passNumberParlletBote()) {
                    parllet_percent = this.config.getFloat(Config.PARLLET_PERCENT_4850_BOTE).floatValue();
                } else if (!this.config.getBoolean(Config.SEPARATE_LIMPIO_BOTE).booleanValue() && passNumberBallBote()) {
                    parllet_percent = this.config.getFloat(Config.PARLLET_PERCENT_4850_BOTE).floatValue();
                }
                return round(Float.valueOf((this._bruto_parllet_bote * (100.0f - parllet_percent)) / 100.0f)).floatValue();
            case Constants.CENTENE_BOTE:
                return round(Float.valueOf((this._bruto_centene_bote * ((float) (100 - this.config.getInteger(Config.CENTENE_PERCENT_BOTE).intValue()))) / 100.0f)).floatValue();
            default:
                return Float.MIN_VALUE;
        }
    }

    public float getLimpio(Row row) {
        return getLimpio(row.getType());
    }

    public float getTotalBruto() {
        return round(Float.valueOf(this._bruto_ball + this._bruto_parllet + this._bruto_centene)).floatValue();
    }

    public float getBrutoBall() {
        return round(Float.valueOf(this._bruto_ball)).floatValue();
    }

    public float getBrutoParlletCentene() {
        return round(Float.valueOf(this._bruto_parllet + this._bruto_centene)).floatValue();
    }

    public float getBrutoParllet() {
        return round(Float.valueOf(this._bruto_parllet)).floatValue();
    }

    public float getBrutoCentene() {
        return round(Float.valueOf(this._bruto_centene)).floatValue();
    }

    public float getTotalBrutoBote() {
        return round(Float.valueOf(this._bruto_ball_bote + this._bruto_parllet_bote + this._bruto_centene_bote)).floatValue();
    }

    public float getBrutoBallBote() {
        return round(Float.valueOf(this._bruto_ball_bote)).floatValue();
    }

    public float getBrutoParlletCenteneBote() {
        return round(Float.valueOf(this._bruto_parllet_bote + this._bruto_centene_bote)).floatValue();
    }

    public float getBrutoParlletBote() {
        return round(Float.valueOf(this._bruto_parllet_bote)).floatValue();
    }

    public float getBrutoCenteneBote() {
        return round(Float.valueOf(this._bruto_centene_bote)).floatValue();
    }

    public float getTotalLimpio() {
        float limpio_ball = getLimpioBall();
        float total_limpio = getLimpioParllet() + limpio_ball + getLimpioCentene();
        if ((100.0f * limpio_ball) / total_limpio < ((float) this.config.getInteger(Config.MIN_BALL_PERCENT).intValue())) {
            return round(Float.valueOf(((getTotalBruto() - total_limpio) / 2.0f) + total_limpio)).floatValue();
        }
        return round(Float.valueOf(total_limpio)).floatValue();
    }

    public float getLimpioBall() {
        return round(Float.valueOf(getLimpio((int) Constants.BALL))).floatValue();
    }

    public float getLimpioParlletCentene() {
        return round(Float.valueOf(getLimpio((int) Constants.PARLLET) + getLimpio((int) Constants.CENTENE))).floatValue();
    }

    public float getLimpioParllet() {
        return round(Float.valueOf(getLimpio((int) Constants.PARLLET))).floatValue();
    }

    public float getLimpioCentene() {
        return round(Float.valueOf(getLimpio((int) Constants.CENTENE))).floatValue();
    }

    public float getTotalLimpioBote() {
        float limpio_ball = getLimpioBallBote();
        float total_limpio = getLimpioParlletBote() + limpio_ball + getLimpioCenteneBote();
        if ((100.0f * limpio_ball) / total_limpio < ((float) this.config.getInteger(Config.MIN_BALL_PERCENT_BOTE).intValue())) {
            return ((getTotalBrutoBote() - total_limpio) / 2.0f) + total_limpio;
        }
        return total_limpio;
    }

    public float getLimpioBallBote() {
        return round(Float.valueOf(getLimpioBote(Constants.BALL_BOTE))).floatValue();
    }

    public float getLimpioParlletCenteneBote() {
        return round(Float.valueOf(getLimpioBote(Constants.PARLLET_BOTE) + getLimpioBote(Constants.CENTENE_BOTE))).floatValue();
    }

    public float getLimpioParlletBote() {
        return round(Float.valueOf(getLimpioBote(Constants.PARLLET_BOTE))).floatValue();
    }

    public float getLimpioCenteneBote() {
        return round(Float.valueOf(getLimpioBote(Constants.CENTENE_BOTE))).floatValue();
    }

    private void updateBruto(Row row, int operation) {
        Float[] price = row.getPrice();
        int i = 0;
        switch (row.getType()) {
            case Constants.BALL:
                int length = price.length;
                while (i < length) {
                    this._bruto_ball = Math.abs(round(Float.valueOf((((float) operation) * this._bruto_ball) + price[i].floatValue())).floatValue());
                    i++;
                }
                return;
            case Constants.PARLLET:
                this._bruto_parllet = Math.abs(round(Float.valueOf((((float) operation) * this._bruto_parllet) + price[0].floatValue())).floatValue());
                return;
            case Constants.CENTENE:
                this._bruto_centene = Math.abs(round(Float.valueOf((((float) operation) * this._bruto_centene) + price[0].floatValue())).floatValue());
                return;
            case Constants.BALL_BOTE:
                int length2 = price.length;
                while (i < length2) {
                    this._bruto_ball_bote = Math.abs(round(Float.valueOf((((float) operation) * this._bruto_ball_bote) + price[i].floatValue())).floatValue());
                    i++;
                }
                return;
            case Constants.PARLLET_BOTE:
                this._bruto_parllet_bote = Math.abs(round(Float.valueOf((((float) operation) * this._bruto_parllet_bote) + price[0].floatValue())).floatValue());
                return;
            case Constants.CENTENE_BOTE:
                this._bruto_centene_bote = Math.abs(round(Float.valueOf((((float) operation) * this._bruto_centene_bote) + price[0].floatValue())).floatValue());
                return;
            default:
                return;
        }
    }

    public void addBruto(Row row) {
        updateBruto(row, 1);
    }

    public void delBruto(Row row) {
        updateBruto(row, -1);
    }

    private void add(HashMap<String, Float> hash, String key, Float price) {
        if (hash.containsKey(key)) {
            hash.put(key, Float.valueOf(hash.get(key).floatValue() + price.floatValue()));
        } else {
            hash.put(key, price);
        }
    }

    public void addParllet(String key, Float price) {
        add(this.parllet, key, price);
    }

    public void addParlletBote(String key, Float price) {
        add(this.parlletBote, key, price);
    }

    public void addCentene(String key, Float price) {
        add(this.centene, key, price);
    }

    public void addCenteneBote(String key, Float price) {
        add(this.centeneBote, key, price);
    }

    public void addBallFijo(String key, Float price) {
        add(this.ballFijo, key, price);
    }

    public void addBallFijoBote(String key, Float price) {
        add(this.ballFijoBote, key, price);
    }

    public void addBallCorrido(String key, Float price) {
        add(this.ballCorrido, key, price);
    }

    public void addBallCorridoDouble(String key, Float price) {
        add(this.ballCorridoDouble, key, price);
    }

    public void addBallCorridoBote(String key, Float price) {
        add(this.ballCorridoBote, key, price);
    }

    public void addBallCorridoDoubleBote(String key, Float price) {
        add(this.ballCorridoDoubleBote, key, price);
    }

    private Float getPass(float limpio, float maxPrice, int basePrice, HashMap<String, Float> map) {
        if (basePrice == 0) {
            basePrice = 1;
        }
        float percent = (limpio * maxPrice) / ((float) basePrice);
        float pass = 0.0f;
        for (String key : map.keySet()) {
            float pay = map.get(key).floatValue();
            if (pay > percent || pay > maxPrice) {
                pass += pay - Math.min(percent, maxPrice);
            }
        }
        return Float.valueOf(pass);
    }

    private Float getPassBote(float limpio, float maxPrice, int basePrice, HashMap<String, Float> map) {
        if (basePrice == 0) {
            return Float.valueOf(0.0f);
        }
        Set<String> keys = map.keySet();
        float percent = round(Float.valueOf((limpio * maxPrice) / ((float) basePrice))).floatValue();
        float pass = 0.0f;
        for (String key : keys) {
            float pay = map.get(key).floatValue();
            if (pay > percent || pay > maxPrice) {
                pass += pay - Math.min(percent, maxPrice);
            }
        }
        return Float.valueOf(pass);
    }

    public Float getParlletPass() {
        return round(getPass(this.config.getBoolean(Config.SEPARATE_LIMPIO).booleanValue() ? getLimpioParlletCentene() : getTotalLimpio(), this.config.getFloat(Config.MAX_PARLLET_PRICE).floatValue(), this.config.getInteger(Config.BASE_PARLLET_PRICE).intValue(), this.parllet));
    }

    public Float getParlletPassBote() {
        float limpio = this.config.getBoolean(Config.SEPARATE_LIMPIO_BOTE).booleanValue() ? getLimpioParlletCenteneBote() : getTotalLimpioBote();
        if (this.config.getBoolean(Config.OPEN_BOTE).booleanValue()) {
            return new PriceOpenBote().getParlletPassBote(limpio, this.parlletBote);
        }
        return round(getPassBote(limpio, this.config.getFloat(Config.MAX_PARLLET_PRICE_BOTE).floatValue(), this.config.getInteger(Config.BASE_PARLLET_PRICE_BOTE).intValue(), this.parlletBote));
    }

    public Float getCentenePass() {
        return round(getPass(this.config.getBoolean(Config.SEPARATE_LIMPIO).booleanValue() ? getLimpioParlletCentene() : getTotalLimpio(), this.config.getFloat(Config.MAX_CENTENE_PRICE).floatValue(), this.config.getInteger(Config.BASE_CENTENE_PRICE).intValue(), this.centene));
    }

    public Float getCentenePassPrice() {
        float limpio = this.config.getBoolean(Config.SEPARATE_LIMPIO).booleanValue() ? getLimpioParlletCentene() : getTotalLimpio();
        float maxPrice = this.config.getFloat(Config.MAX_CENTENE_PRICE).floatValue();
        int basePrice = this.config.getInteger(Config.BASE_CENTENE_PRICE).intValue();
        if (basePrice == 0) {
            return Float.valueOf(0.0f);
        }
        Set<String> keys = this.centene.keySet();
        float percent = round(Float.valueOf((limpio * maxPrice) / ((float) basePrice))).floatValue();
        float pass = 0.0f;
        for (String key : keys) {
            float pay = this.centene.get(key).floatValue();
            if (pay > percent || pay > maxPrice) {
                pass += (pay - Math.min(percent, maxPrice)) * ((float) CenteneRow.pays[key.length() - 3]);
            }
        }
        return Float.valueOf(pass);
    }

    public Float getCentenePassBote() {
        float limpio = this.config.getBoolean(Config.SEPARATE_LIMPIO_BOTE).booleanValue() ? getLimpioParlletCenteneBote() : getTotalLimpioBote();
        if (this.config.getBoolean(Config.OPEN_BOTE).booleanValue()) {
            return new PriceOpenBote().getCentenePassBote(limpio, this.centeneBote);
        }
        return round(getPassBote(limpio, this.config.getFloat(Config.MAX_CENTENE_PRICE_BOTE).floatValue(), this.config.getInteger(Config.BASE_CENTENE_PRICE_BOTE).intValue(), this.centeneBote));
    }

    public Float getCenteneBotePassPrice() {
        float limpio = this.config.getBoolean(Config.SEPARATE_LIMPIO_BOTE).booleanValue() ? getLimpioParlletCenteneBote() : getTotalLimpioBote();
        float maxPrice = this.config.getFloat(Config.MAX_CENTENE_PRICE_BOTE).floatValue();
        int basePrice = this.config.getInteger(Config.BASE_CENTENE_PRICE_BOTE).intValue();
        if (basePrice == 0) {
            return Float.valueOf(0.0f);
        }
        Set<String> keys = this.centeneBote.keySet();
        float percent = round(Float.valueOf((limpio * maxPrice) / ((float) basePrice))).floatValue();
        float pass = 0.0f;
        for (String key : keys) {
            float pay = this.centeneBote.get(key).floatValue();
            if (pay > percent || pay > maxPrice) {
                pass += (pay - Math.min(percent, maxPrice)) * ((float) CenteneBoteRow.pays_bote[key.length() - 3]);
            }
        }
        return Float.valueOf(pass);
    }

    public Float getBallFijoPass() {
        return round(getPass(this.config.getBoolean(Config.SEPARATE_LIMPIO).booleanValue() ? getLimpioBall() : getTotalLimpio(), this.config.getFloat(Config.MAX_BALL_PRICE).floatValue(), this.config.getInteger(Config.BASE_BALL_PRICE).intValue(), this.ballFijo));
    }

    public Float getBallFijoPassBote() {
        float limpio = this.config.getBoolean(Config.SEPARATE_LIMPIO_BOTE).booleanValue() ? getLimpioBallBote() : getTotalLimpioBote();
        if (this.config.getBoolean(Config.OPEN_BOTE).booleanValue()) {
            return round(new PriceOpenBote().getBallFijoPassBote(limpio, this.ballFijoBote));
        }
        return round(getPassBote(limpio, this.config.getFloat(Config.MAX_BALL_PRICE_BOTE).floatValue(), this.config.getInteger(Config.BASE_BALL_PRICE_BOTE).intValue(), this.ballFijoBote));
    }

    public Float getBallCorridoPass() {
        return round(getPass(this.config.getBoolean(Config.SEPARATE_LIMPIO).booleanValue() ? getLimpioBall() : getTotalLimpio(), this.config.getFloat(Config.MAX_BALL_PRICE).floatValue(), this.config.getInteger(Config.BASE_BALL_PRICE).intValue(), this.ballCorrido));
    }

    public Float getBallCorridoDoublePass() {
        return round(getPass(this.config.getBoolean(Config.SEPARATE_LIMPIO).booleanValue() ? getLimpioBall() : getTotalLimpio(), this.config.getFloat(Config.MAX_BALL_PRICE).floatValue(), this.config.getInteger(Config.BASE_BALL_PRICE).intValue(), this.ballCorridoDouble));
    }

    public Float getBallCorridoPassBote() {
        float limpio = this.config.getBoolean(Config.SEPARATE_LIMPIO_BOTE).booleanValue() ? getLimpioBallBote() : getTotalLimpioBote();
        if (this.config.getBoolean(Config.OPEN_BOTE).booleanValue()) {
            return new PriceOpenBote().getBallCorridoPassBote(limpio, this.ballCorridoBote);
        }
        return round(getPassBote(limpio, this.config.getFloat(Config.MAX_BALL_PRICE_BOTE).floatValue(), this.config.getInteger(Config.BASE_BALL_PRICE_BOTE).intValue(), this.ballCorridoBote));
    }

    public Float getBallCorridoDoublePassBote() {
        return round(getPassBote(this.config.getBoolean(Config.SEPARATE_LIMPIO).booleanValue() ? getLimpioBallBote() : getTotalLimpioBote(), this.config.getFloat(Config.MAX_BALL_PRICE_BOTE).floatValue(), this.config.getInteger(Config.BASE_BALL_PRICE_BOTE).intValue(), this.ballCorridoDoubleBote));
    }

    public Float getBallPass() {
        return round(Float.valueOf(getBallCorridoPass().floatValue() + getBallFijoPass().floatValue()));
    }

    public Float getBallPassBote() {
        return round(Float.valueOf(getBallCorridoPassBote().floatValue() + getBallFijoPassBote().floatValue()));
    }

    private Float payOf(HashMap<String, Float> map, int[] different_pays, int offset) {
        float pay = 0.0f;
        for (String key : map.keySet()) {
            pay += map.get(key).floatValue() * ((float) (different_pays == null ? 1 : different_pays[key.length() - offset]));
        }
        return Float.valueOf(pay);
    }

    public Float Pass() {
        return round(Float.valueOf(getBallPass().floatValue() + getParlletPass().floatValue() + getCentenePass().floatValue()));
    }

    public Float PassBote() {
        return round(Float.valueOf(getBallPassBote().floatValue() + getParlletPassBote().floatValue() + getCentenePassBote().floatValue()));
    }

    public Float pay() {
        float payBall = ((payOf(this.ballFijo, (int[]) null, 0).floatValue() - getBallFijoPass().floatValue()) * this.config.getFloat(Config.PAY_BALL_FIJO).floatValue()) + (Float.valueOf((payOf(this.ballCorridoDouble, (int[]) null, 0).floatValue() - getBallCorridoDoublePass().floatValue()) * 2.0f).floatValue() * this.config.getFloat(Config.PAY_BALL_CORRIDO).floatValue()) + ((payOf(this.ballCorrido, (int[]) null, 0).floatValue() - getBallCorridoPass().floatValue()) * this.config.getFloat(Config.PAY_BALL_CORRIDO).floatValue());
        float payParllet = (payOf(this.parllet, (int[]) null, 0).floatValue() - getParlletPass().floatValue()) * this.config.getFloat(Config.PAY_PARLLET).floatValue();
        return round(Float.valueOf(payBall + payParllet + (payOf(this.centene, CenteneRow.pays, 3).floatValue() - getCentenePassPrice().floatValue())));
    }

    public Float payAll() {
        return round(Float.valueOf((payOf(this.ballFijo, (int[]) null, 0).floatValue() * this.config.getFloat(Config.PAY_BALL_FIJO).floatValue()) + (Float.valueOf(payOf(this.ballCorridoDouble, (int[]) null, 0).floatValue() * 2.0f).floatValue() * this.config.getFloat(Config.PAY_BALL_CORRIDO).floatValue()) + (payOf(this.ballCorrido, (int[]) null, 0).floatValue() * this.config.getFloat(Config.PAY_BALL_CORRIDO).floatValue()) + (payOf(this.parllet, (int[]) null, 0).floatValue() * this.config.getFloat(Config.PAY_PARLLET).floatValue()) + payOf(this.centene, CenteneRow.pays, 3).floatValue()));
    }

    public Float payBote() {
        float payBall = ((payOf(this.ballFijoBote, (int[]) null, 0).floatValue() - getBallFijoPassBote().floatValue()) * this.config.getFloat(Config.PAY_BALL_FIJO_BOTE).floatValue()) + (Float.valueOf((payOf(this.ballCorridoDoubleBote, (int[]) null, 0).floatValue() - getBallCorridoDoublePassBote().floatValue()) * 2.0f).floatValue() * this.config.getFloat(Config.PAY_BALL_CORRIDO_BOTE).floatValue()) + ((payOf(this.ballCorridoBote, (int[]) null, 0).floatValue() - getBallCorridoPassBote().floatValue()) * this.config.getFloat(Config.PAY_BALL_CORRIDO_BOTE).floatValue());
        float payParllet = (payOf(this.parlletBote, (int[]) null, 0).floatValue() - getParlletPassBote().floatValue()) * this.config.getFloat(Config.PAY_PARLLET_BOTE).floatValue();
        return round(Float.valueOf(payBall + payParllet + (payOf(this.centeneBote, CenteneBoteRow.pays_bote, 3).floatValue() - getCenteneBotePassPrice().floatValue())));
    }

    public Float payAllBote() {
        return round(Float.valueOf((payOf(this.ballFijoBote, (int[]) null, 0).floatValue() * this.config.getFloat(Config.PAY_BALL_FIJO_BOTE).floatValue()) + (Float.valueOf(payOf(this.ballCorridoDoubleBote, (int[]) null, 0).floatValue() * 2.0f).floatValue() * this.config.getFloat(Config.PAY_BALL_CORRIDO_BOTE).floatValue()) + (payOf(this.ballCorridoBote, (int[]) null, 0).floatValue() * this.config.getFloat(Config.PAY_BALL_CORRIDO_BOTE).floatValue()) + (payOf(this.parlletBote, (int[]) null, 0).floatValue() * this.config.getFloat(Config.PAY_PARLLET_BOTE).floatValue()) + payOf(this.centeneBote, CenteneBoteRow.pays_bote, 3).floatValue()));
    }

    private String equalPriceAux(HashMap<String, Float> map_fijo, HashMap<String, Float> map_corrido, String BALL_COUNT, String BALL_PERCENT, String BALL_PERCENT_100) {
        String number;
        String fijo;
        String list = Constants.NULL_STRING;
        int count_number = 0;
        for (int i = 0; i < 100; i++) {
            if (i < 10) {
                number = "0" + i;
            } else {
                number = String.valueOf(i);
            }
            Float aux = map_fijo.get(number);
            if (aux == null) {
                fijo = "X ";
            } else {
                fijo = aux + " ";
            }
            Float aux1 = map_corrido.get(number);
            String corrido = "\n";
            if (aux1 != null) {
                corrido = aux1 + corrido;
            }
            if (aux == null && aux1 == null) {
                fijo = Constants.NULL_STRING;
                corrido = "Vacio\n";
            } else {
                count_number++;
            }
            list = list + number + ": " + fijo + corrido;
        }
        String change_percent = Constants.NULL_STRING;
        if (count_number > this.config.getInteger(BALL_COUNT).intValue() && !this.config.getInteger(BALL_PERCENT).equals(this.config.getInteger(BALL_PERCENT_100))) {
            change_percent = "\nSe cambia el porciento para " + this.config.getInteger(BALL_PERCENT_100) + "%";
        }
        return list + "--------------------\n" + count_number + " numeros jugados." + change_percent;
    }

    public String equalPrice() {
        return equalPriceAux(this.countBallFijo, this.countBallCorrido, Config.BALL_COUNT_NUMBER, Config.BALL_PERCENT, Config.BALL_PERCENT_100);
    }

    public String equalPriceBote() {
        return equalPriceAux(this.countBallFijoBote, this.countBallCorridoBote, Config.PARLLET_COUNT_NUMBER_BOTE, Config.BALL_PERCENT_BOTE, Config.BALL_PERCENT_100_BOTE);
    }
}
