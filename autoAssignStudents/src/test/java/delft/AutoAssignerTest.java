package delft;

import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.AllArguments;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AutoAssignerTest {

    private ZonedDateTime date(int year, int month, int day, int hour, int minute) {
        return ZonedDateTime.of(year, month, day, hour, minute, 0, 0, ZoneId.systemDefault());
    }

    private ZonedDateTime date1 = date(2025, 11, 04, 10, 30);
    private ZonedDateTime date2 = date(2026, 12, 05, 11, 31);
    private ZonedDateTime date3 = date(2027, 10, 06, 12, 32);

    private Map<ZonedDateTime, Integer> spotsPerDate1 = Map.of(date1, 1);
    private Map<ZonedDateTime, Integer> spotsPerDate2 = Map.of(date2, 3);
    private Map<ZonedDateTime, Integer> spotsPerDate3 = Map.of(date3, 2);

    private List<AllArguments.Assignment> assignments = new ArrayList<>();

    private List<Student> students = new ArrayList<>();
    private Student Louis = new Student(1, "Louis", "Louis@gmail.com");
    private Student Julien = new Student(2, "Julien", "Julien@gmail.com");
    private Student Houria = new Student(3, "Houria", "Houria@gmail.com");
    private Student Anas = new Student(4, "Anas", "Anas@gmail.com");

    private List<Workshop> workshops = new ArrayList<>();
    private Workshop workshop1 = new Workshop(1, "workshop1", spotsPerDate1);
    private Workshop workshop2 = new Workshop(2, "workshop2", spotsPerDate2);
    private Workshop workshop3 = new Workshop(3, "workshop3", spotsPerDate3);



    // ——————————————— Test on students —————————————
    @Test
    public void goodCreationStudentTest(){
        students.add(Louis);
        Student louisTemp = students.get(0);
        Student Louis2 =  new Student(1, "Louis", "Louis@gmail.com");
        Student Louis3 =  new Student(2, "Louis", "Louis@gmail.com");
        Student Louis4 =  new Student(1, "Loui_s", "Louis@gmail.com");
        Student Louis5 =  new Student(1, "Louis", "Louis_B@gmail.com");

        Student Houria = new Student(5, "Houria", "Houria@gmail.com");
        assertTrue(Houria.equals(Houria));
        assertFalse(Houria.equals(null));
        Student Julien = null;
        assertNull(Julien);

        assertTrue(Louis.equals(louisTemp));
        assertFalse(Louis.equals("Not the same type"));

        assertEquals(Louis2, Louis);
        assertNotEquals(Louis3, Louis);
        assertNotEquals(Louis4, Louis);
        assertNotEquals(Louis5, Louis);

        assertEquals(louisTemp.getName(), "Louis");
        assertEquals(louisTemp.getId(), 1);
        assertEquals(louisTemp.getEmail(), "Louis@gmail.com");
    }

    @Test
    public void goodCreationSameStudentTest(){
        students.add(Louis);
        Student louisTemp = students.get(0);
        Student louisTemp2 = new Student(1, "Louis", "Louis@gmail.com");
        assertEquals(louisTemp2, louisTemp);
        assertEquals(louisTemp2.hashCode(), louisTemp.hashCode());
        assertNotEquals(0, louisTemp.hashCode());
    }
    // ————————————— Test on workshop ——————————————

    @Test
    public void goodCreationWorkshopTest(){

        workshops.add(workshop1);
        Workshop workshopTemp = new Workshop(1, "workshop1", spotsPerDate1);
        workshops.add(workshopTemp);

        assertNotNull(workshopTemp.getSpotsPerDate());

        assertFalse(workshopTemp.getSpotsPerDate().isEmpty());

        assertEquals("workshop1", workshopTemp.getName());
        assertEquals(1, workshopTemp.getId());
        assertEquals(1, workshop1.getId());

        assertEquals(workshopTemp.getSpotsPerDate(), workshop1.getSpotsPerDate());
    }

    @Test
    public void goodCreationWorkshopAndEqualsTest(){
        Workshop workshopTemp = new Workshop(3, "workshop3", spotsPerDate3);

        Workshop workshopTest1 = new Workshop(4, "workshop3", spotsPerDate3);
        Workshop workshopTest2 = new Workshop(3, "workshop4", spotsPerDate3);
        Workshop workshopTest3 = new Workshop(3, "workshop3", spotsPerDate2);

        assertTrue(workshopTemp.equals(workshopTemp));

        assertEquals(workshopTemp.hashCode(),  workshopTemp.hashCode());
        assertNotEquals(0, workshopTemp.hashCode());

        assertFalse(workshopTemp.equals("not the same type"));

        assertNotEquals(workshopTemp, workshopTest1);
        assertNotEquals(workshopTemp, workshopTest2);
        assertNotEquals(workshopTemp, workshopTest3);
    }

    @Test
    public void goodCreationWorkshopOnFilterTest(){
        ZonedDateTime dateTemp = date(2027, 10, 06, 12, 32);
        Map<ZonedDateTime, Integer> spotsPerDate = Map.of(dateTemp, 2);

        Workshop workshopTemp = new Workshop(1, "workshop1", spotsPerDate);
        assertEquals(dateTemp, workshopTemp.getNextAvailableDate());
    }

    @Test
    public void goodCreationWorkshopDateWithNoPlaceTest(){
        ZonedDateTime dateTemp = date(2027, 10, 06, 12, 32);
        Map<ZonedDateTime, Integer> spotsPerDate = Map.of(dateTemp, 0);

        Workshop workshopTemp = new Workshop(1, "workshop1", spotsPerDate);
        assertThrows(NoSuchElementException.class, workshopTemp::getNextAvailableDate); //Aider par chatgpt pour trouver comment jeter des exceptions
    }

    @Test
    public void goodCreationWorkshopAndTakeTheGoodOneTest(){
        ZonedDateTime dateTemp = date(2027, 10, 06, 12, 32);
        ZonedDateTime dateTest1 = date(2027, 10, 03, 12, 32);
        ZonedDateTime dateTest2= date(2027, 10, 02, 12, 32);
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();

        spotsPerDate.put(dateTemp, 0);
        spotsPerDate.put(dateTest1, 2);
        spotsPerDate.put(dateTest2, -1);

        Workshop workshopTemp = new Workshop(1, "workshop1", spotsPerDate);
        assertEquals(dateTest1, workshopTemp.getNextAvailableDate());
    }

    @Test
    public void WorkshopAndCaseWhereThereIsNullTest(){
        ZonedDateTime dateTemp = date(2027, 10, 06, 12, 32);
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();
        spotsPerDate.put(dateTemp, 0);
        Workshop workshopTemp = new Workshop(1, "workshop1", spotsPerDate);

        assertFalse(workshopTemp.equals(null));
        Workshop workshopNull = null;
        assertNull(workshopNull);

    }
    // ———————————————— Test on map —————————————————

    @Test
    public void goodCreationOfMapTest(){
        ZonedDateTime dateTemp = date(2027, 10, 06, 12, 32);
        Map<ZonedDateTime, Integer> spotsPerDate = Map.of(dateTemp, 2);

        assertEquals(spotsPerDate, spotsPerDate3);
        assertEquals(spotsPerDate.get(0), spotsPerDate3.get(0));
        assertTrue(spotsPerDate.equals(spotsPerDate3));
        assertEquals(spotsPerDate.hashCode(), spotsPerDate3.hashCode());
        assertNotEquals(0, spotsPerDate.hashCode());
    }


    // —————— Test on students with workshops ———————

    @Test
    public void AddMoreStudentThanPlaceInWorkshopTest() {
        students.add(Louis);
        students.add(Julien);
        workshops.add(workshop1);

        AutoAssigner autoAssigner = new AutoAssigner();
        AssignmentsLogger assignmentsLogger = autoAssigner.assign(students, workshops);

        assertNotNull(assignmentsLogger.getErrors()) ;
        assertNotNull(assignmentsLogger.getAssignments()) ;
        assertEquals(1, assignmentsLogger.getAssignments().size());
        assertEquals(1, assignmentsLogger.getErrors().size());
    }

    @Test
    public void AddStudentsInSeveralWorkshopsTest() {
        students.add(Louis);
        students.add(Julien);
        workshops.add(workshop1);
        workshops.add(workshop2);

        AutoAssigner autoAssigner = new AutoAssigner();
        AssignmentsLogger assignmentsLogger = autoAssigner.assign(students, workshops);

        System.out.println(assignmentsLogger.getErrors());
        System.out.println(assignmentsLogger.getAssignments());
        assertNotNull(assignmentsLogger.getErrors()) ;
        assertNotNull(assignmentsLogger.getAssignments()) ;
        assertEquals(3, assignmentsLogger.getAssignments().size());
        assertEquals(1, assignmentsLogger.getErrors().size());
    }


    @Test
    public void goodNameofStudentInWorkshop() {
        students.add(Louis);
        students.add(Julien);
        workshops.add(workshop2);

        AutoAssigner autoAssigner = new AutoAssigner();
        AssignmentsLogger assignmentsLogger = autoAssigner.assign(students, workshops);

        List<String> listeNameOfStudent = new ArrayList<>();
        for (String assignments : assignmentsLogger.getAssignments()) {
            String[] parts = assignments.split(",");
            listeNameOfStudent.add(parts[1]);
        }
        int i = 0;
        for (Student student : students) {
            assertEquals(listeNameOfStudent.get(i), student.getName());
            i++;
        }
    }

    @Test
    public void testOnFormatOfDateWithStudentInWorkshop() {
        workshops.add(workshop1);
        students.add(Louis);
        AutoAssigner autoAssigner = new AutoAssigner();
        AssignmentsLogger assignmentsLogger = autoAssigner.assign(students, workshops);
        List<String> assignments= assignmentsLogger.getAssignments();
        assertEquals(assignments.size(), 1);
        System.out.println(assignments.get(0));
        assertEquals(assignments.get(0), "workshop1,Louis,04/11/2025 10:30");

    }

}
