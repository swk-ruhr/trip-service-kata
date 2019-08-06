package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

    private final UserSession userSession;

    public TripService() {
        userSession = UserSession.getInstance();
    }

    public TripService(UserSession userSession) {
        this.userSession = userSession;
    }

    public List<Trip> getTripsByUser(User tripOwner) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<Trip>();
        boolean isFriend = false;
        User loggedUser = getLoggedUser();
        for (User friend : tripOwner.getFriends()) {
            if (friend.equals(loggedUser)) {
                isFriend = true;
                break;
            }
        }
        if (isFriend) {
            tripList = TripDAO.findTripsByUser(tripOwner);
        }
        return tripList;
    }

    private User getLoggedUser() {
        User loggedUser = userSession.getLoggedUser();
        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        }
        return loggedUser;
    }

}
