package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MeetingCommand;

public class MeetingCommandParserTest {

    private final MeetingCommandParser parser = new MeetingCommandParser();

    @Test
    public void parse_validArguments() {
        assertParseSuccess(parser, "1 23/03/2026 14:30",
                new MeetingCommand(Index.fromOneBased(1), LocalDate.of(2026, 3, 23), LocalTime.of(14, 30)));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        assertParseFailure(parser, "1 2026-03-23 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_throwsParseException() {
        assertParseFailure(parser, "1 23/03/2026",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetingCommand.MESSAGE_USAGE));
    }
}
