package kiu.webmailnauta.legacy.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import kiu.webmailnauta.legacy.SevenZip.Compression.LZMA.Decoder;
import kiu.webmailnauta.legacy.SevenZip.Compression.LZMA.Encoder;

public class My7zip {
    public static File compress(File input_file) {
        try {
            String name = input_file.getName();
            File outFile7z = new File(input_file.getParent() + File.separator + (name.substring(0, name.indexOf(".")) + ".7z"));
            if (outFile7z.exists()) {
                outFile7z.delete();
            }

            FileInputStream fileInputStream_ = new FileInputStream(input_file);
            FileOutputStream fileOutputStream_ = new FileOutputStream(outFile7z);
            BufferedInputStream inStream = new BufferedInputStream(fileInputStream_);
            BufferedOutputStream outStream = new BufferedOutputStream(fileOutputStream_);
            Encoder encoder = new Encoder();
            encoder.SetEndMarkerMode(false);
            encoder.WriteCoderProperties(outStream);
            long fileSize = input_file.length();
            for (int i = 0; i < 8; i++) {
                outStream.write(((int) (fileSize >>> (i * 8))) & 255);
            }
            Encoder encoder2 = encoder;
            BufferedOutputStream outStream2 = outStream;
            encoder.Code(inStream, outStream, -1, -1, null);
            inStream.close();
            outStream2.flush();
            outStream2.close();
            return outFile7z;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] compress(byte[] data) {
        try {
            ByteArrayInputStream inStream = new ByteArrayInputStream(data);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            Encoder encoder = new Encoder();
            encoder.SetEndMarkerMode(false);
            encoder.WriteCoderProperties(outStream);
            long fileSize = (long) data.length;
            for (int i = 0; i < 8; i++) {
                outStream.write(((int) (fileSize >>> (i * 8))) & 255);
            }
            encoder.Code(inStream, outStream, -1, -1, null);
            inStream.close();
            outStream.flush();
            outStream.close();
            return outStream.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static File decompress(File input_file) {
        try {
            String name = input_file.getName();
            File output_file = new File(input_file.getParent() + File.separator + (name.substring(0, name.indexOf(".")) + ".enc"));
            BufferedInputStream inCompressedStream = new BufferedInputStream(new FileInputStream(input_file));
            BufferedOutputStream outCompressedStream = new BufferedOutputStream(new FileOutputStream(output_file));
            byte[] properties = new byte[5];
            if (inCompressedStream.read(properties, 0, 5) == 5) {
                Decoder decoder = new Decoder();
                if (decoder.SetDecoderProperties(properties)) {
                    long outSize = 0;
                    int i = 0;
                    while (i < 8) {
                        int v = inCompressedStream.read();
                        if (v >= 0) {
                            outSize |= ((long) v) << (i * 8);
                            i++;
                        } else {
                            throw new Exception("Can't read stream size");
                        }
                    }
                    if (decoder.Code(inCompressedStream, outCompressedStream, outSize)) {
                        outCompressedStream.flush();
                        outCompressedStream.close();
                        inCompressedStream.close();
                        return output_file;
                    }
                    throw new Exception("Error in data stream");
                }
                throw new Exception("Incorrect stream properties");
            }
            throw new Exception("input .lzma file is too short");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decompress(byte[] date) {
        try {
            ByteArrayInputStream inCompressedStream = new ByteArrayInputStream(date);
            ByteArrayOutputStream outCompressedStream = new ByteArrayOutputStream();
            byte[] properties = new byte[5];
            if (inCompressedStream.read(properties, 0, 5) == 5) {
                Decoder decoder = new Decoder();
                if (decoder.SetDecoderProperties(properties)) {
                    long outSize = 0;
                    int i = 0;
                    while (i < 8) {
                        int v = inCompressedStream.read();
                        if (v >= 0) {
                            outSize |= ((long) v) << (i * 8);
                            i++;
                        } else {
                            throw new Exception("Can't read stream size");
                        }
                    }
                    if (decoder.Code(inCompressedStream, outCompressedStream, outSize)) {
                        outCompressedStream.flush();
                        outCompressedStream.close();
                        inCompressedStream.close();
                        return outCompressedStream.toByteArray();
                    }
                    throw new Exception("Error in data stream");
                }
                throw new Exception("Incorrect stream properties");
            }
            throw new Exception("input .lzma file is too short");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
