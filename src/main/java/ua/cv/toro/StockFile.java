/*
 * Copyright (C) 2015 Vasil Torous
 * distributed "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND. Use and be happy.
 */
package ua.cv.toro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;


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

    @Override
    public String toString() {
        return epsFile.getName().replaceFirst(".eps", "");
    }

    /** Returns the list of pair jpg and eps file. */
    public static List<StockFile> getStockFilesList (File workDirectory) {
        // Get list of all .jpg file
        List<File> jpgFiles = new ArrayList<>();
        File[] files = workDirectory.listFiles();

        for (File file : files) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".jpg")) {
                jpgFiles.add(file);
                // System.out.println(file.getAbsolutePath());
            }
        }

        // Clear list of jpg file. Leave only if eps is presented and zip is
        // not presented

        List<StockFile> stockFileToProcess = new ArrayList<>();
        for (File file : jpgFiles) {
            File zipFile = new File(file.getAbsolutePath().replaceFirst(".jpg", ".zip"));
            File epsFile = new File(file.getAbsolutePath().replaceFirst(".jpg", ".eps"));
            if (!zipFile.isFile() && epsFile.isFile()) {
                stockFileToProcess.add(new StockFile(epsFile, file));
                // System.out.println(file.getAbsolutePath());
            }
        }
        return stockFileToProcess;
    }

    /** Create zip archive with same name as jpg and eps file inside. */
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

    /** Create folder for VectorStock if not exist. Copy jpt and zrchive eps into VectorStock folder. */
    public void prepareforVS() {
        // create folder inside working if need
        if (!new File(vsFolderFullPath).isDirectory()) {
            System.out.print("The dirctory for Vector stock files does not exist. Creating \"" + VECTORSTOCKFOLDER + "\" folder");
            new File(vsFolderFullPath).mkdir();
        }

        // copy image to the VectorStock folder
        try {
            Files.copy(new File(jpgFileFullPath).toPath(), new File(jpgFileForVSFullPath).toPath());
        }
            catch (IOException e) {
            e.printStackTrace();
        }

        // archived eps file to the VectorStock folder
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

}
