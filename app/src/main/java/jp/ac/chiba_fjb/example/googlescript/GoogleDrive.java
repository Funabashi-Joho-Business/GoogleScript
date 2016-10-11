package jp.ac.chiba_fjb.example.googlescript;

import android.content.Context;
import android.support.v4.app.FragmentActivity;



import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.About;

import java.io.IOException;


/**
 * Created by oikawa on 2016/09/26.
 */

public class GoogleDrive extends GoogleAccount {


    private Drive mDrive;
    Context mActivity;
    public GoogleDrive(Context con){
        super(con,null);

        FragmentActivity activity = null;
        if(con instanceof FragmentActivity)
            activity = (FragmentActivity)con;
        mActivity = con;

        mDrive = new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), getCredential()).build();
    }

    boolean connect(){
        requestAccount();


        return true;
    }

    public void disconnect() {

    }

    @Override
    protected void onExec() throws IOException {

            About about = mDrive.about().get().execute();
            //System.out.println(about.getRootFolderId());

    }
}
