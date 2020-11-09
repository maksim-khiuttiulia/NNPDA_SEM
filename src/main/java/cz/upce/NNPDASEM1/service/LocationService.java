package cz.upce.NNPDASEM1.service;

import cz.upce.NNPDASEM1.domain.Location;
import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.exceptions.ValidationException;
import cz.upce.NNPDASEM1.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getLocations(User user) {
        return locationRepository.findByUser(user);
    }

    public Location getLocation(Long id) {
        Location location = locationRepository.findById(id).orElse(null);
        if (location == null) {
            throw new ValidationException("Location not exists");
        }
        return location;
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }
}
