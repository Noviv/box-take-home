package com.box.noviv;

import com.box.noviv.game.Minishogi;

public class Main {
    public static void main(String[] args) {
        Minishogi g = null;

        if (args.length == 0 || args[0] == "-i") {
            g = new Minishogi("res/default.in");
        } else {
            int fname = 0;
            if (args[fname] == "-f") {
                assert args.length > 1 : "-f requires second argument";
                fname++;
            }
            g = new Minishogi(args[fname]);
        }

        // start parsing from stdin
        g.prompt();
//        Scanner input = new Scanner(System.in);
//        while (g.isRunning()) {
//            g.prompt();
//            String[] line = input.nextLine().split(" ");
//            if (line.length < 3) {
//                System.out.println("failed");
//                return;
//            }
//            g.makeMove(line);
//        }
    }
}
