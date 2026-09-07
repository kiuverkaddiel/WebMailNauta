package kiu.webmailnauta.legacy;

import org.bouncycastle.asn1.eac.EACTags;

import java.util.Arrays;
import java.util.List;

public class BallRow extends Row {
    public BallRow(ContentValues vrow) {
        super(vrow);
    }

    public BallRow() {
    }

    public String correctParentesis(String data) {
        return super.correctParentesis(data).replaceAll(String.valueOf(Constants.TO), "-AL-");
    }

    public String toString() {
        if (getNumbersString((String) null).indexOf(116) == -1) {
            return super.toString();
        }
        String[] number_part = getNumbersString((String) null).split(String.valueOf(Constants.TO));
        String aux = String.valueOf(number_part[0] + "\n AL" + Constants.SPACE + getPriceString() + "\n " + number_part[1]);
        return " " + super.correctParentesis(aux).replace(Constants.SPACE, ' ');
    }

    /* access modifiers changed from: protected */
    public boolean canAddChar() {
        if (config.getInteger(Config.MAX_BALL_PRICE).intValue() != 0) {
            return false;
        }
        this._unlockKeys = new StringBuilder();
        return true;
    }

    public void AddCharacter(char c) {
        if (!canAddChar()) {
            if (this._data.length() < 2) {
                this._data.append(c);
            } else if (this._data.length() == 2) {
                if (isANumber(c) || isEmpty(c)) {
                    StringBuilder sb = this._data;
                    sb.append(Constants.SPACE);
                    sb.append(c);
                } else {
                    this._data.append(c);
                }
            } else if (this._data.length() != 4 || this._data.charAt(2) != 't') {
                char aux = c;
                if (isANumber(aux)) {
                    aux = Constants.NUMBER;
                }
                switch (aux) {
                    case ')':
                        this._data.append(c);
                        break;
                    case '.':
                        this._data.append(Constants.POINT);
                        break;
                    case 'N':
                        String last_part = getLastPart();
                        if (getLastChar() != 't') {
                            if (decimalPart(last_part) != 1) {
                                if (getLastChar() != 'S' && getLastChar() != '.') {
                                    if (integerPart(last_part) != 5 && getLastChar() != ')' && getLastChar() != 'X') {
                                        this._data.append(c);
                                        break;
                                    } else {
                                        StringBuilder sb2 = this._data;
                                        sb2.append(Constants.SPACE);
                                        sb2.append(c);
                                        break;
                                    }
                                } else {
                                    this._data.append(c);
                                    break;
                                }
                            } else {
                                StringBuilder sb3 = this._data;
                                sb3.append(c);
                                sb3.append(Constants.CIRCLE);
                                break;
                            }
                        } else {
                            this._data.append(c);
                            if (c == '0') {
                                StringBuilder sb4 = this._data;
                                sb4.append(Constants.NUMBER9);
                                sb4.append(Constants.SPACE);
                                break;
                            }
                        }
                        break;
                    case EACTags.DISCRETIONARY_DATA /*83*/:
                        this._data.append(Constants.SPACE);
                        break;
                    case EACTags.TRACK3_APPLICATION /*88*/:
                        if (getLastChar() == ')') {
                            this._data.append(Constants.SPACE);
                        }
                        this._data.append(Constants.EMPTY);
                        break;
                    case 't':
                        this._data.append(c);
                        break;
                }
            } else {
                StringBuilder sb5 = this._data;
                sb5.append(c);
                sb5.append(Constants.SPACE);
            }
            updateKeyboard();
        }
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
        } else if (getLastPart().length() != 5 || getLastChar() == ')' || getLastChar() == '.') {
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
                        if (f < ((float) config.getInteger(Config.MAX_BALL_PRICE).intValue())) {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(Constants.POINT);
                            sb6.append(Constants.CIRCLE);
                            this._unlockKeys = sb6;
                        } else if (f == ((float) config.getInteger(Config.MAX_BALL_PRICE).intValue())) {
                            StringBuilder sb7 = this._unlockKeys;
                            sb7.append(Constants.CIRCLE);
                            this._unlockKeys = new StringBuilder(sb7);
                        }
                        if (integerPart(getLastPart()) < config.getString(Config.MAX_BALL_PRICE).length()) {
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

    public String getNumbersString(String data) {
        if (data == null) {
            data = this._data.toString();
        }
        int index = data.indexOf(String.valueOf(Constants.SPACE));
        return index == -1 ? Constants.NULL_STRING : data.substring(0, index);
    }

    /* access modifiers changed from: package-private */
    public String[] generateNumbers(String number) {
        String[] numbers = {number};
        if (!number.endsWith(String.valueOf(Constants.CIRCLE))) {
            if (number.contains(String.valueOf(Constants.TO))) {
                numbers = new String[10];
                String str = number.split(String.valueOf(Constants.TO))[1];
                char c = 65535;
                switch (str.hashCode()) {
                    case 1545:
                        if (str.equals("09")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 1815:
                        if (str.equals("90")) {
                            c = 1;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        for (Integer i = 0; i.intValue() < 10; i = Integer.valueOf(i.intValue() + 1)) {
                            int intValue = i.intValue();
                            numbers[intValue] = "0" + i.toString();
                        }
                        break;
                    case 1:
                        for (Integer i2 = 0; i2.intValue() < 10; i2 = Integer.valueOf(i2.intValue() + 1)) {
                            int intValue2 = i2.intValue();
                            numbers[intValue2] = i2.toString() + "0";
                        }
                        break;
                    default:
                        for (Integer i3 = 0; i3.intValue() < 10; i3 = Integer.valueOf(i3.intValue() + 1)) {
                            int intValue3 = i3.intValue();
                            numbers[intValue3] = i3.toString() + i3.toString();
                        }
                        break;
                }
            }
        } else {
            numbers = new String[10];
            if (number.startsWith(String.valueOf(Constants.NUMBER0))) {
                for (Integer i4 = 0; i4.intValue() < 10; i4 = Integer.valueOf(i4.intValue() + 1)) {
                    int intValue4 = i4.intValue();
                    numbers[intValue4] = i4.toString() + number.charAt(1);
                }
            } else {
                for (Integer i5 = 0; i5.intValue() < 10; i5 = Integer.valueOf(i5.intValue() + 1)) {
                    int intValue5 = i5.intValue();
                    numbers[intValue5] = number.charAt(0) + i5.toString();
                }
            }
        }
        return numbers;
    }

    public int getColor(Bingo bingo, String[] noPlayNumber) {
        String[] numbers = generateNumbers(getNumbersString(this._data.toString()));
        boolean[] mark = {false, false};
        Float[] prices = getBasePrice();
        for (String number : numbers) {
            boolean _continue = false;
            for (String noPlay : noPlayNumber) {
                if (number.equals(noPlay)) {
                    _continue = true;
                }
            }
            if (!_continue) {
                if (number.compareTo(bingo.getFijo()) == 0) {
                    if (prices[0].floatValue() != 0.0f) {
                        mark[0] = true;
                    }
                    if (prices[1].floatValue() != 0.0f) {
                        mark[1] = true;
                    }
                }
                if (prices[1].floatValue() != 0.0f && (number.compareTo(bingo.getCorrido1()) == 0 || number.compareTo(bingo.getCorrido2()) == 0)) {
                    mark[1] = true;
                }
            }
        }
        if (mark[0] && mark[1]) {
            return Constants.BINGO_BALL_ALL;
        }
        if (mark[0]) {
            return Constants.BINGO_BALL_ONLY_FIJO;
        }
        if (mark[1]) {
            return Constants.BINGO_BALL_ONLY_CORRIDO;
        }
        return -16777216;
    }

    public void getPay(Bingo bingo, Price price, String[] limitNumber, String[] noPlayNumber) {
        char c;
        Bingo bingo2 = bingo;
        Price price2 = price;
        String[] strArr = limitNumber;
        String[] strArr2 = noPlayNumber;
        String[] numbers = generateNumbers(getNumbersString(this._data.toString()));
        Float[] prices = getBasePrice();
        int i = 0;
        float[] razon = {config.getFloat(Config.PAY_BALL_FIJO_LIMIT).floatValue() / config.getFloat(Config.PAY_BALL_FIJO).floatValue(), config.getFloat(Config.PAY_BALL_CORRIDO_LIMIT).floatValue() / config.getFloat(Config.PAY_BALL_CORRIDO).floatValue()};
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
                        price2.addBallFijo(number, aux_prices[0]);
                    }
                    c = 1;
                    if (prices[1].floatValue() != 0.0f) {
                        if (bingo2.isDoubleBingo(number)) {
                            price2.addBallCorridoDouble(number, aux_prices[1]);
                        } else {
                            price2.addBallCorrido(number, aux_prices[1]);
                        }
                    }
                } else {
                    c = 1;
                }
                if (prices[c].floatValue() != 0.0f) {
                    if (number.compareTo(bingo.getCorrido1()) == 0 || number.compareTo(bingo.getCorrido2()) == 0) {
                        if (bingo2.isDoubleBingo(number)) {
                            price2.addBallCorridoDouble(number, aux_prices[1]);
                        } else {
                            price2.addBallCorrido(number, aux_prices[1]);
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

    public Float[] getBasePrice() {
        String[] prices = getPriceString().split(String.valueOf(Constants.SPACE));
        Float valueOf = Float.valueOf(0.0f);
        Float[] fprices = {valueOf, valueOf};
        for (int i = 0; i < prices.length; i++) {
            fprices[i] = convertToFloat(prices[i]);
        }
        return fprices;
    }

    public Float[] getPrice() {
        String[] prices = getPriceString().split(String.valueOf(Constants.SPACE));
        Float valueOf = Float.valueOf(0.0f);
        Float[] fprices = {valueOf, valueOf};
        for (int i = 0; i < prices.length; i++) {
            fprices[i] = convertToFloat(prices[i]);
        }
        String number = getNumbersString(this._data.toString());
        if (number.endsWith(String.valueOf(Constants.CIRCLE)) || number.indexOf(116) != -1) {
            for (int i2 = 0; i2 < 2; i2++) {
                fprices[i2] = Float.valueOf(fprices[i2].floatValue() * 10.0f);
            }
        }
        return fprices;
    }

    public void deleteBackspace() {
        if (this._data.length() == 6 && countChar(Constants.TO) == 1) {
            this._data.delete(3, this._data.length());
            updateKeyboard();
        } else if (!isDataEmpty()) {
            if (getLastChar() == 'S') {
                this._data.deleteCharAt(this._data.length() - 1);
            }
            if (getLastChar() == ')' && getLastPart().indexOf(46) != -1) {
                this._data.deleteCharAt(this._data.length() - 1);
            }
            this._data.deleteCharAt(this._data.length() - 1);
            updateKeyboard();
        }
    }

    public int getType() {
        return Constants.BALL;
    }

    /* access modifiers changed from: protected */
    public Float[] getCountPrice(Price price, String number) {
        return price.getCountPriceBall(number);
    }

    /* access modifiers changed from: protected */
    public final String getError(Price price, int topCopao) {
        int k;
        String[] arrayCopaos;
        BallRow ballRow = this;
        String aux_number = ballRow.getNumbersString((String) null);
        if (aux_number.endsWith(String.valueOf(Constants.CIRCLE)) && aux_number.length() < 3) {
            return "Número mal puesto. Borralo y ponlo de nuevo.";
        }
        String[] numbers = ballRow.generateNumbers(aux_number);
        StringBuilder error = new StringBuilder();
        String copaoString = config.getString(Config.COPADO_BALL_DAY);
        int length = getCopaoCount();
        char c = 0;
        String[] arrayCopaos2 = (String[]) Arrays.copyOfRange(copaoString.split(","), 0, length);
        List<String> copaos = Arrays.asList(arrayCopaos2);
        int maxBallPrice = getMaxBallPrice();
        int topBall = getTopBall();
        Float[] basePrice = getBasePrice();
        int length2 = numbers.length;
        int i = 0;
        while (i < length2) {
            String number = numbers[i];
            Float[] aux = ballRow.getCountPrice(price, number);
            if (aux[c] == null) {
                aux[c] = Float.valueOf(0.0f);
            }
            if (aux[1] == null) {
                aux[1] = Float.valueOf(0.0f);
            }
            if (copaos.contains(number)) {
                k = topCopao;
            } else {
                k = topBall;
            }
            int top = Math.min(maxBallPrice, k);
            String aux_number2 = aux_number;
            String[] numbers2 = numbers;
            String copaoString2 = copaoString;
            int length3 = length;
            if (aux[0].floatValue() + basePrice[0].floatValue() > ((float) top)) {
                error.append("El número ");
                error.append(number);
                error.append(" se pasa, ");
                if (aux[0].floatValue() != 0.0f) {
                    error.append("ya tiene ");
                    arrayCopaos = arrayCopaos2;
                    error.append(aux[0]);
                } else {
                    arrayCopaos = arrayCopaos2;
                    error.append("va a tener ");
                    error.append(basePrice[0]);
                }
                error.append(" pesos de fijo.");
            } else {
                arrayCopaos = arrayCopaos2;
            }
            if (aux[1].floatValue() + basePrice[1].floatValue() > ((float) top)) {
                if (error.length() != 0) {
                    error.append("\n");
                }
                error.append("El número ");
                error.append(number);
                error.append(" se pasa, ");
                if (aux[1].floatValue() != 0.0f) {
                    error.append("ya tiene ");
                    error.append(aux[1]);
                } else {
                    error.append("va a tener ");
                    error.append(basePrice[1]);
                }
                error.append(" pesos de corrido.");
            }
            i++;
            ballRow = this;
            aux_number = aux_number2;
            arrayCopaos2 = arrayCopaos;
            numbers = numbers2;
            copaoString = copaoString2;
            length = length3;
            c = 0;
        }
        String[] strArr = numbers;
        String str = copaoString;
        int i2 = length;
        String[] strArr2 = arrayCopaos2;
        if (error.length() == 0) {
            return null;
        }
        return error.toString();
    }

    /* access modifiers changed from: protected */
    public int getCopaoCount() {
        return config.getInteger(Config.COUNT_COPADO_NUMBER_LIST).intValue();
    }

    /* access modifiers changed from: protected */
    public int getMaxBallPrice() {
        return config.getInteger(Config.MAX_BALL_PRICE).intValue();
    }

    /* access modifiers changed from: protected */
    public int getTopBall() {
        return config.getInteger(Config.TOP_BALL).intValue();
    }

    public String getError(Price price) {
        String errorString = price.getBallPassMoreThan(generateNumbers(getNumbersString(this._data.toString())), getBasePrice());
        if (errorString != null) {
            return errorString;
        }
        float maxCopao = config.getFloat(Config.COPAO_PERCENT).floatValue();
        float camPutCopao = (price.getTotalLimpio() * maxCopao) / config.getFloat(Config.BASE_BALL_PRICE).floatValue();
        if (camPutCopao > maxCopao) {
            camPutCopao = maxCopao;
        }
        return getError(price, (int) Math.min(config.getFloat(Config.TOP_BALL_30).floatValue(), camPutCopao));
    }

    public void addPriceMap(Price price) {
        for (String number : generateNumbers(getNumbersString((String) null))) {
            price.addCountPriceBall(number, getBasePrice());
            price.addNumberBallPassOfPri(number);
        }
    }

    public void delPriceMap(Price price) {
        for (String number : generateNumbers(getNumbersString((String) null))) {
            price.delCountPriceBall(number, getBasePrice());
            price.delNumberBallPassOfPri(number);
        }
    }
}
