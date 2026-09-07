package kiu.webmailnauta.legacy;

import org.bouncycastle.asn1.eac.EACTags;

public final class BallBoteRow extends BallRow {
    public BallBoteRow(ContentValues vrow) {
        super(vrow);
    }

    public BallBoteRow() {
    }

    public void getPay(Bingo bingo, Price price, String[] limitNumber, String[] noPlayNumber) {
        char c;
        Bingo bingo2 = bingo;
        Price price2 = price;
        String[] strArr = limitNumber;
        String[] strArr2 = noPlayNumber;
        String[] numbers = generateNumbers(getNumbersString(this._data.toString()));
        int i = 0;
        boolean[] mark = {false, false};
        Float[] prices = getBasePrice();
        float[] razon = {config.getFloat(Config.PAY_BALL_FIJO_LIMIT_BOTE).floatValue() / config.getFloat(Config.PAY_BALL_FIJO_BOTE).floatValue(), config.getFloat(Config.PAY_BALL_CORRIDO_LIMIT_BOTE).floatValue() / config.getFloat(Config.PAY_BALL_CORRIDO_BOTE).floatValue()};
        int length = numbers.length;
        int i2 = 0;
        while (i2 < length) {
            String number = numbers[i2];
            boolean _continue = false;
            int length2 = strArr2.length;
            while (true) {
                if (i >= length2) {
                    break;
                } else if (number.equals(strArr2[i])) {
                    _continue = true;
                    break;
                } else {
                    i++;
                }
            }
            if (!_continue) {
                Float[] aux_prices = (Float[]) prices.clone();
                int length3 = strArr.length;
                int i3 = 0;
                while (i3 < length3) {
                    String limit_number = strArr[i3];
                    if (limit_number.equals(number)) {
                        int i4 = 0;
                        while (true) {
                            String limit_number2 = limit_number;
                            if (i4 >= prices.length) {
                                break;
                            }
                            aux_prices[i4] = Float.valueOf(prices[i4].floatValue() * razon[i4]);
                            i4++;
                            limit_number = limit_number2;
                        }
                    }
                    i3++;
                    strArr = limitNumber;
                    String[] strArr3 = noPlayNumber;
                }
                if (number.compareTo(bingo.getFijo()) == 0) {
                    if (prices[0].floatValue() != 0.0f) {
                        price2.addBallFijoBote(number, aux_prices[0]);
                    }
                    c = 1;
                    if (prices[1].floatValue() != 0.0f) {
                        if (bingo2.isDoubleBingo(number)) {
                            price2.addBallCorridoDoubleBote(number, aux_prices[1]);
                        } else {
                            price2.addBallCorridoBote(number, aux_prices[1]);
                        }
                    }
                } else {
                    c = 1;
                }
                if (prices[c].floatValue() != 0.0f) {
                    if (number.compareTo(bingo.getCorrido1()) == 0 || number.compareTo(bingo.getCorrido2()) == 0) {
                        if (bingo2.isDoubleBingo(number)) {
                            price2.addBallCorridoDoubleBote(number, aux_prices[1]);
                        } else {
                            price2.addBallCorridoBote(number, aux_prices[1]);
                        }
                    }
                }
            }
            i2++;
            strArr = limitNumber;
            strArr2 = noPlayNumber;
            i = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean canAddChar() {
        if (config.getInteger(Config.MAX_BALL_PRICE_BOTE).intValue() != 0) {
            return false;
        }
        this._unlockKeys = new StringBuilder();
        return true;
    }

    /* access modifiers changed from: protected */
    public void updateKeyboard() {
        if (this._data.length() < 2) {
            KeyboardOnlyNumbers();
        } else if (this._data.length() == 2) {
            KeyboardOnlyNumbersWichoutZero();
            this._unlockKeys.append(Constants.EMPTY);
            if (this._data.toString().compareTo("00") == 0) {
                this._unlockKeys.append(Constants.SPACE);
            } else if (this._data.toString().startsWith("0") || this._data.toString().endsWith("0")) {
                this._unlockKeys.append(Constants.CIRCLE);
            }
//            if (MainActivity.getPaperListSize(this) != 0) {
//                this._unlockKeys.append(Constants.OK);
//            }
        } else if ((this._data.length() == 3 || this._data.length() == 4) && this._data.charAt(2) == 't') {
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.NUMBER9);
            sb.append(Constants.NUMBER0);
            this._unlockKeys = sb;
        } else if (getLastChar() == 'S') {
            KeyboardOnlyNumbersWichoutZero();
            if (getPosCursor() < 2) {
                this._unlockKeys.append(Constants.EMPTY);
            }
        } else if (getLastPart().contains(".") && getLastChar() != '.' && getLastPart().split("\\.")[1].length() == 1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Constants.NUMBER5);
            this._unlockKeys = sb2;
            if (getLastChar() != '0') {
                this._unlockKeys.append(Constants.NUMBER0);
            }
        } else if (getLastPart().length() != 3 || getLastChar() == ')' || getLastChar() == '.') {
            char aux = getLastChar();
            if (isANumber(aux)) {
                aux = Constants.NUMBER;
            }
            switch (aux) {
                case ')':
                    if (getPosCursor() == 2) {
                        KeyboardOnlyOK();
                        return;
                    }
                    KeyboardOnlyNumbersWichoutZero();
                    if (this._data.length() == 3) {
                        this._unlockKeys.append(Constants.EMPTY);
                    }
                    if (this._data.length() > 3 /*|| MainActivity.getPaperListSize(this) != 0*/) {
                        this._unlockKeys.append(Constants.OK);
                        return;
                    }
                    return;
                case '.':
                    KeyboardOnlyNumbers();
                    return;
                case 'N':
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(Constants.NUMBER);
                    sb3.append(Constants.CIRCLE);
                    this._unlockKeys = sb3;
                    if (decimalPart(getLastPart()) == 0) {
                        this._unlockKeys.append(Constants.POINT);
                    }
                    if (decimalPart(getLastPart()) == 1) {
                        if (getLastChar() == '0') {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(Constants.NUMBER5);
                            this._unlockKeys = sb4;
                            return;
                        }
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(Constants.NUMBER0);
                        sb5.append(Constants.NUMBER5);
                        this._unlockKeys = sb5;
                        return;
                    } else if (decimalPart(getLastPart()) == 2) {
                        if (getPosCursor() == 2) {
                            KeyboardOnlyOK();
                            return;
                        }
                        KeyboardOnlyNumbersWichoutZero();
                        this._unlockKeys.append(Constants.OK);
                        return;
                    } else if (integerPart(getLastPart()) > 0) {
                        float f = Float.valueOf(getLastPart()).floatValue();
                        this._unlockKeys = new StringBuilder();
                        if (f < ((float) config.getInteger(Config.MAX_BALL_PRICE_BOTE).intValue())) {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(Constants.POINT);
                            sb6.append(Constants.CIRCLE);
                            this._unlockKeys = sb6;
                        } else if (f == ((float) config.getInteger(Config.MAX_BALL_PRICE_BOTE).intValue())) {
                            StringBuilder sb7 = this._unlockKeys;
                            sb7.append(Constants.CIRCLE);
                            this._unlockKeys = new StringBuilder(sb7);
                        }
                        if (integerPart(getLastPart()) < config.getString(Config.MAX_BALL_PRICE_BOTE).length()) {
                            this._unlockKeys.append(Constants.NUMBER);
                        }
                        if (f >= 100.0f) {
                            return;
                        }
                        if (getLastChar() == '0' || getLastChar() == '5') {
                            this._unlockKeys.append(Constants.OK);
                            if (getPosCursor() == 1) {
                                this._unlockKeys.append(Constants.SPACE);
                                return;
                            }
                            return;
                        }
                        return;
                    } else if (getLastChar() == '0' || getLastChar() == '5') {
                        this._unlockKeys.append(Constants.OK);
                        if (getPosCursor() == 1) {
                            this._unlockKeys.append(Constants.SPACE);
                            return;
                        }
                        return;
                    } else {
                        return;
                    }
                case EACTags.TRACK3_APPLICATION /*88*/:
                    KeyboardOnlyNumbersWichoutZero();
                    return;
                case 't':
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(Constants.NUMBER0);
                    sb8.append(Constants.NUMBER9);
                    this._unlockKeys = sb8;
                    return;
                default:
                    return;
            }
        } else {
            KeyboardOnlyCircleAndPoint();
        }
    }

    public int getType() {
        return Constants.BALL_BOTE;
    }

    /* access modifiers changed from: protected */
    public Float[] getCountPrice(Price price, String number) {
        return price.getCountPriceBallBote(number);
    }

    public String getError(Price price) {
        String errorString = price.getBallPassMoreThan(generateNumbers(getNumbersString(this._data.toString())), getBasePrice());
        if (errorString != null) {
            return errorString;
        }
        return getError(price, Math.min(config.getInteger(Config.TOP_BALL_30_BOTE).intValue(), config.getInteger(Config.COPAO_MAX_VALUE_BOTE).intValue()));
    }

    /* access modifiers changed from: protected */
    public int getCopaoCount() {
        return config.getInteger(Config.COUNT_COPADO_NUMBER_BOTE).intValue();
    }

    /* access modifiers changed from: protected */
    public int getMaxBallPrice() {
        return config.getInteger(Config.MAX_BALL_PRICE_BOTE).intValue();
    }

    /* access modifiers changed from: protected */
    public int getTopBall() {
        return config.getInteger(Config.TOP_BALL_BOTE).intValue();
    }

    public void addPriceMap(Price price) {
        for (String number : generateNumbers(getNumbersString((String) null))) {
            price.addCountPriceBallBote(number, getBasePrice());
            price.addNumberBallPassOfPri(number);
        }
    }

    public void delPriceMap(Price price) {
        for (String number : generateNumbers(getNumbersString((String) null))) {
            price.delCountPriceBallBote(number, getBasePrice());
            price.delNumberBallPassOfPri(number);
        }
    }
}
