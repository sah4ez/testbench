package com.vaadin.testUI;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.tests.AbstractTestUI;
import com.vaadin.ui.Slider;

public class SliderGetHandle extends AbstractTestUI {
    @WebServlet(value = { "/VAADIN/*", "/SliderGetHandle/*" }, asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = SliderGetHandle.class)
    public static class Servlet extends VaadinServlet {
    }

    public static final double INITIAL_VALUE = 10.0;

    @Override
    protected void setup(VaadinRequest request) {
        Slider sl1 = new Slider();
        Slider sl2 = new Slider();
        sl2.setValue(INITIAL_VALUE);
        sl1.setWidth("50px");
        sl2.setWidth("50px");
        addComponent(sl1);
        addComponent(sl2);
    }

    @Override
    protected String getTestDescription() {
        return "Check getHanler method works. Get handler and move it, check get Value return new value";
    }

    @Override
    protected Integer getTicketNumber() {
        return 13769;
    }

}
