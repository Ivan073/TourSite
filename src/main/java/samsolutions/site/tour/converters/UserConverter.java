package samsolutions.site.tour.converters;

import samsolutions.site.tour.dtos.SignupRequest;
import samsolutions.site.tour.dtos.TourDTO;
import samsolutions.site.tour.dtos.UserProfile;
import samsolutions.site.tour.entities.Tour;
import samsolutions.site.tour.entities.UserData;

public class UserConverter {

    public static UserData convertToEntity(SignupRequest signupRequest) {
        UserData user = new UserData();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        user.setRole(signupRequest.getRole());
        user.setMobile(signupRequest.getMobile());
        return user;
    }

    public static UserProfile convertToProfile(UserData user){
        UserProfile profile = new UserProfile();
        profile.setEmail(user.getEmail());
        profile.setUsername(user.getUsername());
        profile.setMobile(user.getMobile());
        return profile;
    }
}
