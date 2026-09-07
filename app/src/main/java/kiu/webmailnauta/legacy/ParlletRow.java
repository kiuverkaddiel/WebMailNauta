package kiu.webmailnauta.legacy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class ParlletRow extends Row {
    public ParlletRow(ContentValues vrow) {
        super(vrow);
    }

    public ParlletRow() {
        this._unlockKeys.append(Constants.EMPTY);
    }

    /* access modifiers changed from: protected */
    public boolean canAddChar() {
        if (config.getInteger(Config.MAX_PARLLET_PRICE).intValue() != 0) {
            return false;
        }
        this._unlockKeys = new StringBuilder();
        return true;
    }

    public void AddCharacter(char c) {
        if (!canAddChar()) {
            if (this._data.length() < 2) {
                this._data.append(c);
            } else {
                if (this._data.toString().endsWith(String.valueOf(Constants.CIRCLE))) {
                    this._data.insert(this._data.length() - 1, c);
                } else {
                    this._data.append(c);
                }
                String lastPart = getLastPart();
                if (decimalPart(lastPart) == 2) {
                    this._data.append(Constants.CIRCLE);
                }
                if (!lastPart.endsWith(String.valueOf(Constants.CIRCLE)) && c != ')' && integerPart(lastPart) == 3) {
                    this._data.insert(this._data.length() - 1, Constants.SPACE);
                }
            }
            updateKeyboard();
        }
    }

    public void deleteBackspace() {
        if (getLastPart().length() > 3) {
            this._data.deleteCharAt(this._data.length() - 2);
        } else {
            super.deleteBackspace();
        }
    }

    public String toString() {
        int i;
        if (isDataEmpty()) {
            return Constants.NULL_STRING;
        }
        String[] data = correctParentesis(this._data.toString()).split(String.valueOf(Constants.SPACE));
        if ((data.length - 1) % 2 == 0) {
            i = data.length - 2;
        } else {
            i = data.length - 1;
        }
        int halft = i / 2;
        data[halft] = data[halft] + " " + data[data.length - 1];
        String aux = Constants.NULL_STRING;
        for (int i2 = 0; i2 < data.length - 1; i2++) {
            aux = aux + " " + data[i2] + "\n";
        }
        return (aux + "------------").replace(Constants.SPACE, ' ');
    }

    /* access modifiers changed from: protected */
    public void updateKeyboard() {
        if (this._data.length() < 5) {
            if (this._data.length() <= 3) {
                KeyboardOnlyNumbersAndX();
            } else if (getLastPart().contains(String.valueOf(Constants.EMPTY))) {
                KeyboardOnlyNumbers();
            } else {
                KeyboardOnlyNumbersAndX();
                if (this._data.charAt(3) == '0') {
                    return;
                }
                if (this._data.charAt(0) == 'X' || this._data.charAt(1) == 'X') {
                    this._unlockKeys.append(Constants.CIRCLE);
                }
            }
        } else if (getLastPart().charAt(0) == '0') {
            KeyboardOnlyNumbers();
        } else if (getLastChar() != 'S') {
            if (this._data.length() == 5 && getLastPart().contains(String.valueOf(Constants.EMPTY))) {
                KeyboardOnlyNumbers();
            }
            char lastChar = getLastChar();
            if (isANumber(lastChar)) {
                lastChar = Constants.NUMBER;
            }
            switch (lastChar) {
                case ')':
                    if (!this._data.toString().contains(".")) {
                        KeyboardOnlyNumbers();
                        this._unlockKeys.append(Constants.OK);
                        return;
                    }
                    KeyboardOnlyOK();
                    return;
                case '.':
                    KeyboardOnlyNumbers();
                    return;
                case 'N':
                    String[] parts = getParts();
                    if (decimalPart(parts[parts.length - 1]) == 1) {
                        if (getLastChar() == '0') {
                            StringBuilder sb = new StringBuilder();
                            sb.append(Constants.NUMBER5);
                            this._unlockKeys = sb;
                            return;
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(Constants.NUMBER0);
                        sb2.append(Constants.NUMBER5);
                        this._unlockKeys = sb2;
                        return;
                    } else if (decimalPart(parts[parts.length - 1]) == 2) {
                        KeyboardOnlyOK();
                        return;
                    } else if (this._data.length() == 119) {
                        KeyboardOnlyNumbersWichoutZero();
                        if (Float.parseFloat(getLastPart()) > config.getFloat(Config.MAX_PARLLET_PRICE).floatValue()) {
                            KeyboardOnlyNumbersWichoutZero();
                        } else if (Float.valueOf(getLastPart()).equals(config.getFloat(Config.MAX_PARLLET_PRICE))) {
                            this._unlockKeys.append(Constants.CIRCLE);
                        } else {
                            StringBuilder sb3 = this._unlockKeys;
                            sb3.append(Constants.POINT);
                            sb3.append(Constants.CIRCLE);
                        }
                        if (getLastChar() == '5' || getLastChar() == '0') {
                            this._unlockKeys.append(Constants.OK);
                            return;
                        }
                        return;
                    } else if (this._data.length() == 121) {
                        KeyboardOnlyNumbers();
                        if (integerPart(getLastPart()) < config.getString(Config.MAX_PARLLET_PRICE).length()) {
                            this._unlockKeys.append(Constants.CIRCLE);
                        }
                        this._unlockKeys.append(Constants.POINT);
                        if (getLastChar() == '5' || getLastChar() == '0') {
                            this._unlockKeys.append(Constants.OK);
                            return;
                        }
                        return;
                    } else if (this._data.length() == 122) {
                        this._unlockKeys = new StringBuilder();
                        float price = Float.parseFloat(getLastPart());
                        if (price < config.getFloat(Config.MAX_PARLLET_PRICE).floatValue()) {
                            StringBuilder sb4 = this._unlockKeys;
                            sb4.append(Constants.CIRCLE);
                            sb4.append(Constants.POINT);
                        }
                        if (price == config.getFloat(Config.MAX_PARLLET_PRICE).floatValue()) {
                            this._unlockKeys.append(Constants.CIRCLE);
                        }
                        if (getLastChar() == '5' || getLastChar() == '0') {
                            this._unlockKeys.append(Constants.OK);
                            return;
                        }
                        return;
                    } else if (parts.length <= 2) {
                        KeyboardOnlyNumbers();
                        return;
                    } else if (getLastPart().equals(String.valueOf(Constants.NUMBER5))) {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(Constants.NUMBER);
                        sb5.append(Constants.CIRCLE);
                        sb5.append(Constants.POINT);
                        sb5.append(Constants.OK);
                        this._unlockKeys = sb5;
                        return;
                    } else if (integerPart(getLastPart()) < 2) {
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(Constants.NUMBER);
                        sb6.append(Constants.CIRCLE);
                        sb6.append(Constants.POINT);
                        this._unlockKeys = sb6;
                        return;
                    } else if (integerPart(getLastPart()) >= 2) {
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(Constants.NUMBER);
                        sb7.append(Constants.CIRCLE);
                        sb7.append(Constants.POINT);
                        this._unlockKeys = sb7;
                        if (Float.parseFloat(getLastPart()) > config.getFloat(Config.MAX_PARLLET_PRICE).floatValue()) {
                            StringBuilder sb8 = new StringBuilder();
                            sb8.append(Constants.NUMBER);
                            this._unlockKeys = sb8;
                        } else if (Float.valueOf(getLastPart()).equals(config.getFloat(Config.MAX_PARLLET_PRICE))) {
                            StringBuilder sb9 = new StringBuilder();
                            sb9.append(Constants.CIRCLE);
                            sb9.append(Constants.NUMBER);
                            this._unlockKeys = sb9;
                        }
                        if (getLastChar() == '0' || getLastChar() == '5') {
                            this._unlockKeys.append(Constants.OK);
                            return;
                        }
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        } else if (this._data.length() == 30) {
            KeyboardOnlyNumbersWichoutZero();
        } else {
            KeyboardOnlyNumbers();
        }
    }

    private int countCombination() {
        int countNumbers = getParts().length - 1;
        return ((countNumbers - 1) * countNumbers) / 2;
    }

    public int getColor(Bingo bingo, String[] noPlayNumber) {
        int i;
        if (!noPlayNumber[0].equals("no")) {
            String[] auxNoPlayNumber = new String[(noPlayNumber.length * 2)];
            int i2 = 0;
            for (String noPlay : noPlayNumber) {
                int i3 = i2 + 1;
                auxNoPlayNumber[i2] = noPlay.replace(Constants.SHOW_NUMBER_SEPARATOR, Constants.SPACE);
                String[] parts = noPlay.split(String.valueOf(Constants.SHOW_NUMBER_SEPARATOR));
                if (parts.length == 2) {
                    i = i3 + 1;
                    auxNoPlayNumber[i3] = parts[1] + Constants.SPACE + parts[0];
                } else {
                    i = i3 + 1;
                    auxNoPlayNumber[i3] = noPlay;
                }
                i2 = i;
            }
            noPlayNumber = auxNoPlayNumber;
        }
        String[] auxNoPlayNumber2 = bingo.getParlletCombination();
        String[] combination = getSingleParlletCombination();
        for (String bingocomb : auxNoPlayNumber2) {
            boolean _continue = false;
            int length = noPlayNumber.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    break;
                } else if (bingocomb.equals(noPlayNumber[i4])) {
                    _continue = true;
                    break;
                } else {
                    i4++;
                }
            }
            if (!_continue) {
                for (String comb : combination) {
                    if (comb.compareTo(bingocomb) == 0) {
                        return Constants.BINGO_PARLLET;
                    }
                }
                continue;
            }
        }
        return -16777216;
    }

    public void getPay(Bingo bingo, Price price, String[] limitNumber, String[] noPlayNumber) {
        String[] noPlayNumber2;
        String[] bingoCombinations;
        int i;
        Price price2 = price;
        String[] strArr = limitNumber;
        boolean equals = strArr[0].equals("no");
        char c = Constants.SHOW_NUMBER_SEPARATOR;
        if (!equals) {
            String[] auxLimitNumber = new String[(strArr.length * 2)];
            int i2 = 0;
            int length = strArr.length;
            int i3 = 0;
            while (i3 < length) {
                String limit = strArr[i3];
                int i4 = i2 + 1;
                auxLimitNumber[i2] = limit.replace(c, Constants.SPACE);
                String[] parts = limit.split(String.valueOf(c));
                if (parts.length == 2) {
                    auxLimitNumber[i4] = parts[1] + Constants.SPACE + parts[0];
                    i2 = i4 + 1;
                } else {
                    auxLimitNumber[i4] = limit;
                    i2 = i4 + 1;
                }
                i3++;
                c = Constants.SHOW_NUMBER_SEPARATOR;
            }
            noPlayNumber2 = auxLimitNumber;
        } else {
            noPlayNumber2 = noPlayNumber;
        }
        if (!noPlayNumber2[0].equals("no")) {
            String[] auxNoPlayNumber = new String[(noPlayNumber2.length * 2)];
            int i5 = 0;
            for (String noPlay : noPlayNumber2) {
                int i6 = i5 + 1;
                auxNoPlayNumber[i5] = noPlay.replace(Constants.SHOW_NUMBER_SEPARATOR, Constants.SPACE);
                String[] parts2 = noPlay.split(String.valueOf(Constants.SHOW_NUMBER_SEPARATOR));
                if (parts2.length == 2) {
                    i = i6 + 1;
                    auxNoPlayNumber[i6] = parts2[1] + Constants.SPACE + parts2[0];
                } else {
                    i = i6 + 1;
                    auxNoPlayNumber[i6] = noPlay;
                }
                i5 = i;
            }
            noPlayNumber2 = auxNoPlayNumber;
        }
        String[] bingoCombinations2 = bingo.getParlletCombination();
        String[] combination = getSingleParlletCombination();
        Float basePrice = getBasePrice()[0];
        float razonLimitParlet = config.getFloat(Config.PAY_PARLLET_LIMIT).floatValue() / config.getFloat(Config.PAY_PARLLET).floatValue();
        float razonCombination = 1.0f;
        if (combination.length > 45) {
            razonCombination = 1000.0f / config.getFloat(Config.PAY_PARLLET).floatValue();
        }
        int length2 = bingoCombinations2.length;
        int i7 = 0;
        while (i7 < length2) {
            String bingocomb = bingoCombinations2[i7];
            boolean _continue = false;
            int length3 = noPlayNumber2.length;
            int i8 = 0;
            while (true) {
                if (i8 >= length3) {
                    break;
                } else if (bingocomb.equals(noPlayNumber2[i8])) {
                    _continue = true;
                    break;
                } else {
                    i8++;
                }
            }
            if (!_continue) {
                int length4 = combination.length;
                int i9 = 0;
                while (i9 < length4) {
                    String comb = combination[i9];
                    if (comb.compareTo(bingocomb) == 0) {
                        boolean isNotLimit = true;
                        int length5 = strArr.length;
                        bingoCombinations = bingoCombinations2;
                        int i10 = 0;
                        while (true) {
                            if (i10 >= length5) {
                                break;
                            }
                            int i11 = length5;
                            if (comb.equals(strArr[i10])) {
                                price2.addParllet(comb, Float.valueOf(basePrice.floatValue() * razonLimitParlet));
                                isNotLimit = false;
                                break;
                            }
                            i10++;
                            length5 = i11;
                        }
                        if (isNotLimit) {
                            price2.addParllet(comb, Float.valueOf(basePrice.floatValue() * razonCombination));
                        }
                    } else {
                        bingoCombinations = bingoCombinations2;
                    }
                    i9++;
                    bingoCombinations2 = bingoCombinations;
                }
            }
            i7++;
            bingoCombinations2 = bingoCombinations2;
        }
    }

    public Float[] getBasePrice() {
        return new Float[]{convertToFloat(getLastPart())};
    }

    public Float[] getPrice() {
        return new Float[]{Float.valueOf(convertToFloat(getLastPart()).floatValue() * ((float) countCombination()))};
    }

    public String getPriceString() {
        int index = this._data.lastIndexOf(String.valueOf(Constants.SPACE));
        return index == -1 ? Constants.NULL_STRING : this._data.substring(index + 1);
    }

    public int getType() {
        return Constants.PARLLET;
    }

    public String[] getSingleParlletCombination() {
        String[] combination = new String[countCombination()];
        String[] numbers = getNumbersString(this._data.toString()).split(String.valueOf(Constants.SPACE));
        int index = 0;
        for (int i = 0; i < numbers.length - 1; i++) {
            int j = i + 1;
            while (j < numbers.length) {
                combination[index] = numbers[i] + Constants.SPACE + numbers[j];
                j++;
                index++;
            }
        }
        return combination;
    }

    public String[] getAllParlletCombination() {
        String[] combinations = getSingleParlletCombination();
        String[] allCombiantions = new String[(combinations.length * 2)];
        int i = 0;
        int j = combinations.length;
        while (i < combinations.length) {
            allCombiantions[i] = combinations[i];
            allCombiantions[j] = reverseCombination(combinations[i]);
            i++;
            j++;
        }
        return allCombiantions;
    }

    /* access modifiers changed from: protected */
    public int countCombination(String[] combinations, String cmb) {
        int count = 0;
        for (String c : combinations) {
            if (c.equals(cmb) || reverseCombination(c).equals(cmb)) {
                count++;
            }
        }
        return count;
    }

    private String reverseCombination(String cmb) {
        String[] n = cmb.split(String.valueOf(Constants.SPACE));
        return n[1] + Constants.SPACE + n[0];
    }

    public String[] getArrayString(String s) {
        if (s == null) {
            return null;
        }
        String[] ar = s.split(String.valueOf(','));
        if (ar.length != 0) {
            return ar;
        }
        return new String[]{s};
    }

    /* access modifiers changed from: protected */
    public Float getCountPrice(Price price, String number) {
        return price.getCountPriceParllet(number);
    }

    /* access modifiers changed from: protected */
    public String getCountLimitParlletNotPlay() {
        return Config.COUNT_NOT_PLAY_NUMBER_LIST;
    }

    /* access modifiers changed from: protected */
    public void fillParlet() {
        String data = this._data.toString();
        if (data.contains(String.valueOf(Constants.EMPTY))) {
            String[] parts = getParts();
            for (int i = 0; i < 2; i++) {
                if (parts[i].contains(String.valueOf(Constants.EMPTY))) {
                    String x = parts[i];
                    String aux = x.replace(Constants.EMPTY, Constants.NUMBER0);
                    for (int j = 1; j < 10; j++) {
                        aux = aux + Constants.SPACE + x.replace(Constants.EMPTY, (char) (j + 48));
                    }
                    data = data.replace(x, aux);
                }
            }
            this._data = new StringBuilder(data);
        }
    }

    public String getError(Price var1, int var2) {
        if (_data.length() < 5) {
            return "Error. Parle mal escrito";
        } else {
            fillParlet();
            StringBuilder var14 = new StringBuilder();
            String[] var11 = getNumbersString(_data.toString()).split(String.valueOf('S'));
            String[] var10 = getAllParlletCombination();
            String[] var9 = getArrayString(config.getString(Config.LIMIT_PARLLET_NOT_PLAY));
            byte var4 = 0;
            String var8 = config.getString(getCountLimitParlletNotPlay());
            int var3 = var4;
            if (var8 != null) {
                var3 = var4;
                if (!var8.isEmpty()) {
                    var3 = Integer.parseInt(var8);
                }
            }

            byte var5 = 0;
            String[] var21;
            if (var3 == 0) {
                var21 = new String[0];
            } else {
                var21 = var9;
                if (var9.length > var3) {
                    var21 = Arrays.copyOf(var9, var3);
                }
            }

            String var12;
            int var19;
            if (var21.length > 0 && !var21[0].equals("no")) {
                int var6 = var21.length;

                for (var3 = 0; var3 < var6; ++var3) {
                    var12 = var21[var3];
                    int var7 = var10.length;

                    for (var19 = 0; var19 < var7; ++var19) {
                        String var22 = var10[var19].replace('S', '-');
                        if (var22.equals(var12)) {
                            StringBuilder var17 = new StringBuilder();
                            var17.append("El parle ");
                            var17.append(var22);
                            var17.append(" no juega, no puedes ponerlo. Tienes que desglozar el candado.");
                            return var17.toString();
                        }
                    }
                }
            }

            HashMap var23 = new HashMap();
            var19 = var11.length;

            for (var3 = 0; var3 < var19; ++var3) {
                var12 = var11[var3];
                if (var23.containsKey(var12)) {
                    var23.put(var12, (Integer) var23.get(var12) + 1);
                } else {
                    var23.put(var12, 1);
                }
            }

            Iterator var13 = var23.keySet().iterator();

            while (var13.hasNext()) {
                var12 = (String) var13.next();
                if ((Integer) var23.get(var12) > 3 && var14.length() > 0) {
                    var14.append("\n");
                    var14.append("El número ");
                    var14.append(var12);
                    var14.append(" se repite más de tres veces.");
                }
            }

            Float var25 = this.getBasePrice()[0];
            Float var24 = var25;
            if (config.getBoolean("direct_sum")) {
                var24 = var25 / (float) this.countCombination();
            }

            String[] var15 = this.getSingleParlletCombination();
            var19 = var15.length;

            for (var3 = var5; var3 < var19; ++var3) {
                String var16 = var15[var3];
                Float var26 = this.getCountPrice(var1, var16);
                var25 = var26;
                if (var26 == null) {
                    var25 = 0.0F;
                }

                int var20 = this.countCombination(var15, var16);
                if (var25 + var24 * (float) var20 > (float) var2) {
                    if (var14.length() != 0) {
                        var14.append("\n");
                    }

                    String var27 = var16.replace('S', '-');
                    var14.append("El parlet ");
                    var14.append(var27);
                    var14.append(" se pasa, ");
                    if (var25 != 0.0F) {
                        var14.append("ya tiene ");
                        var14.append(var25.toString());
                    } else {
                        var14.append("va a tener ");
                        var14.append(var24 * (float) var20);
                    }

                    var14.append(" pesos.");
                }
            }

            var2 = var14.length();
            String var18 = null;
            var8 = null;
            if (var2 == 0) {
                if (this.getParts().length - 1 > 10) {
                    var14.append("info:El parle\n");
                    var14.append(this);
                    var14.append("\nSe paga a 1000$");
                }

                var18 = var8;
                if (var14.length() > 0) {
                    var18 = var14.toString();
                }

                return var18;
            } else {
                if (var14.length() > 0) {
                    var18 = var14.toString();
                }

                return var18;
            }
        }
    }

    public String getError(Price price) {
        String errorString = price.getParlletPassMoreThan(getSingleParlletCombination(), getBasePrice()[0]);
        if (errorString != null) {
            return errorString;
        }
        return getError(price, Math.min(config.getInteger(Config.MAX_PARLLET_PRICE).intValue(), config.getInteger(Config.TOP_PARLLET).intValue()));
    }

    public void addPriceMap(Price price) {
        String[] combination = getSingleParlletCombination();
        Float basePrice = getBasePrice()[0];
        for (String cmb : combination) {
            price.addCountPriceParllet(cmb, basePrice);
            price.addNumberParlletPassOfPri(cmb);
        }
    }

    public void delPriceMap(Price price) {
        String[] combination = getSingleParlletCombination();
        Float basePrice = getBasePrice()[0];
        for (String cmb : combination) {
            price.delCountPriceParllet(cmb, basePrice);
            price.delNumberParlletPassOfPri(cmb);
        }
    }

    public void onOkButtonPressed() {
        int countCombination;
        String newPriceString;
        if (config.getBoolean(Config.DIRECT_SUM).booleanValue() && (countCombination = countCombination()) > 1) {
            Float newPrice = roundPrice(Float.valueOf(getBasePrice()[0].floatValue() / ((float) countCombination)));
            String newPriceString2 = newPrice.toString();
            if (newPriceString2.endsWith(".0")) {
                newPriceString2 = newPriceString2.replace(".0", Constants.NULL_STRING);
            }
            if (newPrice.floatValue() >= 1.0f) {
                newPriceString = newPriceString2 + ")";
            } else {
                newPriceString = newPriceString2.replace("0.", Constants.NULL_STRING);
            }
            String[] parts = getParts();
            parts[parts.length - 1] = newPriceString;
            this._data = joinString(Constants.SPACE, parts);
        }
    }

    private Float roundPrice(Float n) {
        String[] parts = n.toString().split("\\.");
        if (parts[1].length() <= 2) {
            return n;
        }
        return Float.valueOf(Float.parseFloat(parts[0] + "." + parts[1].substring(0, 2)));
    }

    private StringBuilder joinString(char separator, String... elements) {
        StringBuilder aux = new StringBuilder();
        aux.append(elements[0]);
        for (int i = 1; i < elements.length; i++) {
            aux.append(separator);
            aux.append(elements[i]);
        }
        return aux;
    }
}
