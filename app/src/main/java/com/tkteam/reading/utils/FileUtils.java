package com.tkteam.reading.utils;

import android.content.Context;
import android.webkit.MimeTypeMap;
import org.apache.http.util.ByteArrayBuffer;

import java.io.*;
import java.security.MessageDigest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * User: khiemvx
 * Date: 6/16/14
 */
public class FileUtils
{
    public static final int BUFFER_SIZE = 8192;

    public static String readRawFileAsString(Context context, int resourceRawTextId) throws IOException
    {
        InputStream is = context.getResources().openRawResource(resourceRawTextId);
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayBuffer baf = new ByteArrayBuffer(256);

        int current = 0;
        while ((current = bis.read()) != -1)
        {
            baf.append((byte) current);
        }

        byte[] myData = baf.toByteArray();
        return new String(myData);
    }

    public static void unzip(String zipFile, String location) throws IOException
    {
        int size;
        byte[] buffer = new byte[BUFFER_SIZE];

        try
        {
            if (!location.endsWith("/"))
            {
                location += "/";
            }
            File f = new File(location);
            if (!f.isDirectory())
            {
                f.mkdirs();
            }
            ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE));
            try
            {
                ZipEntry ze = null;
                while ((ze = zin.getNextEntry()) != null)
                {
                    String path = location + ze.getName();
                    File unzipFile = new File(path);

                    if (ze.isDirectory())
                    {
                        if (!unzipFile.isDirectory())
                        {
                            unzipFile.mkdirs();
                        }
                    }
                    else
                    {
                        // check for and create parent directories if they don't exist
                        File parentDir = unzipFile.getParentFile();
                        if (null != parentDir)
                        {
                            if (!parentDir.isDirectory())
                            {
                                parentDir.mkdirs();
                            }
                        }

                        // unzip the file
                        FileOutputStream out = new FileOutputStream(unzipFile, false);
                        BufferedOutputStream fout = new BufferedOutputStream(out, BUFFER_SIZE);
                        try
                        {
                            while ((size = zin.read(buffer, 0, BUFFER_SIZE)) != -1)
                            {
                                fout.write(buffer, 0, size);
                            }

                            zin.closeEntry();
                        }
                        finally
                        {
                            fout.flush();
                            fout.close();
                        }
                    }
                }
            }
            finally
            {
                zin.close();
            }
        }
        catch (Exception e)
        {
        }
    }

    public static String getMd5String(String datafile) throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        FileInputStream fis = new FileInputStream(datafile);
        byte[] dataBytes = new byte[1024];
        int nRead;
        while ((nRead = fis.read(dataBytes)) != -1)
        {
            md.update(dataBytes, 0, nRead);
        }

        byte[] mdBytes = md.digest();

        //convert the byte to hex format
        StringBuffer md5Buffer = new StringBuffer("");
        for (byte mdByte : mdBytes)
        {
            md5Buffer.append(Integer.toString((mdByte & 0xff) + 0x100, 16).substring(1));
        }
        fis.close();
        return md5Buffer.toString();
    }

    public static String getContentFileText(File file) throws IOException
    {
        StringBuilder text = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        try
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                text.append(line);
                text.append('\n');
            }
        }
        catch (IOException e)
        {
        }

        return text.toString();
    }

    public static String getTypeOfFile(String url)
    {
        return url.substring(url.lastIndexOf(".") + 1);
    }

    public static String getMediaType(String filePath)
    {
        String extension = getTypeOfFile(filePath).toLowerCase();
        MimeTypeMap myMime = MimeTypeMap.getSingleton();
        return myMime.getMimeTypeFromExtension(extension);
    }
}

