package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

    private final UserSession userSession;

    private final Function<User, List<Trip>> tripFinder;

    public TripService() {
        userSession = UserSession.getInstance();
        tripFinder = TripDAO::findTripsByUser;
    }

    public TripService(UserSession userSession,
                       Function<User, List<Trip>> tripFinder) {
        this.userSession = userSession;
        this.tripFinder = tripFinder;
    }

    public List<Trip> getTripsByUser(User tripOwner) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<Trip>();
        boolean isFriend = false;
        User loggedUser = userSession.getLoggedUser();
        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        }
        for (User friend : tripOwner.getFriends()) {
            if (friend.equals(loggedUser)) {
                isFriend = true;
                break;
            }
        }
        if (isFriend) {
            tripList = tripFinder.apply(tripOwner);
        }
        return tripList;
    }

}
