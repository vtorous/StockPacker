package ua.cv.toro;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



// Main class ---

public class Main {

    public static void main(String[] args) {
        // --------------------------------------------------------------------
        // Check to user provide path as argument to program
        // If no parameter use current directory
        // --------------------------------------------------------------------
        File workDirectory;

        if (args.length == 0) {
            workDirectory = new File("");
        } else {
            workDirectory = new File(args[0]);
            if (!workDirectory.isDirectory()) {
                System.out.println("This is not a directory");
                System.exit(1);
            }
        }
        // --------------------------------------------------------------------
        // Get list of all .jpg file
        // --------------------------------------------------------------------
        List<File> jpgFiles = new ArrayList<>();
        File[] files = workDirectory.listFiles();

        //
        for (File file : files) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".jpg")) {
                jpgFiles.add(file);
                // System.out.println(file.getAbsolutePath());
            }
        }

        System.out.println(workDirectory.getAbsolutePath());

        // --------------------------------------------------------------------
        // Clear list of jpg file. Leave only if eps is presented and zip is
        // not presented
        // --------------------------------------------------------------------

        List<StockFile> stockFileToProcess = new ArrayList<>();
        for (File file : jpgFiles) {
            File zipFile = new File(file.getAbsolutePath().replaceFirst(".jpg", ".zip"));
            File epsFile = new File(file.getAbsolutePath().replaceFirst(".jpg", ".eps"));
            if (!zipFile.isFile() && epsFile.isFile()) {
                stockFileToProcess.add(new StockFile(epsFile, file));
                // System.out.println(file.getAbsolutePath());
            }
        }

        // --------------------------------------------------------------------
        // Process all files. Create archives....
        // --------------------------------------------------------------------

        for (StockFile file : stockFileToProcess) {

            System.out.println("Compressing general zip file");
            file.createGeneralZip();

            System.out.println("Compressing eps file to zip to VectorStock folder");
            file.packEpsforVS();

            System.out.println("Comping  jpg to VectorStock folder");
            file.copyJpgForVS();

        }
    }
}