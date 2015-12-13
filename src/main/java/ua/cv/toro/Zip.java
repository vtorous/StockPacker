/*
 * Copyright (C) 2015 Vasil Torous
 * distributed "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND. Use and be happy.
 */

package ua.cv.toro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {


    public static void addToZipFile(String fileName, ZipOutputStream zos) throws IOException {

        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
       // ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(new ZipEntry(file.getName()));

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }
}
