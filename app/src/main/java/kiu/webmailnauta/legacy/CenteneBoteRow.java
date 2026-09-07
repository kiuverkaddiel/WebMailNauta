package kiu.webmailnauta.legacy;

public class CenteneBoteRow extends CenteneRow {
    public static final int[] pays_bote = {config.getInteger(Config.PAY_CENTENE_BOTE).intValue(), config.getInteger(Config.PAY_CENTENE_BOTE_4).intValue(), config.getInteger(Config.PAY_CENTENE_BOTE_5).intValue(), config.getInteger(Config.PAY_CENTENE_BOTE_6).intValue(), config.getInteger(Config.PAY_CENTENE_BOTE_7).intValue()};
    public static final int[] pays_bote_limit = {config.getInteger(Config.PAY_CENTENE_LIMIT_BOTE).intValue(), config.getInteger(Config.PAY_CENTENE_LIMIT_BOTE_4).intValue(), config.getInteger(Config.PAY_CENTENE_LIMIT_BOTE_5).intValue(), config.getInteger(Config.PAY_CENTENE_LIMIT_BOTE_6).intValue(), config.getInteger(Config.PAY_CENTENE_LIMIT_BOTE_7).intValue()};

    public CenteneBoteRow(ContentValues vrow) {
        super(vrow);
    }

    public CenteneBoteRow() {
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
                float razon = (float) (pays_bote_limit[pay_pos] / pays_bote[pay_pos]);
                float basePrice = getBasePrice()[0].floatValue();
                for (String limit_number : limitNumber) {
                    if (limit_number.startsWith(aux_number)) {
                        price.addCentene(number, Float.valueOf(basePrice * razon));
                        return;
                    }
                }
                price.addCentene(aux_number, Float.valueOf(basePrice));
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean canAddChar() {
        if (config.getInteger(Config.MAX_CENTENE_PRICE_BOTE).intValue() != 0) {
            return false;
        }
        this._unlockKeys = new StringBuilder();
        return true;
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
                    } else if (integerPart(parts[1]) < config.getString(Config.MAX_CENTENE_PRICE_BOTE).length()) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(Constants.CIRCLE);
                        sb3.append(Constants.POINT);
                        sb3.append(Constants.NUMBER);
                        this._unlockKeys = sb3;
                        return;
                    } else if (integerPart(parts[1]) >= config.getString(Config.MAX_CENTENE_PRICE_BOTE).length()) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(Constants.CIRCLE);
                        sb4.append(Constants.POINT);
                        this._unlockKeys = sb4;
                        if (Float.valueOf(parts[1]).equals(config.getFloat(Config.MAX_CENTENE_PRICE_BOTE))) {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(Constants.CIRCLE);
                            this._unlockKeys = sb5;
                        } else if (Float.valueOf(getLastPart()).floatValue() > config.getFloat(Config.MAX_CENTENE_PRICE_BOTE).floatValue()) {
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
        return Constants.CENTENE_BOTE;
    }

    /* access modifiers changed from: protected */
    public Float getCountPrice(Price price, String number) {
        return price.getCountPriceCenteneBote(number);
    }

    public String getError(Price price) {
        return getError(price, config.getInteger(Config.MAX_CENTENE_PRICE_BOTE).intValue());
    }

    public void addPriceMap(Price price) {
        price.addCountPriceCenteneBote(getNumbersString(this._data.toString()), getBasePrice()[0]);
    }

    public void delPriceMap(Price price) {
        price.delCountPriceCenteneBote(getNumbersString(this._data.toString()), getBasePrice()[0]);
    }
}
