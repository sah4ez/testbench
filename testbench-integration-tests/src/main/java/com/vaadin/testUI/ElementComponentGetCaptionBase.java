package com.vaadin.testUI;

import com.vaadin.server.VaadinRequest;
import com.vaadin.tests.AbstractTestUI;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ColorPicker;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Flash;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

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
 * 
 * Base testUI class for testing getCaption method. Captions of elements
 * implemented differently in different layouts (FormLayout) This class adds all
 * elements to the layout. The exact layout is created in a child class.
 */
public abstract class ElementComponentGetCaptionBase extends AbstractTestUI {
    // Specify exact type of Layout in a child class
    AbstractLayout mainLayout = null;
    // default captions for all elements
    public static final String[] DEFAULT_CAPTIONS = { "Combobox", "button",
            "table", "treeTable", "tree", "TwinColSelect", "optionGroup",
            "ListSelect", "ColorPicker", "Accordion", "Image", "Flash",
            "BrowserFrame", "CheckBox", "TextField", "TextArea", "DateField",
            "VerticalLayout", "HorizontalLayout", "FormLayout", "GridLayout",
            "CssLayout" };

    @Override
    protected void setup(VaadinRequest request) {
        Component[] comps = { new ComboBox(), new Button(), new Table(),
                new TreeTable(), new Tree(), new TwinColSelect(),
                new OptionGroup(), new ListSelect(), new ColorPicker(),
                new Accordion(), new Image(), new Flash(), new BrowserFrame(),
                new CheckBox(), new TextField(), new TextArea(),
                new DateField(), new VerticalLayout(), new HorizontalLayout(),
                new FormLayout(), new GridLayout(), new CssLayout() };
        addComponent(mainLayout);
        for (int i = 0; i < comps.length; i++) {
            Component component = comps[i];
            component.setCaption(DEFAULT_CAPTIONS[i]);
            mainLayout.addComponent(component);
        }
    }

    @Override
    protected String getTestDescription() {
        return "Test getCaption method for vaadin components";
    }

    @Override
    protected Integer getTicketNumber() {
        return 14453;
    }
}
