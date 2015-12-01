package ua.cv.toro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipOutputStream;

/**
 * Created by Vasiliy.Torous on 24.11.2015.
 */

public class StockFile {

    private final String VECTORSTOCKFOLDER = "For VectorStock";
    private File epsFile;                   // eps file
    private File jpgFile;                   // zip file
    private String epsFileFullPath;         // full path for eps file
    private String jpgFileFullPath;         // full path for jpg file
    private String zipFileFullPath;         // full path for general zip file
    private String vsFolderFullPath;        // folder containing file for VectorStock
    private String zipFileForVSFullPath;    // full path for zip file in VectorStock folder
    private String jpgFileForVSFullPath;    // full path for resized jpg in VectorStock folder


    StockFile(File eps, File jpg) {
        epsFile = eps;
        jpgFile = jpg;

        epsFileFullPath = epsFile.getAbsolutePath();
        jpgFileFullPath = jpgFile.getAbsolutePath();
        zipFileFullPath = jpgFileFullPath.replaceFirst(".jpg", ".zip");
        vsFolderFullPath = epsFile.getParent() + "\\" + VECTORSTOCKFOLDER;
        zipFileForVSFullPath = vsFolderFullPath + "\\" + jpgFile.getName().replaceFirst(".jpg", ".zip");
        jpgFileForVSFullPath = vsFolderFullPath + "\\" + jpgFile.getName();

    }


    public void createGeneralZip() {

        try {
            FileOutputStream fos = new FileOutputStream(zipFileFullPath);
            ZipOutputStream zos = new ZipOutputStream(fos);

            Zip.addToZipFile(epsFileFullPath, zos);
            Zip.addToZipFile(jpgFileFullPath, zos);

            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void packEpsforVS() {
        // create folder inside working if need
        if (!new File(vsFolderFullPath).isDirectory()) {
            System.out.println("Dirctory for Vector stock does not exist");
            new File(vsFolderFullPath).mkdir();
        }

        try {
            FileOutputStream fos = new FileOutputStream(zipFileForVSFullPath);
            ZipOutputStream zos = new ZipOutputStream(fos);

            Zip.addToZipFile(epsFileFullPath, zos);

            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void copyJpgForVS () {

        // copy image to new location
        try {
            Files.copy(new File(jpgFileFullPath).toPath(), new File(jpgFileForVSFullPath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // resize image
//        try {
//            System.out.println("Processing file:   " + jpgFile.getName());
//            ImageResizer.resize(jpgFileFullPath, jpgFileForVSFullPath, 400, 400);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

}
