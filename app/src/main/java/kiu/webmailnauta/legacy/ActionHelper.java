package kiu.webmailnauta.legacy;

import com.google.gson.Gson;

import java.util.List;

import kiu.webmailnauta.legacy.cipher.CipherAesEncrypt;
import kiu.webmailnauta.legacy.cipher.CipherRsaEncrypt;
import kiu.webmailnauta.legacy.runners.Action;
import kiu.webmailnauta.legacy.utils.CodeList;
import kiu.webmailnauta.legacy.utils.Convert;
import kiu.webmailnauta.legacy.utils.My7zip;
import kiu.webmailnauta.legacy.utils.MyBase64;
import kiu.webmailnauta.legacy.utils.PasswordGenerator;

public class ActionHelper {
    public static final String ACTION = "BATCH";
    private final String collector;
    private final PaperList paperList;
    private final String subject;
    private final String userMail;

    public ActionHelper(String subject, PaperList paperList, String collector, String userMail) {
        this.subject = subject;
        this.paperList = paperList;
        this.collector = collector;
        this.userMail = userMail;
    }

    public String getEncryptedAction() {
        String json = new Gson().toJson((Object) new Action(subject, ACTION, getBody(), userMail));
        String passwordGenerated = PasswordGenerator.getPass(16);
        String jsonEncryptedAes = CipherAesEncrypt.doFinal(json, passwordGenerated);
        String passwordEncryptedRsa = CipherRsaEncrypt.doFinal(passwordGenerated);
        return passwordEncryptedRsa + "." + jsonEncryptedAes;
    }

    public String getEncryptedAction(String body) {
        String json = new Gson().toJson((Object) new Action(subject, ACTION, body, userMail));
        String passwordGenerated = PasswordGenerator.getPass(16);
        String jsonEncryptedAes = CipherAesEncrypt.doFinal(json, passwordGenerated);
        String passwordEncryptedRsa = CipherRsaEncrypt.doFinal(passwordGenerated);
        return passwordEncryptedRsa + "." + jsonEncryptedAes;
    }

    public String getBody() {
        return MyBase64.encodeToString(getCompressed(), 10);
    }

    public String getBody(byte[] compressed) {
        return MyBase64.encodeToString(compressed, 10);
    }

    public byte[] getCompressed() {
        return My7zip.compress((".." + Convert.stringToAscii(collector) + "\n" + getEncodeList()).getBytes());
    }

    public byte[] getCompressed(String encodedList) {
        return My7zip.compress((".." + Convert.stringToAscii(collector) + "\n" + encodedList).getBytes());
    }

    public String getEncodeList(PaperList paperList2) {
        return CodeList.encode(getPlaneList(), paperList2.getPlayName(), paperList2.getListNumber(), paperList2.getVersion());
    }

    public String getEncodeList() {
        return getEncodeList(paperList);
    }

    public String getPlaneList(PaperList paperList2) {
        FactoryRowList r_list = paperList2.getRowList();
        String planeList = Constants.NULL_STRING;
        List<Row> bola = r_list.getRowList((int) Constants.BALL);
        if (!bola.isEmpty()) {
            planeList = ((planeList + "-----------------------------------\n") + "---------------Bola----------------\n") + "-----------------------------------\n";
            for (Row b : bola) {
                planeList = planeList + b.toString() + "\n";
            }
        }
        List<Row> parlet = r_list.getRowList((int) Constants.PARLLET);
        if (!parlet.isEmpty()) {
            planeList = ((planeList + "-----------------------------------\n") + "--------------Parlet---------------\n") + "-----------------------------------\n";
            for (Row p : parlet) {
                planeList = planeList + p.toString() + "\n";
            }
        }
        List<Row> centena = r_list.getRowList((int) Constants.CENTENE);
        if (!centena.isEmpty()) {
            planeList = ((planeList + "-----------------------------------\n") + "--------------Centena--------------\n") + "-----------------------------------\n";
            for (Row c : centena) {
                planeList = planeList + c.toString() + "\n";
            }
        }
        List<Row> bolaBote = r_list.getRowList((int) Constants.BALL_BOTE);
        if (!bolaBote.isEmpty()) {
            planeList = ((planeList + "-----------------------------------\n") + "------------Bola Bote--------------\n") + "-----------------------------------\n";
            for (Row b2 : bolaBote) {
                planeList = planeList + b2.toString() + "\n";
            }
        }
        List<Row> parletBote = r_list.getRowList((int) Constants.PARLLET_BOTE);
        if (!parletBote.isEmpty()) {
            planeList = ((planeList + "-----------------------------------\n") + "-----------Parlet Bote-------------\n") + "-----------------------------------\n";
            for (Row p2 : parletBote) {
                planeList = planeList + p2.toString() + "\n";
            }
        }
        List<Row> centenaBote = r_list.getRowList((int) Constants.CENTENE_BOTE);
        if (!centenaBote.isEmpty()) {
            planeList = ((planeList + "-----------------------------------\n") + "-----------Centena Bote------------\n") + "-----------------------------------\n";
            for (Row c2 : centenaBote) {
                planeList = planeList + c2.toString() + "\n";
            }
        }
        return planeList;
    }

    public String getPlaneList() {
        return getPlaneList(paperList);
    }
}
