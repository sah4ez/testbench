/*
 * Copyright 2000-2014 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.tests.testbenchapi;

import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vaadin.testUI.ComponentElementGetValue;
import com.vaadin.testbench.elements.AbstractTextFieldElement;
import com.vaadin.testbench.elements.CheckBoxElement;
import com.vaadin.testbench.elements.ComboBoxElement;
import com.vaadin.testbench.elements.DateFieldElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.elements.ListSelectElement;
import com.vaadin.testbench.elements.NativeSelectElement;
import com.vaadin.testbench.elements.OptionGroupElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TextAreaElement;
import com.vaadin.testbench.elements.TextFieldElement;
import com.vaadin.testbench.elements.TwinColSelectElement;

/**
 * Test clear method. Checks that value of the component was changed both on
 * client and server side Testing of the client side done by comparing first
 * with initial value then calling clear and comparing with empty value. Testing
 * of the server side by checking that changeValue even was raised on the server
 * side. Each element has changeValue listener added in the UI class. Compare
 * labelChangeValue value with the value used in the listener of the UI class.
 *
 * @since
 * @author Vaadin Ltd
 */

public class TestBenchElementClearValueIT extends MultiBrowserTest {
    // The label text is changed on element component ValueChange event
    // Used to test that element.clear() method has actually triggered the
    // server side code
    private LabelElement labelChangeValue;

    // use same TestUI class as for getValue method
    @Override
    protected Class<?> getUIClass() {
        return ComponentElementGetValue.class;
    }

    @Before
    public void init() {
        openTestURL("theme=reindeer");
        labelChangeValue = $(LabelElement.class).get(1);
    }

    @Test
    public void clearTextField() {
        TextFieldElement elem = $(TextFieldElement.class).get(0);
        checkElementValue(elem);
        Assert.assertEquals(ComponentElementGetValue.FIELD_VALUES[0],
                labelChangeValue.getText());
    }

    @Test
    public void clearTextArea() {
        TextAreaElement elem = $(TextAreaElement.class).get(0);
        checkElementValue(elem);
        Assert.assertEquals(ComponentElementGetValue.FIELD_VALUES[1],
                labelChangeValue.getText());
    }

    @Test
    public void clearPasswordField() {
        PasswordFieldElement elem = $(PasswordFieldElement.class).get(0);
        checkElementValue(elem);
        Assert.assertEquals(ComponentElementGetValue.FIELD_VALUES[2],
                labelChangeValue.getText());
    }

    @Test
    public void clearDateField() {
        DateFieldElement df = $(DateFieldElement.class).get(0);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String expected = format1
                .format(ComponentElementGetValue.TEST_DATE_VALUE);
        String initial = df.getValue();
        Assert.assertEquals(expected, initial);
        df.clear();
        Assert.assertEquals("", df.getValue());
        Assert.assertEquals(ComponentElementGetValue.DATEFIELD_VALUE_CHANGE,
                labelChangeValue.getText());
    }

    // Clear method should do not raise exception
    public void clearComboBox() {
        ComboBoxElement elem = $(ComboBoxElement.class).get(0);
        elem.clear();
    }

    public void clearNativeSelect() {
        NativeSelectElement elem = $(NativeSelectElement.class).get(0);
        elem.clear();
    }

    public void clearListSelect() {
        ListSelectElement elem = $(ListSelectElement.class).get(0);
        elem.clear();
    }

    public void clearOptionGroup() {
        OptionGroupElement elem = $(OptionGroupElement.class).get(0);
        elem.clear();
    }

    @Test
    public void clearCheckBox() {
        CheckBoxElement elem = $(CheckBoxElement.class).get(0);
        elem.clear();
        Assert.assertTrue(elem.getValue().equals("unchecked"));
        Assert.assertEquals(ComponentElementGetValue.CHECKBOX_VALUE_CHANGE,
                labelChangeValue.getText());
    }

    @Test
    public void clearTwinCol() {
        TwinColSelectElement elem = $(TwinColSelectElement.class).get(0);
        elem.clear();
        Assert.assertEquals("", elem.getValue());
        Assert.assertEquals(ComponentElementGetValue.TWINCOL_VALUE_CHANGE,
                labelChangeValue.getText());
    }

    // helper functions
    private void checkElementValue(AbstractTextFieldElement elem) {
        String initial = ComponentElementGetValue.TEST_STRING_VALUE;
        checkElementValue(elem, initial);
    }

    private void checkElementValue(AbstractTextFieldElement elem,
            String expected) {
        // check initial element value
        String actual = elem.getValue();
        Assert.assertEquals(expected, actual);
        // check cleared element value
        elem.clear();
        expected = "";
        actual = elem.getValue();
        Assert.assertEquals(expected, actual);
    }
}
