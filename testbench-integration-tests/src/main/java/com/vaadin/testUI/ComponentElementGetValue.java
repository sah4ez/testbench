package com.vaadin.testUI;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.tests.AbstractTestUI;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

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

/**
 * UI test for getValue() method of components: TextField, TextArea,
 * PasswordField, ComboBox, ListSelect, NativeSelect, OptionGroup, CheckBox,
 * DateField, TwinColSelect
 *
 * @since
 * @author Vaadin Ltd
 */
public class ComponentElementGetValue extends AbstractTestUI {
    @WebServlet(value = { "/VAADIN/*", "/ComponentElementGetValue/*" }, asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = ComponentElementGetValue.class)
    public static class Servlet extends VaadinServlet {
    }

    public static final String TEST_STRING_VALUE = "item 2";
    public static final int TEST_SLIDER_VALUE = 42;
    public static final float TEST_FLOAT_VALUE = 0.42f;
    public static final Date TEST_DATE_VALUE = Calendar.getInstance().getTime();
    DateField df;
    final Label valueChangeLabel = new Label("Initial value");

    // These constants are used to check that change value event was
    // called
    public static final String[] FIELD_VALUES = { "textFieldValueChange",
            "textAreaValueChange", "passwordValueChange" };
    public static final String CHECKBOX_VALUE_CHANGE = "checkboxValueChange";
    public static final String DATEFIELD_VALUE_CHANGE = "dateFieldValueChange";
    public static final String TWINCOL_VALUE_CHANGE = "twinColValueChange";

    @Override
    protected void setup(VaadinRequest request) {

        AbstractTextField[] fieldComponents = { new TextField(),
                new TextArea(), new PasswordField()

        };

        AbstractSelect[] selectComponents = { new ComboBox(), new ListSelect(),
                new NativeSelect(), new OptionGroup() };

        for (AbstractSelect comp : selectComponents) {
            comp.addItem("item 1");
            comp.addItem(TEST_STRING_VALUE);
            comp.addItem("item 3");
            comp.setValue(TEST_STRING_VALUE);
            addComponent(comp);
        }
        addComponent(createTwinColSelect());
        for (int i = 0; i < fieldComponents.length; i++) {
            AbstractTextField field = fieldComponents[i];
            field.setValue(TEST_STRING_VALUE);
            ValueChangeListener listener = new MyValueChangeListener(
                    FIELD_VALUES[i]);
            field.addValueChangeListener(listener);
            addComponent(field);
        }

        addComponent(createCheckBox());
        addComponent(createSlider());
        addComponent(createDateField());
        valueChangeLabel.setId("valueChangeLabel");
        addComponent(valueChangeLabel);
    }

    private DateField createDateField() {
        DateField df = new DateField();
        df.setDateFormat("yyyy-MM-dd");
        df.setValue(TEST_DATE_VALUE);
        df.addValueChangeListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                valueChangeLabel.setValue(DATEFIELD_VALUE_CHANGE);
            }
        });
        return df;
    }

    private Slider createSlider() {
        Slider sl = new Slider(0, 100);
        sl.setWidth("100px");
        sl.setValue(new Double(TEST_SLIDER_VALUE));
        return sl;
    }

    private CheckBox createCheckBox() {
        CheckBox cb = new CheckBox();
        cb.setValue(true);
        cb.addValueChangeListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                valueChangeLabel.setValue(CHECKBOX_VALUE_CHANGE);
            }
        });
        return cb;
    }

    @Override
    protected String getTestDescription() {
        return "Field elements getValue() should return test value";
    }

    @Override
    protected Integer getTicketNumber() {
        return 13455;
    }

    private TwinColSelect createTwinColSelect() {
        TwinColSelect tab = new TwinColSelect("");
        tab.addItems("item 1", TEST_STRING_VALUE, "item 3", "item 4");
        // Preselect a few items by creating a set
        tab.setValue(new HashSet<String>(Arrays.asList(TEST_STRING_VALUE)));
        tab.addValueChangeListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                valueChangeLabel.setValue(TWINCOL_VALUE_CHANGE);
            }
        });

        return tab;
    }

    private class MyValueChangeListener implements ValueChangeListener {
        String value;

        public MyValueChangeListener(String value) {
            this.value = value;
        }

        public void valueChange(ValueChangeEvent event) {
            valueChangeLabel.setValue("");
            valueChangeLabel.setValue(value);
        }
    }
}
