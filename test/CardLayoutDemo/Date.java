/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CardLayoutDemo;

import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author Toan Ho
 */
public class Date {

    public static void main(String[] args) {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println("utilDate: " + utilDate);
        System.out.println("sqlDate: " + sqlDate);
        
        String idName = "10 - Ho Gia Bao";
        String[] x = idName.split("-");
        
        for (String i : x) {
            System.out.println(i.trim());
        }
        
//        String[] options = new String[] {"Yes", "No", "Maybe", "Cancel"};
//        int response = JOptionPane.showOptionDialog(null, "Message", "Title",
//            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
//            null, options, options[0]);
//        


//        String[] choices = { "A", "B", "C", "D", "E", "F" };
//        String input = (String) JOptionPane.showInputDialog(null, "Choose now...",
//            "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null, // Use
//                                                                            // default
//                                                                            // icon
//            choices, // Array of choices
//            choices[1]); // Initial choice
//        System.out.println(input);
        
//        JComboBox list = new JComboBox(new String[] {"", "foo", "bar", "gah"});
//        JOptionPane.showMessageDialog(
//          null, list, "Multi-Select Example", JOptionPane.QUESTION_MESSAGE);
//        System.out.println(list.getSelectedIndex());
    }
}
