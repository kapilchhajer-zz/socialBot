package Statistics;

import Conversation.ConversationTree;
import Conversation.Record;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Created by kapil on 20/08/14.
 * static annalysis of tweets with respect to number of user, conversation length and time interval
 */
public class Analysis {


    public Analysis(){

        ConversationTree.createTree();
    }


    public void participantAndConversationLengthAndTimeFreqAnalysis(){

            TreeMap<Integer , Integer> participantGraph = new TreeMap<Integer, Integer>();
            TreeMap<Integer , Integer> conversationLengthGraph = new TreeMap<Integer, Integer>();
            TreeMap<Long , Integer>  timeMap = new TreeMap<Long, Integer>();
            HashSet<Long>  stackItem = new HashSet<Long>();
            HashSet<Long>  userSet = new HashSet<Long>();
            Stack<Long> stk = new Stack<Long>();
            Long tId, userId, MinId, MaxId, startTime, endTime,conversationTime;
            int count, participants, conversation;
            Record recordObj;

            for(Long id : ConversationTree.conversationHead){

                   stk.push(id);
                   stackItem.add(id);
                   userSet.add(ConversationTree.tweetUserMap.get(id));
                   MinId = MaxId = id;
                    while(!stk.isEmpty()){

                        tId = stk.pop();

                        //System.out.println(" hash set " +ConversationTree.relation.get(tId));

                        if(!ConversationTree.relation.containsKey(tId))
                            continue;


                        for(Long child : ConversationTree.relation.get(tId)){

                            if(!stackItem.contains(child)){
                                stk.push(child);
                                stackItem.add(child);

                                if(MinId > child)
                                    MinId = child;

                                if(MaxId < child)
                                    MaxId = child;

                                userId = ConversationTree.tweetUserMap.get(child);

                                if(!userSet.contains(userId)){
                                    userSet.add(userId);
                                }

                            }
                        }

                    }

                    participants =  userSet.size();
                    conversation =  stackItem.size();

                   // System.out.println(participants);
                    if(participantGraph.containsKey(participants)){

                        count = 1 +  participantGraph.get(participants);
                        participantGraph.put(participants , count);
                    }
                    else{
                        participantGraph.put(participants , 1);
                    }

                    if(conversationLengthGraph.containsKey(conversation)){

                        count = 1 + conversationLengthGraph.get(conversation);
                        conversationLengthGraph.put(conversation , count);
                    }
                    else{

                        conversationLengthGraph.put(conversation , 1);
                    }
                    stk.clear();
                    stackItem.clear();
                    userSet.clear();

                    recordObj = ConversationTree.map.get(MinId);
                    startTime = recordObj.timeStamp.getTime();
                    recordObj = ConversationTree.map.get(MaxId);
                    endTime = recordObj.timeStamp.getTime();
                    conversationTime = (endTime - startTime) / 60000;

                    if(timeMap.containsKey(conversationTime)){
                        count = 1 + timeMap.get(conversationTime);
                        timeMap.put(conversationTime,count);
                    }
                    else{
                        timeMap.put(conversationTime , 1);
                    }

            }

        try {
            BufferedWriter bw3X = new BufferedWriter( new FileWriter("/home/kapil/IdeaProjects/Tweetapp/Analysis3X.txt"));
            BufferedWriter bw3Y = new BufferedWriter( new FileWriter("/home/kapil/IdeaProjects/Tweetapp/Analysis3Y.txt"));
            BufferedWriter bw2X = new BufferedWriter( new FileWriter("/home/kapil/IdeaProjects/Tweetapp/Analysis2X.txt"));
            BufferedWriter bw2Y = new BufferedWriter( new FileWriter("/home/kapil/IdeaProjects/Tweetapp/Analysis2Y.txt"));
            BufferedWriter bw1X = new BufferedWriter( new FileWriter("/home/kapil/IdeaProjects/Tweetapp/Analysis1X.txt"));
            BufferedWriter bw1Y = new BufferedWriter( new FileWriter("/home/kapil/IdeaProjects/Tweetapp/Analysis1Y.txt"));

            for(Integer id : participantGraph.keySet()){

                bw1X.write(id  + "\n");
                bw1Y.write(participantGraph.get(id) + "\n");
            }
            bw1X.close();
            bw1Y.close();
            int sum = 0;

            for(Integer id : conversationLengthGraph.keySet()){

                bw2X.write(id + "\n");
                bw2Y.write(conversationLengthGraph.get(id)+"\n");
                sum = sum + id * conversationLengthGraph.get(id);
            }

            bw2X.close();
            bw2Y.close();

            for(Long time : timeMap.keySet()){

                bw3X.write(time + "\n");
                bw3Y.write(timeMap.get(time)+"\n");
            }
            bw3X.close();
            bw3Y.close();
            System.out.println(" sum : "+ sum);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }



}
