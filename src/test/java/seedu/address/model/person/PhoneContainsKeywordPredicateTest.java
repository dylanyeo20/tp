package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("12345");
        List<String> secondPredicateKeywordList = Collections.singletonList("67890");

        PhoneContainsKeywordPredicate firstPredicate = new PhoneContainsKeywordPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordPredicate secondPredicate = new PhoneContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordPredicate firstPredicateCopy = new PhoneContainsKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PhoneContainsKeywordPredicate predicate =
                new PhoneContainsKeywordPredicate(Collections.singletonList("95352563"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("95352563").build()));

        // Partial keyword match
        predicate = new PhoneContainsKeywordPredicate(Collections.singletonList("9535"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("95352563").build()));

        // Multiple keywords (any match)
        predicate = new PhoneContainsKeywordPredicate(Arrays.asList("95352563", "98765432"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("95352563").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneContainsKeywordPredicate predicate = new PhoneContainsKeywordPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("95352563").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordPredicate(Collections.singletonList("12345"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("95352563").build()));

        // Keyword matches name, but does not match phone
        predicate = new PhoneContainsKeywordPredicate(Collections.singletonList("Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("95352563").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("95352563");
        PhoneContainsKeywordPredicate predicate = new PhoneContainsKeywordPredicate(keywords);

        String expected = PhoneContainsKeywordPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
