package com.simpledev.other.classreload;

public class ActorA implements IActor {
    @Override
    public String action(String input) {
        System.out.println("---i am a, i   r cv: " + input);
        //say(input);
        return input;
    }

}
