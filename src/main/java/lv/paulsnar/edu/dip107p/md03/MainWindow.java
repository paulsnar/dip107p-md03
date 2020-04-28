package lv.paulsnar.edu.dip107p.md03;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

class MainWindow extends JFrame {
  private static final long serialVersionUID = -4243166903271524406L;

  private StringManager stringManager = new StringManager();

  public MainWindow() {
    super("DIP107 MD03");

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    Container pane = getContentPane();
    BoxLayout layout = new BoxLayout(pane, BoxLayout.Y_AXIS);
    pane.setLayout(layout);

    JTextField input = new JTextField();
    add(input);

    {
      Container container = new Container();
      container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

      JButton addButton = new JButton("Add");
      addButton.addActionListener((event) -> {
        String text = input.getText();
        if (text.trim().length() == 0) {
          JOptionPane.showMessageDialog(MainWindow.this,
            "Lūdzu, ievadiet tekstu, lai to šifrētu.",
            "Nav teksta",
            JOptionPane.WARNING_MESSAGE);
          return;
        }
        stringManager.enqueue(input.getText());
        input.setText("");
      });
      container.add(addButton);

      JButton runButton = new JButton("Run");
      runButton.addActionListener((event) -> {
        stringManager.process();
      });
      container.add(runButton);

      add(container);
    }

    JList<String> queueList = new JList<>(stringManager.getQueueListModel());
    queueList.setFixedCellHeight(15);
    JList<String> resultList = new JList<>(stringManager.getResultsListModel());
    resultList.setFixedCellHeight(15);
    {
      Container container = new Container();
      container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

      JScrollPane queueListPane = new JScrollPane();
      queueListPane.setPreferredSize(new Dimension(200, 300));
      queueListPane.setViewportView(queueList);
      container.add(queueListPane);

      JScrollPane resultListPane = new JScrollPane();
      resultListPane.setPreferredSize(new Dimension(200, 300));
      resultListPane.setViewportView(resultList);
      container.add(resultListPane);
      add(container);
    }

    {
      Container container = new Container();
      container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

      JButton deleteButton = new JButton("Delete");
      deleteButton.addActionListener((event) -> {
        stringManager.removeIndicesFromQueue(queueList.getSelectedIndices());
        queueList.clearSelection();
      });
      container.add(deleteButton);

      JButton clearButton = new JButton("Clear");
      clearButton.addActionListener((event) -> {
        stringManager.clearResults();
      });
      container.add(clearButton);

      add(container);
    }

    pack();

    setVisible(true);
  }
}
