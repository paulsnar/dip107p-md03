package lv.paulsnar.edu.dip107p.md03;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

class StringManager {
  private static class StringListListModel implements ListModel<String> {
    private HashSet<ListDataListener> listeners;
    private List<String> list;

    private StringListListModel(List<String> list) {
      this.list = list;
      listeners = new HashSet<>();
    }

    @Override public int getSize() {
      return list.size();
    }

    @Override public String getElementAt(int index) {
      return list.get(index);
    }

    @Override public void addListDataListener(ListDataListener l) {
      listeners.add(l);
    }

    @Override public void removeListDataListener(ListDataListener l) {
      listeners.remove(l);
    }

    private void dispatchNewEvent(int type, int index0, int index1) {
      ListDataEvent event = new ListDataEvent(this, type, index0, index1);
      Iterator<ListDataListener> iterator = listeners.iterator();
      while (iterator.hasNext()) {
        ListDataListener listener = iterator.next();
        if (type == ListDataEvent.INTERVAL_ADDED) {
          listener.intervalAdded(event);
        } else if (type == ListDataEvent.INTERVAL_REMOVED) {
          listener.intervalRemoved(event);
        } else {
          listener.contentsChanged(event);
        }
      }
    }
  }

  private List<String> queue, results;
  private StringListListModel queueModel, resultsModel;

  StringManager() {
    queue = new LinkedList<>();
    results = new LinkedList<>();

    queueModel = new StringListListModel(queue);
    resultsModel = new StringListListModel(results);
  }

  void enqueue(String item) {
    queue.add(item);
    int index = queue.size() - 1;
    queueModel.dispatchNewEvent(ListDataEvent.INTERVAL_ADDED, index, index);
  }

  void clearQueue() {
    int size = queue.size();
    if (size > 0) {
      queue.clear();
      queueModel.dispatchNewEvent(
        ListDataEvent.INTERVAL_REMOVED, 0, size - 1);
    }
  }

  void removeIndicesFromQueue(int[] indices) {
    if (indices.length > 1) {
      for (int i = indices.length - 1; i >= 0; i -= 1) {
        queue.remove(indices[i]);
      }
      queueModel.dispatchNewEvent(
        ListDataEvent.INTERVAL_REMOVED,
        indices[0], indices[indices.length - 1]);
    } else if (indices.length == 1) {
      queue.remove(indices[0]);
      queueModel.dispatchNewEvent(
        ListDataEvent.INTERVAL_REMOVED, indices[0], indices[0]);
    }
  }

  void clearResults() {
    int size = results.size();
    if (size > 0) {
      results.clear();
      resultsModel.dispatchNewEvent(
        ListDataEvent.INTERVAL_REMOVED, 0, size - 1);
    }
  }

  void process() {
    clearResults();

    Iterator<String> items = queue.iterator();
    while (items.hasNext()) {
      results.add(StringProcessor.process(items.next()));
    }

    int resultCount = results.size();
    resultsModel.dispatchNewEvent(
      ListDataEvent.INTERVAL_ADDED, 0, resultCount - 1);
  }

  ListModel<String> getQueueListModel() {
    return queueModel;
  }

  ListModel<String> getResultsListModel() {
    return resultsModel;
  }
}
