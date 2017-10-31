package jp.ac.chiba_fjb.example.googlescript;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.api.services.script.model.Operation;

import java.util.ArrayList;
import java.util.List;

/*
//GASのソース作成後、公開から実行可能API(全員)を選ぶ
//リソース Cloud Platformプロジェクトから以下を設定
// APIとサービス
//    Google Apps Script Execution APIを有効
//    認証でAPIキーを作成
//    認証でウイザードAndroid、フィンガーコードを登録

//スクリプト側(指定したフォルダを作成する)
function Main(name) {
   DriveApp.getRootFolder().createFolder(name);
   return name+"作成完了";
}
 */

public class MainActivity extends AppCompatActivity {

    private GoogleScript mGoogleScript;
    private GoogleDrive mDrive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //キー登録用SHA1の出力(いらなければ消す)
        Log.d("フィンガーコード",AppFinger.getSha1(this));

//        mDrive = new GoogleDrive(this);
//        mDrive.setOnConnectedListener(new GoogleDrive.OnConnectListener() {
//            @Override
//            public void onConnected(boolean flag) {
//                if(flag)
//                    mDrive.createFolder(mDrive.getRootId(),"ふぉっふぉっふぉ");
//            }
//        });
//        mDrive.connect();

        //Scriptで必要な権限を記述する
        final String[] SCOPES = {
                "https://www.googleapis.com/auth/drive",
                "https://www.googleapis.com/auth/script.storage",
                "https://www.googleapis.com/auth/spreadsheets"};

        mGoogleScript = new GoogleScript(this,SCOPES);
        //強制的にアカウントを切り替える場合
        //mGoogleScript.resetAccount();

        //送信パラメータ
        List<Object> params = new ArrayList<>();
        params.add("あいうえお");

        //ID,ファンクション名,結果コールバック
        mGoogleScript.execute("McRDijj_Z5jVmvXZvwNDH8_36b1jVoKuO", "AIzaSyBTrJ83xMMO50IbH1aiDebaSRtFdfKQKmY","Main",
                params, new GoogleScript.ScriptListener() {
                    @Override
                    public void onExecuted(GoogleScript script, Operation op) {
                        TextView textView = (TextView) findViewById(R.id.textMessage);

                        if(op == null || op.getError() != null)
                            textView.append("Script結果:エラー\n");
                        else {
                            //戻ってくる型は、スクリプト側の記述によって変わる
                            String s = (String) op.getResponse().get("result");
                            textView.append("Script結果:"+ s+"\n");
                        }
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //必要に応じてアカウントや権限ダイアログの表示
        //mDrive.onActivityResult(requestCode,resultCode,data);
        mGoogleScript.onActivityResult(requestCode,resultCode,data);

    }
}
