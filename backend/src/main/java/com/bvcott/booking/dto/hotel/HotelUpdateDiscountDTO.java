package com.bvcott.booking.dto.hotel;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data @Builder @Jacksonized
public class HotelUpdateDiscountDTO {
	private double discount;

}
