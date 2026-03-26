package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ObjectContainsKeywordsPredicateTest {

    private static class TestObject {
        private final String value;

        TestObject(String value) {
            this.value = value;
        }
    }

    private static class TestPredicate extends ObjectContainsKeywordsPredicate<TestObject> {

        TestPredicate(List<String> keywords) {
            super(keywords);
        }

        @Override
        protected String getFieldValue(TestObject object) {
            return object.value;
        }
    }

    @Test
    public void test_nullFieldValue_returnsFalse() {
        TestPredicate predicate = new TestPredicate(List.of("alice"));
        TestObject object = new TestObject(null);

        assertFalse(predicate.test(object));
    }

    @Test
    public void test_keywordMatches_returnsTrue() {
        TestPredicate predicate = new TestPredicate(List.of("alice"));
        TestObject object = new TestObject("alice tan");

        assertTrue(predicate.test(object));
    }

    @Test
    public void test_keywordDoesNotMatch_returnsFalse() {
        TestPredicate predicate = new TestPredicate(List.of("bob"));
        TestObject object = new TestObject("alice tan");

        assertFalse(predicate.test(object));
    }

    @Test
    public void test_caseInsensitiveMatch_returnsTrue() {
        TestPredicate predicate = new TestPredicate(List.of("ALICE"));
        TestObject object = new TestObject("alice tan");

        assertTrue(predicate.test(object));
    }

    @Test
    public void test_multipleKeywordsOneMatches_returnsTrue() {
        TestPredicate predicate = new TestPredicate(List.of("bob", "alice"));
        TestObject object = new TestObject("alice tan");

        assertTrue(predicate.test(object));
    }

    @Test
    public void equals() {
        TestPredicate firstPredicate = new TestPredicate(List.of("alice"));
        TestPredicate secondPredicate = new TestPredicate(List.of("alice"));
        TestPredicate thirdPredicate = new TestPredicate(List.of("bob"));

        // same object
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values
        assertTrue(firstPredicate.equals(secondPredicate));

        // null
        assertFalse(firstPredicate.equals(null));

        // different type
        assertFalse(firstPredicate.equals(1));

        // different keywords
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void toStringMethod() {
        TestPredicate predicate = new TestPredicate(List.of("alice"));
        String expected = TestPredicate.class.getCanonicalName() + "{keywords=[alice]}";

        assertEquals(expected, predicate.toString());
    }
}
