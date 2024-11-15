package com.bvcott.booking.model.hotel;

public enum Facility {
    WIFI("Free Wi-Fi"),
    POOL("Swimming Pool"),
    GYM("Fitness Center"),
    SPA("Spa"),
    PARKING("Free Parking"),
    RESTAURANT("On-site Restaurant"),
    BAR("Bar"),
    AIR_CONDITIONING("Air Conditioning"),
    ROOM_SERVICE("Room Service"),
    PET_FRIENDLY("Pet Friendly"),
    BUSINESS_CENTER("Business Center"),
    AIRPORT_SHUTTLE("Airport Shuttle"),
    NON_SMOKING_ROOMS("Non-Smoking Rooms"),
    LAUNDRY("Laundry Service"),
    KITCHEN("Kitchen Facilities"),
    BREAKFAST_INCLUDED("Breakfast Included"),
    TV("Flat-Screen TV"),
    MINI_BAR("Mini-Bar"),
    CHILDREN_FACILITIES("Facilities for Children");

    private final String displayName;

    Facility(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
