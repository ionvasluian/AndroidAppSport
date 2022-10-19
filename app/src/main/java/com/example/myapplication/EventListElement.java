package com.example.myapplication;

public class EventListElement {
    String category;
    String number_of_people;
    String event_name;

    public EventListElement(String category, String number_of_people, String event_name) {
        this.category = category;
        this.number_of_people = number_of_people;
        this.event_name = event_name;
    }


    public String getCategory() {
        return category;
    }

    public String getNumber_of_people() {
        return number_of_people;
    }

    public String getEvent_name() {
        return event_name;
    }
}
