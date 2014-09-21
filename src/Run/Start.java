package Run;



import Conversation.ChatBot;
import Conversation.ConversationTree;
import JsonParser.*;
import Statistics.Analysis;


import java.util.Scanner;

/**
 *
 * Created by kapil on 19/08/14.
 */

public class Start {

    public static void main(String []args){



        Scanner scanner = new Scanner(System.in);

        int i;

        System.out.println("************************** Instruction **************************");
        System.out.println(" 1 : Create Intermediate Dump");
        System.out.println(" 2 : Print conversation Tree");
        System.out.println(" 3 : Get Analysis Files");
        System.out.println(" 4 : Social Bot");
        System.out.println("*****************************************************************");

        i = scanner.nextInt();

       /*
       *
       *   DumpParser obj creates an intermediate file that contains useful information only
       *
       * */

        if( i  == 1) {

            DumpParser dumpParserObj = new DumpParser();
            dumpParserObj.parse();   // call parse function of class dumpParser
        }
        else if( i == 2){

            ConversationTree.createTree();
           // ConversationTree.printConversationTree();
            ConversationTree.printit();
        }
        else if( i == 3) {

            Analysis analysisObj = new Analysis();  //Analysis object will run some analysis on Twitter Data
            analysisObj.participantAndConversationLengthAndTimeFreqAnalysis();

        }
        else if(i == 4){  //social Bot

            System.out.println("wait for few seconds System is going to ready for Chat ........");
            ConversationTree.createTree();
            ConversationTree.englishTweetFunction();

            ChatBot chatObj = new ChatBot();
            String input;
            Scanner scannerObj  = new Scanner(System.in);

            System.out.println(">>>>>>> SocialBot  <<<<<<<");

            while(true){
                System.out.print("human > ");
                input = scannerObj.nextLine();
                System.out.print("Robot > ");
                chatObj.chat(input);
            }
        }

    }
}
