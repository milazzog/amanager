package org.mdev.amanager.test.search;

import com.mdev.amanager.persistence.domain.repository.params.base.DefaultMatch;
import com.mdev.amanager.persistence.domain.repository.params.base.MatchingMode;
import com.mdev.amanager.persistence.domain.repository.params.base.SearchParam;
import com.mdev.amanager.persistence.domain.repository.params.base.StringMatcher;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gmilazzo on 05/10/2018.
 */

public class TestSearchParamAnnotation {

    public class TestParam extends SearchParam{

        @DefaultMatch(mode = MatchingMode.ENDS)
        private StringMatcher value1;
        @DefaultMatch
        private StringMatcher value2;

        public StringMatcher getValue1() {
            return value1;
        }

        public void setValue1(StringMatcher value1) {
            this.value1 = value1;
        }

        public StringMatcher getValue2() {
            return value2;
        }

        public void setValue2(StringMatcher value2) {
            this.value2 = value2;
        }
    }

    @Test
    public void test_annotation_processed_should_pass(){

        TestParam p = new TestParam();

        assertNotNull(p.getValue1());
        assertNotNull(p.getValue2());
        assertEquals(MatchingMode.ENDS, p.getValue1().getMode());
        assertEquals(MatchingMode.ANYWHERE, p.getValue2().getMode());
    }
}
