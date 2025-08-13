package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.entity.Rental;

@Builder
public record RentalDto(
     String name,
     Integer rentalTime,
     Integer originalPrice,
     Integer discountedPrice
) {

    public static RentalDto create(Rental rental) {
        return RentalDto.builder()
            .name(rental.getName())
            .rentalTime(rental.getRentalTime())
            .originalPrice(rental.getOriginalPrice())
            .discountedPrice(rental.getDiscountedPrice())
            .build();
    }
}
