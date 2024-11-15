package com.bvcott.booking.model.hotel;

public enum HotelRoomType {
    SINGLE("Single", "A room with one single bed, suitable for one person.", 1),
    DOUBLE("Double", "A room with one double bed or two single beds, suitable for two people.", 2),
    SUITE("Suite", "A luxurious room with additional living space and amenities, suitable for up to four people.", 4),
    FAMILY("Family", "A room designed for families, with beds for up to six people.", 6),
    DELUXE("Deluxe", "A premium room with luxurious amenities, suitable for two people.", 2);

    private final String hotelRoomType;
    private final String description;
    private final int numberOfGuests;

    HotelRoomType(String hotelRoomType, String description, int numberOfGuests) {
        this.hotelRoomType = hotelRoomType;
        this.description = description;
        this.numberOfGuests = numberOfGuests;
    }

    public String getHotelRoomType() {
        return hotelRoomType;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
