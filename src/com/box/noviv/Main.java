package com.box.noviv;

import com.box.noviv.game.Minishogi;

public class Main {
    public static void main(String[] args) {
        Minishogi g;

        if (args.length > 0 && args[0].equals("-f")) {
            assert args.length > 1 : "-f requires a second argument";
            g = new Minishogi(args[1]);
        } else {
            g = new Minishogi("res/default.in");
        }

        // start parsing from stdin
    }
}
