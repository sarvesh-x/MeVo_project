package com.example.mevo.DataModels;

public class Room {

    private int RoomNo;
    private String RoomName;
    private int Available;

    public Room(int RoomNo, int Available, String RoomName){
        this.Available = Available;
        this.RoomName = RoomName;
        this.RoomNo = RoomNo;
    }

    public int getRoomNo() {
        return RoomNo;
    }

    public String getRoomName() {
        return RoomName;
    }

    public int getAvailable() {
        return Available;
    }

    public void setAvailable(int available) {
        Available = available;
    }

    public void setRoomNo(int roomNo) {
        RoomNo = roomNo;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }
}
