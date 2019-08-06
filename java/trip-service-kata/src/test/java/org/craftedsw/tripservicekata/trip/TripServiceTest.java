package org.craftedsw.tripservicekata.trip;

import org.assertj.core.api.WithAssertions;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

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

    @Test
    @DisplayName("Should not throw exception when user is available")
    public void DontThrowWhenLoggedInUserAvailable() {
        // given
        // mocked user session without logged in user
        User loggedUser = new User();
        BDDMockito
                .given(mockUserSession.getLoggedUser())
                .willReturn(loggedUser);

        // and
        User tripOwner = new User();

        // expect
        Assertions.assertDoesNotThrow(() -> tripService.getTripsByUser(tripOwner));
    }


}
