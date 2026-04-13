package seedu.address.model.person;

import java.util.List;

/**
 * Tests that a {@code Person}'s {@code Email} contains any of the keywords given.
 */
public class TagContainsKeywordPredicate extends ObjectContainsKeywordsPredicate<Person> {

    public TagContainsKeywordPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    protected List<String> getFieldValue(Person person) {
        return person.getTags().stream().map(tag -> tag.toString()).toList();
    }
}
