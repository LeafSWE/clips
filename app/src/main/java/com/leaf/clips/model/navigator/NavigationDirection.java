package com.leaf.clips.model.navigator;

//// TODO: 25/05/16 aggiungere tracy
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public enum  NavigationDirection{
    STRAIGHT, LEFT, RIGHT, STAIR_UP, STAIR_DOWN, ELEVATOR_UP, ELEVATOR_DOWN, TURN, DESTINATION;
    public static NavigationDirection fromInt(int i){
        switch (i){
            case 0:
                return STRAIGHT;
            case 1:
                return LEFT;
            case 2:
                return RIGHT;
            case 3:
                return STAIR_UP;
            case 4:
                return STAIR_DOWN;
            case 5:
                return ELEVATOR_UP;
            case 6:
                return ELEVATOR_DOWN;
            case 7:
                return TURN;
            case 8:
                return DESTINATION;
        }
        return STRAIGHT;
    }
    public static int toInt(NavigationDirection navigationDirection){
        switch (navigationDirection){
            case STRAIGHT:
                return 0;
            case LEFT:
                return 1;
            case RIGHT:
                return 2;
            case STAIR_UP:
                return 3;
            case STAIR_DOWN:
                return 4;
            case ELEVATOR_UP:
                return 5;
            case ELEVATOR_DOWN:
                return 6;
            case TURN:
                return 7;
            case DESTINATION:
                return 8;
        }
        return -1;
    }
}