package kiu.webmailnauta.legacy;

import com.sun.mail.imap.IMAPStore;

import java.util.HashMap;
import java.util.Set;

public class PriceOpenBote {
    private int OPEN_BOTE_PLUS_BALL;
    private int OPEN_BOTE_PLUS_CENTENE;
    private int OPEN_BOTE_PLUS_PARLLET;

    public PriceOpenBote() {
        Config config = Config.getInstance();
        this.OPEN_BOTE_PLUS_BALL = config.getInteger(Config.OPEN_BOTE_PLUS_BALL).intValue();
        this.OPEN_BOTE_PLUS_PARLLET = config.getInteger(Config.OPEN_BOTE_PLUS_PARLLET).intValue();
        this.OPEN_BOTE_PLUS_CENTENE = config.getInteger(Config.OPEN_BOTE_PLUS_CENTENE).intValue();
    }

    public Float getCentenePassBote(float limpio_centene, HashMap<String, Float> centene) {
        return getPass(centene, limpio_centene, this.OPEN_BOTE_PLUS_CENTENE);
    }

    public Float getParlletPassBote(float limpio_parllet, HashMap<String, Float> parllet) {
        return getPass(parllet, limpio_parllet, this.OPEN_BOTE_PLUS_PARLLET);
    }

    public Float getBallFijoPassBote(float limpio_ball, HashMap<String, Float> ballFijoBote) {
        return getPass(ballFijoBote, limpio_ball, this.OPEN_BOTE_PLUS_BALL);
    }

    public Float getBallCorridoPassBote(float limpio_ball, HashMap<String, Float> ballCorridoBote) {
        return getPass(ballCorridoBote, limpio_ball, this.OPEN_BOTE_PLUS_BALL);
    }

    public static float canPut(float limpio, int start_open) {
        int BASE = 1100;
        if (Config.getInstance().getBoolean(Config.NOT_PASS_BOTE).booleanValue()) {
            BASE = IMAPStore.RESPONSE;
            if (limpio < ((float) IMAPStore.RESPONSE)) {
                return (float) start_open;
            }
        }
        return auxCanPut(limpio, start_open, BASE);
    }

    private static float auxCanPut(float limpio, int start_open, int BASE) {
        int c;
        int i;
        float can_put = 0.0f;
        int i2 = 1;
        while (true) {
            c = 4;
            if (limpio <= ((float) BASE)) {
                break;
            }
            if (i2 <= 2) {
                i = i2 + 1;
            } else {
                i = i2;
                i2 = 4;
            }
            can_put += (float) (start_open / i2);
            limpio -= (float) BASE;
            i2 = i;
        }
        if (limpio <= 0.0f) {
            return can_put;
        }
        if (i2 <= 2) {
            c = i2;
        }
        return can_put + ((limpio * ((float) (start_open / c))) / ((float) BASE));
    }

    private Float getPass(HashMap<String, Float> map, float limpio, int start_open) {
        Set<String> keys = map.keySet();
        float can_put = canPut(limpio, start_open);
        float pass = 0.0f;
        for (String key : keys) {
            float pay = map.get(key).floatValue();
            if (pay > can_put) {
                pass += pay - can_put;
            }
        }
        return Float.valueOf(pass);
    }
}
