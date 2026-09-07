package kiu.webmailnauta.legacy;

public class CenteneRow extends Row {
    public static final int COUNT_FOR_PAY = 3;
    public static final int[] pays = {config.getInteger(Config.PAY_CENTENE).intValue(), config.getInteger(Config.PAY_CENTENE_4).intValue(), config.getInteger(Config.PAY_CENTENE_5).intValue(), config.getInteger(Config.PAY_CENTENE_6).intValue(), config.getInteger(Config.PAY_CENTENE_7).intValue()};
    public static final int[] pays_limit = {config.getInteger(Config.PAY_CENTENE_LIMIT).intValue(), config.getInteger(Config.PAY_CENTENE_LIMIT_4).intValue(), config.getInteger(Config.PAY_CENTENE_LIMIT_5).intValue(), config.getInteger(Config.PAY_CENTENE_LIMIT_6).intValue(), config.getInteger(Config.PAY_CENTENE_LIMIT_7).intValue()};

    public CenteneRow(ContentValues vrow) {
        super(vrow);
    }

    public CenteneRow() {
    }

    public int getColor(Bingo bingo, String[] noPlayNumber) {
        String seven = bingo.getSeven();
        if (seven != null && seven.startsWith(getNumbersString(this._data.toString()).substring(0, 3))) {
            return Constants.BINGO_CENTENE;
        }
        return -16777216;
    }

    public Float[] getBasePrice() {
        return new Float[]{convertToFloat(getLastPart())};
    }

    public Float[] getPrice() {
        return new Float[]{convertToFloat(getLastPart())};
    }

    public void getPay(Bingo bingo, Price price, String[] limitNumber, String[] noPlayNumber) {
        String seven = bingo.getSeven();
        if (seven != null) {
            String number = getNumbersString(this._data.toString());
            int length = noPlayNumber.length;
            int i = 0;
            while (i < length) {
                if (!number.equals(noPlayNumber[i])) {
                    i++;
                } else {
                    return;
                }
            }
            String aux_number = Constants.NULL_STRING;
            int number_size = number.length();
            for (int i2 = 0; i2 < number_size; i2++) {
                char c = number.charAt(i2);
                if (c != seven.charAt(i2)) {
                    break;
                }
                aux_number = aux_number + c;
            }
            int pay_pos = aux_number.length() - 3;
            if (pay_pos >= 0) {
                float razon = (float) (pays_limit[pay_pos] / pays[pay_pos]);
                float basePrice = getBasePrice()[0].floatValue();
                for (String limit_number : limitNumber) {
                    if (limit_number.startsWith(aux_number)) {
                        price.addCentene(aux_number, Float.valueOf(basePrice * razon));
                        return;
                    }
                }
                price.addCentene(aux_number, Float.valueOf(basePrice));
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean canAddChar() {
        if (config.getInteger(Config.MAX_CENTENE_PRICE).intValue() != 0) {
            return false;
        }
        this._unlockKeys = new StringBuilder();
        return true;
    }

    public void AddCharacter(char c) {
        if (!canAddChar()) {
            this._data.append(c);
            if (this._data.length() == 3) {
                this._data.append(Constants.SPACE);
            }
            if (decimalPart(getLastPart()) == 2) {
                this._data.append(Constants.CIRCLE);
            }
            updateKeyboard();
        }
    }

    /* access modifiers changed from: protected */
    public void updateKeyboard() {
        if (isDataEmpty()) {
            KeyboardOnlyNumbers();
        } else if (getLastChar() == 'S') {
            KeyboardOnlyNumbersWichoutZero();
        } else {
            char lastChar = getLastChar();
            if (isANumber(lastChar)) {
                lastChar = Constants.NUMBER;
            }
            switch (lastChar) {
                case ')':
                    KeyboardOnlyOK();
                    return;
                case '.':
                    KeyboardOnlyNumbers();
                    return;
                case 'N':
                    if (getPosCursor() == 0) {
                        KeyboardOnlyNumbers();
                        return;
                    }
                    String[] parts = getParts();
                    if (parts == null || parts.length == 1) {
                        KeyboardOnlyNumbers();
                        return;
                    } else if (decimalPart(parts[1]) == 1) {
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
                    } else if (decimalPart(parts[1]) == 2) {
                        KeyboardOnlyOK();
                        return;
                    } else if (integerPart(parts[1]) < config.getString(Config.MAX_CENTENE_PRICE).length()) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(Constants.CIRCLE);
                        sb3.append(Constants.POINT);
                        sb3.append(Constants.NUMBER);
                        this._unlockKeys = sb3;
                        return;
                    } else if (integerPart(parts[1]) >= config.getString(Config.MAX_CENTENE_PRICE).length()) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(Constants.CIRCLE);
                        sb4.append(Constants.POINT);
                        this._unlockKeys = sb4;
                        if (Float.valueOf(parts[1]).equals(config.getFloat(Config.MAX_CENTENE_PRICE))) {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(Constants.CIRCLE);
                            this._unlockKeys = sb5;
                        } else if (Float.parseFloat(getLastPart()) > config.getFloat(Config.MAX_CENTENE_PRICE).floatValue()) {
                            this._unlockKeys = new StringBuilder();
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
        }
    }

    public int getType() {
        return Constants.CENTENE;
    }

    /* access modifiers changed from: protected */
    public Float getCountPrice(Price price, String number) {
        return price.getCountPriceCentene(number);
    }

    /* access modifiers changed from: protected */
    public final String getError(Price price, int top) {
        StringBuilder error = new StringBuilder();
        String number = getNumbersString(this._data.toString()).substring(0, 3);
        Float basePrice = getBasePrice()[0];
        Float countNumber = getCountPrice(price, number);
        if (countNumber == null) {
            countNumber = Float.valueOf(0.0f);
        }
        if (countNumber.floatValue() + basePrice.floatValue() > ((float) top)) {
            error.append("La centena ");
            error.append(number);
            error.append(" se pasa, ");
            error.append("ya tiene ");
            error.append(countNumber);
            error.append(" pesos.");
        }
        if (error.length() == 0) {
            return null;
        }
        return error.toString();
    }

    public String getError(Price price) {
        return getError(price, Math.min(config.getInteger(Config.MAX_CENTENE_PRICE).intValue(), config.getInteger(Config.TOP_PARLLET).intValue()));
    }

    public void addPriceMap(Price price) {
        price.addCountPriceCentene(getNumbersString(this._data.toString()), getBasePrice()[0]);
    }

    public void delPriceMap(Price price) {
        price.delCountPriceCentene(getNumbersString(this._data.toString()), getBasePrice()[0]);
    }
}
