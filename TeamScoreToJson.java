/*
 * @@@replace.project@@@
 * システム名：@@@replace.sysname@@@
 * ファイル名：TeamScoreToJson.java
 *
 * -----------------------------------------------------------
 * 更新履歴
 * @@@replace.date@@@
 * -----------------------------------------------------------
 * @@@replace.copyright@@@
 */
package org.cainiaofei.score;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TeamScoreToJson {

    public static void main(String[] args) {

        try {

            // 設定ファイル場所
            // TODO propertiesファイルにてpathを定義
            String readfile = "/opt/runners/conf/2017Runners.csv";
            String writefile = "/opt/runners/conf/2017Runners.json";

            // CSVファイルを開く
            File csv = new File(readfile);
            BufferedReader br = new BufferedReader(new FileReader(csv));

            String tsJson = generateTeamScore(br);

            br.close();

            File jsonfile = new File(writefile);
            FileWriter fw = new FileWriter(jsonfile);
            fw.write(tsJson);
            fw.close();

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    /*
     * [ {
     * no: "1-70",
     * order: "1-70",
     * name: "队名",
     * result: "hh:mm:ss",
     * members: [{
     * no: "1/2/3/4/5",
     * duration: "hh:mm:ss",
     * pace: "mm:ss",
     * adjusted-pace: "mm:ss",
     * gender: "F/M",
     * memo: "修正内容"
     * }, ...]
     * }, ... ]
     */
    private static String generateTeamScore(BufferedReader br)
            throws IOException {

        String jsonStr = "[";

        String line = "";
        int n = 0;
        while ((line = br.readLine()) != null) {
            n++;

            //70チームなので, 70*5 + 3 より大きいの場合抜ける
            if (n > 70 * 5 + 3) {
                break;
            }

            // 先頭の3行をバイパス
            if (n > 3) {

                String[] st = line.split(",", 20);

                int mod = (n - 4) % 5;

                if (mod == 0) {
                    if (n > 4) {
                        jsonStr += ",";
                    }

                    jsonStr += " {";
                    jsonStr += toJsonStr("no") + ":";
                    jsonStr += toJsonStr(st[0]) + ",";
                    jsonStr += toJsonStr("order") + ":";
                    jsonStr += toJsonStr(st[3]) + ",";
                    jsonStr += toJsonStr("name") + ":";
                    jsonStr += toJsonStr(st[1]) + ",";
                    jsonStr += toJsonStr("result") + ":";
                    jsonStr += toJsonStr(st[5]) + ",";
                    jsonStr += toJsonStr("members") + ":[";
                }

                jsonStr += "{";
                jsonStr += toJsonStr("no") + ":";
                jsonStr += toJsonStr(st[7]) + ",";
                jsonStr += toJsonStr("duration") + ":";
                jsonStr += toJsonStr(st[10]) + ",";
                jsonStr += toJsonStr("pace") + ":";
                jsonStr += toJsonStr(st[12]) + ",";
                jsonStr += toJsonStr("adjusted-pace") + ":";
                jsonStr += toJsonStr(st[13]) + ",";
                jsonStr += toJsonStr("gender") + ":";
                jsonStr += toJsonStr(st[14]) + ",";
                jsonStr += toJsonStr("memo") + ":";
                jsonStr += toJsonStr(st[18]);
                jsonStr += "}";

                if (mod == 4) {
                    jsonStr += "]}";
                } else {
                    jsonStr += ",";
                }
            }
        }

        jsonStr += "]";

        return jsonStr;
    }

    private static String toJsonStr(String s) {
        return "\"" + s + "\"";
    }

}
