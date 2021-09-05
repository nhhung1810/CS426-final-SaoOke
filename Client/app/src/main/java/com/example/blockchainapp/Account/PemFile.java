package com.example.blockchainapp.Account;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.blockchainapp.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.security.Key;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

public class PemFile{

    private PemObject pemObject;

    public PemFile (Key key, String description) {
        this.pemObject = new PemObject(description, key.getEncoded());
    }

    public void write(Context context, String filename) throws FileNotFoundException, IOException {

        File folder = new File(context.getFilesDir(), Constants.RESOURCE_LOCATION);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File writeFile = new File(folder.getAbsolutePath() + "/" + filename);
        Log.d("File", writeFile.getAbsolutePath());

        FileOutputStream fout = new FileOutputStream(writeFile);
        PemWriter pemWriter = new PemWriter(new OutputStreamWriter(fout));
        try {
            pemWriter.writeObject(this.pemObject);
        } finally {
            pemWriter.flush();
            pemWriter.close();
            fout.flush();
            fout.close();
        }

    }
}
  