package kiu.webmailnauta.legacy;

import java.util.ArrayList;
import java.util.List;

public class FactoryRowList {
    private List<Row> _ball = new ArrayList();
    private List<Row> _ball_bote = new ArrayList();
    private List<Row> _centene = new ArrayList();
    private List<Row> _centene_bote = new ArrayList();
    private List<Row> _parllet = new ArrayList();
    private List<Row> _parllet_bote = new ArrayList();

    public void clear() {
        this._ball.clear();
        this._parllet.clear();
        this._centene.clear();
        this._ball_bote.clear();
        this._parllet_bote.clear();
        this._centene_bote.clear();
    }

    public List<Row> getRowList(Row row) {
        if (row == null) {
            return null;
        }
        if (row instanceof BallBoteRow) {
            return this._ball_bote;
        }
        if (row instanceof ParlletBoteRow) {
            return this._parllet_bote;
        }
        if (row instanceof CenteneBoteRow) {
            return this._centene_bote;
        }
        if (row instanceof BallRow) {
            return this._ball;
        }
        if (row instanceof ParlletRow) {
            return this._parllet;
        }
        if (row instanceof CenteneRow) {
            return this._centene;
        }
        return null;
    }

    public List<Row> getRowList(int selectedChoice) {
        switch (selectedChoice) {
            case Constants.BALL:
                return this._ball;
            case Constants.PARLLET:
                return this._parllet;
            case Constants.CENTENE:
                return this._centene;
            case Constants.BALL_BOTE:
                return this._ball_bote;
            case Constants.PARLLET_BOTE:
                return this._parllet_bote;
            case Constants.CENTENE_BOTE:
                return this._centene_bote;
            default:
                return null;
        }
    }

    public boolean isEmpty() {
        return isEmptyBote() && isEmptyList();
    }

    public boolean isEmptyBote() {
        return this._ball_bote.size() == 0 && this._parllet_bote.size() == 0 && this._centene_bote.size() == 0;
    }

    public boolean isEmptyList() {
        return this._ball.size() == 0 && this._parllet.size() == 0 && this._centene.size() == 0;
    }

    private void joinList(StringBuilder allList, List<Row> list) {
        for (Row row : list) {
            allList.append(row.getRowString()).append(ConstantsParserRowList.ROW_SEPARATOR);
        }
    }

    public String toString() {
        StringBuilder allList = new StringBuilder();
        joinList(allList, this._ball);
        joinList(allList, this._parllet);
        joinList(allList, this._centene);
        joinList(allList, this._ball_bote);
        joinList(allList, this._parllet_bote);
        joinList(allList, this._centene_bote);
        allList.deleteCharAt(allList.length() - 1);
        return allList.toString();
    }
}
