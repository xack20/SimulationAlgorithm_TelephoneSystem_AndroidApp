package com.example.telephonesimulation;


class Calls{
    private int to,from,arrival=0,end,length=0;

    Calls(int to, int from, int length, int arrival){
        this.to=to;
        this.from=from;
        this.length=length;
        this.arrival=arrival;
        this.end=end_time();
    }


    Calls(int to, int from, int end){
        this.to=to;
        this.from=from;
        this.end=end;
    }

    private int end_time(){
        return this.arrival+this.length;
    }

    int getTo() {
        return to;
    }

    int getFrom() {
        return from;
    }

    int getArrival() {
        return arrival;
    }

    int getEnd() {
        return end;
    }


    @Override
    public String toString() {
        return  to +
                "#" + from +
                "#" + length +
                "#" + arrival +
                "#";
    }
}
