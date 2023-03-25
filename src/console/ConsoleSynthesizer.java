package console;

import java.util.Scanner;

import static logger.CRBLogger.log;

public class ConsoleSynthesizer {
    private Scanner scanner = new Scanner(System.in);

    private void printUsage(){
        System.out.println("'quit' to quit");
        System.out.println("'help' to print this");
        System.out.println("'loaded instruments' to list loaded instruments");
    }
    private void startSession(){
        log("ConsoleSynthesizer.startSession()");
        String cmd;
        System.out.println("cmd: ");
        while(!(cmd = scanner.nextLine()).equalsIgnoreCase("quit")){
            switch (cmd){
                case "help":
                    printUsage();
                    break;
                case "loaded instruments":
                    listLoadedInstruments();
                    break;

            }
        }

    }

    private void listLoadedInstruments() {
    }
}
