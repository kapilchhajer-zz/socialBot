package Conversation;

import java.util.HashSet;
import java.util.TreeMap;

/**
 * Created by kapil on 21/08/14.
 */
public class ChatBot {

    public HashSet<String> tokens(String input){

        input = input.replaceAll("[^A-Za-z0-9]" , " ");
        String []inputTokens = input.split(" ");
        HashSet<String> set = new HashSet<String>();

        for(String str : inputTokens){
            set.add(str);
        }

        return set;
    }


    public  void chat(String input){

           HashSet<String>  inputSet;
           HashSet<String>  intermediateSet;
           HashSet<String>  union = new HashSet<String>();
           HashSet<String>  intersection = new HashSet<String>();
           int intersectionSize , unionSize;
           float  jacardCoefficient, maxCoefficient = 0F;
           Long   maxId = 0L;
           inputSet = tokens(input);


           for(Long tweetId : ConversationTree.englishTweet.keySet()){


               intermediateSet =  ConversationTree.englishTweet.get(tweetId);
               union.addAll(intermediateSet);
               union.addAll(inputSet);
               intersection.addAll(inputSet);
               intersection.retainAll(intermediateSet);

               intersectionSize = intersection.size();
               unionSize = union.size();

               jacardCoefficient =  ((float)intersectionSize / unionSize) * 100;

               if(jacardCoefficient > maxCoefficient  && ConversationTree.relation.containsKey(tweetId) ){

                    for(Long id : ConversationTree.relation.get(tweetId))

                        if(ConversationTree.englishTweet.containsKey(id)) {
                            maxCoefficient = jacardCoefficient;
                            maxId = tweetId;
                            break;
                        }
               }

               union.clear();
               intersection.clear();
               //System.out.println(" tweetId" + tweetId + "jacard "+jacardCoefficient);

           }

            if(maxId == 0L)
                   System.out.println("Ok ");
            else {


                   ConversationTree.printChildTweetText(maxId);

            }
    }

}
