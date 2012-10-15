package com.vaadin.testbench;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

/*
 * This class nags about license information whenever TestBench is started
 * unless the vaadin.testbench.developer.license system property is set or the
 * ~/.vaadin.testbench.developer.license file exists.
 */
public class LicenseChecker {
    public static void nag() {
        String userAccount = System
                .getProperty("vaadin.testbench.developer.license");
        if (userAccount == null) {
            String homedir = System.getProperty("user.home");
            File file = new File(homedir
                    + "/.vaadin.testbench.developer.license");
            if (!file.exists()) {
                printCVALInformationAndHowToGetRidOfThisInformation();
            }
        }
    }

    private static void printCVALInformationAndHowToGetRidOfThisInformation() {
        try {
            System.err.println(CharStreams.toString(new InputStreamReader(
                    LicenseChecker.class.getResourceAsStream("licensenag.txt"),
                    Charsets.UTF_8)));
        } catch (IOException e) {
            System.err
                    .println("VAADIN TESTBENCH IS COMMERCIAL SOFTWARE, SEE https://vaadin.com/license/cval-2.0");
        }
    }
}