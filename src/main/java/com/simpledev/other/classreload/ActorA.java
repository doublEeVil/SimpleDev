package com.simpledev.other.classreload;

public class ActorA implements IActiontor {
    @Override
    public String action(String input) {
        System.out.println("---i am a, i rcv: " + input);
        return input;
    }
}
