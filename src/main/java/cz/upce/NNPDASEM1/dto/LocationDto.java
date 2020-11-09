package cz.upce.NNPDASEM1.dto;

import cz.upce.NNPDASEM1.domain.Location;
import lombok.Data;

@Data
public class LocationDto {
    private Long id;

    private String alias;

    private String city;

    private String street;

    private String buildingNumber;

    private UserDto user;

    public LocationDto() {
    }

    public LocationDto(Location location) {
        this.id = location.getLocationId();
        this.alias = location.getAlias();
        this.buildingNumber = location.getBuildingNumber();
        this.city = location.getCity();
        this.street = location.getStreet();
    }
}
