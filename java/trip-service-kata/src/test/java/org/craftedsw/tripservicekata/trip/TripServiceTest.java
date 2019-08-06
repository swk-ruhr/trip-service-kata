package org.craftedsw.tripservicekata.trip;

import org.assertj.core.api.WithAssertions;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;

@MockitoSettings
public class TripServiceTest implements WithAssertions {

    @InjectMocks
    TripService tripService;
    @Mock
    UserSession mockUserSession;

    @Test
    @DisplayName("Should throw exception when user is not available")
    public void ThrowWhenLoggedInUserNotAvailable() {
        // given
        // mocked user session without logged in user
        BDDMockito
                .given(mockUserSession.getLoggedUser())
                .willReturn(null);

        // and
        User tripOwner = null;

        // expect
        Assertions.assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(tripOwner));
    }

    @Nested
    class WithLoggedInUser {

        private User loggedUser;

        @BeforeEach
        void setUp() {
            loggedUser = new User();
            BDDMockito
                    .given(mockUserSession.getLoggedUser())
                    .willReturn(loggedUser);
        }

        @Test
        @DisplayName("If trip owner has no friends, returns empty trip list")
        public void emptyTripList() {
            // given
            User tripOwner = new User();

            // when
            List<Trip> trips = tripService.getTripsByUser(tripOwner);

            //then
            assertThat(trips).isEmpty();
        }

        @Test
        @DisplayName("If trip owner is no friend of logged user, returns empty trip list")
        public void tripListEmptyIfUserNoFriendOfOwner() {
            // given
            User tripOwner = new User();

            // and
            User tripOwnerFriend = new User();
            tripOwner.addFriend(tripOwnerFriend);

            // when
            List<Trip> trips = tripService.getTripsByUser(tripOwner);

            //then
            assertThat(trips).isEmpty();
        }
    }
}
