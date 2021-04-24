package REST.Api.Basics.maturity.resources;

import REST.Api.Basics.maturity.swamp.Profile;
import REST.Api.Basics.maturity.util.DataFixtureUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/maturity/profiles")
public class ProfileService {

    @PostMapping
    public List<Profile> getAllProfiles() {
        return DataFixtureUtils.allProfiles();
    }

}
