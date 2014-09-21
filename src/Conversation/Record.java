package Conversation;

import com.sun.org.apache.regexp.internal.recompile;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Vector;

/**
 * Created by kapil on 19/08/14.
 *
 */

public class Record {

    public Long tweetId, inReplyToStatusId, userId;
    public String text;
    public Date  timeStamp = new Date();
   // public HashSet<String> textSet = new HashSet<String>();

    public Record(Date tStamp, Long tId, Long replyId, String text, Long userId){

        this.timeStamp = tStamp;
        this.tweetId = tId;
        this.inReplyToStatusId = replyId;
        this.text = text;
        this.userId = userId;


       // text = text.replaceAll("[^A-Za-z0-9]" , " ");
       // text = text.trim();
       /// if( !(text.isEmpty() || text == null)){


//            String []temp = text.split(" ");

  //          for(String i : temp){

    //            textSet.add(i);
      //      }

        }





    Record(){
        this.userId  = this.tweetId = this.inReplyToStatusId = 0L;
        this.text = null;

    }

}
