package com.example.mevo.DataModels;

public class Room {

    private String roomNo;
    private String roomName;
    private String isAvailable;

    public Room(String RoomNo, String Available, String RoomName){
        this.isAvailable = Available;
        this.roomName = RoomName;
        this.roomNo = RoomNo;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getAvailable() {
        return isAvailable;
    }

    public void setAvailable(String available) {
        isAvailable = available;
    }

    public void setRoomNo(String roomNo) {
        roomNo = roomNo;
    }

    public void setRoomName(String roomName) {
        roomName = roomName;
    }
}
