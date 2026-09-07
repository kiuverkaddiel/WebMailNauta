package kiu.webmailnauta.legacy;

import android.util.Log;

public class ParlletBoteRow extends ParlletRow {
    public ParlletBoteRow(ContentValues vrow) {
        super(vrow);
    }

    public ParlletBoteRow() {
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
        float razonLimitParlet = config.getFloat(Config.PAY_PARLLET_LIMIT_BOTE).floatValue() / config.getFloat(Config.PAY_PARLLET_BOTE).floatValue();
        float razonCombination = 1.0f;
        if (combination.length > 45) {
            razonCombination = 1000.0f / config.getFloat(Config.PAY_PARLLET_BOTE).floatValue();
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
                                price2.addParlletBote(comb, Float.valueOf(basePrice.floatValue() * razonLimitParlet));
                                isNotLimit = false;
                                break;
                            }
                            i10++;
                            length5 = i11;
                        }
                        if (isNotLimit) {
                            price2.addParlletBote(comb, Float.valueOf(basePrice.floatValue() * razonCombination));
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

    /* access modifiers changed from: protected */
    public boolean canAddChar() {
        if (config.getInteger(Config.MAX_PARLLET_PRICE_BOTE).intValue() != 0) {
            return false;
        }
        this._unlockKeys = new StringBuilder();
        return true;
    }

    /* access modifiers changed from: protected */
    public void updateKeyboard() {
        if (this._data.length() >= 5) {
            if (getLastChar() != 'S') {
                char lastChar = getLastChar();
                if (isANumber(lastChar)) {
                    lastChar = Constants.NUMBER;
                }
                switch (lastChar) {
                    case ')':
                        KeyboardOnlyOK();
                        break;
                    case '.':
                        KeyboardOnlyNumbers();
                        break;
                    case 'N':
                        String[] parts = getParts();
                        if (decimalPart(parts[parts.length - 1]) != 1) {
                            if (decimalPart(parts[parts.length - 1]) != 2) {
                                if (this._data.length() != 29) {
                                    if (this._data.length() != 31) {
                                        if (this._data.length() != 32) {
                                            if (parts.length > 2) {
                                                if (!getLastPart().equals(String.valueOf(Constants.NUMBER5))) {
                                                    if (integerPart(getLastPart()) >= 2) {
                                                        if (integerPart(getLastPart()) >= 2) {
                                                            StringBuilder sb = new StringBuilder();
                                                            sb.append(Constants.NUMBER);
                                                            sb.append(Constants.CIRCLE);
                                                            sb.append(Constants.POINT);
                                                            this._unlockKeys = sb;
                                                            if (Float.valueOf(getLastPart()).floatValue() > config.getFloat(Config.MAX_PARLLET_PRICE_BOTE).floatValue()) {
                                                                Log.d("mayor", "si es mayor");
                                                                StringBuilder sb2 = new StringBuilder();
                                                                sb2.append(Constants.NUMBER);
                                                                this._unlockKeys = sb2;
                                                            } else if (Float.valueOf(getLastPart()).equals(config.getFloat(Config.MAX_PARLLET_PRICE_BOTE))) {
                                                                StringBuilder sb3 = new StringBuilder();
                                                                sb3.append(Constants.CIRCLE);
                                                                sb3.append(Constants.NUMBER);
                                                                this._unlockKeys = sb3;
                                                            }
                                                            if (getLastChar() == '0' || getLastChar() == '5') {
                                                                this._unlockKeys.append(Constants.OK);
                                                                break;
                                                            }
                                                        }
                                                    } else {
                                                        StringBuilder sb4 = new StringBuilder();
                                                        sb4.append(Constants.NUMBER);
                                                        sb4.append(Constants.CIRCLE);
                                                        sb4.append(Constants.POINT);
                                                        this._unlockKeys = sb4;
                                                        break;
                                                    }
                                                } else {
                                                    StringBuilder sb5 = new StringBuilder();
                                                    sb5.append(Constants.NUMBER);
                                                    sb5.append(Constants.CIRCLE);
                                                    sb5.append(Constants.POINT);
                                                    sb5.append(Constants.OK);
                                                    this._unlockKeys = sb5;
                                                    break;
                                                }
                                            } else {
                                                KeyboardOnlyNumbers();
                                                break;
                                            }
                                        } else {
                                            this._unlockKeys = new StringBuilder();
                                            float price = Float.valueOf(getLastPart()).floatValue();
                                            if (price < config.getFloat(Config.MAX_PARLLET_PRICE_BOTE).floatValue()) {
                                                StringBuilder sb6 = this._unlockKeys;
                                                sb6.append(Constants.CIRCLE);
                                                sb6.append(Constants.POINT);
                                            }
                                            if (price == config.getFloat(Config.MAX_PARLLET_PRICE_BOTE).floatValue()) {
                                                this._unlockKeys.append(Constants.CIRCLE);
                                            }
                                            if (getLastChar() == '5' || getLastChar() == '0') {
                                                this._unlockKeys.append(Constants.OK);
                                                break;
                                            }
                                        }
                                    } else {
                                        KeyboardOnlyNumbers();
                                        if (integerPart(getLastPart()) < config.getString(Config.MAX_PARLLET_PRICE_BOTE).length()) {
                                            this._unlockKeys.append(Constants.CIRCLE);
                                        }
                                        this._unlockKeys.append(Constants.POINT);
                                        if (getLastChar() == '5' || getLastChar() == '0') {
                                            this._unlockKeys.append(Constants.OK);
                                            break;
                                        }
                                    }
                                } else {
                                    KeyboardOnlyNumbersWichoutZero();
                                    if (Float.valueOf(getLastPart()).floatValue() > config.getFloat(Config.MAX_PARLLET_PRICE_BOTE).floatValue()) {
                                        KeyboardOnlyNumbersWichoutZero();
                                    } else if (Float.valueOf(getLastPart()).equals(config.getFloat(Config.MAX_PARLLET_PRICE_BOTE))) {
                                        this._unlockKeys.append(Constants.CIRCLE);
                                    } else {
                                        StringBuilder sb7 = this._unlockKeys;
                                        sb7.append(Constants.POINT);
                                        sb7.append(Constants.CIRCLE);
                                    }
                                    if (getLastChar() == '5' || getLastChar() == '0') {
                                        this._unlockKeys.append(Constants.OK);
                                        break;
                                    }
                                }
                            } else {
                                KeyboardOnlyOK();
                                break;
                            }
                        } else if (getLastChar() != '0') {
                            StringBuilder sb8 = new StringBuilder();
                            sb8.append(Constants.NUMBER0);
                            sb8.append(Constants.NUMBER5);
                            this._unlockKeys = sb8;
                            break;
                        } else {
                            StringBuilder sb9 = new StringBuilder();
                            sb9.append(Constants.NUMBER5);
                            this._unlockKeys = sb9;
                            break;
                        }
                        break;
                }
            } else if (this._data.length() == 30) {
                KeyboardOnlyNumbersWichoutZero();
            } else {
                KeyboardOnlyNumbers();
            }
        } else {
            KeyboardOnlyNumbers();
        }
        if (getLastPart().startsWith(String.valueOf(Constants.NUMBER0))) {
            if (this._data.length() < 29) {
                KeyboardOnlyNumbers();
            }
            if (this._data.length() == 29) {
                KeyboardOnlyNumbersWichoutZero();
            }
        }
    }

    /* access modifiers changed from: protected */
    public Float getCountPrice(Price price, String number) {
        return price.getCountPriceParlletBote(number);
    }

    public int getType() {
        return Constants.PARLLET_BOTE;
    }

    public String getError(Price price) {
        String errorString = price.getParlletPassMoreThan(getSingleParlletCombination(), getBasePrice()[0]);
        if (errorString != null) {
            return errorString;
        }
        return getError(price, Math.min(config.getInteger(Config.MAX_PARLLET_PRICE_BOTE).intValue(), config.getInteger(Config.TOP_PARLLET_BOTE).intValue()));
    }

    /* access modifiers changed from: protected */
    public String getCountLimitParlletNotPlay() {
        return Config.COUNT_NOT_PLAY_NUMBER_BOTE;
    }

    public void addPriceMap(Price price) {
        String[] combination = getSingleParlletCombination();
        Float basePrice = getBasePrice()[0];
        for (String cmb : combination) {
            price.addCountPriceParlletBote(cmb, basePrice);
        }
    }

    public void delPriceMap(Price price) {
        String[] combination = getSingleParlletCombination();
        Float basePrice = getBasePrice()[0];
        for (String cmb : combination) {
            price.delCountPriceParlletBote(cmb, basePrice);
        }
    }
}
