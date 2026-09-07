package kiu.webmailnauta.legacy;

import android.util.Log;

import java.util.List;
import java.util.Properties;

import kiu.webmailnauta.legacy.db.DataBaseHelper;
import kiu.webmailnauta.legacy.utils.MyCipher;

public class PaperList {
    private final String COPAO_BALL;
    private final String LIMIT_BALL;
    private final String LIMIT_CENTENE;
    private final String LIMIT_PARLLET;
    private final String LIST_NUMBER;
    private final String NO_PLAY_BALL;
    private final String NO_PLAY_CENTENE;
    private final String NO_PLAY_PARLLET;
    private final String PLAY_NAME;
    private final String VERSION;
    private Price _price;
    private FactoryRowList _row_list;
    private String code;
    private Config config;
    private String copaoBall;
    private Long id;
    private boolean isNotComputePay;
    private boolean isNotComputePayBote;
    private String limitBall;
    private String limitCentene;
    private String limitParllet;
    private String list_number;
    private String noPlayBall;
    private String noPlayCentene;
    private String noPlayParllet;
    private String play_name;
    private String result;
    private boolean saved;
    private String version;

    public PaperList() {
        result = null;
        config = Config.getInstance();
        _price = new Price();
        isNotComputePay = true;
        isNotComputePayBote = true;
        saved = false;
        version = Constants.LIST_VERSION;
        VERSION = "v";
        PLAY_NAME = "pn";
        LIST_NUMBER = "ln";
        LIMIT_BALL = "lb";
        COPAO_BALL = "cb";
        LIMIT_PARLLET = "lp";
        LIMIT_CENTENE = "lc";
        NO_PLAY_BALL = "npb";
        NO_PLAY_PARLLET = "npp";
        NO_PLAY_CENTENE = "npc";
        _row_list = new FactoryRowList();
        fillLimitNumbers();
        list_number = this.config.getString("list_number");
        play_name = this.config.getString(Config.PLAY_NAME);
        code = this.config.getString(Config.LIST_CODE);
        id = 0L;
    }

    public PaperList(FactoryRowList rowList) {
        result = null;
        config = Config.getInstance();
        _price = new Price();
        isNotComputePay = true;
        isNotComputePayBote = true;
        saved = false;
        version = Constants.LIST_VERSION;
        VERSION = "v";
        PLAY_NAME = "pn";
        LIST_NUMBER = "ln";
        LIMIT_BALL = "lb";
        COPAO_BALL = "cb";
        LIMIT_PARLLET = "lp";
        LIMIT_CENTENE = "lc";
        NO_PLAY_BALL = "npb";
        NO_PLAY_PARLLET = "npp";
        NO_PLAY_CENTENE = "npc";
        _row_list = rowList;
        fillLimitNumbers();
        list_number = this.config.getString("list_number");
        play_name = this.config.getString(Config.PLAY_NAME);
        id = System.currentTimeMillis();
    }

    private void fillLimitNumbers() {
//        if (MainActivity.checkLimitNumbers()) {
//            limitBall = MainActivity.fitLimitNumbers1(MyUtils.getLimitNumberBall());
//            copaoBall = MainActivity.fitCopaoNumbers1(MyUtils.getCopaoNumberBall());
//            limitParllet = MyUtils.getLimitNumberParllet();
//            limitCentene = MyUtils.getLimitNumberCentene();
//            noPlayBall = MainActivity.fitNotPlayNumbers1(this.config.getString(Config.LIMIT_BALL_NOT_PLAY));
//            noPlayParllet = MainActivity.fitNotPlayNumbers1(this.config.getString(Config.LIMIT_PARLLET_NOT_PLAY));
//            noPlayCentene = MainActivity.fitNotPlayNumbers1(this.config.getString(Config.LIMIT_CENTENE_NOT_PLAY));
//        }
    }

    public String equalsPrice() {
        return _price.equalPrice();
    }

    public boolean isSaved() {
        return saved;
    }

    public void savedFinish() {
        this.saved = true;
    }

    public void allowEncrypt() {
        this.result = null;
    }

    private Properties getValuesFromList(String part) {
        String[] aux = part.split(String.valueOf(Constants.VALUE_SEPARATOR));
        Properties properties = new Properties();
        for (int i = 0; i < aux.length; i += 2) {
            properties.setProperty(aux[i], aux[i + 1]);
        }
        return properties;
    }

    public PaperList(String rowListString) {
        int i = 0;
        result = null;
        config = Config.getInstance();
        _price = new Price();
        isNotComputePay = true;
        isNotComputePayBote = true;
        saved = false;
        version = Constants.LIST_VERSION;
        VERSION = "v";
        PLAY_NAME = "pn";
        LIST_NUMBER = "ln";
        LIMIT_BALL = "lb";
        COPAO_BALL = "cb";
        LIMIT_PARLLET = "lp";
        LIMIT_CENTENE = "lc";
        NO_PLAY_BALL = "npb";
        NO_PLAY_PARLLET = "npp";
        NO_PLAY_CENTENE = "npc";
        _row_list = new FactoryRowList();
        String[] parts = rowListString.split(String.valueOf(ConstantsParserRowList.ROW_SEPARATOR));
        id = Long.valueOf(parts[i++]);

        if (parts[1].equals("false") || parts[1].equals("true")) {
            if (parts[1].startsWith("v")) {
                version = parts[1];
                i++;
            }
            list_number = parts[i++];
            play_name = parts[i++];
            limitBall = parts[i++];
            copaoBall = "33,37,11,25,54,13";
            limitParllet = parts[i++];
            limitCentene = parts[i++];
            noPlayBall = parts[i++];
            noPlayParllet = parts[i++];
            noPlayCentene = parts[i++];
        } else {
            Properties properties = getValuesFromList(parts[i++]);
            version = properties.getProperty("v");
            play_name = properties.getProperty("pn");
            list_number = properties.getProperty("ln");
            code = config.getString(Config.LIST_CODE);
            limitBall = properties.getProperty("lb", "no");
            copaoBall = properties.getProperty("cb", "no");
            limitParllet = properties.getProperty("lp", "no");
            limitCentene = properties.getProperty("lc", "no");
            noPlayBall = properties.getProperty("npb", "no");
            noPlayParllet = properties.getProperty("npp", "no");
            noPlayCentene = properties.getProperty("npc", "no");
            auxLimit();
        }

        while (i < parts.length) {
            try {
                addRow(FactoryRow.getRow(parts[i]), null);
            } catch (Exception e) {
                Log.d("Error in list: " + play_name + list_number, "row: " + e.getMessage());
            }
            i++;
        }
    }

    private void auxLimit() {
        if (this.limitBall.equals("null")) {
            this.limitBall = "no";
        }
        if (this.limitParllet.equals("null")) {
            this.limitParllet = "no";
        }
        if (this.limitCentene.equals("null")) {
            this.limitCentene = "no";
        }
        if (this.noPlayBall.equals("null")) {
            this.noPlayBall = "no";
        }
        if (this.noPlayParllet.equals("null")) {
            this.noPlayParllet = "no";
        }
        if (this.noPlayCentene.equals("null")) {
            this.noPlayCentene = "no";
        }
        if (this.copaoBall.equals("null")) {
            this.copaoBall = "03,47";
        }
    }

    public String getVersion() {
        return version;
    }

    public boolean isPassNumberBall() {
        return this._price.passNumberBall();
    }

    public boolean isPassNumberParlle() {
        return this._price.passNumberParllet();
    }

    public boolean isPassNumberBallBote() {
        return this._price.passNumberBallBote();
    }

    public boolean isPassNumberParlletBote() {
        return this._price.passNumberParlletBote();
    }

    public String getLimitNumberBall() {
        return this.limitBall;
    }

    public String getCopaoNumberBall() {
        return this.copaoBall;
    }

    public String getLimitNumberParllet() {
        return this.limitParllet;
    }

    public String getLimitNumberCentene() {
        return this.limitCentene;
    }

    public String getNoPlayNumberBall() {
        return this.noPlayBall;
    }

    public String getNoPlayNumberParllet() {
        return this.noPlayParllet;
    }

    public String getNoPlayNumberCentene() {
        return this.noPlayCentene;
    }

    public String getCode() {
        return this.code;
    }

    public String getListNumber() {
        return this.list_number;
    }

    public String getPlayName() {
        return this.play_name;
    }

    public Price getPrice() {
        return this._price;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id2) {
        this.id = Long.valueOf(id2);
    }

    public FactoryRowList getRowList() {
        return this._row_list;
    }

    public Float Limpio() {
        return Float.valueOf(this._price.getTotalLimpio());
    }

    public Float LimpioBall() {
        return Float.valueOf(this._price.getLimpioBall());
    }

    public Float LimpioParllet() {
        return Float.valueOf(this._price.getLimpioParllet());
    }

    public Float LimpioCentene() {
        return Float.valueOf(this._price.getLimpioCentene());
    }

    public Float LimpioParlletCentene() {
        return Float.valueOf(this._price.getLimpioParlletCentene());
    }

    public Float LimpioBote() {
        return Float.valueOf(this._price.getTotalLimpioBote());
    }

    public Float LimpioBallBote() {
        return Float.valueOf(this._price.getLimpioBallBote());
    }

    public Float LimpioParlletCenteneBote() {
        return Float.valueOf(this._price.getLimpioParlletCenteneBote());
    }

    public Float LimpioParlletBote() {
        return Float.valueOf(this._price.getLimpioParlletBote());
    }

    public Float LimpioCenteneBote() {
        return Float.valueOf(this._price.getLimpioCenteneBote());
    }

    public Float Bruto() {
        return Float.valueOf(this._price.getTotalBruto());
    }

    public Float BrutoBall() {
        return Float.valueOf(this._price.getBrutoBall());
    }

    public Float BrutoParllet() {
        return Float.valueOf(this._price.getBrutoParllet());
    }

    public Float BrutoCentene() {
        return Float.valueOf(this._price.getBrutoCentene());
    }

    public Float BrutoParlletCentene() {
        return Float.valueOf(this._price.getBrutoParlletCentene());
    }

    public Float BrutoBote() {
        return Float.valueOf(this._price.getTotalBrutoBote());
    }

    public Float BrutoBallBote() {
        return Float.valueOf(this._price.getBrutoBallBote());
    }

    public Float BrutoParlletBote() {
        return Float.valueOf(this._price.getBrutoParlletBote());
    }

    public Float BrutoCenteneBote() {
        return Float.valueOf(this._price.getBrutoCenteneBote());
    }

    public Float BrutoParlletCenteneBote() {
        return Float.valueOf(this._price.getBrutoParlletCenteneBote());
    }

    public void removeRow(int selectedChoice, long pos, DataBaseHelper db) {
        List<Row> list = this._row_list.getRowList(selectedChoice);
        if (pos >= 0 && pos < ((long) list.size())) {
            Row row = list.get((int) pos);
            this._price.delBruto(row);
            row.delPriceMap(this._price);
            if (db != null) {
                db.deleteRow(row);
            }
            list.remove(row);
        }
    }

    public Row removeRow(int type, long id2) {
        List<Row> list = this._row_list.getRowList(type);
        if (list == null) {
            return null;
        }
        for (int pos = 0; pos < list.size(); pos++) {
            Row row = list.get(pos);
            if (row.getId() == id2) {
                list.remove(row);
                this._price.delBruto(row);
                row.delPriceMap(this._price);
                return row;
            }
        }
        return null;
    }

    public String getAllPassWith(int count_ball, int count_parllet) {
        String parllet = this._price.getParlletPassWith(count_parllet);
        String ball_fijo = this._price.getBallFijoPassWith(count_ball);
        String ball_corrido = this._price.getBallCorridoPassWith(count_ball);
        if (!parllet.isEmpty() || !ball_fijo.isEmpty() || !ball_corrido.isEmpty()) {
            return parllet + ball_fijo + ball_corrido;
        }
        String aux = Constants.NULL_STRING;
        if (count_parllet != Integer.MAX_VALUE) {
            aux = "No hay ningun parle pasado con: " + count_parllet + " pesos\n";
        }
        if (count_ball == Integer.MAX_VALUE) {
            return aux;
        }
        return aux + "No hay ninguna bola pasada con: " + count_ball + " pesos";
    }

    public Row addRow(Row row, DataBaseHelper db) {
        List<Row> list = this._row_list.getRowList(row);
        this._price.addBruto(row);
        row.addPriceMap(this._price);
        if (db != null) {
            row.save(db);
            list.add(0, row);
        } else {
            list.add(row);
        }
        return FactoryRow.getRow(row);
    }

    public String toString() {
        return this.id.toString() + ConstantsParserRowList.ROW_SEPARATOR + "v" + Constants.VALUE_SEPARATOR + version + Constants.VALUE_SEPARATOR + "ln" + Constants.VALUE_SEPARATOR + this.list_number + Constants.VALUE_SEPARATOR + "pn" + Constants.VALUE_SEPARATOR + this.play_name + Constants.VALUE_SEPARATOR + "lb" + Constants.VALUE_SEPARATOR + this.limitBall + Constants.VALUE_SEPARATOR + "cb" + Constants.VALUE_SEPARATOR + this.copaoBall + Constants.VALUE_SEPARATOR + "lp" + Constants.VALUE_SEPARATOR + this.limitParllet + Constants.VALUE_SEPARATOR + "lc" + Constants.VALUE_SEPARATOR + this.limitCentene + Constants.VALUE_SEPARATOR + "npb" + Constants.VALUE_SEPARATOR + this.noPlayBall + Constants.VALUE_SEPARATOR + "npp" + Constants.VALUE_SEPARATOR + this.noPlayParllet + Constants.VALUE_SEPARATOR + "npc" + Constants.VALUE_SEPARATOR + this.noPlayCentene + ConstantsParserRowList.ROW_SEPARATOR + this._row_list.toString();
    }

    public String getHeaders() {
        return this.id.toString() + ConstantsParserRowList.ROW_SEPARATOR + "v" + Constants.VALUE_SEPARATOR + version + Constants.VALUE_SEPARATOR + "ln" + Constants.VALUE_SEPARATOR + this.list_number + Constants.VALUE_SEPARATOR + "pn" + Constants.VALUE_SEPARATOR + this.play_name + Constants.VALUE_SEPARATOR + "lb" + Constants.VALUE_SEPARATOR + this.limitBall + Constants.VALUE_SEPARATOR + "cb" + Constants.VALUE_SEPARATOR + this.copaoBall + Constants.VALUE_SEPARATOR + "lp" + Constants.VALUE_SEPARATOR + this.limitParllet + Constants.VALUE_SEPARATOR + "lc" + Constants.VALUE_SEPARATOR + this.limitCentene + Constants.VALUE_SEPARATOR + "npb" + Constants.VALUE_SEPARATOR + this.noPlayBall + Constants.VALUE_SEPARATOR + "npp" + Constants.VALUE_SEPARATOR + this.noPlayParllet + Constants.VALUE_SEPARATOR + "npc" + Constants.VALUE_SEPARATOR + this.noPlayCentene;
    }

    public String encrypt() {
        if (this.result == null) {
            this.result = MyCipher.Encrypt(toString());
        }
        return this.result;
    }

    public boolean save(DataBaseHelper db) {
        try {
            db.insertList(this);
            config.setField(Config.CHECK_LIST, db.getPaperListMD5());
            config.updateField(db, Config.CHECK_LIST);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean del(DataBaseHelper db) {
        try {
            db.deletePaperList(getId().longValue());
            this.config.setField(Config.CHECK_LIST, db.getPaperListMD5());
            this.config.updateField(db, Config.CHECK_LIST);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void computePayList(Bingo bingo, int listType, String[] limitNumber, String[] noPlayNumber) {
        for (Row row : this._row_list.getRowList(listType)) {
            row.getPay(bingo, this._price, limitNumber, noPlayNumber);
        }
    }

    private void ComputePayList(Bingo bingo, int BALL, int PARLLET, int CENTENE) {
//        computePayList(bingo, BALL, MainActivity.fitLimitNumberList(this.limitBall), MainActivity.fitNotPlayNumberList(this.noPlayBall));
//        computePayList(bingo, PARLLET, MainActivity.fitArray(this.limitParllet, IMAPStore.RESPONSE), MainActivity.fitNotPlayNumberList(this.noPlayParllet));
//        computePayList(bingo, CENTENE, MainActivity.fitArray(this.limitCentene, IMAPStore.RESPONSE), MainActivity.fitNotPlayNumberList(this.noPlayCentene));
    }

    private void ComputePayBote(Bingo bingo, int BALL, int PARLLET, int CENTENE) {
//        computePayList(bingo, BALL, MainActivity.fitLimitNumberBote(this.limitBall), MainActivity.fitNotPlayNumberBote(this.noPlayBall));
//        computePayList(bingo, PARLLET, MainActivity.fitLimitNumberBote(this.limitParllet), MainActivity.fitNotPlayNumberBote(this.noPlayParllet));
//        computePayList(bingo, CENTENE, MainActivity.fitLimitNumberBote(this.limitCentene), MainActivity.fitNotPlayNumberBote(this.noPlayCentene));
    }

    public void computePay(Bingo bingo) {
        if (this.isNotComputePay) {
            ComputePayList(bingo, Constants.BALL, Constants.PARLLET, Constants.CENTENE);
            this.isNotComputePay = false;
        }
    }

    public void computePayBote(Bingo bingo) {
        if (this.isNotComputePayBote) {
            ComputePayBote(bingo, Constants.BALL_BOTE, Constants.PARLLET_BOTE, Constants.CENTENE_BOTE);
            this.isNotComputePayBote = false;
        }
    }

    private float getGive(float limpio, float pierde) {
        Float auxGiveClean = this.config.getFloat(Config.GIVE_CLEAN);
        if (limpio > (auxGiveClean == null ? 0.0f : auxGiveClean.floatValue())) {
            return (this.config.getFloat(Config.GIVE_PERCENT).floatValue() * pierde) / 100.0f;
        }
        return 0.0f;
    }

    public String[] getStringArrayPay(Bingo bingo) {
        /*
        boolean has_bingo = bingo != null;
        PriceCopao _price_copao = new PriceCopao((this.config.getBoolean(Config.SEPARATE_LIMPIO).booleanValue() ? LimpioBall() : Limpio()).floatValue(), 0.0f, bingo, MainActivity.fitCopaoNumberList(this.copaoBall), (String[]) null);
        Float passBallCopaoFijo = _price_copao.getBallFijoPass(this._price.ballFijo);
        Float passBallCopaoCorrido = Float.valueOf(_price_copao.getBallCorridoPass(this._price.ballCorrido).floatValue() + _price_copao.getBallCorridoPass(this._price.ballCorridoDouble).floatValue());
        Float bruto = Bruto();
        float lmp = Limpio().floatValue();
        float pass = this._price.Pass().floatValue() + passBallCopaoFijo.floatValue() + passBallCopaoCorrido.floatValue();
        Float limpio = Price.round(Float.valueOf(lmp - pass));
        boolean payAll = false;
        if (this.config.getBoolean(Config.PAY_FULL_PRICE).booleanValue() && pass != 0.0f) {
            lmp = bruto.floatValue();
            limpio = bruto;
            payAll = true;
        }
        String str = null;
        if (has_bingo) {
            Float prize = Float.valueOf(payAll ? this._price.payAll().floatValue() : this._price.pay().floatValue() - ((passBallCopaoFijo.floatValue() * ((float) this.config.getInteger(Config.PAY_BALL_FIJO).intValue())) + (passBallCopaoCorrido.floatValue() * ((float) this.config.getInteger(Config.PAY_BALL_CORRIDO).intValue()))));
            Float loss = Price.round(Float.valueOf(prize.floatValue() - limpio.floatValue()));
            String win = null;
            String noWin = null;
            if (loss.floatValue() < 0.0f) {
                Float loss2 = Float.valueOf(Math.abs(loss.floatValue()));
                if (BrutoBote().floatValue() == 0.0f) {
                    PriceCopao priceCopao = _price_copao;
                    loss2 = Price.round(Float.valueOf(loss2.floatValue() - getGive(limpio.floatValue(), loss2.floatValue())));
                }
                noWin = Price.floatToString(loss2);
            } else {
                win = Price.floatToString(loss);
            }
            String[] strArr = new String[5];
            strArr[0] = Price.floatToString(bruto);
            strArr[1] = Price.floatToString(Float.valueOf(lmp));
            if (prize.floatValue() != 0.0f) {
                str = Price.floatToString(prize);
            }
            strArr[2] = str;
            strArr[3] = win;
            strArr[4] = noWin;
            return strArr;
        }
        return new String[]{Price.floatToString(bruto), Price.floatToString(limpio), null, null, null};

         */
        return null;
    }

    public String[] getStringArrayPayBote(Bingo bingo) {
        /*
        boolean has_bingo = bingo != null;
        float limpio_aux = (this.config.getBoolean(Config.SEPARATE_LIMPIO).booleanValue() ? LimpioBall() : Limpio()).floatValue();
        PriceCopao priceCopao = new PriceCopao(limpio_aux, (this.config.getBoolean(Config.SEPARATE_LIMPIO_BOTE).booleanValue() ? LimpioBallBote() : LimpioBote()).floatValue(), bingo, MainActivity.fitCopaoNumberList(this.copaoBall), MainActivity.fitCopaoNumberBote(this.copaoBall));
        Float passBallCopaoFijoBote = priceCopao.getBallFijoPassBote(this._price.ballFijoBote);
        Float passBallCopaoCorridoBote = Float.valueOf(priceCopao.getBallCorridoPassBote(this._price.ballCorridoBote).floatValue() + priceCopao.getBallCorridoPass(this._price.ballCorridoDoubleBote).floatValue());
        Float passBallCopaoFijo = priceCopao.getBallFijoPassBote(this._price.ballFijo);
        Float passBallCopaoCorrido = Float.valueOf(priceCopao.getBallCorridoPassBote(this._price.ballCorrido).floatValue() + priceCopao.getBallCorridoPass(this._price.ballCorridoDouble).floatValue());
        Float brutoBote = BrutoBote();
        float lmp = LimpioBote().floatValue();
        float passBote = this._price.PassBote().floatValue() + passBallCopaoFijoBote.floatValue() + passBallCopaoCorridoBote.floatValue();
        Float limpio = Price.round(Float.valueOf(lmp - passBote));
        boolean payAllBote = false;
        if (this.config.getBoolean(Config.PAY_FULL_PRICE_BOTE).booleanValue() && passBote != 0.0f) {
            lmp = brutoBote.floatValue();
            limpio = brutoBote;
            payAllBote = true;
        }
        Float bruto = Bruto();
        float lmp1 = Limpio().floatValue();
        float pass = this._price.Pass().floatValue();
        Float limpio1 = Price.round(Float.valueOf(lmp1 - pass));
        boolean payAll = false;
        PriceCopao priceCopao2 = priceCopao;
        float f = limpio_aux;
        if (this.config.getBoolean(Config.PAY_FULL_PRICE).booleanValue() && pass != 0.0f) {
            lmp1 = bruto.floatValue();
            limpio1 = bruto;
            payAll = true;
        }
        Float limpioTotal = Price.round(Float.valueOf(lmp1 + lmp));
        Float brutoTotal = Price.round(Float.valueOf(bruto.floatValue() + brutoBote.floatValue()));
        Float f2 = bruto;
        String str = null;
        if (has_bingo) {
            float f3 = pass;
            Float f4 = passBallCopaoFijo;
            Float f5 = passBallCopaoCorrido;
            float pay = this._price.pay().floatValue() - ((passBallCopaoFijo.floatValue() * ((float) this.config.getInteger(Config.PAY_BALL_FIJO).intValue())) + (passBallCopaoCorrido.floatValue() * ((float) this.config.getInteger(Config.PAY_BALL_CORRIDO).intValue())));
            Float f6 = passBallCopaoFijoBote;
            float pay_bote = this._price.payBote().floatValue() - ((passBallCopaoFijoBote.floatValue() * ((float) this.config.getInteger(Config.PAY_BALL_FIJO_BOTE).intValue())) + (passBallCopaoCorridoBote.floatValue() * ((float) this.config.getInteger(Config.PAY_BALL_CORRIDO_BOTE).intValue())));
            Float premio = Float.valueOf(payAllBote ? this._price.payAllBote().floatValue() : pay_bote);
            Float pierde = Price.round(Float.valueOf(premio.floatValue() - limpio.floatValue()));
            Float premio1 = Float.valueOf(payAll ? this._price.payAll().floatValue() : pay);
            Float pierdeTotal = Price.round(Float.valueOf(pierde.floatValue() + Price.round(Float.valueOf(premio1.floatValue() - limpio1.floatValue())).floatValue()));
            Float premioTotal = Price.round(Float.valueOf(premio.floatValue() + premio1.floatValue()));
            String ganaTotal = null;
            String noWinTotal = null;
            if (pierdeTotal.floatValue() < 0.0f) {
                Float pierdeTotal2 = Float.valueOf(Math.abs(pierdeTotal.floatValue()));
                float f7 = pay_bote;
                Float f8 = passBallCopaoCorridoBote;
                noWinTotal = Price.floatToString(Price.round(Float.valueOf(pierdeTotal2.floatValue() - getGive(limpioTotal.floatValue(), pierdeTotal2.floatValue()))));
            } else {
                Float f9 = passBallCopaoCorridoBote;
                ganaTotal = Price.floatToString(pierdeTotal);
            }
            String gana = null;
            String no_gana = null;
            if (pierde.floatValue() < 0.0f) {
                no_gana = Price.floatToString(Float.valueOf(Math.abs(pierde.floatValue())));
            } else {
                gana = Price.floatToString(pierde);
            }
            String[] strArr = new String[10];
            strArr[0] = Price.floatToString(brutoBote);
            strArr[1] = Price.floatToString(Float.valueOf(lmp));
            strArr[2] = premio.floatValue() == 0.0f ? null : Price.floatToString(premio);
            strArr[3] = gana;
            strArr[4] = no_gana;
            strArr[5] = Price.floatToString(brutoTotal);
            strArr[6] = Price.floatToString(limpioTotal);
            if (premioTotal.floatValue() != 0.0f) {
                str = Price.floatToString(premioTotal);
            }
            Float f10 = premio;
            strArr[7] = str;
            strArr[8] = ganaTotal;
            strArr[9] = noWinTotal;
            return strArr;
        }
        return new String[]{Price.floatToString(brutoBote), Price.floatToString(Float.valueOf(lmp)), null, null, null, Price.floatToString(brutoTotal), Price.floatToString(limpioTotal)};

         */
        return null;
    }

    public String getStringPass(Bingo bingo) {
        /*
        if (this.config.getBoolean(Config.PAY_FULL_PRICE).booleanValue()) {
            return null;
        }
        PriceCopao _price_copao = new PriceCopao((this.config.getBoolean(Config.SEPARATE_LIMPIO).booleanValue() ? LimpioBall() : Limpio()).floatValue(), 0.0f, bingo, MainActivity.fitCopaoNumberList(this.copaoBall), (String[]) null);
        Float passBallCopaoFijo = _price_copao.getBallFijoPass(this._price.ballFijo);
        Float passBallCopaoCorrido = Float.valueOf(_price_copao.getBallCorridoPass(this._price.ballCorrido).floatValue() + _price_copao.getBallCorridoPass(this._price.ballCorridoDouble).floatValue());
        Float passBallFijo = this._price.getBallFijoPass();
        Float passBallCorrido = Float.valueOf(this._price.getBallCorridoPass().floatValue() + this._price.getBallCorridoDoublePass().floatValue());
        Float passParllet = this._price.getParlletPass();
        Float passCentene = this._price.getCentenePass();
        if (((((passBallCopaoFijo.floatValue() + passBallCopaoCorrido.floatValue()) + passBallFijo.floatValue()) + passBallCorrido.floatValue()) + passParllet.floatValue()) + passCentene.floatValue() == 0.0f) {
            return null;
        }
        StringBuilder s = new StringBuilder();
        String aux = Constants.NULL_STRING;
        if (passBallCopaoFijo.floatValue() != 0.0f) {
            s.append("Pasado copao fijo: ");
            s.append(Price.floatToString(passBallCopaoFijo));
            aux = "\n";
        }
        if (passBallCopaoCorrido.floatValue() != 0.0f) {
            s.append(aux);
            s.append("Pasado copao corrido: ");
            s.append(Price.floatToString(passBallCopaoCorrido));
            if (aux.isEmpty()) {
                aux = "\n";
            }
        }
        if (passBallFijo.floatValue() != 0.0f) {
            s.append(aux);
            s.append("Pasado fijo: ");
            s.append(Price.floatToString(passBallFijo));
            if (aux.isEmpty()) {
                aux = "\n";
            }
        }
        if (passBallCorrido.floatValue() != 0.0f) {
            s.append(aux);
            s.append("Pasado corrido: ");
            s.append(Price.floatToString(passBallCorrido));
            if (aux.isEmpty()) {
                aux = "\n";
            }
        }
        if (passParllet.floatValue() != 0.0f) {
            s.append(aux);
            s.append("Pasado parllet: ");
            s.append(Price.floatToString(passParllet));
            if (aux.isEmpty()) {
                aux = "\n";
            }
        }
        if (passCentene.floatValue() != 0.0f) {
            s.append(aux);
            s.append("Pasado centena: ");
            s.append(Price.floatToString(passCentene));
        }
        return s.toString();

         */
        return null;

    }

    public String getStringPassBote(Bingo bingo) {
        /*
        if (this.config.getBoolean(Config.PAY_FULL_PRICE_BOTE).booleanValue()) {
            return null;
        }
        PriceCopao _price_copao = new PriceCopao(0.0f, (this.config.getBoolean(Config.SEPARATE_LIMPIO_BOTE).booleanValue() ? LimpioBallBote() : LimpioBote()).floatValue(), bingo, (String[]) null, MainActivity.fitCopaoNumberBote(this.copaoBall));
        Float passBallCopaoFijo = _price_copao.getBallFijoPassBote(this._price.ballFijoBote);
        Float passBallCopaoCorrido = Float.valueOf(_price_copao.getBallCorridoPassBote(this._price.ballCorridoBote).floatValue() + _price_copao.getBallCorridoPassBote(this._price.ballCorridoDoubleBote).floatValue());
        Float passBallFijo = this._price.getBallFijoPassBote();
        Float passBallCorrido = Float.valueOf(this._price.getBallCorridoPassBote().floatValue() + this._price.getBallCorridoDoublePassBote().floatValue());
        Float passParllet = this._price.getParlletPassBote();
        Float passCentene = this._price.getCentenePassBote();
        if (((((passBallCopaoFijo.floatValue() + passBallCopaoCorrido.floatValue()) + passBallFijo.floatValue()) + passBallCorrido.floatValue()) + passParllet.floatValue()) + passCentene.floatValue() == 0.0f) {
            return null;
        }
        StringBuilder s = new StringBuilder();
        String aux = Constants.NULL_STRING;
        if (passBallCopaoFijo.floatValue() != 0.0f) {
            s.append(aux);
            s.append("Pasado copao fijo: ");
            s.append(Price.floatToString(passBallCopaoFijo));
            if (aux.isEmpty()) {
                aux = "\n";
            }
        }
        if (passBallCopaoCorrido.floatValue() != 0.0f) {
            s.append(aux);
            s.append("Pasado copao corrido: ");
            s.append(Price.floatToString(passBallCopaoCorrido));
            if (aux.isEmpty()) {
                aux = "\n";
            }
        }
        if (passBallFijo.floatValue() != 0.0f) {
            s.append(aux);
            s.append("Pasado fijo: ");
            s.append(Price.floatToString(passBallFijo));
            if (aux.isEmpty()) {
                aux = "\n";
            }
        }
        if (passBallCorrido.floatValue() != 0.0f) {
            s.append(aux);
            s.append("Pasado corrido: ");
            s.append(Price.floatToString(passBallCorrido));
            if (aux.isEmpty()) {
                aux = "\n";
            }
        }
        if (passParllet.floatValue() != 0.0f) {
            s.append(aux);
            s.append("Pasado parllet: ");
            s.append(Price.floatToString(passParllet));
            if (aux.isEmpty()) {
                aux = "\n";
            }
        }
        if (passCentene.floatValue() != 0.0f) {
            s.append(aux);
            s.append("Pasado centena: ");
            s.append(Price.floatToString(passCentene));
        }
        return s.toString();

         */

        return null;
    }
}
