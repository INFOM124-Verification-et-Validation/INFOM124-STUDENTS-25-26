package delft;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.*;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.*;
import static delft.Field.*;
import static delft.Property.*;
import static delft.SportsHallPlanner.planHalls;
import static org.junit.Assert.*;

class SportsHallPlannerTests {

    private Property property = NEAR_CITY_CENTRE;
    private Set<Property> setTest = new HashSet<>();
    private Field field = BADMINTON;
    private Map<Field, Integer> fields = new HashMap<>();

    @BeforeEach
    void setUp() {
        setTest.add(property);
        fields.put(field, 1);
    }

    @Test
    void requestConstructor() {
        int minNumberOfFields = 2;
        Request request = new Request(setTest, field, minNumberOfFields);
        Request request2 = new Request(setTest, field, minNumberOfFields);
        assertTrue(request.equals(request2));
    }

    @Test
    void canFulfillRequestCorrectTest() {
        SportsHall hall = new SportsHall(setTest, fields);
        Request request = new Request(setTest, field, 1);
        assertTrue(hall.canFulfillRequest(request));
    }

    @Test
    void planHallsCorrectTest() {
        List<Request> requests = new ArrayList<>();
        List<SportsHall> sportsHalls = new ArrayList<>();

        Request request = new Request(setTest, field, 1);
        SportsHall hall = new SportsHall(setTest, fields);

        requests.add(request);
        sportsHalls.add(hall);

        Map<SportsHall, Request> planHalls = SportsHallPlanner.planHalls(requests, sportsHalls);

        assertEquals(planHalls.get(hall), request);
    }

    @Test
    void planHallsWrongTest() {
        List<Request> requests = new ArrayList<>();
        List<SportsHall> sportsHalls = new ArrayList<>();

        Request request = new Request(setTest, field, 1);
        Request request2 = new Request(setTest, field, 5);
        SportsHall hall = new SportsHall(setTest, fields);

        requests.add(request);
        sportsHalls.add(hall);

        Map<SportsHall, Request> planHalls = SportsHallPlanner.planHalls(requests, sportsHalls);

        assertNotEquals(planHalls.get(hall), request2);
    }

    @Test
    void planHallsExceptionCheckText() {
        List<Request> requests = new ArrayList<>();
        List<SportsHall> sportsHalls = new ArrayList<>();

        Request request = new Request(setTest, field, 1);
        SportsHall hall = new SportsHall(setTest, fields);

        requests.add(request);
        sportsHalls.add(hall);
        sportsHalls.add(hall);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> SportsHallPlanner.planHalls(requests, sportsHalls));
        assertEquals("There should be no duplicate elements in the halls list.", exception.getMessage());

    }

    @Test
    void planHallsNullReturn() {
        List<Request> requests = new ArrayList<>();
        List<SportsHall> sportsHalls = new ArrayList<>();

        Request request = new Request(setTest, field, 1);
        SportsHall hall = new SportsHall(setTest, fields);

        requests.add(request);

        assertNull(SportsHallPlanner.planHalls(requests, sportsHalls));
    }

}
