package kiu.webmailnauta.legacy.utils;

import android.util.Log;

import java.io.File;
import java.io.PrintStream;
import java.util.List;

import kiu.webmailnauta.legacy.Constants;
import kiu.webmailnauta.legacy.parser.Cursor;
import kiu.webmailnauta.legacy.parser.Parser;
import kiu.webmailnauta.legacy.parser.RowLineString;
import kiu.webmailnauta.legacy.parser.encode.ParserFactoryEncode;

public class CodeList {
    private static String encodeDir(File list_dir) {
        int i = 0;
        File[] files = new File[0];
        String str = null;
        String file_text = null;
        File[] files2;
        int i2;
        String str2;
        String str3;
        File file_list;
        File[] files3;
        int i3;
        String str4;
        String str5;
        File file_list2;
        int i4;
        String file_text2 = Constants.NULL_STRING;
        String str6 = ".";
        try {
            if (!list_dir.exists() || !list_dir.isDirectory()) {
                return null;
            }
            File[] files4 = list_dir.listFiles();
            String all_file_text = file_text2;
            int length = files4.length;
            int i5 = 0;
            int i6 = 0;
            while (i6 < i) {
                File file_list3 = files[i6];
                String file_name = file_list3.getName();
                if (!file_name.endsWith(".txt")) {
                    str2 = file_text;
                    str3 = str;
                    files2 = files;
                    i2 = i;
                } else {
                    String list = file_name.substring(i5, file_name.indexOf(str));
                    Cursor cursor = new Cursor(file_list3);
                    Parser parser = new ParserFactoryEncode().createParser(cursor);
                    String[] list_parts = list.split("-");
                    String file_text3 = file_text;
                    if (list_parts.length == 2) {
                        file_text3 = str + Convert.stringToAscii(list_parts[0]) + "X" + list_parts[1] + "\n";
                    }
                    while (cursor.hasNext()) {
                        if (parser.isCreated()) {
                            List<RowLineString> aux = parser.getRowsLines(cursor);
                            if (aux != null) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(file_text3);
                                sb.append(str);
                                str4 = file_text;
                                sb.append(Convert.stringToAscii(parser.getTypeString()));
                                sb.append("\n");
                                String file_text4 = sb.toString();
                                for (RowLineString rl : aux) {
                                    String str7 = str;
                                    File[] files5 = files;
                                    String encode = rl.process();
                                    if (encode.startsWith("Error")) {
                                        PrintStream printStream = System.out;
                                        i4 = i;
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(encode);
                                        file_list2 = file_list3;
                                        sb2.append("in ");
                                        sb2.append(list);
                                        sb2.append(" line ");
                                        sb2.append(cursor.getLineNumber());
                                        printStream.println(sb2.toString());
                                    } else {
                                        i4 = i;
                                        file_list2 = file_list3;
                                        file_text4 = file_text4 + encode + "\n";
                                    }
                                    str = str7;
                                    i = i4;
                                    files = files5;
                                    file_list3 = file_list2;
                                }
                                str5 = str;
                                files3 = files;
                                i3 = i;
                                file_list = file_list3;
                                file_text3 = file_text4;
                            } else {
                                str4 = file_text;
                                str5 = str;
                                files3 = files;
                                i3 = i;
                                file_list = file_list3;
                            }
                            str = str5;
                            file_text = str4;
                            i = i3;
                            files = files3;
                            file_list3 = file_list;
                        } else {
                            parser = new ParserFactoryEncode().createParser(cursor);
                            str = str;
                            file_text = file_text;
                            i = i;
                            files = files;
                            file_list3 = file_list3;
                        }
                    }
                    str2 = file_text;
                    str3 = str;
                    files2 = files;
                    i2 = i;
                    File file = file_list3;
                    all_file_text = all_file_text + file_text3;
                }
                i6++;
                str6 = str3;
                file_text2 = str2;
                length = i2;
                files4 = files2;
                i5 = 0;
            }
            return all_file_text;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String encode(File file) {
        if (file.isDirectory()) {
            return encodeDir(file);
        }
        if (!file.exists()) {
            return null;
        }
        try {
            String file_name = file.getName();
            if (!file_name.endsWith(".txt")) {
                return null;
            }
            String list = file_name.substring(0, file_name.indexOf("."));
            try {
                Cursor cursor = new Cursor(file);
                Parser parser = new ParserFactoryEncode().createParser(cursor);
                String[] list_parts = list.split("-");
                String file_text = Constants.NULL_STRING;
                if (list_parts.length == 2) {
                    file_text = "." + Convert.stringToAscii(list_parts[0]) + "X" + list_parts[1] + "\n";
                }
                while (cursor.hasNext()) {
                    if (parser.isCreated()) {
                        List<RowLineString> aux = parser.getRowsLines(cursor);
                        if (aux != null) {
                            file_text = file_text + "." + Convert.stringToAscii(parser.getTypeString()) + "\n";
                            for (RowLineString rl : aux) {
                                String encode = rl.process();
                                if (encode.startsWith("Error")) {
                                    Log.e("Error", encode + "in " + list + " line " + cursor.getLineNumber());
                                } else {
                                    file_text = file_text + encode + "\n";
                                }
                            }
                        }
                    } else {
                        parser = new ParserFactoryEncode().createParser(cursor);
                    }
                }
                return file_text;
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                return null;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String encode(String list, String play_name, String list_number, String list_version) {
        try {
            Cursor cursor = new Cursor(list);
            Parser parser = new ParserFactoryEncode().createParser(cursor);
            String file_text = "." + Convert.stringToAscii(play_name) + "X" + list_number + "X" + list_version + "\n";
            while (cursor.hasNext()) {
                if (parser.isCreated()) {
                    List<RowLineString> aux = parser.getRowsLines(cursor);
                    if (aux != null) {
                        file_text = file_text + "." + Convert.stringToAscii(parser.getTypeString()) + "\n";
                        for (RowLineString rl : aux) {
                            String compress = rl.process();
                            if (compress.startsWith("Error")) {
                                Log.e("Error", compress + "in " + play_name + "-" + list_number + " line " + cursor.getLineNumber());
                            } else {
                                file_text = file_text + compress + "\n";
                            }
                        }
                    }
                } else {
                    parser = new ParserFactoryEncode().createParser(cursor);
                }
            }
            return file_text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encode(String[] lists, String[] play_name, String[] list_numbers, String[] list_version) {
        try {
            int size = lists.length;
            String all_file_text = Constants.NULL_STRING;
            for (int i = 0; i < size; i++) {
                all_file_text = all_file_text + encode(lists[i], play_name[i], list_numbers[i], list_version[i]);
            }
            return all_file_text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0077 A[Catch:{ Exception -> 0x0184 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static String decode(String r21, String r22) {
        /*
            r1 = r22
            java.lang.String r0 = "&no&no&no&no&no&no"
            java.lang.String r2 = "&"
            java.lang.String r3 = "&false&v4&"
            java.lang.String r4 = "-"
            lottery.list.legacy.parser.Cursor r5 = new lottery.list.legacy.parser.Cursor     // Catch:{ Exception -> 0x0184 }
            r6 = r21
            r5.<init>((java.lang.String) r6)     // Catch:{ Exception -> 0x0184 }
            lottery.list.legacy.parser.decode.ParserFactoryDecode r7 = new lottery.list.legacy.parser.decode.ParserFactoryDecode     // Catch:{ Exception -> 0x0184 }
            r7.<init>()     // Catch:{ Exception -> 0x0184 }
            lottery.list.legacy.parser.Parser r8 = r7.createParser(r5)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r9 = r7.getPlayName()     // Catch:{ Exception -> 0x0184 }
            java.lang.String r10 = r7.getListNumber()     // Catch:{ Exception -> 0x0184 }
            java.lang.String r11 = r7.getCollector()     // Catch:{ Exception -> 0x0184 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0184 }
            r12.<init>()     // Catch:{ Exception -> 0x0184 }
            r12.append(r9)     // Catch:{ Exception -> 0x0184 }
            r12.append(r4)     // Catch:{ Exception -> 0x0184 }
            r12.append(r10)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r13 = ""
            if (r11 == 0) goto L_0x004e
            boolean r14 = r11.isEmpty()     // Catch:{ Exception -> 0x0184 }
            if (r14 != 0) goto L_0x004e
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0184 }
            r14.<init>()     // Catch:{ Exception -> 0x0184 }
            r14.append(r4)     // Catch:{ Exception -> 0x0184 }
            r14.append(r11)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x0184 }
            goto L_0x004f
        L_0x004e:
            r14 = r13
        L_0x004f:
            r12.append(r14)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0184 }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0184 }
            r14.<init>()     // Catch:{ Exception -> 0x0184 }
            r14.append(r1)     // Catch:{ Exception -> 0x0184 }
            r14.append(r3)     // Catch:{ Exception -> 0x0184 }
            r14.append(r10)     // Catch:{ Exception -> 0x0184 }
            r14.append(r2)     // Catch:{ Exception -> 0x0184 }
            r14.append(r9)     // Catch:{ Exception -> 0x0184 }
            r14.append(r0)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x0184 }
        L_0x0071:
            boolean r15 = r5.hasNext()     // Catch:{ Exception -> 0x0184 }
            if (r15 == 0) goto L_0x0183
            boolean r15 = r8.isCreated()     // Catch:{ Exception -> 0x0184 }
            if (r15 == 0) goto L_0x0114
            java.util.List r15 = r8.getRowsLines(r5)     // Catch:{ Exception -> 0x0184 }
            if (r15 == 0) goto L_0x0104
            java.util.Iterator r16 = r15.iterator()     // Catch:{ Exception -> 0x0184 }
        L_0x0087:
            boolean r17 = r16.hasNext()     // Catch:{ Exception -> 0x0184 }
            if (r17 == 0) goto L_0x00fd
            java.lang.Object r17 = r16.next()     // Catch:{ Exception -> 0x0184 }
            lottery.list.legacy.parser.RowLineString r17 = (lottery.list.legacy.parser.RowLineString) r17     // Catch:{ Exception -> 0x0184 }
            java.lang.String r18 = r17.process()     // Catch:{ Exception -> 0x0184 }
            r19 = r18
            java.lang.String r6 = "Error"
            r18 = r9
            r9 = r19
            boolean r6 = r9.startsWith(r6)     // Catch:{ Exception -> 0x0184 }
            if (r6 == 0) goto L_0x00cf
            java.io.PrintStream r6 = java.lang.System.out     // Catch:{ Exception -> 0x0184 }
            r19 = r10
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0184 }
            r10.<init>()     // Catch:{ Exception -> 0x0184 }
            r10.append(r9)     // Catch:{ Exception -> 0x0184 }
            r20 = r11
            java.lang.String r11 = "in "
            r10.append(r11)     // Catch:{ Exception -> 0x0184 }
            r10.append(r12)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r11 = " line "
            r10.append(r11)     // Catch:{ Exception -> 0x0184 }
            int r11 = r5.getLineNumber()     // Catch:{ Exception -> 0x0184 }
            r10.append(r11)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0184 }
            r6.println(r10)     // Catch:{ Exception -> 0x0184 }
            goto L_0x00f4
        L_0x00cf:
            r19 = r10
            r20 = r11
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0184 }
            r6.<init>()     // Catch:{ Exception -> 0x0184 }
            r6.append(r14)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r10 = "&0,0,"
            r6.append(r10)     // Catch:{ Exception -> 0x0184 }
            r6.append(r9)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r10 = ","
            r6.append(r10)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r10 = r8.getTypeString()     // Catch:{ Exception -> 0x0184 }
            r6.append(r10)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0184 }
            r14 = r6
        L_0x00f4:
            r6 = r21
            r9 = r18
            r10 = r19
            r11 = r20
            goto L_0x0087
        L_0x00fd:
            r18 = r9
            r19 = r10
            r20 = r11
            goto L_0x010a
        L_0x0104:
            r18 = r9
            r19 = r10
            r20 = r11
        L_0x010a:
            r6 = r21
            r9 = r18
            r10 = r19
            r11 = r20
            goto L_0x0071
        L_0x0114:
            r18 = r9
            r19 = r10
            r20 = r11
            lottery.list.legacy.parser.Parser r6 = r7.createParser(r5)     // Catch:{ Exception -> 0x0184 }
            r8 = r6
            java.lang.String r6 = r7.getPlayName()     // Catch:{ Exception -> 0x0184 }
            r9 = r6
            java.lang.String r6 = r7.getListNumber()     // Catch:{ Exception -> 0x0184 }
            r10 = r6
            java.lang.String r6 = r7.getCollector()     // Catch:{ Exception -> 0x0184 }
            r11 = r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0184 }
            r6.<init>()     // Catch:{ Exception -> 0x0184 }
            r6.append(r9)     // Catch:{ Exception -> 0x0184 }
            r6.append(r4)     // Catch:{ Exception -> 0x0184 }
            r6.append(r10)     // Catch:{ Exception -> 0x0184 }
            if (r11 == 0) goto L_0x0154
            boolean r15 = r11.isEmpty()     // Catch:{ Exception -> 0x0184 }
            if (r15 != 0) goto L_0x0154
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0184 }
            r15.<init>()     // Catch:{ Exception -> 0x0184 }
            r15.append(r4)     // Catch:{ Exception -> 0x0184 }
            r15.append(r11)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r15 = r15.toString()     // Catch:{ Exception -> 0x0184 }
            goto L_0x0155
        L_0x0154:
            r15 = r13
        L_0x0155:
            r6.append(r15)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0184 }
            boolean r15 = r6.equals(r12)     // Catch:{ Exception -> 0x0184 }
            if (r15 != 0) goto L_0x017f
            r12 = r6
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0184 }
            r15.<init>()     // Catch:{ Exception -> 0x0184 }
            r15.append(r1)     // Catch:{ Exception -> 0x0184 }
            r15.append(r3)     // Catch:{ Exception -> 0x0184 }
            r15.append(r10)     // Catch:{ Exception -> 0x0184 }
            r15.append(r2)     // Catch:{ Exception -> 0x0184 }
            r15.append(r9)     // Catch:{ Exception -> 0x0184 }
            r15.append(r0)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r15 = r15.toString()     // Catch:{ Exception -> 0x0184 }
            r14 = r15
        L_0x017f:
            r6 = r21
            goto L_0x0071
        L_0x0183:
            return r14
        L_0x0184:
            r0 = move-exception
            r0.printStackTrace()
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: lottery.list.legacy.utils.CodeList.decode(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0094 A[Catch:{ Exception -> 0x01d6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x01d1 A[Catch:{ Exception -> 0x01d6 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.HashMap<String, String> decode(File r23, String r24) {
        /*
            r1 = r24
            java.lang.String r0 = "&no&no&no&no&no&no"
            java.lang.String r2 = "&false&"
            java.lang.String r3 = "&"
            java.lang.String r4 = "-"
            boolean r5 = r23.exists()     // Catch:{ Exception -> 0x01d6 }
            if (r5 == 0) goto L_0x01d5
            boolean r5 = r23.isFile()     // Catch:{ Exception -> 0x01d6 }
            if (r5 == 0) goto L_0x01d5
            lottery.list.legacy.parser.Cursor r5 = new lottery.list.legacy.parser.Cursor     // Catch:{ Exception -> 0x01d6 }
            r6 = r23
            r5.<init>((java.io.File) r6)     // Catch:{ Exception -> 0x01d6 }
            lottery.list.legacy.parser.decode.ParserFactoryDecode r7 = new lottery.list.legacy.parser.decode.ParserFactoryDecode     // Catch:{ Exception -> 0x01d6 }
            r7.<init>()     // Catch:{ Exception -> 0x01d6 }
            lottery.list.legacy.parser.Parser r8 = r7.createParser(r5)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r9 = r7.getPlayName()     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r10 = r7.getListNumber()     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r11 = r7.getCollector()     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r12 = r7.getListVersion()     // Catch:{ Exception -> 0x01d6 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01d6 }
            r13.<init>()     // Catch:{ Exception -> 0x01d6 }
            r13.append(r9)     // Catch:{ Exception -> 0x01d6 }
            r13.append(r4)     // Catch:{ Exception -> 0x01d6 }
            r13.append(r10)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r14 = ""
            if (r11 == 0) goto L_0x005e
            boolean r15 = r11.isEmpty()     // Catch:{ Exception -> 0x01d6 }
            if (r15 != 0) goto L_0x005e
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01d6 }
            r15.<init>()     // Catch:{ Exception -> 0x01d6 }
            r15.append(r4)     // Catch:{ Exception -> 0x01d6 }
            r15.append(r11)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r15 = r15.toString()     // Catch:{ Exception -> 0x01d6 }
            goto L_0x005f
        L_0x005e:
            r15 = r14
        L_0x005f:
            r13.append(r15)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x01d6 }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01d6 }
            r15.<init>()     // Catch:{ Exception -> 0x01d6 }
            r15.append(r1)     // Catch:{ Exception -> 0x01d6 }
            r15.append(r2)     // Catch:{ Exception -> 0x01d6 }
            r15.append(r12)     // Catch:{ Exception -> 0x01d6 }
            r15.append(r3)     // Catch:{ Exception -> 0x01d6 }
            r15.append(r10)     // Catch:{ Exception -> 0x01d6 }
            r15.append(r3)     // Catch:{ Exception -> 0x01d6 }
            r15.append(r9)     // Catch:{ Exception -> 0x01d6 }
            r15.append(r0)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r15 = r15.toString()     // Catch:{ Exception -> 0x01d6 }
            java.util.HashMap r16 = new java.util.HashMap     // Catch:{ Exception -> 0x01d6 }
            r16.<init>()     // Catch:{ Exception -> 0x01d6 }
            r17 = r16
        L_0x008e:
            boolean r16 = r5.hasNext()     // Catch:{ Exception -> 0x01d6 }
            if (r16 == 0) goto L_0x01c1
            boolean r16 = r8.isCreated()     // Catch:{ Exception -> 0x01d6 }
            if (r16 == 0) goto L_0x0131
            java.util.List r16 = r8.getRowsLines(r5)     // Catch:{ Exception -> 0x01d6 }
            if (r16 == 0) goto L_0x0121
            java.util.Iterator r18 = r16.iterator()     // Catch:{ Exception -> 0x01d6 }
        L_0x00a4:
            boolean r19 = r18.hasNext()     // Catch:{ Exception -> 0x01d6 }
            if (r19 == 0) goto L_0x011a
            java.lang.Object r19 = r18.next()     // Catch:{ Exception -> 0x01d6 }
            lottery.list.legacy.parser.RowLineString r19 = (lottery.list.legacy.parser.RowLineString) r19     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r20 = r19.process()     // Catch:{ Exception -> 0x01d6 }
            r21 = r20
            java.lang.String r6 = "Error"
            r20 = r9
            r9 = r21
            boolean r6 = r9.startsWith(r6)     // Catch:{ Exception -> 0x01d6 }
            if (r6 == 0) goto L_0x00ec
            java.io.PrintStream r6 = java.lang.System.out     // Catch:{ Exception -> 0x01d6 }
            r21 = r10
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01d6 }
            r10.<init>()     // Catch:{ Exception -> 0x01d6 }
            r10.append(r9)     // Catch:{ Exception -> 0x01d6 }
            r22 = r11
            java.lang.String r11 = "in "
            r10.append(r11)     // Catch:{ Exception -> 0x01d6 }
            r10.append(r13)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r11 = " line "
            r10.append(r11)     // Catch:{ Exception -> 0x01d6 }
            int r11 = r5.getLineNumber()     // Catch:{ Exception -> 0x01d6 }
            r10.append(r11)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x01d6 }
            r6.println(r10)     // Catch:{ Exception -> 0x01d6 }
            goto L_0x0111
        L_0x00ec:
            r21 = r10
            r22 = r11
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01d6 }
            r6.<init>()     // Catch:{ Exception -> 0x01d6 }
            r6.append(r15)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r10 = "&0,0,"
            r6.append(r10)     // Catch:{ Exception -> 0x01d6 }
            r6.append(r9)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r10 = ","
            r6.append(r10)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r10 = r8.getTypeString()     // Catch:{ Exception -> 0x01d6 }
            r6.append(r10)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x01d6 }
            r15 = r6
        L_0x0111:
            r6 = r23
            r9 = r20
            r10 = r21
            r11 = r22
            goto L_0x00a4
        L_0x011a:
            r20 = r9
            r21 = r10
            r22 = r11
            goto L_0x0127
        L_0x0121:
            r20 = r9
            r21 = r10
            r22 = r11
        L_0x0127:
            r6 = r23
            r9 = r20
            r10 = r21
            r11 = r22
            goto L_0x008e
        L_0x0131:
            r20 = r9
            r21 = r10
            r22 = r11
            lottery.list.legacy.parser.Parser r6 = r7.createParser(r5)     // Catch:{ Exception -> 0x01d6 }
            r8 = r6
            java.lang.String r6 = r7.getPlayName()     // Catch:{ Exception -> 0x01d6 }
            r9 = r6
            java.lang.String r6 = r7.getListNumber()     // Catch:{ Exception -> 0x01d6 }
            r10 = r6
            java.lang.String r6 = r7.getCollector()     // Catch:{ Exception -> 0x01d6 }
            r11 = r6
            java.lang.String r6 = r7.getListVersion()     // Catch:{ Exception -> 0x01d6 }
            r12 = r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01d6 }
            r6.<init>()     // Catch:{ Exception -> 0x01d6 }
            r6.append(r9)     // Catch:{ Exception -> 0x01d6 }
            r6.append(r4)     // Catch:{ Exception -> 0x01d6 }
            r6.append(r10)     // Catch:{ Exception -> 0x01d6 }
            if (r11 == 0) goto L_0x0178
            boolean r16 = r11.isEmpty()     // Catch:{ Exception -> 0x01d6 }
            if (r16 != 0) goto L_0x0178
            r16 = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01d6 }
            r5.<init>()     // Catch:{ Exception -> 0x01d6 }
            r5.append(r4)     // Catch:{ Exception -> 0x01d6 }
            r5.append(r11)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x01d6 }
            goto L_0x017b
        L_0x0178:
            r16 = r5
            r5 = r14
        L_0x017b:
            r6.append(r5)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r5 = r6.toString()     // Catch:{ Exception -> 0x01d6 }
            boolean r6 = r5.equals(r13)     // Catch:{ Exception -> 0x01d6 }
            if (r6 != 0) goto L_0x01b3
            r6 = r17
            r6.put(r13, r15)     // Catch:{ Exception -> 0x01d6 }
            r13 = r5
            r17 = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01d6 }
            r4.<init>()     // Catch:{ Exception -> 0x01d6 }
            r4.append(r1)     // Catch:{ Exception -> 0x01d6 }
            r4.append(r2)     // Catch:{ Exception -> 0x01d6 }
            r4.append(r12)     // Catch:{ Exception -> 0x01d6 }
            r4.append(r3)     // Catch:{ Exception -> 0x01d6 }
            r4.append(r10)     // Catch:{ Exception -> 0x01d6 }
            r4.append(r3)     // Catch:{ Exception -> 0x01d6 }
            r4.append(r9)     // Catch:{ Exception -> 0x01d6 }
            r4.append(r0)     // Catch:{ Exception -> 0x01d6 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01d6 }
            r15 = r4
            goto L_0x01b7
        L_0x01b3:
            r6 = r17
            r17 = r4
        L_0x01b7:
            r5 = r16
            r4 = r17
            r17 = r6
            r6 = r23
            goto L_0x008e
        L_0x01c1:
            r16 = r5
            r20 = r9
            r21 = r10
            r22 = r11
            r6 = r17
            boolean r0 = r15.isEmpty()     // Catch:{ Exception -> 0x01d6 }
            if (r0 != 0) goto L_0x01d4
            r6.put(r13, r15)     // Catch:{ Exception -> 0x01d6 }
        L_0x01d4:
            return r6
        L_0x01d5:
            goto L_0x01da
        L_0x01d6:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01da:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: lottery.list.legacy.utils.CodeList.decode(java.io.File, java.lang.String):java.util.HashMap");
    }
}
