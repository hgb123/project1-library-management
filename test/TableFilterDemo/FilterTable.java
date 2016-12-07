
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class FilterTable {

    public static void main(String args[]) {
        Runnable runner = new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Sorting JTable");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Object rows[][] = {
                    {"AMZN", "Amazon", 41.28},
                    {"EBAY", "eBay", 41.57},
                    {"GOOG", "Google", 388.33},
                    {"MSFT", "Microsoft", 26.56},
                    {"NOK", "Nokia Corp", 17.13},
                    {"ORCL", "Oracle Corp.", 12.52},
                    {"SUNW", "Sun Microsystems", 3.86},
                    {"TWX", "Time Warner", 17.66},
                    {"VOD", "Vodafone Group", 26.02},
                    {"YHOO", "Yahoo!", 37.69}
                };
                Object columns[] = {"Symbol", "Name", "Price"};
                TableModel model = new DefaultTableModel(rows, columns) {
                    public Class getColumnClass(int column) {
                        Class returnValue;
                        if ((column >= 0) && (column < getColumnCount())) {
                            returnValue = getValueAt(0, column).getClass();
                        } else {
                            returnValue = Object.class;
                        }
                        return returnValue;
                    }
                };
                JTable table = new JTable(model);
                final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                table.setRowSorter(sorter);
                JScrollPane pane = new JScrollPane(table);
                frame.add(pane, BorderLayout.CENTER);
                JPanel panel = new JPanel(new BorderLayout());
                JLabel label = new JLabel("Filter");
                panel.add(label, BorderLayout.WEST);
                final JTextField filterText = new JTextField("SUN");
                panel.add(filterText, BorderLayout.CENTER);
                frame.add(panel, BorderLayout.NORTH);
                JButton button = new JButton("Filter");
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String text = filterText.getText();
                        if (text.length() == 0) {
                            sorter.setRowFilter(null);
                        } else {
                            try {
                                sorter.setRowFilter(RowFilter.regexFilter(text));
                            } catch (PatternSyntaxException pse) {
                                System.err.println("Bad regex pattern");
                            }
                        }
                    }
                });
                
                //Go text den dau thi loc den do
                filterText.addKeyListener(new KeyListener() {

                    @Override
                    public void keyTyped(KeyEvent e) {
                        String text = filterText.getText();
                        if (text.length() == 0) {
                            sorter.setRowFilter(null);
                        } else {
                            try {
                                sorter.setRowFilter(RowFilter.regexFilter(text));
                            } catch (PatternSyntaxException pse) {
                                System.err.println("Bad regex pattern");
                            }
                        }
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                
                frame.add(button, BorderLayout.SOUTH);
                frame.setSize(300, 250);
                frame.setVisible(true);
            }
        };
        EventQueue.invokeLater(runner);
    }
}