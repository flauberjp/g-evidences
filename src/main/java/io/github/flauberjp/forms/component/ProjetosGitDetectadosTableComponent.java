/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.flauberjp.forms.component;

import io.github.flauberjp.model.GitDir;
import io.github.flauberjp.model.GitDirList;
import io.github.flauberjp.util.Util;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * TableRenderDemo is just like TableDemo, except that it explicitly initializes column sizes and it
 * uses a combo box as an editor for the Sport column.
 */
public class ProjetosGitDetectadosTableComponent extends JPanel {

  public static final String DESCONSIDERAR_HISTORICO = "<DESCONSIDERAR HISTÓRICO>";
  private boolean DEBUG = true;
  JTable table;
  JScrollPane scrollPane;
  Object[][] data = null;
  String username = "";
  private Point hintCell;
  private List<GitDir> gitDirList = null;

  public ProjetosGitDetectadosTableComponent() {
    super(new GridLayout(1, 0));
    atualizarTabela(null, null);
  }

  public void atualizarTabela(List<GitDir> gitDirList, String username) {
    if(gitDirList != null) {
      this.gitDirList = gitDirList;
        this.data = new Object[gitDirList.size()][];
        List<GitDir> list = GitDirList.get();
        for (int i = 0; i < list.size(); i++) {
          GitDir gitDir = list.get(i);
          gitDir.setAuthor(DESCONSIDERAR_HISTORICO);
          data[i] = new Object[] {gitDir.getPath(), gitDir.getActualBranch(), gitDir.getAuthor(), gitDir.isSelected()};
        }
    }

    this.username = username;
    if (scrollPane != null) {
      remove(scrollPane);
    }
    table = new JTable(new MyTableModel(this.data));
    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
    table.setFillsViewportHeight(true);

    //Set up column sizes.
    initColumnSizes(table);

    //Fiddle with the Sport column's cell editors/renderers.
    // setUpSportColumn(table, table.getColumnModel().getColumn(1), -1);

    scrollPane = new JScrollPane(table);
    add(scrollPane);
    if (this.data == null) {
      return;
    }

    initializaUsuarioColumn();

    table.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {

        Point p = e.getPoint();
        int row = table.rowAtPoint(p);
        int col = table.columnAtPoint(p);

        if ((row > -1 && row < table.getRowCount()) && (col == 0 || col == 1)) {

          if (hintCell == null || (hintCell.x != col || hintCell.y != row)) {

            hintCell = new Point(col, row);
            table.isCellEditable(row, col);
          }

        }

      }
    });
  }

  private void initializaUsuarioColumn() {
    for (int i = 0; i < this.data.length; i++) {
      table.isCellEditable(i, 1);
    }
  }

  public String getUsername() {
    return this.username;
  }

  /*
   * This method picks good column sizes.
   * If all column heads are wider than the column's cells'
   * contents, then you can just use column.sizeWidthToFit().
   */
  private void initColumnSizes(JTable table) {
    MyTableModel model = (MyTableModel) table.getModel();
    TableColumn column = null;
    Component comp = null;
    int headerWidth = 0;
    int cellWidth = 0;
    Object[] longValues = model.longValues;
    TableCellRenderer headerRenderer =
        table.getTableHeader().getDefaultRenderer();

    for (int i = 0; i < 2; i++) {
      column = table.getColumnModel().getColumn(i);

      comp = headerRenderer.getTableCellRendererComponent(
          null, column.getHeaderValue(),
          false, false, 0, 0);
      headerWidth = comp.getPreferredSize().width;

      if (DEBUG) {
        System.out.println("Initializing width of column "
            + i + ". "
            + "headerWidth = " + headerWidth);
      }

      if (model.data == null || model.data.length == 0) {
        continue;
      }
      Class columnClass = model.getColumnClass(i);
      TableCellRenderer tableCellRenderer = table.getDefaultRenderer(columnClass);
      comp = tableCellRenderer.
          getTableCellRendererComponent(
              table, longValues[i],
              false, false, 0, i);
      cellWidth = comp.getPreferredSize().width;

      if (DEBUG) {
        System.out.println("; cellWidth = " + cellWidth);
      }

      column.setPreferredWidth(Math.max(headerWidth, cellWidth));
    }
  }

  private void setUpProjetoGitColumn(JTable table,
      TableColumn projetoGitColumn, int rowIndex) {
    if(rowIndex == -1 || GitDirList.get() == null ||
    		GitDirList.get().size() <= rowIndex) {
      return;
    }
    DefaultTableCellRenderer renderer =
        new DefaultTableCellRenderer();
    renderer.setToolTipText(GitDirList.get().get(rowIndex).getPath());
    projetoGitColumn.setCellRenderer(renderer);
  }

  private void setUpBranchAtualColumn(JTable table,
      TableColumn branchAtualColumn, int rowIndex) {
    if(rowIndex == -1 || GitDirList.get() == null ||
        GitDirList.get().size() <= rowIndex) {
      return;
    }
    DefaultTableCellRenderer renderer =
        new DefaultTableCellRenderer();
    renderer.setToolTipText(GitDirList.get().get(rowIndex).getActualBranch());
    branchAtualColumn.setCellRenderer(renderer);
  }

  private void setUpUsuarioColumn(JTable table,
      TableColumn usuarioColumn, int rowIndex) {
    if(rowIndex == -1 || GitDirList.get() == null ||
	  GitDirList.get().size() <= rowIndex) {
      return;
    }
    //Set up the editor for the sport cells.
    JComboBox comboBox = new JComboBox();

    String[] authors = GitDirList.get().get(rowIndex).getAuthors();
    for (int i = 0; i < authors.length; i++) {
      comboBox.addItem(authors[i]);
    }
    comboBox.addItem(DESCONSIDERAR_HISTORICO);
    usuarioColumn.setCellEditor(new DefaultCellEditor(comboBox));

    //Set up tool tips for the sport cells.
    DefaultTableCellRenderer renderer =
        new DefaultTableCellRenderer();
    renderer.setToolTipText("Qual destes e-mails é o seu nesse projeto? Escolha a opção <DESCONSIDERAR HISTÓRICO> caso não queira recuperar seu histórico.");
    usuarioColumn.setCellRenderer(renderer);
  }

  class MyTableModel extends AbstractTableModel {

    private String[] columnNames = {
        "Projeto Git",
        "Branch atual",
        "E-mail do seu usuário nesse projeto",
        "Configurar?"};
    private Object[][] data;

    public MyTableModel(Object[][] data) {
      super();
      this.data = data;
    }

    public final Object[] longValues = {"C:\\Users\\Flaviano Flauber\\Documents\\Projetos\\youtube-carousel-bootstrap",
        "master",
        "E-mail do seu usuário nesse projeto",
        Boolean.FALSE};

    public int getColumnCount() {
      return columnNames.length;
    }

    public int getRowCount() {
      return data == null ? 0 : data.length;
    }

    public String getColumnName(int col) {
      return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
      if(col == 1 && ((String)data[row][col]).equalsIgnoreCase(DESCONSIDERAR_HISTORICO)) {
        GitDir gitDir = GitDirList.get().get(row);
        for (String author: gitDir.getAuthors()
        ) {
          if(author.contains(getUsername())) {
        	gitDir.setAuthor(author);  
            data[row][col] = author;
            break;
          }
        }
      }
      return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
      return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
      //Note that the data/cell address is constant,
      //no matter where the cell appears onscreen.
      if (col == 0) {
        setUpProjetoGitColumn(table, table.getColumnModel().getColumn(col), row);
        return false;
      } else if (col == 1) {
        setUpBranchAtualColumn(table, table.getColumnModel().getColumn(col), row);
        return false;
      } else if (col == 2) {
        setUpUsuarioColumn(table, table.getColumnModel().getColumn(col), row);
        return true;
      } else {
        return true;
      }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
      if (DEBUG) {
        System.out.println("Setting value at " + row + "," + col
            + " to " + value
            + " (an instance of "
            + (value == null ? null : value.getClass()) + ")");
      }

      data[row][col] = value == null ? "" : value;
      
      gitDirList.get(row).setAuthor((String)data[row][2]);
      gitDirList.get(row).setSelected((boolean)data[row][3]);
      
      fireTableCellUpdated(row, col);

      if (DEBUG) {
        System.out.println("New value of data:");
        printDebugData();
      }
    }

    private void printDebugData() {
      int numRows = getRowCount();
      int numCols = getColumnCount();

      for (int i = 0; i < numRows; i++) {
        System.out.print("    row " + i + ":");
        for (int j = 0; j < numCols; j++) {
          System.out.print("  " + data[i][j]);
        }
        System.out.println();
      }
      System.out.println("--------------------------");
    }
  }


}
