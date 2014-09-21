package JsonParser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by kapil on 19/08/14.
 */
public class TweetParser {

    String timeStamp, text;
    Long tweetId, inReplyToStatusId, userId;

    void tweetHandler( String tweet , BufferedWriter bw) {


            JSONParser  parser = new JSONParser();
            JSONObject  jsonObj , jsonDataObj;
            jsonObj = new JSONObject();
            jsonDataObj = new JSONObject();


        try {
            jsonObj = (JSONObject)  parser.parse(tweet);
            jsonDataObj = (JSONObject)(parser.parse( jsonObj.get("Data").toString()));

            timeStamp = (String) jsonDataObj.get("CreatedAt");
            tweetId = (Long) jsonDataObj.get("Id");
            inReplyToStatusId = (Long) jsonDataObj.get("InReplyToStatusId");
            text = (String) jsonDataObj.get("Text");
            userId = (Long) ((JSONObject) jsonDataObj.get("User")).get("Id");


            if( !(text == null || text.isEmpty())){

                bw.write(">" + tweetId + "\n");
                bw.write(">" + timeStamp + "\n");
                bw.write(">" + inReplyToStatusId + "\n");
                bw.write(">" + userId + "\n");
                text = text.trim().replaceAll("\n", " ");
                text = text.replaceAll("\r" , " ");
                bw.write(">" + text+ "\n");
                bw.write("<\n");

            }



        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
