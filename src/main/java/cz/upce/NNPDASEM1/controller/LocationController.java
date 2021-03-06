package cz.upce.NNPDASEM1.controller;

import cz.upce.NNPDASEM1.domain.Location;
import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.dto.LocationDto;
import cz.upce.NNPDASEM1.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/locations")
public class LocationController extends ControllerAncestor {

    @Autowired
    private LocationService locationService;

    @RequestMapping(method = RequestMethod.GET)
    public List<LocationDto> getLocations() {
        User user = getCurrentUser();
        List<Location> locations = locationService.getLocations(user);
        return locations.stream().map(LocationDto::new).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public LocationDto createLocation(@RequestBody LocationDto locationDto) {
        User user = getCurrentUser();
        Location location = new Location();
        location.setAlias(locationDto.getAlias());
        location.setBuildingNumber(locationDto.getBuildingNumber());
        location.setCity(locationDto.getCity());
        location.setStreet(locationDto.getStreet());
        location.setUser(user);
        location = locationService.createLocation(location);
        return new LocationDto(location);
    }
}
