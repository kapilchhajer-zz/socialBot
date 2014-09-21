package Conversation;



import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kapil on 19/08/14.
 *
 * Conversation Tree
 */
public class ConversationTree {

    public static TreeMap<Long,Record> map = new TreeMap<Long, Record>();
    public static HashSet<Long> conversationHead = new HashSet<Long>();
    public static HashMap<Long,HashSet<Long>> relation = new HashMap<Long, HashSet<Long>>();
    public static HashMap<Long, Long> tweetUserMap = new HashMap<Long, Long>();
    public static HashMap<Long , HashSet<String>>  englishTweet = new HashMap<Long, HashSet<String>>();
    public static  Record recordObj;
    public  static BufferedWriter bufferWriter , f;


        public  static  void printChildTweetText(Long tweetId){

                HashSet<Long> child  = relation.get(tweetId);
                boolean flag = false;


                while(true){

                    int num= (int ) Math.random() * child.size();
                    Long id =  (Long) child.toArray()[num];

                    if(englishTweet.containsKey(id) && englishTweet.get(id).size() > 0){

                           for(String str : englishTweet.get(id)){

                               System.out.print(" " + str);
                           }

                           flag = true;
                           System.out.println();
                           break;
                    }
                }



        }
/////
    public static void printMyChildren(Long tweetId)
    {

        try {

            f.write(map.get(tweetId).text + "\n");

            if(!relation.containsKey(tweetId))
                return;

            long len = relation.get(tweetId).size();
            if(len == 0)
                return;


            for(Long temp :relation.get(tweetId))
            {
                printMyChildren(temp);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printit(){

            try {
                 f = new BufferedWriter(new FileWriter("/home/kapil/top.txt"));
                int count = 0;

                for(Long id : conversationHead){

                    if(count < 300){

                        f.write("Conversation Head  "+ id + "\n");
                        printMyChildren(id);
                        f.write("\n");
                        f.write("\n");
                        f.write("\n");
                        count++;
                    }
                    else{


                        break;
                    }

                }
                f.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public static void createTreeUtil(){

            HashSet<Long> deleteNode = new HashSet<Long>();
            HashSet<Long> relSet;

            //int headNullCounter = 0, convHeadCounter = 0, notConversation = 0;


            for(Long id : map.keySet()){

                   recordObj = map.get(id);
                   Long  replyStatusId = recordObj.inReplyToStatusId;

                if( map.containsKey(replyStatusId)){

                       if(!conversationHead.contains(replyStatusId)) {
                           conversationHead.add(replyStatusId);
                           //convHeadCounter++;
                       }

                       if(relation.containsKey(replyStatusId)){
                           relSet = relation.get(replyStatusId);
                           relSet.add(id);
                       }
                       else{
                           relSet = new HashSet<Long>();
                           relSet.add(id);
                           relation.put(replyStatusId, relSet);
                       }
                   }
                  /* else{

                       //headNullCounter++;
                   }*/
            }


            for(Long id : conversationHead){

                recordObj = map.get(id);
                Long  replyStatusId = recordObj.inReplyToStatusId;

                if( replyStatusId != 0){

                    if (conversationHead.contains(replyStatusId)) {
                        deleteNode.add(id);
                        //convHeadCounter--;
                    }
                }

            }

              // System.out.println("parent out side data set :  counter "+ headNullCounter);
              // System.out.println("number of conversation "+convHeadCounter);


            for(Long id : deleteNode){

                conversationHead.remove(id);
            }
        }

       public static void printChildren(Long tweetId)
        {

            try {

                bufferWriter.write(tweetId.toString());

                if(!relation.containsKey(tweetId))
                    return;

                long len = relation.get(tweetId).size();
                if(len == 0)
                    return;
                bufferWriter.write("(");

                for(Long temp :relation.get(tweetId))
                {
                    printChildren(temp);
                    if(len > 1)
                        bufferWriter.write(",");
                    len--;
                }
                bufferWriter.write(")");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void printConversationTree(){

            try {

                    bufferWriter =  new BufferedWriter( new FileWriter("/home/kapil/IdeaProjects/Tweetapp/conversationTree.txt"));

                    for(Long tweetId:conversationHead)
                    {
                        bufferWriter.write("(");
                        printChildren(tweetId);
                        bufferWriter.write(")");
                        bufferWriter.write("\n");
                    }
                    bufferWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public static HashSet<String>  tokenSet(String input){


                input = input.replaceAll("[^A-Za-z0-9]" , " ");
                input = input.trim();
                String []inputTokens = input.split(" ");
                HashSet<String> set = new HashSet<String>();

                for(String str : inputTokens){
                    set.add(str);
                }

                return set;
        }

        public static String getProcessedTweet(String tweetText) {

            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;

            tweetText = tweetText.trim();

            if(tweetText.charAt(0) == 'R' && tweetText.charAt(1) == 'T' ) {
                i = 2;
            }

            for(  ; i < tweetText.length() ; i++ ) {

                char ch = tweetText.charAt(i);

                if( ch == '@' || ch == '#' ) {

                    i++;

                    if( i == tweetText.length() )
                        break;

                    char c = tweetText.charAt(i);

                    while( Character.isLetterOrDigit(c) || c == '_' ) {
                        i++;

                        if( i == tweetText.length() )
                            break;

                        c = tweetText.charAt(i);

                    }

                }
                else if( tweetText.substring(i).startsWith("http://") || tweetText.substring(i).startsWith("https://") ) {

                    char c = tweetText.charAt(i);

                    while( c != ' ' ) {
                        i++;

                        if( i == tweetText.length() )
                            break;

                        c = tweetText.charAt(i);

                    }

                }
                else if( !( ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || Character.isDigit(ch) ) || ch == ':' || ch == ';' || ch == '(' || ch == ')' || ch == ',' || ch == ' ') ) {

                }
                else {
                    stringBuilder.append(ch);
                }

            }

            return new String(stringBuilder).replaceAll("  " , " ").trim().toLowerCase();
        }

        public static boolean isEnglish(String text) {

            if( text.length() > 0 )
                return true;

            return false;
        }


        public static void englishTweetFunction(){

                Stack<Long>  stk = new Stack<Long>();
                HashSet<String> parsedSet;
                Long tweetId;

                int converCount = 0 , total = 0;
                for( Long id : conversationHead) {
                    stk.push(id);
                    converCount++;
                }
                int count = 0;
                while(!stk.isEmpty()){

                        tweetId = stk.pop();
                        total++;

                        String tweetText = map.get(tweetId).text;
                        tweetText = getProcessedTweet(tweetText);

                        if( isEnglish(tweetText)){

                            //System.out.println(tweetText);
                            parsedSet = tokenSet(tweetText);

                            englishTweet.put(tweetId , parsedSet);
                            count++;
                        }


                        if(relation.containsKey(tweetId)){

                            for( Long id : relation.get(tweetId)){

                                stk.push(id);
                            }
                        }
                }

            System.out.println("Total English tweets : " + count + "Head "+ converCount + "  total node "+ total);
        }


        public  static void  createTree(){

            String  timeStamp, text;
            Long tweetId, inReplyToStatusId, userId;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
            simpleDateFormat.setLenient(true);
            Date timeObj;


            try {

                BufferedReader br = new BufferedReader( new FileReader("/home/kapil/IdeaProjects/Tweetapp/tweet_small.txt")) ;
                String line;

                while((line = br.readLine()) != null) {

                    line = line.substring(1);

                    if(line.isEmpty()) {
                        tweetId = 0L;
                    }
                    else
                        tweetId = Long.parseLong(line);

                    timeStamp = br.readLine().substring(1).trim();
                    timeObj = simpleDateFormat.parse(timeStamp);
                    inReplyToStatusId = Long.parseLong(br.readLine().substring(1));
                    userId = Long.parseLong(br.readLine().substring(1));
                    text = br.readLine().substring(1).trim();

                    Record recordObj;

                    tweetUserMap.put(tweetId, userId);
                    recordObj = new Record(timeObj, tweetId, inReplyToStatusId, text, userId);
                    map.put(recordObj.tweetId, recordObj);

                    br.readLine();

                }

                br.close();
                createTreeUtil();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
}
