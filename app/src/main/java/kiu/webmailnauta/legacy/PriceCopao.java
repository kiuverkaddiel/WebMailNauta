package kiu.webmailnauta.legacy;

import java.util.HashMap;

public class PriceCopao {
    private static final int BASE = 1100;
    private static int COPAO_MAX_VALUE_BOTE = 300;
    private static final int DEFAULT_COPAO_MAX_VALUE_BOTE = 300;
    private final float COPAO_PERCENT;
    private final int COPAO_START_BOTE;
    private Bingo bingo;
    private String[] copaosNumbersBote;
    private String[] copaosNumbersList;
    private float limpio;
    private float limpioBote;

    PriceCopao(float limpio2, float limpioBote2, Bingo bingo2, String[] copaosNumbersList2, String[] copaosNumbersBote2) {
        this.limpio = limpio2;
        this.limpioBote = limpioBote2;
        this.bingo = bingo2;
        this.copaosNumbersList = copaosNumbersList2;
        this.copaosNumbersBote = copaosNumbersBote2;
        Config config = Config.getInstance();
        String sCopaoPercent = config.getString(Config.COPAO_PERCENT);
        this.COPAO_PERCENT = sCopaoPercent == null ? 0.0f : Float.valueOf(sCopaoPercent).floatValue();
        String sCopaoMaxValueBote = config.getString(Config.COPAO_MAX_VALUE_BOTE);
        int intValue = sCopaoMaxValueBote == null ? 0 : Integer.valueOf(sCopaoMaxValueBote).intValue();
        this.COPAO_START_BOTE = intValue;
        COPAO_MAX_VALUE_BOTE = intValue == 0 ? 300 : intValue;
    }

    /* access modifiers changed from: package-private */
    public Float getBallFijoPassBote(HashMap<String, Float> ballFijoBote) {
        float f;
        Bingo bingo2 = this.bingo;
        if (bingo2 == null) {
            f = 0.0f;
        } else {
            f = getPassBote(new String[]{bingo2.getFijo()}, this.COPAO_START_BOTE, ballFijoBote).floatValue();
        }
        return Float.valueOf(f);
    }

    /* access modifiers changed from: package-private */
    public Float getBallCorridoPassBote(HashMap<String, Float> ballCorridoBote) {
        float f;
        Bingo bingo2 = this.bingo;
        if (bingo2 == null) {
            f = 0.0f;
        } else {
            f = getPassBote(new String[]{bingo2.getCorrido1(), this.bingo.getCorrido2()}, this.COPAO_START_BOTE, ballCorridoBote).floatValue();
        }
        return Float.valueOf(f);
    }

    /* access modifiers changed from: package-private */
    public Float getBallFijoPass(HashMap<String, Float> ballFijo) {
        float f;
        Bingo bingo2 = this.bingo;
        if (bingo2 == null) {
            f = 0.0f;
        } else {
            f = getPassList(new String[]{bingo2.getFijo()}, this.COPAO_PERCENT, ballFijo).floatValue();
        }
        return Float.valueOf(f);
    }

    /* access modifiers changed from: package-private */
    public Float getBallCorridoPass(HashMap<String, Float> ballCorrido) {
        float f;
        Bingo bingo2 = this.bingo;
        if (bingo2 == null) {
            f = 0.0f;
        } else {
            f = getPassList(new String[]{bingo2.getCorrido1(), this.bingo.getCorrido2()}, this.COPAO_PERCENT, ballCorrido).floatValue();
        }
        return Float.valueOf(f);
    }

    private Float getPassBote(String[] bingoNumbers, int copaoStart, HashMap<String, Float> map) {
        Float aux = Float.valueOf(0.0f);
        for (String copaoNumber : this.copaosNumbersBote) {
            for (String bingoNumber : bingoNumbers) {
                if (copaoNumber.equals(bingoNumber)) {
                    aux = Float.valueOf(aux.floatValue() + getPassBote(copaoNumber, copaoStart, map).floatValue());
                }
            }
        }
        return aux;
    }

    private Float getPassList(String[] bingoNumbers, float copaoPercent, HashMap<String, Float> map) {
        Float aux = Float.valueOf(0.0f);
        for (String copao_number : this.copaosNumbersList) {
            for (String bingo_number : bingoNumbers) {
                if (copao_number.equals(bingo_number)) {
                    aux = Float.valueOf(aux.floatValue() + getPassList(copao_number, copaoPercent, map).floatValue());
                }
            }
        }
        return aux;
    }

    public static float getCanPutBote(float limpio2, int startOpen, int maxCopao) {
        int c;
        int i;
        float canPut = 0.0f;
        int i2 = 1;
        while (true) {
            c = 4;
            if (limpio2 <= 1100.0f) {
                break;
            }
            if (i2 <= 2) {
                i = i2 + 1;
            } else {
                i = i2;
                i2 = 4;
            }
            canPut += (float) (startOpen / i2);
            limpio2 -= 1100.0f;
            i2 = i;
        }
        if (limpio2 > 0.0f) {
            if (i2 <= 2) {
                c = i2;
            }
            canPut += (limpio2 * ((float) (startOpen / c))) / 1100.0f;
        }
        return Math.min(Math.max(canPut, (float) startOpen), (float) maxCopao);
    }

    private Float getPassBote(String copaoNumber, int startCopao, HashMap<String, Float> map) {
        return getPassAux(copaoNumber, map, getCanPutBote(this.limpioBote, startCopao, COPAO_MAX_VALUE_BOTE));
    }

    public static Float getCanPutList(float limpio2, float copaoPercent) {
        return Price.round(Float.valueOf((limpio2 * copaoPercent) / 100.0f));
    }

    private Float getPassList(String copaoNumber, float copaoPercent, HashMap<String, Float> map) {
        return getPassAux(copaoNumber, map, getCanPutList(this.limpio, copaoPercent).floatValue());
    }

    private Float getPassAux(String copaoNumber, HashMap<String, Float> map, float canPut) {
        if (map.containsKey(copaoNumber)) {
            float pay = map.get(copaoNumber).floatValue();
            if (pay > canPut) {
                return Float.valueOf(pay - canPut);
            }
        }
        return Float.valueOf(0.0f);
    }
}
