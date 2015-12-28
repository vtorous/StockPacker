/*
 * Copyright (C) 2015 Vasil Torous
 * distributed "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND. USE AND BE HAPPY.
 */

package ua.cv.toro;

import java.io.File;
import java.util.List;



// Main class ---

public class Main {

    public static void main(String[] args) {
        // --------------------------------------------------------------------
        // Check the folders in the parameters
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

        // Get list of all .jpg file
        List<StockFile> stockFileToProcess = StockFile.getStockFilesList(workDirectory);

        // Process all files. Create archives....
        for (StockFile file : stockFileToProcess) {

            System.out.print("Processing: (" + file + ") ");
            file.createGeneralZip();
            file.prepareforVS();
            System.out.println("... Ok.");

        }
    }
}