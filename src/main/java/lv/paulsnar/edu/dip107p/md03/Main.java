package lv.paulsnar.edu.dip107p.md03;

import javax.swing.SwingUtilities;

public class Main implements Runnable {
  private Main() {
  }

  public void run() {
    new MainWindow().setVisible(true);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Main());
  }
}
