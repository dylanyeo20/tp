package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class MeetingCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_indexOutOfRange() {
        Index index = Index.fromOneBased(model.getAddressBook().getPersonList().size() + 10);
        MeetingCommand meetingCommand = new MeetingCommand(index, LocalDate.of(2026, 3, 23), LocalTime.of(14, 30));
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                meetingCommand.execute(model));
    }

    @Test
    public void execute_indexOutOfRangeFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MeetingCommand meetingCommand =
                new MeetingCommand(outOfBoundIndex, LocalDate.of(2026, 3, 23), LocalTime.of(14, 30));
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                meetingCommand.execute(model));
    }

    @Test
    public void execute_validMeeting_success() throws Exception {
        Model actualModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index index = Index.fromOneBased(1);

        List<Person> lastShownList = expectedModel.getFilteredPersonList();
        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person updatedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getDetails(),
                personToEdit.getTags(),
                personToEdit.getIsFavourite(),
                LocalDate.of(2026, 3, 23),
                LocalTime.of(14, 30));
        expectedModel.setPerson(personToEdit, updatedPerson);

        MeetingCommand command = new MeetingCommand(index, LocalDate.of(2026, 3, 23), LocalTime.of(14, 30));
        CommandResult commandResult = command.execute(actualModel);

        assertEquals(String.format(MeetingCommand.MESSAGE_MEETING_ADDED, updatedPerson.getName(),
                "23/03/2026", "14:30"), commandResult.getFeedbackToUser());
        assertEquals(expectedModel, actualModel);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        MeetingCommand meetingCommand = new MeetingCommand(Index.fromOneBased(1),
                LocalDate.of(2026, 3, 23), LocalTime.of(14, 30));
        assertThrows(NullPointerException.class, () -> meetingCommand.execute(null));
    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MeetingCommand(null,
                LocalDate.of(2026, 3, 23), LocalTime.of(14, 30)));
        assertThrows(NullPointerException.class, () -> new MeetingCommand(Index.fromOneBased(1),
                null, LocalTime.of(14, 30)));
        assertThrows(NullPointerException.class, () -> new MeetingCommand(Index.fromOneBased(1),
                LocalDate.of(2026, 3, 23), null));
    }

    @Test
    public void getFormattedDateAndTime_returnsExpectedFormat() {
        MeetingCommand meetingCommand =
                new MeetingCommand(Index.fromOneBased(1), LocalDate.of(2026, 3, 23), LocalTime.of(14, 30));

        assertEquals("23/03/2026", meetingCommand.getFormattedDate());
        assertEquals("14:30", meetingCommand.getFormattedTime());
    }

    @Test
    public void equals() {
        MeetingCommand first = new MeetingCommand(Index.fromOneBased(1), LocalDate.of(2026, 3, 23),
                LocalTime.of(14, 30));
        MeetingCommand same = new MeetingCommand(Index.fromOneBased(1), LocalDate.of(2026, 3, 23),
                LocalTime.of(14, 30));
        MeetingCommand different = new MeetingCommand(Index.fromOneBased(2), LocalDate.of(2026, 3, 24),
                LocalTime.of(15, 0));
        MeetingCommand sameIndexDifferentDate = new MeetingCommand(Index.fromOneBased(1), LocalDate.of(2026, 3, 24),
                LocalTime.of(14, 30));
        MeetingCommand sameIndexAndDateDifferentTime = new MeetingCommand(Index.fromOneBased(1),
                LocalDate.of(2026, 3, 23), LocalTime.of(15, 0));
        Object otherObject = new ArrayList<>();

        assertTrue(first.equals(first));
        assertTrue(first.equals(same));
        assertFalse(first.equals(different));
        assertFalse(first.equals(sameIndexDifferentDate));
        assertFalse(first.equals(sameIndexAndDateDifferentTime));
        assertFalse(first.equals(otherObject));
        assertFalse(first.equals(null));
    }
}
