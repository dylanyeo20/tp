package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all favourite persons in the address book to the user.
 */
public class FavouritesCommand extends Command {

    public static final String COMMAND_WORD = "favourites";

    public static final String MESSAGE_SUCCESS = "Listed all favourite persons";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(person -> person.getIsFavourite());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
