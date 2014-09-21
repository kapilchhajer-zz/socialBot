package JsonParser;

import javafx.beans.binding.ObjectExpression;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;


/**
 * Created by kapil on 17/08/14.
 */
public class DumpParser {

    public void parse(){

            String line;
            TweetParser tweetParserObj = new TweetParser();
            int counter = 0;

        try {

            BufferedReader br = new BufferedReader( new FileReader("/home/kapil/IdeaProjects/Tweetapp/tweets_2014_07_31_00.txt")) ;
            BufferedWriter bw = new BufferedWriter(new FileWriter("/home/kapil/IdeaProjects/Tweetapp/tweet_small.txt"));


            while( (line = br.readLine() ) != null ) {

                if( line.isEmpty())
                       continue;

                tweetParserObj.tweetHandler( line , bw);
                counter = counter + 6;
            }

            System.out.println("total line " + counter);
            br.close();
            bw.close();

           // System.out.println("counter "+ counter);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
