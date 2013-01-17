/*
 * Copyright (c) 2007-2013, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.threeten.bp.temporal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import static org.threeten.bp.temporal.ChronoField.EPOCH_MONTH;
import static org.threeten.bp.temporal.ChronoField.ERA;
import static org.threeten.bp.temporal.ChronoField.MONTH_OF_YEAR;
import static org.threeten.bp.temporal.ChronoField.YEAR;
import static org.threeten.bp.temporal.ChronoField.YEAR_OF_ERA;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.threeten.bp.AbstractDateTimeTest;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;
import org.threeten.bp.Year;
import org.threeten.bp.YearMonth;

/**
 * Test YearMonth.
 */
@Test
public class TestYearMonth extends AbstractDateTimeTest {

    private YearMonth TEST_2008_06;

    @BeforeMethod
    public void setUp() {
        TEST_2008_06 = YearMonth.of(2008, 6);
    }

    //-----------------------------------------------------------------------
    @Override
    protected List<TemporalAccessor> samples() {
        TemporalAccessor[] array = {TEST_2008_06, };
        return Arrays.asList(array);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_serialization_format() throws ClassNotFoundException, IOException {
        assertEqualsSerialisedForm(YearMonth.of(2012, 9));
    }

    @Test
    public void test_serialization() throws IOException, ClassNotFoundException {
        assertSerializable(TEST_2008_06);
    }

    @Override
    protected List<TemporalField> validFields() {
        TemporalField[] array = {
            MONTH_OF_YEAR,
            EPOCH_MONTH,
            YEAR_OF_ERA,
            YEAR,
            ERA,
        };
        return Arrays.asList(array);
    }

    @Override
    protected List<TemporalField> invalidFields() {
        List<TemporalField> list = new ArrayList<>(Arrays.<TemporalField>asList(ChronoField.values()));
        list.removeAll(validFields());
        list.add(JulianFields.JULIAN_DAY);
        list.add(JulianFields.MODIFIED_JULIAN_DAY);
        list.add(JulianFields.RATA_DIE);
        return list;
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_interfaces() {
        Object obj = TEST_2008_06;
        assertTrue(obj instanceof Serializable);
        assertTrue(obj instanceof Comparable<?>);
        assertTrue(obj instanceof TemporalAdjuster);
    }

    //-----------------------------------------------------------------------
    void check(YearMonth test, int y, int m) {
        assertEquals(test.getYear(), y);
        assertEquals(test.getMonth().getValue(), m);
    }

    @Test
    public void test_with_Year_noChange_same() {
        YearMonth test = YearMonth.of(2008, 6);
        assertSame(test.with(Year.of(2008)), test);
    }

    @Test
    public void test_with_Month_noChange_same() {
        YearMonth test = YearMonth.of(2008, 6);
        assertSame(test.with(Month.JUNE), test);
    }

    @Test
    public void test_withYear_int_noChange_same() {
        YearMonth test = YearMonth.of(2008, 6);
        assertSame(test.withYear(2008), test);
    }

    @Test
    public void test_withMonth_int_noChange_same() {
        YearMonth test = YearMonth.of(2008, 6);
        assertSame(test.withMonth(6), test);
    }

    @Test
    public void test_plusYears_long_noChange_same() {
        YearMonth test = YearMonth.of(2008, 6);
        assertSame(test.plusYears(0), test);
    }

    @Test
    public void test_plusMonths_long_noChange_same() {
        YearMonth test = YearMonth.of(2008, 6);
        assertSame(test.plusMonths(0), test);
    }

    @Test
    public void test_minusYears_long_noChange_same() {
        YearMonth test = YearMonth.of(2008, 6);
        assertSame(test.minusYears(0), test);
    }

    @Test
    public void test_minusMonths_long_noChange_same() {
        YearMonth test = YearMonth.of(2008, 6);
        assertSame(test.minusMonths(0), test);
    }

    @Test
    public void test_adjustDate_same() {
        YearMonth test = YearMonth.of(2008, 6);
        LocalDate date = LocalDate.of(2008, 6, 30);
        assertSame(test.adjustInto(date), date);
    }

    void doTest_comparisons_YearMonth(YearMonth... localDates) {
        for (int i = 0; i < localDates.length; i++) {
            YearMonth a = localDates[i];
            for (int j = 0; j < localDates.length; j++) {
                YearMonth b = localDates[j];
                if (i < j) {
                    assertTrue(a.compareTo(b) < 0, a + " <=> " + b);
                    assertEquals(a.isBefore(b), true, a + " <=> " + b);
                    assertEquals(a.isAfter(b), false, a + " <=> " + b);
                    assertEquals(a.equals(b), false, a + " <=> " + b);
                } else if (i > j) {
                    assertTrue(a.compareTo(b) > 0, a + " <=> " + b);
                    assertEquals(a.isBefore(b), false, a + " <=> " + b);
                    assertEquals(a.isAfter(b), true, a + " <=> " + b);
                    assertEquals(a.equals(b), false, a + " <=> " + b);
                } else {
                    assertEquals(a.compareTo(b), 0, a + " <=> " + b);
                    assertEquals(a.isBefore(b), false, a + " <=> " + b);
                    assertEquals(a.isAfter(b), false, a + " <=> " + b);
                    assertEquals(a.equals(b), true, a + " <=> " + b);
                }
            }
        }
    }

}
