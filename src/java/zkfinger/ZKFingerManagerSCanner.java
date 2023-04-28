package zkfinger;

import Controller.EmpreinteCtrl;
import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ZKFingerManagerSCanner {
    //for verify test

    private byte[] lastRegTemp = new byte[2048];
    //the length of lastRegTemp
    private int cbRegTemp = 0;
    //pre-register template
    private byte[][] regtemparray = new byte[3][2048];
    //Register
    private boolean bRegister = false;
    //Identify
    private boolean bIdentify = true;
    //finger id
    private int iFid = 1;

    private int nFakeFunOn = 1;
    //must be 3
    static final int enroll_cnt = 3;
    //the index of pre-register function
    private int enroll_idx = 0;

    private byte[] imgbuf = null;
    private byte[] template = new byte[2048];
    private int[] templateLen = new int[1];

    //the width of fingerprint image
    int fpWidth = 0;
    //the height of fingerprint image
    int fpHeight = 0;

    private long mhDevice = 0;
    private long mhDB ;

    private static ZKFingerManagerSCanner scanner;
    public boolean mbStop = true;
    private boolean openned = false;

    public static ZKFingerManagerSCanner getInstance() {
        if (scanner == null) {
            scanner = new ZKFingerManagerSCanner();
        }
        return scanner;
    }

    public void FreeSensor() {
        mbStop = true;
        if (0 != mhDB) {
            FingerprintSensorEx.DBFree(mhDB);
            mhDB = 0;
        }
        if (0 != mhDevice) {
            FingerprintSensorEx.CloseDevice(mhDevice);
            mhDevice = 0;

        }
        FingerprintSensorEx.Terminate();
    }

    public String open() {

        openned = false;

        if (0 != mhDevice) {

            return "Fermer ld'abord le scanner";

        }

        if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init()) {
            return "Erreur d'initiation du Scanner";
        }

        if (FingerprintSensorEx.GetDeviceCount() < 0) {
            return "Aucun n'appareil est connecté";

        }

        mhDevice = FingerprintSensorEx.OpenDevice(0);

        if (0 == mhDevice) {
            return "Erreur pendant l'ouverture du scanner";
        }

        mhDB = FingerprintSensorEx.DBInit();

        if (0 == mhDB) {
            return "Erreur pendant l'initiation du DB";
        }

        if (0 == FingerprintSensorEx.DBSetParameter(mhDB, 5010, 0)) {

            byte[] paramValue = new byte[4];
            int[] size = new int[1];
            size[0] = 4;
            FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue, size);
            fpWidth = byteArrayToInt(paramValue);
            size[0] = 4;
            FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
            fpHeight = byteArrayToInt(paramValue);

            imgbuf = new byte[fpWidth * fpHeight];

        }

        openned = true;

        return "Scanner connecté et bien activé";

    }

    public boolean isOpenned() {

        return openned;

    }

//    public boolean capture() {
//        File file = new File(AllPath.pathEmpreinte);
//        if (!file.exists()) {
//            file.mkdir();
//            System.out.println("vao2");
//        }
//        String userWindows = "";
//        templateLen[0] = 2048;
//        if (0 == FingerprintSensorEx.AcquireFingerprint(mhDevice, imgbuf, template, templateLen)) {
//            try {
//                userWindows = System.getProperty("user.name");
//                writeBitmap(imgbuf, fpWidth, fpHeight, AllPath.pathEmpreinte + "\\fingerprint.bmp");
//                return true;
//            } catch (Exception ex) {
//                System.out.println(ex.getMessage());
//            }
//
//        }
//        return false;
//    }
    public int match(String matricule, String doigt) throws ParseException {
      //  BigInteger a=new BigInteger("140723777122344");
       
        //  ZKFingerManager match = new ZKFingerManager(0, null);
        byte[] temp0 = new byte[2048];
        byte[] template1 = new byte[2048];

        int[] tempLen0 = new int[1];
        int[] templateLen1 = new int[1];

        tempLen0[0] = 2048;
        FingerprintSensorEx.ExtractFromImage(mhDB, "C:\\Users\\eqima\\Empreinte\\fingerprint.bmp", 500, temp0, tempLen0);
        int score = 332;
       // List<Blob> empreinte = EmpreinteDAO.getInstance().getImageEmpreinteBDD(matricule, doigt);
        List<Blob> empreinte =null;
        String mhBB = "122344";
        String JsonData = "{\"resultat\":" + mhBB + "}";
        JSONParser parser = new JSONParser();
        JSONObject resultat = (JSONObject) parser.parse(JsonData);
       // int res =(int) resultat.get("file");
       // System.out.println(res);

        if (empreinte != null) {
            System.out.println("eto");
            for (Blob blob : empreinte) {
                templateLen1[0] = 2048;

                try {
                    InputStream is = blob.getBinaryStream(1, (int) blob.length());

                    BufferedImage imag = ImageIO.read(is);

                    ImageIO.write(imag, "bmp", new File("BDD.bmp"));

                    FingerprintSensorEx.ExtractFromImage(mhDB, "BDD.bmp", 500, template1, templateLen1);

                    score = FingerprintSensorEx.DBMatch(mhDB, template1, temp0);
                    System.out.println("eto");
                 
                } catch (IOException | SQLException e) {
                    Logger.getLogger(ZKFingerManagerSCanner.class.getName()).log(Level.SEVERE, null, e);
                }

            }
        }
        return score;
    }

//    public class ZKScannerMatch {
//        
//        public int score;
//        public Empreinte empreinte;
//        
//        public ZKScannerMatch(int score, Empreinte empreinte) {
//            this.score = score;
//            this.empreinte = empreinte;
//        }
//    }
    public static void writeBitmap(byte[] imageBuf, int nWidth, int nHeight, String path) throws IOException {
        java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
        java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

        int w = (((nWidth + 3) / 4) * 4);
        int bfType = 0x424d;
        int bfSize = 54 + 1024 + w * nHeight;
        int bfReserved1 = 0;
        int bfReserved2 = 0;
        int bfOffBits = 54 + 1024;

        dos.writeShort(bfType);
        dos.write(changeByte(bfSize), 0, 4);
        dos.write(changeByte(bfReserved1), 0, 2);
        dos.write(changeByte(bfReserved2), 0, 2);
        dos.write(changeByte(bfOffBits), 0, 4);

        int biSize = 40;
        int biWidth = nWidth;
        int biHeight = nHeight;
        int biPlanes = 1;
        int biBitcount = 8;
        int biCompression = 0;
        int biSizeImage = w * nHeight;
        int biXPelsPerMeter = 0;
        int biYPelsPerMeter = 0;
        int biClrUsed = 0;
        int biClrImportant = 0;
        dos.write(changeByte(biSize), 0, 4);
        dos.write(changeByte(biWidth), 0, 4);
        dos.write(changeByte(biHeight), 0, 4);
        dos.write(changeByte(biPlanes), 0, 2);
        dos.write(changeByte(biBitcount), 0, 2);
        dos.write(changeByte(biCompression), 0, 4);
        dos.write(changeByte(biSizeImage), 0, 4);
        dos.write(changeByte(biXPelsPerMeter), 0, 4);
        dos.write(changeByte(biYPelsPerMeter), 0, 4);
        dos.write(changeByte(biClrUsed), 0, 4);
        dos.write(changeByte(biClrImportant), 0, 4);

        for (int i = 0; i < 256; i++) {
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(0);
        }

        byte[] filter = null;
        if (w > nWidth) {
            filter = new byte[w - nWidth];
        }

        for (int i = 0; i < nHeight; i++) {
            dos.write(imageBuf, (nHeight - 1 - i) * nWidth, nWidth);
            if (w > nWidth) {
                dos.write(filter, 0, w - nWidth);
            }
        }
        dos.flush();
        dos.close();
        fos.close();
    }

    public static byte[] changeByte(int data) {
        return intToByteArray(data);
    }

    public static byte[] intToByteArray(final int number) {
        byte[] abyte = new byte[4];

        abyte[0] = (byte) (0xff & number);

        abyte[1] = (byte) ((0xff00 & number) >> 8);
        abyte[2] = (byte) ((0xff0000 & number) >> 16);
        abyte[3] = (byte) ((0xff000000 & number) >> 24);
        return abyte;
    }

    public static int byteArrayToInt(byte[] bytes) {
        int number = bytes[0] & 0xFF;

        number |= ((bytes[1] << 8) & 0xFF00);
        number |= ((bytes[2] << 16) & 0xFF0000);
        number |= ((bytes[3] << 24) & 0xFF000000);
        return number;
    }

}
