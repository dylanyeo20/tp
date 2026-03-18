package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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

/**
 * Contains integration tests for {@code UnmarkAsFavouriteCommand}.
 */
public class UnmarkAsFavouriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests that executing the command with an out-of-range index fails.
     */
    @Test
    public void execute_indexOutOfRange() {
        Index index = Index.fromOneBased(model.getAddressBook().getPersonList().size() + 10);
        UnmarkAsFavouriteCommand unmarkAsFavouriteCommand = new UnmarkAsFavouriteCommand(index);
        assertCommandFailure(unmarkAsFavouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Tests that executing the command on a person who is not marked as favourite fails.
     */
    @Test
    public void execute_personNotInFavourites_throwsCommandException() throws CommandException {
        Index index = Index.fromOneBased(2);

        UnmarkAsFavouriteCommand command = new UnmarkAsFavouriteCommand(index);

        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToEdit = lastShownList.get(index.getZeroBased());

        assertCommandFailure(command, model,
                String.format(UnmarkAsFavouriteCommand.MESSAGE_UNMARK_PERSON_DUPLICATE,
                        personToEdit.getName()));
    }

    /**
     * Tests that executing the command with a valid index successfully removes the favourite status.
     */
    @Test
    public void execute_validUnmarkAsFavourites() throws Exception {
        Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index index = Index.fromOneBased(1);

        //Add Index to Favourites
        new MarkAsFavouriteCommand(index).execute(testModel);

        List<Person> lastShownList = testModel.getFilteredPersonList();
        Person personToEdit = lastShownList.get(index.getZeroBased());

        UnmarkAsFavouriteCommand command = new UnmarkAsFavouriteCommand(index);


        assertCommandSuccess(command, testModel,
                String.format(UnmarkAsFavouriteCommand.MESSAGE_UNMARK_PERSON_SUCCESS, personToEdit.getName()),
                testModel);
    }

    /**
     * Testing of equals method overwritten in UnmarkAsFavouriteCommand
     */
    @Test
    public void execute_equals() {
        UnmarkAsFavouriteCommand sameOne = new UnmarkAsFavouriteCommand(Index.fromOneBased(1));
        UnmarkAsFavouriteCommand sameTwo = new UnmarkAsFavouriteCommand(Index.fromOneBased(1));
        UnmarkAsFavouriteCommand different = new UnmarkAsFavouriteCommand(Index.fromOneBased(2));
        Object otherObject = new ArrayList<>();

        assertTrue(sameOne.equals(sameTwo));
        assertFalse(sameOne.equals(different));
        assertFalse(sameOne.equals(otherObject));
    }
}
